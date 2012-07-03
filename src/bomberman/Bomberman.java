package bomberman;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bomberman.game.Game;

/**
 * This class contains the main method and starts the game. This class does not have a constructor or any methods but the main method. This game does not use multiple threads explicitly except for creating the actual game for compatibility with being run as an applet.
 * 
 * @see bomberman.Bomberman#main
 */
public class Bomberman
{
	/**
	 * Indicates whether the game is run as an application
	 */
	public static boolean	isApplication;
	/**
	 * Indicates whether the game is run as an applet
	 */
	public static boolean	isApplet;

	/**
	 * Applet-instance if set via setApplet
	 */
	public static Applet	applet;
	/**
	 * Relative path to data files if run as applet
	 */
	public static String	appletDir	= "file:../";

	private static Game		bomberman;

	/**
	 * Makes the game accessible in all classes.
	 */
	public static void setApplication()
	{
		isApplication = true;
		isApplet = false;
	}

	/**
	 * Makes the game accessible in all classes.
	 */
	public static void setApplet(Applet applet)
	{
		isApplication = false;
		isApplet = true;
		Bomberman.applet = applet;
	}

	/**
	 * Makes the game accessible in all classes.
	 */
	public static Game getGame()
	{
		return bomberman;
	}

	/**
	 * Creates a Game instance, adds it to a window and enters the main loop by invoking game.start().
	 * 
	 * @param args - Java standard but not used in this game.
	 * @see bomberman.game.Game
	 * @see bomberman.game.Game#Update()
	 * @see bomberman.game.Game#Render()
	 */
	public static void main(String[] args)
	{
		setApplication(); // must me called before creating an instance of Game

		bomberman = new Game();

		JFrame window = new JFrame("Bomberman");
		JPanel panel = (JPanel) window.getContentPane();

		panel.setPreferredSize(new Dimension(Game.WIDTH - 12, Game.HEIGHT - 12));
		panel.setLayout(new BorderLayout());

		panel.add(bomberman);
		window.pack();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);

		window.setVisible(true);

		bomberman.start();
	}
}
