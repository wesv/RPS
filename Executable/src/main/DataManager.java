package main;

import gui.ConsoleUI;
import gui.popups.RelocatePopup;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import config.CfgLang;
import js.JSArray;
import js.JSFuncData;
import js.JSParam;
import js.JSScript;
import js.functions.JSAudio;
import js.functions.JSBackground;
import js.functions.JSEntity;
import js.functions.JSImageURL;
import js.functions.JSSprite;
import js.functions.JSUI;
import js.functions.JSWorld;
import util.FileLoadErrorException;
import util.FileSaveErrorException;
import util.Utils;

/** @author Kayler Renslow, Zach King<br>
 * <br>
 * This class manages the program's save data. */
public class DataManager{
	private final GameData gameData;
	private ProjectConfig projectConfig = new ProjectConfig();
	private File projectSave;

	public File getProjectSave() {
		return projectSave;
	}

	/** Creates a new DataManager instance. This class is used for saving and loading game data on the disk.
	 * 
	 * @param gameData GameData instance that holds all the game's data. */
	public DataManager() {
		this.gameData = new GameData();
	}

	public GameData getGameData() {
		return gameData;
	}

	/** Checks if the JSAudio and JSImageURL paths are correct. If they aren't the user will be prompted to relocate them. */
	public void checkData() {
		File file;
		for (JSAudio audio : gameData.getAudioURLData()){
			file = new File(audio.getAudioURL());
			if (!file.exists()){
				file = RelocatePopup.relocate(file.getPath());
				audio.setParameter(JSAudio.SELECT_AUDIO_URL, new JSParam(file.getPath(), JSParam.TYPE_STRING));
				RatPackStudio.setNeedsSave(true);
			}
		}

		for (JSImageURL img : gameData.getImgURLData()){
			file = new File(img.getImageURL());
			if (!file.exists()){
				file = RelocatePopup.relocate(file.getPath());
				img.setParameter(JSImageURL.SELECT_IMAGE_URL, new JSParam(file.getPath(), JSParam.TYPE_STRING));
				RatPackStudio.setNeedsSave(true);
			}
		}

		for (JSScript script : gameData.getScriptData()){
			file = new File(script.getPath());
			if (!file.exists()){
				file = RelocatePopup.relocate(file.getPath());
				script.setPath(file.getPath());
				RatPackStudio.setNeedsSave(true);
			}
		}
	}

	/** Checks to see if the file saveLocation already exists. If it does exist, it will ask the user if they want to overwrite it. If the file doesn't exist, a new file will be created and this method will return true.
	 * NOTE: if any errors occur, this method will return false.
	 * 
	 * @param saveLocation file to check existence
	 * @return true if the user wants to proceed with saving, false if they don't. This will return true if the file doesn't exist as well.
	 * @throws FileSaveErrorException throws an exception when a new file could not be created. */
	private boolean checkFile(File saveLocation, boolean override) throws FileSaveErrorException {
		if (saveLocation.exists()){
			if (override){
				return true;
			}
			if (this.projectSave != null){
				if (!saveLocation.getPath().equals(this.projectSave.getPath())){
					int input = JOptionPane.showConfirmDialog(null, "Do you wish to overwrite this file?");
					if (input == JOptionPane.YES_OPTION){
						return true;
					}else{
						return false;
					}
				}else{
					return true;
				}
			}else{
				int input = JOptionPane.showConfirmDialog(null, "Do you wish to overwrite this file?");
				if (input == JOptionPane.YES_OPTION){
					return true;
				}else{
					return false;
				}
			}
		}else{
			try{
				saveLocation.createNewFile();
			}catch (Exception e){
				e.printStackTrace();
				throw new FileSaveErrorException("A new file for saving could not be created.");
			}
		}
		return true;
	}

