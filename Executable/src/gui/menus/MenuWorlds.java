package gui.menus;

import gui.popups.ScriptEditor;
import gui.popups.WorldPopup;

import java.awt.event.ActionEvent;

import main.RatPackStudio;
import config.CfgLang;

/** @author Kayler Renslow<br />
 * <br />
 * Class that will be called when Worlds has been selected in the menu bar. */
public class MenuWorlds extends TitleItemListener{

	public static final int TYPE_NEW_WORLD = 0;
	public static final int TYPE_VIEW_SOURCE = 1;

	/** Type of this instance for the menu. */
	private final int type;

	public MenuWorlds(String title, int type) {
		super(title);
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (type == TYPE_NEW_WORLD){
			new WorldPopup();
		}else if (type == TYPE_VIEW_SOURCE){
			new ScriptEditor(false, RatPackStudio.getEngineFile(CfgLang.JS_FUNC_NAME_WORLD + ".js"));
		}
	}

};
