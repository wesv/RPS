<?php
	require_once "database.php";
	
	$gpid = "";
	if(isset($_REQUEST['gpid']))
		$gpid = $_REQUEST['gpid'];
	else
		die("error");
	
	$database = new Database();
	
	$gpid = $database->esc_str($gpid);
	$query = "SELECT gameid FROM users WHERE gpid='".$gpid."'";
	
	$gameids = $database->query($query);
	//echo var_dump($gameids);
	if($gameids->num_rows > 0){
		$rows = array();
		for($x = 0; $r = $gameids->fetch_assoc(); $x++) {
			$rows[$x] = $r['gameid'];
		}
		//echo var_dump($rows);
		echo json_encode($rows);
	}
	else echo '[]';
	
	$gameids->free();
	$database->close();