	/** This saves all game data to disk as a plain text file.
	 * 
	 * @return true if the data could successfully save. False if an error occurred. */
	public boolean saveDataToDisk(File saveLocation, boolean override) throws FileSaveErrorException {
		if (!saveLocation.getPath().endsWith(".rps")){
			saveLocation = new File(saveLocation.getPath() + ".rps");
		}
		if (!checkFile(saveLocation, override)){
			return false;
		}
		PrintWriter pw = Utils.getWriter(saveLocation);
		printData(pw, gameData.getEntityData());
		printData(pw, gameData.getSpriteData());
		printData(pw, gameData.getImgURLData());
		printData(pw, gameData.getAudioURLData());
		printData(pw, gameData.getBackgroundData());
		printData(pw, gameData.getWorldData());
		printData(pw, gameData.getUIData());
		printScripts(pw, gameData.getScriptData());

		printProjectConfig(pw, getProjectConfig());

		printServerConfig(pw);
		pw.println(gameData.getGameID());
		pw.println(gameData.hasUploaded());

		try{
			pw.flush();
			pw.close();
		}catch (Exception e){
			e.printStackTrace();
			throw new FileSaveErrorException("The output stream could not be closed.");
		}
		RatPackStudio.setNeedsSave(false);
		ConsoleUI.printToConsole("Project saved successfully.");
		projectSave = saveLocation;
		return true;
	}

	private void printServerConfig(PrintWriter pw) {
		pw.println(ServerConfig.SERVER_HOSTED);
		if (ServerConfig.localHostLocation == null){
			pw.println("null");
		}else{
			pw.println(ServerConfig.localHostLocation.getPath());
		}
	}

	public void changeProjectConfig(ProjectConfig newConfig) {
		projectConfig = newConfig;
		RatPackStudio.setNeedsSave(true);
	}

	public ProjectConfig getProjectConfig() {
		return projectConfig;
	}

	private void printProjectConfig(PrintWriter pw, ProjectConfig proCon) {
		pw.println(proCon.getName());
		pw.println(proCon.getHeight());
		pw.println(proCon.getWidth());
		pw.println(proCon.getNumPlayers());
		pw.println(proCon.getDescription());
		pw.println(proCon.getBackground());
		pw.println(proCon.getTextColor());
		if (proCon.getExportLocation() == null){
			pw.println("null");
		}else{
			pw.println(proCon.getExportLocation().getPath());
		}
	}

	private void printScripts(PrintWriter pw, ArrayList<JSScript> scriptData) {
		pw.println(scriptData.size());
		if (scriptData.size() == 0){
			return;
		}
		for (JSScript js : scriptData){
			if (js == null || js.isDeleted()){
				continue;
			}
			pw.println(js.getVarName());
			pw.println(js.getPath());
		}

	}

	private JSScript readScriptFromDisk(Scanner scan) {
		JSScript script = new JSScript(scan.nextLine(), scan.nextLine());
		ArrayList<JSWorld> w = gameData.getWorldData();
		for (JSWorld world : w){
			if (world.getInitScript().equals(script.getVarName())){
				world.setInitScriptObject(script);
			}
		}

		return script;
	}

	private void printData(PrintWriter pw, ArrayList<? extends JSFuncData> data) {
		JSParam param;

		pw.println(data.size());
		if (data.size() == 0){
			return;
		}
		int numParams = data.get(0).getNumParams();
		pw.println(numParams);
		for (JSFuncData js : data){
			if (js == null || js.isDeleted()){
				continue;
			}
			pw.println(js.getJSVarName());
			for (int i = 0; i < numParams; i++){
				param = js.getParameter(i);
				pw.println(param.getType());
				if (param.isTypeObject() || param.isTypeArray()){
					pw.println(param.getPrefix());
				}
				pw.println(param.getSavableParam());
			}
		}
	}

	/** This loads all plain text game data from disk.
	 * 
	 * @return true if the data could successfully load. False if an error occurred. */
	public boolean loadDataFromDisk(File saveLocation) throws FileLoadErrorException {
		boolean loaded = false;
		try{
			loaded = loadData(saveLocation);
		}catch (Exception e){
			e.printStackTrace();
			throw new FileLoadErrorException("Cannot load from file " + saveLocation.getPath() + ". (Corrupt or wrong format)");
		}
		checkData();
		projectSave = saveLocation;
		ConsoleUI.printToConsole("Project successfully loaded");
		return loaded;
	}

