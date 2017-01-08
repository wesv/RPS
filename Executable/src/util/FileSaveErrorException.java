package util;


/** @author Kayler Renslow<br>
 * <br>
 * This exception occurs when the game was trying to save data but an error occurred and the save could not be performed. */
@SuppressWarnings("serial")
public class FileSaveErrorException extends Exception{

	public FileSaveErrorException() {}
	
	public FileSaveErrorException(String string) {
		super(string);
	}

}


