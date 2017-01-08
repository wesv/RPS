package gui.nodes;

import gui.popups.SpritePopup;
import js.JSFuncData;
import js.functions.JSSprite;
import config.CfgMenus;

public class SpritesNode extends NodeOption{

	public String[] getList() {
		return CfgMenus.OPTIONS_TNODE_AUDIO_MENU;
	}

	@Override
	public void loadPopup(JSFuncData func, int pos) {
		new SpritePopup((JSSprite) func, pos);
	}

}
