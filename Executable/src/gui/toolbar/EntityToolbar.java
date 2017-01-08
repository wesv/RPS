package gui.toolbar;

import gui.popups.EntityPopup;

import java.awt.event.ActionEvent;


public class EntityToolbar extends ToolbarButton{

	public EntityToolbar(String title, String icon) {
		super(title,icon);
		
	    
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		new EntityPopup();
	}

}
