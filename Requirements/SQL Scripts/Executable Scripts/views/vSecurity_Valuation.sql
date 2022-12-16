USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vSecurity_Valuation]    Script Date: 7/07/2022 10:25:32 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [dbo].[vSecurity_Valuation] AS

SELECT
	_appValuation.ID,
	_appValuation.iSecurityID, 
	_zoning.cLookupItemName cZoning,
	_appValuation.iLandArea, 	
	_unitOfMeasurement.cLookupItemName cUnitOfMeasurement,
	_appValuation.fLivingAreaInSquareMetres,
	_appValuation.iNoOfUnits, 			
	_appValuation.iLandRating,
	_appValuation.iNeighbourhoodRating,
	_appValuation.iEnvironmentalRating,
	_appValuation.iImprovementsRating,
	_appValuation.iReducedValueNextYearsRating,
	_appValuation.iMarketVolatilityRating,
	_appValuation.iMarketSegmentConditionRating,
	_appValuation.dValuationDate,
	_appValuation.iLocalEconomyImpactRating
FROM 
	Loanworks_v18..tblAppValuation _appValuation
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 1061
	) _zoning
ON
	_zoning.iLookupItemID = _appValuation.iZoningType
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 161
	) _unitOfMeasurement
ON
	_unitOfMeasurement.iLookupItemID = _appValuation.iUnitOfMeasurement


--SELECT * FROM v_CA_Security_Valuation WHERE iSecurityID = 41933

--SELECT * FROM tblAppSecurity WHERE iSecurityID = 106077

GO


