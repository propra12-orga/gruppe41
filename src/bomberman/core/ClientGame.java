package bomberman.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import bomberman.game.Game;
import bomberman.input.Keyboard;
import bomberman.map.MapObject;
import bomberman.network.Client;
import bomberman.players.Player;

public final class ClientGame extends CoreGame
{
	private Client	connection;
	protected boolean won;

	public ClientGame(Game game, Keyboard input, Client con)
	{
		super(game, input, "basic");
		connection = con;
		map.Add(new Player(con, map, Game.spawns[0][0], Game.spawns[0][1], 0));
		map.Add(new Player(input, map, Game.spawns[1][0], Game.spawns[1][1], 1));
	}

	public void UpdateEnd()
	{
		super.UpdateEnd();
		if (input.use(KeyEvent.VK_ENTER) && selected == 1)
		{
			if (connection != null)
			{
				connection.close();
			}
			System.exit(0);
		}
		else if (input.use(KeyEvent.VK_ESCAPE))
		{
			game.stopCoreGame();
			if (connection != null)
			{
				connection.disconnect();
			}
		}
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
		else if (paused)
		{
			UpdatePause();
			return;
		}
		//map.Update();
		connection.update();
		if (map.num_of_players < 2)
		{
			gameOver = true;
			won = false;
			for (MapObject o : map.objects)
			{
				if (o instanceof Player && ((Player) o).player_id==1){
					won = true;
				}
			}
		}
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
