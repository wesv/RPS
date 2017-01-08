package js;

import gui.ConsoleUI;

/** @author Kayler Renslow<br>
 * <br>
 * This class encapsulates one JS parameter for a function. The data type can be any object. For primitives, use wrapper classes. <br>
 * All JSParam instances will store their JS data as Strings but in the JS engine, they will be converted to their required type. */
public class JSParam{

	/** The value this JavaScript parameter holds. */
	protected String param;

	public static final int TYPE_INT = 0;
	public static final int TYPE_DOUBLE = 1;
	public static final int TYPE_STRING = 2;
	public static final int TYPE_ARRAY = 3;
	public static final int TYPE_BOOLEAN = 4;
	public static final int TYPE_OBJECT = 5;

	protected final int type;
	protected String prefix;

	public static final String[] BOOLEAN_OPTIONS = {"true", "false"};

	/** This class encapsulates one JS parameter for a function. The data type can be any object. For primitives, use wrapper classes.
	 * 
	 * @param param a JSParam instance to represent the function parameter. Since this class will only hold strings, for non string parameters you will be forced to save them as a string and cast them
	 * back to what they are when they are retrieved. For array parameters, use the JSArray class.
	 * @param typeArray the type of this parameter */
	public JSParam(String param, int type) {
		this.param = param;
		this.type = type;
		this.prefix = "";
	}

	/** This class encapsulates one JS parameter for a function. The data type can be any object. For primitives, use wrapper classes.<br>
	 * This constructor creates a JSParam of type JSParam.TYPE_OBJECT. The prefix parameter is what the object's JSJuncData function name is. An underscore will be added automatically to the prefix.
	 * 
	 * @param param a JSParam instance to represent the function parameter. Since this class will only hold strings, for non string parameters you will be forced to save them as a string and cast them
	 * back to what they are when they are retrieved. For array parameters, use the JSArray class.
	 * @param prefix the prefix for the object */
	public JSParam(String param, String prefix) {
		this.param = param;
		this.type = JSParam.TYPE_OBJECT;
		setPrefix(prefix);
	}

	protected void setPrefix(String prefix) {
		prefix = prefix.trim();
		if (prefix.length() > 0 && prefix.charAt(prefix.length() - 1) != '_'){
			this.prefix = prefix + "_";
		}else if(prefix.length() > 0){
			this.prefix = prefix;
		}else{
			this.prefix = "";			
		}
	}

	public String getPrefix() {
		return prefix;
	}

	/** Get the type of this parameter.
	 * 
	 * @return type of this parameter */
	public int getType() {
		return type;
	}

	public boolean isTypeInt() {
		return type == TYPE_INT;
	}

	public boolean isTypeDouble() {
		return type == TYPE_DOUBLE;
	}

	public boolean isTypeString() {
		return type == TYPE_STRING;
	}

	public boolean isTypeArray() {
		return type == TYPE_ARRAY;
	}

	public boolean isTypeBoolean() {
		return type == TYPE_BOOLEAN;
	}

	public boolean isTypeObject() {
		return type == TYPE_OBJECT;
	}

	/** Get the String representation of this parameter
	 * 
	 * @return value of this parameter */
	public String getParam() {
		return param;
	}

	/** Get the String used to save to plain text files
	 * 
	 * @return String */
	public String getSavableParam() {
		return param;
	}

	@Override
	public String toString() {
		return param;
	}
}
