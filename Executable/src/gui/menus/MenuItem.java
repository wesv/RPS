package gui.menus;

/** @author Kayler Renslow<br />
 * <br />
 * 
 *         Class that holds the sub-menu of a menu bar item. */
public class MenuItem{

	protected TitleItemListener[] subList;
	private String title;

	public MenuItem(String title, TitleItemListener[] subList) {
		this.subList = subList;
		this.title = title;
	}

	/** Returns the sub-menu of the menu bar item
	 * 
	 * @return the sub-menu of the menu bar item */
	public TitleItemListener[] getMenuList() {
		return subList;
	}

	/** Gets the title of the main parent node.
	 * 
	 * @return title of the main parent node. */
	public String getTitle() {
		return title;
	}

}
