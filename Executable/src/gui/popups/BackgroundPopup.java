package gui.popups;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import util.Utils;
import js.JSParam;
import js.functions.JSBackground;
import js.functions.JSImageURL;
import main.RatPackStudio;
import config.CfgLang;

/** @author Kayler Renslow<br>
 * <br>
 * Creates a new "New Background" popup window */
@SuppressWarnings("serial")
public class BackgroundPopup extends GenericPopup{
	private JTextField name = new JTextField(12);
	private JComboBox<String> imageSelection;

	private JComboBox<String> tile = new JComboBox<String>(JSParam.BOOLEAN_OPTIONS);
	private JComboBox<String> fillToScene = new JComboBox<String>(JSParam.BOOLEAN_OPTIONS);
	private final boolean noImages;
	private JButton create = new JButton("Create");
	
	private int dataPos = -1;

	public BackgroundPopup() {
		super("New " + CfgLang.JS_FUNC_NAME_BACKGROUND);
		setSize(300, 200);
		setLayout(new FlowLayout());
		ArrayList<JSImageURL> imageUrls = RatPackStudio.dataManager.getGameData().getImgURLData();
		String[] selection;
		if (imageUrls.size() <= 0){
			selection = new String[1];
			selection[0] = "No Images            ";
			noImages = true;
		}else{
			noImages = false;
			selection = new String[imageUrls.size()];
		}
		for (int i = 0; i < imageUrls.size(); i++){
			selection[i] = imageUrls.get(i).getJSVarName();
		}
		imageSelection = new JComboBox<String>(selection);
		create.addActionListener(new PopupButtonListener(this, 0));
		add(imageSelection);
		add(name);
		add(Utils.getColoredLabel("Tile: "));
		add(tile);
		add(Utils.getColoredLabel("Fill to scene: "));
		add(fillToScene);
		add(create);
	}

	public BackgroundPopup(JSBackground background, int pos) {
		this();
		name.setText(background.getJSFuncName());
		imageSelection.setSelectedItem(background.getImageURLObj());
		tile.setSelectedItem(background.getIsTiled());
		fillToScene.setSelectedItem(background.getFillToScene());
		dataPos = pos;
		create.setText("Save Changes");
	}

	@Override
	public void buttonPressed(int id) {
		if (noImages){
			JOptionPane.showMessageDialog(null, "You can not make a background without an image.");
			return;
		}
		if (name.getText().contains(" ")){
			JOptionPane.showMessageDialog(null, "The name cannot have spaces.");
			return;
		}
		if (name.getText().length() == 0){
			JOptionPane.showMessageDialog(null, "You did not enter a name.");
			return;
		}
		String img = (String) imageSelection.getSelectedItem();
		JSParam[] params = {new JSParam(img, CfgLang.JS_FUNC_VAR_NAME_PREFIX_IMAGE_URL), new JSParam((String) tile.getSelectedItem(), JSParam.TYPE_BOOLEAN), new JSParam((String) fillToScene.getSelectedItem(), JSParam.TYPE_BOOLEAN)};
		JSBackground background = new JSBackground(name.getText(), params);

		if (dataPos >= 0){
			RatPackStudio.dataManager.getGameData().getBackgroundData().set(dataPos, background);
		}else{
			RatPackStudio.dataManager.getGameData().addJSBackground(background);			
		}
		
		System.out.println(background);

		this.dispose();
	}
}
