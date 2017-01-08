package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/** @author Kayler Renslow <br />
 * <br />
 * 
 *         JPanel that holds the console and the game view JPanels. */
@SuppressWarnings("serial")
public class MainViewUI extends JPanel{
	private final ConsoleUI console = new ConsoleUI();
	private final LogoPartViewUI gameView = new LogoPartViewUI();

	public MainViewUI() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints con = new GridBagConstraints();
		setLayout(layout);
		con.gridx = 0;
		con.gridy = 0;
		con.weightx = 1;
		con.weighty = .8824;
		con.gridheight = 1;
		con.fill = GridBagConstraints.BOTH;
		add(gameView, con);

		con.gridx = 0;
		con.gridy = 1;
		con.weightx = 1;
		con.weighty = .1176;
		con.gridheight = 1;

		add(console, con);

	}

	/** Returns the console UI.
	 * 
	 * @return the console UI */
	public ConsoleUI getConsole() {
		return console;
	}

}
