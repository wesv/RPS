package util;

/** @author Kayler Renslow<br>
 * <br> */
public interface IWindowCloseEvent{

	
	/** this method is ran when the window is requested to be closed.
	 * 
	 * @return true if the window should be closed, false if it shouldn't */
	public boolean windowClosing();
}
