/*
    Author: Kayler Renslow
    This is the core of the key handling and click handling. More things will be added later.
*/

var rps_keys = {};// array of pressed keys and their statuses (i.e. key 'e' is true which means it is pressed.)

// Function called when any key is pressed or released.
function handleKey(event, status) {
    var code = event.keyCode;
    var key = String.fromCharCode(code);
    rps_keys[key] = status;
}

document.addEventListener('keydown', function (e) {
    handleKey(e, true);
});

document.addEventListener('keyup', function (e) {
    handleKey(e, false);
});

// This is to make sure when the window loses focus the keys are no longer registered.
window.addEventListener('blur', function () {
    rps_keys = {};
});

window.input = {
    isDown: function (key) {
        return rps_keys[key.toUpperCase()];
    }
};

function handleClick(event) {
    game_current_world.handleClick(event);
    //alert("click@" + event.x + " " + event.y);
};

window.onload = function () {
    var canvas = document.getElementById("game-window");
    canvas.addEventListener('click', function (event) { handleClick(event) });
}
