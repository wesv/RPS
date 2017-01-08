package gui.popups;

import gui.ListSelectionPane;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import config.CfgStyles;
import js.JSFuncData;

/** @author Kayler Renslow<br>
 * Base class used for any popup. When a new instance is created, a new window is created with all components added. <br>
 * Automatically disposes the window on close. */
@SuppressWarnings("serial")
public abstract class GenericPopup extends JFrame{
	public GenericPopup(String frameTitle) {
		super(frameTitle);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(500, 500);
		setLayout(new FlowLayout());
		this.getContentPane().setBackground(CfgStyles.COLOR_ALMOST_BLACK);
		
	}

	/** A button was pressed with unique id, id
	 * 
	 * @param id a unique number */
	public void buttonPressed(int id) {
		System.out.println(this.getTitle() + " popup button with id " + id + " was pressed (GenericPopup.buttonPressed(int id))");
	}

	public ListSelectionPane createSelectionPane(ArrayList<? extends JSFuncData> data, String name) {
		String[] sel;
		if (data.size() == 0){
			sel = new String[1];
			sel[0] = "No " + name + "               ";
		}else{
			sel = new String[data.size()];
			for (int i = 0; i < data.size(); i++){
				sel[i] = data.get(i).getJSVarName();
			}
		}
		return new ListSelectionPane(sel);

	}

	public JComboBox<String> createComboBox(ArrayList<? extends JSFuncData> data, String name, boolean allowEmpty) {
		String[] sel;
		if (data.size() == 0){
			if (allowEmpty){
				sel = new String[1];
				sel[0] = "null";
			}else{
				sel = new String[1];
				sel[0] = "No " + name + "               ";
			}
		}else{
			if (allowEmpty){
				sel = new String[data.size() + 1];
				sel[data.size()] = "null";
			}else{
				sel = new String[data.size()];
			}
			for (int i = 0; i < data.size(); i++){
				sel[i] = data.get(i).getJSVarName();
			}
		}
		return new JComboBox<String>(sel);
	}

	public JComboBox<String> createComboBox(ArrayList<? extends JSFuncData> data, String name) {
		return createComboBox(data, name, false);
	}

}

class PopupButtonListener implements ActionListener{
	private GenericPopup popup;
	private final int id;

	public PopupButtonListener(GenericPopup popup, int id) {
		this.popup = popup;
		this.id = id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		popup.buttonPressed(this.id);
	}
}