package main;

import gui.ConsoleUI;
import gui.FileCrawl;
import gui.FolderChooser;
import gui.RPSWindow;
import gui.popups.GameUploadPopup;

import java.awt.Color;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import util.DropInput;
import util.ErrorLog;
import util.FileLoadErrorException;
import util.FileSaveErrorException;
import util.ServerConnectionException;
import util.Utils;
import config.CfgLang;
import config.CfgStyles;

/** @author Kayler Renslow <br>
 * <br>
 * Creates the Rat Pack Studio application. */
public class RatPackStudio{

	private static RPSWindow window;
	private static boolean needsSave;
	public static final DataManager dataManager = new DataManager();
	private static File rpsRoaming;
	private static File rpsRomData;
	public static boolean firstLoad = true;

	public static RPSWindow getWindow() {
		return window;
	}

	public static void setNeedsSave(boolean val) {
		needsSave = val;
	}

	public static boolean needsSave() {
		return needsSave;
	}

	public RatPackStudio() {
		window = new RPSWindow();
		new DropTarget(window, new DropInput());
	}

	public static void main(String[] args) {
		try{
			new RatPackStudio();
			checkRoaming();
		}catch (Exception e){
			PrintWriter pw = ErrorLog.getLog();
			e.printStackTrace(pw);
		}
	}

	private static void checkRoaming() {
		String rpsRomLoc = System.getenv("APPDATA") + "/Rat Pack Studios";
		rpsRoaming = new File(rpsRomLoc);
		rpsRomData = new File(rpsRomLoc + "/data");
		if (!rpsRoaming.exists()){
			rpsRoaming.mkdir();
		}
		if (!rpsRomData.exists()){
			try{
				rpsRomData.createNewFile();
			}catch (Exception e){
				e.printStackTrace();
			}
			downloadEngine();
			return;
		}
		boolean redownload = updateEngineData();
		if (redownload){
			downloadEngine();
		}

	}

	public static File getEngineFile(String fileName) {
		return new File(rpsRoaming.getPath() + "/engine/" + fileName);
	}

	/** Downloads the engine files from the server and saves the roaming data value to 1. */
	public static void downloadEngine() {
		try{
			downloadEngineFiles();
		}catch (Exception e){
			e.printStackTrace();
			ConsoleUI.printToConsole("Could not download engine files from server.", Color.RED);
		}
	}

	public static void writeToRoamingData(int value) {
		PrintWriter pw = null;
		try{
			pw = Utils.getWriter(rpsRomData);
		}catch (FileSaveErrorException e){
			e.printStackTrace();
			ConsoleUI.printToConsole("Couldn't create roaming data.", Color.RED);
			return;
		}
		pw.println(value);
		pw.flush();
		pw.close();

	}

