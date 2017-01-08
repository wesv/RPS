/* Authors: Kayler Renslow and Wesley Vansteenburg. Noted where code is written*/

var hostPanelShow = false;
var post = "POST";
var theConstantSearch;
var isSearching = false;
var currentBoxes = new Array();
var images = ["lamp.jpg","dino.png"];

//Author: Wesley Vansteenburg
function create(gameID)
{	
	jPrompt("What is the name of your room?", "Only letters and numbers", "Name Your Room", function(room) {
		if(room == null)  return;
		
		var expression = /^([a-zA-Z0-9 _-])+$/;
		
		if(!expression.test(room))
		{
			jAlert('"'+room +'" is not a valid room name.\nA <strong>valid</strong> room name can have: <ul><li>Alphanumeric Characters</li><li>Spaces</li><li>Dashes(-)</li><li>Underscores(_)</li></ul>', "Error: Invalid Room Name"); 
			return; 
		}
		ajaxCall(post, "/verifyRoom.php", {"roomName": room, "gameID": gameID}, createGameServerResponse);
	});
	
  //  $(document).ready(function() { var expression=/^([a-zA-Z0-9 _-])+$/; if(expression.test($('#popup_prompt').val) == false) $('#popup_prompt').css('background-color', '#ff3333'); else $('#popup_prompt').css('background-color', 'green');});
}

//Author: Wesley Vansteenburg
function createGameServerResponse(response)
{
	if(response.indexOf("game/game.php?" > -1))
		window.location.href = response;
	else
		jAlert("The server responded with: " + response, "Server Error");
	
}

/* Creates the list of games you have made as a developer.
   Author: Wesley Vansteenburg, Kayler Renslow
*/
function gotMyGames(data){
	var games = JSON.parse(data);
	if(games.length == 0){
		$(".my-games").html('<div class="my-game">No Games</div>');
		return;
	}
	$(".my-games").html("<div class='my-game' id='loading-games'>Loading games...</div>");
	for(var x=0; x < games.length; x++)
		getConfig(printGames, games[x]);
}

//Author: Kayler Renslow
function printGames(config, id){
	var game = '<div class="my-game" onclick="create('+id+');">'+config[0]+'</div>';
	$("#loading-games").css("display","none");
	$(".my-games").append(game);
}

//Author: Kayler Renslow
function fetchMyGames(){
	if(typeof userGPID !== 'undefined')
		ajaxCall(post, "/myGames.php?gpid="+userGPID, {}, gotMyGames);
	else
		console.log("you shouldn't be seeing this message.");
}

//Author: Kayler Renslow
function getGameConfigURL(gameid){
	return "game/"+gameid+"/config/Configuration.js";
}

//Author: Kayler Renslow
function getConfig(done, gameid){
    var id = gameid;
	try {
		$.ajaxSetup( { cache: true } );
		$.getScript( getGameConfigURL(gameid) )
		  .done(function( script, textStatus ) {
			console.log("Load");
			done([GAME_TITLE, GAME_WIDTH, GAME_HEIGHT, GAME_NUM_PLAYERS, GAME_DESCRIPTION, GAME_WINDOW_BACKGROUND, GAME_WINDOW_TEXT_COLOR], id);
		  })
		  .fail(function( jqxhr, settings, exception ) {
			console.log("Could not load configuration for gameid : " + id)
		});
	} catch(e) {
		// The game was not found
	}
}

//Author: Wesley Vansteenburg
function hasSameRoomId(needle, haystack)
{
	for(var x = 0; x < haystack.length; x++)
		if(needle == haystack[x])
			return true;
	return false;
	
}

//Author: Wesley Vansteenburg
function searchDone(result){
	
	if(result==="")
		return;
	
	var data = JSON.parse(result);
	
	//Add boxes
	$.each(data, function(key, val) {
		if(! hasSameRoomId(val.roomID, currentBoxes))  			
			getConfig(function(gameData, gameID) {
				appendBox(val.roomID, gameID, gameData[0] /*GAME_TITLE*/, gameData[4] /*GAME_DESCRIPTION*/, "", gameData[3] /*GAME_NUM_PLAYERS*/);
				currentBoxes[currentBoxes.length] = val.roomID;
			}, val.gameid);
		else {
			//Check if number of players has changed
			if(val.numPlayers == 0)
				console.log("Room " + val.roomID + " is closed.");
			
			updateBox(val.roomID, val.numPlayers, val.maxPlayers);
		}
		
	});
	
	//Remove boxes
	/*Brute force. Loop through all elements of currentBoxes and 
	   remove any boxes that weren't in the query result.
	  NOTE: I could not find a way to asynchronously remove an element from this
	   array, so I just set its value to null
	*/
	for(var x = 0, len = currentBoxes.length; x < len; x++) {
		var entered = false;
		$.each(data, function(key, val) {
			if(currentBoxes[x] == val.roomID)
				entered = true;
		});
		if(entered === false) {
			console.log(currentBoxes[x]);
			//Remove Box from showing
			removeBox(currentBoxes[x]);
			
			//Remove that element from the array
			currentBoxes[x]  = '';
		}
	}
}

//Author: Wesley Vansteenburg
function search(){
	var query = document.getElementById('searchBox').value;
	if(query === "" || !isSearching)
		query = "*";
	
	ajaxCall(post,"/search.php?search="+query, {}, searchDone);
}

