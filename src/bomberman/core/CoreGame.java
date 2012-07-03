package bomberman.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import bomberman.game.Game;
import bomberman.input.Keyboard;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.network.Connector;
import bomberman.objects.terrain.Exit;
import bomberman.objects.terrain.Rock;
import bomberman.players.Player;
import bomberman.powerups.Bombup;
import bomberman.powerups.Flameup;
import bomberman.powerups.Kickup;
import bomberman.powerups.Speedup;

/**
 * Class for the game. Methods used by Bomberman#main to update and render the game or menu.
 * 
 */
public class CoreGame
{
	/**
	 * Starts a single player game with an exit.
	 */
	public static final int	NORMAL_GAME			= 0;
	/**
	 * Starts a local battle game with up to four players.
	 */
	public static final int	BATTLE_GAME			= 1;

	public static final int	TUTORIAL			= 2;
	/**
	 * Starts a network game as host.
	 */
	public static final int	NETWORK_GAME_HOST	= 3;
	/**
	 * Starts a network game as client.
	 */
	public static final int	NETWORK_GAME_CLIENT	= 4;
	/**
	 * Reference to the game instance, used for startgame/endgame methods.
	 */
	private Game			game;
	
	public static final int HSS         = 5;

	private int				tutorialcount		= 0;
	/**
	 * Keyboard input to control players.
	 * 
	 * @see bomberman.input.Input
	 */
	private Keyboard		input;
	/**
	 * The connector that controls the other player.
	 */
	private Connector		netput;
	/**
	 * Game map.
	 */
	private Map				map;
	/**
	 * Gametype, 0 = single player, 1 = battle game, third mode will come soon.
	 */
	private int				gametype;
	/**
	 * A boolean for every player. States true if this player is in the game and not dead.
	 */
	private boolean[]		players;
	/**
	 * Self explanatory.
	 */
	private boolean			paused				= false;

	private boolean			tutoring			= true;

	private boolean			rock_destroyed1		= false;
	private boolean			rock_destroyed2		= true;
	private boolean			rock_destroyed3		= true;
	private boolean			rock_destroyed4		= true;
	private boolean			rock_destroyed5		= true;
	
	/**
	   * the highscore Time
	   */
	private long      highscore;

	private long			tutoringTime;
	/**
	 * The paused time.
	 */
	private long			pauseTime;
	/**
	 * The items in the pause menu.
	 */
	private String[]		menu				= { "Resume Game", "Restart Game", "Return to Title Screen", "Exit Game" };

	private String[][]		tutorialscreen		= {
												{ "This is Bomberman. The goal of this game is to reach the white exit at the right side.", "Next! " },
												{ "You can move your character by pressing the arrow keys on your keyboard.", "Next! " },
												{ "To reach the exit you have to destroy the destructible rocks for which you got bombs!", "Next! " },
												{ "The destructible rocks are the squares which look like they were built of bricks.", "Next! " },
												{ "You can place your bomb by pressing the ctrl-key.", "Next! " },
												{ "Please destoy the first rock on your way to the exit.", " Next!" },
												{ "You got to be careful, though, as your bombs could also kill you.", "Next! " },
												{ "", " " },
												{ "Well done! So now you already comprehended the main mechanism of the game:", "Next! " },
												{ "Place bombs, destroy the rocks and try to reach the exit as fast as possible.", "Next! " },
												{ "Of course it's boring to destroy rocks all alone and simply get to the exit.", "Next! " },
												{ "So in order to make it a little more spicy, there can be other players who would try to reach it before anyone else.", "Next! " },
												{ "And to make the game even more exiting you also can pick up Powerups!", "Next! " },
												{ "So place your bomb near by the next destroyable rock and you will find the first Powerup.", "Next! " },
												{ "", "" },
												{ "This is the Bombup-Powerup. If you pick it up you are able to place more than one bomb.", "Next! " },
												{ "For picking up just walk over it.", "Next! " },
												{ "So place more bombs and continue your way through the level.", "Next! " },
												{ "", "" },
												{ "This is the Flameup-Powerup. If you pick it up your bombs will have a bigger radius.", "Next! " },
												{ "So go on try it out!", "Next! " },
												{ "", "" },
												{ "This is the Kickup-Powerup. If you pick it up you will be able to kick bombs.", "Next! " },
												{ "This is very usefull if you play against other players.", "Next! " },
												{ "To use it you have to place the bomb, get off of it and then walk towards it again.", "Next! " },
												{ "It will be kicked in the direction you walked.", "Next! " },
												{ "", "" },
												{ "This is the Speedup-Powerup. If you pick it up you will be able to run faster.", "Next! " },
												{ "Now you can run to the exit as fast as Speedy Gonzales", "Next! " }
												};
	/**
	 * The items in the end of game menu.
	 */
	private String[]		end					= { "New Game?", "Exit Game" };

