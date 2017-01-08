package gui.nodes;

import gui.ConsoleUI;
import gui.popups.ScriptEditor;
import gui.popups.WorldPopup;
import js.JSFuncData;
import js.JSScript;
import js.functions.JSWorld;
import main.RatPackStudio;
import config.CfgMenus;

/** @author Kayler Renslow <br />
 * <br />
 * NodeOption class associated with the World tab in the option pane */
public class WorldNode extends NodeOption{

	@Override
	public void loadPopup(JSFuncData func, int pos) {
		new WorldPopup((JSWorld) func, pos);
	}

	@Override
	public String[] getList() {
		return CfgMenus.OPTIONS_TNODE_WORLDS_MENU;
	}

	@Override
	public void click(int i, int j) {
		if (i == 0){// open init script
			JSWorld world = RatPackStudio.dataManager.getGameData().getWorldData().get(j);
			JSScript script = world.getInitScriptObject();
			if (script == null){
				ConsoleUI.printToConsole("No Init Script assigned to this world.");
				return;
			}
			new ScriptEditor(script);
		}
	}

}
