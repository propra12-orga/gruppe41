package bomberman.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import bomberman.game.Game;
import bomberman.input.Input;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.terrain.Exit;
import bomberman.players.Player;

/**
 * Class for the game, excluding menu and stuff.
 * 
 */
public class CoreGame {
	public static final int NORMAL_GAME = 0;
	public static final int BATTLE_GAME = 1;
	/**
	 * Reference to the game instance, used for startgame/endgame methods.
	 */
	private Game game;
	/**
	 * Keyboard input to control players.
	 * 
	 * @see bomberman.input.Input
	 */
	private Input input;
	/**
	 * Game map.
	 */
	private Map map;
	/**
	 * Gametype, 0 = single player, 1 = battle game, third mode will come soon.
	 */
	private int gametype;
	/**
	 * A boolean for every player. States true if this player is in the game and
	 * not dead.
	 */
	private boolean[] players;
	/**
	 * Self explanatory.
	 */
	private boolean paused = false;
	/**
	 * The paused time.
	 */
	private long pauseTime;
	/**
	 * The items in the pause menu.
	 */
	private String[] menu = { "Resume Game", "Restart Game",
			"Return to Title Screen", "Exit Game" };
	/**
	 * The items in the end of game menu.
	 */
	private String[] end = { "New Game?", "Exit Game" };
	/**
	 * Selected item in the menu (if any).
	 */
	private int selected;
	/**
	 * Number of the last living player in a battle game. -1 when starting a
	 * game.
	 */
	private int winner;

	/**
	 * 
	 * @param game
	 *            - The Game.
	 * @param input
	 *            - Keyboard input.
	 * @param mapname
	 *            - The name of the map.
	 * @param gametype
	 *            - zero for single, 1 for battle.
	 * @param players
	 *            - Boolean array, a player will spawn for any true boolean.
	 */
	public CoreGame(Game game, Input input, String mapname, int gametype,
			boolean[] players) {
		this.game = game;
		this.input = input;
		this.map = new Map(mapname);

		this.gametype = gametype;
		this.players = players;
		this.winner = -1;

		if (gametype == NORMAL_GAME) {
			map.Add(new Player(input, map, 0, 0, 0));
			do {
				int a = (int) (Math.random() * 17);
				int b = (int) (Math.random() * 11);
				if (a != 0 && b != 0 && b != 10 && a != 16 && a % 2 == 0
						&& b % 2 == 0) {
					map.Add(new Exit(map, a, b));
					break;
				}
			} while (true);
		} else if (gametype == BATTLE_GAME) {
			for (int i = 0; i < 4; i++) {
				if (players[i]) {
					map.Add(new Player(input, map, Game.spawns[i][0],
							Game.spawns[i][1], i));
				}
			}
		}
	}

	/**
	 * Pauses the game and saves the system time when game was paused.
	 */
	public void Pause() {
		pauseTime = System.currentTimeMillis();
		paused = true;
	}

	/**
	 * Resumes the game and adds the paused time to the creation time of all map
	 * objects.
	 */
	public void Resume() {
		long pausedTime = System.currentTimeMillis() - pauseTime;
		for (MapObject o : map.objects) {
			o.creationTime += pausedTime;
		}
		paused = false;
	}

	/**
	 * The update method used when the game is paused.
	 */
	public void UpdatePause() {
		int sel = 0;

		if (input.keys[KeyEvent.VK_DOWN].clicked)
			sel++;
		else if (input.keys[KeyEvent.VK_UP].clicked)
			sel--;

		do {
			selected += sel;

			if (selected >= menu.length)
				selected -= menu.length;
			else if (selected < 0)
				selected += menu.length;

		} while (menu[selected] == "");

		if (input.keys[KeyEvent.VK_ENTER].clicked) {
			switch (selected) {
			case 0:
				Resume();
				break;
			case 1:
				if (gametype == NORMAL_GAME) {
					game.startCoreGame(0, null);
				} else {
					game.startCoreGame(1, players);
				}
				break;
			case 2:
				game.stopCoreGame();
				break;
			case 3:
				System.exit(0);
				break;
			}
		} else if (input.keys[KeyEvent.VK_ESCAPE].clicked)
			Resume();
	}

