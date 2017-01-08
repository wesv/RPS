package main;

import gui.nodes.ScriptNode;
import gui.ConsoleUI;
import gui.GameOptionsUI;
import gui.TreeNode;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import js.JSFuncData;
import js.JSScript;
import js.JavaScriptWriter;
import js.functions.JSAudio;
import js.functions.JSBackground;
import js.functions.JSEntity;
import js.functions.JSImageURL;
import js.functions.JSSprite;
import js.functions.JSUI;
import js.functions.JSWorld;
import util.FileSaveErrorException;
import util.Utils;
import config.CfgLang;
import config.CfgMenus;
import config.CfgStyles;

/** @author Kayler Renslow<br>
 * <br> */
public class GameData{

	private ArrayList<JSEntity> entityData = new ArrayList<>();
	private ArrayList<JSSprite> spriteData = new ArrayList<>();
	private ArrayList<JSScript> scriptData = new ArrayList<>();
	private ArrayList<JSImageURL> imgURLData = new ArrayList<>();
	private ArrayList<JSAudio> audioURLData = new ArrayList<>();
	private ArrayList<JSBackground> backgroundData = new ArrayList<>();
	private ArrayList<JSUI> uiData = new ArrayList<>();
	private ArrayList<JSWorld> worldData = new ArrayList<>();
	private String gameID = "-1";
	private boolean hasUploaded;

	/** Adds a new JSWorld to game data and adds a node to the tab pane
	 * 
	 * @param obj what to add */
	public void addJSWorld(JSWorld obj) {
		worldData.add(obj);
		GameOptionsUI.addNode(CfgMenus.GAME_OPTION_SELECT_WORLD, obj.getJSVarName());
		RatPackStudio.setNeedsSave(true);
	}

	/** Adds a new JSUI to game data and adds a node to the tab pane
	 * 
	 * @param obj what to add */
	public void addJSUI(JSUI obj) {
		uiData.add(obj);
		GameOptionsUI.addNode(CfgMenus.GAME_OPTION_SELECT_UI, obj.getJSVarName());
		RatPackStudio.setNeedsSave(true);
	}

	/** Adds a new JSAudioURL to game data and adds a node to the tab pane
	 * 
	 * @param obj what to add */
	public void addJSAudioURL(JSAudio obj) {
		audioURLData.add(obj);
		GameOptionsUI.addNode(CfgMenus.GAME_OPTION_SELECT_AUDIO, obj.getJSVarName());
		RatPackStudio.setNeedsSave(true);
	}

	/** Adds a new JSSprite to game data and adds a node to the tab pane
	 * 
	 * @param obj what to add */
	public void addJSSprite(JSSprite obj) {
		spriteData.add(obj);
		GameOptionsUI.addNode(CfgMenus.GAME_OPTION_SELECT_SPRITE, obj.getJSVarName());
		RatPackStudio.setNeedsSave(true);
	}

	/** Adds a new JSScript to game data and adds a node to the tab pane
	 * 
	 * @param obj what to add */
	public void addJSScript(JSScript obj) {
		scriptData.add(obj);
		GameOptionsUI.addNode(CfgMenus.GAME_OPTION_SELECT_SCRIPT, obj.getVarName());
		RatPackStudio.setNeedsSave(true);
	}

	/** Adds a new JSBackground to game data and adds a node to the tab pane
	 * 
	 * @param obj what to add */
	public void addJSBackground(JSBackground obj) {
		backgroundData.add(obj);
		GameOptionsUI.addNode(CfgMenus.GAME_OPTION_SELECT_BACKGROUND, obj.getJSVarName());
		RatPackStudio.setNeedsSave(true);
	}

	/** Adds a new JSImageURL to game data and adds a node to the tab pane
	 * 
	 * @param obj what to add */
	public void addJSImageURL(JSImageURL obj) {
		imgURLData.add(obj);
		GameOptionsUI.addNode(CfgMenus.GAME_OPTION_SELECT_IMAGE, obj.getJSVarName());
		RatPackStudio.setNeedsSave(true);
	}

	/** Adds a new JSEntity to game data and adds a node to the tab pane
	 * 
	 * @param obj what to add */
	public void addJSEntity(JSEntity obj) {
		entityData.add(obj);
		GameOptionsUI.addNode(CfgMenus.GAME_OPTION_SELECT_ENTITY, obj.getJSVarName());
		RatPackStudio.setNeedsSave(true);
	}

