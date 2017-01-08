package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/** @author Kayler Renslow<br>
 * <br> */
@SuppressWarnings("serial")
public class ListSelectionPane extends JPanel{
	private JList<String> list = new JList<>(new DefaultListModel<String>());
	private JComboBox<String> selection = new JComboBox<String>(new DefaultComboBoxModel<String>());

	/** Keeps track how how many things are selected. */
	private int numItems = 0;

	/** Creates a new JList
	 * 
	 * @param options all the options that are appendable to the List */
	public ListSelectionPane(String[] options) {
		this(null, options);
	}

	public boolean isEmpty() {
		return numItems == 0;
	}


	public String[] getItems() {
		if (numItems == 0){
			String[] s = {};
			return s;
		}
		String[] str = new String[numItems];
		for (int i = 0; i < numItems; i++){
			str[i] = ((DefaultListModel<String>) list.getModel()).get(i);
		}
		return str;
	}

	public int getNumItems() {
		return numItems;
	}

	/** Creates a new JList
	 * 
	 * @param startData initial String entries
	 * @param options all the options that are appendable to the List */
	public ListSelectionPane(String[] startData, String[] options) {
		if (startData != null){
			for (String str : startData){
				addItemToSelected(str);
			}
		}

		for (String str : options){
			selection.addItem(str);
		}

		((DefaultListModel<String>) list.getModel()).addElement("No Items");
		final int width = 120;
		final int height = 200;

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints con = new GridBagConstraints();

		setSize(new Dimension(width, height));

		con.fill = GridBagConstraints.BOTH;
		con.gridx = 0;
		con.gridy = 0;
		con.gridwidth = 2;
		con.gridheight = 1;
		con.weightx = 1;
		con.weighty = .70;

		add(new JScrollPane(list), con);
		con.gridx = 0;
		con.gridy = 1;
		con.gridwidth = 2;
		con.gridheight = 1;
		con.weightx = 1;
		con.weighty = .10;

		add(selection, con);

		con.gridx = 0;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		con.weightx = .49;
		con.weighty = .10;

		// add(new JLabel());
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				addItemToSelected((String) selection.getSelectedItem());
			}
		});
		add(add, con);
		con.fill = GridBagConstraints.BOTH;
		con.gridx = 1;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		con.weightx = .49;
		con.weighty = .10;
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removeSelected();
			}
		});
		add(delete, con);
	}

	public void addItemToSelected(String item) {
		if (numItems == 0){
			((DefaultListModel<String>) list.getModel()).remove(0);
		}
		selection.removeItemAt(selection.getSelectedIndex());
		selection.revalidate();
		((DefaultListModel<String>) list.getModel()).addElement(item);
		numItems++;
	}

	public void addItem(String str) {
		selection.addItem(str);
	}

	public void removeItemFromSelection(int index) {
		if (index < 0){
			return;
		}
		String item = ((DefaultListModel<String>) list.getModel()).get(index);
		((DefaultListModel<String>) list.getModel()).remove(index);
		selection.addItem(item);
		numItems--;
		if (numItems == 0){
			((DefaultListModel<String>) list.getModel()).addElement("No Items");
		}
	}

	public void removeSelected() {
		removeItemFromSelection(list.getSelectedIndex());
	}

}
