package config;

/** @author Kayler Renslow<br>
 * <br>
 * Configuration file for all static final strings for the entire program. */
public class CfgLang{
	public static final String SOFTWARE_VERSION = "1.0.6";
	public static final String WINDOW_TITLE = "Rat Pack Studios - v" + SOFTWARE_VERSION;

	public static final String JS_FUNC_NAME_ENTITY = "Entity";
	public static final String JS_FUNC_NAME_BACKGROUND = "Background";
	public static final String JS_FUNC_NAME_SPRITE = "Sprite";
	public static final String JS_FUNC_NAME_WORLD = "World";
	public static final String JS_FUNC_NAME_SCRIPT = "Script";
	public static final String JS_FUNC_NAME_AUDIO = "Audio";
	public static final String JS_FUNC_NAME_IMAGE_URL = "ImageURL";
	public static final String JS_FUNC_NAME_UI = "UI";

	public static final String JS_FUNC_VAR_NAME_PREFIX_ENTITY = JS_FUNC_NAME_ENTITY;
	public static final String JS_FUNC_VAR_NAME_PREFIX_BACKGROUND = JS_FUNC_NAME_BACKGROUND;
	public static final String JS_FUNC_VAR_NAME_PREFIX_SPRITE = JS_FUNC_NAME_SPRITE;
	public static final String JS_FUNC_VAR_NAME_PREFIX_WORLD = JS_FUNC_NAME_WORLD;
	public static final String JS_FUNC_VAR_NAME_PREFIX_SCRIPT = JS_FUNC_NAME_SCRIPT;
	public static final String JS_FUNC_VAR_NAME_PREFIX_AUDIO_URL = JS_FUNC_NAME_AUDIO;
	public static final String JS_FUNC_VAR_NAME_PREFIX_UI = JS_FUNC_NAME_UI;
	public static final String JS_FUNC_VAR_NAME_PREFIX_IMAGE_URL = "Image";

	public static final String JS_CFG_BACKGROUND = "CfgBackgrounds.js";
	public static final String JS_CFG_ENTITY = "CfgEntities.js";
	public static final String JS_CFG_IMAGE = "CfgImages.js";
	public static final String JS_CFG_SPRITE = "CfgSprites.js";
	public static final String JS_CFG_SCRIPT = "CfgScripts.js";
	public static final String JS_CFG_AUDIO = "CfgAudio.js";
	public static final String JS_CFG_UI = "CfgUI.js";
	public static final String JS_CFG_WORLD = "CfgWorlds.js";
	public static final String JS_CFG_CONFIGURATION = "Configuration.js";

	public static final String ENGINE_DOWNLOAD_FILE_PREFIX = "http://k-town.ws/rpstudios/game/engine/";
	public static final String[] ENGINE_FILES = {"Background.js", "Entity.js", "ImageURL.js", "InputHandle.js","Main.js", "Resources.js", "Sprite.js", "UI.js", "World.js","game.html"};

	public static final String JS_CONFIGURATION_VAR_TITLE = "GAME_TITLE";
	public static final String JS_CONFIGURATION_VAR_WIDTH = "GAME_WIDTH";
	public static final String JS_CONFIGURATION_VAR_HEIGHT = "GAME_HEIGHT";
	public static final String JS_CONFIGURATION_VAR_NUM_PLAYERS = "GAME_NUM_PLAYERS";
	public static final String JS_CONFIGURATION_VAR_DESCRIPTION = "GAME_DESCRIPTION";
	public static final String JS_CONFIGURATION_VAR_BACKGROUND = "GAME_WINDOW_BACKGROUND";
	public static final String JS_CONFIGURATION_VAR_TEXT_COLOR = "GAME_WINDOW_TEXT_COLOR";
	public static final String JS_CONFIGURATION_VAR_SERVER_HOSTED = "SERVER_HOSTED";

}
