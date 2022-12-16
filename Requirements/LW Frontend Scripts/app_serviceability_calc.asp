<%@  language="VBScript" %>
<%
Option explicit 
Response.Buffer = true 
Response.Expires = -10000  	
Response.AddHeader "Pragma", "no-cache"
Response.AddHeader "cache-control", "no-store"
Server.ScriptTimeout = 0

'Felix Chu 12/12/2005
'[1] 20060502 Chad Wu if one if the item is not required by funder, not take into account.
'[2] 20070101 Fred Chan new serviceability function
'
'[4][890]Shafrina Farid - 22 July 2010 - Change Update mode based on sec task to Allow Editing Locked Application
'migrated code from MGIC S3
'[2] [509] Change, remove, and add sections and calculations.
'[3] Anagan Babu 10 Jul 2007 
'[4] Anagan Babu 23 Jul 2007: Linking DLA Management 
'[5] BL 10/08/2007 Update the credit assessment display
'[6] Anagan Babu 14 Aug 2007 : Added MRF Warning
'[7] BL 19/12/2007 ADD ARREARS WARNING
'[8] Alexey Lutsenko 17/04/2008 - HTML Cleanup
'[9][564.7]EY 05/05/2008 add MGIC serviceability calculation
'[10][564.4] BL 18/05/08 MGIC PROJECT
'[11] Hide the button.
'[12] 25/08/2008 Add new line connector
'[13][613.54] EY 05/09/2008 add new field dInitalCreditDecisionRunTime so that the run datetime stamp will not
'			change on Decline/Approve Loan
'[14][644.10] Jie 2/10/08: MGIC stage 3 required: Cross Coll. warning
'[15][644.11] EY 17/11/08 Add Borrower Explosure functionality in Decisioning screen
'[16][670.21] RC 23/12/2008 Created CheckLoanPurposeExist() ASP function to check if there are loan purposes assigned
'   for all products before locking the application
'[17][1069] GB 06/06/2011: Look And Feel  
'[18][1083] CJL 24/07/2011: Gateway - New Credit Rules
'           - Display Borrower Income Result
'           - Get fTotalGrossIncome from serviceability webservice and pass parameter value to sp_AppCreditDecision
'[19][1083]EY 15/07/2011 Add iDecisionRunID to GetDLASetting store proc
'[20][1083] EY 20/09/2011 add LockApplication logic
'[21][1196] RA 17/04/2012 NMC Branding project - Screen header freeze
'[22][2548] JPN 27/06/2017 - Internal - LoanworksV10R9 rebooted
'                         - made Footer visible on screen
'[23][3531] PF 06/02/2020 - YBR Securitisation: 
'                         - Added DTI and Loan to Gross 
'                         - Added Override Notes 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<% 'SERVER SCRIPT INCLUSIONS (PLEASE REMOVE THOSE INCLUDES THAT ARE NOT NEEDED) %>
<!-- #include file="incSecurity.asp" -->
<!-- #include file="incADO.asp" -->
<!-- #include file="incConstants.asp" -->
<!-- #include file="incServerUtils.asp" -->
<!-- #include file="app_incApplication.asp" -->
<!-- #include file="func_ox_serviceability_send_process.asp" -->
<!-- #include file="func_app_serviceability_calc.asp" -->
<!-- #include file="func_ado_GlobalSettings_GetValue.asp" -->
<!--[3]-->
<!-- #include file="func_ado_app_main.asp" -->
<!--[4]-->
<!-- #include file="func_ado_wf_validate_rules.asp" -->
<% '[670.21] %>
<% 'CIENT SCRIPT INCLUSIONS (PLEASE REMOVE THOSE INCLUDES THAT ARE NOT NEEDED) %>
<%
'DECLARATIONS
Dim myTitle
Dim myPageName
Dim myTableName
Dim mySection1Header
Dim mySection2Header
Dim mySection3Header
Dim myFormName
Dim myImageAdd 
Dim	myImageUpdate 
Dim	myImageDelete 
Dim	myImageParent 
Dim	myImageTheme 
Dim	mycolor1 
Dim	mycolor2
Dim	myFormColor
Dim myAction
Dim mycolor
Dim myIDFieldName
Dim dAppDate

Dim action
Dim granted


Dim iStatus
Dim myDeleteConfirmMessage
Dim iAppID
Dim iStmOwner,iStmID
Dim iBorrowerID
Dim iCorporateID
Dim iAppOwner
Dim cAction

dim fActualNSR
dim fActualDSR
dim fExpectedNSR
dim fActualLMINSR
dim fExpectedDSR
Dim fExpectedMaxAmt
Dim fExpectedMinAmt
DIM fActualLVR
dim iOverAllResult
dim iMessageTypeID,cDisabled, bAllowChangeCCDecision
dim iSelResult, iSelRule
dim bDLASetting, iDecisionID, sApprovedMessage, sLevelExceedMessage '[4]
dim rs '[6]
dim cTempAppStatus

'[509]
dim fActualLMIDSR
dim fActualNDI
dim fActualTotalOutgoing 


dim objXMLDoc       '[1083] 

'[564.7]
dim fNetMonthlySurplus, fNDIRatio

dim iAllProductHasLoanPurpose  '[670.21]

Dim fTotalGrossIncome   '[1083]
dim bLockApplication	'[1083]
'[3531] - START
dim cOverrideNote
dim fDTI,fLTG
'[3531] - END

Call main()
sub main()
	Call ReadInputs() 'from the incoming form if any
	Call GetApplicationHeader(iAppID)
    Call Configure()
	Call GetUserRights() 'determine user rights
	bLockApplication = LockApplication(iAppID, UserID)	'[1083]
		
	Select Case action
		Case "updateexec"
			call updateexec()
			'Response.End
			Response.Redirect(myPageName & "?iAppID=" & iAppID)
			action="Update"
		case "ChangeResult"
		    call ChangeResult(0)            '[2911.4] - If Internal Credit Decision Set to 0
			action="Update"	
        '[2911.4] - START
        case "ChangeExternalResult"
		    call ChangeResult(1)            'If External Credit Decision Set to 1
			action="Update"	
        '[2911.4] - END
		case "Approve"		     
	        call Approve()
		    action = "Update"			
        case "Decline"
		    call Decline()
			action="Update"
	End Select
End sub


