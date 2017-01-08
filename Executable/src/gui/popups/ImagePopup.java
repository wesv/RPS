package gui.popups;

import gui.FileCrawl;
import gui.ImagePreview;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import js.JSArray;
import js.JSParam;
import js.functions.JSImageURL;
import main.RatPackStudio;
import config.CfgLang;
import config.CfgStyles;

/** @author Zach King, Kayler Renslow <br>
 * <br>
 * This class allows the user to locate an image, preview it, and save it to game data. */
@SuppressWarnings("serial")
public class ImagePopup extends GenericPopup{
	// private JButton open;
	private String file = null;
	private JTextField textName = new JTextField(12);
	private Dimension size;
	private ImagePreview imagePreview = new ImagePreview(500, 500);
	private int dataPos = -1;
	private JButton create = new JButton("Create");

	public ImagePopup() {
		super("New " + CfgLang.JS_FUNC_VAR_NAME_PREFIX_IMAGE_URL);
		setSize(700, 700);
//		setLayout(new FlowLayout());

		JLabel labelName = new JLabel("Name");
		labelName.setForeground(CfgStyles.COLOR_LIGHT_GRAY);

		JButton buttonLocate = new JButton("Locate");
		buttonLocate.addActionListener(new PopupButtonListener(this, 1));

		JButton help = new JButton("?");
		this.getContentPane().setBackground(CfgStyles.COLOR_ALMOST_BLACK);
		help.setBackground(CfgStyles.COLOR_LIGHT_GREEN);

		create.addActionListener(new PopupButtonListener(this, 0));
		add(labelName);
		add(textName);
		add(buttonLocate);
		add(create);
		add(help);
		setVisible(true);
		add(imagePreview);

	}
	
	public ImagePopup(JSImageURL image, int pos) {
		this(new File(image.getImageURL()));
		dataPos = pos;
		create.setText("Save Changes");
	}
	public ImagePopup(File file) {
		this();
		readImage(file);
		this.file = file.getPath();
		textName.setText(file.getName().substring(0, file.getName().indexOf('.')));
	}

	@Override
	public void buttonPressed(int id) {
		if (id == 0){
			createImage();
		}else if (id == 1){
			locateFile();
		}
	}

	private void createImage() {
		if (file == null){
			JOptionPane.showMessageDialog(null, "You have not located an image.");
			return;
		}
		if (textName.getText().contains(" ")){
			JOptionPane.showMessageDialog(null, "The name can not have spaces.");
			return;
		}else if (textName.getText().length() == 0){
			JOptionPane.showMessageDialog(null, "You did not enter a name");
			return;
		}
		JSParam[] params = {new JSParam(file, JSParam.TYPE_STRING), new JSArray(JSImageURL.dimensionToStringArray(size))};
		JSImageURL image = new JSImageURL(textName.getText(), params);
		if (dataPos >= 0){
			RatPackStudio.dataManager.getGameData().getImgURLData().set(dataPos, image);
		}else{
			RatPackStudio.dataManager.getGameData().addJSImageURL(image);			
		}
		System.out.println(image);

		this.dispose();// close window
	}

	private void locateFile() {
		FileCrawl fc = new FileCrawl(new File(".").getPath());
		File opened = fc.open();
		if (opened == null){
			return;
		}
		readImage(opened);
		this.file = opened.getPath();
	}
	
	/**Reads the image at the given file and paints it to the image preview.
	 * @param file where the image is located
	 */
	private void readImage(File file){
		BufferedImage img = null;
		try{
			img = ImageIO.read(file);
		}catch (IOException e1){
			e1.printStackTrace();
		}
		imagePreview.paintImage(img);
		this.size = new Dimension(img.getWidth(), img.getHeight());
	}
}
