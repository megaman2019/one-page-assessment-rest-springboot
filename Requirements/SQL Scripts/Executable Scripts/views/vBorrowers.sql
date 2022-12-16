USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vBorrowers]    Script Date: 7/25/2022 12:56:04 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE VIEW [dbo].[vBorrowers]

AS

SELECT 
	_AppBorrower.iAppID,
	_AppBorrower.iBorrowerID,
	_AppBorrower.iCustID,
	_AppBorrower.cFirstName,
	_AppBorrower.cMiddleName,
	concat(_AppBorrower.cSurname, case when _AppBorrower.bPrimary = 1 then ' (P)' else case when _AppBorrower.bGuarantor = 1 then ' (G)' end end) as cSurname, 
	ISNULL(_MaritalStatus.cLookupItemName, 'Unknown') cMaritalStatus,
	_AppBorrower.cCurrentAddress1 + ' ' + _AppBorrower.cCurrentAddressSuburb + ' ' + _State.cState + ' ' + _AppBorrower.cCurrentAddressPostCode cCurrentAddress,
	_AppBorrower.dDOB,
	/*ISNULL(_AppBorrower.iAdults, 0) +*/ ISNULL(_AppBorrower.iChildren, 0) iDependents,
	_AppBorrower.bPrimary,
	_AppBorrower.bCorporate,
	--Current Employment--
	_AppBorrower.cCurrentEmpName,
	_AppBorrower.cOccupation,
	_AppBorrower.cCurrentEmpABN,
	_AppBorrower.fCurrentEmpIncome,
	_AppBorrower.fCurrentEmpNetIncome,	
	_CurrentEmploymentType.cLookupItemName cCurrentEmploymentType,
	_AppBorrower.iCurrentEmpYears,
	_AppBorrower.iCurrentEmpMonths,
	--End--
	--Previous Emplyment--
	_AppBorrower.cPreviousEmpName,
	_AppBorrower.cPrevOccupation,
	_AppBorrower.cPreABN,
	_AppBorrower.fPreviousEmpIncome,
	_AppBorrower.fPreviousEmpNetIncome,	
	_PrevioustEmploymentType.cLookupItemName cPreviousEmploymentType,
	_AppBorrower.iPreviousEmpYears,	
	--End--
	_AppBorrower.bGuarantor,
	ISNULL(_ResidencyStatus.cLookupItemName, '') cResidencyStatus
	
FROM 
	Loanworks_v18..tblAppBorrower _AppBorrower
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 136
	) _CurrentEmploymentType
ON
	_CurrentEmploymentType.iLookupItemID = _AppBorrower.iCurrentEmploymentTypeID
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 136
	) _PrevioustEmploymentType
ON
	_PrevioustEmploymentType.iLookupItemID = _AppBorrower.iPrevEmploymentTypeID
LEFT JOIN
	(SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 6) _MaritalStatus
ON
	_MaritalStatus.iLookupItemID = _AppBorrower.iMarital
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName AS cState  FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 2
	) _State
ON
	_State.iLookupItemID = _AppBorrower.iCurrentAddressState
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 1184
	) _ResidencyStatus
ON
	_ResidencyStatus.iLookupItemID = _AppBorrower.iResidencyStatus
--ORDER BY _AppBorrower.bPrimary desc
WHERE bCorporate <> 1
UNION ALL
SELECT 
	_AppBorrower.iAppID,
	_AppBorrower.iBorrowerID,
	_AppBorrower.iCustID,
	'' AS cFirstName,
	'' AS cMiddleName,
	CONCAT(CONCAT(_tblAppCorporate.cCompanyName, IIF(_tblAppCorporate.cTrustName IS NULL, '', ' - ' + _tblAppCorporate.cTrustName)), 
		CASE 
			WHEN _AppBorrower.bPrimary = 1 THEN ' (P)' 
		ELSE 
			CASE 
				WHEN _AppBorrower.bGuarantor = 1 THEN ' (G)' 
			END 
		END
	) AS cSurname,
	ISNULL(_MaritalStatus.cLookupItemName, 'Unknown') cMaritalStatus,
	_AppBorrower.cCurrentAddress1 + ' ' + _AppBorrower.cCurrentAddressSuburb + ' ' + _State.cState + ' ' + _AppBorrower.cCurrentAddressPostCode cCurrentAddress,
	_AppBorrower.dDOB,
	/*ISNULL(_AppBorrower.iAdults, 0) +*/ ISNULL(_AppBorrower.iChildren, 0) iDependents,
	_AppBorrower.bPrimary,
	_AppBorrower.bCorporate,
	--Current Employment--
	_AppBorrower.cCurrentEmpName,
	_AppBorrower.cOccupation,
	_AppBorrower.cCurrentEmpABN,
	_AppBorrower.fCurrentEmpIncome,
	_AppBorrower.fCurrentEmpNetIncome,	
	_CurrentEmploymentType.cLookupItemName cCurrentEmploymentType,
	_AppBorrower.iCurrentEmpYears,
	_AppBorrower.iCurrentEmpMonths,
	--End--
	--Previous Emplyment--
	_AppBorrower.cPreviousEmpName,
	_AppBorrower.cPrevOccupation,
	_AppBorrower.cPreABN,
	_AppBorrower.fPreviousEmpIncome,
	_AppBorrower.fPreviousEmpNetIncome,	
	_PrevioustEmploymentType.cLookupItemName cPreviousEmploymentType,
	_AppBorrower.iPreviousEmpYears,	
	--End--
	_AppBorrower.bGuarantor,
	ISNULL(_ResidencyStatus.cLookupItemName, '') cResidencyStatus
	
FROM 
	Loanworks_v18..tblAppBorrower _AppBorrower
INNER JOIN Loanworks_v18..tblAppCorporate _tblAppCorporate on _AppBorrower.iBorrowerID = _tblAppCorporate.iBorrowerID
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 136
	) _CurrentEmploymentType
ON
	_CurrentEmploymentType.iLookupItemID = _AppBorrower.iCurrentEmploymentTypeID
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 136
	) _PrevioustEmploymentType
ON
	_PrevioustEmploymentType.iLookupItemID = _AppBorrower.iPrevEmploymentTypeID
LEFT JOIN
	(SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 6) _MaritalStatus
ON
	_MaritalStatus.iLookupItemID = _AppBorrower.iMarital
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName AS cState  FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 2
	) _State
ON
	_State.iLookupItemID = _AppBorrower.iCurrentAddressState
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 1184
	) _ResidencyStatus
ON
	_ResidencyStatus.iLookupItemID = _AppBorrower.iResidencyStatus
WHERE bCorporate = 1


GO



GO