'================================================================
'GETUSERRIGHTS
'================================================================
Private Function GetUserRights()	
	'check 'User Rights for Applicaion
	dim bUpdateApplication
	Dim bAllowEditingLockedApplication '[890]
	
	bAllowChangeCCDecision= GetTaskPermission(UserID, SEC_APP_ALLOW_UPDATE_CREDIT_DECISION)
	if bAllowChangeCCDecision=1 then
	    cDisabled=""
	end if
	
	bUpdateApplication = GetTaskPermission( UserID, SEC_APP_APPLICATION_CAN_UPDATE )
	bAllowEditingLockedApplication = GetTaskPermission(UserID, SEC_APP_ALLOW_UPDATE_LOCKED)'[890]

	if not IsAssignedApplication( iAppID ) then
		granted = FIELD_DISABLED
	else
		'get latest application status
		dim iLatestAppStatus
		iLatestAppStatus = getLatestAppStatus( iAppID )
		select case iLatestAppStatus
			case LATEST_STATUS_IS_WITHSOLICITOR, LATEST_STATUS_IS_SETTLED
				dim iUserOwnerRelationship
				iUserOwnerRelationship = GetUserOwnerRelationship( UserID, iAppID )
				select case iUserOwnerRelationship
					case USEROWNERRELATIONSHIP_APP_ONLY, USEROWNERRELATIONSHIP_BOTH_NOT 'AppOwner or With Right only, Both NOT
						granted = FIELD_DISABLED
					case USEROWNERRELATIONSHIP_STM_ONLY, USEROWNERRELATIONSHIP_BOTH 'StmOwner or With Right only, Both YES
						if bUpdateApplication then
							granted = FIELD_ACTIVE
						else
							granted = FIELD_DISABLED
						end if

					case else
						granted = FIELD_DISABLED
				end select
			case LATEST_STATUS_IS_REJECTEDORCANCELED, LATEST_STATUS_IS_DISCHARGEDORPAYOUT
				granted=FIELD_DISABLED 
			
			case LATEST_STATUS_IS_SETTLEDPLUSLOCKED		'[890]
				If bAllowEditingLockedApplication = 1 then 
					granted = FIELD_ACTIVE
				else
					granted=FIELD_DISABLED	
				End if
				
			case else 'Before with solicitor and not rejected or canceled
				if IsUserAppOwnerOrWithRight( UserID, iAppID ) then
					if bUpdateApplication then
						granted = FIELD_ACTIVE
					else
						granted = FIELD_DISABLED
					end if
				else
					granted = FIELD_DISABLED
				end if
		end select
	end if
		
End Function


'================================================================
'READ INPUTS FROM INCOMING FORM
'================================================================
Private Function ReadInputs()
	
	action = GetInput("action", Request.Form)
	
	iBorrowerID = GetInput("iBorrowerID", Request.Form)
	iCorporateID = GetInput("iCorporateID", Request.Form)
	iAppID = GetInput("iAppID",Request.Form)
    iSelResult=GetInput("iSelResult",Request.Form)
    iSelRule=GetInput("iSelRule",Request.Form)
    iDecisionID = GetInput("iDecisionID",Request.Form) '[4]
    fFinalLoanAmount = GetInput("fFinalLoanAmount",Request.Form) '[4]
    cOverrideNote = GetInput("cOverrideNote",Request.Form) '[3531]
	if action="" then
		action = "update"
	end if
End Function


'CONFIGURATION
'============================================================
'============================================================
Private sub Configure()	
	myTitle = "Credit Decision"
	myPageName = "app_serviceability_calc.asp"	
	mySection1Header = "Serviceability"
	mySection2Header = "Overall Result"
	mySection3Header = "Serviceability Rules Result"
	myFormName  = "frmServiceabilityCal"
	myImageAdd = "images/market_01over.jpg"
	myImageUpdate = "images/update.jpg"
	myImageDelete = "images/delnote.gif"
	myImageParent = "images/aplpication_01over.jpg"
	myImageTheme = "images/product-01.jpg"
	mycolor2 = "#ffffff"
	mycolor1 = "#faeafa"
	myFormColor = "floralwhite"
	myIDFieldName = "iFundsPositionID"
	myDeleteConfirmMessage = GetError(UserID, "CONFIRM_DELETE")
    cDisabled="disabled"
	'sApprovedMessage = GetError(UserID, "MESSAGE_DECISION_APPROVED")                   '[1069]
	'sLevelExceedMessage = GetError(UserID, "MESSAGE_DECISION_APPROVALLEVELEXCEEDED")   '[1069]
	iCurDecisionRunID = 0
    sLVRWarning = GetError(UserID, "ERR_LVR_WARNING") '[6]
    sMRFNotValidWarning = GetError(UserID, "ERR_MRF_NOTVALID_WARNING") '[6]
    
End sub


'============================================================
'============================================================
Private sub updateexec()
    'on error resume next
	'Get Actual NSR and DSR from OX
	Call GetOxMessageTypeByOxMessageTypeID("FSERVREQ",iMessageTypeID)
	
	'Call GetBorrowerExposure(iAppID)	'[644.11] [1066] comment out for now since module is not compatible for GW
    
	Call GetActualServiceabiltyResults(iAppID, iMessageTypeID, fActualNSR, fActualDSR, fActualLMINSR, _
        fActualLMIDSR, fActualLVR, fActualNDI, fActualTotalOutgoing, fNetMonthlySurplus, fNDIRatio, objXMLDoc, _
        fTotalGrossIncome,fDTI,fLTG)    '[564.7][1083][3531]
	
	'Get Actual LVR
	Call GetAppActualLVR(iAppID, fActualLVR)
	fActualLVR = fActualLVR / 100

    'Credit Decision for program guideline
    dim iDecisionRunID
    iDecisionRunID = 0
	
	Response.Write("AppCreditDecision_ProgramGuideline START<br/>")
	Response.Write(iAppID)
	Response.Write("<br/>")
	Response.Write(UserID)
	Response.Write("<br/>")
	Response.Write(fActualNSR)
	Response.Write("<br/>")
	Response.Write(fActualDSR)
	Response.Write("<br/>")
	Response.Write(fActualLMINSR)
	Response.Write("<br/>")
	Response.Write(fActualLMIDSR)
	Response.Write("<br/>")
	Response.Write(fActualLVR)
	Response.Write("<br/>")
	Response.Write(fActualNDI)
	Response.Write("<br/>")
	Response.Write(fActualTotalOutgoing)
	Response.Write("<br/>")
	Response.Write(fNetMonthlySurplus)
	Response.Write("<br/>")
	Response.Write(fNDIRatio)
	Response.Write("<br/>")
	Response.Write(fTotalGrossIncome)
	Response.Write("<br/>")
	Response.Write(fDTI)
	Response.Write("<br/>")
	Response.Write(fLTG)
	Response.Write("<br/>")
	
    Call AppCreditDecision_ProgramGuideline(iAppID, iDecisionRunID, UserID, fActualNSR, fActualDSR, _
            fActualLMINSR, fActualLMIDSR, fActualLVR, fActualNDI, fActualTotalOutgoing, fNetMonthlySurplus, fNDIRatio, _
            fTotalGrossIncome,fDTI,fLTG) '[564.7][1083][3531]
            
	Response.Write("AppCreditDecision_ProgramGuideline END<br/>")

    Call AppCreditDecision_BorrowerIncomeResult(iDecisionRunID, objXMLDoc)    '[1083]
    

	'Get Expected NSR and DSR
	'Call GetAppExpectedNSR(iAppID,fExpectedNSR)
	'Call GetAppExpectedDSR(iAppID,fExpectedDSR)
	
	'Calculate Result
	'Call CalAppNSRResult(iAppID,fExpectedNSR,fActualNSR,UserID)
	'Call ServiceabilityTest(iAppID, fActualLVR, fActualNSR, fActualLMINSR, UserID)
End sub


'============================================================
'============================================================
private sub ChangeResult(iType)
    '[4]
    'call UpdateSrvResult(iappid, iSelRule, iSelResult)
    'call ChangeDecisionResult(iDecisionID)
     call ChangeDecisionResult(iDecisionID, iType, cOverrideNote)           '[2911.4]
end sub

'[4]
private sub Approve() '[4]
    if ApproveLoan(iDecisionID, fFinalLoanAmount) = true then
    Response.Write("<script language='javascript'>alert('Please enter a valid valuation before approving Decisioning');</script>")
    end if
end sub
'[4] End


'[4]
private sub Decline()
    call DeclineLoan(iDecisionID, fFinalLoanAmount)
end sub
'[4] End


