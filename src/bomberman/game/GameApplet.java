package bomberman.game;

import java.applet.Applet;
import java.awt.BorderLayout;

import bomberman.Bomberman;

public class GameApplet extends Applet
{
	private static final long	serialVersionUID	= 1L;
	private Game				bomberman;

	public void init()
	{
		Bomberman.setApplet(this); // must me called before creating an instance of Game

		bomberman = new Game();

		setLayout(new BorderLayout());
		add(bomberman, "Center");
	}

	public void start()
	{
		bomberman.start();
	}

	public void stop()
	{
		bomberman.stop();
	}
}
