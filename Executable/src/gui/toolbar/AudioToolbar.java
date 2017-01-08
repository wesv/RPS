package gui.toolbar;

import gui.popups.AudioPopup;

import java.awt.event.ActionEvent;


public class AudioToolbar extends ToolbarButton{

	public AudioToolbar(String title, String icon) {
		super(title,icon);
		
	    
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		new AudioPopup();
	}

}
