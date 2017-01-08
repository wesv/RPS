package gui;

import gui.nodes.NodeOption;

import javax.swing.ImageIcon;

/** @author Kayler Renslow <br />
 * <br />
 *         Container class to hold anything that goes to the tree nodes. */
public class GameOptionContainer{

	/** Image that goes to the nodes. */
	public ImageIcon icon;

	/** Text that goes to the parent nodes. */
	public String text;

	/** NodeOption that goes to the nodes of a certain branch. */
	public NodeOption nodeOption;

	/** Container class to hold anything that goes to the tree nodes.
	 * 
	 * @param imgPath path to the image of the tree node
	 * @param text text that goes to each parent node
	 * @param nodeOption Node Option that holds the event for when a node of a certain branch is
	 *        clicked. */
	public GameOptionContainer(String imgPath, String text, NodeOption nodeOption) {
		this.icon = new ImageIcon(getClass().getResource("/" + imgPath));
		this.text = text;
		this.nodeOption = nodeOption;
	}
}
