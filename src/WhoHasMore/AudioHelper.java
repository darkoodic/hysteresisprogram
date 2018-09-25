package WhoHasMore;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Helper class to handle playing audio feedback. Plays correct and incorrect
 * sounds.
 * 
 * @author Ryan Ly
 */
public class AudioHelper {

	public static final String SOUNDS_DIR = "./data/sounds/";
	public static final String CORRECT_SOUND_FILE = SOUNDS_DIR + "Ping.aiff";
	public static final String INCORRECT_SOUND_FILE = SOUNDS_DIR
			+ "Basso.aiff";

	private Clip correctAudioClip;
	private Clip incorrectAudioClip;

	/**
	 * Creates a new AudioHelper instance and sets up the associated correct and
	 * incorrect audio clips.
	 */
	public AudioHelper() {
		correctAudioClip = setupAudioClip(CORRECT_SOUND_FILE);
		incorrectAudioClip = setupAudioClip(INCORRECT_SOUND_FILE);
	}

	/**
	 * Plays the correct audio clip
	 */
	public void playCorrectSound() {
		playSound(correctAudioClip);
	}

	/**
	 * Plays the incorrect audio clip
	 */
	public void playIncorrectSound() {
		playSound(incorrectAudioClip);
	}

	/**
	 * Plays the sound signifying a correct response
	 * 
	 * @param clip
	 *            the sound clip to play
	 */
	private void playSound(Clip clip) {
		clip.setFramePosition(0); // reset the clip to beginning
		clip.start();
		//while (clip.isRunning()) {}
	}

	/**
	 * Sets up the audio clip for playing
	 * 
	 * @param path
	 *            the path of the audio clip
	 * @return the sound clip stored in the path given
	 */
	private Clip setupAudioClip(String path) {
		AudioInputStream ain = null;
		Clip line = null;
		try {
			// Get an audio input stream from the file
			ain = AudioSystem.getAudioInputStream(new File(path));
			// Get information about the format of the stream
			AudioFormat format = ain.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			// Open the line
			line = (Clip) AudioSystem.getLine(info);
			line.open(ain);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return line;
	}
}