	private boolean loadData(File saveLocation) throws FileLoadErrorException {
		if (RatPackStudio.needsSave()){
			int entered = JOptionPane.showConfirmDialog(null, "Do you want to continue without saving? All unsaved progress will be lost.");
			if (entered != 0){// didn't push yes
				return false;
			}
		}
		Scanner scan = Utils.getReader(saveLocation);

		int numEntries = 0;

		JSParam[] params;
		String varName;
		int numParams;

		numEntries = Integer.valueOf(scan.nextLine());
		JSEntity[] entities = new JSEntity[numEntries];
		if (numEntries > 0){
			numParams = Integer.valueOf(scan.nextLine());
			for (int i = 0; i < numEntries; i++){
				varName = scan.nextLine();
				params = getParams(scan, numParams);
				entities[i] = new JSEntity(params);
			}
		}
		numEntries = Integer.valueOf(scan.nextLine());
		JSSprite[] sprites = new JSSprite[numEntries];
		if (numEntries > 0){
			numParams = Integer.valueOf(scan.nextLine());
			for (int i = 0; i < numEntries; i++){
				varName = scan.nextLine();
				params = getParams(scan, numParams);
				sprites[i] = new JSSprite(varName, params);
			}
		}

		numEntries = Integer.valueOf(scan.nextLine());
		JSImageURL[] imgurls = new JSImageURL[numEntries];
		if (numEntries > 0){
			numParams = Integer.valueOf(scan.nextLine());
			for (int i = 0; i < numEntries; i++){
				varName = scan.nextLine();
				params = getParams(scan, numParams);
				imgurls[i] = new JSImageURL(varName, params);
			}

		}
		numEntries = Integer.valueOf(scan.nextLine());
		JSAudio[] audiourls = new JSAudio[numEntries];
		if (numEntries > 0){
			numParams = Integer.valueOf(scan.nextLine());
			for (int i = 0; i < numEntries; i++){
				varName = scan.nextLine();
				params = getParams(scan, numParams);
				audiourls[i] = new JSAudio(varName, params);
			}

		}
		numEntries = Integer.valueOf(scan.nextLine());
		JSBackground[] backgrounds = new JSBackground[numEntries];
		if (numEntries > 0){
			numParams = Integer.valueOf(scan.nextLine());
			for (int i = 0; i < numEntries; i++){
				varName = scan.nextLine();
				params = getParams(scan, numParams);
				backgrounds[i] = new JSBackground(varName, params);
			}

		}
		numEntries = Integer.valueOf(scan.nextLine());
		JSWorld[] worlds = new JSWorld[numEntries];
		if (numEntries > 0){
			numParams = Integer.valueOf(scan.nextLine());
			for (int i = 0; i < numEntries; i++){
				varName = scan.nextLine();
				params = getParams(scan, numParams);
				worlds[i] = new JSWorld(varName, params);
			}

		}
		numEntries = Integer.valueOf(scan.nextLine());
		JSUI[] ui = new JSUI[numEntries];
		if (numEntries > 0){
			numParams = Integer.valueOf(scan.nextLine());
			for (int i = 0; i < numEntries; i++){
				varName = scan.nextLine();
				params = getParams(scan, numParams);
				ui[i] = new JSUI(varName, params);
			}

		}
		gameData.setWorldData(worlds);
		numEntries = Integer.valueOf(scan.nextLine());
		JSScript[] scripts = new JSScript[numEntries];
		if (numEntries > 0){
			for (int i = 0; i < numEntries; i++){
				scripts[i] = readScriptFromDisk(scan);
			}
		}

		loadProjectData(scan);
		loadServerData(scan);
		gameData.setData(entities, sprites, scripts, imgurls, audiourls, backgrounds, ui, null);
		String gameID = scan.nextLine();
		boolean hasUploaded = scan.nextLine().toLowerCase().contains("true");
		gameData.setGameID(gameID);
		gameData.setHasUploaded(hasUploaded);

		RatPackStudio.setNeedsSave(false);

		return true;
	}

