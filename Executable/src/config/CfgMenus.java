package config;

import gui.GameOptionContainer;
import gui.menus.MenuAudio;
import gui.menus.MenuEntity;
import gui.menus.MenuFile;
import gui.menus.MenuGraphics;
import gui.menus.MenuItem;
import gui.menus.MenuSEFile;
import gui.menus.MenuSEItem;
import gui.menus.MenuScripts;
import gui.menus.MenuSettings;
import gui.menus.MenuUI;
import gui.menus.MenuWorlds;
import gui.menus.TitleItemListener;
import gui.nodes.AudioNode;
import gui.nodes.BackgroundNode;
import gui.nodes.EntitiesNode;
import gui.nodes.ImagesNode;
import gui.nodes.ScriptNode;
import gui.nodes.SpritesNode;
import gui.nodes.UINode;
import gui.nodes.WorldNode;
import gui.toolbar.AudioToolbar;
import gui.toolbar.EntityToolbar;
import gui.toolbar.GraphicToolbar;
import gui.toolbar.ScriptToolbar;
import gui.toolbar.ToolbarButton;
import gui.toolbar.UIToolbar;
import gui.toolbar.WorldToolbar;

/** @author Kayler Renslow, Zach King <br />
 * <br />
 * Config file for all menus. */
public class CfgMenus{
	/** Array of all items that go to the File option in the menu bar. */
	public static final TitleItemListener[] MENU_FILE_ITEMS = {new MenuFile("New Project", MenuFile.TYPE_NEW),new MenuFile("Open", MenuFile.TYPE_OPEN), new MenuFile("Save", MenuFile.TYPE_SAVE), new MenuFile("Save As", MenuFile.TYPE_SAVE_AS), new MenuFile("Recompile JS", MenuFile.TYPE_RECOMPILE),
			new MenuFile("Upload", MenuFile.TYPE_UPLOAD), new MenuFile("Export", MenuFile.TYPE_EXPORT), new MenuFile("Load Preview", MenuFile.TYPE_LOAD_PREVIEW), new MenuFile("Exit", MenuFile.TYPE_EXIT)};

	/** Array of all items that go to the Settings option in the menu bar. */
	public static final TitleItemListener[] MENU_SETTINGS_ITEMS = {new MenuSettings("Server Settings", MenuSettings.TYPE_SERVER_CONFIG), new MenuSettings("Project Settings", MenuSettings.TYPE_PROJECT_CONFIG)};

	/** Array of all items that go to the Object option in the menu bar. */
	public static final TitleItemListener[] MENU_ENTITY_ITEMS = {new MenuEntity("New Entity", MenuEntity.TYPE_NEW_ENTITY), new MenuEntity("View Source", MenuEntity.TYPE_VIEW_SOURCE)};

	/** Array of all items that go to the Graphics option in the menu bar. */
	public static final TitleItemListener[] MENU_GRAPHICS_ITEMS = {new MenuGraphics("Add Image", MenuGraphics.TYPE_ADD_IMAGE), new MenuGraphics("New Sprite", MenuGraphics.TYPE_NEW_SPRITE),
			new MenuGraphics("New Background", MenuGraphics.TYPE_NEW_BACKGROUND), new MenuGraphics("View Sprite Source", MenuGraphics.TYPE_VIEW_SPRITE_SOURCE)};

	/** Array of all items that go to the Worlds option in the menu bar. */
	public static final TitleItemListener[] MENU_WORLDS_ITEMS = {new MenuWorlds("New World", MenuWorlds.TYPE_NEW_WORLD), new MenuWorlds("View Source", MenuWorlds.TYPE_VIEW_SOURCE)};

	/** Array of all items that go to the UI option in the menu bar. */
	public static final TitleItemListener[] MENU_UI_ITEMS = {new MenuUI("New UI", MenuUI.TYPE_NEW_UI), new MenuUI("View Source", MenuUI.TYPE_VIEW_SOURCE)};

