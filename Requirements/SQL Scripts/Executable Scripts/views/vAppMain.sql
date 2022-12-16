USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vAppMain]    Script Date: 6/07/2022 12:53:48 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[vAppMain] AS 

SELECT 
	_AppMain.iAppID,
	_AppMain.cLoanName,
	_AppMain.fFinalLVR,
	_LoanPurpose.cLookupItemName cLoanPurpose,	
	ISNULL(_appMain.iNumberOfHouseholds, 0) iNumberOfHouseholds,
	_Insurer.cInsurerName,
	ISNULL(_AppMain.fMortgageInsurerPremium, 0) fMortgageInsurerPremium,
	_FundsPosition.fFundsPositionTotal,
	_FundsRequired.fFundsRequiredTotal,
	_FundsPosition.fFundsPositionTotal - _FundsRequired.fFundsRequiredTotal fSurplusTotal,
	_AppOwner.cOwner,
	_AppOwner.cBranch,		
	_Consultant.cSalesConsultant,
	_Consultant.cConsultantBranch,
	_Consultant.cConsultantRANNo,
	IIF(_AppStatus.dWithSolicitor IS NOT NULL, 1, 0) isLocked
	
FROM 
	Loanworks_v18..tblAppMain _AppMain
LEFT JOIN
	(SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 5) _LoanPurpose
ON
	_LoanPurpose.iLookupItemID = _AppMain.iLoanPurpose
LEFT JOIN 
	(
		SELECT 
			iAppID,
			SUM(ISNULL(fAmount,0)) fFundsPositionTotal
		FROM 
			vFundsPosition
		GROUP BY 
			iAppID
	) _FundsPosition
ON
	_FundsPosition.iAppID = _AppMain.iAppID
LEFT JOIN
	(
		SELECT 
			iAppID,
			SUM(ISNULL(fCostAmount,0)) fFundsRequiredTotal
		FROM 
			vFundsRequired
		GROUP BY 
			iAppID
	) _FundsRequired
ON
	_FundsRequired.iAppID = _AppMain.iAppID
LEFT JOIN
	Loanworks_v18..tblInsurer _Insurer
ON
	_Insurer.iInsurerID = _AppMain.iMortgageInsurer
LEFT JOIN
	(
		SELECT 
			_AppMain.iAppID,		
			ISNULL(_User.cFirstName, '') + ' ' + ISNULL(_User.cSurname, '') + ' - ' + _Org.cOrgPrefix cOwner,
			_Org.cOrgName cBranch
		FROM 
			Loanworks_v18..tblAppMain _AppMain
		LEFT JOIN
			Loanworks_v18..tblUser _User 
		ON
			_AppMain.iAppOwner = _User.iUserID	
		LEFT JOIN
			Loanworks_v18..tblOrg _Org
		ON
			_Org.iOrgID = _User.iOrgID
	) _AppOwner
ON
	_AppMain.iAppID = _AppOwner.iAppID
LEFT JOIN
	(
		SELECT 			
			_AppMain.iAppID,
			_Org.cOrgName cConsultantBranch,
			ISNULL(_User.cSurname, '') + ', ' + ISNULL(_User.cFirstName, '') cSalesConsultant,
			'cboIntroducer' + CAST(ISNULL(_IntrodProfileSlot.iIntroducerSlotID,0) AS VARCHAR(10)) cConsultantRANNo
		FROM 
			Loanworks_v18..tblAppMain _AppMain
		LEFT JOIN
			Loanworks_v18..tblUser _User 
		ON
			_AppMain.iConsultantID = _User.iUserID	
		LEFT JOIN
			Loanworks_v18..tblOrg _Org
		ON
			_Org.iOrgID = _User.iOrgID
		LEFT JOIN
			Loanworks_v18..tblIntroducerProfileSlot _IntrodProfileSlot
		ON
			_IntrodProfileSlot.iIntroducerProfileHeaderID = _AppMain.iIntroducerProfileHeaderID AND 
			_IntrodProfileSlot.iMandatoryOptionalStatus = 1
	) _Consultant
ON
	_AppMain.iAppID = _Consultant.iAppID
LEFT JOIN
	Loanworks_v18..tblAppStatus _AppStatus
ON
	_AppStatus.iAppID = _AppMain.iAppID
	
GO

