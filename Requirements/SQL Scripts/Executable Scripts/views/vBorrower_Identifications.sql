USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vBorrower_Identifications]    Script Date: 6/07/2022 12:55:18 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO




CREATE VIEW [dbo].[vBorrower_Identifications] AS 

SELECT 
	_BorrowerIdentfication.iBorrowerIdentificationID,
	_BorrowerIdentfication.iBorrowerID,
	_BorrowerIdentfication.cDocumentNumber,
	_Identification.cLookupItemName cDocumentType
FROM
	Loanworks_v18..tblAppBorrower_Identification _BorrowerIdentfication
LEFT JOIN
	(
		SELECT iLookupItemID, cLookupItemName FROM Loanworks_v18..tblLookupItems WHERE iLookupCatID = 1185
	) _Identification
ON
	_Identification.iLookupItemID = _BorrowerIdentfication.iDocumentType


GO

