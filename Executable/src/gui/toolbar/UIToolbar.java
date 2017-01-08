package gui.toolbar;

import gui.popups.UIPopup;

import java.awt.event.ActionEvent;


public class UIToolbar extends ToolbarButton{

	public UIToolbar(String title, String icon) {
		super(title,icon);
		
	    
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		new UIPopup();
	}

}
