package util;

import java.awt.event.WindowEvent;



/**
 * @author Kayler Renslow<br><br>
 *
 */
public class WindowListener implements java.awt.event.WindowListener{
	private IWindowCloseEvent window;

	public WindowListener(IWindowCloseEvent window) {
		this.window = window;
	}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		window.windowClosing();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}

}