	private String[]		endtutorial			= { "Main Menu", "Exit Game" };
	/**
	 * Selected item in the menu (if any).
	 */
	private int				selected;
	/**
	 * Number of the last living player in a battle game. -1 when starting a game.
	 */
	private int				winner;

	/**
	 * 
	 * @param game - The Game.
	 * @param input - Keyboard input.
	 * @param netput - The connector for a network game. Always null when playing local games.
	 * @param mapname - The name of the map.
	 * @param gametype - zero for single, 1 for battle.
	 * @param players - Boolean array, a player will spawn for any true boolean.
	 */
	public CoreGame(Game game, Keyboard input, Connector con, String mapname, int gametype, boolean[] players)
	{
		this.game = game;
		this.input = input;
		this.input.clear();
		this.netput = con;
		this.map = new Map(mapname);
		this.highscore = System.currentTimeMillis();

		this.gametype = gametype;
		this.players = players;
		this.winner = -1;

		if (gametype == NORMAL_GAME)
		{
			map.Add(new Player(input, map, 0, 0, 0));

			do
			{
				int a = (int) (Math.random() * 17);
				int b = (int) (Math.random() * 11);
				if (a != 0 && b != 0 && b != 10 && a != 16 && a % 2 == 0 && b % 2 == 0)
				{
					map.Add(new Exit(map, a, b));
					break;
				}
			} while (true);
		}
		else if (gametype == BATTLE_GAME)
		{
			for (int i = 0; i < 4; i++)
			{
				if (players[i])
				{
					map.Add(new Player(input, map, Game.spawns[i][0],
					Game.spawns[i][1], i));
				}
			}
		}
		else if (gametype == NETWORK_GAME_HOST)
		{
			map.Add(new Player(input, map, Game.spawns[0][0], Game.spawns[0][1], 0));
			map.Add(new Player(netput, map, Game.spawns[1][0], Game.spawns[1][1], 1));
		}
		else if (gametype == NETWORK_GAME_CLIENT)
		{
			map.Add(new Player(netput, map, Game.spawns[0][0], Game.spawns[0][1], 0));
			map.Add(new Player(input, map, Game.spawns[1][0], Game.spawns[1][1], 1));
		}
		else if (gametype == TUTORIAL)
		{
			map.Add(new Player(input, map, 0, 5, 0));
			for (int count = 2; count < 9; count = count + 1)
			{
				if (count % 2 == 0)
				{
					if (count != 4 & count != 6)
					{
						for (int seccount = 5; seccount < 17; seccount++)
						{
							map.Add(new Rock(map, seccount, count));
						}
					}
					else
					{
						for (int seccount = 5; seccount < 15; seccount++)
						{
							map.Add(new Rock(map, seccount, count));
						}
					}
				}
				else
				{
					if (count != 5)
					{
						for (int seccount = 5; seccount < 17; seccount = seccount + 2)
						{
							map.Add(new Rock(map, seccount, count));
						}
					}
					else
					{
						for (int seccount = 5; seccount < 15; seccount = seccount + 2)
						{
							map.Add(new Rock(map, seccount, count));
						}
					}
				}

			}
			map.Add(new Exit(map, 16, 5));
		}
		else
		{
			System.err.println("Invalid Game Type.");
			System.exit(0);
		}
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
	 * Pauses the game if the game tells you how to play and saves the system time when game was paused.
	 */
	public void Tutoring()
	{
		tutoringTime = System.currentTimeMillis();
		tutoring = true;
	}

	/**
	 * Resumes the game and adds the paused time to the creation time of all map objects.
	 */
	public void Resume()
	{
		if (paused == true)
		{
			long pausedTime = System.currentTimeMillis() - pauseTime;
			 highscore += pausedTime;
			for (MapObject o : map.objects)
			{
				o.creationTime += pausedTime;
			}
			paused = false;
		}
		else
		{
			long tutoredTime = System.currentTimeMillis() - tutoringTime;
			for (MapObject o : map.objects)
			{
				o.creationTime += tutoredTime;
			}
			tutoring = false;
		}

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

		if (input.use(KeyEvent.VK_ENTER))
		{
			switch (selected)
			{
				case 0:
					Resume();
					break;
				case 1:
					if (gametype == NORMAL_GAME)
					{
						game.startCoreGame(0, null);
					}
					else if (gametype == BATTLE_GAME)
					{
						game.startCoreGame(1, players);
					}
					else
					{
						game.startCoreGame(2, null);
					}
					break;
				case 2:
					game.stopCoreGame();
					break;
				case 3:
					System.exit(0);
					break;
			}
		}
		else if (input.use(KeyEvent.VK_ESCAPE))
			Resume();
	}

	/**
	 * The update method used when the tutorial text is visible.
	 */

	public void UpdateTutoring()
	{

		selected = 1;
		if (input.use(KeyEvent.VK_ENTER))
		{
			tutorialcount++;
			if (tutorialcount == 7 || tutorialcount == 14 || tutorialcount == 18 || tutorialcount == 21 || tutorialcount == 26 || tutorialcount == 29)
			{
				tutoring = false;
				tutorialcount++;
				Resume();
			}
		}
		else if (input.use(KeyEvent.VK_ESCAPE))
		{
			tutorialcount++;
			if (tutorialcount == 7 || tutorialcount == 14 || tutorialcount == 18 || tutorialcount == 21 || tutorialcount == 26 || tutorialcount == 29)
			{
				tutoring = false;
				tutorialcount++;
				Resume();
			}
		}

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
	public void RenderTutoring(Graphics2D g)
	{

		String len = "";

		for (int i = 0; i < 2; i++)
		{
			String s = tutorialscreen[tutorialcount][i];
			if (s.length() > len.length())
				len = s;
		}

		int x1 = (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(len) / 2) - 15, x2 = (Game.WIDTH / 2) + (g.getFontMetrics().stringWidth(len) / 2) + 9;
		int y1 = (Game.HEIGHT / 2) - 2 * 4 - 24, y2 = (Game.HEIGHT / 2) - 2 * 4 + 2 * 24;

		g.fillRect(x1, y1, x2 - x1 + g.getFontMetrics().stringWidth("<"), y2 - y1);

		for (int i = 0; i < 2; i++)
		{
			String msg = tutorialscreen[tutorialcount][i];
			int y = (Game.HEIGHT / 2) - 2 * 4 + i * 24;
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

		if (input.use(KeyEvent.VK_ENTER))
		{
			switch (selected)
			{
				case 0:
					if (gametype == NORMAL_GAME)
					{
						game.startCoreGame(NORMAL_GAME, null);
					}
					else if (gametype == BATTLE_GAME)
					{
						game.startCoreGame(BATTLE_GAME, players);
					}
					else if (gametype == NETWORK_GAME_HOST)
					{
						netput.sayStart();
					}
					else if (gametype == TUTORIAL)
					{
						game.stopCoreGame();
						break;
					}
					break;
				case 1:
					if (netput != null)
					{
						netput.close();
					}
					System.exit(0);
					break;
			}
		}
		else if (input.use(KeyEvent.VK_ESCAPE))
		{
			game.stopCoreGame();
			if (netput != null)
			{
				netput.disconnect();
			}
		}
	}

	/**
	 * Renders the game while end menu is active.
	 * 
	 * @param g - Graphics to be painted.
	 */
	public void RenderEnd(Graphics2D g)
	{
		String len = "";
		g.fillRect(Game.WIDTH * 107 / 320, Game.HEIGHT * 93 / 320, Game.WIDTH * 108 / 320, Game.HEIGHT * 93 / 320);
		g.setColor(Color.gray);
		if (winner != 0)
			if (gametype == TUTORIAL)
			{
				g.drawString("Congratulation, you finished the tutorial!", Game.WIDTH * 29 / 80, Game.HEIGHT * 31 / 80);
			}
			else
			{
				g.drawString("And the winner is ... " + winner, Game.WIDTH * 31 / 80, Game.HEIGHT * 31 / 80);
			}
		else
			g.drawString("You lost!", Game.WIDTH * 31 / 80, Game.HEIGHT * 31 / 80);

		for (int i = 0; i < end.length; i++)
		{
			String s = end[i];
			if (s.length() > len.length())
				len = s;
		}

		int x1 = (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(len) / 2) - 15, x2 = (Game.WIDTH / 2) + (g.getFontMetrics().stringWidth(len) / 2) + 9;
		// int y1 = (Game.HEIGHT / 2) - end.length * 4 - 24, y2 = (Game.HEIGHT / 2) - end.length * 4 + end.length * 24;

		// g.fillRect(x1, y1, x2 - x1 + g.getFontMetrics().stringWidth("<"), y2 - y1);

		if (gametype == TUTORIAL)
		{
			for (int i = 0; i < endtutorial.length; i++)
			{

				String msg = endtutorial[i];
				int y = (Game.HEIGHT / 2) - endtutorial.length * 4 + i * 24;
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
		else
		{
			for (int i = 0; i < end.length; i++)
			{

				String msg = end[i];
				int y = (Game.HEIGHT / 2) - end.length * 4 + i * 24;
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
	}

	/**
	 * The update method for core game. Updates the map and (if needed) the pause- or end- menu.
	 * 
	 * @see bomberman.map.Map#Update()
	 */
	public void Update()
	{
		if (winner > -1)
		{
			UpdateEnd();
			return;
		}
		else if (paused)
		{
			UpdatePause();
			return;
		}
		if (gametype == TUTORIAL)
		{
			if (!rock_destroyed1)
			{
				boolean rock_found = false;

				for (MapObject o : map.getObjectsOnTile(5, 5))
				{
					if (o instanceof Rock)
					{
						rock_found = true;
						break;
					}
				}
				if (rock_found == false)
				{
					rock_destroyed1 = true;
					rock_destroyed2 = false;
					tutoring = true;
				}
			}
			if (!rock_destroyed2)
			{
				boolean rock_found = false;

				for (MapObject o : map.getObjectsOnTile(5, 4))
				{
					if (o instanceof Rock)
					{
						rock_found = true;
						break;
					}
				}
				if (rock_found == false)
				{
					rock_destroyed2 = true;
					rock_destroyed3 = false;
					map.Add(new Bombup(map, 0, 4));
					tutoring = true;
				}
			}
			if (!rock_destroyed3)
			{
				boolean rock_found = false;
				boolean rock_found2 = false;

				for (MapObject o : map.getObjectsOnTile(5, 3))
				{
					if (o instanceof Rock)
					{
						rock_found = true;
						break;
					}
				}
				for (MapObject o : map.getObjectsOnTile(5, 7))
				{
					if (o instanceof Rock)
					{
						rock_found2 = true;
						break;
					}
				}

				if (rock_found == false | rock_found2 == false)
				{
					rock_destroyed3 = true;
					rock_destroyed4 = false;
					map.Add(new Flameup(map, 2, 4));
					tutoring = true;
				}

			}

			if (!rock_destroyed4)
			{
				boolean player_reached = false;

				for (int i = 2; i < 9; i = i + 2)
				{
					for (MapObject o : map.getObjectsOnTile(8, i))
					{
						if (o instanceof Player)
						{
							player_reached = true;
							break;
						}
					}
				}

				if (player_reached == true)
				{
					rock_destroyed4 = true;
					rock_destroyed5 = false;
					map.Add(new Kickup(map, 2, 6));
					tutoring = true;
				}
			}
			if (!rock_destroyed5)
			{
				boolean player_reached = false;

				for (int i = 2; i < 9; i = i + 1)
				{
					for (MapObject o : map.getObjectsOnTile(11, i))
					{
						if (o instanceof Player)
						{
							player_reached = true;
							break;
						}
					}
				}

				if (player_reached == true)
				{
					rock_destroyed5 = true;
					map.Add(new Speedup(map, 0, 6));
					map.Add(new Speedup(map, 0, 6));
					tutoring = true;
				}
			}

			if (tutoring)
			{
				Tutoring();
				UpdateTutoring();
				return;
			}

		}

		map.Update();

		if (gametype == NORMAL_GAME)
		{
			Player player = null;

			for (MapObject o : map.objects)
			{
				if (o instanceof Player)
					player = (Player) o;
			}

			if (player == null)
				winner = 0;
			else if (player.hasReachedExit)
				winner = 1;
				highscore = System.currentTimeMillis() - highscore;
				writehs();
		}
		else if (gametype == BATTLE_GAME)
		{
			if (map.num_of_players < 2)
			{
				highscore = System.currentTimeMillis() - highscore;
				writehs();
				for (MapObject o : map.objects)
				{
					if (o instanceof Player)
						winner = ((Player) o).player_id + 1;
				}
			}
		}
		else if (gametype == TUTORIAL)
		{
			Player player = null;
			for (MapObject o : map.objects)
			{
				if (o instanceof Player)
					player = (Player) o;
			}
			if (player == null)
				winner = 0;
			else if (player.hasReachedExit)
				winner = 1;
		}

		if (2 < gametype)
		{
			netput.update();
		}

		if (input.use(KeyEvent.VK_ESCAPE) && gametype < 3)
			Pause();

	}

	/**
	 * Renders the map and a menu if needed.
	 * 
	 * @param g - Graphics to be painted.
	 */
	public void Render(Graphics2D g)
	{
		map.Render(g);

		if (winner > -1)
			RenderEnd(g);
		else if (paused)
			RenderPause(g);
		else if (gametype == TUTORIAL)
		{
			if (tutoring)
			{
				RenderTutoring(g);
			}
		}
	}

	/**
	 * Write Highscore into textfile
	 */
	private void writehs()
	{
		File hsfile = new File("highscore.txt");
		boolean nhs = false; // new highscore?
		Scanner in = null;
		FileWriter out = null;
		String hs[] = new String[HSS]; // the highscore-file
		int linesread = -1;

		try
		{
			in = new Scanner(hsfile);
			for (int i = 0; i < HSS; i++)
			{ // Save File in hs
				if (in.hasNextLine())
				{
					linesread++;
					hs[i] = in.nextLine();
				}
				else
					hs[i] = (char) 255 + ""; // Beacause otherwise Arrays.sort() don't work

			}
			if (linesread == HSS - 1)// Highscore-List full
			{
				for (int i = 0; i < HSS; i++)
				{ // Check: new highscore?
					if (hs[i] != null)
					{
						Scanner tmp = new Scanner(hs[i]);
						if (tmp.hasNextLong())
						{
							if (tmp.nextLong() > highscore)
							{// New Highscore
								hs[linesread] = highscore + " " + new Date();
								nhs = true;
								break;
							}
						}
					}
				}

			}
			// Highscore-List not full add any highscore
			else
			{
				hs[++linesread] = highscore + " " + new Date();
				nhs = true;
			}
			Arrays.sort(hs);

		}
		catch (FileNotFoundException e)
		{
			// No HS-File exists
			hs[0] = highscore + " " + new Date();
			linesread = 0;
			nhs = true;

		}
		finally
		{
			if (in != null)
			{
				in.close();
			}
		}
		// Write new Highscore-File
		if (nhs)
		{
			if (hsfile.exists())
				if (!hsfile.delete()) return;
			try
			{
				out = new FileWriter(hsfile);
				for (int i = 0; i <= linesread; i++)
				{
					out.write(hs[i] + System.getProperty("line.separator"));
				}

			}
			catch (FileNotFoundException e)
			{
				// No HS-File could created
				return;
			}
			catch (IOException e)
			{
				// IO-Error
				return;
			}
			finally
			{
				if (out != null)
				{
					try
					{
						out.close();
					}
					catch (IOException e)
					{
						// IO-Error
						return;
					}
				}
			}
		}

	}
}
