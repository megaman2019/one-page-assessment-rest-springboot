USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vMortgagors]    Script Date: 6/07/2022 12:58:52 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[vMortgagors]

AS

SELECT DISTINCT
	_AppMortgagor.iBorrowerID as iMortgagorID,
	_AppSecurity.iAppID,
	_AppMortgagor.iBorrowerID,
	_AppMortgagor.cFirstName as cFirstNameOnly,
	_AppMortgagor.cMiddleName,
	_AppMortgagor.cSurname
FROM
	vBorrowers _AppMortgagor
INNER JOIN 
	Loanworks_v18..tblFinancialOwnership _tblFinancialOwnership
ON
	_AppMortgagor.iBorrowerID = _tblFinancialOwnership.iOwnerID
INNER JOIN
	Loanworks_v18..tblAppSecurity _AppSecurity
ON
	_tblFinancialOwnership.iFinancialID = _AppSecurity.iSecurityID
WHERE _tblFinancialOwnership.iIsMortgagor = 1

GO