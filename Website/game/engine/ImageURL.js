/*
Author: Kayler Renslow
ImageURL objects are strictly an object to hold a url to an image

Parameters:
    imgURL    - URL for an image this object holds
    size      - Size of the sprite of one frame (array - 2d dimension)
Other varibales:
	pathForGame - Path to the current game being loaded. Found in game.php
*/
function ImageURL(imgURL, size) {
	this.imgURL = imgURL;
    this.size = size;
}