package gui.menus;

import gui.popups.AudioPopup;

import java.awt.event.ActionEvent;

/** @author Kayler Renslow<br />
 * <br />
 * 
 *         Class that will be called when Audio has been selected in the menu bar. */
public class MenuAudio extends TitleItemListener{

	public static final int TYPE_NEW_AUDIO = 0;

	/** Type of this instance for the menu. */
	private final int type;

	public MenuAudio(String title, int type) {
		super(title);
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new AudioPopup();
	}

};
