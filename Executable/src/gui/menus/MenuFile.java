package gui.menus;

import java.awt.event.ActionEvent;

import main.RatPackStudio;

/** @author Kayler Renslow<br />
 * <br />
 * Class that will be called when File has been selected in the menu bar. */
public class MenuFile extends TitleItemListener{

	public static final int TYPE_OPEN = 0;
	public static final int TYPE_SAVE = 1;
	public static final int TYPE_EXIT = 2;
	public static final int TYPE_EXPORT = 3;
	public static final int TYPE_SAVE_AS = 4;
	public static final int TYPE_LOAD_PREVIEW = 5;
	public static final int TYPE_UPLOAD = 6;
	public static final int TYPE_RECOMPILE = 7;
	public static final int TYPE_NEW = 8;

	/** Type of this instance for the menu. */
	private final int type;

	public MenuFile(String title, int type) {
		super(title);
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (type == TYPE_OPEN){
			RatPackStudio.openNewProject();
		}else if (type == TYPE_SAVE_AS){
			RatPackStudio.saveProject(true, false);
		}else if (type == TYPE_SAVE){
			RatPackStudio.saveProject(false, false);
		}else if (type == TYPE_EXIT){
			RatPackStudio.requestClose();
		}else if (type == TYPE_EXPORT){
			RatPackStudio.export();
		}else if (type == TYPE_LOAD_PREVIEW){
			RatPackStudio.loadGamePreview();
		}else if (type == TYPE_UPLOAD){
			RatPackStudio.initUpload();
		}else if (type == TYPE_RECOMPILE){
			RatPackStudio.recompile();
		}else if (type == TYPE_NEW){
			RatPackStudio.createNewProject();
		}

	}

}
