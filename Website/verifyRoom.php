<?php
	//Author Wesley Vansteenburg
	
	if(isset($_POST['roomName']) && isset($_POST['gameID'])) {
		$roomName = $_POST['roomName'];
		$game = $_POST['gameID'];
	} else 
		die("Invalid parameters: ".$_POST['roomName']." and ".$_POST['gameID']);
	
	require_once "database.php";
	$db = new Database();
	
	$room =  $db->esc_str($roomName);
	$result = $db->query("SELECT * FROM sessions WHERE roomID='".$room."'");
	
	
	//If there is exactly one entry, the room name is taken
	// and we need to check if the room name is open or not
	if($result->num_rows == 1) { 
		$players = $result->fetch_array()['players'];
		
		if($players === 'null')
			//The room is available to take
			echo "/game/game.php?game=".$game."&roomID=".$roomName;
		else
			echo "Room name taken.";
	} else if($result->num_rows > 1)
		//There is an error and more than one room has the same room ID.
		// No one way have this room
		echo "Room name taken.";
	
	else
		echo "/game/game.php?game=".$game."&roomID=".$roomName;
	
	$result->free();
	$db->close();
?>