package util;

/** @author Kayler Renslow<br>
 * <br>
 * This exception occurs when the game was trying to load data but an error occurred and the load could not be performed. */
@SuppressWarnings("serial")
public class FileLoadErrorException extends Exception{

	public FileLoadErrorException() {}

	public FileLoadErrorException(String string) {
		super(string);
	}
}
