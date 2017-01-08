package gui.popups;

import gui.FileCrawl;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import util.SoundPreview;
import js.JSParam;
import js.functions.JSAudio;
import main.RatPackStudio;
import config.CfgLang;
import config.CfgStyles;

/** @author Zach King, Kayler Renslow <br>
 * <br>
 * This class allows the user to locate a Sound, preview it, and save it to game data. */
@SuppressWarnings("serial")
public class AudioPopup extends GenericPopup{
	// private JButton open;
	private File file = null;
	private JTextField textName = new JTextField(12);
	private SoundPreview sound = new SoundPreview();
	private JLabel explain;
	private JButton create;
	private int dataPos = -1;

	public AudioPopup() {
		super("New " + CfgLang.JS_FUNC_NAME_AUDIO);
		// setSize(700, 700);

		JLabel labelName = new JLabel("Name");
		labelName.setForeground(CfgStyles.COLOR_LIGHT_GRAY);

		JButton buttonLocate = new JButton("Locate");
		buttonLocate.addActionListener(new PopupButtonListener(this, 1));

		JButton help = new JButton("?");
		this.getContentPane().setBackground(CfgStyles.COLOR_ALMOST_BLACK);
		help.setBackground(CfgStyles.COLOR_LIGHT_GREEN);
		help.addActionListener(new PopupButtonListener(this, 4));

		create = new JButton("Create");
		create.addActionListener(new PopupButtonListener(this, 0));

		JButton preview = new JButton("Preview");
		preview.addActionListener(new PopupButtonListener(this, 2));

		JButton stopPreview = new JButton("Stop");
		stopPreview.addActionListener(new PopupButtonListener(this, 3));

		add(labelName);
		add(textName);
		add(buttonLocate);
		add(preview);
		add(stopPreview);
		add(create);
		add(help);
		// add(explain);
		setVisible(true);

	}

	public AudioPopup(JSAudio audio, int pos) {
		this();
		this.file = new File(audio.getAudioURL());
		this.textName.setText(audio.getJSVarName());
		dataPos = pos;
		create.setText("Save Changes");
	}

	public AudioPopup(File file) {
		this();
		this.file = file;
		textName.setText(file.getName().substring(0, file.getName().indexOf('.')));
	}

	@Override
	public void buttonPressed(int id) {
		if (id == 0){
			createAudio();
		}else if (id == 1){
			locateFile();
		}else if (id == 2){
			sound.play(file);
		}else if (id == 3){
			sound.stop();
		}else if (id == 4){
			explain.setText("this will create audio objects for the game");
		}
	}

	private void createAudio() {
		if (file == null){
			JOptionPane.showMessageDialog(null, "You have not located a sound.");
			return;
		}
		if (textName.getText().contains(" ")){
			JOptionPane.showMessageDialog(null, "The name can not have spaces.");
			return;
		}else if (textName.getText().length() == 0){
			JOptionPane.showMessageDialog(null, "You did not enter a name");
			return;
		}
		JSParam[] params = {new JSParam(file.getPath(), JSParam.TYPE_STRING)};
		JSAudio audio = new JSAudio(textName.getText(), params);
		if (dataPos >= 0){
			RatPackStudio.dataManager.getGameData().getAudioURLData().set(dataPos, audio);
		}else{
			RatPackStudio.dataManager.getGameData().addJSAudioURL(audio);
		}
		System.out.println(audio);

		this.dispose();// close window
	}

	private void locateFile() {
		FileCrawl fc = new FileCrawl(new File(".").getPath());
		File opened = fc.open();
		if (opened == null){
			return;
		}
		file = opened;
	}
}
