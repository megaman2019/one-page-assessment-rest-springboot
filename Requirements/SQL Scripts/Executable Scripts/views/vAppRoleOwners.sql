USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vAppRoleOwners]    Script Date: 6/07/2022 12:54:19 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[vAppRoleOwners] AS 

/* 
	Role: Application Roles 
*/
SELECT 
	_RoleOwners.ID,
	_RoleOwners.iAppID,	
	_ApplicationRoles.cRoleName,
	_RoleOwners.cRoleOwner
FROM 
(
	SELECT 
		_RolesAppOwners.ID, _RolesAppOwners.iAppID, _RolesAppOwners.iRoleID, ISNULL(tblUser.cFirstname, '') + ' ' + ISNULL(tblUser.cSurname, '') AS cRoleOwner
	FROM
		Loanworks_v18..tblRolesAppOwners _RolesAppOwners
	LEFT JOIN
		Loanworks_v18..tblUser
	ON
		tblUser.iUserID = _RolesAppOwners.iUserID

) _RoleOwners

LEFT JOIN 

(
	SELECT  tblRM.cRoleName, tblRM.iRoleID
	FROM Loanworks_v18..tblRolesManagementAvailable AS tblRMA
	INNER JOIN Loanworks_v18..tblRolesManagement AS tblRM
		ON tblRMA.iRoleAvailableID = tblRM.iRoleAvailableID
	WHERE tblRMA.cRoleAvailableName = 'Application' AND
		tblRM.iActive = 1 AND
		tblRM.iAllowedToDelete <> 0
) _ApplicationRoles
ON
	_ApplicationRoles.iRoleID = _RoleOwners.iRoleID
GO

