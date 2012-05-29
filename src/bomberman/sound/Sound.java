package bomberman.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound
{
	public static final Sound	explosion	= new Sound("data/sounds/explosion.wav");
	public static final Sound	plantBomb	= new Sound("data/sounds/plantbomb.wav");

	private AudioClip			clip;

	private Sound(String name)
	{
		try
		{
			clip = Applet.newAudioClip(new URL("file:" + name));
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

	public void play()
	{
		new Thread()
		{
			public void run()
			{
				clip.play();
			}
		}.start();
	}
}
