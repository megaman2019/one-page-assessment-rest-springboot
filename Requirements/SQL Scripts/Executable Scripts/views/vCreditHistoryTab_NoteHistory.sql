USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vCreditHistoryTab_NoteHistory]    Script Date: 6/07/2022 12:56:56 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[vCreditHistoryTab_NoteHistory] AS 


SELECT 
	 _AppHistory.iAppHistoryID, _AppHistory.iAppID, _AppHistory.cNotes, _AppHistory.dDateStamp, _AppHistory.iFootPrint, _User.cFullName
	FROM
	Loanworks_v18..tblNoteEntity _NoteEntity
INNER JOIN
	Loanworks_v18..tblAppHistory _AppHistory
ON 
	_AppHistory.iAppHistoryID = _NoteEntity.iNoteID
LEFT JOIN
	(		
		SELECT 
			_Entity.iEntityID,
			_User.cFirstName + ' ' + _User.cSurname cFullName
		FROM 
			Loanworks_v18..tblEntity _Entity
		LEFT JOIN
			Loanworks_v18..tblUser _User
		ON
			_Entity.iEntityRootID = _User.iUserID AND 
			_Entity.iEntityType = 3
	) _User
ON
	_User.iEntityID = _AppHistory.iFootPrint
WHERE 
	_NoteEntity.iNoteCategory = (SELECT TOP 1 iCategoryId FROM Loanworks_v18..tblNoteCategory WHERE varName = 'One Page Assessment')
	AND _NoteEntity.iNoteSubCategory = (SELECT TOP 1 iSubCategoryId FROM Loanworks_v18..tblNoteSubCategory WHERE varName = 'Credit History')
GO

