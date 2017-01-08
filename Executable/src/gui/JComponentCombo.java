package gui;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/** @author Kayler Renslow<br>
 * <br>
 * Holds a Java Component and a String */
public class JComponentCombo{

	private final Component component;
	private String text;

	public JComponentCombo(Component component, String text) {
		this.component = component;
		this.text = text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/** If the component this class holds is a JTextField, it will return the text inside the text field. If it is not a JTextField, will return null
	 * 
	 * @return String or null */
	public String getTextFieldText() {
		String text;
		try{
			text = ((JTextField) component).getText();
		}catch (Exception e){
			return null;
		}

		return text;

	}

	/** If the component this class holds is a JComboBox, it will return the selected item in the combobox. If it is not a JComboBox, will return null
	 * 
	 * @return String or null */
	public String getComboBoxSelected() {
		String text;
		try{
			text = (String) ((JComboBox<String>) component).getSelectedItem();
		}catch (Exception e){
			return null;
		}

		return text;
	}

	public Component getComponent() {
		return component;
	}

	public String getText() {
		return text;
	}

}
