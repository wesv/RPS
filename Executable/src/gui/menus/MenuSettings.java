package gui.menus;

import gui.popups.ProjectConfigPopup;
import gui.popups.ServerConfigPopup;

import java.awt.event.ActionEvent;

/** @author Kayler Renslow<br />
 * <br />
 * Class that will be called when File has been selected in the menu bar. */
public class MenuSettings extends TitleItemListener{

	/** Close menu item type. */
	public static final int TYPE_SERVER_CONFIG = 0;

	/** Save menu item type. */
	public static final int TYPE_PROJECT_CONFIG = 1;

	/** Type of this instance for the menu. */
	private final int type;

	public MenuSettings(String title, int type) {
		super(title);
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (type == TYPE_PROJECT_CONFIG){
			new ProjectConfigPopup();
		}else if (type == TYPE_SERVER_CONFIG){
			new ServerConfigPopup();
		}
	}

};
