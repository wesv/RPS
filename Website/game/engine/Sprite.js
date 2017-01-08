/*
Author: Kayler Renslow
Sprites are images used to represent Entities in the game. 

Parameters:
    imgURLObj - ImageURL object for the image of the sprite (see CfgImages)
    frameSec  - Speed of the animation in frames/second (int)
    frames    - How many frames there are for the animation (int)
    once      - If true, the animation will be rendered once. If false, the animation will render infinitely many times. (boolean)
*/
function Sprite(imgURLObj, frameSec, frames, once) {
    this.imgURLObj = imgURLObj;
    this.frameSec = frameSec;
    this.frames = frames;
    this.once = once;
    this.lastUpdate = Date.now();

    this.currentFrame = 0; // Current frame of the animation

    this.update = function (dt) {
        if (this.once && this.currentFrame >= this.frames) {
            return;
        }
        var curTime = Date.now();
        if (this.lastUpdate + (this.frameSec * 1000) <= curTime) {
            this.lastUpdate = Date.now();
            this.currentFrame += 1;
            if (this.currentFrame >= this.frames) {
                this.currentFrame = 0;
            }
        }
    }

    this.render = function (ctx, xpos, ypos) {
        var size = this.imgURLObj.size;
        ctx.drawImage(resources.get(this.imgURLObj), 0, this.currentFrame * size[1], size[0], size[1], xpos, ypos, size[0], size[1]);
    }
}