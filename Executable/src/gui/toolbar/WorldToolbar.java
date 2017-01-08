package gui.toolbar;

import gui.popups.WorldPopup;

import java.awt.event.ActionEvent;


public class WorldToolbar extends ToolbarButton{

	public WorldToolbar(String title, String icon) {
		super(title,icon);
		
	    
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		new WorldPopup();
	}

}
