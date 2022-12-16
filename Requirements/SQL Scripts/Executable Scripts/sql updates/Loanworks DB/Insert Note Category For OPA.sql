USE [Loanworks_v18]
GO

DECLARE @iNextCategoryID INT;

SELECT @iNextCategoryID = MAX(iCategoryID) + 1 FROM tblNoteCategory

SELECT @iNextCategoryID

INSERT INTO [dbo].[tblNoteCategory]
           ([iCategoryId]
           ,[varName]
           ,[varDescription]
           ,[iActive]
           ,[iFootPrint]
           ,[dTimeStamp]
           ,[iIsSystem]
           ,[iIsDefault]
           ,[iIsDeleted]
           ,[iIsReadOnly]
           ,[iIntroducerNoteOnly]
           ,[iOutboundCallNotes])
     VALUES
           (@iNextCategoryID
           ,'One Page Assessment'
           ,'One Page Assessment'
           ,1
           ,142
           ,getDate()
           ,0
           ,0
           ,null
           ,null
           ,0
           ,0)
GO