//Author: Kayler Renslow
function postKey(key){
	if(key==-1){
		key = "an error occured";
	}
	$("#uploadToken").css("display","inline").text(key);
	console.log("upload key: "+ key);
}

//Author: Kayler Renslow
function getKey(){
	var stateKey = $("#clientState").text();
	ajaxCall(post,"/devKey.php?getKey", {state:stateKey}, postKey);
}

//Author: Kayler Renslow
function initMainPage() {
	if (typeof gapi !== 'undefined') {
		gapi.hangout.render('placeholder-div1', {
			'render': 'createhangout',
			'initial_apps': [{ 'app_id': 'vast-math-861', 'start_data': 'dQw4w9WgXcQ', 'app_type': 'ROOM_APP' }]
		});    
	}
    $("#hostGame").on('click', function () {
        toggleHostGamePanel();
    });
    $("#closeHostGame").on('click', function () {
        toggleHostGamePanel();
    });
}

//Author: Kayler Renslow
function toggleHostGamePanel() {
    if (hostPanelShow) {
        $(".host-game").animate({ "left": "-=15vw" }, "slow");
    } else {
		fetchMyGames();
        $(".host-game").animate({ "left": "+=15vw" }, "slow").css("position", "fixed");
    }
    hostPanelShow = !hostPanelShow;
}

/* Removes a box from the page. Selects the box with the given roomid. 
   Author: Kayler Renslow
*/
function removeBox(roomid) {
	roomid = replaceAll(roomid, " ", "_nbsp_");
    $("#box_" + roomid).remove();
}

/* Updates an existing box with the given gameid with number of players and max number of players 
   Author: Kayler Renslow
*/
function updateBox(roomid, numPlayers, maxPlayers) {
    roomid = replaceAll(roomid, " ", "_nbsp_");
	$("#box_" + roomid + " .box-players").text(numPlayers+"/"+maxPlayers);
}

/* Creates a new box and appends it to the web page.
   Author: Kayler Renslow
*/
function appendBox(roomid, gameid, title, description, img, maxPlayers) {
	var roomName = roomid;
	roomid = replaceAll(roomid, " ", "_nbsp_");
	
    var content = "<div class='box' id='box_" + roomid + "'>" +
					"<div class='box-top-description box-top'>"+description+"</div><div class='box-top'></div>" +
					"<div class='box-bottom'>" +
						"<div class='box-room-name'>"+roomName+"</div>" +
						"<div class='box-game-name'>"+title+"</div>"+
						"<div class='box-players'>0/"+maxPlayers+"</div>" +
					"</div></div>";
	$("#boxes").append(content);
	var ind = Math.floor(Math.random()*images.length);
	//Used this form because other forms were not working
	$('body').on("click", '#box_' + roomid, function () {
		window.location.href = 'game/game.php?game=' + gameid + '&roomID=' + roomName;
	});
	$('#box_' + roomid + " .box-top").on("mouseenter", function () {
	    $('#box_' + roomid + " .box-top-description").css("display", "inline");
	}).on("mouseleave", function () {
	    $('#box_' + roomid + " .box-top-description").css("display", "none");
	}).css("background-image","url(../img/"+images[ind]+")");
	
}
//Author: http://stackoverflow.com/questions/1144783/replacing-all-occurrences-of-a-string-in-javascript
function replaceAll(string, find, replacement) {
  return string.replace(new RegExp(find, 'g'), replacement);
}

//Author: Kayler Renslow
function getInnerBoxContent(description, img, maxPlayers) {
    return "<div class='box-inner'><div class='box-description'>"+
        "<img class='box-img' src='"+img+"'/>"+description+"</div><div class='box-bottom'>0/"+maxPlayers+" players</div></div>";
}

/* Makes an ajax call to the given url of the given type with the given data. 
   Returns the result or -1 if an error occurred. 
   Logs the error in the console
   Author: Kayler Renslow
*/
function ajaxCall(type,url, data, doneFunc){
	$.ajax({
		type: type,
		url: url,
		data: data,
		success: function (result) {
			doneFunc(result);
		},
		error: function(error, textError, errorThrown) {
			doneFunc(-1);
			console.log(textError+" " + errorThrown);
		}
	});
}


$(document).ready(function () {
    initMainPage();
	
	theConstantSearch = setInterval(function() {
		search();
	}, 1000);
	
	window.onunload = function() { 
		window.clearInterval(theConstantSearch); 
	};
	
	//Hitting Enter will start a search
	/*$("#searchBox").keyPress(function(e) {
		if(e.keyCode == 13) 
			$(".search-button").click();
	});*/
});


/* boxes take this form:
<div class="box" id="box0" onclick="window.location='game.php?specificGameID=3';">
	<div class="box-top">
		//Put background image here
	</div>
	<div class="box-bottom">
		<div class="box-room-name"> roomID </div>
		<div class="box-game-name"> gameName </div>
		<div class="box-num-Players"> 0/5 </div>
	</div>
	
	<h4 class="box-title">Test Game</h4>
    <div class="box-inner">
        <div class="box-description">
            <img class="box-img" src="test-game-icon.png" />
            Diggity giggity miggity dog.
	    </div>
		<div class="box-bottom">0/1 players</div>
	</div>
</div>*/