	/** Saves data loaded from disk into this class. */
	public void setData(JSEntity[] entityData, JSSprite[] spriteData, JSScript[] scriptData, JSImageURL[] imgURLData, JSAudio[] audioURLData, JSBackground[] backgroundData, JSUI[] uiData, JSWorld[] worldData) {
		setEntityData(entityData);
		setSpriteData(spriteData);
		setScriptData(scriptData);
		setImgURLData(imgURLData);
		setAudioURLData(audioURLData);
		setBackgroundData(backgroundData);
		setWorldData(worldData);
		setUIData(uiData);
	}

	public void compileJS(File compileLocation) throws FileSaveErrorException {
		File config = new File(compileLocation.getPath() + "/config");
		config.mkdir();

		JavaScriptWriter.writeFuncDataToDisk(audioURLData, CfgLang.JS_FUNC_VAR_NAME_PREFIX_AUDIO_URL, compileLocation.getPath() + "/config/" + CfgLang.JS_CFG_AUDIO, false);
		JavaScriptWriter.writeFuncDataToDisk(backgroundData, CfgLang.JS_FUNC_VAR_NAME_PREFIX_BACKGROUND, compileLocation.getPath() + "/config/" + CfgLang.JS_CFG_BACKGROUND, false);
		JavaScriptWriter.writeFuncDataToDisk(entityData, CfgLang.JS_FUNC_VAR_NAME_PREFIX_ENTITY, compileLocation.getPath() + "/config/" + CfgLang.JS_CFG_ENTITY, true);
		JavaScriptWriter.writeFuncDataToDisk(imgURLData, CfgLang.JS_FUNC_VAR_NAME_PREFIX_IMAGE_URL, compileLocation.getPath() + "/config/" + CfgLang.JS_CFG_IMAGE, true);
		JavaScriptWriter.writeFuncDataToDisk(spriteData, CfgLang.JS_FUNC_VAR_NAME_PREFIX_SPRITE, compileLocation.getPath() + "/config/" + CfgLang.JS_CFG_SPRITE, false);
		JavaScriptWriter.writeFuncDataToDisk(worldData, CfgLang.JS_FUNC_VAR_NAME_PREFIX_WORLD, compileLocation.getPath() + "/config/" + CfgLang.JS_CFG_WORLD, true);
		JavaScriptWriter.writeFuncDataToDisk(uiData, CfgLang.JS_FUNC_VAR_NAME_PREFIX_UI, compileLocation.getPath() + "/config/" + CfgLang.JS_CFG_UI, true);

		JavaScriptWriter.writeScriptDataToDisk(scriptData, compileLocation.getPath() + "/config/" + CfgLang.JS_CFG_SCRIPT);

		JavaScriptWriter.writeGameConfiguration(compileLocation.getPath() + "/config/", RatPackStudio.dataManager.getProjectConfig());
		ConsoleUI.printToConsole("JavaScript compiled successfully.", CfgStyles.COLOR_DARK_GREEN);
	}

	public void copyImages(File newLocation) {
		File f;
		File loc;
		for (JSImageURL img : imgURLData){
			f = new File(img.getImageURL());
			loc = new File(newLocation.getPath() + "/" + f.getName());
			Utils.copyFile(f, loc);
			ConsoleUI.printToConsole("Image " + img.getImageURL() + " successfully copied.", CfgStyles.COLOR_DARK_GREEN);
		}
	}

	public void copyAudio(File newLocation) {
		File f;
		File loc;
		for (JSAudio audio : audioURLData){
			f = new File(audio.getAudioURL());
			loc = new File(newLocation.getPath() + "/" + f.getName());
			Utils.copyFile(f, loc);
			ConsoleUI.printToConsole("Audio " + audio.getAudioURL() + " successfully copied.", CfgStyles.COLOR_DARK_GREEN);
		}
	}

	public void setWorldData(JSWorld[] data) {
		if (data == null){
			return;
		}
		worldData = new ArrayList<JSWorld>();
		for (JSWorld d : data){
			addJSWorld(d);
		}
	}

	public void setUIData(JSUI[] data) {
		if (data == null){
			return;
		}
		uiData = new ArrayList<>();
		for (JSUI d : data){
			addJSUI(d);
		}
	}

	public void setAudioURLData(JSAudio[] data) {
		if (data == null){
			return;
		}
		audioURLData = new ArrayList<>();
		for (JSAudio d : data){
			addJSAudioURL(d);
		}
	}

	public void setBackgroundData(JSBackground[] data) {
		if (data == null){
			return;
		}
		backgroundData = new ArrayList<>();
		for (JSBackground d : data){
			addJSBackground(d);
		}
	}

