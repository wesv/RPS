function signOut()
{
	send('signout');
}

function revoke()
{
	send('revoke');
}

function send(request)
{
	if(request != 'signout' && request != 'revoke'){
		console.log("Invalid parameter. Must be: 'signout' or 'revoke'");
		return;
	}
	
	var stateKey = $("#clientState").text();

	$.ajax ({
		type: "POST",
		url: "/plus.php?" + request,
		success: function(result) {
			//Verify Server Response for Log Out
			if(result == (request+'=yes')) {
				console.log("Signing out");
				gapi.auth.signOut();
				window.location.href="index.php";
			}
			else
				console.log("Well, that didn't work : " + result);
		},
		error: function(e, f, d) {
			//FIX error message
			console.log("this is worse than I thought");
		},
		data: {'state' : stateKey}
	});
}