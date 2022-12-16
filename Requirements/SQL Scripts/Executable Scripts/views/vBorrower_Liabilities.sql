USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vBorrower_Liabilities]    Script Date: 6/07/2022 12:55:49 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE VIEW [dbo].[vBorrower_Liabilities] AS

	SELECT 
		MAX(_liabilityAndExpense.iLiabilityID) iLiabilityID,
		_liabilityAndExpense.iBorrowerID,
		_liabilityAndExpense.cLiabilityType,
		SUM(_liabilityAndExpense.fLiabilityAmount) fLiabilityAmount,
		SUM(_liabilityAndExpense.fLiabilityPerMonth) fLiabilityPerMonth,
		SUM(_liabilityAndExpense.fLiabilityOwing) fLiabilityOwing,
		--_liabilityAndExpense.cNotes,		
		SUM(_liabilityAndExpense.fAdjustment) fAdjustment,
		SUM(_liabilityAndExpense.fDifference) fDifference,
		SUM(_liabilityAndExpense.fManualTotal) fManualTotal,
		SUM(_liabilityAndExpense.fCorrection) fCorrection,
		--cReason,
		AVG(_liabilityAndExpense.fPercentage) fPercentage,
		_liabilityAndExpense.cPayoutOnSettlement,
		_liabilityAndExpense.cInstitution,		
		_liabilityAndExpense.cAccountNumber, 
		_liabilityAndExpense.bIsinArrearsorOverLimit,
		_liabilityAndExpense.iCurrentIntrimStatement
	FROM
		(
			SELECT  
				iLiabilityID, 
				iBorrowerID, 
				iLiabilityType, 
				--iLiabilityCat, 
				ISNULL( TypeLookup.cLookupItemName, '' ) AS cLiabilityType, 
				fLiabilityAmount,  
				fLiabilityPerMonth, 
				fLiabilityOwing, 
				cNotes, 								
				--tblAppLiability.bInternalRefinance, 
				--ISNULL(bDeclared,0) AS bDeclared,	
				ISNULL(fAdjustment,0) AS fAdjustment, 
				CASE
					WHEN ISNULL(fAdjustment,0) + ISNULL(fCorrection,0) <> 0 THEN 
						ISNULL(fAdjustment,0) + ISNULL(fCorrection,0)
				END AS fDifference,
				ISNULL(fLiabilityPerMonth,0) + (ISNULL(fAdjustment,0) + ISNULL(fCorrection,0)) AS fManualTotal,
				--ISNULL(bSystemImported,0) AS bSystemImported, 
				ISNULL(fCorrection,0) AS fCorrection, 
				--ISNULL(cReason, '') AS cReason,
				_FinancialOwnership.fPercentage,
				CASE iPayoutOnSettlement
					WHEN 1 THEN
						'Yes'
					WHEN 2 THEN
						'No'
					WHEN 3 THEN
						'Partial'
				ELSE
					''
				END cPayoutOnSettlement,
				cInstitution,
				cAccountNumber,
				CASE bIsinArrearsorOverLimit
					WHEN 1 THEN
						1
					ELSE
						0
				END as bIsinArrearsorOverLimit,
				iCurrentIntrimStatement

			FROM Loanworks_v18..tblAppLiability  
			LEFT JOIN( Loanworks_v18..tblLookupItems AS TypeLookup INNER JOIN Loanworks_v18..tblLookupCat AS TypeCat ON TypeLookup.iLookupCatID = TypeCat.iLookupCatID )  
				ON iLiabilityType = TypeLookup.iLookupItemID AND TypeCat.cLookupCatName = 'Liability Type'  
			LEFT JOIN 
				Loanworks_v18..tblFinancialOwnership _FinancialOwnership
			ON
				_FinancialOwnership.iFinancialType = 2 AND
				_FinancialOwnership.iFinancialID = tblAppLiability.iLiabilityID AND
				_FinancialOwnership.iOwnerID = tblAppLiability.iBorrowerID
		) _liabilityAndExpense
	GROUP BY
		_liabilityAndExpense.iBorrowerID, _liabilityAndExpense.cLiabilityType, _liabilityAndExpense.cPayoutOnSettlement, cInstitution, cAccountNumber, bIsinArrearsorOverLimit, iCurrentIntrimStatement

		


GO

