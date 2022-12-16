USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vCreditHistory]    Script Date: 6/07/2022 12:56:40 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE VIEW [dbo].[vCreditHistory]

AS
	--Non Corporate
	SELECT 	tblAppCreditSummary.iAppCreditSummaryID,
		tblAppBorrower.iAppID,
		tblAppBorrower.iBorrowerID,
		tblAppBorrower.iCustID,
		tblAppCreditSummary.iBankruptcies,
		tblAppCreditSummary.iDirectorships,
		tblAppCreditSummary.iDefaults,
		tblAppCreditSummary.iTotalEnquiries,
		tblAppCreditSummary.iEnquiriesLastYear,
		tblAppCreditSummary.iJudgements,
		tblAppCreditSummary.iWritsAndSummons,
		tblAppCreditSummary.cVedaScore,
		tblAppBorrower.cFirstName,
		tblAppBorrower.cMiddleName,
		tblAppBorrower.cSurname,                                                                                                
		--tblAppBorrower.bGuarantor,
		tblAppBorrower.bCorporate,
		_Result.cLookupItemName cResult,                                                                                                                                                                                                           
		--tblAppCreditSummary.cNotes,
		'Yes' cCreditStatus
	FROM 	
		Loanworks_v18..tblAppCreditSummary
	INNER JOIN 
		Loanworks_v18..tblAppBorrower 
	ON 
		tblAppCreditSummary.iBorrowerID = tblAppBorrower.iBorrowerID
	LEFT JOIN 
		(SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatid=200) _Result
	ON
		_Result.iLookupItemID = tblAppCreditSummary.iResult
	WHERE 
		tblAppBorrower.bCorporate <> 1
	
	UNION	
	
	--Corporate
	SELECT 	tblAppCreditSummary.iAppCreditSummaryID,
		tblAppBorrower.iAppID,
		tblAppBorrower.iBorrowerID,
		tblAppBorrower.iCustID,
		tblAppCreditSummary.iBankruptcies,                                                                                                                                                                                                                           
		tblAppCreditSummary.iDirectorships,
		tblAppCreditSummary.iDefaults,
		tblAppCreditSummary.iTotalEnquiries,
		tblAppCreditSummary.iEnquiriesLastYear,
		tblAppCreditSummary.iJudgements,
		tblAppCreditSummary.iWritsAndSummons,
		tblAppCreditSummary.cVedaScore,
		'',
		'',
		CASE 
			WHEN ISNULL(tblAppCorporate.cCompanyName, '') = '' THEN
				tblAppCorporate.[cTrustName]
			ELSE
				tblAppCorporate.cCompanyName
		END cCompanyName,
		--tblAppBorrower.bGuarantor,
		tblAppBorrower.bCorporate,
		_Result.cLookupItemName cResult,
		--tblAppCreditSummary.cNotes,
		'Yes' cCreditStatus
	FROM 
		Loanworks_v18..tblAppCreditSummary
	INNER JOIN 
		Loanworks_v18..tblAppCorporate 
	ON 
		tblAppCreditSummary.iBorrowerID = tblAppCorporate.iBorrowerID
	INNER JOIN 
		Loanworks_v18..tblAppBorrower 
	ON 
		tblAppCreditSummary.iBorrowerID = tblAppBorrower.iBorrowerID
	LEFT JOIN 
		(
			SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatid=200
		) _Result
	ON
		_Result.iLookupItemID = tblAppCreditSummary.iResult



GO

