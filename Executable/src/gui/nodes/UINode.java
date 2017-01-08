package gui.nodes;

import gui.popups.UIPopup;
import js.JSFuncData;
import js.functions.JSUI;
import config.CfgMenus;

public class UINode extends NodeOption{

	@Override
	public void loadPopup(JSFuncData func, int pos) {
		new UIPopup((JSUI) func, pos);
	}

	public String[] getList() {
		return CfgMenus.OPTIONS_TNODE_AUDIO_MENU;
	}

}
