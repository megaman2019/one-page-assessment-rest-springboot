<%@  language="VBScript" %>
<%
'[1] Wayne Macartney 16/08/2001
'Logon form added
'[2] Wayne Macartney 27/08/2001
' Page layout reformatted
'[3][974.15][802] RC 6/11/2009 - New Look and Feel
'[4][1044]EY 22/06/2011 add try and catch to .focus to avoid javascript error 
'[5][2548]JPN 02/04/2018 Internal - Look and Feel

Response.Buffer =true

Dim SESSION_CLOSED_BY_TIMEOUT

SESSION_CLOSED_BY_TIMEOUT=1

Call Main

Sub Main()

	Session.Abandon ()
	Call CloseUserSessionByTimeOut() 'update tblUserSessionLog and tblUserSession
	
End Sub

Function CloseUserSessionByTimeOut()
	dim sConn
	dim oConn	
	Dim oSecurity
	Dim cSql
	Dim iUserSessionID
	
	iUserSessionID=Session.SessionID 
		
	Set oSecurity = Server.CreateObject("lmsSecurity.Constants")	
	sConn  = oSecurity.GetValue (UserID, "CONNECTION_STRING",Application("Loanworks_constantsPath"))

	Set oConn = Server.CreateObject("ADODB.Connection")      
	oConn.Open sConn

	cSql="sp_UserSessionLogUpdateSessionEnd " & iUserSessionID & ", " & SESSION_CLOSED_BY_TIMEOUT

	oConn.Execute (cSql)
	
	'Cleaning
	set oConn=nothing
	set oSecurity = nothing

End Function

'[1]
%>
<!-- [802]-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>LoanWorks | Session Timeout</title>
    <!-- #include file="incStylesheet.asp" --> 
</head>

<body>
	<iframe style="display:none;" src="https://assessment.betterchoice.com.au/getlocalstorage" id="ifr"></iframe>
    <div class="login-box">
        <div class="login-logo">
             <img height="150" width="150" src="images/logo.gif" alt="" />
        </div>
        <div class="login-box-body">
            <h3 class="login-box-msg">Session Timeout</h3>
            <p>Your connection to LoanWorks has been<br /> closed automatically because no action has
                <br />been taken for the past <%=Session.Timeout%> minutes.
            </p>
            <br/>
            <form method="post" action="duo.asp" id="form1" name="form1" class="form-element">
                <input type="hidden" id="hcUserID" name="hcUserID"/>
                <input type="hidden" id="hcPassword" name="hcPassword"/>

                <div class="form-group">
                    <input id="cUserID" name="cUserID" value="" class="form-control" placeholder="Username" />
                </div>
                <div class="form-group">
                    <input id="Password1" name="cPassword" type="password" value="" class="form-control" placeholder="Password"/>
                </div>
                <div class="login-body-row">
                    
                </div>
                <div>
                    <input class="btn btn-blue" type="submit" onclick="SubmitForm();" value="Login" />
                </div>
            </form>
        </div>
    </div>
</body>
</html>

<script language="Javascript">


	//$('#ifr').on('load', function() {
		//put your code here, so that those code can be make sure to be run after the frame loaded
		//console.log("Message sent")
	//});
	
	document.getElementById('ifr').onload = function(){
		//remove OPA token		
		window.localStorage.removeItem("assessment-token");
		postCrossDomainMessage(""); //send empty token to OPA domain
	};
	
	//[1044] 
	 try {
	    document.getElementById("cUserID").focus();
	    document.getElementById("cUserID").select();	
	 }
     catch (e) { }
	

	function SubmitForm() {

		// Pass username and password for OPA authentication
		initTokenFromAssessmentApp(form1.cUserID.value, form1.cPassword.value);
		
		form1.hcUserID.value =form1.cUserID.value 
		form1.hcPassword.value =form1.cPassword.value 
		form1.cPassword.value =""
		form1.cUserID.value =""

		form1.submit ();
	}

	function checkEnter(evtObj)
	{
	  if(navigator.appName == 'Netscape') {
		if(evtObj.which == 13) return SubmitForm();
	  } else {
		if(event.keyCode == 13) return SubmitForm();
	  }
	}
	
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

