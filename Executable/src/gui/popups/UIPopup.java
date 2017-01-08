package gui.popups;

import gui.JComponentCombo;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import js.JSArray;
import js.JSParam;
import js.JSScript;
import js.functions.JSUI;
import main.RatPackStudio;
import util.Utils;
import config.CfgLang;
import config.CfgStyles;

/** @author Zach King, Kayler Renslow */
@SuppressWarnings("serial")
public class UIPopup extends GenericPopup{

	private JButton help;
	private JLabel explain;
	private JComponentCombo[] components = {new JComponentCombo(new JTextField(5), "Width"), new JComponentCombo(new JTextField(5), "Height"), new JComponentCombo(new JTextField(5), "X Position"),
			new JComponentCombo(new JTextField(5), "Y Position"), new JComponentCombo(new JTextField(5), "Name")};
	private JComboBox<String> gobal = new JComboBox<String>(JSParam.BOOLEAN_OPTIONS);
	private JComboBox<String> images;
	private JComboBox<String> entities;
	private JComboBox<String> scripts;
	private boolean noScripts;
	private JButton create = new JButton("Create");

	private int dataPos = -1;

	public UIPopup() {
		super("New " + CfgLang.JS_FUNC_NAME_UI);
		setSize(300, 500);
		// setResizable(false);

		ArrayList<JSScript> script = RatPackStudio.dataManager.getGameData().getScriptData();
		String[] election;
		if (script.size() <= 0){
			election = new String[1];
			election[0] = "No Scripts            ";
			noScripts = true;
		}else{
			election = new String[script.size()];
			for (int i = 0; i < script.size(); i++){
				election[i] = script.get(i).getVarName();
			}
		}
		explain = Utils.getColoredLabel("");
		scripts = new JComboBox<String>(election);
		images = createComboBox(RatPackStudio.dataManager.getGameData().getImgURLData(), "Images", true);
		entities = createComboBox(RatPackStudio.dataManager.getGameData().getEntityData(), "Entities", true);

		add(scripts);

		add(Utils.getColoredLabel("Image: "));
		add(images);

		add(Utils.getColoredLabel("Entity: "));
		add(entities);

		for (JComponentCombo comp : components){
			add(Utils.getColoredLabel(comp.getText() + ": "));
			add(comp.getComponent());
		}

		add(Utils.getColoredLabel("Global UI: "));
		add(gobal);

		help = new JButton("?");
		help.setBackground(CfgStyles.COLOR_LIGHT_GREEN);
		help.addActionListener(new PopupButtonListener(this, 0));
		add(help);

		create.addActionListener(new PopupButtonListener(this, 1));
		add(create);
		add(explain);

	}

	public UIPopup(JSUI ui, int pos) {
		this();
		((JTextField) components[0].getComponent()).setText(ui.getWidth());
		((JTextField) components[1].getComponent()).setText(ui.getHeight());
		((JTextField) components[2].getComponent()).setText(ui.getPosition()[0]);
		((JTextField) components[3].getComponent()).setText(ui.getPosition()[1]);
		((JTextField) components[4].getComponent()).setText(ui.getName());
		dataPos = pos;
		create.setText("Save Changes");
	}

	@Override
	public void buttonPressed(int id) {
		if (id == 0){
			explain.setText("UI generates a clickable area. They can be attached to entities.");
		}else if (id == 1){
			createUI();
		}
	}

	private void createUI() {
		for (JComponentCombo comp : components){
			if (comp.getTextFieldText() != null){// is a text field
				if (comp.getTextFieldText().contains(" ")){
					JOptionPane.showMessageDialog(null, "The " + comp.getText() + " can not have spaces.");
					return;
				}else if (comp.getTextFieldText().length() == 0){
					JOptionPane.showMessageDialog(null, "You did not enter a value for " + comp.getText() + ".");
					return;
				}
			}
		}
		if (noScripts){
			JOptionPane.showMessageDialog(null, "You cannot make a UI without a script attached.");
			return;
		}

		String[] pos = {components[2].getTextFieldText(), components[3].getTextFieldText()};
		String script = (String) scripts.getSelectedItem();
		String entity = (String) entities.getSelectedItem();
		String image = (String) images.getSelectedItem();

		JSParam[] params = {new JSParam(components[4].getTextFieldText(), JSParam.TYPE_STRING), new JSArray(pos), new JSParam(components[0].getTextFieldText(), JSParam.TYPE_INT), new JSParam(components[1].getTextFieldText(), JSParam.TYPE_INT),
				new JSParam(gobal.getSelectedItem() + "", JSParam.TYPE_BOOLEAN), new JSParam(entity, (entity.equals("null") ? "" : CfgLang.JS_FUNC_VAR_NAME_PREFIX_ENTITY)), new JSParam(image, (image.equals("null") ? "" : CfgLang.JS_FUNC_VAR_NAME_PREFIX_IMAGE_URL)),
				new JSParam(script, (script.equals("null") ? "" : CfgLang.JS_FUNC_VAR_NAME_PREFIX_SCRIPT))};

		if (dataPos >= 0){
			RatPackStudio.dataManager.getGameData().getUIData().set(dataPos, new JSUI(components[4].getTextFieldText(), params));
		}else{
			RatPackStudio.dataManager.getGameData().addJSUI(new JSUI(components[4].getTextFieldText(), params));
		}
		System.out.println(new JSUI(components[4].getTextFieldText(), params));

		this.dispose();// close window
	}
}
