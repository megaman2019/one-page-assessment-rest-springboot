USE [OnePageAssessment]
GO

/****** Object:  Trigger [dbo].[Applicant_Note_To_Loanworks_HistoryNote]    Script Date: 6/07/2022 4:03:52 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE TRIGGER [dbo].[Applicant_Note_To_Loanworks_HistoryNote] 
   ON  [dbo].[tblApplicantTab_Input] 
   AFTER INSERT, UPDATE
AS 

DECLARE @iUserID INT, @iAppID INT, @cNote varchar(MAX);
DECLARE @dNow datetime;
DECLARE @iNoteEntityID INT, @RC INT, @RC2 INT, @iAppHistoryID INT;

DECLARE @NOTE_CATEGORY VARCHAR(50) = 'One Page Assessment';
DECLARE @NOTE_SUB_CATEGORY VARCHAR(50) = 'Applicants';
DECLARE @iNoteCategory INT;
DECLARE @iNoteSubCategory INT;

DECLARE @deleted_cNote varchar(MAX) = '' --important to set initial value to be able to compare if virtual deleted doesn't have value

BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	--Get new values
	SELECT @iUserID = modifiedBy, @iAppID = appId, @cNote = notes, @dNow = getdate() FROM inserted
	--Get old values
	SELECT @deleted_cNote = notes FROM deleted

	--Get iNoteCategory
	SELECT @iNoteCategory = iCategoryId FROM Loanworks_v18..tblNoteCategory WHERE varName= @NOTE_CATEGORY
	--Get iNoteSubCategory
	SELECT @iNoteSubCategory = iSubCategoryId FROM Loanworks_v18..tblNoteSubCategory WHERE varName = @NOTE_SUB_CATEGORY

	/*
		Insert or update to notes field.
		Check if field notes was updated
		and if @cNote value length is greater than 7 to handle default empty value from frontend = '<p></p>'
		and if @cNote != @deleted_cNote to compare new note versus the old note
		and if @iAppId is not null		
	*/
	IF UPDATE (notes) AND LEN(RTRIM(@cNote)) > 7 AND @cNote != @deleted_cNote AND @iAppID IS NOT NULL 
    BEGIN
		
		--Insert statements for trigger here		 
		--call below based on cNoteType
		--1 Call [dbo].[sp_appHistoryAdd]
		--with param @ignoreNoteEntity = 0 -- so that note entity record not created				
		EXECUTE @RC = [Loanworks_v18].[dbo].[sp_appHistoryAdd] 
		   @iUserID -- [tbl_CA_Note].iUserId
		  ,@iAppID -- [tbl_CA_Note].iAppID
		  ,'' --@cSubject -- [tbl_CA_Note].cNoteType
		  ,null --@iSummarise
		  ,@dNow --@dDate
		  ,null --@dFollowup
		  ,null --@iAction
		  ,@cNote --@cNotes
		  ,null --@cparameter
		  ,@iUserID --@iFootPrint -- [tbl_CA_Note].iUserId lookup entity???
		  ,@dNow --@dDateStamp
		  ,null --@dOutlookFollowup
		  ,'' --@DoYouWantSelect
		  ,1 --@ignoreNoteEntity -- 0
		  ,null --@iAppProdID
	  
		  select @iAppHistoryID = iAppHistoryID
		  from Loanworks_v18..tblAppHistory
		  where iAppID = @iAppID
		  and dDateStamp = @dNow

		-- 2 Then call USP_CRM_NoteEntity_Ins
		-- with category and subcategory
	
		EXECUTE @RC2 = [Loanworks_v18].[dbo].[USP_CRM_NoteEntity_Ins] 
		   1 --@iEntityType -- 1
		  ,@iAppHistoryID --@iNoteID -- from above call
		  ,0 --@iNoteCreateTypeId -- 0
		  ,@iNoteCategory --@iNoteCategory -- 133 - One Page Assessment category
		  ,@iNoteSubCategory --@iNoteSubCategory
		  ,5 --@iRecurrenceID -- 5
		  ,@iUserID --@iCreatedBy -- [tbl_CA_Note].iUserID into footprint
		  ,@iUserID --@iAssignedToID -- [tbl_CA_Note].iUserID into footprint
		  ,@iUserID --@iOwnerID -- [tbl_CA_Note].iUserID into footprint
		  ,3 --@iPriorityID -- 3
		  ,0 --@iRestrictedID -- 0
		  ,1 --@iStatusID -- 1
		  ,null --@dLastStatusDate -- null
		  ,null --@iLinkedNoteID -- 
		  ,@iUserID --@iFootprint -- [tbl_CA_Note].iUserID into footprint
		  ,@dNow --@dCreatedDate -- getdate()
		  ,null --@dFollowupDate -- null
		  ,0 --@iFollowupOwner -- 0
		  ,null --@iFollowupAssignedTo
		  ,@iNoteEntityID OUTPUT
		  ,null --@iMarketingEventID -- null
	  
		-- Clear down note as we have create a LW HN from it
		-- UPDATE tblApplicantTab_Input set notes = '' where appid = @iAppID
	END
END
GO

ALTER TABLE [dbo].[tblApplicantTab_Input] ENABLE TRIGGER [Applicant_Note_To_Loanworks_HistoryNote]
GO

