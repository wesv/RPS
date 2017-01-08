package gui.popups;

import gui.ListSelectionPane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import util.Utils;
import js.JSArray;
import js.JSParam;
import js.JSScript;
import js.functions.JSWorld;
import main.RatPackStudio;
import config.CfgLang;
import config.CfgStyles;

/** @author Zach this is the World pop that will allow for adding worlds */
@SuppressWarnings("serial")
public class WorldPopup extends GenericPopup{

	private JButton help;
	private JTextField worldID;
	private JLabel explain;
	private JTextField textName;
	private ListSelectionPane ui;
	private ListSelectionPane entities;
	private ListSelectionPane backgrounds;
	private JComboBox<String> initScript;
	private JButton create = new JButton("Create");

	private int dataPos = -1;

	public WorldPopup() {
		super("New " + CfgLang.JS_FUNC_NAME_WORLD);
		setSize(650, 300);
		setResizable(false);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel jLabel1 = new JLabel();
		jLabel1.setText("Name: ");
		jLabel1.setForeground(CfgStyles.COLOR_LIGHT_GRAY);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = .4;
		c.weighty = .8;
		add(jLabel1, c);

		ui = createSelectionPane(RatPackStudio.dataManager.getGameData().getUIData(), "UI");

		c.gridx = 2;
		c.gridy = 1;
		c.weightx = .4;
		c.weighty = .8;
		add(ui, c);

		entities = createSelectionPane(RatPackStudio.dataManager.getGameData().getEntityData(), "Entities");

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = .4;
		c.weighty = .8;
		add(entities, c);

		backgrounds = createSelectionPane(RatPackStudio.dataManager.getGameData().getBackgroundData(), "Backgrounds");
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = .4;
		c.weighty = .8;
		add(backgrounds, c);

		textName = new JTextField(12);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = .5;
		c.weighty = .5;
		add(textName, c);

		ArrayList<JSScript> script = RatPackStudio.dataManager.getGameData().getScriptData();
		String[] election;
		if (script.size() <= 0){
			election = new String[1];
			election[0] = "null";
		}else{
			election = new String[script.size() + 1];
			for (int i = 0; i < script.size(); i++){
				election[i] = script.get(i).getVarName();
			}
			election[script.size()] = "null";
		}
		initScript = new JComboBox<String>(election);
		add(initScript);

		c.gridx = 3;
		c.gridy = 1;
		c.weightx = .4;
		c.weighty = .8;
		create.addActionListener(new PopupButtonListener(this, 1));
		add(create, c);

		help = new JButton("?");
		help.setBackground(CfgStyles.COLOR_LIGHT_GREEN);
		help.addActionListener(new PopupButtonListener(this, 0));
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 2;
		add(help, c);

		explain = new JLabel();
		explain.setText(" ");
		explain.setForeground(CfgStyles.COLOR_LIGHT_GRAY);
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = .0;
		c.gridwidth = 3;
		c.weighty = .5;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(explain, c);

		JLabel w = new JLabel();
		w.setText("                                       ID: ");
		w.setForeground(CfgStyles.COLOR_LIGHT_GRAY);
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = .0;
		c.gridwidth = 3;
		add(w, c);
		worldID = new JTextField(12);
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = .5;
		c.weighty = .5;
		add(worldID, c);

		setVisible(true);
	}

	public WorldPopup(JSWorld func, int pos) {
		this();
		textName.setText(func.getJSVarName());
		worldID.setText(func.getID());
		if (func.getBackgrounds().length > 0){
			for (String str : func.getBackgrounds()){
				backgrounds.addItemToSelected(str);
			}
		}
		if (func.getEntities().length > 0){
			for (String str : func.getEntities()){
				entities.addItemToSelected(str);
			}
		}
		if (func.getUI().length > 0){
			for (String str : func.getUI()){
				ui.addItemToSelected(str);
			}
		}
		dataPos = pos;
		create.setText("Save Changes");
	}

	@Override
	public void buttonPressed(int id) {
		if (id == 0){
			explain.setText("Worlds are places in which defined entities and ui exist. Different worlds have different characteristics.");
		}else if (id == 1){
			createWorld();
		}
	}

	private void createWorld() {
		if (textName.getText().contains(" ")){
			JOptionPane.showMessageDialog(null, "The name can not have spaces or be empty.");
			return;
		}else if (textName.getText().length() == 0){
			JOptionPane.showMessageDialog(null, "You did not enter a value for the name.");
			return;
		}
		if (worldID.getText().contains(" ")){
			JOptionPane.showMessageDialog(null, "The ID can not have spaces or be empty.");
			return;
		}else if (worldID.getText().length() == 0){
			JOptionPane.showMessageDialog(null, "You did not enter a value for ID.");
			return;
		}
		String script = (String) initScript.getSelectedItem();
		String scriptPrefix = script.equals("null") ? "" : CfgLang.JS_FUNC_VAR_NAME_PREFIX_SCRIPT;

		JSParam[] params = {new JSParam(textName.getText(), JSParam.TYPE_STRING), new JSParam(worldID.getText(), JSParam.TYPE_INT), new JSArray(entities.getItems(), CfgLang.JS_FUNC_VAR_NAME_PREFIX_ENTITY),
				new JSArray(ui.getItems(), CfgLang.JS_FUNC_VAR_NAME_PREFIX_UI), new JSArray(backgrounds.getItems(), CfgLang.JS_FUNC_VAR_NAME_PREFIX_BACKGROUND), new JSParam(script, scriptPrefix)};

		JSWorld world = new JSWorld(textName.getText(), params);
		if (!scriptPrefix.equals("null")){
			int sel = initScript.getSelectedIndex();
			if (RatPackStudio.dataManager.getGameData().getScriptData().size() > 0){
				world.setInitScriptObject(RatPackStudio.dataManager.getGameData().getScriptData().get(sel));// -1 because the list contains null and the scriptData array doesn't
			}

		}

		if (dataPos >= 0){
			RatPackStudio.dataManager.getGameData().getWorldData().set(dataPos, world);
		}else{
			RatPackStudio.dataManager.getGameData().addJSWorld(world);
		}
		System.out.println(new JSWorld(textName.getText(), params));

		this.dispose();// close window
	}

}
