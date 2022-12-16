USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vBorrower_Assets]    Script Date: 6/07/2022 12:54:49 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO




CREATE VIEW [dbo].[vBorrower_Assets]
/*
	Derived from USP_AppBorrowerCorporateAssetDataGetByBorrower
*/
AS


	SELECT 
		MAX(iAssetID) iAssetID,
		iBorrowerID,
		cAssetType,
		SUM(fAssetValue) fAssetValue,
		cNotes,
		AVG(fPercentage) fPercentage
	FROM
		(
			SELECT 
				iAssetID, 
				iBorrowerID, 
				--iAssetType, 
				--iAssetCat, 
				ISNULL( TypeLookup.cLookupItemName, cNotes ) AS cAssetType, 
				fAssetValue,
				cNotes, 
				--cDescription,
				--cAddress1, 
				--cAddress2, 
				--cAddressSuburb,
				--ISNULL( StateLookup.cLookupItemName, '' ) AS cAddressState,
				--cAddressPostcode, ISNULL( CountryLookup.varCountry, '' ) AS cAddressCountry,
				--iPropertyType, 
				--iNoOfUnits,
				_FinancialOwnership.fPercentage
			FROM 
				Loanworks_v18..tblAppAsset
			LEFT JOIN
				( Loanworks_v18..tblLookupItems AS TypeLookup INNER JOIN Loanworks_v18..tblLookupCat AS TypeCat ON TypeLookup.iLookupCatID = TypeCat.iLookupCatID )
			ON 
				iAssetType = TypeLookup.iLookupItemID AND TypeCat.cLookupCatName = 'Asset Type'
			--LEFT JOIN
			--	( tblLookupItems AS StateLookup INNER JOIN tblLookupCat AS StateCat ON StateLookup.iLookupCatID = StateCat.iLookupCatID )
			--ON 
			--	iAddressState = StateLookup.iLookupItemID AND StateCat.cLookupCatName = 'State'
			--LEFT JOIN 
			--	tblLookupCountry AS CountryLookup 
			--ON 
			--	tblAppAsset.iAddressCountry = CountryLookup.iCountryID
			LEFT JOIN 
				Loanworks_v18..tblFinancialOwnership _FinancialOwnership
			ON
				_FinancialOwnership.iFinancialType = 1 AND
				_FinancialOwnership.iFinancialID = tblAppAsset.iAssetID AND
				_FinancialOwnership.iOwnerID = tblAppAsset.iBorrowerID
		
		) _asset

	GROUP BY
		iBorrowerID,
		cAssetType,
		cNotes


GO

