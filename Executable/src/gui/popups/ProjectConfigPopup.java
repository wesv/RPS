package gui.popups;

import gui.ConsoleUI;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.Utils;
import main.ProjectConfig;
import main.RatPackStudio;
import config.CfgStyles;

/** @author Zach */
@SuppressWarnings("serial")
public class ProjectConfigPopup extends GenericPopup{

	private JButton done = new JButton("Done");;
	private JTextField numPlayers = new JTextField(3);
	private JTextField width = new JTextField(3);
	private JTextField height = new JTextField(3);
	private JTextField textName = new JTextField(15);
	private JTextField background = new JTextField(10);
	private JTextField textColor = new JTextField(10);
	private JTextArea description = new JTextArea(20, 20);

	public ProjectConfigPopup() {
		super("Project Properties");
		setSize(250, 600);
		setResizable(false);

		setLayout(new FlowLayout());

		done.addActionListener(new PopupButtonListener(this, 0));
		add(Utils.getColoredLabel("Game width:"));
		add(width);
		add(Utils.getColoredLabel("Game height: "));
		add(height);
		add(Utils.getColoredLabel("Game's name:"));
		add(textName);
		add(Utils.getColoredLabel("Max number of players: "));
		add(numPlayers);
		add(description);
		
		add(Utils.getColoredLabel("Background color:"));
		add(background);
		
		add(Utils.getColoredLabel("Text color:"));
		add(textColor);
		add(done);

		setVisible(true);

		setValues();
	}

	private void setValues() {
		ProjectConfig pc = RatPackStudio.dataManager.getProjectConfig();
		numPlayers.setText(pc.getNumPlayers() + "");
		width.setText(pc.getWidth() + "");
		height.setText(pc.getHeight() + "");
		textName.setText(pc.getName());
		background.setText(pc.getBackground());
		textColor.setText(pc.getTextColor());
		description.setText(pc.getDescription());
	}

	@Override
	public void buttonPressed(int id) {
		if (id == 0){
			String title = textName.getText();
			try{
				RatPackStudio.dataManager.changeProjectConfig(new ProjectConfig(title, Integer.valueOf(width.getText()), Integer.valueOf(height.getText()), Integer.valueOf(numPlayers.getText()), description.getText(), background.getText(), textColor
						.getText()));
			}catch (Exception e){
				ConsoleUI.printToConsole("Invalid entry in the project config");
				JOptionPane.showMessageDialog(null, "Invalid entry", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			setVisible(false);
			dispose();

		}
	}
}