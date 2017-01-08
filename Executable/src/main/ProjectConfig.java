package main;

import java.io.File;

/** @author Zach King */
public class ProjectConfig{
	private String name;
	private int numPlayers;
	private int width;
	private int height;
	private String description;
	private String background;
	private String textColor;
	private File exportLocation;

	/** Creates a project configuration with default values */
	public ProjectConfig() {
		this("untitled", 1440, 810, 1, "No Description", "white", "black");
	}

	/** @param name title of the game
	 * @param width width of the game
	 * @param height height of the game
	 * @param numberplayers max number of players
	 * @param description description of the game (can allow HTML)
	 * @param background background color of the game window (hex or CSS values).
	 * @param textColor text color of the game window (hex or CSS values) */
	public ProjectConfig(String name, int width, int height, int numberplayers, String description, String background, String textColor) {
		setName(name);
		setNumPlayers(numberplayers);
		setWidth(width);
		setHeight(height);
		setDescription(description);
		setBackground(background);
		setTextColor(textColor);
	}

	public int getNumPlayers() {
		return numPlayers;
	}
	
	public File getExportLocation(){
		return exportLocation;
	}
	
	public void setExportLocation(File file){
		this.exportLocation = file;
	}
	

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int w) {
		this.width = w;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int h) {
		this.height = h;
	}
}
