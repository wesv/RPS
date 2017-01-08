package js;

import main.DataManager;

/** @author Kayler Renslow<br>
 * <br>
 * Creates a JavaScript-like array when the getJSArray() method is invoked. Also saves the Java version for public access.
 * @param <E> The type of array */
public class JSArray extends JSParam{

	/** The array with data */
	private String[] array;

	/** Creates a JavaScript-like array when the toString() method is invoked. Also saves the Java version for public access.
	 * 
	 * @param array the array with its data */
	public JSArray(String[] array) {
		super("", JSParam.TYPE_ARRAY);
		this.array = array;
		this.param = formatToJS(array);
	}

	/** Creates a JavaScript-like array when the toString() method is invoked. Also saves the Java version for public access.
	 * 
	 * @param array the array with its data
	 * @param prefix prefix for each element in the array. */
	public JSArray(String[] array, String prefix) {
		super("", JSParam.TYPE_ARRAY);
		this.array = array;
		setPrefix(prefix);
		this.param = formatToJS(array, this.prefix);
	}

	/** Set the Java-based array this class holds and then also store it into the JS-based array format
	 * 
	 * @param array String[] to convert and save */
	public void setArray(String[] array) {
		this.array = array;
		this.param = formatToJS(array);
	}

	/** Converts a regular String[] into a String which is used in the JSParam class's parameter String
	 * 
	 * @param array String[] to convert
	 * @return converted String[] */
	public static String formatToJS(String[] array, String prefix) {
		if (array.length == 0){
			return "[]";
		}
		String strArr = "[";
		for (int i = 0; i < array.length - 1; i++){
			strArr += prefix+ array[i] + ",";
		}
		strArr += prefix + array[array.length - 1] + "]";
		return strArr;
	}

	/** Converts a regular String[] into a String which is used in the JSParam class's parameter String
	 * 
	 * @param array String[] to convert
	 * @return converted String[] */
	public static String formatToJS(String[] array) {
		return formatToJS(array, "");
	}

	/** Get the Java-based array.
	 * 
	 * @return Java-based array */
	public String[] getArray() {
		return array;
	}

	/* Return the JavaScript-based String version of the array that this class contains. This will return the same value as getParam() */
	public String getJSArray() {
		return getParam();
	}

	@Override
	public String getSavableParam() {
		return DataManager.fromStringArrayToString(array);
	}
}
