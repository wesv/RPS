package gui.nodes;

import gui.popups.BackgroundPopup;
import js.JSFuncData;
import js.functions.JSBackground;
import config.CfgMenus;

public class BackgroundNode extends NodeOption{

	public String[] getList() {
		return CfgMenus.OPTIONS_TNODE_AUDIO_MENU;
	}

	@Override
	public void loadPopup(JSFuncData func, int pos) {
		new BackgroundPopup((JSBackground) func, pos);
	}

}
