var Script_potatoClicked = {
    execute: function (dt, ui) {
        var entity = ui.entity;
        if (entity.name == Entity_potato.name) {
            game_current_world.worldData.potatoesClicked += 1;
            game_current_world.worldData.points += 10;
            Audio_punch.play();
        } else if (entity.name == Entity_rottenPotato.name) {
            //game_current_world.potatoesClicked += 1;
            game_current_world.worldData.points -= 10;
            Audio_badPotatoPunch.play();
        }
        game_current_world.removeEntity(entity);
        game_current_world.removeUI(entity.ui)
        //console.log(game_current_world.worldData.points);
	}
}

var Script_move = {
    execute: function (dt, potato) {
        potato.ypos += -3;
        if (potato.ypos + potato.sprite.imgURLObj.size[1] < 0) {
            game_current_world.worldData.lostPotatoes += 1;
            game_current_world.removeUI(potato.ui)
            game_current_world.removeEntity(potato);
          //  console.log("lost" + (potato.ypos));
        }
    }
}

var Script_world = {
    execute: function (dt, world) {
        var curTime = Date.now();
        if (WORLD_world.worldData.lastPotatoTime + (1.0 * 1000) <= curTime) {
            var posx = Math.floor(Math.random() * (GAME_WIDTH - 20) + 1);
            var newp;
            if (Math.floor((Math.random() * 10) + 1) >= 7) {
                newp = Entity_rottenPotato.newInstance(posx, GAME_HEIGHT - 50);
                world.addEntity(newp);
              //  console.log("r potato");
            } else {
                newp = Entity_potato.newInstance(posx, GAME_HEIGHT - 50);
                world.addEntity(newp);
             //   console.log("potato");
            }
            world.addUI(UI_Potato.newInstance([], newp, null));

            WORLD_world.worldData.lastPotatoTime = Date.now();
        }
    }
}

var Script_GAME_INIT = {
    execute: function () {
        Audio_coffee.onloadedmetadata = function () {
          //  alert("Meta data for video loaded");
        };
        //Audio_coffee.play();
    }
}