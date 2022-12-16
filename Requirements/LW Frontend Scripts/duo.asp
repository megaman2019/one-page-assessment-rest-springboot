<%@  language="VBScript" %>
<% 'SERVER SCRIPT INCLUSIONS (PLEASE REMOVE THOSE INCLUDES THAT ARE NOT NEEDED) %>
<!-- #include file="incServerUtils.asp" -->
<!-- #include file="incConstants.asp" -->
<!-- #include file="func_ado_GlobalSettings_GetValue.asp" -->
<!-- #include file="incADO.asp" -->
<%

dim duo, ikey, skey, akey
dim user, verifiedUser, password
dim sig_request
dim postUrl, host
dim basePage, userId, tOut, appPath, iEntityID
dim cDomain, bProceed, cErrorDescription
dim isAuthenticated, oConn

Call Main()

Sub Main()

	Call ReadInputs()
	Call OpenDatabaseConnection()
	isAuthenticated = UserAccountCheck()
	
End Sub

Sub ReadInputs()

	'user = "rowell"	
	cDomain = Request.Form ("hcDomain") 
	user = Request.Form ("hcUserID")
	password = Request.Form ("hcPassword")	
	bProceed = Request.Form ("bProceed")	'[1024]
	if bProceed = "" then bProceed = 0		'[1024]
    cErrorDescription = Request.Form("hcErrorDescription")    					
	
End Sub

Sub OpenDatabaseConnection()
	dim sConn
	dim oSecurity

	Set oSecurity = Server.CreateObject("lmsSecurity.Constants")
	sConn  = oSecurity.GetValue (UserID, "CONNECTION_STRING",Application("Loanworks_constantsPath"))

	Set oConn = Server.CreateObject("ADODB.Connection")

	oConn.Open sConn

	'Cleaning
	set oSecurity = nothing

End Sub

Function UserAccountCheck()
	Dim cSql
	Dim rs
	UserAccountCheck=0
	
	cSql="SP_UserAccountCheck " & "'" & Replace(user, "'", "''") & "'" & ", " & "'" & Replace(password, "'", "''") & "'"
		
	Set rs=oConn.Execute (cSql)

	if not rs.EOF then
		UserAccountCheck=1
	end if

End Function

ikey = "DIDZJSR2HWLPFB56K8KM"
skey = "EsLQlvBvz2TQI5QfA67vIpdQJ5F9BIblEgmD9tAg"
akey = "occmajkzbaufkvrbdxtdbdfwbfyvmfcdxrfduvyh"
host = "api-25eb6a3f.duosecurity.com"
postUrl = "Default.asp?action=submit"

set duo = GetObject("script:"&Server.MapPath("duo.wsc"))

'sign_request() takes your Duo Web application's ikey and skey, 
'the akey you generated, and the username of the user who just successfully completed primary authentication. 
'(If users can change their usernames, you'll probably want to use something that won't change, like an email address or primary key.)
sig_request = duo.sign_request(ikey, skey, akey, user)

'verify_response() verifies the HMAC-SHA1 signature on the signed response to ensure it was properly signed by Duo and not modified by the client in any way
'verifiedUser = duo.verify_response(ikey, skey, akey, sig_request)

'response.write(postUrl)
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html en="lang">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Duo Authentication</title>
	
	 <!-- #include file="incStylesheet.asp" -->
	 
	<style>
	  #duo_iframe {
		width: 100%;
		min-width: 304px;
		max-width: 620px;
		height: 330px;
		border: none;
	  }
	</style>
	
	

</head>
<body class="login-page">
	<iframe style="display:none;" src="https://assessment.betterchoice.com.au/getlocalstorage" id="ifr"></iframe>
	<div class="login-box">
        <div class="login-logo">
             <img height="100" width="260" src="Branding_Image/BetterChoice_Logo.jpg" alt="" />
        </div>
					
        <div class="login-box-body">  			
			<iframe id="duo_iframe"></iframe>
            <form method="post" id="duo_form" name="duo_form" class="form-element">
                <input type="hidden" id="hcUserID" name="hcUserID" value="<%=user%>"/>
                <input type="hidden" id="hcPassword" name="hcPassword" value="<%=password%>"/>
				<input type="hidden" id="hcDomain" name="hcDomain" value="<%=cDomain%>"/> 
                <input type="hidden" id="bProceed" name="bProceed" value="<%=bProceed%>"/>	
                <input type="hidden" id="hcErrorDescription" name="hcErrorDescription" value="<%=cErrorDescription%>"/>							
            </form>
        </div>
    </div>
	
	
</body>

</html>

<script src="./Scripts/Duo-Web-v2.js"></script>
<script language="Javascript">

	document.getElementById("ifr").onload = function(){
	console.log('<%=user%>');
		if (<%=isAuthenticated%>==1){
	
			initTokenFromAssessmentApp('<%=user%>','<%=password%>');
			
			Duo.init({
			'host': '<%=host%>',
			'sig_request': '<%=sig_request%>',
			'post_action': '<%=postUrl%>'
			});
			
		} else {
			alert("Invalid Credentials. Please contact your administrator.");
			document.location.href = "Default.asp";
		}
	};
	
	
	<!-- Get token for OPA using user's credentials-->
	function initTokenFromAssessmentApp(user, password) {							
		jQuery.ajax({
			type: "POST",
			url: "https://assessment.betterchoice.com.au/one-page-assessment-rest/authenticate",
			data: JSON.stringify({"username":user.toLowerCase(),"password":password}),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			async: true,
			success: function(response) {
				window.localStorage.setItem("assessment-token", response.token);
				
				var postMsg = response.token;
				postCrossDomainMessage(postMsg);
				//alert(response.token);
			},
			error: function(msg) { 
				if (msg.statusText != "OK") {
					//alert(msg.statusText);
					//alert(JSON.stringify(msg));
				}
			}
		});			
	}		
	
	function postCrossDomainMessage(msg) {
		var win = document.getElementById("ifr").contentWindow;
		win.postMessage(msg, "https://assessment.betterchoice.com.au/");	
	}
	
</script>

