package js.functions;

import js.JSArray;
import js.JSFuncData;
import js.JSParam;
import js.JSScript;
import config.CfgLang;

/** @author Kayler Renslow<br>
 * <br>
 * This class represents the World function in the JS engine. */
public class JSWorld extends JSFuncData{

	/** Select the name parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_NAME = 0;

	/** Select the id parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_ID = 1;

	/** Select the entities array parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_ENTITIES = 2;

	/** Select the ui parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_UI = 3;

	/** Select the backgrounds parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_BACKGROUND = 4;

	/** Select the Init Script parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_INIT_SCRIPT = 5;

	private JSScript initScript;

	/** Create a new JS World function instance.
	 * 
	 * @param jsFuncParams the parameters to this instance of the JSWorld function. (name, id, entities, ui, backgrounds) */
	public JSWorld(String jsVarName, JSParam[] jsFuncParams) {
		super(jsVarName, CfgLang.JS_FUNC_NAME_WORLD, jsFuncParams);
	}


	/** Get the name of the world. */
	public String getName() {
		return this.jsFuncParams[SELECT_NAME].getParam();
	}

	/** Get the id of the world. */
	public String getID() {
		return this.jsFuncParams[SELECT_ID].getParam();
	}

	/** Get the entity array of the world. */
	public String[] getEntities() {
		return ((JSArray) jsFuncParams[SELECT_ENTITIES]).getArray();
	}

	/** Get the UI of the world. */
	public String[] getUI() {
		return ((JSArray) jsFuncParams[SELECT_UI]).getArray();
	}

	/** Get the backgrounds of the world. */
	public String[] getBackgrounds() {
		return ((JSArray) jsFuncParams[SELECT_BACKGROUND]).getArray();
	}

	/** Get the initialization script associated with this world
	 * 
	 * @return String with the path */
	public String getInitScript() {
		return this.jsFuncParams[SELECT_INIT_SCRIPT].getParam();
	}

	public void setInitScriptObject(JSScript script) {
		this.initScript = script;
		this.setParameter(SELECT_INIT_SCRIPT, new JSParam(script.getVarName(), CfgLang.JS_FUNC_VAR_NAME_PREFIX_SCRIPT));
	}

	public JSScript getInitScriptObject() {
		return initScript;
	}

}
