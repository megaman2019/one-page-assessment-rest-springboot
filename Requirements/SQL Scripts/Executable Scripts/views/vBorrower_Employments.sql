USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vBorrower_Employments]    Script Date: 6/07/2022 12:55:04 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [dbo].[vBorrower_Employments]

AS

SELECT
	ID,
	iBorrowerID,
	cOccupation AS occupation,
	cCurrentEmpName AS empName,
	fCurrentEmpIncome AS income,
	fCurrentEmpNetIncome AS netIncome,
	cCurrentEmpABN AS abn,
	'Current' AS [type],
	iCurrentEmpYears as [tenure_years],
	iCurrentEmpMonths as [tenure_months],
	_CurrentEmploymentType.cLookupItemName as cEmploymentType
	
FROM 
	Loanworks_v18..tblAppBorrower _AppBorrower
	LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 136
	) _CurrentEmploymentType
ON
	_CurrentEmploymentType.iLookupItemID = _AppBorrower.iCurrentEmploymentTypeID
UNION ALL

SELECT
	ID,
	iBorrowerID,
	cPrevOccupation AS occupation,
	cPreviousEmpName AS empName,
	fPreviousEmpIncome AS income,
	fPreviousEmpNetIncome AS netIncome,
	'' AS abn,
	'Previous' AS [type],
	iPreviousEmpYears as [tenure_years],
	0 as [tenure_months],
	_PrevioustEmploymentType.cLookupItemName as cEmploymentType

FROM 
	Loanworks_v18..tblAppBorrower _AppBorrower
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 136
	) _PrevioustEmploymentType
ON
	_PrevioustEmploymentType.iLookupItemID = _AppBorrower.iPrevEmploymentTypeID
GO

