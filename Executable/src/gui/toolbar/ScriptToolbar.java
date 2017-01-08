package gui.toolbar;

import gui.popups.ScriptEditor;

import java.awt.event.ActionEvent;

public class ScriptToolbar extends ToolbarButton{

	public ScriptToolbar(String title, String icon) {
		super(title, icon);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new ScriptEditor();
	}

}