	public void setEntityData(JSEntity[] data) {
		if (data == null){
			return;
		}
		entityData = new ArrayList<>();
		for (JSEntity d : data){
			addJSEntity(d);
		}
	}

	public void setImgURLData(JSImageURL[] data) {
		if (data == null){
			return;
		}
		imgURLData = new ArrayList<>();
		for (JSImageURL d : data){
			addJSImageURL(d);
		}
	}

	public void setScriptData(JSScript[] data) {
		if (data == null){
			return;
		}
		scriptData = new ArrayList<>();
		for (JSScript d : data){
			addJSScript(d);
		}
	}

	public void setSpriteData(JSSprite[] data) {
		if (data == null){
			return;
		}
		spriteData = new ArrayList<>();
		for (JSSprite d : data){
			addJSSprite(d);
		}
	}

	public ArrayList<JSWorld> getWorldData() {
		return worldData;
	}

	public ArrayList<JSUI> getUIData() {
		return uiData;
	}

	public ArrayList<JSAudio> getAudioURLData() {
		return audioURLData;
	}

	public ArrayList<JSBackground> getBackgroundData() {
		return backgroundData;
	}

	public ArrayList<JSEntity> getEntityData() {
		return entityData;
	}

	public ArrayList<JSImageURL> getImgURLData() {
		return imgURLData;
	}

	public ArrayList<JSScript> getScriptData() {
		return scriptData;
	}

	public ArrayList<JSSprite> getSpriteData() {
		return spriteData;
	}

	public void removeData(TreeNode node) {
		for (int i = 0; i < CfgMenus.OPTIONS_LIST.length; i++){
			if (node.getNodeOption() == CfgMenus.OPTIONS_LIST[i].nodeOption){
				ArrayList<? extends JSFuncData> data = decodeNode(i);
				if (data == null && i == CfgMenus.GAME_OPTION_SELECT_SCRIPT){
					scriptData.get(node.getInitialPos()).setDeleted(true);
				}else{
					data.get(node.getInitialPos()).setDeleted(true);
				}
				RatPackStudio.setNeedsSave(true);
				return;
			}
		}
	}

	public void editData(TreeNode node) {
		for (int i = 0; i < CfgMenus.OPTIONS_LIST.length; i++){
			if (node.getNodeOption() == CfgMenus.OPTIONS_LIST[i].nodeOption){
				ArrayList<? extends JSFuncData> data = decodeNode(i);
				if (data == null && i == CfgMenus.GAME_OPTION_SELECT_SCRIPT){
					JSScript script = scriptData.get(node.getInitialPos());
					((ScriptNode) node.getNodeOption()).loadScriptPopup(script);
				}else{
					node.getNodeOption().loadPopup(data.get(node.getInitialPos()), node.getInitialPos());
				}
				RatPackStudio.setNeedsSave(true);
				return;
			}
		}
	}

	public void renameData(TreeNode node, String newName) {
		for (int i = 0; i < CfgMenus.OPTIONS_LIST.length; i++){
			if (node.getNodeOption() == CfgMenus.OPTIONS_LIST[i].nodeOption){
				ArrayList<? extends JSFuncData> data = decodeNode(i);
				if (data == null && i == CfgMenus.GAME_OPTION_SELECT_SCRIPT){
					scriptData.get(node.getInitialPos()).setVarName(newName);
				}else{
					data.get(node.getInitialPos()).setJSVarName(newName);
				}
				RatPackStudio.setNeedsSave(true);
				return;
			}
		}
	}

	private ArrayList<? extends JSFuncData> decodeNode(int nodeOption) {
		switch (nodeOption) {
		case CfgMenus.GAME_OPTION_SELECT_AUDIO:
			return audioURLData;
		case CfgMenus.GAME_OPTION_SELECT_BACKGROUND:
			return backgroundData;
		case CfgMenus.GAME_OPTION_SELECT_ENTITY:
			return entityData;
		case CfgMenus.GAME_OPTION_SELECT_IMAGE:
			return imgURLData;
		case CfgMenus.GAME_OPTION_SELECT_SPRITE:
			return spriteData;
		case CfgMenus.GAME_OPTION_SELECT_UI:
			return uiData;
		case CfgMenus.GAME_OPTION_SELECT_WORLD:
			return worldData;

		}
		return null;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public String getGameID() {
		return gameID;
	}

	public boolean hasUploaded() {
		return hasUploaded;
	}

	public void setHasUploaded(boolean v) {
		this.hasUploaded = v;
	}

}
