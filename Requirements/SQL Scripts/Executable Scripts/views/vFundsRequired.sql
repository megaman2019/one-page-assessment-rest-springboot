USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vFundsRequired]    Script Date: 6/07/2022 12:58:06 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE VIEW [dbo].[vFundsRequired] AS

SELECT
	ROW_NUMBER() OVER(ORDER BY iAppID) AS id,
	iAppCostID,
	iAppID,
	iAppCostProdID,
	cCostName,
	fCostAmount,
	bCapitalized,
	bAllowCapitalized
FROM
	(
		--App Fees
		SELECT 
			A.iAppCostID,
			A.iAppID,
			A.iAppCostProdID,
			A.cCostName,
			A.fCostAmount,
			A.bCapitalized,
			F.bAllowCapitalized	
		FROM Loanworks_v18..tblAppCost A
		INNER JOIN Loanworks_v18..tblFees F ON A.iFeeLUPID=F.iFeeID
		WHERE iAppCostProdID = 0

		UNION
		--Insurance fee
		SELECT
			NULL,
			iAppID,
			NULL,
			'Mortgage Insurance Premium/Risk Fee' cCostName,
			ISNULL(fMortgageInsurerPremium,0) fCostAmount,
			NULL bCapitalized,
			NULL bAllowCapitalized
		FROM 
			Loanworks_v18..tblAppMain

		UNION

		-- Legal Fee
		SELECT
			NULL,
			iAppID,
			NULL,
			'Legal Fee (' + cSecurityAddress1 + ')',
			fLegalFees,			
			bLegalFeeCapitalized,
			1 bAllowCapitalized
		FROM 
			Loanworks_v18..tblAppSecurity 
		WHERE 
			ISNULL(fLegalFees, 0) <> 0

		UNION	

		-- Valuation Fee
		SELECT
			NULL,
			tblAppSecurity.iAppID,
			NULL,
			'Valuation Fee (' + tblValuer.cValuerName + ')',
			tblAppValuation.fValuationFee,			
			tblAppValuation.bValuationFeeCapitalized,
			1 bAllowCapitalized
		FROM
			Loanworks_v18..tblAppValuation
		INNER JOIN 
			Loanworks_v18..tblAppSecurity 
		ON 
			tblAppValuation.iSecurityID = tblAppSecurity.iSecurityID
		INNER JOIN
			Loanworks_v18..tblValuer on tblAppValuation.iAppValuerID = tblValuer.iValuerID
		WHERE 
			ISNULL(tblAppValuation.fValuationFee, 0) <> 0

		UNION

		SELECT
			NULL,
			tblAppFundsPosition.iAppID,
			NULL,
			tblFundsPosition.cFundsPosition,
			ISNULL(tblAppFundsPosition.fAmount,0) AS fAmount,     
			NULL,
			NULL
		FROM	
			Loanworks_v18..tblFundsPosition
		LEFT JOIN 
			Loanworks_v18..tblAppFundsPosition 
		ON 
			tblFundsPosition.iFundsPositionID = tblAppFundsPosition.iFundsPositionID
		LEFT JOIN
			(
				(SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 1074)
			) _Verification
		ON
			_Verification.iLookupItemID = [tblAppFundsPosition].iVerficationType
		WHERE   tblFundsPosition.iFundsPositionType = 1 --Funds Required
				AND ( tblFundsPosition.iStatus = 1
						OR ( tblFundsPosition.iStatus = 0)
					)  --[1]  
				AND (ISNULL(iAssetType,-1) = -1)
				AND tblAppFundsPosition.iAppID IS NOT NULL
				AND tblFundsPosition.cFundsPosition != 'mortgage insurance premium/risk fee'
				AND ISNULL(tblAppFundsPosition.fAmount, 0) != 0

	) _FundsRequired





GO

