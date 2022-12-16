USE Loanworks_v18
GO 

CREATE VIEW [dbo].[v_OPA_User]

AS 

SELECT 
	_user.iUserID,
	LOWER(_user.cNTUserID) cNTUserID,
	_userPassword.cPassword cEncryptedPassword,
	--CONVERT(varchar(MAX),DecryptByCert(Cert_ID('LWCertificate'),_userPassword.cPassword ) ) AS cPassword,
	dbo.Bcrypt(CONVERT(varchar(MAX),DecryptByCert(Cert_ID('LWCertificate'),_userPassword.cPassword )),10) AS cPassword,
	'USER' cRole
FROM 
	Loanworks_v18..tblUser _user
LEFT JOIN
	Loanworks_v18..tblUserPassword _userPassword
ON 
	_user.iUserID = _userPassword.iUserID
WHERE 
	CONVERT(varchar(MAX),DecryptByCert(Cert_ID('LWCertificate'),_userPassword.cPassword )) IS NOT NULL
	AND ISNULL(_user.cNTUserID, '') != ''
UNION
SELECT 
	-999,
	'user',
	'',
	dbo.Bcrypt(CONVERT(varchar(MAX),'user'),10) AS cPassword,
	'USER'
