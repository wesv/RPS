package gui.nodes;

import js.JSFuncData;

/** @author Kayler Renslow <br />
 * <br />
 * 
 *         Holds all the items that will pop into the right click menu and actions associated with
 *         each item. */
public abstract class NodeOption{

	
	/** @return String array of all menu items in the right click menu. */
	public String[] getList() {
		return null;
	}
	
	public void loadPopup(JSFuncData func, int pos){}

	/** Called when a menu item was pressed.
	 * 
	 * @param i index of the menu in the right click menu 
	 * @param j */
	public void click(int i, int j) {
		System.out.println("class NodeOption: click[" + i + "]");
	}
}
