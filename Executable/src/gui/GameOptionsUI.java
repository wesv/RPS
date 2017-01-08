package gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import config.CfgMenus;
import config.CfgStyles;

/** @author Kayler Renslow<br />
 * <br />
 * JPanel that holds the game objects and things tabs. */
@SuppressWarnings("serial")
public class GameOptionsUI extends JPanel{

	private static TreeUI trees[];
	private JTabbedPane tabs;
	private TreeNode[] topNodes;

	public GameOptionsUI() {
		setBackground(CfgStyles.COLOR_LIGHT_GRAY);
		setLayout(new GridLayout());
		tabs = new JTabbedPane();
		trees = new TreeUI[CfgMenus.OPTIONS_LIST.length];
		topNodes = new TreeNode[CfgMenus.OPTIONS_LIST.length];
		buildTabs();
		add(tabs);

	}

	/** Removes everything from all tabs. */
	public void clearTabs() {
		tabs.removeAll();
	}

	/** Builds a new set of tabs with clear trees inside them */
	public void buildTabs() {
		int i = 0;
		JScrollPane treeView;

		for (GameOptionContainer option : CfgMenus.OPTIONS_LIST){
			topNodes[i] = new TreeNode(option.text, option.nodeOption, -1, true);
			trees[i] = new TreeUI(topNodes[i]);
			trees[i].setBackground(CfgStyles.COLOR_LIGHT_GRAY);
			treeView = new JScrollPane(trees[i]);
			tabs.addTab(option.text, treeView);
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
			renderer.setLeafIcon(option.icon);
			trees[i].setCellRenderer(renderer);

			i++;

		}

	}

	/** Adds a new Node to the selected game option tab. Use CfgMenus.GAME_OPTION_SELECT_ integers to select which tab to add to.
	 * 
	 * @param select which tab to add a node
	 * @param string text that will appear for the node */
	public static void addNode(int select, String string) {
		trees[select].addNode(string, -1);
	}

	@Deprecated
	public static void getNode(int select, int index) {
		DefaultTreeModel model = (DefaultTreeModel) trees[select].getModel();
		DefaultMutableTreeNode root = (TreeNode) model.getRoot();
		root.getChildAt(index);
	}

	/** Removes a node from the selected game option tab. Use CfgMenus.GAME_OPTION_SELECT_ integers to select which tab to remove from.
	 * 
	 * @param select which tab to remove a node
	 * @param index index of where to remove the node */
	public static void removeNode(int select, int index) {
		trees[select].deleteItem(index);
	}
}