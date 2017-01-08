package gui.menus;

import gui.ConsoleUI;
import gui.FileCrawl;
import gui.popups.ScriptEditor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Scanner;

/** @author Kayler Renslow<br />
 * <br />
 * Class that will be called when File has been selected in the menu bar. */
public class MenuSEFile extends TitleItemListener{

	/** Save menu item type. */
	public static final int TYPE_SAVE = 0;

	/** Close the executable */
	public static final int TYPE_CLOSE = 1;

	public static final int TYPE_IMPORT = 2;

	/** Type of this instance for the menu. */
	private final int type;

	private ScriptEditor scriptEditor;

	public MenuSEFile(String title, int type) {
		super(title);
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (type == TYPE_SAVE){
			scriptEditor.save();
		}else if (type == TYPE_CLOSE){
			scriptEditor.windowClosing();
		}else if (type == TYPE_IMPORT){
			scriptEditor.importScript();
		}

	}

	public void setScriptEditor(ScriptEditor se) {
		this.scriptEditor = se;
	}

};
