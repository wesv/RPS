package gui.menus;

import gui.popups.ScriptEditor;

import java.awt.event.ActionEvent;

import main.RatPackStudio;

/** @author Kayler Renslow<br />
 * <br />
 * Class that will be called when Script has been selected in the menu bar. */
public class MenuScripts extends TitleItemListener{

	public static final int TYPE_OPEN_EDITOR = 0;

	public static final int TYPE_UPDATE_ENGINE = 1;

	public static final int TYPE_IMPORT_SCRIPT = 2;

	/** Type of this instance for the menu. */
	private final int type;

	public MenuScripts(String title, int type) {
		super(title);
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (type == TYPE_OPEN_EDITOR){
			new ScriptEditor();
		}else if(type==TYPE_UPDATE_ENGINE){
			RatPackStudio.downloadEngine();
		}else if(type==TYPE_IMPORT_SCRIPT){
			new ScriptEditor().importScript();
		}
	}

};
