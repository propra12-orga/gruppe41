package bomberman.core;

import highscore.Highscore;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import bomberman.game.Game;
import bomberman.input.Keyboard;
import bomberman.map.MapObject;
import bomberman.players.Player;

public final class BattleGame extends CoreGame
{
	protected int		winner;
	protected long		highscore;
	protected boolean[]	players;

	public BattleGame(Game game, Keyboard input, boolean[] players)
	{
		super(game, input, "basic");
		this.players = players;
		highscore = System.currentTimeMillis();
		for (int i = 0; i < 4; i++)
		{
			if (players[i])
			{
				map.Add(new Player(input, map, Game.spawns[i][0],
				Game.spawns[i][1], i));
			}
		}
	}

	public void Resume()
	{
		super.Resume();
		highscore += (System.currentTimeMillis() - pauseTime);
	}

	public void UpdateEnd()
	{
		super.UpdateEnd();
		if (input.use(KeyEvent.VK_ENTER))
		{
			switch (selected)
			{
				case 0:
					game.startBattleGame(players);
					break;
				case 1:
					System.exit(0);
					break;
			}
		}
	}

	public void UpdatePause()
	{
		super.UpdatePause();
		if (input.use(KeyEvent.VK_ENTER))
		{
			switch (selected)
			{
				case 0:
					Resume();
					break;
				case 1:
					game.startBattleGame(players);
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

	public void RenderEnd(Graphics2D g)
	{
		String len = "";
		g.fillRect(Game.WIDTH * 107 / 320, Game.HEIGHT * 93 / 320, Game.WIDTH * 108 / 320, Game.HEIGHT * 93 / 320);
		g.setColor(Color.gray);
		if (winner != 0)
			g.drawString("And the winner is ... " + winner, Game.WIDTH * 31 / 80, Game.HEIGHT * 31 / 80);
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
		if (gameOver)
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

		if (map.num_of_players < 2)
		{
			gameOver = true;
			highscore = System.currentTimeMillis() - highscore;
			Highscore.writeHighscore(highscore);
			for (MapObject o : map.objects)
			{
				if (o instanceof Player)
					winner = ((Player) o).player_id + 1;
			}
		}

		if (input.use(KeyEvent.VK_ESCAPE))
			Pause();
	}

	public void Render(Graphics2D g)
	{
		map.Render(g);
		if (gameOver)
			RenderEnd(g);
		else if (paused)
			RenderPause(g);
	}

}
