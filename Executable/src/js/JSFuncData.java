package js;

/** @author Kayler Renslow<br>
 * <br>
 * This class is an abstract class that will encapsulate all types of function data that will be written to disk from the Java based data. <br>
 * <br>
 * <b>NOTE:</b> Super classes of this class should have constants set to each index of the parameters of the JS function this class represents. For instance, if I have a function with 4 parameters, there should be 4
 * constants. Constant one will select index 0 of the jsFuncParams array, Constant 2 will select index 1, etc. <br>
 * All JSFuncData instances will store their JS data as Strings but in the JS engine, they will be converted to their required type. */
public abstract class JSFuncData{
	/** The name to the variable of the function this JSData class represents. */
	protected final String jsFuncName;

	/** The parameters to the function this JSData class represents. */
	protected final JSParam[] jsFuncParams;

	/** The number of parameters this JS function has. */
	protected int numParams;

	/** Variable name for the JavaScript variable that will be saved into the game engine's configs */
	protected String jsVarName;

	protected boolean deleted = false;

	public JSFuncData(String jsVarName, String jsFuncName, JSParam[] jsFuncParams) {
		this.jsVarName = jsVarName;
		this.jsFuncName = jsFuncName;
		this.jsFuncParams = jsFuncParams;
		numParams = jsFuncParams.length;
	}

	/** Gets the variable name for the JavaScript variable that will be saved into the game engine's configs
	 * 
	 * @return String of the JS variable */
	public String getJSVarName() {
		return jsVarName;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean d) {
		this.deleted = d;
	}

	/** Get the name of the function this JSData class represents.
	 * 
	 * @return name of the function */
	public String getJSFuncName() {
		return jsFuncName;
	}

	public int getNumParams() {
		return numParams;
	}

	/** Get the parameters of the function this JSData class represents.
	 * 
	 * @return parameters of the function */
	public JSParam[] getJSFuncParams() {
		return jsFuncParams;
	}

	/** Get a JavaScript parameter.
	 * 
	 * @param select The attribute to select and return. Use the static final integers in this class beginning with SELECT.
	 * @return the JSParam instance of the selected parameter */
	public JSParam getParameter(int select) throws ArrayIndexOutOfBoundsException {
		if (select < 0 || select >= numParams){ // use >= because numParams doesn't include zero
			throw new ArrayIndexOutOfBoundsException("Can not select parameter at index " + select);
		}
		return this.jsFuncParams[select];
	}

	/** Set a parameter of this JS function.
	 * 
	 * @param select The attribute to select and return. Use the static final integers in the super class beginning with SELECT.
	 * @param newParam the new JSParam object to replace the selected parameter with. */
	public void setParameter(int select, JSParam newParam) throws ArrayIndexOutOfBoundsException {
		if (select < 0 || select >= numParams){ // use >= because numParams doesn't include zero
			throw new ArrayIndexOutOfBoundsException("Can not select parameter at index " + select);
		}
		this.jsFuncParams[select] = newParam;
	}

	public void setJSVarName(String name) {
		this.jsVarName = name;
	}

	@Override
	public String toString() {
		String out = getClass().getName() + "[ " + jsVarName + " ";
		for (JSParam param : jsFuncParams){
			out += param.toString() + " ";
		}
		out += "]";

		return out;
	}
}
