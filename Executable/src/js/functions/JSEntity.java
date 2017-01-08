package js.functions;

import js.JSArray;
import js.JSFuncData;
import js.JSParam;
import config.CfgLang;

/** @author Kayler Renslow<br>
 * <br>
 * This class represents the Entity function in the JS engine. */
public class JSEntity extends JSFuncData{

	/** Select the name parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_NAME = 0;

	/** Select the scripts parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_SCRIPTS = 1;

	/** Select the sprite parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_SPRITE = 2;

	/** Select xpos parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_XPOS = 3;

	/** Select ypos parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_YPOS = 4;
	
	
	/** Create a new JS Entity function instance.
	 * 
	 * @param jsFuncParams the parameters to this instance of the entity function. (name, scripts, sprite, xpos, ypos) */
	public JSEntity(JSParam[] jsFuncParams) {
		super(jsFuncParams[SELECT_NAME].getParam(), CfgLang.JS_FUNC_NAME_ENTITY, jsFuncParams);
	}

	/** Get the name of this entity instance. Each name of each entity is unique to themselves. */
	public String getEntityName() {
		return this.jsFuncParams[SELECT_NAME].getParam();
	}

	/** Get the scripts associated with this entity. Scripts are a String array with paths to each script. */
	public String[] getEntityScripts() {
		return ((JSArray) this.jsFuncParams[SELECT_SCRIPTS]).getArray();
	}

	/** Get the entity sprite associated with this entity. The sprite should be a constant variable defined in the JS engine and saved in the Java version as that as well. */
	public String getEntitySprite() {
		return this.jsFuncParams[SELECT_SPRITE].getParam();
	}

	/** Get the x position of this entity.*/
	public String getEntityXPos() {
		return this.jsFuncParams[SELECT_XPOS].getParam();
	}
	
	/** Get the y position of this entity.*/
	public String getEntityYPos() {
		return this.jsFuncParams[SELECT_YPOS].getParam();
	}

}
