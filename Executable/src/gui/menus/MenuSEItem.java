package gui.menus;

/** @author Kayler Renslow<br />
 * <br />
 * 
 *         Class that holds the sub-menu of a menu bar items for the Scipt Editor. */
public class MenuSEItem extends MenuItem{

	public MenuSEItem(String title, MenuSEFile[] subList) {
		super(title,subList);
	}

	/** Returns the sub-menu of the menu bar item
	 * 
	 * @return the sub-menu of the menu bar item */
	public MenuSEFile[] getMenuList() {
		return (MenuSEFile[]) subList;
	}

}
