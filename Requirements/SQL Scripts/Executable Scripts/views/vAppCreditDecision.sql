USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vAppCreditDecision]    Script Date: 7/25/2022 12:51:46 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[vAppCreditDecision] 

AS

	SELECT 
		_PGResults.iAppID,
		_PGResultsDetail.*
	FROM
		(
			SELECT 
				iSN, 
				iDecisionRunID, 
				cRule, 
				cAppValue, 
				cRuleValue, 
				cResult,
				iAppValueFormat,
				iRuleValueFormat,
				iBorrowerID,
				cType,
				cRuleCode, 
				ROW_NUMBER() OVER(PARTITION BY iDecisionRunID, cRule ORDER BY iSN DESC) as rowNumber
			FROM Loanworks_v18..tblAppCreditDecision_PGResultsDetail
		) AS _PGResultsDetail
	INNER JOIN
		(
			SELECT 
				iAppID, MAX(iDecisionRunID) iDecisionRunID
			FROM 
				Loanworks_v18..tblAppCreditDecision_PGResults
			GROUP BY
				iAppID
		) _PGResults
	ON
		_PGResults.iDecisionRunID = _PGResultsDetail.iDecisionRunID
	WHERE
		_PGResultsDetail.rowNumber = 1


GO

