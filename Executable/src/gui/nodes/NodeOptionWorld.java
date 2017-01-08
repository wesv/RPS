package gui.nodes;

import gui.popups.WorldPopup;
import js.JSFuncData;
import js.functions.JSWorld;
import config.CfgMenus;

/** @author Kayler Renslow <br />
 * <br />
 * NodeOption class associated with the World tab in the option pane */
public class NodeOptionWorld extends NodeOption{
	@Override
	public String[] getList() {
		return CfgMenus.OPTIONS_TNODE_WORLDS_MENU;
	}

	@Override
	public void loadPopup(JSFuncData func, int pos) {
		new WorldPopup((JSWorld) func, pos);
	}

}
