package gui.nodes;

import gui.popups.ImagePopup;
import js.JSFuncData;
import js.functions.JSImageURL;
import config.CfgMenus;

public class ImagesNode extends NodeOption{

	@Override
	public void loadPopup(JSFuncData func, int pos) {
		new ImagePopup((JSImageURL) func, pos);
	}

	public String[] getList() {
		return CfgMenus.OPTIONS_TNODE_AUDIO_MENU;
	}
}
