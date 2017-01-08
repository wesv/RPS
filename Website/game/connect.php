<?php 
//Author: Wesley Vansteenburg
session_start();

//Test for CSRF
if(isset($_POST['state']) && isset($_SESSION['gameState'])) {
	if ($_POST['state'] != $_SESSION['gameState'])
		die($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');
} 
else 
	die($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');

	
/*This file will do the following:
  (1) Start a new game or join, depending on how the data looks
  (2) Pull information
		(a) Get number of players and set MID
		(b) Pull Entities & WorldData (already encoded) and send information back
		(c) Set MID with G+ ID (An array)
		(d) echo MID
  
*/

//Test for correct variables
if(!isset($_POST['roomID'])) 
	die("Invalid Parameter in connect: Did not contain room id parameter");

if(!isset($_POST['maxPlayers']) && gettype($_POST['maxPlayers']) === "integer" && $_POST['maxPlayers'] > 0)
	die("Invalid Parameter in connect: Did not contain valid maximumPlayers id parameter");

if(!isset($_POST['gameid']))
	die("Invalid Parameter in connect: Did not contain game id parameter");


require_once "../database.php";
$database = new Database();

$roomID = $database->esc_str($_POST['roomID']);
$gpid   = $database->esc_str($_SESSION['gpid']);
$gameid = $database->esc_str($_POST['gameid']);
$maxPlayers = intval($_POST['maxPlayers']);

//Search for specific room
$query = "SELECT * FROM sessions WHERE roomID='".$roomID."'";
$result = $database->query($query);

//If the room has never been created before
if($result->num_rows == 0) {
	/*	This is the PLAYER array that each room will have
	
		The Array:
			Multiplayer ID (MID):
				ID: Their google+ ID
				HEART:
					BEAT: Checks if user is still in the room. Every time user checks for new data, they tell the server they're still available
					ACHE: The warning flag. If all other users BEAT, any user that hasn't BEAT in that time gets a HEART ACHE	
					ATTACK: The user did not BEAT soon enough, so they have been kicked. The MasterClient will get a notification if a user leaves and asks them whether or not to keep the victim's information
					PULSE: If the user can BEAT or not. This'll be set to false iff the user had an attack and the MasterClient wanted to keep their information
	*/
	
	//Create The first person in the room
	$peopleObject = array("1"=>array("ID"=>$gpid, "HEART"=>array("BEAT"=>false, "ACHE"=>false, "ATTACK"=>false, "PULSE"=>true)));
	$encodedArray = json_encode($peopleObject);
	
	$query = "INSERT INTO sessions (roomID, data, players, numPlayers, maxPlayers, gameid, turn) VALUES ('".$roomID."', '{}', '".$encodedArray."', 1, ".$maxPlayers.", ".$gameid.", '1')";
	$result = $database->query($query);
	
	if($result) echo "successful_connect MID=1/";
	
	else // The query failed
		echo "Error in connect: could not create room";
	
} else if ($result->num_rows > 1) {
	//More than 1 room with the same name. This should NOT happen, and if it does, we need to remove it manually
	// No player is allowed to connect to a room that has multiple entries
	echo "SUPER MAJOR BAD ERROR";
}
//Else, the room has been created before
else { 
	$theRoom = $result->fetch_array();
	
	//If the room is now closed.
	if($theRoom['players'] == "null" || $theRoom['numPlayers'] == 0)
	{
		//Create the first person in the room
		$peopleObject = array("1"=>array("ID"=>$gpid, "HEART"=>array("BEAT"=>false, "ACHE"=>false, "ATTACK"=>false, "PULSE"=>true)));
		$encodedArray = json_encode($peopleObject);
		
		$query = "UPDATE sessions SET data='{}', players='".$encodedArray."', numPlayers=1, maxPlayers=".$maxPlayers.", gameid='".$gameid."', turn='1' WHERE roomID='".$roomID."'";
		$result = $database->query($query);
		
		if($result)
			echo "successful_connect MID=1/";
		else // The query failed
			echo "Error in connect: could not modify room";
	}
	//Else the room is open, and we are trying to join a room
	else { 
		$playerArray = json_decode($theRoom['players'], true);
		
		foreach($playerArray as $theID => &$valSearch) {
			//If the client is already  in the room, restart their pulse and give them their MID back.
			if($valSearch['ID'] == $gpid) {
				$valSearch['HEART']['PULSE'] = true;
				$valSearch['HEART']['BEAT'] = false;
				$valSearch['HEART']['ACHE'] = false;
				$valSearch['HEART']['ATTACK'] = false;
				
				//Update database
				$encodedArray = json_encode($playerArray);
				$query = "UPDATE sessions SET players='".$encodedArray."' WHERE roomID='".$roomID."'";
				if( $database->query($query) )
					echo "successful_connect MID=".$theID."/ DATA";
				
				else // The query failed
					echo "Error in connect: could not revive";
				
				$result->free();
				$database->close();
				
				exit;
			}
		}
		
		//If the room is not full
		if($theRoom['numPlayers'] < $theRoom['maxPlayers'])
		{
			//Get The MID
			end($playerArray);
			$mid = key($playerArray) + 1;
			
			//Add it to the player array
			$playerArray[$mid] = array("ID"=>$gpid, "HEART"=>array("BEAT"=>false, "ACHE"=>false, "ATTACK"=>false, "PULSE"=>true));
			$encodedArray = json_encode($playerArray);
			
			//Update database
			$query = "UPDATE sessions SET players='".$encodedArray."', numPlayers=".($theRoom['numPlayers'] + 1)." WHERE roomID='".$roomID."'";
			if($database->query($query))
				echo "successful_connect MID=".$mid."/ DATA";
			else
				echo "Error: could not connect to database";
		
		} else {
			echo "no_room"; // The room is full
		}
	}
}

$database->close();
