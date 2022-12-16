USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vSettlementConditions]    Script Date: 6/07/2022 1:01:22 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO




CREATE VIEW [dbo].[vSettlementConditions] AS 

SELECT 
	_SettlementConditions.iAppConditionID,
	_SettlementConditions.iAppID,
	_SettlementConditions.cConditionDesc,
	_SettlementConditions.cStatus,
	_SettlementConditions.dReceived,
	_SettlementConditions.dCompleted,
	_SettlementConditions.iOrder
FROM
	(
		SELECT 
			tblConditionCategory.cConditionCat, 
			tblConditionCategory.iConditionCatId,
			tblAppConditions.iAppID
		FROM 
			Loanworks_v18..tblAppConditions  
		INNER JOIN 
			Loanworks_v18..tblLookupItems   
		ON 
			tblLookupItems.iLookupItemID = tblAppConditions.iAppConStatus
			AND tblLookupItems.iLookupCatID =151
		INNER JOIN Loanworks_v18..tblConditions
		ON 
			tblConditions.iConditionID = tblAppConditions.iConditionID
			AND tblConditions.iLinkedConditionCat = 1 --settlement conditions
		INNER JOIN 
			Loanworks_v18..tblConditionCategory
		ON 
			tblConditionCategory.iConditionCatID = tblConditions.iLinkedConditionCat
		GROUP BY
			tblConditionCategory.cConditionCat, 
			tblConditionCategory.iConditionCatId,
			iAppID,
			tblConditionCategory.iOrder
	) _SettlementConditionCategories
INNER JOIN
	(
		SELECT	
			tblAppConditions.iAppConditionID,
			tblAppConditions.iAppID,
			tblAppConditions.iConditionID,
			tblConditions.cConditionDesc,
			tblConditionCategory.cConditionCat,
			tblConditionCategory.iConditionCatId,
			tblAppConditions.cNotes,
			tblConditions.cNotes AS cSysNotes,
			tblAppConditions.dReceived,	--[662]
			tblAppConditions.dCompleted,
			tblAppConditions.iAppConStatus,
			tblLookupItems.cLookupItemName AS cStatus,
			tblAppConditions.iFootprint,
			CONVERT(varchar,tblAppConditions.dTimeStamp,106) as dTimeStamp,
			tblAppConditions.iIncludeInSolicitorInstruction, --[2911.6]  
  			tblConditions.iIncludeinSettlementConditions, --[2911.6]
			tblConditions.iOrder
		FROM 
			Loanworks_v18..tblAppConditions  
		INNER JOIN 
			Loanworks_v18..tblLookupItems
		ON 
			tblLookupItems.iLookupItemID = tblAppConditions.iAppConStatus
			AND tblLookupItems.iLookupCatID =151
		INNER JOIN 
			Loanworks_v18..tblConditions
		ON
			tblConditions.iConditionID = tblAppConditions.iConditionID
			AND tblConditions.iLinkedConditionCat = 1 --settlement conditions
		INNER JOIN 
			Loanworks_v18..tblConditionCategory
		ON 
			tblConditionCategory.iConditionCatID = tblConditions.iLinkedConditionCat
	) _SettlementConditions
ON
	_SettlementConditions.iAppID = _SettlementConditionCategories.iAppID
	AND _SettlementConditions.iConditionCatID = _SettlementConditionCategories.iConditionCatID

		


GO

