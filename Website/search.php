<?php
	//DONT HAVE IT PICK FILES WITH 0 PLAYERS
	//Author Kayler Renslow
	//Modified and documented by Wesley Vansteenburg
	if(isset($_GET['search']))
		$search = $_GET['search'];
	else
		die("I broke");
	
	require_once "database.php";
	$database = new Database();
	
	//If a search for all query is made
	if($search === "*") 
		$query = "SELECT roomID, gameid FROM sessions";
	
	//Otherwise, do default query
	else {
		$search = $database->esc_str($search);
		$query = "SELECT roomID, gameid FROM sessions WHERE roomID LIKE'%$search%' OR gameid LIKE '%$search%'";
	}
	
	$roomData = $database->query($query);
	$newBoxInfo = array();
	
	//Loop through all the data and return it
	if($roomData->num_rows > 0)
		while($room = $roomData->fetch_assoc())
			//Add to list of rows
			$newBoxInfo[] = $room;
	
	echo json_encode($newBoxInfo);
	$database->close();