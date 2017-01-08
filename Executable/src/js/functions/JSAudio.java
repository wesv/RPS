package js.functions;

import js.JSFuncData;
import js.JSParam;
import config.CfgLang;

/** @author Kayler Renslow<br>
 * <br>
 * This class represents the AudioURL function in the JS engine. */
public class JSAudio extends JSFuncData{

	/** Select the audioURL parameter of this function. This constant is used with getParameter(int select) and setParameter(int select, JSParam newParam). */
	public static final int SELECT_AUDIO_URL = 0;

	/** Create a new JS AudioURL function instance.
	 * 
	 * @param jsFuncParams the parameters to this instance of the AudioURL function. (audioURL) */
	public JSAudio(String jsVarName, JSParam[] jsFuncParams) {
		super(jsVarName, CfgLang.JS_FUNC_NAME_AUDIO, jsFuncParams);
	}
	
	/** Get the url for the audio this instance represents.*/
	public String getAudioURL() {
		return this.jsFuncParams[SELECT_AUDIO_URL].getParam();
	}

}
