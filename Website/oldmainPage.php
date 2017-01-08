<?php
	session_start();
	
	if(!isset($_SESSION['access_token']))
		header('Location: /index.php');
	
?>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="https://apis.google.com/js/client:platform.js?onload=start" async defer></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
                    
	<script type="text/javascript" src="js/signOutEvent.js"> </script>
	<link href="css/index.css" rel="stylesheet" />
	<link href="css/mainPage.css" rel="stylesheet" />
    <link href='http://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'>
    <title></title>
</head>
<body>
    <div class="wrapper">
        <div class="header">
            <div class="header-inside">
                <div class="header-item header-title">Rat Pack Studios</div>
                <!--<div class="header-item" id="home"><a>Home</a></div>-->
                <!--<div class="header-item" id="dl"><a>Download</a></div>-->
                <div class="header-item" id="search">
					<input class="search-bar search-combo" type="search" />
					<button class="search-button search-combo">Search</button>
				</div>
				<div class="header-item header-item-hangout">
                    <div id="placeholder-div1"></div>
                    <script>
					$(document).ready(function load() {
                        gapi.hangout.render('placeholder-div1', {
                            'render': 'createhangout',
                            'initial_apps': [{ 'app_id': 'vast-math-861', 'start_data': 'dQw4w9WgXcQ', 'app_type': 'ROOM_APP' }]
						}}));
                    </script>
                </div>
            </div>
        </div>

        <div class="half half-left">
            <div class="menu-bar">
                <div class="menu-bar-item">
                    Home
                </div>
                <div class="menu-bar-item">
                    My Games
                </div>
                <div class="menu-bar-item">
                    Host a Game
                </div>
                <div class="menu-bar-item">
                    Download Editor
                </div>
                <div class="menu-bar-item" onclick="signOut();">
                    Sign Out
                </div>
            </div>
        </div>
        <div class="half half-right">
		<!-- Change onclick to a js function, and have it call a php to set the specific game id. The php will make sure random game name dne-->
            <div class="box" id="box0" onclick="window.location='game.php?specificGameID=3';">
				<h4 class="box-title">Test Game</h4>
				<div class="box-inner">
					<div class="box-description">
						<img class="box-img" src="test-game-icon.png" />
						Diggity giggity miggity dog.
					</div>
					<div class="box-bottom">0/1 players</div>
				</div>
			</div>
			<div class="box" id="box0g">
				<h4 class="box-title">Yo Diggity Dog</h4>
				<div class="box-inner">
					<div class="box-description">
						<img class="box-img" src="test-game-icon.png" />
						Diggity giggity miggity dog.
					</div>
					<div class="box-bottom">0/1 players</div>
				</div>
			</div>
			<div class="box" id="box0r">
				<h4 class="box-title">Yo Diggity Dog</h4>
				<div class="box-inner">
					<div class="box-description">
						<img class="box-img" src="test-game-icon.png" />
						Diggity giggity miggity dog.
					</div>
					<div class="box-bottom">0/1 players</div>
				</div>
			</div>
			<div class="box" id="box320">
				<h4 class="box-title">Yo Diggity Dog</h4>
				<div class="box-inner">
					<div class="box-description">
						<img class="box-img" src="test-game-icon.png" />
						Diggity giggity miggity dog.
					</div>
					<div class="box-bottom">0/1 players</div>
				</div>
			</div>
			<div class="box" id="box045678">
				<h4 class="box-title">Yo Diggity Dog</h4>
				<div class="box-inner">
					<div class="box-description">
						<img class="box-img" src="test-game-icon.png" />
						Diggity giggity miggity dog.
					</div>
					<div class="box-bottom">0/1 players</div>
				</div>
			</div>
			<div class="box" id="box044">
				<h4 class="box-title">Yo Diggity Dog</h4>
				<div class="box-inner">
					<div class="box-description">
						<img class="box-img" src="test-game-icon.png" />
						Diggity giggity miggity dog.
					</div>
					<div class="box-bottom">0/1 players</div>
				</div>
			</div>
			<div class="box" id="box0111">
				<h4 class="box-title">Yo Diggity Dog</h4>
				<div class="box-inner">
					<div class="box-description">
						<img class="box-img" src="test-game-icon.png" />
						Diggity giggity miggity dog.
					</div>
					<div class="box-bottom">0/1 players</div>
				</div>
			</div>
		</div>
	
    </div><!-- END WRAPPER -->
	<br>
	<div id="clientState" style="display: none"><?php echo $_SESSION['state'] ?></div>
</body>
</html>
