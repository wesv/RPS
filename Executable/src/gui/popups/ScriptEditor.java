package gui.popups;

import gui.ConsoleUI;
import gui.FileCrawl;
import gui.menus.MenuSEFile;
import gui.menus.MenuSEItem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import js.JSScript;
import main.RatPackStudio;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import util.FileLoadErrorException;
import util.FileSaveErrorException;
import util.IWindowCloseEvent;
import util.Utils;
import util.WindowListener;

@SuppressWarnings("serial")
public class ScriptEditor extends GenericPopup implements IWindowCloseEvent{
	private JMenuBar menuBar = new JMenuBar();
	private RSyntaxTextArea textArea;
	private JSScript editing;
	private File scriptLoc;
	private boolean needsSaving = false;
	private boolean editable = true;

	/** If true, the GameData class has the script saved into data already and thus only needs to be updated upon save. Otherwise, the script should be saved to GameData. */
	private boolean scriptExists = false;
	
	/** Array of all items that go to the file option in the script editor. */
	private final MenuSEFile[] MENU_SE_FILE_ITEMS = {new MenuSEFile("Import", MenuSEFile.TYPE_IMPORT), new MenuSEFile("Save", MenuSEFile.TYPE_SAVE), new MenuSEFile("Close", MenuSEFile.TYPE_CLOSE)};

	/** Array of all Menu items that are on the menu bar for the script editor. */
	private final MenuSEItem[] MENU_SE_BAR_LIST = {new MenuSEItem("File", MENU_SE_FILE_ITEMS)};

	public ScriptEditor() {
		super("Script Editor - untitled");
		JPanel cp = new JPanel(new BorderLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		textArea = new RSyntaxTextArea();
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
		textArea.setCodeFoldingEnabled(true);
		textArea.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				needsSaving = true;
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}

		});
		setIconImage(new ImageIcon(this.getClass().getResource("/script.png")).getImage());
		InputStream in = getClass().getResourceAsStream("/dark.xml");
		try{
			Theme theme = Theme.load(in);
			theme.apply(textArea);
		}catch (IOException ioe){
			ioe.printStackTrace();
		}

		RTextScrollPane sp = new RTextScrollPane(textArea);
		sp.setFoldIndicatorEnabled(true);
		cp.add(sp);

		setContentPane(cp);
		pack();
		setVisible(true);
		addMenu();
		setJMenuBar(menuBar);
		setSize(800, 500);
		addWindowListener(new WindowListener(this));
	}

	public ScriptEditor(boolean editable, File file) {
		this(file);
		this.editable = editable;
		textArea.setEditable(editable);
	}

	public ScriptEditor(JSScript script) {
		this();
		readScriptFromFile(new File(script.getPath()));
		setExists(true);
		editing = script;
		scriptLoc = new File(script.getPath());
		setTitle("Script Editor - " + script.getVarName());
	}

	public RSyntaxTextArea getTextArea() {
		return textArea;
	}

	private void addMenu() {
		JMenu menu;
		JMenuItem menuItem;

		for (MenuSEItem cfgMenu : MENU_SE_BAR_LIST){
			menu = new JMenu(cfgMenu.getTitle());
			// menu.setForeground(Color.WHITE);

			for (MenuSEFile item : cfgMenu.getMenuList()){
				menuItem = new JMenuItem(item.getTitle());
				menuItem.addActionListener(item);
				menu.add(menuItem);
				item.setScriptEditor(this);
				// menuItem.setBackground(CfgStyles.COLOR_GRAY);
				// menuItem.setForeground(Color.WHITE);

			}
			menuBar.add(menu);
		}

	}

	public ScriptEditor(File f) {
		this();
		readScriptFromFile(f);
		setTitle("Script Editor - " + f.getName());
		needsSaving = true;
	}

	private void readScriptFromFile(File loc) {
		Scanner scan = null;
		try{
			scan = Utils.getReader(loc);
		}catch (FileLoadErrorException e){
			ConsoleUI.printToConsole("Could not open the file to read.", Color.RED);
			e.printStackTrace();
		}
		while (scan.hasNextLine()){
			textArea.append(scan.nextLine() + "\n");
		}
		scan.close();

		scriptLoc = loc;
	}

	private void saveScriptToFile(File saveLocation) {
		if (!editable){
			return;
		}
		PrintWriter pw = null;
		try{
			pw = Utils.getWriter(saveLocation);
		}catch (FileSaveErrorException e){
			ConsoleUI.printToConsole("Could not save the JavaScript.", Color.RED);
			e.printStackTrace();
		}

		pw.println(textArea.getText());
		pw.flush();
		pw.close();
		scriptLoc = saveLocation;
		if (!scriptExists){
			setExists(true);

			String name = getScriptName();
			editing = new JSScript(name, saveLocation.getPath());
			RatPackStudio.dataManager.getGameData().addJSScript(editing);
			setTitle("Script Editor - " + editing.getVarName());
		}
	}

	private String getScriptName() {
		String name = JOptionPane.showInputDialog(null, "Enter a name for the Script.");
		if (name == null || name.length() == 0){
			return "untitledScript";
		}
		return name;
	}

	public void setExists(boolean exists) {
		this.scriptExists = exists;
	}

	/** If true, the GameData class has the script saved into data already and thus only needs to be updated upon save. Otherwise, the script should be saved to GameData.
	 * 
	 * @return true if script is saved into GameData, false otherwise */
	public boolean scriptExists() {
		return scriptExists;
	}

	public void openFromData(JSScript script) {
		readScriptFromFile(new File(script.getPath()));
		setExists(true);
	}

	/** Closes the script editor if the user wants to close the script editor (will prompt) */
	public boolean requestClose() {
		if (!editable){
			return true;
		}
		if (needsSaving){
			int response = JOptionPane.showConfirmDialog(this, "Do you want to exit without saving?");
			if (response == JOptionPane.YES_OPTION){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}

	public void save() {
		if (!editable){
			System.out.println("im not saveable");
			return;
		}
		if (scriptLoc == null){
			scriptLoc = new FileCrawl("", "js").save();
			if (scriptLoc == null){
				return;
			}
			if (!scriptLoc.getPath().endsWith(".js")){
				scriptLoc = new File(scriptLoc.getPath() + ".js");
			}
		}
		saveScriptToFile(scriptLoc);
		needsSaving = false;

	}

	public void importScript() {
		File loc = new FileCrawl("", "js").open();
		if (loc == null){
			return;
		}
		readScriptFromFile(loc);
	}

	@Override
	public boolean windowClosing() {
		boolean shouldClose = requestClose();
		if (shouldClose){
			dispose();
			return true;
		}
		setVisible(true);
		setEnabled(true);
		return false;
	}
}
