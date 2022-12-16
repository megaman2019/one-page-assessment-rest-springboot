USE [OnePageAssessment]
GO

/****** Object:  Table [dbo].[tblApplicantTab_Input]    Script Date: 5/07/2022 5:58:53 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblApplicantTab_Input](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[appId] [int] NOT NULL,
	[notes] [varchar](max) NULL,
	[policyException] [bit] NULL,
	[modifiedBy] [int] NOT NULL,
	[modifiedDate] [datetime] NOT NULL,
 CONSTRAINT [PK_tblApplicantTab_Input] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[tblApplicantTab_Input] ADD  CONSTRAINT [DF_tblApplicantTab_Input_policyException]  DEFAULT ((0)) FOR [policyException]
GO

ALTER TABLE [dbo].[tblApplicantTab_Input] ADD  CONSTRAINT [DF_tblApplicantTab_Input_modifiedDate]  DEFAULT (getdate()) FOR [modifiedDate]
GO

