/*
Author: Kayler Renslow
UI is short for User Interface and really only handles click areas. A developer can set the image of the UI. A UI can be attached to an Entity. With that, you can see if a user clicked an Entity.

Parameters:
    name     - name for the UI. (String)
    position - 2d array of xpos and ypos to place it. (int[])
    width    - width of the click area (int)
    height   - height of the click area (int)
    isGlobal - if true, the UI will appear in every world (boolean)
    entity   - Entity object associated with this UI. Can be null (Entity object)
    image    - ImageURL object associated with this UI. Can be null. (ImageURL object)
    script   - Object which contains a function to call when executed. The function should should have two parameters (dt (int) and params (array)). The dt passed through for UI is 1
               and params is just the UI's name in an array. (Object)
*/
function UI(name, position, width, height, isGlobal, entity, image, script) {
    this.name = name;
    this.position = position;
    this.width = width;
    this.height = height;
    this.image = image;
    this.isGlobal = isGlobal;
    this.script = script;
    this.worldID = 0;
	this.entity = null;
	this.appendRender = null;

    this.newInstance = function (position, entity, image) {
        return new UI(this.name, position, this.width, this.height, this.isGlobal, entity, image, this.script);
    }
    this.setEntity = function (entity) {
        this.entity = entity;
        this.entity.ui = this;
    }

    if (entity != null) {
        this.setEntity(entity);
    }

    this.setImage = function (image) {//takes in a ImageURL object
        this.image = image;
        this.width = image.size[0];
        this.height = image.size[1];
    }
    this.checkClick = function (event) {//if the ui's area contains the event's point, will return true, otherwise false
        var posx = this.position[0];
        var posy = this.position[1];
        var width = this.width;
        var height = this.height;
        if (this.entity != null) {
            posx = this.entity.xpos;
            posy = this.entity.ypos;
            width = this.entity.sprite.imgURLObj.size[0];
            height = this.entity.sprite.imgURLObj.size[1];
        }

        if (posx <= event.x + CANVAS_OFFSET[1] && posx + width >= event.x + CANVAS_OFFSET[1]) {
            if (posy <= event.y - CANVAS_OFFSET[0] && posy + height >= event.y- CANVAS_OFFSET[0]) {
                if (this.entity != null) { //if this ui is tracking an entity, the transparent part of the entity shouldn't be clickable.
					var transparent = rps_checkTransparency(this.entity.sprite, (event.x + CANVAS_OFFSET[1])-posx, (event.y - CANVAS_OFFSET[0]) - posy);
                    return !transparent;//if not transparent, then return true
                }
                return true;
            }
        }
        return false;
    }

    this.click = function () {
        if (this.script == null) {
            alert("script for ui " + this.name + " is null");
            return;
        }
        this.script.execute(1,this);
    }

    this.render = function (ctx) {
        if (this.image != null) {
            ctx.drawImage(resources.get(this.image), this.position[0], this.position[1], this.width, this.height);
        }
		if(this.appendRender != null){
			this.appendRender.render(ctx);
		}
    }
}