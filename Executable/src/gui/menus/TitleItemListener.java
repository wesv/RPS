package gui.menus;

import java.awt.event.ActionListener;

/** @author Kayler Renslow<br />
 * <br />
 * 
 *         Class that holds the title of an item and the click actions
 *         associated with it. */
public abstract class TitleItemListener implements ActionListener{
	private String title;

	public TitleItemListener(String title) {
		this.title = title;
	}

	/** @return the title of the sub-item of a menu bar menu */
	public String getTitle() {
		return title;
	}

}