	/** Checks the roaming data
	 * 
	 * @return true if the engine data is out of data and should be updated, false if up to date */
	private static boolean updateEngineData() {
		Scanner scan = null;
		try{
			scan = new Scanner(rpsRomData);
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		boolean shouldUpdate = false;
		try{
			shouldUpdate = readDataFromRoaming(scan);
		}catch (Exception e){
			e.printStackTrace();
			ConsoleUI.printToConsole("Couldn't read data from roaming.", Color.RED);
			return true;
		}
		scan.close();
		return shouldUpdate;
	}

	/** Reads roaming data
	 * 
	 * @param scan scanner used to read the data
	 * @return true if the data was 0, false if data was 1 */
	private static boolean readDataFromRoaming(Scanner scan) throws MalformedURLException, IOException {
		while (scan.hasNextInt()){
			int read = scan.nextInt();
			if (read != 1){
				return true;
			}
		}
		return false;
	}

	/** Downloads the engine files from the server and saves the roaming data value to 1. Throws exceptions if the data couldn't be downloaded */
	private static void downloadEngineFiles() throws MalformedURLException, IOException {
		File engine = new File(rpsRoaming.getPath() + "/engine");
		engine.mkdir();
		for (String str : CfgLang.ENGINE_FILES){
			ConsoleUI.printToConsole("Downloading engine file " + str + " from server.");
			FileUtils.copyURLToFile(new URL(CfgLang.ENGINE_DOWNLOAD_FILE_PREFIX + str), new File(engine.getPath() + "/" + str));
		}
		ConsoleUI.printToConsole("Engine files download complete");
		writeToRoamingData(1);
	}

	public static void requestClose() {
		window.windowClosing();
	}

	public static void forceClose() {
		System.exit(0);
	}

	/** Exports game data to a file
	 * 
	 * @param exportLocation place to export to
	 * @param copyArtFiles if true, audio and image files will be copied over as well. */
	private static void export(File exportLocation, boolean copyArtFiles) {
		File file;
		if (exportLocation == null){
			ConsoleUI.printToConsole("Export location is invalid or never specified.");
			return;
		}
		file = new File(exportLocation.getPath());
		file.mkdir();
		try{
			dataManager.getGameData().compileJS(file);
			ConsoleUI.printToConsole("JavaScript compilation successful.", CfgStyles.COLOR_DARK_GREEN);
		}catch (FileSaveErrorException e1){
			ConsoleUI.printToConsole(e1.getMessage(), Color.RED);
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		if (copyArtFiles){
			file = new File(exportLocation.getPath() + "/audio");
			file.mkdir();
			dataManager.getGameData().copyAudio(file);

			file = new File(exportLocation.getPath() + "/images");
			file.mkdir();
			dataManager.getGameData().copyImages(file);
		}

		File engineCopyLocation = new File(exportLocation.getPath() + "/engine");
		engineCopyLocation.mkdir();
		File engineSrc = new File(rpsRoaming.getPath() + "/engine");
		try{
			FileUtils.copyDirectory(engineSrc, engineCopyLocation);
		}catch (IOException e){
			e.printStackTrace();
			ConsoleUI.printToConsole("Couldn't copy engine src from roaming", Color.RED);
		}
		File gameExportLoc = new File(exportLocation.getPath() + "/game.html");
		if (!gameExportLoc.exists()){
			Utils.copyFile(new File(engineCopyLocation.getPath() + "/game.html"), gameExportLoc);
		}
		dataManager.getProjectConfig().setExportLocation(exportLocation);
		ConsoleUI.printToConsole("Export to " + exportLocation.getPath() + " successful.", CfgStyles.COLOR_DARK_GREEN);

	}

	/** Exports the game data to a new location */
	public static void export() {
		File file = new FolderChooser("").save();
		if (file == null){
			return;
		}
		file.mkdir();

		export(file, true);
	}

	public static void loadGamePreview() {
		File host = getExportLocation();
		if (host == null){
			ConsoleUI.printToConsole("Preview could not be loaded. Please export the data from File->Export.");
			return;
		}
		try{
			ConsoleUI.printToConsole("Reading game.html from " + host.getPath());
			ConsoleUI.printToConsole("Loading game preview on default browser...", Color.BLUE);
			if (ServerConfig.SERVER_HOSTED){
				new GamePreviewLoader(new URI("http://localhost:80/game.html")).load();
			}else{
				new GamePreviewLoader(new File(host.getPath() + "\\game.html").toURI()).load();
			}
		}catch (Exception e){
			e.printStackTrace();
			ConsoleUI.printToConsole("Could not load preview (An error occured).", Color.RED);
		}
	}

	public static void recompile() {
		File f = getExportLocation();

		if (f == null){
			JOptionPane.showMessageDialog(null, "You must export before you can recompile the code.");
			f = new FolderChooser("").save();
			if (f == null){
				return;
			}
			f.mkdir();
		}
		export(f, true);
		ConsoleUI.printToConsole("Recompile successful.");
	}

	public static File getExportLocation() {
		File host;
		if (ServerConfig.SERVER_HOSTED){
			host = ServerConfig.localHostLocation;
		}else{
			host = dataManager.getProjectConfig().getExportLocation();
		}
		return host;
	}

	public static boolean checkToken(String token) {
		ServerConnection dt = new ServerConnection("verify=" + token, "http://rpstudios.k-town.ws/devKey.php");
		String received = "";
		try{
			received = dt.send();
		}catch (ServerConnectionException e){
			e.printStackTrace();
			ConsoleUI.printToConsole(e.getMessage(), Color.RED, true);
		}
		String gameID = "-1";
		System.out.println("RAW received: " + received);
		if (received.contains("error") || received.contains("Error")){
			return false;
		}
		if (received.contains(".")){
			received = received.substring(0, received.indexOf('.'));// get rid of the decimals
		}
		System.out.println("Received converted: " + received);
		gameID = received;
		if (dataManager.getGameData().getGameID().contains("-")){
			dataManager.getGameData().setGameID(gameID);
		}
		return !gameID.contains("-");
	}

	public static void uploadGame(String token) {
		if (getExportLocation() == null){
			ConsoleUI.printToConsole("Can not upload a game that has not been exported first.");
			JOptionPane.showMessageDialog(null, "Can not upload a game that has not been exported first.");
			return;
		}
		GameUploader upload = new GameUploader();
		boolean hasUploaded = dataManager.getGameData().hasUploaded();
		upload.uploadFiles();
		upload.removeToken(!hasUploaded, token);// if the game has been uploaded once before, we do not want to send the game id since there already is one for this game

		dataManager.getGameData().setHasUploaded(true);
	}

	public static void openNewProject() {
		File f = new FileCrawl("", "rps").open();
		if (f == null){
			return;
		}

		if (!firstLoad){
			window.clearTabs();
			window.buildTabs();
		}
		firstLoad = false;

		try{
			RatPackStudio.dataManager.loadDataFromDisk(f);
		}catch (FileLoadErrorException e1){
			ConsoleUI.printToConsole(e1.getMessage(), Color.RED);
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}

	}
	
	public static void createNewProject(){
		if (needsSave()){
			int entered = JOptionPane.showConfirmDialog(null, "Do you want to continue without saving? All unsaved progress will be lost.");
			if (entered != 0){// didn't push yes
				return;
			}
		}
		File f = new FileCrawl("", "rps").save();
		if (f == null){
			return;
		}
		try{
			RatPackStudio.dataManager.saveDataToDisk(f, false);
		}catch (FileSaveErrorException e1){
			ConsoleUI.printToConsole(e1.getMessage(), Color.RED);
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		firstLoad = false;
		window.clearTabs();
		window.buildTabs();
		
	}

	public static void saveProject(boolean newSave, boolean override) {
		File location = dataManager.getProjectSave();
		if (newSave || location == null){
			location = new FileCrawl("", "rps").save();
			if (location == null){
				return;
			}
		}

		try{
			RatPackStudio.dataManager.saveDataToDisk(location, override);
		}catch (FileSaveErrorException e1){
			ConsoleUI.printToConsole(e1.getMessage(), Color.RED);
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		firstLoad = false;
	}

	public static void initUpload() {
		if (RatPackStudio.dataManager.getProjectSave() == null){
			JOptionPane.showMessageDialog(null, "You must save your progress before uploading.");
			File file = new FolderChooser("").save();
			if (file == null){
				return;
			}
			saveProject(true, false);
		}
		new GameUploadPopup();
	}

}
