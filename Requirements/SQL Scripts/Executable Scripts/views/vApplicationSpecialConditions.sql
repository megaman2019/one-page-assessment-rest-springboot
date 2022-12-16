USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vApplicationSpecialConditions]    Script Date: 6/07/2022 12:53:28 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO





CREATE VIEW [dbo].[vApplicationSpecialConditions]

AS

SELECT 
	_AppSpecialConditions.iSpecialConditionID,
	_AppSpecialConditions.iAppID,
	_AppSpecialConditions.cSpecialConditionDesc,
	_AppSpecialConditions.cStatus,
	_AppSpecialConditions.dReceived,
	_AppSpecialConditions.dCompleted,
	_AppSpecialConditions.iOrder
FROM
	(
		SELECT
			tblAppSpecialConditions.iAppID,
			tblConditionCategory.cConditionCat, 
			tblConditionCategory.iConditionCatId
		FROM 
			Loanworks_v18..tblAppSpecialConditions  
		INNER JOIN 
			Loanworks_v18..tblLookupItems
		ON 
			tblLookupItems.iLookupItemID = tblAppSpecialConditions.iAppConStatus
			AND tblLookupItems.iLookupCatID =151
		--[1] use LEFT JOIN instead of INNER JOIN
		LEFT JOIN 
			Loanworks_v18..tblConditionCategory
		ON 
			tblConditionCategory.iConditionCatID = tblAppSpecialConditions.iLinkedConditionCat
		GROUP BY
			tblAppSpecialConditions.iAppID, tblConditionCategory.cConditionCat , 
			tblConditionCategory.iConditionCatId, tblAppSpecialConditions.iLinkedConditionCat,
			tblConditionCategory.iOrder	
		HAVING
			ISNULL(tblAppSpecialConditions.iLinkedConditionCat,0) <> 1 AND tblConditionCategory.cConditionCat IS NOT NULL -- non settlement condition		 		
	) _AppSpecialConditionCategory
INNER JOIN
	(
		SELECT	
			tblAppSpecialConditions.iAppID,
			tblAppSpecialConditions.iSpecialConditionID,
			tblAppSpecialConditions.cSpecialConditionDesc,
			tblAppSpecialConditions.iLinkedConditionCat,	
			tblAppSpecialConditions.cNotes,
			tblAppSpecialConditions.dCompleted,
			tblAppSpecialConditions.dReceived,	--[662]
			tblAppSpecialConditions.iAppConStatus,
			tblLookupItems.cLookupItemName AS cStatus,
			tblAppSpecialConditions.iFootprint,
			CONVERT(varchar,tblAppSpecialConditions.dTimeStamp,106) as dTimeStamp,
			tblAppSpecialConditions.iOrder
		FROM 
			Loanworks_v18..tblAppSpecialConditions  
		INNER JOIN 
			Loanworks_v18..tblLookupItems
		ON 
			tblLookupItems.iLookupItemID = tblAppSpecialConditions.iAppConStatus
			AND tblLookupItems.iLookupCatID =151
	) _AppSpecialConditions
ON
	_AppSpecialConditions.iAppID = _AppSpecialConditionCategory.iAppID
	AND _AppSpecialConditions.iLinkedConditionCat = _AppSpecialConditionCategory.iConditionCatID




GO

