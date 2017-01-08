package gui;

import gui.menus.MenuItem;
import gui.menus.TitleItemListener;
import gui.toolbar.ToolbarButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import main.RatPackStudio;
import util.IWindowCloseEvent;
import util.WindowListener;
import config.CfgLang;
import config.CfgMenus;
import config.CfgStyles;

/** @author Kayler Renslow<br />
 * <br />
 * Creates the main window. Once the window is created, it adds all the buttons and such to the window and styles them accordingly. I don't really know if the threading is actually working. ^.^ */
@SuppressWarnings("serial")
public class RPSWindow extends JFrame implements Runnable, IWindowCloseEvent{

	private JMenuBar menuBar;
	private MasterUI mainUIPanel = new MasterUI();
	private Thread thread = new Thread(this);

	public RPSWindow() {
		thread.start();
	}
	
	public void buildTabs() {
		mainUIPanel.getOptions().buildTabs();
	}

	public void clearTabs(){
		mainUIPanel.getOptions().clearTabs();
	}

	private void addMenuBarItems(JMenuBar menuBar) {
		JMenu menu;
		JMenuItem menuItem;

		for (MenuItem cfgMenu : CfgMenus.MENU_BAR_LIST){
			menu = new JMenu(cfgMenu.getTitle());
			menu.setForeground(Color.WHITE);

			for (TitleItemListener item : cfgMenu.getMenuList()){
				menuItem = new JMenuItem(item.getTitle());
				menuItem.addActionListener(item);
				menu.add(menuItem);

				menuItem.setBackground(CfgStyles.COLOR_GRAY);
				menuItem.setForeground(Color.WHITE);

			}
			menuBar.add(menu);
		}
	}

	private void addToolbar() {
		JToolBar toolbar = new JToolBar("Rat Pack Tools");
		toolbar.setRollover(true);
		toolbar.setFloatable(false);
		toolbar.setLayout(new GridLayout());
		for (ToolbarButton item : CfgMenus.TOOLBAR_LIST){

			JButton b = new JButton(item.getIcon());

			b.setToolTipText(item.getTitle());
			b.addActionListener(item);
			b.setText(item.getTitle());

			toolbar.add(b);

			toolbar.setBackground(CfgStyles.COLOR_GRAY);
			toolbar.setForeground(Color.WHITE);

		}
		add(toolbar, BorderLayout.PAGE_START);
	}

	@Override
	public void run() {
		menuBar = new JMenuBar();
		menuBar.setBackground(CfgStyles.COLOR_GRAY);

		setJMenuBar(menuBar);
		setTitle(CfgLang.WINDOW_TITLE);
		setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
		addMenuBarItems(menuBar);
		addToolbar();

		mainUIPanel.setBackground(CfgStyles.COLOR_ALMOST_BLACK);
		add(mainUIPanel);
		setBackground(CfgStyles.COLOR_ALMOST_BLACK);
		setForeground(CfgStyles.COLOR_ALMOST_BLACK);
		pack();
		setSize(1000, 700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener(this));
	}

	@Override
	public boolean windowClosing() {
		if (RatPackStudio.needsSave()){
			int entered = JOptionPane.showConfirmDialog(null, "Do you want to exit without saving? All unsaved progress will be lost.");
			if (entered == JOptionPane.YES_OPTION){
				RatPackStudio.forceClose();
				return true;
			}else{
				return false;
			}
		}
		RatPackStudio.forceClose();
		System.out.println("shoud lcose");
		return true;
	}


}
