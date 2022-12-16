<%@  language="VBScript" %>
<% 'SERVER SCRIPT INCLUSIONS (PLEASE REMOVE THOSE INCLUDES THAT ARE NOT NEEDED) %>
<!-- #include file="incServerUtils.asp" -->
<!-- #include file="incConstants.asp" -->
<!-- #include file="func_ado_GlobalSettings_GetValue.asp" -->
<!-- #include file="incADO.asp" -->
<%
Response.Buffer = True

'[1] Chad Wu (19-12-2001)changed incFooter.asp and LMS On-Line to LoanWorks
'[2] Chad Wu (19-12-2001) add incConstants.asp into page
'[3] Chad 19 December 2001  getCompanyTradingAs() Copied from inctopbanner.asp
'[4] Chad (19-12-2001)introduce company name and style sheet into this page
'[5] Wayne Macartney 10 Feb 2002
'[6] HENRY HUANG 08/03/2006 Introduce asp.net 2 logon page for LWCRM
' HTML and page setup improved
' Submit button supports enter key
'[7][790] EY  03/09/09 Default.asp resumits the page twice on login button click
'[8][974.15] Wayne Macartney 19 October 2009 new look and feel
'[9][988.8] EY add iUserEntity so that note can be create/update by user/introducer/3G etc
'[10][1024] UV:- Add a pop up page to submit a requst to Pisces Produc Web Service. By clicking on YES in popu up page
		' it submits reqeust to pisces web service and add/update all product/funders in Loanworks. by clicking on NO
		' it will redirect to User's home page.
'[11][1075]UV:-17/06/2011 Pisces - Pass LMS constant path to Pisces product page in order to fix the issue.
'[12][1044]EY 22/06/2011 add try and catch to .focus to avoid javascript error 
'[13][2367] JPN  10/03/2016 RAMS / AUSSIE - Added the LDAP Authentication for Login.
			' - Added Functions IsLDAPAuthenticated & LoadLDAPSettings, Also added Redirection when LDAP login fails. 
'[14][2548]JPN 02/04/2018 Internal - Look and Feel
'[15][2548] CJL 15/05/2018  Loanworks V10R9 rebooted - FEATURE MERGE
'           - Apply Build [1971]  Arab Bank - User Password Enhancement
'               - First time log-in users will be redirected to the 'Change Password Screen', forcing them to change the System Generated password. 
'               - Added CheckUserLoginAccountLockoutLimit(), DeleteUserLoginFailHistoryLog(), InsertUserLoginFailHistoryLog(), IsPWSystemGenerated()


Dim action
Dim cUserID
Dim cPassword
Dim iUserID
Dim oConn
Dim sHomePage
dim cCompanyName

Dim dLastUpdate
Dim iFrequency
Dim iExpiryDay
Dim rsGlobalSettings_Active '[1024]
Dim rsGlobalSettings_Interval'[1024]
Dim iPiscesProductCatgalogActive '[1024]
Dim iPicesProductRequestInterval '[1024]
Dim UserID '[1024]
Dim bShowPices, bProceed' [1024]
Dim iProductCataglogRequestInterval'[1024]
Dim iCanRequestProductCatalogUpdate, iGlobalSettingsValue'[1024]
dim cDomain,iLDAPAuthenticationActive,rsGlobalSettings_LDAPActive,bUserAuthenticated,cErrorDescription,cLDAPServerName,cLDAPPort,cADDomain,isUserGroupAllowed,UserADGroups,ADAllowedGroupList '[2367]

Dim WARNING_EXPIRY_DAYS
WARNING_EXPIRY_DAYS=5

Dim cCompanyLogo 

UserID = -2	'[1024]
bShowPices = 0	'[1024]

Call Main()
'==============================================================

