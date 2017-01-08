package gui.nodes;

import gui.popups.AudioPopup;
import js.JSFuncData;
import js.functions.JSAudio;
import config.CfgMenus;

public class AudioNode extends NodeOption{

	@Override
	public void loadPopup(JSFuncData func, int pos) {
		new AudioPopup((JSAudio) func, pos);
	}

	public String[] getList() {
		return CfgMenus.OPTIONS_TNODE_AUDIO_MENU;
	}

}
