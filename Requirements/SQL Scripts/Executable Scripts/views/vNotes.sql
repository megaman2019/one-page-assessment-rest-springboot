USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vNotes]    Script Date: 6/07/2022 12:59:08 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO





CREATE VIEW [dbo].[vNotes]
AS

SELECT 
	ah.iAppHistoryID, ah.iAppID, ah.cSubject, ah.dDateStamp, ah.cNotes,
	e.iNoteCategory, 
	'One Page Application' as [CategoryName],
	e.iNoteSubCategory,
	case e.iNoteSubCategory
		when 957 then 'Applicant Tab'
		when 958 then 'Transactions Tab'
		when 959 then 'Exit Strategy Tab'
		when 960 then 'Credit History Tab'
		when 961 then 'Income Tab'
		when 962 then 'Securities Tab'
		when 963 then 'ANL Tab'
	end as [SubCategoryName]
--	 *
from Loanworks_v18..tblAppHistory ah inner join
Loanworks_v18..tblNoteEntity e on ah.iAppHistoryID = e.iNoteID
where e.iNoteCategory = 133 -- OPA
--and ah.iAppID = 123898
--order by ah.dDate desc



GO

