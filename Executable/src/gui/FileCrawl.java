package gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/** @author Zach King, Kayler Renslow<br>
 * <br> */
@SuppressWarnings("serial")
public class FileCrawl extends JFrame{

	protected JFileChooser fc;

	public FileCrawl() {
		this("", null);
		
	}

	public FileCrawl(String startPath, String fileType) {
		fc = new JFileChooser(startPath);
		
		if (fileType == null){
			return;
		}
		fc.setFileFilter(new FileNameExtensionFilter(fileType, fileType));
	}

	public FileCrawl(String startPath) {
		this(startPath, null);
	}

	/** @return returns null if no file was selected */
	public File open() {
		int returnVal = fc.showOpenDialog(FileCrawl.this);
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
		}
		return file;
	}

	/** @return returns null if no file was selected */
	public File save() {
		int returnVal = fc.showSaveDialog(FileCrawl.this);
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
		}
		return file;

	}
}
