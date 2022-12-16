USE [OnePageAssessment]
GO

/****** Object:  View [dbo].[vAppDLAProfile]    Script Date: 6/07/2022 12:52:26 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[vAppDLAProfile] AS

SELECT DISTINCT
	_AppMain.iAppID,	
	_Funder.cFinancier,
	_DLAProfile.cProfileName,
	_DLAProfile.fApprovalLimit,
	_DLAProfile.cFirstName,
	_DLAProfile.cSurname	 
FROM 
	Loanworks_v18..tblAppMain _AppMain
LEFT JOIN
	Loanworks_v18..tblAppProduct _AppProduct
ON
	_AppMain.iAppID = _AppProduct.iAppID
LEFT JOIN
	Loanworks_v18..tblProduct _Product
ON
	_Product.iProdID = _AppProduct.iProdID
LEFT JOIN
	Loanworks_v18..tblFinancier _Funder
ON
	_Funder.iFinancierID = _Product.iFinancier
INNER JOIN
	(
		SELECT 
			'Multiple Funder DLAs' as cType,
			f.cFinancier, 
			dla.cProfileName, 
			dla.iDLAProfileID, 
			dla.fApprovalLimit, 
			u.cFirstName, 
			u.cSurname, 
			u.cPosition, 
			u.iUserID
		FROM
			Loanworks_v18..[bctblUserDLAProfiles] mdla
		INNER JOIN
			Loanworks_v18..tblDLAProfiles dla 
		ON 
			mdla.iDLAProfileID = dla.iDLAProfileID 
		INNER JOIN
			Loanworks_v18..tblUser u 
		ON 
			mdla.iUserID = u.iUserID 
		INNER JOIN
			Loanworks_v18..tblFinancier f 
		ON 
			mdla.iFunderID = f.iFinancierID
		UNION ALL
		SELECT 
			'Single Funder DLA' as cType, 
			CASE 
				WHEN dla.cProfileName like 'BNK%' THEN
					'BNK Banking Corporation Limited' 
			ELSE 
				'Golden Eagle Mortgages Pty Ltd' 
			END AS cFinancier, 
			dla.cProfileName, 
			dla.iDLAProfileID, 
			dla.fApprovalLimit, 
			u.cFirstName, 
			u.cSurname, 
			u.cPosition, 
			u.iUserID
		FROM 
			Loanworks_v18..tblUser u
		INNER JOIN
			Loanworks_v18..tblDLAProfiles dla 
		ON 
			u.iDLAProfileID = dla.iDLAProfileID
		WHERE 
			u.iDLAProfileID is not null AND
			u.iDLAProfileID <> 17 -- Not Multiple DLA setting
	) _DLAProfile
ON
	_DLAProfile.iUserID = _AppMain.iAppOwner AND _DLAProfile.cFinancier = _Funder.cFinancier



GO