'==============================================================
Sub Main()
	Call ReadInputs() 'from the incoming form if any
	Call OpenDatabaseConnecttion()
	Call GetCompanyInfo()	

    Call GlobalSettingGetValue(1, "LDAP_AUTHENTICATION", rsGlobalSettings_LDAPActive)

    If not rsGlobalSettings_LDAPActive.eof then
	    iLDAPAuthenticationActive = rsGlobalSettings_LDAPActive("iValue")
    End IF

	if action="submit" then
	
	    if iLDAPAuthenticationActive = 1 then
            bUserAuthenticated = IsLDAPAuthenticated()
	    else
            
            if CheckUserAccount() then
                bUserAuthenticated = True
                '[1971] - START
                if IsPWSystemGenerated() then              
                    bUserAuthenticated = False
                    oConn.Close ()
				    Set oConn=nothing
				    Response.Redirect "userchangepassword.asp"
                elseif CheckUserLoginAccountLockoutLimit() then
                    bUserAuthenticated = False
                    'Check if log-in user account lockout            
                    oConn.Close ()
				    Set oConn=nothing
                    Response.Redirect "WarningAccountLockout.asp"
                End If
                '[1971] - END
            End If
        end if
	
		if bUserAuthenticated then

			Call UpdateUserSession() ' Update tblUserSession and tblUserSessionLog
            Call DeleteUserLoginFailHistoryLog()            '[1971]

			sHomePage= getUserHomePage(iUserID)


			if iExpiryDay<=WARNING_EXPIRY_DAYS then

				if iExpiryDay>0 then
					'[5]
					'Close Database Connecttion
					oConn.Close ()
					Set oConn=nothing
					Response.Redirect "WarningChangePassword.asp?Homepage=" & sHomePage & "&iExpiryDay=" & iExpiryDay
				else
					'Close Database Connecttion
					'[5]
					oConn.Close ()
					Set oConn=nothing
					Response.Redirect "WarningPasswordExpiried.asp"
				end if

			else
				  
			    '[1024]start
				Call GlobalSettingGetValue(1, "PISCES_PRODCUT_CATALOG_ACTIVATE", rsGlobalSettings_Active)
				If not rsGlobalSettings_Active.eof then
					iPiscesProductCatgalogActive = rsGlobalSettings_Active("iValue")
				End IF
				Call GlobalSettingGetValue(1, "PISCES_PRODUCT_CATALOG_REQUEST_INTERVAL", rsGlobalSettings_Interval)
				If not rsGlobalSettings_Interval.eof then
					iGlobalSettingsValue = rsGlobalSettings_Interval("iValue")
					iPicesProductRequestInterval = GetLookupItemname (iGlobalSettingsValue)
				End IF
				iCanRequestProductCatalogUpdate = GetTaskPermission(iUserID, SEC_MAN_REQ_PROD_CATALOG)
				iProductCataglogRequestInterval = GetProductRequestTimeInterval()

				If IsNull(iPicesProductRequestInterval) or iPicesProductRequestInterval = "" Then iPicesProductRequestInterval= 0
				dim bRoleShowPices
				if (cint(iProductCataglogRequestInterval) >= cint(iPicesProductRequestInterval) or iProductCataglogRequestInterval = -1 )_
					    and iPiscesProductCatgalogActive = 1 and iCanRequestProductCatalogUpdate = 1 Then
					bRoleShowPices = 1
				end if
				    
				if bRoleShowPices = 1 and bProceed = 0 then
					bShowPices = 1
				else				
					response.Redirect("loanCrm/SetSecurity.aspx?backpage=" + _
						    sHomePage + "&UserId=" + cstr(iUserID) + "&TOut=" + cstr(Session.Timeout )	+ _
						    "&AppPath=" + cstr(Application("Loanworks_constantsPath")) + _
						    "&iEntityID=" + cstr(Session("iEntityID")))
				end if
				'[5]
				oConn.Close ()
				Set oConn=nothing
				'[1024]end
			end if
            
        else

            If iLDAPAuthenticationActive <> 1 Then

                '[1971] - START
                if len(trim(cUserID)) <> 0  then
                    Call InsertUserLoginFailHistoryLog()
                end if

                If CheckUserLoginAccountLockoutLimit() then
                    Response.Redirect "WarningAccountLockout.asp"
                else               
                '[1971] - END
			        Response.Redirect "Warning.asp"            
                end if                                              '[1971]

			    'Close Database Connecttion
			    '[5]
			    oConn.Close ()
			    Set oConn=nothing
            Else  
                if cErrorDescription <> "" then
                    Response.Redirect "ADAuthenticationWarning.asp?authenticationError=" + cErrorDescription
                else
                    Response.Redirect "Warning.asp"
                end if			    
            end if			    
		end if
	else

	    'Close Database Connecttion
	    '[5]
	    cCompanyName=getCompanyTradingAs()
	    'oConn.Close ()
	    'Set oConn=nothing
	end if