	/**
	 * Renders the game screen while game is paused.
	 * 
	 * @param g
	 *            - Graphics used for painting.
	 */
	public void RenderPause(Graphics2D g) {
		String len = "";

		for (int i = 0; i < menu.length; i++) {
			String s = menu[i];
			if (s.length() > len.length())
				len = s;
		}

		int x1 = (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(len) / 2)
				- 15, x2 = (Game.WIDTH / 2)
				+ (g.getFontMetrics().stringWidth(len) / 2) + 9;
		int y1 = (Game.HEIGHT / 2) - menu.length * 4 - 24, y2 = (Game.HEIGHT / 2)
				- menu.length * 4 + menu.length * 24;

		g.fillRect(x1, y1, x2 - x1 + g.getFontMetrics().stringWidth("<"), y2
				- y1);

		for (int i = 0; i < menu.length; i++) {
			String msg = menu[i];
			int y = (Game.HEIGHT / 2) - menu.length * 4 + i * 24;
			g.setColor(Color.gray);
			if (i == selected) {
				g.setColor(Color.white);
				g.drawString(">", x1, y);
				g.drawString("<", x2, y);
			}
			g.drawString(msg, (Game.WIDTH / 2)
					- (g.getFontMetrics().stringWidth(msg) / 2), y);
		}
	}

	/**
	 * The update function for a ended game (when end menu is active).
	 */
	public void UpdateEnd() {
		int sel = 0;

		if (input.keys[KeyEvent.VK_DOWN].clicked)
			sel++;
		else if (input.keys[KeyEvent.VK_UP].clicked)
			sel--;

		do {
			selected += sel;

			if (selected >= end.length)
				selected -= end.length;
			else if (selected < 0)
				selected += end.length;

		} while (end[selected] == "");

		if (input.keys[KeyEvent.VK_ENTER].clicked) {
			switch (selected) {
			case 0:
				if (gametype == NORMAL_GAME) {
					game.startCoreGame(NORMAL_GAME, null);
				} else if (gametype == BATTLE_GAME) {
					game.startCoreGame(BATTLE_GAME, players);
				}
				break;
			case 1:
				System.exit(0);
				break;

			}
		} else if (input.keys[KeyEvent.VK_ESCAPE].clicked)
			game.stopCoreGame();
	}

	/**
	 * Renders the game while end menu is active.
	 * 
	 * @param g
	 *            - Graphics to be painted.
	 */
	public void RenderEnd(Graphics2D g) {
		String len = "";
		g.fillRect(Game.WIDTH * 107 / 320, Game.HEIGHT * 93 / 320,
				Game.WIDTH * 108 / 320, Game.HEIGHT * 93 / 320);
		g.setColor(Color.gray);
		if (winner != 0)
			g.drawString("And the winner is ... " + winner,
					Game.WIDTH * 31 / 80, Game.HEIGHT * 31 / 80);
		else
			g.drawString("You lost!", Game.WIDTH * 31 / 80,
					Game.HEIGHT * 31 / 80);

		for (int i = 0; i < end.length; i++) {
			String s = end[i];
			if (s.length() > len.length())
				len = s;
		}

		int x1 = (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(len) / 2)
				- 15, x2 = (Game.WIDTH / 2)
				+ (g.getFontMetrics().stringWidth(len) / 2) + 9;
		// int y1 = (Game.HEIGHT / 2) - end.length * 4 - 24, y2 = (Game.HEIGHT /
		// 2) - end.length * 4 + end.length * 24;

		// g.fillRect(x1, y1, x2 - x1 + g.getFontMetrics().stringWidth("<"), y2-
		// y1);

		for (int i = 0; i < end.length; i++) {
			String msg = end[i];
			int y = (Game.HEIGHT / 2) - end.length * 4 + i * 24;
			g.setColor(Color.gray);

			if (i == selected) {
				g.setColor(Color.white);
				g.drawString(">", x1, y);
				g.drawString("<", x2, y);
			}
			g.drawString(msg, (Game.WIDTH / 2)
					- (g.getFontMetrics().stringWidth(msg) / 2), y);
		}
	}

	/**
	 * The update method for core game. Updates the map and (if needed) the
	 * pause- or end- menu.
	 * 
	 * @see bomberman.map.Map#Update()
	 */
	public void Update() {
		if (winner > -1) {
			UpdateEnd();
			return;
		} else if (paused) {
			UpdatePause();
			return;
		}

		map.Update();

		if (gametype == NORMAL_GAME) {
			Player player = null;

			for (MapObject o : map.objects) {
				if (o instanceof Player)
					player = (Player) o;
			}

			if (player == null)
				winner = 0;
			else if (player.hasReachedExit)
				winner = 1;
		} else if (gametype == BATTLE_GAME) {
			if (map.num_of_players < 2) {
				for (MapObject o : map.objects) {
					if (o instanceof Player)
						winner = ((Player) o).player_id + 1;
				}
			}
		}

		if (input.keys[KeyEvent.VK_ESCAPE].clicked)
			Pause();

	}

	/**
	 * Renders the map and a menu if needed.
	 * 
	 * @param g
	 *            - Graphics to be painted.
	 */
	public void Render(Graphics2D g) {
		map.Render(g);

		if (winner > -1)
			RenderEnd(g);
		else if (paused)
			RenderPause(g);
	}
}
