package gui.popups;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import util.Utils;
import main.ServerConnection;
import main.RatPackStudio;

/** @author Kayler Renslow<br>
 * <br> */
@SuppressWarnings("serial")
public class GameUploadPopup extends GenericPopup{

	private JTextField tokenText = new JTextField(12);
	private JLabel status;
	private JButton upload = new JButton("Upload");
	private KeyInputListener listen;

	public GameUploadPopup() {
		super("Upload Game to Website");
		add(tokenText);
		status = Utils.getColoredLabel("");
		listen = new KeyInputListener(tokenText, status);
		tokenText.addKeyListener(listen);
		tokenText.setBackground(Color.WHITE);
		JButton verify = new JButton("Verify Token");
		verify.addActionListener(new PopupButtonListener(this, 0));
		upload.addActionListener(new PopupButtonListener(this, 1));
		add(verify);
		add(status);
		add(upload);
		upload.setVisible(false);
		setResizable(false);
		setSize(300, 200);
	}

	@Override
	public void buttonPressed(int id) {
		if (id == 0){
			if (listen.checkText()){
				String token = tokenText.getText();
				boolean tokenValid = RatPackStudio.checkToken(token);
				if (tokenValid){
					status.setText("Token is valid. Ready to upload.");
					upload.setVisible(true);
				}else{
					status.setText("Upload Token entered is invalid or expired.");
					return;
				}
			}
		}else if (id == 1){
			if (listen.checkText()){
				RatPackStudio.uploadGame(tokenText.getText());
			}
		}

	}

}

class KeyInputListener implements KeyListener{

	private JTextField input;
	private JLabel label;

	public KeyInputListener(JTextField listening, JLabel label) {
		this.input = listening;
		this.label = label;
	}

	public boolean checkText() {
		String text = input.getText();
		if (text.length() > 10){
			input.setBackground(Color.RED);
			input.setForeground(Color.WHITE);
			label.setText("Length of the token is under 10.");
			return false;
		}
		for (int i = 0; i < text.length(); i++){
			if (!isAllowedChar(text.charAt(i))){// not a letter or a number
				label.setText("Invalid character entered.");
				input.setBackground(Color.RED);
				input.setForeground(Color.WHITE);
				return false;
			}
		}
		label.setText("");
		input.setBackground(Color.WHITE);
		input.setForeground(null);
		return true;
	}

	private boolean isAllowedChar(char c) {
		return Character.isAlphabetic(c) || Character.isDigit(c);
	}

	@Override
	public void keyPressed(KeyEvent key) {
		checkText();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		checkText();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		checkText();
	}

}