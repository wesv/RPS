package gui.nodes;

import gui.popups.ScriptEditor;
import js.JSScript;
import main.RatPackStudio;
import config.CfgMenus;

public class ScriptNode extends NodeOption{

	public String[] getList() {
		return CfgMenus.OPTIONS_TNODE_SCRIPTS_MENU;
	}

	public void loadScriptPopup(JSScript script) {
		new ScriptEditor(script);
	}

}
