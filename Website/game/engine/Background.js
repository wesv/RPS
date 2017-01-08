/*
Author: Kayler Renslow
Backgrounds are images that will never move on the screen.

Parameters:
    imgURLObj    - ImageURL object for the image of the background (see CfgBackgrounds)
    size         - Size of the image (array - 2d dimension)
    tile         - If true, the background will be tiled to fill the entire canvas when rendered. (int)
    fillToScene  - If true, the background will be scaled to fit the entire canvas. (boolean)
*/
function Background(imgURLObj, tile, fillToScene) {
    this.imgURLObj = imgURLObj;
    this.tile = tile;
    this.fillToScene = fillToScene;

    this.render = function (ctx) {
        if (this.tile) {
            var pattern = ctx.createPattern(resources.get(this.imgURLObj), "repeat");
            ctx.fillStyle = pattern;
            ctx.fill();
        } else if (this.fillToScene) {
            ctx.drawImage(resources.get(this.imgURLObj), 0, 0, GAME_WIDTH, GAME_HEIGHT);
        } else {
            ctx.drawImage(resources.get(this.imgURLObj), 0, 0, this.imgURLObj.size[0], this.imgURLObj.size[1]);
        }
    }
}