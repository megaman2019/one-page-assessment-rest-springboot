USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vProducts]    Script Date: 6/07/2022 12:59:58 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[vProducts] AS 

SELECT 
	--ROW_NUMBER() OVER(ORDER BY _appProduct.iAppProductID ASC) AS id,
	_appProduct.iAppProductID,
	_appProduct.iAppID,
	_appProduct.cAPProductName,
	_appProduct.fAPBorrowerRate,
	_appProduct.fAPAmount,
	_appProduct.fAPRepayments,
	_appProduct.dAPMaturity,
	replace(_appProduct.cAPProductType, '&amp;', '&') as cAPProductType,
	_appProduct.iAPTermYear,
	_appProduct.iAPTermMonth,
	_appProduct.cAPFinancier,
	_appProduct.cPortionCode,
	replace(_productLoanType.cLookupItemName, '&amp;', '&') cProductLoanType
	--_ProductFee.cFeeName,
	--_ProductFee.fAmount AS fFeeAmount,
	--_ProductLoanPurpose.varLoanPurposeDesc,
	--_ProductLoanPurpose.fLoanPurposeAmount

FROM
	Loanworks_v18..tblAppProduct _appProduct
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 39
	) _productLoanType
ON
	_productLoanType.iLookupItemID = _appProduct.iDefaultLoanType
--LEFT JOIN
--	(		
--		SELECT 	
--			tblAppProduct.iAppID, 
--			tblAppProduct.iAppProductID,
--   			tblAppCost.fCostAmount AS fAmount, 
--			tblFees.cFeeName    	    		
--		FROM Loanworks_v18..tblAppProduct
--		INNER JOIN Loanworks_v18..tblAppCost	--[4]
--			ON  tblAppProduct.iAppProductID = tblAppCost.iAppCostProdID 
--		LEFT JOIN Loanworks_v18..tblFees 
--			ON tblAppCost.iFeeLUPID=tblFees.iFeeID 
--		LEFT JOIN Loanworks_v18..tblBankTransactionType
--			ON tblFees.iBankTransactionType=tblBankTransactionType.iBankTransactionTypeID 						
--		WHERE (tblBankTransactionType.iBankTransactionTypeID <> 22
--		AND tblBankTransactionType.iBankTransactionTypeID <> 21
--		OR tblBankTransactionType.iBankTransactionTypeID IS NULL)
--	) _ProductFee
--ON
--	_ProductFee.iAppProductID = _appProduct.iAppProductID

--LEFT JOIN
--	(
--		SELECT
--			iAppProductID,
--			varLoanPurposeDesc,
--			fLoanPurposeAmount
--		FROM 
--			vProduct_LoanPurpose
--	) _ProductLoanPurpose
--ON
--	_ProductLoanPurpose.iAppProductID = _appProduct.iAppProductID
GO

