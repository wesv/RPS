package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import config.CfgStyles;

/** @author Kayler Renslow <br />
 * <br />
 * JPanel for the console. */
@SuppressWarnings("serial")
public class ConsoleUI extends JPanel{
	private static JTextPane console = new JTextPane();
	private static int numEntries = 0;
	private final static int maxEntries = 30;
	private final static Style timeStyle = console.addStyle("time", null);

	public ConsoleUI() {
		setLayout(new BorderLayout());

		JScrollPane pane = new JScrollPane(console);
		// pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(pane);
		setBackground(CfgStyles.COLOR_ALMOST_BLACK);
		console.setBackground(Color.WHITE);
		console.setForeground(Color.BLACK);
		console.setText("Welcome to Rat Pack Studios\n");
		console.setEditable(false);
		console.setFont(CfgStyles.FONT_CONSOLE);
		StyleConstants.setForeground(timeStyle, Color.BLACK);
		StyleConstants.setBold(timeStyle, false);

	}

	/** Prints text to the console. No more then 30 lines allowed in the console.
	 * 
	 * @param text text to be appended to the console */
	public static void printToConsole(String text) {
		printToConsole(text, Color.BLACK, false);
	}

	public static void printToConsole(String text, Color color, boolean bold) {
		StyledDocument doc = console.getStyledDocument();
		Style style = console.getStyle(color.toString() + bold);
		if (style == null){
			style = console.addStyle(color.toString() + bold, null);
			StyleConstants.setForeground(style, color);
		}
		if (bold){
			StyleConstants.setBold(style, true);
		}
		String time = new SimpleDateFormat("hh:mm a").format(new Date());
		try{
			doc.insertString(doc.getLength(), time + ": ", timeStyle.copyAttributes());
		}catch (BadLocationException e){
			e.printStackTrace();
		}
		try{
			doc.insertString(doc.getLength(), text + "\n", style.copyAttributes());
		}catch (BadLocationException e){
			e.printStackTrace();
		}
		try{
			if (numEntries > maxEntries){
				String te = doc.getText(0, doc.getLength());
				int firstNewLine = te.indexOf('\n');
				doc.remove(0, firstNewLine + 1);
			}
		}catch (BadLocationException e){
			e.printStackTrace();
		}
		numEntries++;

	}

	public static void printToConsole(String text, Color color) {
		printToConsole(text, color, false);
	}
}
