package main;

import java.io.File;

/** @author Kayler Renslow<br>
 * <br> */
public class ServerConfig{
	/** true if the game is being run on a server. False if the game is being loaded on the developer's computer */
	public static boolean SERVER_HOSTED = false;
	/** Location of the localhost root directory. null if not defined. */
	public static File localHostLocation = null;
}
