<?php session_start() ?>
<!DOCTYPE html>
<!--xmlns="http://www.w3.org/1999/xhtml" -->
<html itemscope itemtype="http://schema.org/Article">
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="https://apis.google.com/js/client:platform.js?onload=start" async defer></script> 
    <script type="text/javascript" src="js/scrollEffects.js"> </script>
    <script type="text/javascript" src="js/signInButtonEvent.js"> </script>
    <script type="text/javascript" src="js/menuButtonEvents.js"> </script>
    <link href="css/index.css" rel="stylesheet" />
    <link href="css/styleDownload.css" rel="stylesheet" type="text/css" />
    <link href='http://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'>
    <title></title>
</head>
<body>
    <div class="header">
        <div class="header-inside">
            <div class="header-item header-title">Rat Pack Studios</div>
            <div class="header-item" id="home"><a>Top</a></div>
            <div class="header-item" id="about"><a>About</a></div>
            <div class="header-item" id="preview"><a>Preview</a></div>
            <div class="header-item header-item-signin">
				<img class="inline" src="img/caverat.gif" id="loading">
				<div class="header-signin inline">
					<a>Sign in</a>
				</div>
			</div>
        </div>
        <div class="loginPopup">
            <div id="gSignInWrapper">
                <div id="customBtn" class="customGPlusSignIn">
                    <span class="icon"></span>
                    <!--If time, change data-clientid to php -->
					<span class="g-signin"
                          data-scope="https://www.googleapis.com/auth/plus.login"
                          data-clientid="694270436499-0t218372930j73vc5pfdj0au568c59ds.apps.googleusercontent.com"
                          data-redirecturi="postmessage"
                          data-accesstype="offline"
                          data-cookiepolicy="single_host_origin"
                          data-callback="signInCallback">
                    Sign in with Google
                    </span>
                </div>
           </div>
           <div class="result" style "top: 1vh"></div>
		</div>
    </div>
    <div class="wrapper">
        <div class="section top-section">
            <div class="title-text">
                <div class="title-text-top">
                    Modern Games in a Modern Editor
                </div>
                <br />
                <div class="title-text-breaker"></div>
                <br />
                <div class="title-text-bottom">
                    - Rat Pack Studios -
                </div>
            </div>
        </div>
   <div class="section" id="aboutContent">
             <div class="inside">
                 <div class="content">
                     <h1>About Rat Pack Studios</h1>
                     <div class="about">
                         Rat Pack Stuios will feature a full graphics engine, game networking, social interaction with other players, lobbies for games made by developers, and a touch of awesome.
                     </div>
                     <div class="about-part-list">
						<h3>The Story</h3>
						<img class="about-icon" src="img/origin.png" /><br />
						<span class="about-text">
							Rat pack studios is the result of 3 people at Iowa State University focusing their programming efforts for the coms 309 project class.<br>This project will take 3 months to finish and is off to a fantastic start.
						</span>
					<h3>The Team</h3>
						<img class="about-icon" src="img/team.png" /><br />
						<span class="about-text">
							Rat Pack Studios was created and coded by Kayler Renslow, Zach King, and Wesley Vansteenburg.
						</span>
					<h3>The Software</h3>
						<img class="about-icon" src="img/software.png" /><br />
						<span class="about-text">
							Rat Pack Studios is a modern game editor and creation tool to allow people to easily make 2D board games. So far, the program is in very early stages of development and not much is done.
						</span>
                     </div>
                     <br /><br /><br /><br />
                     <div onclick="pop()" class="download-btn" href="img/download.jpg" download>
                         <span class="dl-left"></span>
                         <span class="dl-right">Download</span>
                     </div>
                     Most Recent Build (v0.01a)
                 </div>
            </div>
        </div>
    </div><!-- END WRAPPER -->


	<?php
		// Create a state token to prevent request forgery.
		// Store it in the session for later validation.
		$state = md5(rand());
		
		$_SESSION['state'] = $state;	
	?>
	<div id="clientState" style="display: none"><?php echo $state ?></div>
</body>
</html>
