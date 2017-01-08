package gui.popups;

import gui.FolderChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import config.CfgStyles;
import main.ServerConfig;

/** @author Kayler Renslow<br>
 * <br> */
public class ServerConfigPopup extends GenericPopup{
	private JCheckBox useLocalHost;
	private JButton locate;
	private JButton save;
	private JTextField rootDir;

	public ServerConfigPopup() {
		super("Server Configuration");
		setSize(400, 400);
		useLocalHost = new JCheckBox("Preview Game with Local Host");
		useLocalHost.setSelected(ServerConfig.SERVER_HOSTED);
		useLocalHost.addActionListener(new PopupButtonListener(this, 0));
		useLocalHost.setBackground(CfgStyles.COLOR_ALMOST_BLACK);
		useLocalHost.setForeground(CfgStyles.COLOR_LIGHT_GRAY);
		locate = new JButton("Locate");
		locate.addActionListener(new PopupButtonListener(this, 1));
		locate.setEnabled(ServerConfig.SERVER_HOSTED);

		rootDir = new JTextField(12);
		rootDir.setEditable(false);
		rootDir.setText((ServerConfig.localHostLocation != null ? ServerConfig.localHostLocation.getPath() : ""));
		rootDir.setEditable(ServerConfig.SERVER_HOSTED);

		save = new JButton("Save");
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		save.addActionListener(new PopupButtonListener(this, 2));
		add(useLocalHost);
		add(rootDir);
		add(locate);
		add(save);
		add(cancel);
	}

	@Override
	public void buttonPressed(int id) {
		if (id == 0){
			locate.setEnabled(useLocalHost.isSelected());
			rootDir.setEnabled(useLocalHost.isSelected());
		}else if (id == 1){
			File f = new FolderChooser().open();
			if (f != null){
				rootDir.setText(f.getPath());
			}
		}else if (id == 2){
			ServerConfig.SERVER_HOSTED = useLocalHost.isSelected();
			if (rootDir.getText().length() > 0){
				ServerConfig.localHostLocation = new File(rootDir.getText());
			}
			dispose();
		}
	}

}
