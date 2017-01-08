package gui.toolbar;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;


	public abstract class ToolbarButton implements ActionListener{
		private String title;
		private ImageIcon icons;

		public ToolbarButton(String title,String icon) {
			this.title = title;
			this.icons = new ImageIcon(getClass().getResource("/" + icon));
		}

		/** @return the title of the sub-item of a menu bar menu */
		public String getTitle() {
			return title;
		}
		

		/** @return the title of the sub-item of a menu bar menu */
		public ImageIcon getIcon() {
			return icons;
		}

}

