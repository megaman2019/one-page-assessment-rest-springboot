USE [OnePageAssessment]
GO

/****** Object:  Table [dbo].[tblResponsibleLendingTab_Input]    Script Date: 5/07/2022 6:01:58 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblResponsibleLendingTab_Input](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[appId] [int] NOT NULL,
	[costAndRiskOngoingFee] [bit] NULL,
	[costAndRiskBreakCosts] [bit] NULL,
	[costAndRiskLMI] [bit] NULL,
	[costAndRiskRefinanceCosts] [bit] NULL,
	[loanRepayTypePrincipalAndInterest] [bit] NULL,
	[loanRepayTypeInterestOnly] [bit] NULL,
	[loanRepayTypeFixed] [bit] NULL,
	[loanRepayTypeVariable] [bit] NULL,
	[objectivePurchase] [bit] NULL,
	[objectiveRefinance] [bit] NULL,
	[objectiveDebtConsolidation] [bit] NULL,
	[objectiveLowInterestRate] [bit] NULL,
	[objectiveFinancialGoals] [bit] NULL,
	[objectiveOther] [bit] NULL,
	[applicationNotSuitable] [bit] NULL,
	[applicationMeetRequirements] [bit] NULL,
	[notes] [varchar](max) NULL,
	[policyException] [bit] NULL,
	[modifiedBy] [int] NOT NULL,
	[modifiedDate] [datetime] NOT NULL,
 CONSTRAINT [PK_tblResponsibleLending] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_costAndRiskOngoingFee]  DEFAULT ((0)) FOR [costAndRiskOngoingFee]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_costAndRiskBreakCosts]  DEFAULT ((0)) FOR [costAndRiskBreakCosts]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_costAndRiskLMI]  DEFAULT ((0)) FOR [costAndRiskLMI]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_costAndRiskRefinanceCosts]  DEFAULT ((0)) FOR [costAndRiskRefinanceCosts]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_loanRepayTypeOngoingFee]  DEFAULT ((0)) FOR [loanRepayTypePrincipalAndInterest]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_loanRepayTypeBreakCosts]  DEFAULT ((0)) FOR [loanRepayTypeInterestOnly]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_loanRepayTypeLMI]  DEFAULT ((0)) FOR [loanRepayTypeFixed]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_loanRepayTypeRefinanceCosts]  DEFAULT ((0)) FOR [loanRepayTypeVariable]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_objectivePurchase]  DEFAULT ((0)) FOR [objectivePurchase]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_objectiveRefinance]  DEFAULT ((0)) FOR [objectiveRefinance]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_objectiveDebtConsolidation]  DEFAULT ((0)) FOR [objectiveDebtConsolidation]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_objectiveLowInterestRate]  DEFAULT ((0)) FOR [objectiveLowInterestRate]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_objectiveFInancialGoals]  DEFAULT ((0)) FOR [objectiveFinancialGoals]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_objectiveOther]  DEFAULT ((0)) FOR [objectiveOther]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_policyException]  DEFAULT ((0)) FOR [policyException]
GO

ALTER TABLE [dbo].[tblResponsibleLendingTab_Input] ADD  CONSTRAINT [DF_tblResponsibleLending_modifiedDate]  DEFAULT (getdate()) FOR [modifiedDate]
GO

