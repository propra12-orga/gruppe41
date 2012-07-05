package bomberman.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import bomberman.game.Game;
import bomberman.input.Keyboard;
import bomberman.map.Map;
import bomberman.map.MapObject;

/**
 * Class for the game. Methods used by Bomberman#main to update and render the game or menu.
 * 
 */
public abstract class CoreGame
{
	/**
	 * Reference to the game instance, used for startgame/endgame methods.
	 */
	protected Game					game;
	/**
	 * Keyboard input to control players.
	 * 
	 * @see bomberman.input.Input
	 */
	protected Keyboard				input;
	/**
	 * Game map.
	 */
	protected Map					map;
	/**
	 * Self explanatory.
	 */
	protected boolean				paused	= false;

	/**
	 * The paused time.
	 */
	protected long					pauseTime;
	/**
	 * The items in the pause menu.
	 */
	protected static final String[]	menu	= { "Resume Game", "Restart Game", "Return to Title Screen", "Exit Game" };

	/**
	 * The items in the end of game menu.
	 */
	protected static final String[]	end		= { "New Game?", "Exit Game" };

	/**
	 * Selected item in the menu (if any).
	 */
	protected int					selected;

	protected long					highscore;

	protected boolean				gameOver;

	/**
	 * 
	 * @param game - The Game.
	 * @param input - Keyboard input.
	 * @param netput - The connector for a network game. Always null when playing local games.
	 * @param mapname - The name of the map.
	 * @param gametype - zero for single, 1 for battle.
	 * @param players - Boolean array, a player will spawn for any true boolean.
	 */
	public CoreGame(Game game, Keyboard input, String mapname)
	{
		this.game = game;
		this.input = input;
		this.input.clear();
		this.map = new Map(mapname);
		this.gameOver = false;
		this.highscore = System.currentTimeMillis();
	}

	public Map getMap(){
		return this.map;
	}
	
	/**
	 * Pauses the game and saves the system time when game was paused. Not supported in network mode.
	 */
	public void Pause()
	{
		pauseTime = System.currentTimeMillis();
		paused = true;
	}

	/**
	 * Resumes the game and adds the paused time to the creation time of all map objects.
	 */
	public void Resume()
	{
		long pausedTime = System.currentTimeMillis() - pauseTime;
		for (MapObject o : map.objects)
		{
			o.creationTime += pausedTime;
		}
		paused = false;
	}

	/**
	 * The update method used when the game is paused.
	 */
	public void UpdatePause()
	{
		int sel = 0;

		if (input.use(KeyEvent.VK_DOWN))
			sel++;
		else if (input.use(KeyEvent.VK_UP))
			sel--;

		do
		{
			selected += sel;

			if (selected >= menu.length)
				selected -= menu.length;
			else if (selected < 0)
				selected += menu.length;

		} while (menu[selected] == "");
	}

	/**
	 * Renders the game screen while game is paused.
	 * 
	 * @param g - Graphics used for painting.
	 */
	public void RenderPause(Graphics2D g)
	{
		String len = "";

		for (int i = 0; i < menu.length; i++)
		{
			String s = menu[i];
			if (s.length() > len.length())
				len = s;
		}

		int x1 = (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(len) / 2) - 15, x2 = (Game.WIDTH / 2) + (g.getFontMetrics().stringWidth(len) / 2) + 9;
		int y1 = (Game.HEIGHT / 2) - menu.length * 4 - 24, y2 = (Game.HEIGHT / 2) - menu.length * 4 + menu.length * 24;

		g.fillRect(x1, y1, x2 - x1 + g.getFontMetrics().stringWidth("<"), y2 - y1);

		for (int i = 0; i < menu.length; i++)
		{
			String msg = menu[i];
			int y = (Game.HEIGHT / 2) - menu.length * 4 + i * 24;
			g.setColor(Color.gray);
			if (i == selected)
			{
				g.setColor(Color.white);
				g.drawString(">", x1, y);
				g.drawString("<", x2, y);
			}
			g.drawString(msg, (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(msg) / 2), y);
		}
	}

	/**
	 * Renders the game screen while game is tutoring.
	 * 
	 * @param g - Graphics used for painting.
	 */

	/**
	 * The update function for a ended game (when end menu is active).
	 */
	public void UpdateEnd()
	{
		int sel = 0;

		if (input.use(KeyEvent.VK_DOWN))
			sel++;
		else if (input.use(KeyEvent.VK_UP))
			sel--;

		do
		{
			selected += sel;

			if (selected >= end.length)
				selected -= end.length;
			else if (selected < 0)
				selected += end.length;

		} while (end[selected] == "");
	}

	/**
	 * Renders the game while end menu is active.
	 * 
	 * @param g - Graphics to be painted.
	 */
	public void RenderEnd(Graphics2D g)
	{
	}

	/**
	 * The update method for core game. Updates the map and (if needed) the pause- or end- menu.
	 * 
	 * @see bomberman.map.Map#Update()
	 */
	public void Update()
	{
	}

	/**
	 * Renders the map and a menu if needed.
	 * 
	 * @param g - Graphics to be painted.
	 */
	public void Render(Graphics2D g)
	{
	}

}
