<?php
//Author: Wesley Vansteenburg
session_start();

if(isset($_POST['state']) && isset($_SESSION['gameState'])) {
	if ($_POST['state'] != $_SESSION['gameState'])
		die($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');
}
else 
	die($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');

require_once "../database.php";

if(isset($_POST['roomID'])) {
	$database = new Database();
	$thisRoom = $database->query("SELECT * FROM sessions WHERE roomID='".$database->esc_str($_POST['roomID'])."'");
} else
	die("Invalid Parameter in connect: Did not contain room id parameter");

if($thisRoom->num_rows !== 1)
{
	$thisRoom->free();
	$database->close();
	die("Room " . $_POST['roomID'] . " does not exist or isn't unique.");
}

$theRoom = $thisRoom->fetch_array();
//echo var_dump($thisRoom);

/*
  Send back the data object
*/
if(isset($_REQUEST['fetch']))
	echo $theRoom['data'];


/*
	Send update to database
*/
if(isset($_REQUEST['sendUpdate']))
{
	if(!isset($_POST['data']))
	{
		$thisRoom->free();
		$database->close();
		die("No data sent");
	}
	
	$updateData = $database->esc_str($_POST['data']);
	
	$query = "UPDATE sessions SET data='".$updateData."' WHERE roomID='".$database->esc_str($_POST['roomID'])."'";
	$database->query($query);
	
}
$database->close();