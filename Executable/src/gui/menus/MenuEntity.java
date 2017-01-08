package gui.menus;

import gui.popups.EntityPopup;
import gui.popups.ScriptEditor;

import java.awt.event.ActionEvent;

import config.CfgLang;
import main.RatPackStudio;

/** @author Kayler Renslow<br />
 * <br />
 * Class that will be called when Entity has been selected in the menu bar. */
public class MenuEntity extends TitleItemListener{

	public static final int TYPE_NEW_ENTITY = 0;
	public static final int TYPE_VIEW_SOURCE = 1;

	/** Type of this instance for the menu. */
	private final int type;

	public MenuEntity(String title, int type) {
		super(title);
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (type == TYPE_NEW_ENTITY){
			new EntityPopup();
		}else if (type == TYPE_VIEW_SOURCE){
			new ScriptEditor(false, RatPackStudio.getEngineFile(CfgLang.JS_FUNC_NAME_ENTITY + ".js"));
		}
	}

};
