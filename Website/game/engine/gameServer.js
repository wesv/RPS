// Author: Wesley Vansteenburg

var SERVER;

/**
	This class deals with all Multi-player functions needed to have a game.
*/
function Multiplayer(clientState) {
	var self = this;
	var isTurn = false;
	
	
	var beatTime = 1000;      //How long to wait in between each beat in the heartbeat
	var heartBeatData = null; //The Data to be sent with the heartbeat. Only used with MC data, for now.
	self.myOwnMID = 0;        //The Multiplayer ID
	self.room = roomID;       //The room ID
	self.state = clientState; //The client state set to prevent XSS
	self.intervalID; 		  //The ID of the interval set for the heartbeat
	self.notificationTitle = self.room + " Room Notification"; //Default Notification
	self.notificationTitle = self.room + " Room Notification"; //Default Notification
	
	/*			**************************** start() ****************************
		Starts the connection with the server.
		
		This will try to connect a user 3 times. If connecting failed, the user will be redirected to the mainPage with a message telling them they failed to connect
		
		Parameters:
			hasPassed: This is only used for recursive uses within the function. You do not send any parameters to start. (It knows the difference)
		
	*/
	self.start = function(hasPassed) {
		
		//If we've tried 3 times and failed, disconnect
		if(typeof hasPassed !== 'undefined' && hasPassed === '#')
			window.location.href = '/mainPage.php?toThisPage=4';
		
		self.connect();	
		
		setTimeout(function() { 
			if( self.myOwnMID == 0 ) {
				if( hasPassed == '!' ) //They've been through once
					self.start('@');
				else if( hasPassed == '@' ) //They've been through twice
					self.start('#');
				else //This is their first time through
					self.start('!');
			}
		}, 5000);
	};
	
	/*			**************************** fetch() ****************************
		Fetches the data from the database. 
		This function is NOT automatically ran, so the developer needs to call this directly to get their data.
		
		This function asks the server for data and does one of two things
		  (1) Passes the data object through the callback function, if defined OR
		  (2) Sets the object to the WORLD.receivedData variable if a function is not defined
		 
		Parameters:
			callback (function) - The function to send the data to after the data is fetched
	*/
	self.fetch = function (callback) {
		var fetched = function(response){
			if(response === ""){
				console.log("Nothing to fetch (self.fetched).");
				return;
			}
			
			// game_current_world is a public variable in Main.js
			if(typeof game_current_world !== 'undefined')
				if(callback)
					callback(JSON.parse(response));
				else
					game_current_world.receivedData = JSON.parse(response);
			else
				console.log("current world is undefined (gameServer.js)");
			
		}		
			
		sendToServer("/game/data.php?fetch", fetched);
	};

	/*			**************************** snedUpdate() ****************************
		Sends data to the database.
		This function is called automatically whenever the nextTurn() function is called, and it is your turn.
		
		sendUpdate() will send the public variable game_current_world.data. The contents of game_cuurent_world.data will OVERRIDE the current game data.
	*/
	self.sendUpdate = function () {
		var theData = JSON.stringify(game_current_world.data);
		//console.log(theData + " data");
		
		if(self.myOwnMID!=-1){
			sendToServer("/game/data.php?sendUpdate", function(response) {
				console.log("---Update Sent--");
				/*console.log(response + " server response");*/
			}, {data: theData});		
		}
	};
	
	/*			**************************** nextTurn() ****************************
		If it is your turn, nextTurn() moves the turn to the next person and sends an update.
		
		If you are the only active person in a room, it will notify you that it is your turn.
	*/
	self.nextTurn = function() {
		console.log(self.myOwnMID);
		if(self.myOwnMID != -1){
			sendToServer('/game/nextTurn.php', function(response) {
				if(response.indexOf("update|") > -1)
					self.sendUpdate();
				
				if(response.indexOf("send:") > -1)
					self.j_alert("<font size='20px'>" + response.substring(response.indexOf("send:") + 6) + "</font>");
			});			
		}
	};
	
	/* 			**************************** getTurn() ****************************
		This function returns whether or not it's your turn or not.
	
		Returns:
			true - if it is your turn
			false - otherwise
	*/
	self.getTurn = function() { return self.isTurn; };
	
	
	/*			**************************** checkHeartBeat() ****************************
		This function does many things with the heartbeat. 
		
		First, it determines what "state" the user is in:
			0: This user has been observed to have a "heart attack" by all other users.
				Since they have been kicked, they must return to the mainPage.
			1: This user is still connected. However, it is not their turn yet.
			2: This user is still connected. Also, it is their turn. It will notify the
				user one time when it becomes their turn.
			3: The MasterClient has left the game, so everyone else is kicked. The game
				will be saved until the MasterClient returns.
				
		Secondly, it determines if the user is the MasterClient. 
			If so, it will display it to the user and show the option
			to close the game, if they wish.
			
		Lastly, if the server responded with the word "confirm", it will show a confirm dialogue
			and send whatever data was sent to it back to the server signalling a "no". 
	*/
	self.checkHeartBeat = function () {
		//Functions used by heartBeatSuccess
		var kill = self.close;
		
		var heartBeatSuccess = function(response) {
			
			//Get connection state
			var connectionState = response.indexOf("State:");
			if(connectionState == -1)
			{
				console.log("kill");
				//klll();
			}
			else {
				var stateNumber = response.substring(connectionState + "State:".length, response.indexOf('|', connectionState));
				//console.log(stateNumber);
				switch(stateNumber)
				{
					case '0':
						console.log("kill");
						kill();
						break;
					case '1':
						console.log("Not your turn");
						self.isTurn = false;
						break;
					case '2':
						if(self.isTurn === false)
							self.j_alert("<font size='20px'>It's your turn.</font>");
						
						self.isTurn = true;
						console.log("Your turn");
						break;
					case '3':
						console.log("MC left.");
						//window.location.href = "/mainPage.php?toThisPage=6";
						break;
				}
			}
			
			//If I am MC, show "Close"
			if(response.indexOf("MC|") > -1)
				$(".close-game").css("display", "inline");
			
			
			
			//If I get a confirm message, send a message back
			if(response.indexOf("confirm:") > -1) {
				var start_confirm_index = response.indexOf("confirm:") + "confirm:".length;
				var end_confirm_index = response.indexOf("|", start_confirm_index);
				
				var start_data_index = response.indexOf("data:");
				
				if(start_data_index == -1)
				{
					console.log("Could not get data. Aborting.");
					return;
				} else start_data_index += "data:".length;
				
				var end_data_index = response.indexOf("|", start_data_index);
				
				var confirm_message = response.substring(start_confirm_index, end_confirm_index);
				var heart_attack_user = response.substring(start_data_index, end_data_index);
				
				self.j_confirm(confirm_message, self.notificationTitle, function(result) {
					if(!result)
						heartBeatData = {removeUser: heart_attack_user};
				});
			}
		};
		
		sendToServer("/game/heartBeat.php", heartBeatSuccess, heartBeatData);
		heartBeatData = null;
	}
	
	
	/*			**************************** connect() ****************************
		This function connects the user to this room and, if successful, starts the heartbeat.
		
		Once connected, it fetches the data from the database.
	*/
	self.connect = function () {
		
		var connectCallback = function(response) {
			
			//console.log(response);
			//If a valid success, update server with objs
			if(response.indexOf("successful_connect") > -1)
			{
				var index_of_mid = response.indexOf("MID=");
				self.myOwnMID = response.substring( index_of_mid + "MID=".length,
												  response.indexOf("/", index_of_mid) ) ;
				
				self.fetch();
				
				//Start the heartbeat
				self.intervalID = setInterval(self.checkHeartBeat, beatTime);
			}
			else if(response.indexOf("no_room") > -1) {
				//Pull Data
				self.myOwnMID = "-1";
				self.j_alert("All of the player spots are now full.\nHowever, feel free to stay and watch.");
				self.fetch();
			}
			
			
		};
		
		
		sendToServer("/game/connect.php", connectCallback, { 
			// Extra Data 
			maxPlayers: GAME_NUM_PLAYERS, 
			gameid: pathForGame.substring(0, pathForGame.length -1) //Remove the '/' from the end of the string
		});
	};
	
	
	/*			**************************** isConnected() ****************************
		Returns: true if the user is connected, false otherwise.
	*/
	self.isConnected = function() { 
		return self.myOwnMID != 0 
	};
	
	/*			**************************** close() ****************************
		Closes the Multiplayer Object
		
		Parameter:
			onWindowClose: If this variable is set, it means the window was closed by leaving the page. If not, it means this was called normally
		Returns:
			Nothing
	*/
	self.close = function (synchronous) {
		
		if(synchronous === undefined) synchronous = false;
		
		//On disconnect, simply stop the intervals
		if(synchronous === true) clearInterval(self.intervalID);

		//If they didn't simply close the window, call disconnect.php
		if(!synchronous)
				if(self.myOwnMID != '-1')
					self.j_confirm("Are you sure you want to leave?", self.notificationTitle, function(r) {
						if(r) {
							clearInterval(self.intervalID);
							sendToServer("/game/disconnect.php", function(response) {
								//console.log(response);
								window.location.href = "/mainPage.php";
							});
						}
					});
				else
					window.location.href = "/mainPage.php";
	};

	/*			**************************** destroy() ****************************
	*/
	self.destroy = function() 
	{
		self.j_confirm("Are you sure you want to close the room?", self.notificationTitle, function(r) {
			if(r) {
				sendToServer("/game/destroyGame.php", function(response) {
					console.log(response + "Destroyed");
					if(response.indexOf("destroyed") > -1)
						window.location.href = "/mainPage.php?toThisPage=7";
				});
			}
		});
	}
	
	/*			**************************** set_Multiplayer_HTML_on() ****************************
	*/
	self.set_MultiPlayer_HTML_on = function(div)
	{
		sendToServer("/game/player.php", function(result) {
			$(div).html("Players:<br>" + result);
		});
	}

	
	/*			**************************** sendToServer() ****************************
		Sends data to link via ajax and calls successFunction on success.
		If link is the file used to connect, it will do the asynchronous call needed to work.
	*/
	function sendToServer(link, successFunction, /*optional*/ dataObject) {
		
		//The Default data to be sent to the server in every request
		var defaultServerData = {state: self.state, roomID: self.room, MID: self.myOwnMID};
		
		//The Data that'll actually be sent
		var sentData = $.extend({}, defaultServerData, dataObject);
		
		//Test if the link given is a valid link
		if(link.indexOf(".php") == -1)
			return "Error: invalid url";
		
		
		//Test if we need to make the request asynchronous or not
		
		$.ajax({
			type: "POST",
			url: link,
			data: sentData,
			success: function (r) { if(successFunction) successFunction(r); },
			error: function (errorObj, message, errorThrown)
			{
				console.log("Data sent to <" + link + "> erred with " + message + ".");
			}
		});
	}
	
	/*			**************************** j_alert() ****************************
	
	*/
	self.j_alert = function (message, title) {
		if($(".alert-window").css("visibility") == 'visible'){
			setTimeout(function() { 
				self.j_alert(message, title) 
			}, 1000);
		}else {
			if(!title) title = self.notificationTitle;
			
			$(".alert-window").css("visibility", "visible");
			$(".alert-title").text(title);
			$(".alert-description").html(message);
			
			$(".alert-button-yes").css("display", "none");
			$(".alert-button-no").css("display", "none");
			$(".alert-button-cancel").css("display", "none");
			$(".alert-button-ok").css("display", "initial");
			
			
			$(".alert-button-ok").on("click", function(e) { 
				$(".alert-window").css("visibility", "hidden");
			});
		}
	}
	
	/*			**************************** j_confirm() ****************************
	
	*/
	self.j_confirm = function (message, title, callback) {
		if($(".alert-window").css("visibility") == 'visible') {
			setTimeout(function() { 
				self.j_confirm(message, title, callback) 
			}, 1000);
		}
		else {
			$(".alert-window").css("visibility", "visible");
			$(".alert-title").text(title);
			$(".alert-description").html(message);
			
			$(".alert-button-cancel").css("display", "none");
			$(".alert-button-ok").css("display", "none");
			$(".alert-button-yes").css("display", "initial");
			$(".alert-button-no").css("display", "initial");
			
			
			$(".alert-button-yes").on("click", function(e) { 
				$(".alert-window").css("visibility", "hidden");
				callback(true);
			});
			$(".alert-button-no").on("click", function(e) { 
				$(".alert-window").css("visibility", "hidden");
				callback(false);
			});
		}
	}
}

$(document).ready(function() {
	SERVER = new Multiplayer($("#clientState").text());
	SERVER.start();
});