	private void loadServerData(Scanner scan) {
		ServerConfig.SERVER_HOSTED = scan.nextLine().toLowerCase().contains("true");
		String f = scan.nextLine();
		if (f.length() == 0 || f.equals("null")){
			ServerConfig.localHostLocation = null;
		}else{
			ServerConfig.localHostLocation = new File(f);
			if (!ServerConfig.localHostLocation.exists()){
				ServerConfig.localHostLocation = null;
				ConsoleUI.printToConsole("Local Host Location could not be re-located.");
			}
		}
	}

	private void loadProjectData(Scanner scan) {
		projectConfig.setName(scan.nextLine());
		projectConfig.setHeight(Integer.valueOf(scan.nextLine()));
		projectConfig.setWidth(Integer.valueOf(scan.nextLine()));
		projectConfig.setNumPlayers(Integer.valueOf(scan.nextLine()));
		projectConfig.setDescription(scan.nextLine());
		projectConfig.setBackground(scan.nextLine());
		projectConfig.setTextColor(scan.nextLine());
		String file = scan.nextLine();
		if (!file.equals("null")){
			File f = new File(file);
			if (f.exists()){
				projectConfig.setExportLocation(f);
			}else{
				ConsoleUI.printToConsole("Previous export location for the project doesn't exist.");
			}
		}

	}

	/** Get the JSParams from file where the current scanner is located.
	 * 
	 * @param scan Scanner to use with location prepped
	 * @param numParams number of parameters to read
	 * @return JSParam[] array with parameters
	 * @throws FileLoadErrorException */
	private JSParam[] getParams(Scanner scan, int numParams) throws FileLoadErrorException {
		JSParam[] params = new JSParam[numParams];
		int type;
		String data;
		String[] arr;
		String prefix = "";
		try{
			for (int i = 0; i < numParams; i++){
				type = Integer.valueOf(scan.nextLine());
				if (type == JSParam.TYPE_ARRAY || type == JSParam.TYPE_OBJECT){
					prefix = scan.nextLine();
				}
				data = scan.nextLine();
				if (type == JSParam.TYPE_ARRAY){
					arr = fromStringToArray(data);
					params[i] = new JSArray(arr, prefix);
				}else if (type == JSParam.TYPE_OBJECT){
					params[i] = new JSParam(data, prefix);
				}else{
					params[i] = new JSParam(data, type);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new FileLoadErrorException();
		}
		return params;
	}

	/** Converts a String made from the method 'DataManager.fromStringArrayToString(String[] arr)' and returns a new String array
	 * 
	 * @param str the String to convert
	 * @return a String[] */
	public static String[] fromStringToArray(String str) {
		boolean readingWord = false;

		String length = "";
		int stringLength = 0;
		int stringPos = 0;
		String totalString = "";
		int cur = 0;

		int endSize = str.indexOf('_');
		int size = Integer.valueOf(str.substring(0, endSize));
		if (size == 0){
			return new String[0];
		}
		String[] out = new String[size];
		for (int i = endSize + 1; i < str.length(); i++){
			if (Character.isDigit(str.charAt(i)) && !readingWord){
				length += str.charAt(i);
				continue;
			}else if (str.charAt(i) == '_' && !readingWord){
				readingWord = true;
				stringLength = Integer.valueOf(length);
				length = "";
				continue;
			}
			if (readingWord && stringPos < stringLength){
				totalString += str.charAt(i);
				stringPos++;
			}else if (readingWord){
				out[cur] = totalString;
				cur++;
				totalString = "";
				readingWord = false;
				stringPos = 0;
				length = str.charAt(i) + "";
			}
		}
		out[cur] = totalString;

		return out;
	}

	/** Converts a String array 'arr' into a String formatted as '<#numberOfStrings>_<#OfCharacters>_string1<#OfCharacters>_string2' and returns it. Example return: "4_16_I am long string4_help2_hi6_cheese"
	 * 
	 * @param arr the String array to convert
	 * @return a String representation of the String array arr */
	public static String fromStringArrayToString(String[] arr) {
		String str = arr.length + "_";
		for (int i = 0; i < arr.length; i++){
			str += arr[i].length() + "_" + arr[i];
		}

		return str;
	}

}