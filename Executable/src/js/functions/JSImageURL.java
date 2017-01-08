package js.functions;

import java.awt.Dimension;

import js.JSArray;
import js.JSFuncData;
import js.JSParam;
import config.CfgLang;

/** @author Kayler Renslow<br>
 * <br>
 * This class represents the ImageURL function in the JS engine. */
public class JSImageURL extends JSFuncData{

	/** Select the imageURL parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_IMAGE_URL = 0;

	/** Select the size parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_IMAGE_SIZE = 1;

	/** Create a new JS ImageURL function instance.
	 * 
	 * @param jsFuncParams the parameters to this instance of the ImageURL function. (imageURL, size) */
	public JSImageURL(String jsVarName, JSParam[] jsFuncParams) {
		super(jsVarName, CfgLang.JS_FUNC_NAME_IMAGE_URL, jsFuncParams);
	}

	/** Get the url for the image this instance represents. */
	public String getImageURL() {
		return this.jsFuncParams[SELECT_IMAGE_URL].getParam();
	}

	/** Get the size for the image this instance represents. */
	public String[] getSize() {
		return ((JSArray) this.jsFuncParams[SELECT_IMAGE_SIZE]).getArray();
	}

	/** Converts a Dimension object into an String[]. The String[] will have the dimension.width placed at index 0 and dimension.height at index 1
	 * 
	 * @param dim Dimension to convert
	 * @return String[] */
	public static String[] dimensionToStringArray(Dimension dim) {
		String[] s = {(int)dim.getWidth() + "", (int)dim.getHeight() + ""};
		return s;
	}

}
