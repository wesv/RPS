package js.functions;

import js.JSArray;
import js.JSFuncData;
import js.JSParam;
import config.CfgLang;

/** @author Kayler Renslow<br>
 * <br>
 * This class represents the UI function in the JS engine. */
public class JSUI extends JSFuncData{

	/** Select the position parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_NAME = 0;

	/** Select the position parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_POSITION = 1;

	/** Select the width parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_WIDTH = 2;

	/** Select the height parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_HEIGHT = 3;

	/** Select the isGlobal parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_IS_GLOBAL = 4;

	/** Select the isGlobal parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_ENTITY = 5;

	/** Select the isGlobal parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_IMAGE = 6;

	/** Select the isGlobal parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_SCRIPT = 7;

	/** Create a new JS UI function instance.
	 * 
	 * @param jsFuncParams the parameters to this instance of the UI function. (name, position, width, height, isGlobal, entity, image, script) */
	public JSUI(String jsVarName, JSParam[] jsFuncParams) {
		super(jsVarName, CfgLang.JS_FUNC_NAME_UI, jsFuncParams);
	}

	/** Get the position of the UI. */
	public String[] getPosition() {
		return ((JSArray) this.jsFuncParams[SELECT_POSITION]).getArray();
	}

	/** Get the width of the UI. */
	public String getWidth() {
		return this.jsFuncParams[SELECT_WIDTH].getParam();
	}

	/** Get the height of the UI. */
	public String getHeight() {
		return this.jsFuncParams[SELECT_HEIGHT].getParam();
	}

	/** Get the name of the UI. */
	public String getName() {
		return this.jsFuncParams[SELECT_NAME].getParam();
	}

	/** Get the if gobal. */
	public String getGobal() {
		return this.jsFuncParams[SELECT_IS_GLOBAL].getParam();
	}

	/** Get the entity of the UI. */
	public String getEntity() {
		return this.jsFuncParams[SELECT_ENTITY].getParam();
	}

	/** Get the script of the UI. */
	public String getScript() {
		return this.jsFuncParams[SELECT_SCRIPT].getParam();
	}

	/** Get the image of the UI. */
	public String getImage() {
		return this.jsFuncParams[SELECT_IMAGE].getParam();
	}

}