End Sub
'====================================================================================
'
'===================================================================================
Sub ReadInputs()

	action=Request.QueryString ("action")
    cDomain=Request.Form("hcDomain")
	cUserID=Request.Form ("hcUserID")
	cPassword=Request.Form ("hcPassword")
	bProceed = Request.Form ("bProceed")	'[1024]
	if bProceed = "" then bProceed = 0		'[1024]
    cErrorDescription = Request.Form("hcErrorDescription")

    

End Sub
'============================================================
'	[1] HH added AppPath
'==============================================================
Sub OpenDatabaseConnecttion()
	dim sConn
	Dim oSecurity

	Set oSecurity = Server.CreateObject("lmsSecurity.Constants")
	sConn  = oSecurity.GetValue (UserID, "CONNECTION_STRING",Application("Loanworks_constantsPath"))

	Set oConn = Server.CreateObject("ADODB.Connection")

	oConn.Open sConn

	'Cleaning
	set oSecurity = nothing

End Sub

'===================================================================
''	 Company Trading As should read from database tblCompany
'===================================================================
Function getCompanyTradingAs()
	Dim cSqlGet
	Dim rsGet

	getCompanyTradingAs=""

	cSqlGet="SELECT cTradingAs from tblCompany Where iCompanyID=1"
	SET rsGet=oConn.Execute(cSqlGet)

	if NOT rsGet.EOF then
		getCompanyTradingAs=rsGet("cTradingAs")
	else
		getCompanyTradingAs=""
	end if

	Set rsGet=nothing
End Function
'=========================================================================
'
'===========================================================================

Function CheckUserAccount()
	Dim cSql
	Dim rs

	cSql="SP_UserAccountCheck " & "'" & Replace(cUserID, "'", "''") & "'" & ", " & "'" & Replace(cPassword, "'", "''") & "'"
		
	Set rs=oConn.Execute (cSql)

	if rs.EOF then
		CheckUserAccount=false
	else
		CheckUserAccount=true
		iUserID=rs("iUserID")
		Session("NTUserID")=rs("cNTUserID")
		Session("Password")=rs("cPassword")
		Session("UserSessionID")=Session.SessionID
		Session("iUserID")=rs("iUserID")
		dLastUpdate=rs("dLastUpdate")
		iFrequency=rs("iFrequency")
		'[988.8]
		Session("iEntityID") = rs("iEntityID")
		iExpiryDay=iFrequency-DateDiff("d", CDate(dLastUpdate), Date())

	end if

End Function

'[1971] - START
Function CheckUserLoginAccountLockoutLimit()

    dim cmd
	set cmd = Server.CreateObject( "ADODB.Command" )
	cmd.CommandType = adCmdStoredProc
	cmd.Parameters.Append cmd.CreateParameter( "@cNTUserID", adVarchar, adParamInput, 20, cUserID)
    cmd.Parameters.Append cmd.CreateParameter( "@bLogSystemHistory", adBoolean, adParamInput,, 1)
	cmd.Parameters.Append cmd.CreateParameter( "@bAccountLock", adBoolean, adParamOutput )
	cmd.CommandText = "USP_UserLoginAccountLockoutLimit"
	cmd.ActiveConnection = oConn
	cmd.Execute
	CheckUserLoginAccountLockoutLimit = cmd.Parameters( "@bAccountLock" ).Value

