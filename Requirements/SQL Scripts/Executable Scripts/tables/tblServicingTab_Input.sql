USE [OnePageAssessment]
GO

/****** Object:  Table [dbo].[tblServicingTab_Input]    Script Date: 5/07/2022 6:02:37 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblServicingTab_Input](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[appId] [int] NOT NULL,
	[nsr] [float] NULL,
	[nms] [float] NULL,
	[nas] [float] NULL,
	[dti] [float] NULL,
	[lti] [float] NULL,
	[subordination] [float] NULL,
	[lastCreditEvent] [datetime] NULL,
	[notes] [varchar](max) NULL,
	[policyException] [bit] NULL,
	[modifiedBy] [int] NOT NULL,
	[modifiedDate] [datetime] NOT NULL,
	[noOfCreditEvents] [int] NULL,
 CONSTRAINT [PK_tblServicing] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[tblServicingTab_Input] ADD  CONSTRAINT [DF_tblServicing_policyException]  DEFAULT ((0)) FOR [policyException]
GO

ALTER TABLE [dbo].[tblServicingTab_Input] ADD  CONSTRAINT [DF_tblServicing_modifiedDate]  DEFAULT (getdate()) FOR [modifiedDate]
GO

