package gui.popups;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.ProjectConfig;
import main.RatPackStudio;
import config.CfgStyles;

/** @author Zach */
public class SettingsPopup extends JFrame implements ActionListener{

	private JButton done;
	private JTextArea num;
	private JTextArea w;
	private JTextArea h;
	private JTextField textName;

	public SettingsPopup() {
		super("Project Properties");
		setSize(300, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.getContentPane().setBackground(CfgStyles.COLOR_ALMOST_BLACK);

		setLayout(new FlowLayout());

		JLabel widthset = new JLabel();
		widthset.setText("width: ");
		widthset.setForeground(CfgStyles.COLOR_LIGHT_GRAY);

		w = new JTextArea(1, 3);

		JLabel heightset = new JLabel();
		heightset.setText("Height : ");
		heightset.setForeground(CfgStyles.COLOR_LIGHT_GRAY);

		h = new JTextArea(1,3);

		JLabel name = new JLabel();
		name.setText("          Game's name:");
		name.setForeground(CfgStyles.COLOR_LIGHT_GRAY);

		textName = new JTextField(8);

		JLabel player = new JLabel();
		player.setText("number of players : ");
		player.setForeground(CfgStyles.COLOR_LIGHT_GRAY);

		num = new JTextArea(1,2);

		done = new JButton("done");
		done.addActionListener(this);
		add(widthset);
		add(w);
		add(heightset);
		add(h);
		add(name);
		add(textName);
		add(player);
		add(num);
		add(done);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == done){
			// add to project save stuff but where TODO
			Scanner scan = new Scanner(w.getText());
			int width = scan.nextInt();
			scan = new Scanner(h.getText());
			int height = scan.nextInt();
			scan = new Scanner(num.getText());
			int number = scan.nextInt();
			String title = textName.getText();
			RatPackStudio.dataManager.changeProjectConfig(new ProjectConfig(title, height, width, number,"","",""));
			setVisible(false); 
			dispose();
		}

	}
}