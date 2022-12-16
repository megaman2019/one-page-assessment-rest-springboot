USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vProduct_Fees]    Script Date: 6/07/2022 12:59:25 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [dbo].[vProduct_Fees] AS 

SELECT 	
	tblFees.iFeeID,
	tblAppProduct.iAppProductID,
   	tblAppCost.fCostAmount AS fAmount, 
	tblFees.cFeeName    	    		
FROM 
	Loanworks_v18..tblAppProduct
INNER JOIN 
	Loanworks_v18..tblAppCost	--[4]
ON  
	tblAppProduct.iAppProductID = tblAppCost.iAppCostProdID 
LEFT JOIN 
	Loanworks_v18..tblFees 
ON 
	tblAppCost.iFeeLUPID=tblFees.iFeeID 
LEFT JOIN 
	Loanworks_v18..tblBankTransactionType
ON 
	tblFees.iBankTransactionType=tblBankTransactionType.iBankTransactionTypeID 						
WHERE
	(tblBankTransactionType.iBankTransactionTypeID <> 22
	AND tblBankTransactionType.iBankTransactionTypeID <> 21
	OR tblBankTransactionType.iBankTransactionTypeID IS NULL)


GO

