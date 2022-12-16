USE [OnePageAssessment]
GO

/****** Object:  Table [dbo].[tblApplicant_Input]    Script Date: 5/07/2022 5:58:26 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblApplicant_Input](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[appId] [int] NOT NULL,
	[applicantId] [int] NOT NULL,
	[matrixIdPass] [int] NOT NULL,
	[pepSanctionMatch] [int] NOT NULL,
	[modifiedBy] [int] NOT NULL,
	[modifiedDate] [datetime] NOT NULL,
 CONSTRAINT [PK_tblApplicant_Input] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[tblApplicant_Input] ADD  CONSTRAINT [DF_tblApplicant_Input_modifiedDate]  DEFAULT (getdate()) FOR [modifiedDate]
GO