End Function
'[1971] - END


'[1024]start
Function GetProductRequestTimeInterval()
	Dim cSqlGet
	Dim rsGet

	GetProductRequestTimeInterval=0

	cSqlGet="SELECT Top 1 DATEDIFF(Hour,dDateTime,GETDATE()) as TimeInterval from tblProductCatalog"
	SET rsGet=oConn.Execute(cSqlGet)

	if NOT rsGet.EOF then
		GetProductRequestTimeInterval=rsGet("TimeInterval")
	else
		GetProductRequestTimeInterval=-1
	end if

	Set rsGet=nothing
End Function

Function GetLookupItemName(iGlobalSettingsValue)
	Dim cSqlGet
	Dim rsGet

	GetLookupItemName=""

	cSqlGet= "SELECT cLookupItemName FROM tblLookupItems WHERE iLookUpCatID = 1083 AND iLookupItemID = " & iGlobalSettingsValue
	SET rsGet=oConn.Execute(cSqlGet)

	if NOT rsGet.EOF then
		GetLookupItemName=rsGet("cLookupItemName")
	else
		GetLookupItemName=""
	end if

	Set rsGet=nothing
End Function

Private Function GetTaskPermission(iUserID, Token)
	dim iAppEnterOwnerID
	dim objSecurity
	Set objSecurity = server.CreateObject("lmsSecurity.Profiles")
	GetTaskPermission = objSecurity.GetPermissionByKey(iUserID, Token,Application("Loanworks_constantsPath"))

	if Token=SEC_APP_APPLICATION_CAN_UPDATE and GetTaskPermission<>1 then
		iAppEnterOwnerID=Session("AppEnterOwnerID")
		if iAppEnterOwnerID=iUserID then
			GetTaskPermission=1
		end if
	end if

	Set objSecurity = nothing
end Function
'[1024]end

'=====================================================
''[3]
'Jack cheng 23/07/2001

'============================================================
Function UpdateUserSession()
	Dim cSql
	cSql="SP_UserSessionUpdate " & Session.SessionID & ", " & iUserID
	oConn.Execute (cSql)

End Function

'[1971] - START
Function IsPWSystemGenerated() 
	Dim cSqlGet
	Dim rsGet

	cSqlGet="SELECT dbo.fn_IsPWSystemGenerated(" & iUserID & ") AS IsPWSystemGenerated"    
	SET rsGet=oConn.Execute(cSqlGet)

    IsPWSystemGenerated = False

	if not (rsGet.BOF and rsGet.EOF) then
		if rsGet("IsPWSystemGenerated")	= 1 then
            IsPWSystemGenerated = True
        end if
    end if

	Set rsGet=nothing
End Function

Function DeleteUserLoginFailHistoryLog()
'Delete tblUserLoginFailHistoryLog for every successful user log in 
	Dim cSql
	cSql="USP_UserLoginFailHistoryLog_Delete " & iUserID
	oConn.Execute (cSql)

End Function

Function InsertUserLoginFailHistoryLog()
	Dim cSql
	'Response.write(cUserID)
	if cUserID <> "" then
		cSql="USP_UserLoginFailHistoryLog_Insert '" & cUserID & "'"
		oConn.Execute (cSql)
	end if

End Function

'[1971] - END

'=====================================================

'[2367] Added Function to Verifiy login from Active Directory

