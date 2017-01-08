package js;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import util.FileSaveErrorException;
import js.functions.JSAudio;
import js.functions.JSImageURL;
import main.ProjectConfig;
import main.ServerConfig;
import config.CfgLang;

/** @author Kayler Renslow<br>
 * <br>
 * This class will write JavaScript code from the Java data in the editor. */
public abstract class JavaScriptWriter{

	private static void checkFile(File file) {
		if (!file.exists()){
			try{
				file.createNewFile();
			}catch (IOException e){
				e.printStackTrace();
				return;
			}
		}
	}

	/** Get a new instance of a PrintWriter to write to the writeFile associated with this JavaScriptWriter instance.
	 * 
	 * @return a new PrintWriter with writing location writeFile (the instance variable) */
	private static PrintWriter getWriter(File writeFile) throws FileSaveErrorException {
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(writeFile);
		}catch (Exception e){
			e.printStackTrace();
			throw new FileSaveErrorException("Could not create file writer.");
		}
		return pw;
	}

	/** Writes the project configuration to the Game's JS configuration file
	 * 
	 * @param saveLocation location to save to (should be a directory and ending with a /).
	 * @param config config to save
	 * @throws FileSaveErrorException thrown if the data couldn't be saved */
	public static void writeGameConfiguration(String saveLocation, ProjectConfig config) throws FileSaveErrorException {
		File file = new File(saveLocation + CfgLang.JS_CFG_CONFIGURATION);
		checkFile(file);
		PrintWriter pw = getWriter(file);
		pw.println("var " + CfgLang.JS_CONFIGURATION_VAR_TITLE + " = \"" + config.getName() + "\";");
		pw.println("var " + CfgLang.JS_CONFIGURATION_VAR_NUM_PLAYERS + " = " + config.getNumPlayers() + ";");
		pw.println("var " + CfgLang.JS_CONFIGURATION_VAR_WIDTH + " = " + config.getWidth() + ";");
		pw.println("var " + CfgLang.JS_CONFIGURATION_VAR_HEIGHT + " = " + config.getHeight() + ";");
		pw.println("var " + CfgLang.JS_CONFIGURATION_VAR_DESCRIPTION + " = \"" + config.getDescription() + "\";");
		pw.println("var " + CfgLang.JS_CONFIGURATION_VAR_TEXT_COLOR + " = \"" + config.getTextColor() + "\";");
		pw.println("var " + CfgLang.JS_CONFIGURATION_VAR_BACKGROUND + " = \"" + config.getBackground() + "\";");

		pw.println("var " + CfgLang.JS_CONFIGURATION_VAR_SERVER_HOSTED + " = " + (String.valueOf(ServerConfig.SERVER_HOSTED).toLowerCase()) + ";");

		pw.flush();
		pw.close();
	}

	/** Writes all JSFuncData to disk. The writing location is defined in the instance variable writeFile.
	 * 
	 * @param jsData data to write
	 * @param prefix the prefix String that comes before the JSFuncData variable name (i.e. Entity_entity1. Entity is prefix and entity1 is JSFuncData variable name)
	 * @param jsCfgAudio where to write
	 * @param createAllVariable if true, a JS variable will be created and assigned to an array containing all jsData function names
	 * @throws FileSaveErrorException */
	public static void writeFuncDataToDisk(ArrayList<? extends JSFuncData> jsData, String prefix, String fileLocation, boolean createAllVariable) throws FileSaveErrorException {
		File saveLocation = new File(fileLocation);
		checkFile(saveLocation);

		if (jsData.size() == 0){
			return;
		}

		PrintWriter pw = getWriter(saveLocation);

		JSParam[] params;
		String jsVarName;
		String funcName = jsData.get(0).getJSFuncName();

		for (JSFuncData data : jsData){
			jsVarName = prefix + "_" + data.jsVarName;
			pw.print("var " + jsVarName + " = new " + funcName + "(");
			params = data.jsFuncParams;
			for (int i = 0; i < data.jsFuncParams.length - 1; i++){
				if (data.jsFuncParams[i].isTypeString()){
					checkIfURL(pw, data, i);
					pw.print(", ");
				}else if (data.jsFuncParams[i].isTypeObject()){
					pw.print(params[i].prefix + params[i] + ", ");
				}else{
					pw.print(params[i] + ", ");
				}
			}
			int i = params.length - 1;
			if (data.jsFuncParams[i].isTypeString()){
				checkIfURL(pw, data, i);
				pw.println(");");
			}else if (data.jsFuncParams[i].isTypeObject()){
				pw.println(params[i].prefix + params[i] + ");");
			}else{
				pw.println(params[i] + ");");
			}
		}
		if (createAllVariable){
			pw.println();
			pw.print(funcName.toUpperCase() + "_ALL = [");
			for (int i = 0; i < jsData.size() - 1; i++){
				jsVarName = (prefix + "_" + jsData.get(i).jsVarName);
				pw.print(jsVarName + ",");
			}
			jsVarName = prefix + "_" + jsData.get(jsData.size() - 1).jsVarName;
			pw.print(jsVarName + "];");
		}
		pw.flush();
		pw.close();
	}

	private static void checkIfURL(PrintWriter pw, JSFuncData data, int param) {
		if (data instanceof JSImageURL && param == JSImageURL.SELECT_IMAGE_URL){
			pw.print("pathForGame+\"images/" + new File(data.jsFuncParams[param].getParam()).getName() + "\"");
		}else if (data instanceof JSAudio && param == JSAudio.SELECT_AUDIO_URL){
			pw.print("pathForGame+\"audio/" + new File(data.jsFuncParams[param].getParam()).getName() + "\"");
		}else{
			pw.print("\"" + data.jsFuncParams[param] + "\"");
		}
	}

	/** Writes JSScript[] data to disk at the location designated by file.
	 * 
	 * @param scriptData data to write
	 * @param file where to write data
	 * @throws FileSaveErrorException */
	public static void writeScriptDataToDisk(ArrayList<JSScript> scriptData, String file) throws FileSaveErrorException {
		File saveLocation = new File(file);
		checkFile(saveLocation);
		if (file == null){
			throw new FileSaveErrorException("Could not create a new file");
		}
		if (scriptData.size() == 0){
			return;
		}
		PrintWriter pw = getWriter(saveLocation);
		Scanner scan = null;
		for (JSScript script : scriptData){
			try{
				scan = new Scanner(new File(script.getPath()));
				pw.println("var " + CfgLang.JS_FUNC_NAME_SCRIPT + "_" + script.getVarName() + "= {");
				while (scan.hasNextLine()){
					pw.println(scan.nextLine());
				}
				pw.print("};");

			}catch (Exception e){
				e.printStackTrace();
				throw new FileSaveErrorException("Cannot read the script file " + script.getVarName() + " from path " + script.getPath());
			}
		}
		pw.flush();
		pw.close();

		scan.close();
	}

}
