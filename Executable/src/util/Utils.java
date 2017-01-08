package util;

import gui.ConsoleUI;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JLabel;

import config.CfgStyles;

/** @author Kayler Renslow<br>
 * <br> */
public class Utils{
	public static final String[] IMAGE_FILE = {".jpg", ".png", ".bmp"};
	public static final String[] AUDIO_FILE = {".wav", ".mp3"};

	/** Creates a new JLabel with text and with the foreground set dictated by styles
	 * 
	 * @param text text that is in the label
	 * @return new JLabel */
	public static JLabel getColoredLabel(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(CfgStyles.COLOR_LIGHT_GRAY);
		return label;
	}

	public static boolean isTypeFile(File file, String[] endings) {
		for (String str : endings){
			if (file.getName().toLowerCase().endsWith(str)){
				return true;
			}
		}
		return false;
	}

	public static PrintWriter getWriter(File saveLocation) throws FileSaveErrorException {
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(saveLocation);
		}catch (IOException e){
			e.printStackTrace();
			throw new FileSaveErrorException("Could not load file for writing.");
		}
		return pw;
	}

	public static Scanner getReader(File saveLocation) throws FileLoadErrorException {
		Scanner scan = null;
		try{
			scan = new Scanner(saveLocation);
		}catch (IOException e){
			e.printStackTrace();
			throw new FileLoadErrorException("Could not load file for reading.");
		}
		return scan;
	}

	public static void copyFile(File toCopy, File newFile) {
		try{
			Files.copy(toCopy.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}catch (IOException e){
			e.printStackTrace();
			ConsoleUI.printToConsole("An error occured when copying file " + toCopy.getPath() + ".", Color.RED);
		}
	}

}
