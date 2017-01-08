<?php
	session_start();
	
	//Kayler, please do not modify. If you have issues signing in, let me know
	//because it could cause a larger error later
	if(!isset($_SESSION['access_token']) || isset($_SESSION['logout'])){
		header('Location: /index.php');
	}
?>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Rat Pack Studios - Game Lobbies</title>
	<link href="jquery_alert_files/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="css/index.css" rel="stylesheet" />
	<link href="css/mainPage.css" rel="stylesheet" />
    <link href='http://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="https://apis.google.com/js/client:platform.js?onload=start" async defer></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
	<!--<script type="text/javascript" src="jQuery.js"></script>-->
	<script type="text/javascript" src="jquery_alert_files/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="jquery_alert_files/jquery.alerts.js"></script>
	<script type="text/javascript" src="js/signOutEvent.js"></script>
    <script type="text/javascript" src="js/mainPage.js"></script>
    <!--This script is used to get their Google+ ID and to alert the user of an error if one occurred on game.php-->
	<script>
		var userGPID = '<?php echo $_SESSION['gpid']?>';
		
		<?php 
		if(isset($_REQUEST['toThisPage']))
		{
			$jsAlert = '$(document).ready(function() { jAlert("';
			if($_REQUEST['toThisPage'] == 1)
				$jsAlert .= 'The <i><strong>Game ID</strong></i> was not sent successfully.';
			
			else if($_REQUEST['toThisPage'] == 2)
				$jsAlert .= 'The <i><strong>Room Name</strong></i> was not sent successfully.';

			else if($_REQUEST['toThisPage'] == 3)
				$jsAlert .= 'I couldn\'t find the <i><strong>Game ID</strong></i>.';
			
			else if($_REQUEST['toThisPage'] == 4)
				$jsAlert .= 'I couldn\'t connect to that game. <i>My bad!</i> Please try again.';

			else if($_REQUEST['toThisPage'] == 5)
				$jsAlert=''; // No error to report. The user left on their own volition
			
			else if($_REQUEST['toThisPage'] == 6)
				$jsAlert .= 'The <strong>MasterClient</strong> left the game for unknown reasons.\nThe game has been saved and you cannot reconnect when the MasterClient does.';
			else if($_REQUEST['toThisPage'] == 7)
				$jsAlert .= 'The <strong>MasterClient</strong> closed the game.<br>Thanks for playing!';
			else
				$jsAlert .= 'You were removed from the game for unknown reasons. <i>Good luck with that!</i>';
			
			$jsAlert .= '", "Game.php Returned With an Error");});';
			echo $jsAlert; 
		}
		?>
		
	</script>
</head>
<body>
    <div class="wrapper">
        <div class="header">
            <div class="header-inside">
                <div class="header-item header-title">Rat Pack Studios</div>
                <div class="header-item" id="search">
					<input class="search-bar search-combo" id='searchBox' type="text" />
                    <button class="search-button search-combo" onclick="isSearching = true; search();">Search</button>
				</div>
				<div class="header-item header-item-hangout">
                    <div id="placeholder-div1"></div>
                </div>
                <div class="header-item" id="uploadToken"></div>
            </div>
        </div>
        <div class="half half-left">
            <div class="menu-bar">
                <div class="menu-bar-item" onclick="isSearching = false;">
                    Home
                </div>
                <div class="menu-bar-item" id="hostGame">
                    Host a Game
                </div>
                <div class="menu-bar-item" onclick="getKey();">
                    Get Upload Key <a onclick="jAlert('Upload keys are used for developers to upload their games to the website.', 'Notification');">(?)</a>
                </div>
                <div class="menu-bar-item" onclick="window.location.href='http://rpstudios.k-town.ws/Rat%20Pack%20Studios.jar'">
                    Download Editor <a onclick="jAlert('Most Recent Build (v1.05)<br>Requires Java 8 or higher.', 'Notification');">(?)</a>
                </div>
                <div class="menu-bar-item" onclick="signOut();">
                    Sign Out
                </div>
                <div class="menu-bar-item revokeAccess" onclick="revoke();">
                    Revoke Google Access
                </div>
            </div>
            <div class="host-game">
                <div class="host-game-title">
                    <div>
                        Host a Game<span id="closeHostGame">X</span>
                    </div>
                </div>
				<div class="host-game-content">
                    <div class="host-game-sep">
						<div class="big-font">Your Games</div>
						<div class="my-games">
							<!--<div class="my-game">No Games</div>-->
						</div>
					</div>
                    <div class="host-game-sep">
                        <div class="big-font">Create with GameID</div>
						<div class="create-game">
                            <input class="search-combo gameid-text" type="text" />
                            <button class="search-combo host-game-create" onclick="create($('.gameid-text').val());">Create</button>
						</div>
					</div>
				</div>
			</div>
        </div>
		<div class="half half-right" id="boxes"><!--boxes go here--></div>
    </div><!-- END WRAPPER -->
	<br>
	<div id="clientState" style="display: none"><?php echo $_SESSION['state'] ?></div>
</body>
</html>
