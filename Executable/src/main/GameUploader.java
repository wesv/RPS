package main;

import gui.ConsoleUI;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import config.CfgStyles;
import util.FTPUploader;
import util.FileSaveErrorException;
import util.ServerConnectionException;

/** @author Kayler Renslow<br>
 * <br> */
public class GameUploader{

	private String gameID;
	public static boolean canUpload = false;

	public GameUploader() {
		gameID = RatPackStudio.dataManager.getGameData().getGameID();
	}

	public void uploadFiles() {
		File export = RatPackStudio.getExportLocation();
		File audio = new File(export.getPath() + "/audio");
		File cfg = new File(export.getPath() + "/config");
		File img = new File(export.getPath() + "/images");
		FTPUploader ftp = new FTPUploader();
		try{
			ftp.createFolder("game/" + gameID + "/");
			ftp.createFolder("game/" + gameID + "/config/");
			ftp.createFolder("game/" + gameID + "/audio/");
			ftp.createFolder("game/" + gameID + "/images/");
		}catch (IOException e1){
			e1.printStackTrace();
		}
		ftp.changeDir("game/");
		ftp.changeDir(gameID + "/");
		try{
			ftp.changeDir("audio/");
			ftp.upload(audio.listFiles());
			ftp.changeDir("../config/");
			ftp.upload(cfg.listFiles());
			ftp.changeDir("../images/");
			ftp.upload(img.listFiles());
		}catch (IOException e){
			e.printStackTrace();
			ConsoleUI.printToConsole("Could not upload files to server.", Color.RED);
		}
		ftp.closeConnection();

	}

	public void removeToken(boolean sendGameID, String token) {
		String params = "remove=" + token;
		if (sendGameID){
			params += "&gameID=" + gameID;
		}
		ServerConnection sc = new ServerConnection(params, "http://rpstudios.k-town.ws/devKey.php");
		String received = "";
		try{
			received = sc.send();
		}catch (ServerConnectionException e){
			e.printStackTrace();
			ConsoleUI.printToConsole(e.getMessage(), Color.RED);
			return;
		}
//		if (received.length() == 2){
//		}else{
//			ConsoleUI.printToConsole("Error?: " + received, Color.RED);
//		}
		ConsoleUI.printToConsole("Upload complete. May the force be with you.",CfgStyles.COLOR_DARK_GREEN,true);
		try{
			RatPackStudio.dataManager.saveDataToDisk(RatPackStudio.dataManager.getProjectSave(), true);
		}catch (FileSaveErrorException e){
			e.printStackTrace();
			ConsoleUI.printToConsole(e.getMessage(), Color.RED);
		}

	}

}
