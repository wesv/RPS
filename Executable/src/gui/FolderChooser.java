package gui;

import javax.swing.JFileChooser;

/** @author Kayler Renslow<br>
 * <br> Simple file chooser that will only be used to select directories.*/
public class FolderChooser extends FileCrawl{

	public FolderChooser(String startLocation) {
		super(startLocation);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
	}
	
	public FolderChooser() {
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
	}

}
