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
	// /**
	// * Starts a single player game with an exit.
	// */
	// public static final int NORMAL_GAME = 0;
	// /**
	// * Starts a local battle game with up to four players.
	// */
	// public static final int BATTLE_GAME = 1;
	//
	// public static final int TUTORIAL = 2;
	// /**
	// * Starts a network game as host.
	// */
	// public static final int NETWORK_GAME_HOST = 3;
	// /**
	// * Starts a network game as client.
	// */
	// public static final int NETWORK_GAME_CLIENT = 4;
	/**
	 * Reference to the game instance, used for startgame/endgame methods.
	 */
	protected Game			game;

	/**
	 * Keyboard input to control players.
	 * 
	 * @see bomberman.input.Input
	 */
	protected Keyboard		input;
	/**
	 * The connector that controls the other player.
	 */
	/**
	 * Game map.
	 */
	protected Map			map;
	/**
	 * Gametype, 0 = single player, 1 = battle game, third mode will come soon.
	 */
	// protected int gametype;
	/**
	 * Self explanatory.
	 */
	protected boolean		paused	= false;

	/**
	 * The paused time.
	 */
	protected long			pauseTime;
	/**
	 * The items in the pause menu.
	 */
	protected static final String[]		menu	= { "Resume Game", "Restart Game", "Return to Title Screen", "Exit Game" };

	/**
	 * The items in the end of game menu.
	 */
	protected static final String[]		end		= { "New Game?", "Exit Game" };

	/**
	 * Selected item in the menu (if any).
	 */
	protected int			selected;
	
	protected long highscore;
	
	protected boolean gameOver;

	/**
	 * Number of the last living player in a battle game. -1 when starting a game.
	 */

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
		
		// if (gametype == 0)
		// {
		// map.Add(new Player(input, map, 0, 0, 0));
		//
		// do
		// {
		// int a = (int) (Math.random() * 17);
		// int b = (int) (Math.random() * 11);
		// if (a != 0 && b != 0 && b != 10 && a != 16 && a % 2 == 0 && b % 2 == 0)
		// {
		// map.Add(new Exit(map, a, b));
		// break;
		// }
		// } while (true);
		// }
		// else if (gametype == BATTLE_GAME)
		// {
		// for (int i = 0; i < 4; i++)
		// {
		// if (players[i])
		// {
		// map.Add(new Player(input, map, Game.spawns[i][0],
		// Game.spawns[i][1], i));
		// }
		// }
		// }
		// else if (gametype == NETWORK_GAME_HOST)
		// {
		// map.Add(new Player(input, map, Game.spawns[0][0], Game.spawns[0][1], 0));
		// map.Add(new Player(netput, map, Game.spawns[1][0], Game.spawns[1][1], 1));
		// }
		// else if (gametype == NETWORK_GAME_CLIENT)
		// {
		// map.Add(new Player(netput, map, Game.spawns[0][0], Game.spawns[0][1], 0));
		// map.Add(new Player(input, map, Game.spawns[1][0], Game.spawns[1][1], 1));
		// }
		// else if (gametype == TUTORIAL)
		// {
		// map.Add(new Player(input, map, 0, 5, 0));
		// for (int count = 2; count < 9; count = count + 1)
		// {
		// if (count % 2 == 0)
		// {
		// if (count != 4 & count != 6)
		// {
		// for (int seccount = 5; seccount < 17; seccount++)
		// {
		// map.Add(new Rock(map, seccount, count));
		// }
		// }
		// else
		// {
		// for (int seccount = 5; seccount < 15; seccount++)
		// {
		// map.Add(new Rock(map, seccount, count));
		// }
		// }
		// }
		// else
		// {
		// if (count != 5)
		// {
		// for (int seccount = 5; seccount < 17; seccount = seccount + 2)
		// {
		// map.Add(new Rock(map, seccount, count));
		// }
		// }
		// else
		// {
		// for (int seccount = 5; seccount < 15; seccount = seccount + 2)
		// {
		// map.Add(new Rock(map, seccount, count));
		// }
		// }
		// }
		//
		// }
		// map.Add(new Exit(map, 16, 5));
		// }
		// else
		// {
		// System.err.println("Invalid Game Type.");
		// System.exit(0);
		// }
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
	 * The update method used when the tutorial text is visible.
	 */

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
//		if (gameOver)
//		{
//			UpdateEnd();
//			return;
//		}
//		else if (paused)
//		{
//			UpdatePause();
//			return;
//		}
//		if (gametype == TUTORIAL)
//		{
//			if (!rock_destroyed1)
//			{
//				boolean rock_found = false;
//
//				for (MapObject o : map.getObjectsOnTile(5, 5))
//				{
//					if (o instanceof Rock)
//					{
//						rock_found = true;
//						break;
//					}
//				}
//				if (rock_found == false)
//				{
//					rock_destroyed1 = true;
//					rock_destroyed2 = false;
//					tutoring = true;
//				}
//			}
//			if (!rock_destroyed2)
//			{
//				boolean rock_found = false;
//
//				for (MapObject o : map.getObjectsOnTile(5, 4))
//				{
//					if (o instanceof Rock)
//					{
//						rock_found = true;
//						break;
//					}
//				}
//				if (rock_found == false)
//				{
//					rock_destroyed2 = true;
//					rock_destroyed3 = false;
//					map.Add(new Bombup(map, 0, 4));
//					tutoring = true;
//				}
//			}
//			if (!rock_destroyed3)
//			{
//				boolean rock_found = false;
//				boolean rock_found2 = false;
//
//				for (MapObject o : map.getObjectsOnTile(5, 3))
//				{
//					if (o instanceof Rock)
//					{
//						rock_found = true;
//						break;
//					}
//				}
//				for (MapObject o : map.getObjectsOnTile(5, 7))
//				{
//					if (o instanceof Rock)
//					{
//						rock_found2 = true;
//						break;
//					}
//				}
//
//				if (rock_found == false | rock_found2 == false)
//				{
//					rock_destroyed3 = true;
//					rock_destroyed4 = false;
//					map.Add(new Flameup(map, 2, 4));
//					tutoring = true;
//				}
//
//			}
//
//			if (!rock_destroyed4)
//			{
//				boolean player_reached = false;
//
//				for (int i = 2; i < 9; i = i + 2)
//				{
//					for (MapObject o : map.getObjectsOnTile(8, i))
//					{
//						if (o instanceof Player)
//						{
//							player_reached = true;
//							break;
//						}
//					}
//				}
//
//				if (player_reached == true)
//				{
//					rock_destroyed4 = true;
//					rock_destroyed5 = false;
//					map.Add(new Kickup(map, 2, 6));
//					tutoring = true;
//				}
//			}
//			if (!rock_destroyed5)
//			{
//				boolean player_reached = false;
//
//				for (int i = 2; i < 9; i = i + 1)
//				{
//					for (MapObject o : map.getObjectsOnTile(11, i))
//					{
//						if (o instanceof Player)
//						{
//							player_reached = true;
//							break;
//						}
//					}
//				}
//
//				if (player_reached == true)
//				{
//					rock_destroyed5 = true;
//					map.Add(new Speedup(map, 0, 6));
//					map.Add(new Speedup(map, 0, 6));
//					tutoring = true;
//				}
//			}
//
//			if (tutoring)
//			{
//				Tutoring();
//				UpdateTutoring();
//				return;
//			}
//
//		}
//
//		map.Update();
//
//		if (gametype == NORMAL_GAME)
//		{
//			Player player = null;
//
//			for (MapObject o : map.objects)
//			{
//				if (o instanceof Player)
//					player = (Player) o;
//			}
//
//			if (player == null)
//				winner = 0;
//			else if (player.hasReachedExit)
//				winner = 1;
//			highscore = System.currentTimeMillis() - highscore;
//			writehs();
//		}
//		else if (gametype == BATTLE_GAME)
//		{
//			if (map.num_of_players < 2)
//			{
//				highscore = System.currentTimeMillis() - highscore;
//				writehs();
//				for (MapObject o : map.objects)
//				{
//					if (o instanceof Player)
//						winner = ((Player) o).player_id + 1;
//				}
//			}
//		}
//		else if (gametype == TUTORIAL)
//		{
//			Player player = null;
//			for (MapObject o : map.objects)
//			{
//				if (o instanceof Player)
//					player = (Player) o;
//			}
//			if (player == null)
//				winner = 0;
//			else if (player.hasReachedExit)
//				winner = 1;
//		}
//
//		if (2 < gametype)
//		{
//			netput.update();
//		}
//
//		if (input.use(KeyEvent.VK_ESCAPE) && gametype < 3)
//			Pause();

	}

	/**
	 * Renders the map and a menu if needed.
	 * 
	 * @param g - Graphics to be painted.
	 */
	public void Render(Graphics2D g)
	{
//		map.Render(g);
//
//		if (winner > -1)
//			RenderEnd(g);
//		else if (paused)
//			RenderPause(g);
//		else if (gametype == TUTORIAL)
//		{
//			if (tutoring)
//			{
//				RenderTutoring(g);
//			}
//		}
	}
}
