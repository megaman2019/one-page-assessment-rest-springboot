USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vBorrower_Incomes]    Script Date: 6/07/2022 12:55:35 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [dbo].[vBorrower_Incomes] 
AS 

SELECT 
	MAX(iIncomeID) iIncomeID,
	iBorrowerID,
	cIncomeType,
	SUM(fAmount) fAmount,
	SUM(fNetAmount) fNetAmount,
	cIncomePeriod,
	SUM(fAdjustment) fAdjustment,
	SUM(fCorrection) fCorrection,
	SUM(fDifference) fDifference,
	SUM(fManualTotal) fManualTotal,
	AVG(fPercentage) fPercentage,
	cReason
FROM 
	(
		SELECT 
			iIncomeID, 
			iBorrowerID, 
			--iIncomeType, 
			ISNULL( TypeLookup.cLookupItemName, cDetails ) AS cIncomeType, 
			fAmount, 
			fNetAmount,
			--cDetails, 
			--iIncomeCat,
			--iIncomePeriod, 
			ISNULL( PeriodLookup.cLookupItemName, '' ) AS cIncomePeriod,
			--iSecSelfEmpType, 			 
			--ISNULL(bDeclared,0) AS bDeclared,
			ISNULL(fAdjustment,0) AS fAdjustment, 
			ISNULL(fCorrection,0) AS fCorrection, 
			CASE
				WHEN ISNULL(fAdjustment,0) + ISNULL(fCorrection,0) <> 0 THEN 
					ISNULL(fAdjustment,0) + ISNULL(fCorrection,0)
			END AS fDifference,
			ISNULL(fNetAmount,0) + (ISNULL(fAdjustment,0) + ISNULL(fCorrection,0)) AS fManualTotal,
			--ISNULL(bSystemImported,0) AS bSystemImported, 						
			_financialOwnership.fPercentage,
			ISNULL(cReason,'') AS cReason

		FROM Loanworks_v18..tblAppIncome
		LEFT JOIN( Loanworks_v18..tblLookupItems AS TypeLookup INNER JOIN Loanworks_v18..tblLookupCat AS TypeCat ON TypeLookup.iLookupCatID = TypeCat.iLookupCatID )
				 ON iIncomeType = TypeLookup.iLookupItemID AND TypeCat.cLookupCatName = 'Income Type'
		LEFT JOIN( Loanworks_v18..tblLookupItems AS PeriodLookup INNER JOIN Loanworks_v18..tblLookupCat AS PeriodCat ON PeriodLookup.iLookupCatID = PeriodCat.iLookupCatID )
				ON iIncomePeriod = PeriodLookup.iLookupItemID AND PeriodCat.cLookupCatName = 'Income Period'
		LEFT JOIN
			Loanworks_v18..tblFinancialOwnership _financialOwnership
		ON
			_financialOwnership.iFinancialID = tblAppIncome.iIncomeID
	) _Incomes
GROUP BY
	iBorrowerID, cIncomeType, cIncomePeriod, cReason


GO

