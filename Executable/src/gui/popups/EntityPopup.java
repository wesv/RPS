package gui.popups;

import gui.JComponentCombo;
import gui.ListSelectionPane;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import js.JSArray;
import js.JSParam;
import js.functions.JSEntity;
import js.functions.JSSprite;
import main.RatPackStudio;
import config.CfgLang;
import config.CfgStyles;

/** @author Zach King, Kayler Renslow<br>
 * <br> */
@SuppressWarnings("serial")
public class EntityPopup extends GenericPopup{
	private final JComponentCombo[] components = {new JComponentCombo(new JTextField(12), "Name"), null, null, new JComponentCombo(new JTextField(12), "Initial X Position"), new JComponentCombo(new JTextField(12), "Initial Y Position")};

	private int dataPos = -1;
	private JButton create = new JButton("Create");

	public EntityPopup() {
		super("New " + CfgLang.JS_FUNC_NAME_ENTITY);
		setSize(276, 413);
		setLayout(new FlowLayout());

		String[] sel;
		if (RatPackStudio.dataManager.getGameData().getScriptData().size() <= 0){
			sel = new String[1];
			sel[0] = "No Scripts          ";
		}else{
			sel = new String[RatPackStudio.dataManager.getGameData().getScriptData().size()];
		}
		for (int i = 0; i < RatPackStudio.dataManager.getGameData().getScriptData().size(); i++){
			sel[i] = RatPackStudio.dataManager.getGameData().getScriptData().get(i).getVarName();
		}

		ListSelectionPane lsw = new ListSelectionPane(sel);
		components[1] = new JComponentCombo(lsw, "Scripts");
		components[2] = new JComponentCombo(createComboBox(RatPackStudio.dataManager.getGameData().getSpriteData(), "Sprites", true), "Sprite");

		JButton help = new JButton("?");
		this.getContentPane().setBackground(CfgStyles.COLOR_ALMOST_BLACK);
		help.setBackground(CfgStyles.COLOR_LIGHT_GREEN);

		JLabel label;

		for (JComponentCombo c : components){
			label = new JLabel(c.getText());
			label.setForeground(CfgStyles.COLOR_LIGHT_GRAY);
			add(label);
			add(c.getComponent());
		}

		create.addActionListener(new PopupButtonListener(this, 0));
		help.addActionListener(new PopupButtonListener(this, 1));
		add(help);
		add(create);
		setVisible(true);

	}

	public EntityPopup(JSEntity entity, int pos) {
		this();
		((JTextField) components[0].getComponent()).setText(entity.getJSVarName());
		for (String str : entity.getEntityScripts()){
			((ListSelectionPane) components[1].getComponent()).addItemToSelected(str);
		}
		((JTextField) components[3].getComponent()).setText(entity.getEntityXPos());
		((JTextField) components[4].getComponent()).setText(entity.getEntityYPos());
		dataPos = pos;
		create.setText("Save Changes");
	}

	@Override
	public void buttonPressed(int id) {
		if (id == 0){
			createEntity();
		}
	}

	private void createEntity() {
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

		String sprite = components[2].getComboBoxSelected();
		String prefixSprite = sprite.equals("null") ? "" : CfgLang.JS_FUNC_VAR_NAME_PREFIX_SPRITE;
		JSParam[] params = {new JSParam(components[0].getTextFieldText(), JSParam.TYPE_STRING), new JSArray(((ListSelectionPane) components[1].getComponent()).getItems(), CfgLang.JS_FUNC_VAR_NAME_PREFIX_SCRIPT), new JSParam(sprite, prefixSprite),
				new JSParam(components[3].getTextFieldText(), JSParam.TYPE_INT), new JSParam(components[4].getTextFieldText(), JSParam.TYPE_INT)};

		if (dataPos >= 0){
			RatPackStudio.dataManager.getGameData().getEntityData().set(dataPos, new JSEntity(params));
		}else{
			RatPackStudio.dataManager.getGameData().addJSEntity(new JSEntity(params));
		}

		System.out.println(new JSEntity(params));

		this.dispose();// close window
	}
}
