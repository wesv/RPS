<?php
//Author: Wesley Vansteenburg
session_start();

if(isset($_POST['state']) && isset($_SESSION['gameState'])) {
	if ($_POST['state'] != $_SESSION['gameState'])
		die($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');
} 
else 
	die($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');

/*
	(1) Check (with room ID && MID) if player is still connected in players column
	(2) If connected, echo "stillConnected" or something
	(3) If not connected, echo "notConnected" and disconnect user
*/

if(!(isset($_POST['roomID']) && isset($_POST['MID'])))
	die("Invalid parameters sent. : ".$_POST['roomID']." AND ".$_POST['MID']);


require_once "../database.php";
$database = new Database();

$room = $database->esc_str($_POST['roomID']);
$mid = $database->esc_str($_POST['MID']);

$result = $database->query("SELECT * FROM sessions WHERE roomID='".$room."'");

if($result->num_rows !== 1) {
	$result->free();
	$database->close();
	die('Error in heartbeat: too many rows : ' . $room);
}

//The user is connected, but is everyone else still there?

$sessionArray = $result->fetch_array();
$players =  json_decode($sessionArray['players'], true);

if($players[$mid] === 'undefined' || $sessionArray['players'] == 'null') {
	$result->free();
	$database->close();
	die("State:0|");
}

/** psuedocode for verifying if other users and myself are here:
	If BEAT == true:
		check for BEAT == false in all other players
		For each BEAT == false
			If ACHE == false: ACHE = true
			Else ATTACK=true
		for all PLAYERS:
			BEAT = false
	If BEAT = false:
		ACHE = false
		BEAT = true
		
	The above code is how the server checks if you are still connected. 
	If you don't respond twice, you will be removed from server.
*/

$MC = key($players);

//If they are the MC
if($MC == $mid)
{
	//This  is MC
	$isMC = true;
	echo "MC|";
	if(isset($_POST['removeUser'])){
		$removeID = $database->esc_str($_POST['removeUser']);
		foreach($players as $multiplayerID => $data) {
			if( $removeID == $multiplayerID && $data['HEART']['PULSE'] === false /*&& $theArray['HEART']['ATTACK'] === false*/)
			{
				unset($players[$multiplayerID]);
				unset($removeID);
				$numPlayers = $sessionArray['numPlayers'] - 1;
				
				break;
			}
		}
	}
}

if($players[$mid]['HEART']['BEAT'] === true)
{
	foreach($players as $key => &$theArray)
	{
		//The MC has left
		if( $MC == $key && $theArray['HEART']['PULSE'] === false) {
			$result->free();
			$database->close();
			die("State:3|");
		}		
		
		if($theArray['HEART']['BEAT'] === false && $theArray['HEART']['PULSE'] === true) {
			if($theArray['HEART']['ACHE'] === false)
				$theArray['HEART']['ACHE'] = true;
			else {
				$theArray['HEART']['ATTACK'] = true;
				$theArray['HEART']['PULSE'] = false;
				
				if($key == $sessionArray['turn'])
				{
					$_POST['MID'] = $key;
					include 'nextTurn.php';
				}
			}
		}
		
		//If the ID sent to the MC has returned, remove it
		/*	Function moved to above
		if(isset($removeID)) {
			if( $removeID == $key && $theArray['HEART']['PULSE'] === false /*&& $theArray['HEART']['ATTACK'] === false*//*)
			{
				unset($players[$key]);
				unset($removeID);
				$numPlayers = $sessionArray['numPlayers'] - 1;
				
				echo var_dump($players);
				
				continue;
			}
		}*/
		
		//If a User left, notify MC
		if($theArray['HEART']['PULSE'] === false && $theArray['HEART']['ATTACK'] === true && $isMC === true)
		{
			//Remove to only allow 1 notification at a time
			unset($isMC);
			
			echo "confirm:Player #<strong>".$key."</strong> (Google+ ID = " . $theArray['ID'] .") has left the room.\nDo you want to keep their <u>game data</u>?|data:".$key."|";
			
			//Set to false to stop constant notification
			$theArray['HEART']['ATTACK'] = false;
		}
		$theArray['HEART']['BEAT'] = false;
	}
}
else if($players[$mid]['HEART']['PULSE'] === true){
	$players[$mid]['HEART']['BEAT'] = true;
	$players[$mid]['HEART']['ACHE'] = false;
}

//Update Server

$encodedArray = json_encode($players);

if(isset($numPlayers))
	$database->query("UPDATE sessions SET players='".$encodedArray."', numPlayers=".$numPlayers." WHERE roomID='".$room."'");
else
	$database->query("UPDATE sessions SET players='".$encodedArray."' WHERE roomID='".$room."'");

if($players[$mid]['HEART']['PULSE'] === false)
	echo "State:0|";
else if($sessionArray['turn'] === $mid)
	echo "State:2|";
else 
	echo "State:1|";

$result->free();
$database->close();
