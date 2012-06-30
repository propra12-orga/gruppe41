package bomberman.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * The class needed to play sound during the game.
 */
public class Sound {
	public static final Sound explosion = new Sound("data/sounds/explosion.wav");
	public static final Sound plantBomb = new Sound("data/sounds/plantbomb.wav");
	public static final Sound pickup = new Sound("data/sounds/pickup.wav");

	private AudioClip clip;
	/**
	 * Private constructor, can only be called up by the play method. Creates an audio clip.
	 * @param name - Sets what sound to be played.
	 */
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(new URL("file:" + name));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Plays the selected sound. Only multithread method in the whole game.
	 * 
	 */
	public void play() {
		new Thread() {
			public void run() {
				clip.play();
			}
		}.start();
	}
}
