package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import main.RatPackStudio;

/** @author Kayler Renslow <br />
 * <br />
 * Component that will be added to the GameOptionsUI JPanel. This class holds all the trees for the game options tabs and their right clicking attributes. */
@SuppressWarnings("serial")
public class TreeUI extends JTree{

	public TreeUI(DefaultMutableTreeNode node) {
		super(node);
		setName(node.toString());
		ClickListener clickListen = new ClickListener(this);
		addMouseListener(clickListen);
		addMouseMotionListener(clickListen);
	}

	/** Adds a folder to the tree structure.
	 * 
	 * @param name display name of the folder
	 * @param index index of the node that will be renamed. */
	public void addFolder(String name, int index) {
		DefaultTreeModel model = (DefaultTreeModel) getModel();
		TreeNode root = (TreeNode) model.getRoot();
		if (index > root.getChildCount() || index < 0){
			index = root.getChildCount();
		}
		model.insertNodeInto(new TreeNode(name, root.getNodeOption(), index, true), root, index);
		setCellRenderer(new TreeRenderer());
		model.reload();

		scrollRowToVisible(index + 1);
	}

	/** Adds a new node to the tree structure.
	 * 
	 * @param name display name of the node
	 * @param index index of where the node will be inserted */
	public void addNode(String name, int index) {
		DefaultTreeModel model = (DefaultTreeModel) getModel();
		TreeNode root = (TreeNode) model.getRoot();
		if (index > root.getChildCount() || index < 0){
			index = root.getChildCount();
		}

		model.insertNodeInto(new TreeNode(name, root.getNodeOption(), index, false), root, index);
		model.reload();

		scrollRowToVisible(index + 1);

	}

	/** Delete the node at the given index from the tree.
	 * 
	 * @param index index of the node that will be deleted. */
	public void deleteItem(int index) {
		DefaultTreeModel model = (DefaultTreeModel) getModel();
		DefaultMutableTreeNode root = (TreeNode) model.getRoot();
		root.remove(index);
		model.reload();
	}

	private class ClickListener extends MouseAdapter{
		/** The tree for this listener. */
		private final TreeUI tree;

		/** Node that was left clicked and being dragged. */
		private TreeNode dragNode;

		public ClickListener(TreeUI tree) {
			this.tree = tree;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			TreeNode root = (TreeNode) model.getRoot();

			if (dragNode != null){
				int selRow = tree.getRowForLocation(e.getX(), e.getY());// where the mouse is at in relation to the tree.

				TreeNode mouseOverNode = null;// the node the mouse is currently hovering over
				if (selRow - 1 >= 0){
					mouseOverNode = (TreeNode) root.getChildAt(selRow - 1);

					if (mouseOverNode.getUserObject().equals(dragNode.getUserObject())){
						dragNode = null;
						return;
					}

					if (mouseOverNode.getAllowsChildren()){
						mouseOverNode.add(new TreeNode((String) dragNode.getUserObject(), dragNode.getNodeOption(), dragNode.getInitialPos(), false));
						dragNode.removeFromParent();
					}else{
						model.insertNodeInto(dragNode, (MutableTreeNode) dragNode.getRoot(), selRow - 1);
					}
				}
				model.reload();

				dragNode = null;
			}

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int selRow = tree.getRowForLocation(e.getX(), e.getY());// where the mouse is at in relation to the tree.

			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			TreeNode root = (TreeNode) model.getRoot();

			TreeNode mouseOverNode = null;// the node the mouse is currently hovering over
			if (selRow - 1 >= 0){
				mouseOverNode = (TreeNode) root.getChildAt(selRow - 1);
			}

			if (dragNode != null && selRow - 1 >= 0){
				if (!mouseOverNode.getAllowsChildren()){
					model.insertNodeInto(dragNode, (MutableTreeNode) model.getRoot(), selRow - 1);
				}else{

				}
			}
			model.reload();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int selRow = tree.getRowForLocation(e.getX(), e.getY());
			JPopupMenu menu = null;

			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			DefaultMutableTreeNode root = (TreeNode) model.getRoot();

			TreeNode clickedNode = null;
			if (selRow - 1 >= 0){
				clickedNode = (TreeNode) root.getChildAt(selRow - 1);
			}

			if (e.getButton() == 1 && clickedNode != null && e.getClickCount() == 1){
				dragNode = clickedNode;
				return;
			}

			if (selRow - 1 >= 0 && e.getButton() == 3){
				menu = rightClickNodeHandle(selRow, menu, model, clickedNode);
			}
			if (e.getButton() == 3){

				tree.setSelectionRow(selRow);
				if (menu == null){
					menu = new JPopupMenu();
				}

				/* JMenuItem addFolder = new JMenuItem("Add Folder"); addFolder.addActionListener(new ActionListener(){
				 * @Override public void actionPerformed(ActionEvent arg0) { String name = JOptionPane.showInputDialog(null, "Enter folder name."); if (name != null){ if (name.length() == 0){
				 * tree.addFolder("New Folder", selRow - 1); }else{ tree.addFolder(name, selRow - 1); } } } }); menu.add(addFolder); */

			}
			if (menu != null){
				menu.show(tree, e.getX(), e.getY());
			}
		}

		/** Handles a right click if the user right clicks a node.
		 * 
		 * @param selRow the node index right clicked.
		 * @param menu JPopupMenu that will have options appended to. If null, a new one will be created.
		 * @param model DefaultTreeModel that node exists in
		 * @param node TreeNode that was right clicked
		 * @return JPopupMenu with the options appended to it */
		private JPopupMenu rightClickNodeHandle(int selRow, JPopupMenu menu, DefaultTreeModel model, TreeNode node) {
			if (menu == null){
				menu = new JPopupMenu();
			}

			if (selRow - 1 < 0 || node == null){
				return menu;
			}

			JMenuItem item;
			int i = 0;
			for (String dinosaur : node.getNodeOption().getList()){
				final int ii = i;
				item = new JMenuItem(dinosaur);
				item.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						node.getNodeOption().click(ii, selRow - 1);
					}
				});
				menu.add(item);
				i++;
			}
			JMenuItem edit = new JMenuItem("Edit");
			edit.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					RatPackStudio.dataManager.getGameData().editData(node);
				}
			});
			JMenuItem delete = new JMenuItem("Delete");
			delete.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + node.getUserObject() + "?");
					if (response == 0){
						tree.deleteItem(selRow - 1);
						RatPackStudio.dataManager.getGameData().removeData(node);
					}
				}
			});
			JMenuItem rename = new JMenuItem("Rename");
			rename.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String newName = JOptionPane.showInputDialog(null, "Enter a new name for " + node.getUserObject() + ".");
					if (newName != null){
						if (newName.length() != 0){
							node.setUserObject(newName);
							model.reload();
							RatPackStudio.dataManager.getGameData().renameData(node, newName);
						}
					}
				}
			});
			menu.add(edit);
			menu.add(delete);
			menu.add(rename);
			return menu;
		}

	}

	private class TreeRenderer extends DefaultTreeCellRenderer{

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			TreeNode node = (TreeNode) value;
			if (node.getAllowsChildren()){
				setIcon(getDefaultOpenIcon());
			}
			return this;
		}
	}

}
