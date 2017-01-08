<?php
/**
 * Author: Wesley Vansteenburg
 *
 * Database is a cover class for a MySQLi object
 * This will create a connection and check for errors for common MySQLi operations
 *
 * The operations covered are:
 *		connection errors
 *		query errors, and returns an error if one exists
 *
 */
class Database
{
	private static $servername;
	private static $dbName;
	private static $username;
	private static $password;
	
	protected $connection;
	
	public function __construct()
	{
		$servername = "localhost";
		$dbName = "kulopto_rpsData";
		$username = "kulopto_rps";
		$password = "Z9V{p~L]p?ep";
		
		$this->connection = new mysqli($servername, $username, $password, $dbName);
	
		if($this->connection->connect_error) {
			die ("Connection Failed: " . $conn->connect_error);
		}
	}
	
	public function query($theQuery)
	{
		$result = $this->connection->query($theQuery);
	
		if(!$result) {
			$this->close();
			die('Invalid query: '.$theQuery);
		}
		
		return $result;
	}
	
	public function esc_str($str)
	{
		return $this->connection->real_escape_string($str);
	}
	
	public function close()
	{
		$this->connection->close();
		return true;
	}
}
?>