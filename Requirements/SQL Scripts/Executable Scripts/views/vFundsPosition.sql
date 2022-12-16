USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vFundsPosition]    Script Date: 6/07/2022 12:57:55 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [dbo].[vFundsPosition] AS


SELECT 
	ROW_NUMBER() OVER(ORDER BY iAppID) AS id,
	iAppID, 
	iFundsPositionId, 
	cFundsPosition, 
	fAmount, 
	cVerificationType, 
	fMonthlyRepayment, 
	cInstitution
FROM
(
	SELECT 
		iAppID,
		NULL iFundsPositionId,
		'This ' + cAPFinancier + ' Loan' cFundsPosition,
		SUM(ISNULL(fAPAmount,0)) fAmount,
		NULL cVerificationType,
		NULL fMonthlyRepayment,
		NULL cInstitution
	FROM 
		vProducts
	GROUP BY iAppID, cAPFinancier

	UNION
	
	SELECT 
		tblAppFundsPosition.iAppID,
		tblFundsPosition.iFundsPositionId,
		tblFundsPosition.cFundsPosition,
		ISNULL(tblAppFundsPosition.fAmount,0) AS fAmount,     
		IIF([tblFundsPosition].iFundsVerificationRequired = 1, _Verification.cLookupItemName, '') cVerficationType,   
		IIF([tblAppFundsPosition].fMonthlyRepayment = 1, [tblFundsPosition].iPersonalLoan, '') fMonthlyRepayment,
		[tblAppFundsPosition].cInstitution   
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
	WHERE   tblFundsPosition.iFundsPositionType = 2 --Funds Source
			AND ( tblFundsPosition.iStatus = 1
					OR ( tblFundsPosition.iStatus = 0
						AND tblAppFundsPosition.fAmount IS NOT NULL
						)
				)  --[1]  
			AND (ISNULL(iAssetType,-1) = -1)
			AND tblAppFundsPosition.iAppID IS NOT NULL
) _FundsPosition


GO

