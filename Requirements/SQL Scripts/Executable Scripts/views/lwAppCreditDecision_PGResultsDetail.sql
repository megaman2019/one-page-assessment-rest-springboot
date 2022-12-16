USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[lwAppCreditDecision_PGResultsDetail]    Script Date: 6/07/2022 12:51:13 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [dbo].[lwAppCreditDecision_PGResultsDetail]
AS
SELECT [iSN]
      ,[iDecisionRunID]
      ,[cRule]
      ,[cAppValue]
      ,[cRuleValue]
      ,[cResult]
      ,[iAppValueFormat]
      ,[iRuleValueFormat]
      ,[iBorrowerID]
      ,[cType]
      ,[cRuleCode]
  FROM Loanworks_v18.[dbo].[tblAppCreditDecision_PGResultsDetail]


GO

