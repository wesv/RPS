<?php session_start() ?>
<!DOCTYPE html>
<!--xmlns="http://www.w3.org/1999/xhtml" -->
<html itemscope itemtype="http://schema.org/Article">
<head>
    <title>Rat Pack Studios</title>
	<link rel="shortcut icon" href="http://rpstudios.k-town.ws/img/favicon.ico" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="https://apis.google.com/js/client:platform.js?onload=start" async defer></script> 
    <script type="text/javascript" src="js/scrollEffects.js"> </script>
    <script type="text/javascript" src="js/signInButtonEvent.js"> </script>
    <script type="text/javascript" src="js/menuButtonEvents.js"> </script>
    <link href="css/index.css" rel="stylesheet" />
    <link href="css/styleDownload.css" rel="stylesheet" type="text/css" />
    <link href='http://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'>
</head>
<body>
    <div class="header">
        <div class="header-inside">
            <div class="header-item header-title">Rat Pack Studios</div>
            <div class="header-item" id="home"><a>Top</a></div>
            <div class="header-item" id="about"><a>About</a></div>
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
                          data-callback="signInCallback"
						  data-approvalprompt=force>
                    Sign in with Google
                    </span>
                </div>
           </div>
           <div class="result" style="top: 1vh"></div>
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
                        Rat Pack Studios will feature a full graphics engine, game networking, social interaction with other players, lobbies for games made by developers, and a touch of awesome.
                    </div>
                    <div class="about-part-list">
	                <h3>The Story</h3>
	                <img class="about-icon" src="img/origin.png" /><br />
	                <span class="about-text">
		                Rat pack studios is the result of 3 people at Iowa State University focusing their programming efforts for 3 months on a single project. 
		                <br>The project was developed for the class Computer Science 309 (Software Development Practices) from January 28 - April 28 2015.
		                The project took 3 months to finish and is comprised of <span style="color:red;"><b>over 9,000</b></span> lines of code across 3 programming languages (JavaScript, Java, and PHP). The project also has remnants of HTML (obviously), CSS, and MYSQL which is another ~3,000 lines of code.<br>
		                The project uses libraries Apache Commons, Google OAuth 2.0, and RSyntaxTextArea.
	                </span>
                    <h3 onclick="alert('RAWR I\'m a dinosaur')">The Team</h3>
	                <img class="about-icon" src="img/team.png" /><br />
	                <span class="about-text">
		                Rat Pack Studios was created and coded by <a href="http://k-town.ws" target="_blank">Kayler Renslow</a>, <a href="https://www.linkedin.com/pub/zachary-king/81/32/478" target="_blank">Zach King</a>, and <a href="https://www.linkedin.com/pub/wesley-vansteenburg/80/343/b9" target="_blank">Wesley Vansteenburg</a>. When this project was conceived and finished, we were all Juniors at Iowa State University. Zach and Kayler are majoring in Computer Science while Wesley is majoring in Software Engineering.<br>
		                
	                </span>
                    <h3>The Software</h3>
	                <img class="about-icon" src="img/software.png" /><br />
	                <span class="about-text">
		                Rat Pack Studios is a modern game editor and creation tool to allow people to easily make 2D board games.<br>
		                The editor has a full fledged JavaScript editor and saving functionality to make sure the games you made are with you forever.
		                The website has Google+ signin to handle users and a Google Hangouts chat system.
		                Once games have been created, the developer can upload straight to the database once they grabbed their upload token from the website. Once games are uploaded, users can launch their games and play with their friends.<br>
		                <br><b>Executable Feature List</b>
		                <ul>
		                <li>Java to PHP server connection handling</li>
		                <li>Java FTP to server handling</li>
		                <li>JavaScript code writer</li>
		                <li>Full fledged script editor</li>
		                <li>Saving functionality</li>
		                <li>Drag and Drop features (i.e. drag in a photo into the executable and it will auto load it for you)</li>
		                <li>Open source JavaScript engine</li>
		                <li>Game preview with support for locally hosted servers</li>
		                <li>Exporting of the game and uploading to the server under your Google+ account</li>
		                </ul>
		                <b>Server Feature List</b>
		                <ul>
		                <li>Multiplayer support for turn based games</li>
		                <li>Completely custom and original Multiplayer system with "Heart Beat" style implementations</li>
		                <li>Lobby/Session Hosting</li>
		                <li>Saving functionality for games so you can resume them later.</li>
		                <li>"Master Client" which is a fancy word for the player that controls the saving of data and closing the session</li>
		                <li>Google+ Signin</li>
		                <li>Dinosaurs</li>
		                </ul>
	                </span>
                    </div>
                    <br />
                    <div onclick="pop()" class="download-btn">
                        <span class="dl-left"></span>
                        <span class="dl-right" onclick="window.location.href='http://rpstudios.k-town.ws/Rat%20Pack%20Studios.jar'">Download</span>
                    </div>
                    Most Recent Build (v1.05)<br>Requires Java 8 or higher.
                </div>
            </div>
        </div>
        <!--<div class="section" id="footer"></div>-->
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
