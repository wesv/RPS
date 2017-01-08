/*
Author: Kayler Renslow
Entities are things that are updated every game tick. They need not move, however, but have the capability to do so.

Parameters:
    name    - unique name assigned to the entity. (string)
    scripts - array of scripts this entity has. (array of strings with the filename)
    sprite  - Sprite object associated with this Entity. (Sprite Object. See Sprite.js)
    xpos    - x position of entity (int)
    ypos    - y position of entity (int)
*/
function Entity(name, scripts, sprite, xpos, ypos) {
    this.name = name;
    this.scripts = scripts;
    this.sprite = sprite;

    this.xpos = xpos;
    this.ypos = ypos;

    this.worldID = 0;//how to identify this entity in the world

    this.ui = null;

    // This function is called for every move update.
    this.move = function (dx, dy, dt) {
        this.xpos += dx * dt;
        this.ypos += dy * dt;
    };

    // Called every game tick.
    this.update = function (dt) {
        this.sprite.update(dt);
        for (var i = 0; i < this.scripts.length; i++) {
            this.scripts[i].execute(dt,this);
        }
    }

    this.newInstance = function (xpos,ypos) {
        return new Entity(this.name, this.scripts, this.sprite, xpos, ypos);
    }

    this.render = function (ctx) {
        this.sprite.render(ctx, this.xpos, this.ypos);
    }
}

