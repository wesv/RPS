package util;

import gui.ConsoleUI;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * @author Kayler Renslow
 *
 */
public class SoundPreview{
	private Clip clip;
	AudioInputStream stream = null;
	AudioFormat format;
	DataLine.Info info;

	public boolean isPlaying() {
		return clip != null && clip.getFrameLength() != clip.getFramePosition();
	}

	public void play(File file) {
		try{
			stream = AudioSystem.getAudioInputStream(file);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);

		}catch (Exception e){
			e.printStackTrace();
			ConsoleUI.printToConsole("Cannot preview this sound. Some file formats (i.e. .mp3) aren't supported.");
			return;
		}

		if (clip.isRunning()){
			clip.stop(); // Stop the player if it is still running
		}
		clip.setFramePosition(0);
		clip.start(); // Start playing
	}

	public void stop() {
		if (clip == null){
			return;
		}
		clip.stop();
		clip.close();
		try{
			stream.close();
		}catch (IOException e){
			e.printStackTrace();
		}
		clip = null;
	}
}