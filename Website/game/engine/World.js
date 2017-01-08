/*
Author: Kayler Renslow

Parameters:
    name       - name of the world (String)
    id         - unique id for the world (int)
    entities   - all Entity instances that exist in this world. (Entity object array)
    UI         - all UI instances that exist in this world. (UI object array)
    background - all Background instances that exist in this world. (Background object array)
*/
function World(name, id, entities, ui, backgrounds, updateScript) {
    this.name = name;
    this.id = id;
    this.data = {};
    this.data['entities'] = entities;
	this.data['worldData'] = {};
	this.worldData = this.data['worldData'];
    this.ui = ui;
    this.updateScript = updateScript;
	this.receivedData = {};;
    this.update = function (dt) {
        if (this.updateScript != null) {
            this.updateScript.execute(dt, this);
        }
        for (var i = 0; i < this.data['entities'].length; i++) {
            this.data['entities'][i].update(dt);
        }
    }
    this.handleClick = function (clickEvent) {
        var succeed;
        for (var i = 0; i < this.ui.length; i++) {
            succeed = this.ui[i].checkClick(clickEvent);
            if (succeed) {
                this.ui[i].click();
            }
        }
    }
    this.backgrounds = backgrounds;
    this.addEntity = function (entity) {
        entity.worldID = this.data['entities'].length;
        this.data['entities'].push(entity);
    }
    this.removeEntity = function (entity) {
        this.removeFromArray(this.data['entities'], entity);
    }

    this.removeFromArray = function (array, item) {//item must have worldID
        if (array.length == 0) {
            return;
        }
        var move;
        if (array.worldID != array.length - 1) {//this is to make sure we aren't selecting the one we want to remove in the array and removing the wrong element
            move = array[array.length - 1];
        } else {//obviously the entity is the last element in the array
            array.pop();
            return;
        }
        //copies the last element into the parameter entity's spot and then removes the last element since it would be duplicated in this process
        array[item.worldID] = move;
        move.worldID = item.worldID;
        array.pop();
    }
    this.addUI = function (ui) {
        ui.worldID = this.ui.length;
        this.ui.push(ui);
    }

    this.removeUI = function (ui) {
        this.removeFromArray(this.ui, ui);
    }
}