<?php 
	session_start();

	//Throws Error 2: Could not load room
	if(!isset($_GET['roomID']) || !($_GET['roomID']))
		header('Location: /mainPage.php?toThisPage=2');
	
	//Throws error 1: Could not find game 
	if(!isset($_GET['game']) || !($_GET['game']))
		header('Location: /mainPage.php?toThisPage=1');
	
	if(isset($_SESSION['logout']))
		header('Location: /index.php');
	
?>

<!-- WHEN DONE, THIS CODE GOES INTO EVERY GAME FOLDER -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="_head">
    <title id="_title">Untitled</title>
	<link href="../jquery_alert_files/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="https://apis.google.com/js/client:platform.js?onload=start" async defer></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
	<script type="text/javascript" src="../jquery_alert_files/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="../jquery_alert_files/jquery.alerts.js"></script>
	<link href="/css/game.css" rel="stylesheet" />
    <script>
		var pathForGame="<?php echo $_GET['game'] . "/"?>";
		var roomID="<?php echo $_GET['roomID']?>";
		
		//List of all the dynamic scripts to load
		var scriptLoad = {audio: false, images: false, sprites: false, 
						  background: false, scripts: false, entities: false, 
						  ui: false, worlds: false, configuration: false, main: false};
						  
		function checkSuccessfulLoad() {
			console.log("called");
			$.each(scriptLoad, function(fileType, hasLoaded) {
				console.log("fileType: " + fileType + " hasLoaded: " + hasLoaded);
				if(hasLoaded === false)
					window.location.href = "/mainPage.php?toThisPage=3"; /* Could not find game*/
			});
		}
	</script>
	
</head>
<body id="_body" style="margin:0;padding:0;" onload="checkSuccessfulLoad()">
    <div class="alert">
        <div class="alert-window">
            <div class="alert-title">
                Title
            </div>
            <div class="alert-description">
                Description
			</div>
            <div class="alert-buttons">
                <button class="alert-button alert-button-yes">Yes</button>
                <button class="alert-button alert-button-no">No</button>
                <button class="alert-button alert-button-ok">Ok</button>
				<button class="alert-button alert-button-cancel">Cancel</button>
            </div>
        </div>
    </div>
	<div class="top">
		
	</div>
	<canvas id="game-window" style="background:black;border:1px solid black;"></canvas>
	<div style="margin-top:10px;padding-left:10px;" id="description"></div>
	<div class = "bottom row">
		<div class = "menu-button close-game" onclick="SERVER.destroy();">Close Room</div><!-- if(typeof(SERVER) != 'undefined') SERVER.destroy(); else window.location.href='/mainPage.php'; -->
		<div class = "menu-button quit-game" onclick="quitClicked()">Quit Game</div>
		<div class = "menu-button get-players" onclick="playerClicked();">Players</div>
		<div class="menu-button left next-turn" onclick="SERVER.nextTurn();">Next Turn</div>
		<div class = "menu-button left hangout">
			<div id="placeholder-div1"></div>
		</div>
	</div>
	<div class="list-players" style="display: none; margin-left:2em; margin-top:1em;">Players:</div>
	
	<script type="text/javascript" src="engine/Resources.js"></script>
    <script type="text/javascript" src="engine/ImageURL.js"></script>
	<script type="text/javascript" src="engine/Background.js"></script>
    <script type="text/javascript" src="engine/Sprite.js"></script>
    <script type="text/javascript" src="engine/Entity.js"></script>
    <script type="text/javascript" src="engine/UI.js"></script>
    <script type="text/javascript" src="engine/World.js"></script>
    <script type="text/javascript" src="engine/InputHandle.js"></script>
    <script type="text/javascript" onload="scriptLoad['audio']=true;"         src=<?php echo $_GET['game'] . "/config/CfgAudio.js"       ?>></script>
    <script type="text/javascript" onload="scriptLoad['images']=true;"        src=<?php echo $_GET['game'] . "/config/CfgImages.js"      ?>></script>
    <script type="text/javascript" onload="scriptLoad['sprites']=true;"       src=<?php echo $_GET['game'] . "/config/CfgSprites.js"     ?>></script>
    <script type="text/javascript" onload="scriptLoad['background']=true;"    src=<?php echo $_GET['game'] . "/config/CfgBackgrounds.js" ?>></script>
    <script type="text/javascript" onload="scriptLoad['scripts']=true;"       src=<?php echo $_GET['game'] . "/config/CfgScripts.js"     ?>></script>
    <script type="text/javascript" onload="scriptLoad['entities']=true;" 	  src=<?php echo $_GET['game'] . "/config/CfgEntities.js"    ?>></script>
    <script type="text/javascript" onload="scriptLoad['ui']=true;" 			  src=<?php echo $_GET['game'] . "/config/CfgUI.js"          ?>></script>
    <script type="text/javascript" onload="scriptLoad['worlds']=true;"		  src=<?php echo $_GET['game'] . "/config/CfgWorlds.js"      ?>></script>
    <script type="text/javascript" onload="scriptLoad['configuration']=true;" src=<?php echo $_GET['game'] . "/config/Configuration.js"  ?>></script>
    <script type="text/javascript" onload="scriptLoad['main']=true;" src="engine/Main.js"></script>
	<script type="text/javascript" src="engine/gameServer.js"></script>
	<script>
		
		$(document).ready(function() {
			if (typeof gapi !== 'undefined') {
				gapi.hangout.render('placeholder-div1', {
					'render': 'createhangout'//,
					//'initial_apps': [{ 'app_id': 'vast-math-861', 'start_data': 'dQw4w9WgXcQ', 'app_type': 'ROOM_APP' }]
				});
			}
			
			/* Unneccessary code with current design
			//Move the div's to their correct position
			if(GAME_WIDTH-330-$(".get-players") - $(".close-game") > 0)
				$(".next-turn").css("left", GAME_WIDTH-330-$(".next-turn").width() );
		//	if(GAME_WIDTH-305-$(".quit-game") > 0)
				$(".hangout").css("left", GAME_WIDTH-305);	
			if(GAME_WIDTH-330-$(".get-players") > 0)*/
		});
				//$(".close-game").css("left", (GAME_WIDTH-330-$(".close-game").width()) / 2.0 );
		checkSuccessfulLoad();
		SERVER_HOSTED = true;
		
		if( $.alerts.draggable ) {
			try {
                $(".alert").draggable({ handle: $(".alert-title") });
                $(".alert-title").css({ cursor: 'move' });
            } catch(e) { /* requires jQuery UI draggables */ }
		}
		
		function playerClicked() {
			if($(".list-players").css('display') == 'none') {
				$('.list-players').slideDown();
				var multiplayerInterval = setInterval( function() {
					SERVER.set_MultiPlayer_HTML_on('.list-players');
				}, 500);
			} else {
				$('.list-players').slideUp();
				clearInterval(multiplayerInterval);
			}
		}
		
		function quitClicked() {
			if(typeof(SERVER) != 'undefined')
				SERVER.close(); 
			else 
				window.location.href='/mainPage.php';
		}
		
	</script>
	
	<?php
		// Create a state token to prevent cross site request forgery.
		// Store it in the session for later validation.
		$state = md5(rand());
		
		$_SESSION['gameState'] = $state;	
	?>
	
	<div id="clientState" style="display: none"><?php echo $_SESSION['gameState'] ?></div>
	
</body>
</html>
