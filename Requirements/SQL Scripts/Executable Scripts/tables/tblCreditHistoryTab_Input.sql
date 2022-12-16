USE [OnePageAssessment]
GO

/****** Object:  Table [dbo].[tblCreditHistoryTab_Input]    Script Date: 5/07/2022 6:00:14 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblCreditHistoryTab_Input](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[appId] [int] NOT NULL,
	[directoryOrBusinessToQuery] [bit] NULL,
	[notes] [varchar](max) NULL,
	[policyException] [bit] NULL,
	[modifiedBy] [int] NOT NULL,
	[modifiedDate] [datetime] NOT NULL,
 CONSTRAINT [PK_tblCreditHistoryTab_Input] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[tblCreditHistoryTab_Input] ADD  CONSTRAINT [DF_tblCreditHistoryTab_Input_directoryOrBusinessToQuery]  DEFAULT ((0)) FOR [directoryOrBusinessToQuery]
GO

ALTER TABLE [dbo].[tblCreditHistoryTab_Input] ADD  CONSTRAINT [DF_tblCreditHistoryTab_Input_policyException]  DEFAULT ((0)) FOR [policyException]
GO

ALTER TABLE [dbo].[tblCreditHistoryTab_Input] ADD  CONSTRAINT [DF_tblCreditHistoryTab_Input_modifiedDate]  DEFAULT (getdate()) FOR [modifiedDate]
GO

