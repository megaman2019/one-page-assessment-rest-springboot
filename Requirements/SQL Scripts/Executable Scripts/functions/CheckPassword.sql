USE [OnePageAssessment]
GO

/****** Object:  UserDefinedFunction [dbo].[CheckPassword]    Script Date: 6/07/2022 4:02:18 PM ******/
SET ANSI_NULLS OFF
GO

SET QUOTED_IDENTIFIER OFF
GO

CREATE FUNCTION [dbo].[CheckPassword](@password [nvarchar](4000), @hashed [nvarchar](4000))
RETURNS [bit] WITH EXECUTE AS CALLER
AS 
EXTERNAL NAME [BCrypt].[BCryptPackage.UserDefinedFunctions].[CheckPassword]
GO

EXEC sys.sp_addextendedproperty @name=N'AutoDeployed', @value=N'yes' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'FUNCTION',@level1name=N'CheckPassword'
GO

EXEC sys.sp_addextendedproperty @name=N'SqlAssemblyFile', @value=N'BCryptAssembly' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'FUNCTION',@level1name=N'CheckPassword'
GO

EXEC sys.sp_addextendedproperty @name=N'SqlAssemblyFileLine', @value=820 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'FUNCTION',@level1name=N'CheckPassword'
GO

