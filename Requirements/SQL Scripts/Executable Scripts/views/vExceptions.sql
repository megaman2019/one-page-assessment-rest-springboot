USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vExceptions]    Script Date: 6/07/2022 12:57:31 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [dbo].[vExceptions]
AS

SELECT 
	ROW_NUMBER() OVER(ORDER BY appId) AS id,
	_Exceptions.appId,
	_Exceptions.tabName,
	_Exceptions.policyException,
	_Exceptions.input,
	_Exceptions.modifiedBy,
	_Exceptions.modifiedDate,
	concat(u.cFirstName, ' ', u.cSurname) as username
FROM (
	SELECT 
		_input.appId, 'Applicant Tab' tabName, _input.policyException, _exceptionTab.input, _exceptionTab.modifiedBy, _exceptionTab.modifiedDate
	FROM 
		tblApplicantTab_Input _input
	LEFT JOIN 
		tblExceptionTab_Input _exceptionTab
	ON
		_exceptionTab.appId = _input.appId AND
		_exceptionTab.tabName = 'Applicant Tab'		
	UNION
	SELECT 
		_input.appId, 'Asset And Liability Tab', _input.policyException, _exceptionTab.input, _exceptionTab.modifiedBy, _exceptionTab.modifiedDate
	FROM 
		tblAssetAndLiabilityTab_Input _input
	LEFT JOIN 
		tblExceptionTab_Input _exceptionTab
	ON
		_exceptionTab.appId = _input.appId AND
		_exceptionTab.tabName = 'Asset And Liability Tab'	
	UNION
	SELECT 
		_input.appId, 'Condition Tab', _input.policyException, _exceptionTab.input, _exceptionTab.modifiedBy, _exceptionTab.modifiedDate
	FROM 
		tblConditionTab_Input _input
	LEFT JOIN 
		tblExceptionTab_Input _exceptionTab
	ON
		_exceptionTab.appId = _input.appId AND
		_exceptionTab.tabName = 'Condition Tab'	
	UNION
	SELECT 
		_input.appId, 'Credit History Tab', _input.policyException, _exceptionTab.input, _exceptionTab.modifiedBy, _exceptionTab.modifiedDate
	FROM 
		tblCreditHistoryTab_Input _input
	LEFT JOIN 
		tblExceptionTab_Input _exceptionTab
	ON
		_exceptionTab.appId = _input.appId AND
		_exceptionTab.tabName = 'Credit History Tab'
	UNION
	SELECT 
		_input.appId, 'Decision Summary Tab', _input.policyException, _exceptionTab.input, _exceptionTab.modifiedBy, _exceptionTab.modifiedDate
	FROM 
		tblDecisionSummaryTab_Input _input
	LEFT JOIN 
		tblExceptionTab_Input _exceptionTab
	ON
		_exceptionTab.appId = _input.appId AND
		_exceptionTab.tabName = 'Decision Summary Tab'
	UNION
	SELECT 
		_input.appId, 'Exit Strategy Tab', _input.policyException, _exceptionTab.input, _exceptionTab.modifiedBy, _exceptionTab.modifiedDate
	FROM 
		tblExitStrategyTab_Input _input
	LEFT JOIN 
		tblExceptionTab_Input _exceptionTab
	ON
		_exceptionTab.appId = _input.appId AND
		_exceptionTab.tabName = 'Exit Strategy Tab'
	UNION
	SELECT 
		_input.appId, 'Income Tab', _input.policyException, _exceptionTab.input, _exceptionTab.modifiedBy, _exceptionTab.modifiedDate
	FROM 
		tblIncomeTab_Input _input
	LEFT JOIN 
		tblExceptionTab_Input _exceptionTab
	ON
		_exceptionTab.appId = _input.appId AND
		_exceptionTab.tabName = 'Income Tab'
	UNION
	SELECT 
		_input.appId, 'Living Expense Tab', _input.policyException, _exceptionTab.input, _exceptionTab.modifiedBy, _exceptionTab.modifiedDate
	FROM 
		tblLivingExpenseTab_Input _input
	LEFT JOIN 
		tblExceptionTab_Input _exceptionTab
	ON
		_exceptionTab.appId = _input.appId AND
		_exceptionTab.tabName = 'Living Expense Tab'
	UNION
	SELECT 
		_input.appId, 'Responsible Lending Tab', _input.policyException, _exceptionTab.input, _exceptionTab.modifiedBy, _exceptionTab.modifiedDate
	FROM 
		tblResponsibleLendingTab_Input _input
	LEFT JOIN 
		tblExceptionTab_Input _exceptionTab
	ON
		_exceptionTab.appId = _input.appId AND
		_exceptionTab.tabName = 'Responsible Lending Tab'
	UNION
	SELECT 
		_input.appId, 'Security Tab', _input.policyException, _exceptionTab.input, _exceptionTab.modifiedBy, _exceptionTab.modifiedDate
	FROM 
		tblSecurityTab_Input _input
	LEFT JOIN 
		tblExceptionTab_Input _exceptionTab
	ON
		_exceptionTab.appId = _input.appId AND
		_exceptionTab.tabName = 'Security Tab'
	UNION
	SELECT
		_input.appId, 'Servicing Tab', _input.policyException, _exceptionTab.input, _exceptionTab.modifiedBy, _exceptionTab.modifiedDate
	FROM 
		tblServicingTab_Input _input
	LEFT JOIN 
		tblExceptionTab_Input _exceptionTab
	ON
		_exceptionTab.appId = _input.appId AND
		_exceptionTab.tabName = 'Servicing Tab'
	UNION
	SELECT 
		_input.appId, 'Transaction Tab', _input.policyException, _exceptionTab.input, _exceptionTab.modifiedBy, _exceptionTab.modifiedDate
	FROM 
		tblTransactionTab_Input _input
	LEFT JOIN 
		tblExceptionTab_Input _exceptionTab
	ON
		_exceptionTab.appId = _input.appId AND
		_exceptionTab.tabName = 'Transaction Tab'
) _Exceptions Left Join
Loanworks_v18..tblUser u on _Exceptions.modifiedBy = u.iUserID

GO

