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

public class CoreGame
{
	public static final int	NORMAL_GAME	= 0;
	public static final int	BATTLE_GAME	= 1;

	private Game			game;
	private Input			input;
	private Map				map;

	private int				gametype;
	private boolean[]		players;

	private boolean			paused		= false;
	private long			pauseTime;

	private String[]		menu		= { "Resume Game", "Return to Title Screen", "Exit Game" };
	private String[]		end			= { "New Game?", "Exit Game" };
	private int				selected;

	private int				winner;

	public CoreGame(Game game, Input input, String mapname, int gametype, boolean[] players)
	{
		this.game = game;
		this.input = input;
		this.map = new Map(mapname);

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
					map.Add(new Player(input, map, Game.spawns[i][0], Game.spawns[i][1], i));
				}
			}
		}
	}

	public void Pause()
	{
		pauseTime = System.currentTimeMillis();
		paused = true;
	}

	public void Resume()
	{
		long pausedTime = System.currentTimeMillis() - pauseTime;
		for (MapObject o : map.objects)
		{
			o.creationTime += pausedTime;
		}
		paused = false;
	}

	public void UpdatePause()
	{
		int sel = 0;

		if (input.keys[KeyEvent.VK_DOWN].clicked)
			sel++;
		else if (input.keys[KeyEvent.VK_UP].clicked)
			sel--;

		do
		{
			selected += sel;

			if (selected >= menu.length)
				selected -= menu.length;
			else if (selected < 0)
				selected += menu.length;

		} while (menu[selected] == "");

		if (input.keys[KeyEvent.VK_ENTER].clicked)
		{
			switch (selected)
			{
				case 0:
					Resume();
					break;
				case 1:
					game.stopCoreGame();
					break;
				case 2:
					System.exit(0);
					break;
			}
		}
		else if (input.keys[KeyEvent.VK_ESCAPE].clicked)
			Resume();
	}

	public void RenderPause(Graphics2D g)
	{
		String len = "";

		for (int i = 0; i < menu.length; i++)
		{
			String s = menu[i];
			if (s.length() > len.length()) len = s;
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

	public void UpdateEnd()
	{
		int sel = 0;

		if (input.keys[KeyEvent.VK_DOWN].clicked)
			sel++;
		else if (input.keys[KeyEvent.VK_UP].clicked)
			sel--;

		do
		{
			selected += sel;

			if (selected >= end.length)
				selected -= end.length;
			else if (selected < 0)
				selected += end.length;

		} while (end[selected] == "");

		if (input.keys[KeyEvent.VK_ENTER].clicked)
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
					break;
				case 1:
					System.exit(0);
					break;

			}
		}
		else if (input.keys[KeyEvent.VK_ESCAPE].clicked)
			game.stopCoreGame();
	}

	public void RenderEnd(Graphics2D g)
	{
		String len = "";
		g.fillRect(Game.WIDTH * 107 / 320, Game.HEIGHT * 93 / 320, Game.WIDTH * 108 / 320, Game.HEIGHT * 93 / 320);
		g.setColor(Color.gray);
		if (winner != 0)
			g.drawString("Der Sieger ist Spieler Nummer " + winner, Game.WIDTH * 31 / 80, Game.HEIGHT * 31 / 80);
		else
			g.drawString("Verloren :(", Game.WIDTH * 31 / 80, Game.HEIGHT * 31 / 80);

		for (int i = 0; i < end.length; i++)
		{
			String s = end[i];
			if (s.length() > len.length()) len = s;
		}

		int x1 = (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(len) / 2) - 15, x2 = (Game.WIDTH / 2) + (g.getFontMetrics().stringWidth(len) / 2) + 9;
		// int y1 = (Game.HEIGHT / 2) - end.length * 4 - 24, y2 = (Game.HEIGHT /
		// 2) - end.length * 4 + end.length * 24;

		// g.fillRect(x1, y1, x2 - x1 + g.getFontMetrics().stringWidth("<"), y2-
		// y1);

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
		}
		else if (gametype == BATTLE_GAME)
		{
			if (map.num_of_players < 2)
			{
				for (MapObject o : map.objects)
				{
					if (o instanceof Player)
						winner = ((Player) o).player_id + 1;
				}
			}
		}

		if (input.keys[KeyEvent.VK_ESCAPE].clicked)
			Pause();

	}

	public void Render(Graphics2D g)
	{
		map.Render(g);

		if (winner > -1)
			RenderEnd(g);
		else if (paused)
			RenderPause(g);
	}
}
