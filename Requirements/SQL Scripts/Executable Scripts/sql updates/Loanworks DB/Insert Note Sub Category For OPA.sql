USE [Loanworks_v18]
GO

DECLARE @iNextSubCategoryID INT;

SELECT @iNextSubCategoryID = MAX(iSubCategoryId) + 1 FROM tblNoteSubCategory

INSERT INTO [dbo].[tblNoteSubCategory]
           ([iSubCategoryId]
			,[varName]
           ,[varDescription]
           ,[iFootprint]
           ,[dTimeStamp]
           ,[iActive]
           ,[iIsDefault]
           ,[iSuccessfulOutboundCall])

SELECT @iNextSubCategoryID,	'Summary',	'Summary',	142, getDate(),1,0,0
UNION SELECT @iNextSubCategoryID + 1,	'Applicants',	'Applicants',	142, getDate(),1,0,0
UNION SELECT @iNextSubCategoryID + 2,	'Transactions',	'Transactions',	142, getDate(),1,0,0
UNION SELECT @iNextSubCategoryID + 3,	'Exit Strategy',	'Exit Strategy',	142, getDate(),1,0,0
UNION SELECT @iNextSubCategoryID + 4,	'Credit History',	'Credit History',	142, getDate(),1,0,0
UNION SELECT @iNextSubCategoryID + 5,	'Incomes',	'Incomes',	142, getDate(),1,0,0
UNION SELECT @iNextSubCategoryID + 6,	'Securities',	'Securities',	142, getDate(),1,0,0
UNION SELECT @iNextSubCategoryID + 7,	'Assets and Liabilities',	'Assets and Liabilities',	142, getDate(),1,0,0
UNION SELECT @iNextSubCategoryID + 8,	'Living Expenses',	'Living Expenses',	142, getDate(),1,0,0
UNION SELECT @iNextSubCategoryID + 9,	'Conditions',	'Conditions',	142, getDate(),1,0,0
UNION SELECT @iNextSubCategoryID + 10,	'Decision Summaries',	'Decision Summaries',	142, getDate(),1,0,0
UNION SELECT @iNextSubCategoryID + 11,	'Responsible Lendings',	'Responsible Lendings',	142, getDate(),1,0,0
UNION SELECT @iNextSubCategoryID + 12,	'Servicing',	'Servicing',	142, getDate(),1,0,0

GO


SELECT * FROM tblNoteSubCategory