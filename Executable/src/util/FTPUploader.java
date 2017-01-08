package util;

import gui.ConsoleUI;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import config.CfgStyles;

/** @author Kayler Renslow */
public class FTPUploader{

	private static final String SERVER_URL = "ftp.k-town.ws";
	private static final int SERVER_PORT = 21;
	private static final String USER = "rps@k-town.ws";
	private static final String PASS = "Tw44x+^!ircP";
	private FTPClient ftpClient = new FTPClient();

	
	public FTPUploader() {
		ftpClient = establishConnection();
	}
	
	public void upload(File[] files) throws IOException {
		for (File file : files){
			send(file);
		}// plus.php?requestGPid

	}
	
	public void changeDir(String folderName) {
		try{
			int reply = ftpClient.cwd(folderName);
			System.out.println(reply);
		}catch (IOException e){
			e.printStackTrace();
		}
		try{
			System.out.println(ftpClient.printWorkingDirectory());
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void createFolder(String folderName) throws IOException {
		ftpClient.makeDirectory(folderName);
	}

	private FTPClient establishConnection() {
		FTPClient ftpClient = new FTPClient();
		try{
			ftpClient.connect(SERVER_URL, SERVER_PORT);
			ftpClient.login(USER, PASS);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

		}catch (IOException ex){
			ConsoleUI.printToConsole("Error. Could not establish FTP connection to server.", Color.RED);
			ex.printStackTrace();
		}
		return ftpClient;
	}

	private void send(File file) throws IOException {
		ConsoleUI.printToConsole("Uploading file " + file.getName());
		InputStream inputStream = new FileInputStream(file);
		System.out.println(ftpClient.getStatus());

		boolean done = ftpClient.storeFile(file.getName(), inputStream);
		inputStream.close();

		if (done){
			ConsoleUI.printToConsole("File" + file.getName() + " successfully uploaded",CfgStyles.COLOR_DARK_GREEN);
		}
	}

	public void closeConnection() {
		try{
			if (ftpClient.isConnected()){
				ftpClient.logout();
				ftpClient.disconnect();
			}
		}catch (IOException ex){
			ex.printStackTrace();
			ConsoleUI.printToConsole("Error. The FTP connection closed with errors.", Color.RED);
		}
	}
}
