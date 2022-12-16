USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vUser]    Script Date: 6/07/2022 1:02:31 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE VIEW [dbo].[vUser]

AS 

SELECT * FROM Loanworks_v18..v_OPA_User
GO

