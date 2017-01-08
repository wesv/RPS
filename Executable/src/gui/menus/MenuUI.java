package gui.menus;

import gui.popups.ScriptEditor;
import gui.popups.UIPopup;

import java.awt.event.ActionEvent;

import main.RatPackStudio;
import config.CfgLang;

/** @author Kayler Renslow<br />
 * <br />
 * Class that will be called when UI has been selected in the menu bar. */
public class MenuUI extends TitleItemListener{

	public static final int TYPE_NEW_UI = 0;
	public static final int TYPE_VIEW_SOURCE = 1;

	/** Type of this instance for the menu. */
	private final int type;

	public MenuUI(String title, int type) {
		super(title);
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (type == TYPE_NEW_UI){
			new UIPopup();
		}else if (type == TYPE_VIEW_SOURCE){
			new ScriptEditor(false, RatPackStudio.getEngineFile(CfgLang.JS_FUNC_NAME_UI + ".js"));
		}
	}

};
