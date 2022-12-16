USE [OnePageAssessment]
GO

/****** Object:  UserDefinedFunction [dbo].[bcfn_GetCreditDecision]    Script Date: 6/07/2022 4:01:41 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE FUNCTION [dbo].[bcfn_GetCreditDecision](@iDecisionID INTEGER)
RETURNS VARCHAR(30) 
 
/*
[0][1] CM 22/10/2019 Created - return brand prefixed iAppID for reports to save joins
*/

AS
BEGIN

DECLARE 
	@iAppID INT,
	@iSecurityStatusID INT,
	@iCount1 INT, @iTempStatus VARCHAR(100)	, @cAppliedProgramGuideline VARCHAR(255)
DECLARE @cStatus VARCHAR(30)
  
SET @cStatus = 'Referred'

SELECT   @cAppliedProgramGuideline = LTRIM(RTRIM(ISNULL(cAppliedProgramGuideline, '')))
FROM     Loanworks_v18.dbo.tblAppCreditDecision_PGResults
WHERE    iDecisionRunID = @iDecisionID 

IF @cAppliedProgramGuideline = ''	--[1083]
	RETURN @cStatus

SELECT   @iCount1 = COUNT(1)
FROM     Loanworks_v18.dbo.tblDecisionApprovalHistory
WHERE    [iDecisionID] = @iDecisionID AND UPPER(ISNULL([cCurrentStatus],'')) = 'DECLINED' 

IF @iCount1 > 0 --if the status is Declined
BEGIN
	SET @cStatus = 'Declined'
	--RETURN 
END

IF EXISTS(SELECT 1 FROM Loanworks_v18.dbo.tblAppCreditDecision_PGResultsDetail WHERE iDecisionRunID = @iDecisionID AND cResult = 'Fail') 
	OR EXISTS (SELECT 1 FROM Loanworks_v18.dbo.tblAppCreditDecision_ExternalPGResultsDetail WHERE iDecisionRunID = @iDecisionID AND cResult = 'Referred')		--[2911.4]
	OR NOT EXISTS(SELECT 1 FROM Loanworks_v18.dbo.tblAppCreditDecision_PGResultsDetail WHERE iDecisionRunID = @iDecisionID)  
	
BEGIN
	SET @cStatus = 'Referred'
	--RETURN
END
ELSE 
BEGIN

	SELECT   TOP 1 @iAppID = ISNULL(iAppID,0)
	FROM     Loanworks_v18.dbo.[tblAppCreditDecision_PGResults]
	WHERE    [iDecisionRunID] = @iDecisionID

	SELECT   @iCount1 = COUNT(1)
	FROM     Loanworks_v18.dbo.tblDecisionApprovalHistory
	WHERE    [iDecisionID] = @iDecisionID AND UPPER(ISNULL([cCurrentStatus],'')) IN ('APPROVED','CONDITIONALLY APPROVED') 

	SELECT TOP 1 @iTempStatus = [cCurrentStatus]
	FROM     Loanworks_v18.dbo.tblDecisionApprovalHistory 
	WHERE    [iDecisionID] = @iDecisionID AND UPPER(ISNULL([cCurrentStatus],'')) IN ('APPROVED','CONDITIONALLY APPROVED') 
	ORDER BY iid DESC	
	
	/******************************************************************************
	if we can enter into this block, that means all the PG tests have 'Pass' Status
	******************************************************************************/
	
	
	IF @iCount1 > 0 --we have approved status for this decision run previously	 
		SET @cStatus = @iTempStatus --if we have it previously we keep the latest status
	ELSE	--we dont have approved status for this decision run
		SET @cStatus = 'Pending Approval'		
		

	--[2911.4] - START
	--THIS WILL RETURN EITHER APPROVE OR WITHDRAW BASED ON FINAL STATUS IN tblAppCreditDecision_ExternalPGResults
	
	IF EXISTS(SELECT 1 FROM Loanworks_v18.dbo.tblAppCreditDecision_ExternalPGResults WHERE iDecisionRunID = @iDecisionID AND cFinalDecision = 'Approval To Bid')
		SELECT @cStatus = cFinalDecision FROM Loanworks_v18.dbo.tblAppCreditDecision_ExternalPGResults WHERE iDecisionRunID = @iDecisionID
	--[2911.4] - END
	
END

--[2911.4] - START
--SET @cExternalDecisionStatus = ''
--SELECT @cExternalDecisionStatus = ISNULL(cStatus,'') FROM tblAppCreditDecision_ExternalPGResults WHERE iDecisionRunID = @iDecisionID 

--OVERWRITE STATUS TO DECLINED IF THERE IS EXTERNAL DECISIONING RESULT = DECLINED
IF EXISTS (SELECT 1 FROM Loanworks_v18.dbo.tblAppCreditDecision_ExternalPGResultsDetail WHERE iDecisionRunID = @iDecisionID AND cResult = 'Declined')
	SET @cStatus = 'Declined'

--[2911.4] - END

RETURN @cStatus
END

 

GO

