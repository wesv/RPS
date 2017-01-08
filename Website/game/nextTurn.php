<?php
//Author: Wesley Vansteenburg
session_start();

//Prevent XSS
if(isset($_POST['state']) && isset($_SESSION['gameState'])) {
	if ($_POST['state'] != $_SESSION['gameState'])
		die($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');
} 
else 
	die($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');

/*
	For nextTurn:
		Verify the user has the turn (turn = theirMID)
		Move the turn to the next person in the player array
*/


if(!(isset($_POST['roomID']) && isset($_POST['MID'])))
	die("Invalid parameters sent. : ".$_POST['roomID']." AND ".$_POST['MID']);


require_once "../database.php";
$database = new Database();

$room = $database->esc_str($_POST['roomID']);
$mid = $_POST['MID'];

$result = $database->query("SELECT * FROM sessions WHERE roomID='".$room."'");

if($result->num_rows != 1) {
	$result->free();
	$database->close();
	die('Error in nextTurn: invalid number of rooms');
}


$theRoom = $result->fetch_array();


if($theRoom['turn'] == $mid)
{
	echo "update|";
	$players = json_decode($theRoom['players'], true);
	$newTurnMID = "-1";
	
	end($players);
	$end = key($players);
	reset($players);
	
	//Go through all players to find my MID and then set the next turn to the next person in the array (this will loop if we go out of bounds)
	for($x = $mid + 1; $x <= $end; $x++) {
		echo $x .": " . gettype($players[$x]) . ": ";
		
		if(gettype($players[$x]) !== 'undefined') {
			if($players[$x]['HEART']['PULSE'] === true) {
				$newTurnMID = $x;
				break;
			}
		}
	}
	if($newTurnMID == -1)
		$newTurnMID = key($players);
	
	$result->free();
	
	if($newTurnMID != $mid)
	{
		$database->query("UPDATE sessions SET turn='".$newTurnMID."' WHERE roomID='".$room."'");
		echo "success";
	}
	else echo("send: It's Your Turn"); // You are the only one in the room :D
}


$database->close();