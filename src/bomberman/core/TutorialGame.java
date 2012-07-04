package bomberman.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import bomberman.game.Game;
import bomberman.input.Keyboard;
import bomberman.map.MapObject;
import bomberman.objects.terrain.Exit;
import bomberman.objects.terrain.Rock;
import bomberman.players.Player;
import bomberman.powerups.Bombup;
import bomberman.powerups.Flameup;
import bomberman.powerups.Kickup;
import bomberman.powerups.Speedup;

public final class TutorialGame extends CoreGame
{
	protected static final String[]		end				= { "Main Menu", "Exit Game" };
	protected static final String[][]	tutorialscreen	= {
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
	protected int						tutorialcount	= 0;
	protected boolean					tutoring		= true;
	protected boolean					rock_destroyed1	= false;
	protected boolean					rock_destroyed2	= true;
	protected boolean					rock_destroyed3	= true;
	protected boolean					rock_destroyed4	= true;
	protected boolean					rock_destroyed5	= true;
	protected long						tutoringTime;
	protected Player					player;
	protected boolean					won;

	public TutorialGame(Game game, Keyboard input)
	{
		super(game, input, "tutorial");
		won = false;
		this. player = new Player(input, map, 0, 5, 0);
		map.Add(player);
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

	/**
	 * Pauses the game if the game tells you how to play and saves the system time when game was paused.
	 */
	public void Tutoring()
	{
		tutoringTime = System.currentTimeMillis();
		tutoring = true;
	}

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
				ResumeTut();
			}
		}
		else if (input.use(KeyEvent.VK_ESCAPE))
		{
			tutorialcount++;
			if (tutorialcount == 7 || tutorialcount == 14 || tutorialcount == 18 || tutorialcount == 21 || tutorialcount == 26 || tutorialcount == 29)
			{
				tutoring = false;
				tutorialcount++;
				ResumeTut();
			}
		}

	}

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

	public void UpdateEnd()
	{
		super.UpdateEnd();

		if (input.use(KeyEvent.VK_ENTER))
		{
			switch (selected)
			{
				case 0:
					game.stopCoreGame();
					break;
				case 1:
					System.exit(0);
					break;
			}
		}
	}

	public void ResumeTut()
	{
		long tutoredTime = System.currentTimeMillis() - tutoringTime;
		for (MapObject o : map.objects)
		{
			o.creationTime += tutoredTime;
		}
		tutoring = false;

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
					game.startTutorial();
					;
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
			g.drawString("Congratulation, you finished the tutorial!", Game.WIDTH * 29 / 80, Game.HEIGHT * 31 / 80);
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

		map.Update();

		if (map.num_of_players == 0)
		{
			input.clear();
			gameOver = true;
			won = false;
		}
		else if (player.hasReachedExit)
		{
			input.clear();
			gameOver = true;
			won = true;
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
		else if (tutoring)
		{
			RenderTutoring(g);
		}
	}

}