'=====================================================
Function IsLDAPAuthenticated()
    dim Authenticated
    Dim oLDAP
    Dim cSql
	Dim rs
    On Error Resume Next

    'Get Load the XMLSettings of LDAP
    Call LoadLDAPSetting 'Load LDAP XML Settings

    if cLDAPPort <> "" then 
        cLDAPPort = ":" & cLDAPPort 
    else 
        cLDAPPort = "" 
    end if

    Set oLDAP = Server.CreateObject("Loanworks.LDAPAuthentication")
    oLDAP.LDAPServerPath = cLDAPServerName & cLDAPPort
    oLDAP.LDAPConfigXMLPath = Server.MapPath("LDAPSettings.xml")
    IsLDAPAuthenticated = oLDAP.IsAuthenticated(cADDomain,cUserID,cPassword)

    'Gets the result of UserADGroup based on the LDAP authentication.
	UserADGroups = oLDAP.ADGroups
    
    'Gets the list of Allowed Group on the LDAPSettings.XML
    ADAllowedGroupList = oLDAP.getAllowedUserGroupViaXML()
    
    'Validate the result of UserADGroups against the AllowedADGrouplist
    isUserGroupAllowed = oLDAP.isADUserGroupValid(UserADGroups,ADAllowedGroupList)
    
    if IsLDAPAuthenticated = true then
        if  isUserGroupAllowed = true then
		
             cSql="SP_UserAccountCheck " & "'" & Replace(cUserID, "'", "''") & "'" & ", " & "'" & Replace(cPassword, "'", "''") & "'" & "," & " 1 "
	        Set rs=oConn.Execute (cSql)

	        if rs.EOF then
		        IsLDAPAuthenticated=false
	        else
		        IsLDAPAuthenticated=true
		        iUserID=rs("iUserID")
		        Session("NTUserID")=rs("cNTUserID")
		        Session("Password")=rs("cPassword")
		        Session("UserSessionID")=Session.SessionID
		        Session("iUserID")=rs("iUserID")
		        dLastUpdate=rs("dLastUpdate")
		        iFrequency=rs("iFrequency")
		        '[988.8]
		        Session("iEntityID") = rs("iEntityID")
		        iExpiryDay=iFrequency-DateDiff("d", CDate(dLastUpdate), Date())

	        end if
		else
			IsLDAPAuthenticated = false
        end if
		 
    end if

    if Err.number <> 0 then
        cErrorDescription = Err.Description
        IsLDAPAuthenticated = false
    end if

End Function

'=====================================================

'Load LDAP settings XML which is located in the Loanworks_Web folder

'=====================================================
Function LoadLDAPSetting()
'Load LDAP XMLSettings on the Server.MapPath
set objXMLDoc = server.CreateObject("MSXML2.DOMDocument")
objXMLDoc.async = false
objXMLDoc.Load Server.MapPath("LDAPSettings.xml")


        cLDAPServerName = objXMLDoc.documentElement.SelectSingleNode("LDAPServerName").text
        cLDAPPort = objXMLDoc.documentElement.SelectSingleNode("LDAPPortNumber").text
        cADDomain = objXMLDoc.documentElement.SelectSingleNode("ADDomain").text

End Function
'[2367]

private function GetCompanyInfo()
 
	Dim cSqlGet
	Dim rsGet

	cCompanyLogo=""

	cSqlGet="SELECT cLogoImagePath from tblCompany a INNER JOIN tblLogoImage b on b.id = a.iCompanyScreenLogo where iCompanyID = 1"
	SET rsGet=oConn.Execute(cSqlGet)

	if NOT rsGet.EOF then
		cCompanyLogo="Branding_image" & "\" & rsGet("cLogoImagePath")
	else
		cCompanyLogo=""
	end if

	Set rsGet=nothing

End function


%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html en="lang">
<head>
	<meta charset="UTF-8">
    <title>LoanWorks Login Screen</title>
    <!-- #include file="incStylesheet.asp" -->
