package gui.nodes;

import gui.popups.EntityPopup;
import js.JSFuncData;
import js.functions.JSEntity;
import config.CfgMenus;

public class EntitiesNode extends NodeOption{

	@Override
	public void loadPopup(JSFuncData func, int pos) {
		new EntityPopup((JSEntity) func, pos);
	}

	public String[] getList() {
		return CfgMenus.OPTIONS_TNODE_AUDIO_MENU;
	}

}
