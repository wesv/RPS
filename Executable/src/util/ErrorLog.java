package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/** @author Kayler Renslow<br>
 * <br> */
public class ErrorLog{

	public static PrintWriter getLog() {
		File f = new File("crash_" + System.currentTimeMillis()+".txt");
		try{
			f.createNewFile();
		}catch (IOException e1){
			e1.printStackTrace();
		}
		PrintWriter pw = null;
		try{
			pw = Utils.getWriter(f);
		}catch (FileSaveErrorException e){
			e.printStackTrace();
		}
		
		return pw;
	}
}
