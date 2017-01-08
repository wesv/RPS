<?php
// ZACH KING 
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

if($thisRoom->num_rows !== 1){
	$thisRoom->free();
	$database->close();
	die("Room " . $_POST['roomID'] . " does not exist or isn't unique.");
}

if(isset($_POST['MID'])) {
	$mid = $_POST['MID'];
}
else
	die("Invalid Parameter in connect: Did not contain room id parameter");


$theRoom = $thisRoom->fetch_array();
$players = json_decode($theRoom['players'], true);
$check=$theRoom['turn'];
$j = 0;
echo "<div class='list-player data' style='margin-left:3em;'>";
foreach( $players as $i => $value)
{
	if($i == $check)
		echo " ->";
	
	echo $value['ID'];
	
	if($j == 0)
		echo " |MC|";
	if($i == $mid)
		echo " (you)";
	
	$j++;
	echo "<br/>";
}
echo "</div>";