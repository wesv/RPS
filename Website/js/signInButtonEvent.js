//The opacity of the gif when signing in
var opacity = 0;

//Stop the fade in animation of the gif
var stopAnimate = false;

//If the List of sign in options is visible
var isVisible = false;

//This function is called after the user verifies website use.
function signInCallback(authResult)
{
	if(authResult['code'])
    {
		loginAnimation();
		
		//Change the text to show user the server is trying to connect them.
		$(".header-signin a").html("Signing In");
		var currentWidth = $(".header-item-signin").width(); // store the width till later, if needed.
		$(".header-item-signin").css("width","130px");
		
		var stateKey = $("#clientState").text();// Get the Client ID
		
		$.ajax({
            type: "POST",
            url: "/plus.php?storeToken",
			success: function (result) {
				if(result.indexOf("signin=yes") > -1) {
					//gapi.auth.signOut();
					// ****** TEMP TEST. FIX WHEN FINISHED ***
					
					window.location.href = "mainPage.php";
				}
				else
					failedToSignIn(result, currentWidth);
            },
			error: function(error, textError, errorThrown) {
				failedToSignIn(error['responseText'], currentWidth);
			},	
            data:{
				state: stateKey,
				code: authResult['code']
			}
        });
    }
    else if(authResult['error'])
    {
        console.log("Error: " + authResult['error']);
    }
	
}

function failedToSignIn(errorResponseText, currentWidth)
{
	console.log(errorResponseText);
	stopAnimate = true;
	$("#loading").css("opacity", 0);
	opacity = 0;
	$(".header-signin a").html("Sign In");
	$(".header-item-signin").css("width",currentWidth);
	$(".result").html("<br><strong>Error: Cannot connect<br>to server</strong>").css("color", "#CC0000");
	clickLogin(true);
}


function loginAnimation()
{
	//Animate the Loading Image
	var t = setInterval(function(){
		if(opacity >= 1.0 || stopAnimate){
			clearInterval(t);
			return;
		}
		$("#loading").css("opacity", opacity);
		opacity+=.05;
	},
	
	10); // How long to wait between each interval
}

function clickLogin(signInClicked)
{
    
    if (!isVisible && signInClicked) {
        $(".loginPopup").slideDown(500);
        isVisible = true;
    }
    else if(isVisible && !signInClicked){
		$(".loginPopup").slideUp(500);
        isVisible = false;
		$(".result").html("");
    }
}

$(document).ready(function () {
    $(".wrapper").click(function (e) {
        clickLogin(false)
    });

    $(".header-item-signin").click(function (e) {
        clickLogin(true)
    });
});
