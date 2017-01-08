package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/** @author Kayler Renslow <br />
 * <br />
 * 
 *         JPanel that holds all the JPanels of the main GUI. */
@SuppressWarnings("serial")
public class MasterUI extends JPanel{
	private final MainViewUI mainView = new MainViewUI();
	private final GameOptionsUI gameOptions = new GameOptionsUI();

	public GameOptionsUI getOptions(){
		return gameOptions;
	}
	
	public MasterUI() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints con = new GridBagConstraints();
		setLayout(layout);
		mainView.setBackground(Color.BLACK);

		con.fill = GridBagConstraints.WEST & GridBagConstraints.BOTH;

		con.gridx = 0;
		con.gridy = 0;
		// con.gridwidth = 6;
		con.gridheight = 2;
		con.weightx = .1667;
		con.weighty = .8461;
		add(gameOptions, con);

		con.gridx = 1;
		con.gridy = 0;
		// con.gridwidth = 6;
		// con.gridheight = 3;
		con.weightx = .8333;
		con.weighty = .1539;
		// con.fill = GridBagConstraints.REMAINDER;
		con.fill = GridBagConstraints.EAST & GridBagConstraints.BOTH;
		add(mainView, con);
	}

	/** Returns the console UI.
	 * 
	 * @return the console UI */
	public ConsoleUI getConsole() {
		return mainView.getConsole();
	}
}
