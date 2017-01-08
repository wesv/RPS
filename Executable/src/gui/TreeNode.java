package gui;

import gui.nodes.NodeOption;

import javax.swing.tree.DefaultMutableTreeNode;

/** @author Kayler Renslow <br />
 * <br />
 * This class extends javax.swing.tree.DefaultMutableTreeNode and returns the NodeOption associated with this Tree. */
@SuppressWarnings("serial")
public class TreeNode extends DefaultMutableTreeNode{
	private NodeOption option;
	/** This value represents the first index the node was at in the tree before any movement. */
	private final int initialPosition;

	/** Creates a new tree node that doesn't allow children.
	 * 
	 * @param name display name of the node
	 * @param option NodeOption associated with this node
	 * @param initialPos this value represents the first index the node was at in the tree before any movement */
	public TreeNode(String name, NodeOption option, int initialPos) {
		super(name);
		this.option = option;
		this.initialPosition = initialPos;
	}

	/** Creates a tree node with children if allowsChildren is true.
	 * 
	 * @param name display name of the node
	 * @param option NodeOption associated with this node
	 * @param allowsChildren true if this node is allowed to have children, false otherwise
	 * @param initialPos this value represents the first index the node was at in the tree before any movement */
	public TreeNode(String name, NodeOption option, int initialPos, boolean allowsChildren) {
		super(name, allowsChildren);
		this.option = option;
		this.initialPosition = initialPos;
	}

	/** Gets the NodeOption associated with this TreeNode
	 * 
	 * @return the NodeOption associated with this TreeNode */
	public NodeOption getNodeOption() {
		return option;
	}
	
	public int getInitialPos(){
		return initialPosition;
	}

	/** Sets the NodeOption to the defined one
	 * 
	 * @param option new NodeOption */
	public void setOption(NodeOption option) {
		this.option = option;
	}

}