</head>
<body class="login-page">
	<iframe style="display:none;" src="https://assessment.betterchoice.com.au/getlocalstorage" id="ifr"></iframe>
    <div class="login-box">
		<div class="login-logo">
			 <img height="120" width="360" src="<%=cCompanyLogo%>" alt="" />
		</div>
		<div class="login-box-body">
			<h3 class="login-box-msg"><%=cCompanyName%></h3>
			<p>Welcome, Please login.</p>
			<br/>
			<form method="post" action="DefaultWL.asp?action=submit" id="form1" name="form1" class="form-element">
                <input  type="hidden" id="hcDomain" name="hcDomain" value="<%=cDomain%>"/> <%'[2367] %>
				<input type="hidden" id="hcUserID" name="hcUserID"  value="<%=cUserID%>"/><%'[1024] %>
                <input type="hidden" id="hcPassword" name="hcPassword" value="<%=cPassword%>"/><%'[1024] %>
                <input  type="hidden" id="bProceed" name="bProceed" value="<%=bProceed%>"/>	<%'[1024] %>
                <input type="hidden" id="hcErrorDescription" name="hcErrorDescription" value="<%=cErrorDescription%>"/> <%'[2367] %>

				<div class="form-group">
					<input id="Text1" name="cUserID" value="" class="form-control" placeholder="Username" />
				</div>
				<div class="form-group">
					<input id="Password1" name="cPassword" type="password" value="" class="form-control" placeholder="Password"/>
				</div>
				<div class="login-body-row">
					
				</div>
				<div>
                    <input class="btn btn-blue" type="submit" onclick="SubmitForm();" value="Login" />
                    <% if iLDAPAuthenticationActive = 0 Then %> <%'[2367] %>
                        <input class="btn" type="button" value="Change Password" onclick="resetPassword();" />
                    <% end if %> <%'[2367] %>
                    <!-- UserChangePassword.asp -->
                </div>
			</form>
		</div>
	</div>
    <div class="footer-container">
     <!-- #include file="incFooter.asp" -->
     </div>
</body>
</html>

<script type="text/javascript">
<!--

	<!-- Get token for OPA using user's credentials-->
	function initTokenFromAssessmentApp(user, password) {							
		jQuery.ajax({
			type: "POST",
			url: "https://assessment.betterchoice.com.au/one-page-assessment-rest/authenticate",
			data: JSON.stringify({"username":user.toLowerCase(),"password":password}),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			async: true,
			success: function(response) {
				window.localStorage.setItem("assessment-token", response.token);
				
				var postMsg = response.token;
				postCrossDomainMessage(postMsg);
				//alert(response.token);
			},
			error: function(msg) { 
				if (msg.statusText != "OK") {
					//alert(msg.statusText);
					//alert(JSON.stringify(msg));
				}
			}
		});			
	}		
	
	function postCrossDomainMessage(msg) {
		var win = document.getElementById("ifr").contentWindow;
		win.postMessage(msg, "https://assessment.betterchoice.com.au/");	
	}
	
    //[1044] 
    try {
        document.forms(0).elements("cUserID").focus();
        document.forms(0).elements("cUserID").select();
        //[1024]start
        ShowPisces();
    }
    catch (e) { }

    function ShowPisces() {

        if ('<%=bShowPices%>' == 1) {
            //alert('<%=replace(Application("Loanworks_constantsPath"),"\","\\")%>');
            var s = 'LoanWorksDotNet/DataPages/PiscesProductProcessing.aspx?iUserID=' + '<%=iUserID %>' + '&Path=' + '<%=replace(Application("Loanworks_constantsPath"),"\","\\")%>'
            //[1075]
            var resutl = window.showModalDialog(s, '', 'dialogHeight: 200px; dialogWidth: 450px; center: Yes; resizable: Yes; status: No; scroll: Yes');
            form1.bProceed.value = "1";
            form1.cUserID.value = form1.hcUserID.value
            form1.cPassword.value = form1.hcPassword.value;
            form1.cDomain.value = form1.hcDomain.value;
            form1.submit();
        }
    }

    //[1024]end

    function SubmitForm() {
	
		// Pass username and password for OPA authentication
		initTokenFromAssessmentApp(form1.cUserID.value, form1.cPassword.value);
		
        form1.hcUserID.value = form1.cUserID.value
        form1.hcPassword.value = form1.cPassword.value
        form1.hcDomain.value = form1.cDomain.value
        form1.cPassword.value = ""
        form1.cUserID.value = ""
        form1.cDomain.value = ""
        //[5]
        //form1.submit ();
    }

    function resetPassword() {

        document.location.href = 'UserChangePassword.asp';
    }

    //[5]
-->
</script>

