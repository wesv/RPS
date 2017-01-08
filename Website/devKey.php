<?php 
session_start();

/*error_reporting(E_ALL);
ini_set('display_errors', 1);*/

//Set the precision for the time-stamp
ini_set("precision", 20);

//The database where queries are sent
require_once "database.php";
$database = new Database();

//The time a token is valid (1 hour)
$addTime = 3600.0;


/***********************************************
   Creates a key and saves it to the database
************************************************/	
if(isset($_REQUEST['getKey']))
{
	//Checks if the user is who he says he is. (XSS Protection)
	if(isset($_POST['state']) && isset($_SESSION['state'])) {
		if ($_POST['state'] != $_SESSION['state']) {
			$database->close();
			die("Error:" . $_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');
			
		}
	} else {
		$database->close();
		die("Error: " .$_SERVER['SERVER_PROTOCOL'] . ' Unauthorized');
	}
	
	//Check if the user has a valid token 
	$gpid = $database->esc_str($_SESSION['gpid']);
	$checkExistance = $database->query("SELECT * FROM devToken WHERE gpid='".$gpid."'");
	
	//If the user has a token
	if($checkExistance->num_rows > 0) {
		$return_code = " ";
		
		//Get every row in the query
		while($eachRow = $checkExistance->fetch_array())
			
			//If the token hasn't expired
			if($return_code == " " && $eachRow['expiration'] >= microtime(true))
				$return_code = $eachRow['token'];
			
			//Remove any codes in the query that are expired. 
			else if($eachRow['expiration'] < microtime(true)) 
				$database->query("UPDATE devToken SET token='', expiration=0.0, gpid='emptyRow' ".
					"WHERE gpid='".$eachRow['gpid']."'");
		
		//If a token is found, return it
		if($return_code != " ")
			die($return_code);
		
		//Free the query
		$checkExistance->free();
	}

	
	/* 
		Creates a token with a length from 7 - 10 only with the visible ASCII characters.
		If the token already exists in the database(highly unlikely), create a new token.
	*/
	$token = "";
	do {
		$length = rand(7, 10);
		for($x = 0; $x < $length; $x++)
		{
			switch(rand(1, 3))
			{
				case 1: // Numbers
					$token .= chr(rand(48, 57));
					break;
				case 2: // Capital Letters
					$token .= chr(rand(65, 90));
					break;
				case 3: // Lowercase Letters
					$token .= chr(rand(97, 122));
					break;
				default:
					$token .= '~';
			}
		}
		$token = $database->esc_str($token);
		$checkExistance = $database->query("SELECT * FROM devToken WHERE token='".$token."' LIMIT 1");
	}while($checkExistance->num_rows > 0);
	
	//Free the query
	$checkExistance->free();
	
	
	//Store the token in database with a lifetime of 60 minutes
	$expirationTime = microtime(true) + $addTime;
	
	if($database->query("INSERT INTO devToken (token, expiration, gpid) ".
				"VALUES ('".$token."', ".$expirationTime.", '".$gpid."')"))
		echo $token;
	else
		echo "Error: Key could not be Created.";
}


/****************************************************
     Verifies a token is in the database
     Returns the gameID (should be to the executable
****************************************************/	
if(isset($_REQUEST['verify']))
{
	$importedToken = $_REQUEST['verify'];
	
	//If the token is valid token
	if(!$importedToken || strlen($importedToken) > 10 || strlen($importedToken) < 7){
		$database->close();
		die("Error: invalid token");
	}
	
	$importedToken = $database->esc_str($importedToken);
	$query = "SELECT * FROM devToken WHERE token='".$importedToken."' LIMIT 1";
	$result = $database->query($query);
	
	//Check if token exists in database
	if($result->num_rows == 0)
	{
		$result->free();
		$database->close();
		die("Error: token does not exists");
	}
	
	//Get the token and expiration
	$server = $result->fetch_array();
	
	//If Token has not expired, echo the time when the token was created
	if($server['expiration'] >= microtime(true))
		echo $server['expiration'] - $addTime; 
	
	//Else, the token has not expired and remove it
	else {
		echo "Error: token has expired";
		$database->query("UPDATE devToken SET token='', expiration=0.0, gpid='emptyRow' ".
				"WHERE token='".$server['token']."'");
	}
	
	//Free the query
	$result->free();
}

/****************************************************
     Removes a token from the database and sets the
	 game ID to the user if it is requested
	 Returns 1 if the game ID was set. 0, otherwise.
****************************************************/	
if(isset($_REQUEST['remove']))
{
	$token = $database->esc_str($_REQUEST['remove']);
	
	//If set, update the users table with gpid and gameId
	if(isset($_REQUEST['gameID'])) {
		
		$gameId = $database->esc_str($_REQUEST['gameID']);

		//Get the gpid
		$getGPID = $database->query("SELECT * FROM devToken WHERE token='".$token."'");
		
		$database->query("INSERT INTO users (gameID, gpid) VALUES ('".$gameId."', '".$getGPID->fetch_array()['gpid']."')");
		
		$getGPID->free();
		echo "1";
	}
	//Then, destroy the token
	$database->query("UPDATE devToken SET token='', expiration=0.0, gpid='emptyRow' ".
				"WHERE token='".$token."'");
	echo "0";
}
$database->close();
?>