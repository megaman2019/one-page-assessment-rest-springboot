USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vSecurities]    Script Date: 6/07/2022 1:00:25 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [dbo].[vSecurities] AS 

	SELECT DISTINCT
		_appSec.iAppID,
		_appSec.iSecurityID,
		_PropertyType.cPropertyType,
		_appSec.fPurchasePrice,
		_appSec.fEstimatedValue,
		_valuation.fValuationValue,
		_valuation.fInsuranceReplacementAmount,
		_appSec.fLegalFees,
		IIF(_appSec.bLegalFeeCapitalized = 1, 'Yes', 'No') cCapitalized,
		_appSec.cSecurityAddress1 + ' ' + cSecurityAddress2 + ' ' + cSecurityAddressSuburb + ' ' + _State.cState + ' ' + cSecurityAddressPostCode AS cSecurityAddress,
		IIF(CHARINDEX(',', cRuleValue) > 0, SUBSTRING(cRuleValue, 1,  CHARINDEX(',', cRuleValue) -1), '') cCategory
	FROM
		Loanworks_v18..tblAppSecurity _appSec
	LEFT JOIN
		Loanworks_v18..tblAppValuation _valuation
	ON
		_valuation.iSecurityID=_appSec.iSecurityID and _valuation.bprimary=1
	LEFT JOIN
		(
			SELECT iLookupItemID, cLookupItemName AS cState  FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 2
		) _State
	ON
		_State.iLookupItemID = _appSec.iSecurityAddressState
	LEFT JOIN
		(
			SELECT iLookupItemID, cLookupItemName AS cPropertyType  FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 19
		) _PropertyType
	ON
		_PropertyType.iLookupItemID = _appSec.iPropertyType
	LEFT JOIN
		vAppCreditDecision
	ON
		vAppCreditDecision.iAppID = _appSec.iAppID
		AND vAppCreditDecision.cRule = 'Allowed Security Location:'

GO

