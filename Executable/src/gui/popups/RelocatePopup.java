package gui.popups;

import gui.FileCrawl;

import java.io.File;

import javax.swing.JOptionPane;

/** @author Kayler Renslow<br>
 * <br> */

public class RelocatePopup{

	public static File relocate(String missing) {
		File file = null;
		int input = JOptionPane.showConfirmDialog(null, "The file " + missing + " is missing.\n Relocate it?");
		if (input == 0){
			file = new FileCrawl().open();
		}
		return file;
	}

}
