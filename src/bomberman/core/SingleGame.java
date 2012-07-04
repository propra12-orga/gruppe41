package bomberman.core;

import highscore.Highscore;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import bomberman.game.Game;
import bomberman.input.Keyboard;
import bomberman.objects.terrain.Exit;
import bomberman.players.Player;

public final class SingleGame extends CoreGame
{
	protected boolean	won;
	protected Player	player;
	protected long		highscore;

	public SingleGame(Game game, Keyboard input)
	{
		super(game, input, "basic");
		this.player = new Player(input, map, 0, 0, 0);
		map.Add(player);
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
		won = false;
		highscore = System.currentTimeMillis();
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
					game.startNormalGame();
					break;
				case 1:
					System.exit(0);
					break;
			}
		}
		else if (input.use(KeyEvent.VK_ESCAPE))
		{
			game.stopCoreGame();
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
					game.startNormalGame();
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
		if (won)
			g.drawString("Yout won!", Game.WIDTH * 31 / 80, Game.HEIGHT * 31 / 80);
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
		if (paused)
		{
			UpdatePause();
			return;
		}

		map.Update();

		if (map.num_of_players == 0)
		{
			gameOver = true;
			won = false;
		}
		else if (player.hasReachedExit)
		{
			gameOver = true;
			won = true;
			highscore = System.currentTimeMillis() - highscore;
			Highscore.writeHighscore(highscore);
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
