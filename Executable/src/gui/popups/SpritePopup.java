package gui.popups;

import gui.JComponentCombo;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import util.Utils;
import js.JSParam;
import js.functions.JSImageURL;
import js.functions.JSSprite;
import main.RatPackStudio;
import config.CfgLang;

/** @author Kayler Renslow<br>
 * <br>
 * Creates a new "New Sprite" popup */
@SuppressWarnings("serial")
public class SpritePopup extends GenericPopup{
	private JComponentCombo[] components = {new JComponentCombo(new JTextField(12), "Name"), new JComponentCombo(new JTextField(12), "Frames per Second"), new JComponentCombo(new JTextField(12), "Number of Frames"),
			new JComponentCombo(new JComboBox<String>(JSParam.BOOLEAN_OPTIONS), "Play animation once")};
	private boolean noImages;
	private JComboBox<String> imgurls;
	private int dataPos = -1;
	private JButton create = new JButton("Create");

	public SpritePopup() {
		super("New " + CfgLang.JS_FUNC_NAME_SPRITE);
		ArrayList<JSImageURL> urls = RatPackStudio.dataManager.getGameData().getImgURLData();
		String[] imgs;
		if (urls.size() <= 0){
			imgs = new String[1];
			imgs[0] = "No Images               ";
			noImages = true;
		}else{
			imgs = new String[urls.size()];
		}
		for (int i = 0; i < urls.size(); i++){
			imgs[i] = urls.get(i).getJSVarName();
		}
		imgurls = new JComboBox<>(imgs);
		add(imgurls);

		for (JComponentCombo comp : components){
			add(Utils.getColoredLabel(comp.getText()+": "));
			add(comp.getComponent());
		}

		create.addActionListener(new PopupButtonListener(this, 0));
		add(create);
	}

	@SuppressWarnings("unchecked")
	public SpritePopup(JSSprite sprite, int pos) {
		this();
		((JTextField) components[0].getComponent()).setText(sprite.getJSVarName());
		((JTextField) components[1].getComponent()).setText(sprite.getSpriteFrameSec());
		((JTextField) components[2].getComponent()).setText(sprite.getSpriteFrameCount());
		((JComboBox<String>) components[3].getComponent()).setSelectedItem(sprite.getSpriteAnimationReplay());
		dataPos = pos;
		create.setText("Save Changes");
	}

	@Override
	public void buttonPressed(int id) {
		if (noImages){
			JOptionPane.showMessageDialog(null, "You can not make a sprite without an image.");
			return;
		}
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

		String img = (String) imgurls.getSelectedItem();

		JSParam[] params = {new JSParam(img, CfgLang.JS_FUNC_VAR_NAME_PREFIX_IMAGE_URL), new JSParam(components[1].getTextFieldText(), JSParam.TYPE_INT), new JSParam(components[2].getTextFieldText(), JSParam.TYPE_INT),
				new JSParam(components[3].getComboBoxSelected(), JSParam.TYPE_BOOLEAN)};
		JSSprite sprite = new JSSprite(components[0].getTextFieldText(), params);
		if (dataPos >= 0){
			RatPackStudio.dataManager.getGameData().getSpriteData().set(dataPos, sprite);
		}else{
			RatPackStudio.dataManager.getGameData().addJSSprite(sprite);			
		}
		
		System.out.println(sprite);
		this.dispose();
	}
}
