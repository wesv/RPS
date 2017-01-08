package js.functions;

import js.JSFuncData;
import js.JSParam;
import config.CfgLang;

/** @author Kayler Renslow<br>
 * <br>
 * This class represents the Background function in the JS engine. */
public class JSBackground extends JSFuncData{

	/** Select the imgURLObj parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_IMG_URL_OBJ = 0;

	/** Select the tile parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_IS_TILED = 1;

	/** Select the fillToScene parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_FILL_TO_SCENE = 2;

	/** Create a new JS Background function instance.
	 * 
	 * @param jsFuncParams the parameters to this instance of the background function. (imgURLObj, tile, fillToScene) */
	public JSBackground(String jsVarName, JSParam[] jsFuncParams) {
		super(jsVarName, CfgLang.JS_FUNC_NAME_BACKGROUND, jsFuncParams);
	}

	/** Get the imgURLObj of this background instance. This parameter is a ImageURL object for the image of the background. This should be a constant variable defined in the JS engine and saved in the Java version as that
	 * as well. */
	public String getImageURLObj() {
		return this.jsFuncParams[SELECT_IMG_URL_OBJ].getParam();
	}

	/** Get the tile parameter. Tile is a boolean and if true, the background will be tiled to fill the screen. If false, it will not tile. */
	public String getIsTiled() {
		return this.jsFuncParams[SELECT_IS_TILED].getParam();
	}

	/** Get the fillToScene parameter. The fillToScene parameter is a boolean and designates whether the background will be stretched to fill the screen. */
	public String getFillToScene() {
		return this.jsFuncParams[SELECT_FILL_TO_SCENE].getParam();
	}

}
