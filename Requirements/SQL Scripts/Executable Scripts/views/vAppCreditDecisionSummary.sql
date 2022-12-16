USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vAppCreditDecisionSummary]    Script Date: 6/07/2022 12:52:08 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO









CREATE VIEW [dbo].[vAppCreditDecisionSummary]
AS

SELECT     a.* 
, concat(u.cFirstname, ' ', u.cSurname) as cUsername
, dbo.bcfn_GetCreditDecision(a.iDecisionRunID) as cStatus
FROM Loanworks_v18.dbo.tblAppCreditDecision_PGResults a
LEFT JOIN Loanworks_v18.dbo.tblUser u on a.iUserID = u.iUserID
--where iAppID = @iAppID 
--ORDER BY 1 DESC



GO