Private Function ShowDecisionID(iCurDecisionRunID)
     ShowDecisionID = ""
     If iCurDecisionRunID <> 0 Then ShowDecisionID = " (Decision ID: " & iCurDecisionRunID & ")"
End Function

%>
<head>
    <title>Credit Decision</title>
    <!-- #include file="incStylesheet.asp" -->
    <!--[1069] -->
</head>
<!-- BODY -->
<body>
    <div id="site-container">
        <script type="text/javascript" src="wz_tooltip.js"></script>
        <!--[1069] BEGIN -->
        <%gPageTitle="Serviceability"%>
        <div id="topbody" class="wide pad-left-header">
            <!--[1196]-->
            <!-- #include file="incTopBanner.asp" -->
            <!-- #include file="app_incHeaderID.asp" -->
            <!-- #include file="incMenu.asp" -->
        </div>
        <!--[1069] END -->
        <div id="lowerbody">
            <!-- [1196] move the include file -->
            <!-- #include file="app_incBorrowerList.asp" -->
            <!-- #include file="MortgageRiskPremiumFee_Warning.asp" -->
            <!--[6]-->
            <div id="main" class="wide pad-left-body">
                <div id="content">
				<a id="link" href="https://assessment.betterchoice.com.au/home/<%=iAppID %>/summary", target= "_blank">View Credit Assessment</a>
                    <div class="one-column">
                        <div class="column-a">
                            <%
 fFinalLoanAmount = GetFinalValue(iAppID, "BaseLoan") '[4] 
 cTempAppStatus = GetCurrentApprovedStatusByAppID(iAppID) 
                            %>
                            <!--[509] begin-->
                            <table border="0" width="100%" cellpadding="3" cellspacing="3">
                                <!--[4] Start-->
                                <%If cTempAppStatus = "Approved"Then %>
                                <tr>
                                    <td align="center">
                                        <font color="red" size="2"><b>
                                            <%=sApprovedMessage%></b></font>
                                    </td>
                                </tr>
                                <%End If %>
                                <%If Trim(GetApprovalsRequired(fFinalLoanAmount)) = "Approval Levels Exceeded" Then %>
                                <tr>
                                    <td align="center">
                                        <font color="red" size="2"><b>
                                            <%=sLevelExceedMessage%></b></font>
                                    </td>
                                </tr>
                                <%End If %>
                                <!--[4] End-->
                            </table>
                            <%	dim iDecisionRunID
		dim rsAppCreditDecisionPGResult
		dim bHasResult, bHideApprove,bHideDecline
		dim cAppliedPrograms
		bHasResult = False
		bHideApprove = False
		bHideDecline = false
		iDecisionRunID = 0
		set rsAppCreditDecisionPGResult = GetAppCreditDecisionPGResult(iAppID, iDecisionRunID)
        if not rsAppCreditDecisionPGResult.Eof then
	        bHasResult = True
	        cAppliedPrograms = rsAppCreditDecisionPGResult("cAppliedProgramGuideline")
	        if cAppliedPrograms = "" then
	            bHideApprove = True
	            bHideDecline = true
	            'cAppliedPrograms = "Application does not have applied programs."
                cAppliedPrograms = "Please check if the following meets Credit Policy:" '[3531]
	        end if
        end if
                            %>
                            <!--[4] Start-->
                            <% 'Call ShowSectionHeader("Credit Decision History") %>
                            <div class="block collapsable block-shadowed">
                                <div class="inner">
                                    <h2 class="bg-bluelight">
                                        <span class="title">Credit Decision History</span> <span class="collapse-handle"><a
                                            href="#">Hide <em>-</em></a></span></h2>
                                    <div class="content open">
                                        <table border="0" class="cmn-table">
                                            <tr>
                                                <th>
                                                    Decision ID
                                                </th>
                                                <th>
                                                    Run Date/Time
                                                </th>
                                                <th>
                                                    Credit Decision<br />
                                                    Performed By
                                                </th>
                                                <th>
                                                    Loan Amount
                                                </th>
                                                <th>
                                                    Current Status
                                                </th>
                                                <th>
                                                    Status Changed
                                                </th>
                                                <th>
                                                    Approvals<br />
                                                    Required
                                                </th>
                                                <th>
                                                    Approve
                                                </th>
                                                <th>
                                                    Decline
                                                </th>
                                            </tr>
                                            <% 	
		dim rsAppDecisionHistory, fFinalLoanAmount, cCurrentDecisionStatus, iCntr, iCurDecisionRunID
		dim bHideCDPerformButton, iNo_of_approvalRequired
		set rsAppDecisionHistory =  GetAppDecisionRunHistory(iAppID) 
		
        iCntr = 1
		while not rsAppDecisionHistory.EOF
			if mycolor = mycolor1 then
				mycolor = mycolor2
			else
				mycolor = mycolor1
			end if	
			cCurrentDecisionStatus = GetCurrentDecisionStatus(rsAppDecisionHistory("iDecisionRunID"))
		 
			If cCurrentDecisionStatus <> "Referred" Then
				If GetCurrentApprovedStatus(rsAppDecisionHistory("iDecisionRunID")) = "Approved" Then
					 cCurrentDecisionStatus = GetCurrentApprovedStatus(rsAppDecisionHistory("iDecisionRunID"))
				End If
				'If cCurrentDecisionStatus = "Approved" or cCurrentDecisionStatus = "Declined"  Then bHideCDPerformButton = True     'Allow to reapprove 20211125 
			End If

			If  bHideCDPerformButton = True and Not iCntr = 1 Then
				iNo_of_approvalRequired = rsAppDecisionHistory("iNo_of_Approvals_Required")
			Else
				iNo_of_approvalRequired =  GetApprovalsRequired(fFinalLoanAmount)
			End If
                                            %>
                                            <tr valign="top">
                                                <td>
                                                    <%=rsAppDecisionHistory("iDecisionRunID")%>
                                                </td>
                                                <!--[613.54]-->
                                                <%if isnull(rsAppDecisionHistory("dInitalCreditDecisionRunTime")) then  %>
                                                <td>
                                                    <%=rsAppDecisionHistory("dCreditDecisionRunTime")%>
                                                </td>
                                                <%else %>
                                                <td>
                                                    <%=rsAppDecisionHistory("dInitalCreditDecisionRunTime")%>
                                                </td>
                                                <%end if%>
                                                <td>
                                                    <%=GetUserName(rsAppDecisionHistory("iUserID"))%>
                                                </td>
                                                <td>
                                                    $<%=LMSAmount(rsAppDecisionHistory("fFinalLoanAmount")) %>
                                                </td>
                                                <td>
                                                    <%=cCurrentDecisionStatus%>
                                                </td>
                                                <td>
                                                    <%=GetUserName(rsAppDecisionHistory("iStatusUpdateByUserID"))%>
                                                    <%if  rsAppDecisionHistory("iStatusUpdateByUserID") <> "0" then%>
                                                    &nbsp
                                                    <%end if%>
                                                    <%=rsAppDecisionHistory("dCreditDecisionRunTime")%>
                                                </td>
                                                <td>
                                                    <%=iNo_of_approvalRequired%>
                                                </td>
                                                <td>
                                                    <span style="display:none">
                                                        <%=GetUserApprovalLevelRight(iAppID) %>
                                                        <%=iCntr %>
                                                        <%=cCurrentDecisionStatus %>
                                                        <%=UserID %>
                                                        <%=IsUserAlreadyApproved(rsAppDecisionHistory("iDecisionRunID")) %>
                                                        <%=bHideApprove %>
                                                        <%=CheckAllProductHasLoanPurpose(iAppId)  %>
                                                    </span>

                                                    
                                                 
                                                    <% If GetUserApprovalLevelRight(iAppID) = 1 and  iCntr = 1 and _
					        (cCurrentDecisionStatus = "Conditionally Approved" or cCurrentDecisionStatus = "Pending Approval" or cCurrentDecisionStatus = "Referred") and _
					        IsUserAlreadyApproved(rsAppDecisionHistory("iDecisionRunID"))<>1 and  bHideApprove = False Then
					        
					        iAllProductHasLoanPurpose = CheckAllProductHasLoanPurpose(iAppId)   '[670.21]
                                                    %>
                                                    <form id="frmApprove" name="frmApprove" method="post" action="app_serviceability_calc.asp">
                                                    <input type="Hidden" name="action" value="Approve" />
                                                    <input type="Hidden" name="fFinalLoanAmount" value="<%=fFinalLoanAmount%>" />
                                                    <input type="hidden" name="iAppID" id="Hidden" value="<%=iAppID%>" />
                                                    <input type="hidden" name="iDecisionID" id="Hidden1" value="<%=rsAppDecisionHistory("iDecisionRunID")%>" />
                                                    <input type="submit" value="Approve" onclick="return (validate());" />
                                                    <%'[670.21] %>
                                                    </form>
                                                    <%End If %>
                                                </td>
                                                <td>

                                                    <% If GetUserApprovalLevelRight(iAppID) = 1 and  iCntr = 1 and _
					(cCurrentDecisionStatus = "Referred" or cCurrentDecisionStatus = "Pending Approval" or cCurrentDecisionStatus = "Referred") and _
					IsUserAlreadyApproved(rsAppDecisionHistory("iDecisionRunID"))<>1 and  bHideDecline = False Then%>
                                                    <form id="Form1" name="frmApprove" method="post" action="app_serviceability_calc.asp">
                                                    <input type="Hidden" name="action" value="Decline" />
                                                    <input type="Hidden" name="fFinalLoanAmount" value="<%=fFinalLoanAmount%>" />
                                                    <input type="hidden" name="iAppID" id="Hidden2" value="<%=iAppID%>" />
                                                    <input type="hidden" name="iDecisionID" id="Hidden3" value="<%=rsAppDecisionHistory("iDecisionRunID")%>" />
                                                    <input type="submit" value="Decline" />
                                                    </form>
                                                    <%End If %>
                                                </td>
                                            </tr>
                                            <%
			If iCntr = 1 Then iCurDecisionRunID = rsAppDecisionHistory("iDecisionRunID")
			rsAppDecisionHistory.movenext
			iCntr =  iCntr  + 1
		wend
		
		rsAppDecisionHistory.close
		set rsAppDecisionHistory = nothing
                                            %>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <!--[4] end-->
                            <div class="block collapsable block-shadowed">
                                <div class="inner">
                                    <h2 class="bg-bluelight">
                                        <span class="title">Credit Approval History</span> <span class="collapse-handle"><a
                                            href="#">Hide <em>-</em></a></span></h2>
                                    <div class="content open">
                                        <!--[4] Start-->
                                        <table border="0" class="cmn-table">
                                            <tr>
                                                <th>
                                                    Decision ID
                                                </th>
                                                <th>
                                                    Approved On
                                                </th>
                                                <th nowrap>
                                                    Approved/Declined By
                                                </th>
                                                <th nowrap>
                                                    Approved Amount
                                                </th>
                                                <th nowrap>
                                                    Approval Status
                                                </th>
                                            </tr>
                                            <% 	
		dim rsAppApprovalHistory
		set rsAppApprovalHistory =  GetAppDecisionApprovalHistory(iCurDecisionRunID) 
		
		while not rsAppApprovalHistory.EOF
			if mycolor = mycolor1 then
				mycolor = mycolor2
			else
				mycolor = mycolor1
			end if	
                                            %>
                                            <tr valign="top">
                                                <td>
                                                    <%=rsAppApprovalHistory("iDecisionID")%>
                                                </td>
                                                <td>
                                                    <%=rsAppApprovalHistory("dDateApproved")%>
                                                </td>
                                                <td>
                                                    <%=GetUserName(rsAppApprovalHistory("iUserID"))%>
                                                </td>
                                                <td>
                                                    $<%=LMSAmount(rsAppApprovalHistory("fApprovedAmount")) %>
                                                </td>
                                                <td>
                                                    <%=rsAppApprovalHistory("cCurrentStatus") %>
                                                </td>
                                            </tr>
                                            <%
			rsAppApprovalHistory.movenext
				
		wend
			
		rsAppApprovalHistory.close
		set rsAppApprovalHistory = nothing
                                            %>
                                        </table>
                                        <!--[4] end-->
                                    </div>
                                </div>
                            </div>
                            <% 
		dim bTurnOffOldDecision
		bTurnOffOldDecision = true
		if not bTurnOffOldDecision then
                            %>
                            <div class="block collapsable block-shadowed">
                                <div class="inner">
                                    <h2 class="bg-bluelight">
                                        <span class="title">
                                            <%=mySection3Header%></span> <span class="collapse-handle"><a href="#">Hide <em>-</em></a></span></h2>
                                    <div class="content open">
                                        <table class="cmn-table">
                                            <tr>
                                                <th>
                                                    Rule
                                                </th>
                                                <th>
                                                    Rule Value
                                                </th>
                                                <!--[509]-->
                                                <th>
                                                    Application Value
                                                </th>
                                                <!--[509]-->
                                                <th>
                                                    Result
                                                </th>
                                                <th>
                                                    Last User Modified
                                                </th>
                                                <th>
                                                    Last Modified Date
                                                </th>
                                            </tr>
                                            <% 	
				'[509] begin
				dim rsAppServiceabilityResult
			
				set rsAppServiceabilityResult =  GetAppServiceabilityResult(iAppID) 
				'[509] end

				while not rsAppServiceabilityResult.EOF
					if mycolor = mycolor1 then
						mycolor = mycolor2
					else
						mycolor = mycolor1
					end if	
                                            %>
                                            <tr valign="top">
                                                <td>
                                                    <%=rsAppServiceabilityResult("varNodeTypeDescription")%>
                                                </td>
                                                <%if vNumber(rsAppServiceabilityResult("fExpectedValue"))>0 then%>
                                                <td>
                                                    <%'[509] %>
                                                    <%if rsAppServiceabilityResult("iAmountType") = 2 then %>
                                                    <%=LMSAMOUNT(rsAppServiceabilityResult("fExpectedValue"))%>
                                                    <%else %>
                                                    <%=rsAppServiceabilityResult("fExpectedValue")%>
                                                    <%end if %>
                                                </td>
                                                <%else'[1]%>
                                                <td>
                                                    Not Required
                                                </td>
                                                <%end if%>
                                                <td>
                                                    <%'[509] %>
                                                    <%if rsAppServiceabilityResult("iAmountType") = 2 then %>
                                                    <%=LMSAMOUNT(rsAppServiceabilityResult("fActualValue"))%>
                                                    <%else %>
                                                    <%=rsAppServiceabilityResult("fActualValue")%>
                                                    <%end if %>
                                                </td>
                                                <td>
                                                    <select <%=cDisabled%> id="cboServRst<%=rsAppServiceabilityResult("iNodeTypeID")%>"
                                                        name="cboServRst" tag="<%=rsAppServiceabilityResult("iNodeTypeID")%>" onchange="javascript:updateResult(this)">
                                                        <%=GetComboStringFromLookUp(200,rsAppServiceabilityResult("iResult") ) %>
                                                    </select>
                                                </td>
                                                <td>
                                                    <%=rsAppServiceabilityResult("cUserName")%>
                                                </td>
                                                <td>
                                                    <%=rsAppServiceabilityResult("dDateTime")%>
                                                </td>
                                            </tr>
                                            <%
					rsAppServiceabilityResult.movenext
				wend
				
				rsAppServiceabilityResult.close
				set rsAppServiceabilityResult = nothing
                                            %>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <%end if %>
                            <!--[509] begin-->
                            <!--Applied Program guidlines-->
                            <div class="block collapsable block-shadowed">
                                <div class="inner">
                                    <h2 class="bg-bluelight">
                                        <span class="title">Applied Program Guidelines</span> <span class="collapse-handle">
                                            <a href="#">Hide <em>-</em></a></span></h2>
                                    <div class="content open">
                                        <table class="cmn-table">
                                            <% 	      
		  if bHasResult = True then          
                                            %>
                                            <tr>
                                                <td colspan="3" align="left">
  
                                                    <b>Program Guideline(s) Applied:</b>&nbsp;
                                                    <span style="color:red">
                                                    <%=cAppliedPrograms %>
                                                    <% if cAppliedPrograms = "Please check if the following meets Credit Policy:" then %>
                                                    </span>
                                                        <ul style="margin-left:60px; list-style-type:disc; color:red;  font-weight: bold;">
                                                          <li>Application tab - Funder</li>
                                                          <li>Application tab - Regulated/Unregulated</li>
                                                          <li>Application tab - Primary Loan Purpose</li>
                                                          <li>Products Tab</li>
                                                          <li>Loan Purpose Tab</li>
                                                          <li>Security Property Type</li>
                                                        </ul>
                                                    <% end if %>
                                                </td>
                                                <td colspan="2" align="right">
                                                    &nbsp;
                                                </td>
                                                <!--[4]-->
                                            </tr>
                                            <%Else%>
                                            <tr>
                                                <td colspan="3" align="left">
                                                    <!-- [3531] -->
                                                    <b>Program Guideline(s) Applied:</b>&nbsp; <span style="color:red">Please check if the following meets Credit Policy:</span>
                                                        <ul style="margin-left:60px; list-style-type:disc; color:red;  font-weight: bold;">
                                                          <li>Application tab - Funder</li>
                                                          <li>Application tab - Regulated/Unregulated</li>
                                                          <li>Application tab - Primary Loan Purpose</li>
                                                          <li>Products Tab</li>
                                                          <li>Loan Purpose Tab</li>
                                                          <li>Security Property Type</li>
                                                        </ul>
                                                    <!-- [3531] -->
                                                </td>
                                                <td colspan="2" align="right">
                                                    &nbsp;
                                                </td>
                                            </tr>
                                            <%end if %>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <!--[509] end-->
                            <!--BL: Credit Assessment begin-->
                            <!-- program strucutre:
         for each borrower:
            display the CreditAssessment result
         loop   
         -->
                            <div class="block collapsable block-shadowed">
                                <div class="inner">
                                    <h2 class="bg-bluelight">
                                        <span class="title">
                                            <%=("Credit Assessment Results" & ShowDecisionID(iCurDecisionRunID))%></span>
                                        <span class="collapse-handle"><a href="#">Hide <em>-</em></a></span></h2>
                                    <div class="content open">
                                        <table border="0" width="100%" cellpadding="3" cellspacing="3" class="linktable">
                                            <% 	
		dim rsAppCreditAssessmentPGResult
		dim eachBorrowerID
		iDecisionRunID = 0
                                            %>
                                            <!--[BL: Check multiple borrowers] begin-->
                                            <%
		call appGetBorrowerNames (0, iAppID, rx)
						
		If Not rx.eof then 
		End If				

		While Not rx.EOF
                                            %>
                                            <tr>
                                                <td>
                                                    <b><font color="black">
                                                        <% '[3531] - START
                                                        if rx("bCorporate") = 0 then
                                                        %>
                                                            <%=rx("cFirstName")%>,<%=rx("cSurname")%>
                                                        <%
                                                        else
                                                        %>
                                                            <%=rx("cCompanyName") %>
                                                        <%
                                                        end if '[3531] - END %>
                                                        </font></b>
                                                </td>
                                                <% 	
			dim rsAppCreditAssessmentPGResultDetail
			set rsAppCreditAssessmentPGResultDetail = GetAppCreditAssessmentPGResultDetail(iAppID, iDecisionRunID,rx("iBorrowerID"))
			   
			'[5] start
			if rsAppCreditAssessmentPGResultDetail.EOF then
                                                %>
                                                <td style="color: Red">
                                                    <b>There is no Credit Assessment Result available for this person</b>
                                                </td>
                                            </tr>
                                            <%
            '[5] end
			else
                                            %>
                                            <tr>
                                                <th style="width: 35%">
                                                    <b>Rule</b>
                                                </th>
                                                <th style="width: 15%">
                                                    <b>Application Value</b>
                                                </th>
                                                <th style="width: 10%">
                                                    <b>Rule Value</b>
                                                </th>
                                                <th>
                                                    <b>Result</b>
                                                </th>
                                                <th>
                                                    <b>Discretionary Override Authority</b>
                                                </th>
                                                <!--[4]-->
                                            </tr>
                                            <%
			end if
                
            while not rsAppCreditAssessmentPGResultDetail.EOF
				bDLASetting = GetDLASetting(rsAppCreditAssessmentPGResultDetail("cRuleCode"), iAppID, icurDecisionRunID)   '[1083]
				
                if mycolor = mycolor1 then
	                mycolor = mycolor2
                else
	                mycolor = mycolor1
                end if	

                if rsAppCreditAssessmentPGResultDetail("cResult") = "Pass" then
                    cResultColor = "Green"
                else
                    cResultColor = "Red"
                end if
					    
                                            %>
                                            <tr valign="top">
                                                <td style="width: 25%">
                                                    <%=rsAppCreditAssessmentPGResultDetail("cRule") %>
                                                </td>
                                                <%if rsAppCreditAssessmentPGResultDetail("iAppValueFormat") = 1 then %>
                                                <td style="width: 20%">
                                                    <%=LMSAmount(rsAppCreditAssessmentPGResultDetail("cAppValue")) %>
                                                </td>
                                                <%else %>
                                                <td style="width: 20%">
                                                    <%=rsAppCreditAssessmentPGResultDetail("cAppValue") %>
                                                </td>
                                                <%end if %>
                                                <%if rsAppCreditAssessmentPGResultDetail("iRuleValueFormat") = 1 then %>
                                                <td style="width: 25%">
                                                    <%=LMSAmount(rsAppCreditAssessmentPGResultDetail("cRuleValue")) %>
                                                </td>
                                                <%else %>
                                                <td style="width: 25%">
                                                    <%=rsAppCreditAssessmentPGResultDetail("cRuleValue") %>
                                                </td>
                                                <%end if %>
                                                <td style="color: <%=cResultColor %>; font-weight: bold; width=10%">
                                                    <%=rsAppCreditAssessmentPGResultDetail("cResult") %>
                                                </td>
                                                <td style="width: 20%">
                                                        <%If rsAppCreditAssessmentPGResultDetail("cResult") = "Fail" and  bDLASetting = 1 Then  %>
                                                       <a href="Javascript:addOverrideNote('<%=rsAppCreditAssessmentPGResultDetail( "iSN" )%>', '0');"><b>Pass</b></a>       <%'[2911.4] %>
                                                        <%Else  
                                                            Response.Write  GetOverrideHistory(rsAppCreditAssessmentPGResultDetail("iSN"), 0)
                                                        End If %>  <%'[2911.4] %>
                                                </td>
                                            </tr>
                                            <%
	             rsAppCreditAssessmentPGResultDetail.MoveNext
	        wend
			rsAppCreditAssessmentPGResultDetail.Close
			set rsAppCreditAssessmentPGResultDetail = Nothing
			rx.MoveNext 					
        Wend 
                                            %>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <!--[BL: Credit Assessment End -->
                            <!--[509] begin-->
                            <!--Program guidline rule test result-->
                            <div class="block collapsable block-shadowed">
                                <div class="inner">
                                    <h2 class="bg-bluelight">
                                        <span class="title">
                                            <%=("Program Guideline Results" & ShowDecisionID(iCurDecisionRunID))%></span>
                                        <span class="collapse-handle"><a href="#">Hide <em>-</em></a></span></h2>
                                    <div class="content open">
                                        <table class="cmn-table">
                                            <tr>
                                                <th style="width: 20%">
                                                    Rule
                                                </th>
                                                <th style="width: 10%">
                                                    Application Value
                                                </th>
                                                <th style="width: 30%">
                                                    Rule Value
                                                </th>
                                                <th style="width: 5%">
                                                    Result
                                                </th>
                                                <th style="width: 35%">
                                                    Discretionary Override Authority
                                                </th>
                                                <!--[4]-->
                                            </tr>
                                            <% 	
		dim rsAppCreditDecisionPGResultDetail
		dim cResultColor

		if not rsAppCreditDecisionPGResult.EOF then
			set rsAppCreditDecisionPGResultDetail = GetAppCreditDecisionPGResultDetail(iAppID, iDecisionRunID)

		    while not rsAppCreditDecisionPGResultDetail.EOF
			    if mycolor = mycolor1 then
				    mycolor = mycolor2
			    else
				    mycolor = mycolor1
			    end if	

			    if rsAppCreditDecisionPGResultDetail("cResult") = "Pass" then
			        cResultColor = "Green"
			    else
			        cResultColor = "Red"
			    end if
					    
			    bDLASetting = GetDLASetting(rsAppCreditDecisionPGResultDetail("cRuleCode"), iAppID, icurDecisionRunID) '[4][1083]%>
                                            <tr valign="top">
                                                <td style="width: 25%">
                                                    <%=rsAppCreditDecisionPGResultDetail("cRule") %>
                                                </td>
                                                <%if rsAppCreditDecisionPGResultDetail("iAppValueFormat") = 1 then %>
                                                <td style="width: 20%">
                                                    <%=LMSAmount(rsAppCreditDecisionPGResultDetail("cAppValue")) %>
                                                </td>
                                                <%else %>
                                                <td style="width: 20%">
                                                    <%=rsAppCreditDecisionPGResultDetail("cAppValue") %>
                                                </td>
                                                <%end if %>
                                                <%if rsAppCreditDecisionPGResultDetail("iRuleValueFormat") = 1 then %>
                                                <td style="width: 25%">
                                                    <%=LMSAmount(rsAppCreditDecisionPGResultDetail("cRuleValue")) %>
                                                </td>
                                                <%else %>
                                                <td style="width: 25%">
                                                    <%=rsAppCreditDecisionPGResultDetail("cRuleValue") %>
                                                </td>
                                                <%end if %>
                                                <td style="color: <%=cResultColor %>; font-weight: bold; width=10">
                                                    <%=rsAppCreditDecisionPGResultDetail("cResult") %>
                                                </td>
                                                <td style="width: 20%">
                                                    <%If rsAppCreditDecisionPGResultDetail("cResult") = "Fail" and  bDLASetting = 1 Then  %>
                                                       <a href="Javascript:addOverrideNote('<%=rsAppCreditDecisionPGResultDetail( "iSN" )%>', '0');"><b>Pass</b></a>      
                                                    <%Else  
                                                        Response.Write GetOverrideHistory(rsAppCreditDecisionPGResultDetail("iSN"), 0) 
                                                    End If %> 
                                                </td>
                                                <!--[4]-->

                                            </tr>
                                            <%
		        rsAppCreditDecisionPGResultDetail.MoveNext
		        wend
		        rsAppCreditDecisionPGResultDetail.Close
		        set rsAppCreditDecisionPGResultDetail = Nothing
		        rsAppCreditDecisionPGResult.Close
		        set rsAppCreditDecisionPGResult = Nothing
			end if
                                            %>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <!--Serviceability rule test result-->
                            <div class="block collapsable block-shadowed">
                                <div class="inner">
                                    <h2 class="bg-bluelight">
                                        <span class="title">
                                            <%=("Serviceability Results" & ShowDecisionID(iCurDecisionRunID) )%></span>
                                        <span class="collapse-handle"><a href="#">Hide <em>-</em></a></span></h2>
                                    <div class="content open">
                                        <table class="cmn-table">
                                            <tr>
                                                <th style="width: 35%">
                                                    Rule
                                                </th>
                                                <th style="width: 15%">
                                                    Application Value
                                                </th>
                                                <th style="width: 10%">
                                                    Rule Value
                                                </th>
                                                <th>
                                                    Result
                                                </th>
                                                <th>
                                                    Discretionary Override Authority
                                                </th>
                                                <!--[4]-->
                                            </tr>
                                            <%
		if bHasResult then
		    dim rsAppCreditDecisionSEResultDetail
		    set rsAppCreditDecisionSEResultDetail = GetAppCreditDecisionSEResultDetail(iAppID,icurDecisionRunID )'iDecisionRunID

	        while not rsAppCreditDecisionSEResultDetail.EOF
		        if mycolor = mycolor1 then
			        mycolor = mycolor2
		        else
			        mycolor = mycolor1
		        end if	

		        if rsAppCreditDecisionSEResultDetail("cResult") = "Pass" then
		            cResultColor = "Green"
		        else
		            cResultColor = "Red"
		        end if
    					    
				bDLASetting = GetDLASetting(rsAppCreditDecisionSEResultDetail("cRuleCode"), iAppID, icurDecisionRunID) '[4][1083]%>
                                            <tr valign="top">
                                                <td width="30%">
                                                    <%=rsAppCreditDecisionSEResultDetail("cRule") %>
                                                </td>
                                                <%if rsAppCreditDecisionSEResultDetail("iAppValueFormat") = 1 then %>
                                                <td style="width: 15%">
                                                    <%=LMSAmount(rsAppCreditDecisionSEResultDetail("cAppValue")) %>
                                                </td>
                                                <%else %>
                                                <td style="width: 15%">
                                                    <%=rsAppCreditDecisionSEResultDetail("cAppValue") %>
                                                </td>
                                                <%end if %>
                                                <%if rsAppCreditDecisionSEResultDetail("iRuleValueFormat") = 1 then %>
                                                <td style="width: 25%">
                                                    <%=LMSAmount(rsAppCreditDecisionSEResultDetail("cRuleValue")) %>
                                                </td>
                                                <%else %>
                                                <td style="width: 25%">
                                                    <%=rsAppCreditDecisionSEResultDetail("cRuleValue") %>
                                                </td>
                                                <%end if %>
                                                <td style="color: <%=cResultColor %>; font-weight: bold; width=10%">
                                                    <%=rsAppCreditDecisionSEResultDetail("cResult") %>
                                                </td>
                                                <td width="20%">

                                                             <%If rsAppCreditDecisionSEResultDetail("cResult") = "Fail" and  bDLASetting = 1 Then  %>
                                                                <a href="Javascript:addOverrideNote('<%=rsAppCreditDecisionSEResultDetail( "iSN" )%>', '0');"><b>Pass</b></a>        
                                                            <%Else  
				                                                Response.Write  GetOverrideHistory(rsAppCreditDecisionSEResultDetail("iSN"), 0)             '[2911.4]
				                                              End If 
                                                            %>

                                                </td>
                                                <!--[4]-->
                                            </tr>
                                            <%
	            rsAppCreditDecisionSEResultDetail.MoveNext
	        wend
			
			rsAppCreditDecisionSEResultDetail.Close
			set rsAppCreditDecisionSEResultDetail = Nothing
		end if
                                            %>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <!--[509] end-->
                            <!--[1083] - START -->
                            <!--Borrower Income Result -->
                            <div class="block collapsable block-shadowed">
                                <div class="inner">
                                    <h2 class="bg-bluelight">
                                        <span class="title">
                                            <%=("Borrower Income Results - Yearly" & ShowDecisionID(iCurDecisionRunID) )%></span>
                                        <span class="collapse-handle"><a href="#">Hide <em>-</em></a></span></h2>
                                    <div class="content open">
                                        <table class="cmn-table">
                                            <tr>
                                                <th style="width: 20%">
                                                     Borrower 
                                                </th>
                                                <th style="width: 10%">
                                                     Total Net Borrower Income After Tax 
                                                </th>
                                                <th style="width: 10%">
                                                     Total Borrower Other Income 
                                                </th>
                                                <th style="width: 10%">
                                                    Total Borrower Tax 
                                                </th>
                                                <th style="width: 10%">
                                                     Total Borrower Gearing Benefit Amount
                                                </th>
                                                <th style="width: 10%">
                                                    Total Borrower Outgoing 
                                                </th>
                                                <!-- [3531] - START --> 
                                                <th style="width: 10%">
                                                    Total Borrower Living Expenses 
                                                </th>
                                                <th style="width: 10%">
                                                    Total Borrower Living Expenses Client Advised
                                                </th>
                                                <th style="width: 10%">
                                                    HEM
                                                </th>
                                                 <!-- [3531] - END --> 
                                            </tr>
                                            <%
		if bHasResult then
		    dim rsAppCreditDecisionBorrowerIncomeResults
		    set rsAppCreditDecisionBorrowerIncomeResults = GetAppCreditDecisionBorrowerIncomeResult(icurDecisionRunID)

	        while not rsAppCreditDecisionBorrowerIncomeResults.EOF
		        if mycolor = mycolor1 then
			        mycolor = mycolor2
		        else
			        mycolor = mycolor1
		        end if	
                                            %>
                                            <tr valign="top">
                                                <td style="width: 20%">
                                                    <%=rsAppCreditDecisionBorrowerIncomeResults("BorrowerName") %>
                                                </td>
                                                <td style="width: 10%">
                                                    $<%=LMSAmount(rsAppCreditDecisionBorrowerIncomeResults("fTotalNetBorrowerIncomeAfterTax")) %>
                                                </td>
                                                <td style="width: 10%">
                                                    $<%=LMSAmount(rsAppCreditDecisionBorrowerIncomeResults("fTotalBorrowerOtherIncome")) %>
                                                </td>
                                                <td style="width: 10%">
                                                    $<%=LMSAmount(rsAppCreditDecisionBorrowerIncomeResults("fTotalBorrowerTax")) %>
                                                </td>
                                                <td style="width: 10%">
                                                    $<%=LMSAmount(rsAppCreditDecisionBorrowerIncomeResults("fTotalBorrowerGearingBenefitAmount")) %>
                                                </td>
                                                <td style="width: 10%">
                                                    $<%=LMSAmount(rsAppCreditDecisionBorrowerIncomeResults("fTotalBorrowerOutgoing")) %>
                                                </td>
                                                <td style="width: 10%">
                                                    $<%=LMSAmount(rsAppCreditDecisionBorrowerIncomeResults("fTotalBorrowerLivingExpenses")) %>
                                                </td>
                                                <!-- [3531] - START --> 						 
                                                <% if rsAppCreditDecisionBorrowerIncomeResults("fTotalBorrowerLivingExpensesClientAdvised") > rsAppCreditDecisionBorrowerIncomeResults("fTotalHem") then %>
                                                    <td style="color: Red; font-weight: bold; width=10%">
                                                        *$<%=LMSAmount(rsAppCreditDecisionBorrowerIncomeResults("fTotalBorrowerLivingExpensesClientAdvised")) %>
                                                    </td>
                                                <% else %>
                                                    <td style="width=10%">
                                                        $<%=LMSAmount(rsAppCreditDecisionBorrowerIncomeResults("fTotalBorrowerLivingExpensesClientAdvised")) %>
                                                    </td>
                                                <% end if %>

                                                <% if rsAppCreditDecisionBorrowerIncomeResults("fTotalHem") > rsAppCreditDecisionBorrowerIncomeResults("fTotalBorrowerLivingExpensesClientAdvised") then %>
                                                     <td style="color: Red; font-weight: bold; width=10%">
                                                        *$<%=LMSAmount(rsAppCreditDecisionBorrowerIncomeResults("fTotalHem")) %>
                                                    </td>
                                                <% else  %>
                                                    <td style="width: 10%">
                                                        $<%=LMSAmount(rsAppCreditDecisionBorrowerIncomeResults("fTotalHem")) %>
                                                    </td>
                                                <% end if %>

                                                <!-- [3531] - END --> 
                                            </tr>
                                            <%
	            rsAppCreditDecisionBorrowerIncomeResults.MoveNext
	        wend
			
			rsAppCreditDecisionBorrowerIncomeResults.Close
			set rsAppCreditDecisionBorrowerIncomeResults = Nothing
		end if
                                            %>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <!--[1083] - END -->
                            <form id="frmServiceabilityCal" name="frmServiceabilityCal" method="post" action="app_serviceability_calc.asp"
                            onsubmit="SubmitMe()">
                            <input type="hidden" name="iAppID" id="iAppID" value="<%=iAppID%>" />
                            <input type="hidden" name="action" id="action" value="<%=action%>" />
                            <input type="hidden" name="iSelResult" id="iSelResult" value="<%=iSelResult%>" />
                            <input type="hidden" name="iSelRule" id="iSelRule" value="<%=iSelRule%>" />
                            <% if granted = "" and Not bHideCDPerformButton = True and not bLockApplication  Then '[1083]%>
                            <!--[1069 Start -->
                            <div id="footer">
                                <div class="application-footer block">
                                    <div class="inner">
                                        <div class="btn-group-b">
                                            <input class="btn btn-yellow" type="submit" value="Run Decision" onclick="SubmitMe();" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <% End If %>
                            <%'[509] [4]%>
                            <!--[1069 End -->
                            <!--<td align="center"><% if granted = "" and Not bHideCDPerformButton = True Then %><strong>Perform Credit Decision</strong><% End If %></td>	-->
                            <!--[509] [4]-->
                            </form>
                            <script type="text/javascript" language="javascript">
                                if (!window.createPopup) {
                                    window.createPopup = function () {
                                        var popup = document.createElement("iframe"), //must be iframe because existing functions are being called like parent.func()
                                            isShown = false, popupClicked = false;
                                        popup.src = "about:blank";
                                        popup.style.position = "absolute";
                                        popup.style.border = "0px";
                                        popup.style.display = "none";
                                        popup.addEventListener("load", function (e) {
                                            popup.document = (popup.contentWindow || popup.contentDocument);//this will allow us to set innerHTML in the old fashion.
                                            if (popup.document.document) popup.document = popup.document.document;
                                        });
                                        document.body.appendChild(popup);
                                        var hidepopup = function (event) {
                                            if (isShown)
                                                setTimeout(function () {
                                                    if (!popupClicked) {
                                                        popup.hide();
                                                    }
                                                    popupClicked = false;
                                                }, 150);//timeout will allow the click event to trigger inside the frame before closing.
                                        }

                                        popup.show = function (x, y, w, h, pElement) {
                                            if (typeof (x) !== 'undefined') {
                                                var elPos = [0, 0];
                                                if (pElement) elPos = findPos(pElement);//maybe validate that this is a DOM node instead of just falsy
                                                elPos[0] += y, elPos[1] += x;

                                                if (isNaN(w)) w = popup.document.scrollWidth;
                                                if (isNaN(h)) h = popup.document.scrollHeight;
                                                if (elPos[0] + w > document.body.clientWidth) elPos[0] = document.body.clientWidth - w - 5;
                                                if (elPos[1] + h > document.body.clientHeight) elPos[1] = document.body.clientHeight - h - 5;

                                                popup.style.left = elPos[0] + "px";
                                                popup.style.top = elPos[1] + "px";
                                                popup.style.width = w + "px";
                                                popup.style.height = h + "px";
                                            }
                                            popup.style.display = "block";
                                            isShown = true;
                                        }

                                        popup.hide = function () {
                                            isShown = false;
                                            popup.style.display = "none";
                                        }

                                        window.addEventListener('click', hidepopup, true);
                                        window.addEventListener('blur', hidepopup, true);
                                        return popup;
                                    }
                                }
                                function findPos(obj, foundScrollLeft, foundScrollTop) {
                                    var curleft = 0;
                                    var curtop = 0;
                                    if (obj.offsetLeft) curleft += parseInt(obj.offsetLeft);
                                    if (obj.offsetTop) curtop += parseInt(obj.offsetTop);
                                    if (obj.scrollTop && obj.scrollTop > 0) {
                                        curtop -= parseInt(obj.scrollTop);
                                        foundScrollTop = true;
                                    }
                                    if (obj.scrollLeft && obj.scrollLeft > 0) {
                                        curleft -= parseInt(obj.scrollLeft);
                                        foundScrollLeft = true;
                                    }
                                    if (obj.offsetParent) {
                                        var pos = findPos(obj.offsetParent, foundScrollLeft, foundScrollTop);
                                        curleft += pos[0];
                                        curtop += pos[1];
                                    } else if (obj.ownerDocument) {
                                        var thewindow = obj.ownerDocument.defaultView;
                                        if (!thewindow && obj.ownerDocument.parentWindow)
                                            thewindow = obj.ownerDocument.parentWindow;
                                        if (thewindow) {
                                            if (!foundScrollTop && thewindow.scrollY && thewindow.scrollY > 0) curtop -= parseInt(thewindow.scrollY);
                                            if (!foundScrollLeft && thewindow.scrollX && thewindow.scrollX > 0) curleft -= parseInt(thewindow.scrollX);
                                            if (thewindow.frameElement) {
                                                var pos = findPos(thewindow.frameElement);
                                                curleft += pos[0];
                                                curtop += pos[1];
                                            }
                                        }
                                    }
                                    return [curleft, curtop];
                                }
                                //[2911.4] - START


                                var vReturn = null;
                                function parent_disable() {
                                    if (vReturn && !vReturn.closed)
                                        vReturn.focus();
                                }

                                //[2911.4] - END
                                var iAllProductHasLoanPurpose = "<%=iAllProductHasLoanPurpose %>"; //[670.21]
                                var frm = document.frmServiceabilityCal
                                var oPopup = window.createPopup();

                                function updateResult(obj) {

                                    var msg = confirm("Are you going to change the serviceability result?")
                                    if (msg) {
                                        frm.action.value = "ChangeResult"
                                        frm.iSelResult.value = obj.value;
                                        frm.iSelRule.value = obj.tag;
                                        frm.submit();
                                    }
                                    else {
                                        return;
                                    }
                                }

                                function SubmitMe() {
                                    frm.action.value = "updateexec";
                                }

                                function HideToolTip() {
                                    if (oPopup) {
                                        oPopup.hide();
                                    }
                                }

                                //[670.21]
                                function validate() {
                                    if (iAllProductHasLoanPurpose == 0) {
                                        alert('Approval can not be completed. A Loan Purpose has not been assigned to a loan record.');
                                        return false;
                                    }

                                }

                                //[2911.1 - START]
                                function addOverrideNote(iID, iType) {

                                    var height = 250;
                                    var width = 700;
                                    var left = (screen.width / 2) - (width / 2);
                                    var top = (screen.height / 2) - (height / 2);

                                    if (vReturn && !vReturn.closed) {
                                        vReturn.focus();
                                    }
                                    else {
                                        var s = 'app_Serviceability_Calc_Override_Note.asp?action=add&iDecisionID=' + iID + '&iType=' + iType;

                                        vReturn = window.open(s, 'Add Override Note', 'resizable=no,status=no,scrollbars=no,height=' + height + ',width=' + width + 'top=' + top + ', left=' + left + 'screenY=' + top + ', screenX=' + left);
                                    }
                                }
                                function getOverrideNote(vReturn) {
                                    if (vReturn != null) {

                                        if (vReturn[2] == 0) {
                                            document.location.href = "app_serviceability_calc.asp?action=ChangeResult&iAppID=" + <%=iAppID %> + "&iDecisionID=" + vReturn[1] + "&cOverrideNote=" + vReturn[0];
                                        }
                                        else {
                                            document.location.href = "app_serviceability_calc.asp?action=ChangeExternalResult&iAppID=" + <%=iAppID %> + "&iDecisionID=" + vReturn[1] + "&cOverrideNote=" + vReturn[0];
                                        }
                                    }
                                }
                                //[2911.1 - END]

                                //[2911.4 - START]
                                function SelectTitleReference() {
                                    var height = 150;
                                    var width = 1000;
                                    var left = (screen.width / 2) - (width / 2);
                                    var top = (screen.height / 2) - (height / 2);

                                    if (vReturn && !vReturn.closed) {
                                        vReturn.focus();
                                    }
                                    else {
                                        var s = 'app_Serviceability_Title_Reference_Selection.asp?action=add&iAppID=' + <%=iAppID%>;
                                        vReturn = window.open(s, 'Title Reference Selection', 'resizable=no,status=no,scrollbars=no,height=' + height + ',width=' + width + 'top=' + top + ', left=' + left + 'screenY=' + top + ', screenX=' + left);
                                    }
                                }

                                function refreshScreen() {
                                    document.location.href = 'app_serviceability_calc.asp?iAppID=' + <%=iAppID %>;
                                }
                                //[2911.4 - End]


                            </script>
                            
                        </div>
                        <!-- #include file="app_incNav.asp" -->
                    </div>
                    
                </div>
                
            </div>
           <!-- #include file="incFooter.asp" -->
            <br /><br /><br /><br />
        </div>
         
        <!--[1196] end-->
    </div>
</body>
</html>
