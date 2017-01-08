package js.functions;

import js.JSFuncData;
import js.JSParam;
import config.CfgLang;

/** @author Kayler Renslow<br>
 * <br>
 * This class represents the Sprite function in the JS engine. */
public class JSSprite extends JSFuncData{

	/** Select the sprite's image url object parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_IMG_URL_OBJ = 0;

	/** Select the frames per second parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_FRAME_SEC = 1;

	/** Select the frames parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_FRAME_COUNT = 2;

	/** Select the animation replay parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_ANIMATION_REPLAY = 3;

	/** Create a new JS Sprite function instance.
	 * 
	 * @param jsFuncParams the parameters to this instance of the sprite function. (imgURL, size, dir, frameSec, frames, animation_replay) */
	public JSSprite(String jsVarName, JSParam[] jsFuncParams) {
		super(jsVarName, CfgLang.JS_FUNC_NAME_SPRITE, jsFuncParams);
	}

	/** Get the image url associated with this sprite. In the JS engine, this will most likely be represented as a constant variable with the path defined in a config file rather than a direct url
	 * inputted into the sprite. */
	public String getImgURLObj() {
		return this.jsFuncParams[SELECT_IMG_URL_OBJ].getParam();
	}

	/** Get is the number of frames per second this sprite will run it's animation. This value doesn't matter if the sprite doesn't have an animation. Frames per second should be represented as a
	 * double type. */
	public String getSpriteFrameSec() {
		return this.jsFuncParams[SELECT_FRAME_SEC].getParam();
	}

	/** Get the number of frames for the animation of this sprite. If the sprite has no animation, the value should be 0. The sprite will start at frame 0. */
	public String getSpriteFrameCount() {
		return this.jsFuncParams[SELECT_FRAME_COUNT].getParam();
	}

	/** Get whether the animation of this sprite will only run once at spawn. Value is true if the animation will run once, false to run infinite time */
	public String getSpriteAnimationReplay() {
		return this.jsFuncParams[SELECT_ANIMATION_REPLAY].getParam();

	}

}
