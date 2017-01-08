package util;

import gui.ConsoleUI;
import gui.popups.AudioPopup;
import gui.popups.ImagePopup;
import gui.popups.ScriptEditor;

import java.io.File;

/** @author Kayler Renslow<br>
 * <br> */
public class DropInputDecoder{
	public static void decode(File file) {
		if (file.getName().toLowerCase().endsWith(".js")){
			new ScriptEditor(file);
			ConsoleUI.printToConsole("Opening the JS file: " + file.getName());
		}else if (Utils.isTypeFile(file, Utils.IMAGE_FILE)){
			new ImagePopup(file);
			ConsoleUI.printToConsole("Opening the image file: " + file.getName());
		}else if (Utils.isTypeFile(file, Utils.AUDIO_FILE)){
			new AudioPopup(file);
			ConsoleUI.printToConsole("Opening the audio file: " + file.getName());
		}

	}
}
