<?php
/*
	Author: Wesley Vansteenburg
	
	disconnect.php disconnects users from their game if they click the Quit button. 
		Otherwise this is never called.
		
	(1) On disconnect
	If host MID == 1: (they are MasterClient)
		Change this MID to 0
		Get next player & set their MID to 1
	Set Heart Attack to true
	Notify host of disconnect, and ask whether or not to keep data
*/

session_start();

//Prevent XSS
if(isset($_POST['state']) && isset($_SESSION['gameState'])) {
	if ($_POST['state'] != $_SESSION['gameState'])
		die($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');
} 
else 
	die($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');

//Check parameters
if( !(isset($_POST['roomID']) && isset($_POST['MID'])) )
	die("Invalid Parameters sent : ".$_POST['roomID']." AND ".$_POST['MID']);


require_once "../database.php";
$database = new Database();
	
$room = $database->esc_str($_POST['roomID']);
$mid = $database->esc_str($_POST['MID']);

//Get the room
$result = $database->query("SELECT * FROM sessions WHERE roomID='".$room."'");


//If too many rooms exists, throw error
if($result->num_rows != 1 ) {
	$result->free();
	$database->close();
	die('Error in disconnect: too many rows');
}

$theRoom = $result->fetch_array();
$player = json_decode($theRoom['players'], true);

$MC = key($player);
end($player);
$endID = key($player);


//If they're the MasterClient, move MC to next user.
if($mid == $MC)
{
	//If no more people are in the room, close it.
	if(count($player) == 1)
	{
		$database->query("UPDATE sessions SET players='null', data='', turn='', numPlayers=0 WHERE roomID='".$room."'");
		
		$database->close();
		$result->free();
		
		die("room closed");
	}
	
	$player[$endID + 1] = $player[$mid];
	$mid = $endID + 1;
	
	//Set their temporary MID until the end of file
	unset($player[$mid]);
	
	
}
//Set Heart Attack to true
$player[$mid]['HEART']['ATTACK'] = true;
$player[$mid]['HEART']['PULSE'] = false;

$encodeArray = json_encode($player);

//Update the Database
if (! $database->query("UPDATE sessions SET players='".$encodeArray."' WHERE roomID='".$room."'") )
	echo "Error in disconnect: could not update database";
else
	echo "Database updated";

$result->free();
$database->close();

//Run the next turn script
include 'nextTurn.php';
