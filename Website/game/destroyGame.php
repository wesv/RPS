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

if(isset($_POST['roomID']) && isset($_POST['MID'])) {
	$database = new Database();
	$thisRoom = $database->query("SELECT * FROM sessions WHERE roomID='".$database->esc_str($_POST['roomID'])."'");
	$mid = $_POST['MID'];
} else
	die("Invalid Parameter in connect: Did not contain room id parameter");

if($thisRoom->num_rows !== 1)
{
	$thisRoom->free();
	$database->close();
	die("Room " . $_POST['roomID'] . " does not exist or isn't unique.");
}

$theRoom = $thisRoom->fetch_array();

$players = json_decode($theRoom['players'], true);

echo var_dump($players);

$MC = key($players);

if($MC == $mid) {
	$database->query("UPDATE sessions SET data='{}', players='null', numPlayers=0, maxPlayers=0, gameid='', turn='' WHERE roomID='".$database->esc_str($_POST['roomID'])."'");
	echo "destroyed";
}