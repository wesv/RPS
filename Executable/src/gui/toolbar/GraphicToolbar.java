package gui.toolbar;

import gui.menus.TitleItemListener;
import gui.popups.ImagePopup;

import java.awt.event.ActionEvent;

public class GraphicToolbar extends ToolbarButton{

	
	public GraphicToolbar(String title,String icon) {
		super(title,icon);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		new ImagePopup();
	}

}