	/** Array of all items that go to the Audio option in the menu bar. */
	public static final TitleItemListener[] MENU_AUDIO_ITEMS = {new MenuAudio("New Audio", MenuAudio.TYPE_NEW_AUDIO)};

	/** Array of all items that go to the Scripts option in the menu bar. */
	public static final TitleItemListener[] MENU_SCRIPTS_ITEMS = {new MenuScripts("New Script", MenuScripts.TYPE_OPEN_EDITOR), new MenuScripts("Import Script", MenuScripts.TYPE_IMPORT_SCRIPT),
			new MenuScripts("Update Engine", MenuScripts.TYPE_UPDATE_ENGINE)};

	/** Array of all Menu items that are on the menu bar. */
	public static final MenuItem[] MENU_BAR_LIST = {new MenuItem("File", MENU_FILE_ITEMS), new MenuItem("Settings", MENU_SETTINGS_ITEMS), new MenuItem("Entity", MENU_ENTITY_ITEMS), new MenuItem("Graphics", MENU_GRAPHICS_ITEMS),
			new MenuItem("Worlds", MENU_WORLDS_ITEMS), new MenuItem("UI", MENU_UI_ITEMS), new MenuItem("Audio", MENU_AUDIO_ITEMS), new MenuItem("Scripts", MENU_SCRIPTS_ITEMS)};

	public static final int GAME_OPTION_SELECT_ENTITY = 0;
	public static final int GAME_OPTION_SELECT_IMAGE = 1;
	public static final int GAME_OPTION_SELECT_SPRITE = 2;
	public static final int GAME_OPTION_SELECT_BACKGROUND = 3;
	public static final int GAME_OPTION_SELECT_WORLD = 4;
	public static final int GAME_OPTION_SELECT_UI = 5;
	public static final int GAME_OPTION_SELECT_AUDIO = 6;
	public static final int GAME_OPTION_SELECT_SCRIPT = 7;

	/** Array of all items that go to the tab menu. IF YOU CHANGE THE ORDER OF ANYTHING, CHANGE THE GAME_OPTION_SELECT INTEGERS. THEY RELATE TO THE ORDER OF THIS ARRAY. */
	public static final GameOptionContainer[] OPTIONS_LIST = {new GameOptionContainer("cog.png", "Entities", new EntitiesNode()), new GameOptionContainer("camera.png", "Images", new ImagesNode()),
			new GameOptionContainer("puzzle.png", "Sprites", new SpritesNode()), new GameOptionContainer("terrain.png", "Backgrounds", new BackgroundNode()), new GameOptionContainer("world.png", "Worlds", new WorldNode()),
			new GameOptionContainer("mouse.png", "UI", new UINode()), new GameOptionContainer("note.png", "Audio", new AudioNode()), new GameOptionContainer("script.png", "Scripts", new ScriptNode())};

	/** Array of all options that go to the right click menu of the tree node worlds. These are unique options. All right click menus have edit, rename, and delete */
	public static final String[] OPTIONS_TNODE_WORLDS_MENU = {"Open Init Script"};
	public static final String[] OPTIONS_TNODE_AUDIO_MENU = {};
	public static final String[] OPTIONS_TNODE_SCRIPTS_MENU = {};
	public static final String[] OPTIONS_TNODE_BACKGROUND_MENU = {};
	public static final String[] OPTIONS_TNODE_UI_MENU = {};
	public static final String[] OPTIONS_TNODE_ENTITIES_MENU = {};
	public static final String[] OPTIONS_TNODE_SPRITES_MENU = {};
	public static final String[] OPTIONS_TNODE_IMAGES_MENU = {};

	/** Zach's array of the toolbar */
	/** Array of all items that on the toolbar. */
	public static final ToolbarButton[] TOOLBAR_LIST = {new ScriptToolbar("Script Editor", "script.png"), new GraphicToolbar("Image", "camera.png"), new EntityToolbar("Entity", "cog.png"), new AudioToolbar("Audio", "note.png"),
			new WorldToolbar("World", "world.png"), new UIToolbar("UI", "mouse.png")};

}
