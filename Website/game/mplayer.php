<?php
//Author: Wesley Vansteenburg
session_start();

if(isset($_POST['state']) && isset($_SESSION['state'])) {
	if ($_POST['state'] != $_SESSION['state'])
		die($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');
} 
else 
	die($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');

require_once "../database.php";
$database = new Database();

if(isset($_REQUEST['fetch']))
{
	/*
	 (1) Query table name
	 (2) Send back the data object
	*/
	$tableName = $database->esc_str($_GET['updateMe']);
	
	$query = "SELECT * FROM sessions WHERE roomID='".$tableName."'";
	$result = $database->query($query);
	
	
	if($result->num_rows !== 1) {
		$result->free();
		$database->close();
		die('null');
	}
	//else
	$theRoom = $result->fetch_array;
	echo $theRoom['data'];
	$result->free();
}

$database->close();