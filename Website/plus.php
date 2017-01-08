<?php
/**
 * Author: Wesley Vansteenburg
 *
 * plus.php covers operations needed to sign a user in and out of the website
 *  and keeps track of any data the web site needs.
 *
 * Supports:
 *		Sign in 
 *		Sign out
 *		Revoke our Access
 *		Getting Users' Google+ ID (gpid)
 *		Code to prevent XSS
 *		
 */
session_start();

require_once  'config/config.php';
require_once  'google-api-php-client/src/Google/autoload.php';

//These are required_once to make sure they have been loaded
require_once  'google-api-php-client/src/Google/Client.php';
require_once  'google-api-php-client/src/Google/Service/Analytics.php';
require_once  'google-api-php-client/src/Google/Service/Plus.php';


/***********************************************
 Verify the user is the one sending the request
***********************************************/
if(isset($_POST['state']) && isset($_SESSION['state'])) {
	if ($_POST['state'] != $_SESSION['state']) {
		header($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized', true, 401);
		exit;
	}
}
else {
	header($_SERVER['SERVER_PROTOCOL'] . ' Unauthorized', true, 401);
	exit;
}


/**********************************
 Authenticate User on Server Side
**********************************/

//Help from http://www.gvngroup.be/doc/Google/google_plus_sign_in_tutorial.php
$client = new Google_Client();

$client->setApplicationName($application_name);
$client->setDeveloperKey($deveoper_id);
$client->setClientId($client_id);
$client->setClientSecret($client_secret);
$client->setRedirectURI($redirect_uri);

//Set Necessary Scopes Here
$client->setScopes(array("https://www.googleapis.com/auth/analytics.readonly"));

	
/**********************************
   Handles Signing In Operation
**********************************/	
if(isset($_REQUEST['storeToken']))
{
	//If we need to exchange a code on server to verify client
	if(isset($_POST['code']))
	{
		$client->authenticate($_POST['code']);
		$_SESSION['access_token'] = $client->getAccessToken();
		unset($_SESSION['logout']);
		echo "signin=yes";
	}
}

/**********************************
       Downloads User Data
**********************************/
if(isset($_SESSION['access_token']) && $_SESSION['access_token'] && !isset($_SESSION['gpid']))
{
	//Get any information needed about the use
	
	//Get the user's Google+ id
	$client->setAccessToken($_SESSION['access_token']);
	$pService = new Google_Service_Plus($client);
	$me = new Google_Service_Plus_Person();
	
	//Get Google+ ID
	$me = $pService->people->get('me');
	$_SESSION['gpid'] = $me->id;
}

/**********************************
   Handles Sign Out Operation
**********************************/
if(isset($_REQUEST['signout']))
{
	unset ($_SESSION['access_token']);
	unset ($_SESSION['gpid']);
	$_SESSION['logout'] = 1;
	echo "signout=yes";
	exit;
}

/**********************************
Handles Revoke Access Operation
**********************************/
if(isset($_REQUEST['revoke']))
{
	if(isset($_SESSION['access_token'])) {
		$token = json_decode($_SESSION['access_token'])->access_token;
		$discon = $client->revokeToken($token);
		if(!$discon) 
			echo "Unknown Error"; // Could not revoke token. Either due to already being disconnected or server issues
		else {
			$_SESSION['logout'] = 1;
			unset($_SESSION['access_token']);
			unset ($_SESSION['gpid']);
			echo "revoke=yes";
			exit;
		}
	} else 
		echo "Unknown Error";
		
}
?>