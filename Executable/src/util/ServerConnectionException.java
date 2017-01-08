package util;

/** @author Kayler Renslow<br>
 * <br>
 * This exception occurs when a connection to php server encountered an error. */
@SuppressWarnings("serial")
public class ServerConnectionException extends Exception{

		public ServerConnectionException() {}
		
		public ServerConnectionException(String string) {
			super(string);
		}
}
