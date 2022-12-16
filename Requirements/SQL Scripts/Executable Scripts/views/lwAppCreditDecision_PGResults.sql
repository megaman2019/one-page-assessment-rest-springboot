USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[lwAppCreditDecision_PGResults]    Script Date: 6/07/2022 12:50:23 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [dbo].[lwAppCreditDecision_PGResults]
AS
SELECT [iDecisionRunID]
      ,[iAppID]
      ,[cAppliedProgramGuideline]
      ,[dCreditDecisionRunTime]
      ,[iUserID]
      ,[fFinalLoanAmount]
      ,[iNo_of_Approvals_Required]
      ,[dInitalCreditDecisionRunTime]
  FROM Loanworks_v18.[dbo].[tblAppCreditDecision_PGResults]


GO

