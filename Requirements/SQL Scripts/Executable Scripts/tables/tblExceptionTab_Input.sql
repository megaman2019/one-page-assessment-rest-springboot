USE [OnePageAssessment]
GO

/****** Object:  Table [dbo].[tblExceptionTab_Input]    Script Date: 5/07/2022 6:01:00 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblExceptionTab_Input](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[appId] [int] NOT NULL,
	[tabName] [varchar](100) NOT NULL,
	[input] [varchar](100) NULL,
	[modifiedBy] [int] NOT NULL,
	[modifiedDate] [datetime] NOT NULL,
 CONSTRAINT [PK_tblExceptionTab_Input] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[tblExceptionTab_Input] ADD  CONSTRAINT [DF_tblExceptionTab_Input_modifiedDate]  DEFAULT (getdate()) FOR [modifiedDate]
GO

