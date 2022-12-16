USE [OnePageAssessment]
GO

/****** Object:  Table [dbo].[tblTabs]    Script Date: 5/07/2022 6:02:57 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblTabs](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tabName] [varchar](100) NOT NULL,
	[tabCode] [varchar](50) NOT NULL,
	[modifiedBy] [int] NOT NULL,
	[modifiedDate] [datetime] NOT NULL,
 CONSTRAINT [PK_tblTabs] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[tblTabs] ADD  CONSTRAINT [DF_tblTabs_modifiedDate]  DEFAULT (getdate()) FOR [modifiedDate]
GO

