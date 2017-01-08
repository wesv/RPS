package js;

/** @author Kayler Renslow<br>
 * <br>
 * A JSScript is a script that a developer has written themselves. Since there isn't a good way to represent them as a JSFuncData instance, they will be saved uniquely.<br>
 * <b>Every developer script should have an execute() function as that is the starting point of the script. </b> */
public class JSScript{
	/** JS variable name used to call the execute() function in every developer made script. */
	private String varName;
	/** Path to the script. */
	private String path;
	private boolean deleted;

	/** Constucts a new JSScript instance. A JSScript is a script that a developer has written themselves. Since there isn't a good way to represent them as a JSFuncData instance, they will be saved uniquely.<br>
	 * <b>Every developer script should have an execute() function as that is the starting point of the script. </b>
	 * 
	 * @param varName JS variable name used to call the execute() function in every developer made script
	 * @param path Path to the script */
	public JSScript(String varName, String path) {
		this.varName = varName;
		this.path = path;
	}

	public String getVarName() {
		return varName;
	}

	public String getPath() {
		return path;
	}

	public void setVarName(String name) {
		this.varName = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean d) {
		this.deleted = d;
	}
}
