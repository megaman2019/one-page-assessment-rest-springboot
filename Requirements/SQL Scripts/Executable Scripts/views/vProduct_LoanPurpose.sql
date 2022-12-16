USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vProduct_LoanPurpose]    Script Date: 6/07/2022 12:59:39 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[vProduct_LoanPurpose] AS

	SELECT 
		tblAppLoanPurposeLink.id,
		tblLoanPurposeLookup.varLoanPurposeDesc, 
		--tblAppLoanPurpose.iAppLoanPurposeID, 
		ISNULL(tblAppLoanPurpose.fLoanPurposeAmount, 0) AS fLoanPurposeAmount,
		tblAppLoanPurposeLink.iAppProductID
		--tblAppProduct.cPortionCode,
		--tblAppProduct.cAPProductName,
		--tblAppProduct.fAPAmount,
		--tblAppLoanPurpose.cNotes, --[2]
		--tblFunderLoanPurpose.iFunderID,		
	FROM Loanworks_v18..tblLoanPurposeLookup 
	INNER JOIN Loanworks_v18..tblFunderLoanPurpose
		ON tblLoanPurposeLookup.iLoanTypeLookupID = tblFunderLoanPurpose.iLoanTypeLookupID
	INNER JOIN Loanworks_v18..tblAppLoanPurpose 
		ON tblFunderLoanPurpose.iLoanPurposeTypeID = tblAppLoanPurpose.iLoanPurposeTypeID
			--AND tblAppLoanPurpose.iAppID = @iAppID
	--[1] add join tables
	INNER JOIN Loanworks_v18..tblAppLoanPurposeLink
		ON tblAppLoanPurposeLink.iAppLoanPurposeID = tblAppLoanPurpose.iAppLoanPurposeID
	INNER JOIN Loanworks_v18..tblAppProduct 
		ON tblAppProduct.iAppProductID = tblAppLoanPurposeLink.iAppProductID
		--AND tblAppProduct.iAppID = @iAppID --[2]
	--INNER JOIN Loanworks_v18..tblProduct _Product
	--	ON _Product.iProdID = tblAppProduct.iProdID
	--INNER JOIN Loanworks_v18..tblFinancier _Funder
	--	ON _Funder.iFinancierID = _Product.iFinancier

GO

