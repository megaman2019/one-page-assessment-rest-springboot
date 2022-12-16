USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vApplicationConditions]    Script Date: 6/07/2022 12:53:09 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO





CREATE VIEW [dbo].[vApplicationConditions] AS 

SELECT 
	_Conditions.iAppConditionID,
	_ConditionCategory.iAppID,
	_Conditions.cConditionDesc,
	_Conditions.cStatus,
	_Conditions.dReceived,
	_Conditions.dCompleted,
	_Conditions.iOrder
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
			AND tblConditions.iLinkedConditionCat <> 1 --non settlement conditions
		INNER JOIN 
			Loanworks_v18..tblConditionCategory
		ON 
			tblConditionCategory.iConditionCatID = tblConditions.iLinkedConditionCat
		GROUP BY
			tblConditionCategory.cConditionCat, 
			tblConditionCategory.iConditionCatId,
			iAppID,
			tblConditionCategory.iOrder
	) _ConditionCategory
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
			AND tblConditions.iLinkedConditionCat <> 1 --non settlement conditions
		INNER JOIN 
			Loanworks_v18..tblConditionCategory
		ON 
			tblConditionCategory.iConditionCatID = tblConditions.iLinkedConditionCat
	) _Conditions
ON
	_Conditions.iAppID = _ConditionCategory.iAppID
	AND _Conditions.iConditionCatID = _ConditionCategory.iConditionCatID


GO

