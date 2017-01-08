var WORLD_world = new World("world", 0, [], [], [])
var WORLD_ALL = [WORLD_world];
WORLD_world.worldData.points = 0;
WORLD_world.worldData.level = 0;
WORLD_world.worldData.potatoesClicked = 0;
WORLD_world.worldData.lostPotatoes = 0;
WORLD_world.worldData.lastPotatoTime = Date.now();
WORLD_world.updateScript = Script_world;