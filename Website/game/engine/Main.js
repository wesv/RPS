/*
    Author: Kayler Renslow
    This file is where the game execution starts and continues.
*/

var rps_requestAnimFrame = (function () {
    return window.requestAnimationFrame ||
        window.webkitRequestAnimationFrame ||
        window.mozRequestAnimationFrame ||
        window.oRequestAnimationFrame ||
        window.msRequestAnimationFrame ||
        function (callback) {
            window.setTimeout(callback, 1000 / 60);
        };
})();

var rps_lastTime;
var game_current_world = WORLD_ALL[0];
var CANVAS_OFFSET;//offset of the canvas in pixels [top, left]

resources.load(IMAGEURL_ALL);
resources.onReady(rps_init);

/* First function called for game initialization. */
function rps_init() {
    console.log("Game resources loaded.");
    rps_injectScripts();

    rps_lastTime = Date.now();
    rps_main();

    var canv = document.getElementById("game-window");
    canv.width = GAME_WIDTH;
    canv.height = GAME_HEIGHT;
    canv.oncontextmenu = function (e) {
        e.preventDefault();
    };
	var rect = canv.getBoundingClientRect();
	console.log(rect.top, rect.right, rect.bottom, rect.left);
	CANVAS_OFFSET = [rect.top, rect.left];

    var description = document.getElementById("description");
    description.innerHTML = GAME_DESCRIPTION;
    var title = document.getElementById("_title");
    title.innerHTML = GAME_TITLE;

    //document.getElementById("_body").style.background = GAME_WINDOW_BACKGROUND;
    //document.getElementById("_body").style.color = GAME_WINDOW_TEXT_COLOR;
    if (typeof Script_GAME_INIT !== 'undefined' && Script_GAME_INIT != null) {
        Script_GAME_INIT.execute();
    }
}

function game_changeWorld(id) {
    game_current_world = WORLD_ALL[id];
}

function rps_injectScripts() {
    /*var head = document.getElementById("_head");
    var script;
    for (var i = 0; i < SCRIPT_ALL.length; i++) {
        script = document.createElement('script');
        script.src = SCRIPT_ALL[i];
        document.getElementsByTagName('head')[0].appendChild(script);
    }
    console.log("Game Scripts injected.");*/
}

function rps_main() {
    var now = Date.now();
    var dt = (now - rps_lastTime) / 1000.0;
    /*SERVER.run(SERVER);*/
	rps_update(dt);
    rps_render();
    rps_lastTime = now;
    rps_requestAnimFrame(rps_main);
};

function rps_update(dt) {
    game_current_world.update(dt);
}

function rps_render() {
    var canv = document.getElementById("game-window");
    var ctx = canv.getContext("2d");
    ctx.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
    var i;
    for (i = 0; i < game_current_world.backgrounds.length; i++) {
        game_current_world.backgrounds[i].render(ctx);
    }
    for (i = 0; i < game_current_world.data['entities'].length; i++) {
        game_current_world.data['entities'][i].render(ctx);
    }
    for (i = 0; i < game_current_world.ui.length; i++) {
        game_current_world.ui[i].render(ctx);
    }
}


function rps_checkTransparency(sprite, x, y) {
	if(SERVER_HOSTED){
		var canv = document.createElement("canvas");
		canv.width = sprite.imgURLObj.size[0];
		canv.height = sprite.imgURLObj.size[1];
		var ctx = canv.getContext("2d");
		sprite.render(ctx, 0, 0);
		var imageData = ctx.getImageData(x, y, 1, 1);
		return imageData.data[3] == 0;
	}
	return true;
}