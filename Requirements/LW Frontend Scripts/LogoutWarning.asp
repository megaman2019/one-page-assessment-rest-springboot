<%@ language=VBScript%>.
<% '[1][974.15] Wayne Macartney 19 October 2009 
'New look and feel
'[2][2548]JPN 02/04/2018 Internal - Look and Feel
'[3][2548] CJL 27/06/2017 - Internal - LoanworksV10R9 rebooted
'                         - made Footer visible on screen
'                         - Change doctype to HTML5

%>
<!DOCTYPE HTML><%'[2548] %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>LoanWorks | Log Out</title>
    <!-- #include file="incStylesheet.asp" -->
</head>
<body>  
	<iframe style="display:none;" src="https://assessment.betterchoice.com.au/getlocalstorage" id="ifr"></iframe>
    <div class="login-box">
        <div class="login-logo">
             <img height="120" width="360" src="Branding_Image/BetterChoice_Logo.jpg" alt="" />
        </div>
        <div class="login-box-body">
            <h3 class="login-box-msg">Log Out</h3>
            <p>If you wish to log out, click OK.</p>
            <p>Click Cancel to return.</p>
            <br/>
            <form method="post" action="Default.asp" id="form1" name="form1" class="form-element">
                <input type="hidden" id="hcUserID" name="hcUserID"/>
                <input type="hidden" id="hcPassword" name="hcPassword"/>

                <div>
                    <input class="btn btn-yellow" type="submit" onclick="SubmitForm();" value="OK" />
                    <input class="btn" type="submit"onclick="javascript: back();" value="Cancel" />
                </div>
            </form>
        </div>
    </div>

    <div class="footer-container">
        <!-- #include file="incFooter.asp" -->
    </div>
<script type="text/javascript">
<!--
	function back() {
		window.history.back();
	}
	
	
	function SubmitForm(){
		//remove OPA token		
		window.localStorage.removeItem("assessment-token");
		postCrossDomainMessage(""); //send empty token to OPA domain
	    document.location.href = 'Logout.asp';
	}
	
	function postCrossDomainMessage(msg) {
		var win = document.getElementById("ifr").contentWindow;
		win.postMessage(msg, "https://assessment.betterchoice.com.au/");	
	}
-->	
</script>
</body>
</html>


