package gui.menus;

import gui.popups.BackgroundPopup;
import gui.popups.ImagePopup;
import gui.popups.ScriptEditor;
import gui.popups.SpritePopup;

import java.awt.event.ActionEvent;

import main.RatPackStudio;
import config.CfgLang;

/** @author Kayler Renslow<br />
 * <br />
 * Class that will be called when File has been selected in the menu bar. */
public class MenuGraphics extends TitleItemListener{

	public static final int TYPE_ADD_IMAGE = 0;
	public static final int TYPE_NEW_SPRITE = 1;
	public static final int TYPE_NEW_BACKGROUND = 2;
	public static final int TYPE_VIEW_SPRITE_SOURCE = 3;

	/** Type of this instance for the menu. */
	private final int type;

	public MenuGraphics(String title, int type) {
		super(title);
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(type==TYPE_ADD_IMAGE){
			new ImagePopup();			
		}else if(type==TYPE_NEW_SPRITE){
			new SpritePopup();			
		}else if(type==TYPE_NEW_BACKGROUND){
			new BackgroundPopup();			
		}else if(type==TYPE_VIEW_SPRITE_SOURCE){
			new ScriptEditor(false, RatPackStudio.getEngineFile(CfgLang.JS_FUNC_NAME_SPRITE + ".js"));
		}
	}

};
