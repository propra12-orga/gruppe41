package bomberman.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.game.Game;
import bomberman.input.Input;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.terrain.Exit;
import bomberman.objects.terrain.Rock;
import bomberman.players.Player;
import bomberman.powerups.Bombup;
import bomberman.powerups.Flameup;
import bomberman.powerups.Kickup;

public class CoreGame
{
	private Game		game;
	private Input		input;
	private Map			map;

	private boolean		paused	= false;
	private boolean		ended 	= false;
	private boolean		start   = true;
	private long		pauseTime;

	private int		winner=0;
	private String[]	menu	= { "Resume Game", "Restart", "Return to Gameselection", "Exit Game" };//"Return to Title Screen" ausgelagert
	private String[]	end	= { "New Game?","Exit Game" };
	
	private int			selected;
	
	public double 		cardgenerator=0.6;
	public int			exitcount = 0;
	
	private BufferedImage			one;
	

	public CoreGame(Game game, Input input, String mapname)
	{
		this.game = game;
		this.input = input;
		this.map = new Map(mapname);

		map.Add(new Player(input, map, 0, 0, 0));
		map.Add(new Player(input, map, 0, 10, 1));
		//map.Add(new Player(input, map, 16, 0, 2));
		//map.Add(new Player(input, map, 16, 10, 3));
		//map.Add(new Speedup(map, 4, 2));
		//map.Add(new Kickup(map, 4, 3));			//nicht mehr notwendig da zufallskalkulator integriert
		//map.Add(new Flameup(map, 4, 2));
		//map.Add(new Bombup(map, 4, 4));
		
		do{
			int a=(int)(Math.random()*17);
			int b=(int)(Math.random()*11);
			if(a!=0&&b!=0&&b!=10&&a!=16&&a%2==0&&b%2==0){
				map.Add(new Exit(map,a,b));
				exitcount=1;
			
			}
			}
			while(exitcount!=1);
		
		for (int x = 0; x < 17; x += 1)
		{
			if(x==0||x==16){
				for (int y = 2; y< 9; y += 1){
					if(Math.random()<cardgenerator)
					map.Add(new Rock(map, x, y));
				}
			
			}
			else if(x%2==0){
				for (int y = 0; y< 11; y += 1){
					if(Math.random()<cardgenerator)
					map.Add(new Rock(map, x, y));
				}
			}
			else if(x%2!=0){
			for (int y = 0; y< 11; y += 2)
			{
				if(x==1||x==15){
					if(y<8){
						if(Math.random()<cardgenerator)
						map.Add(new Rock(map, x, y+2));
					
					}
				}
				else
				if(Math.random()<cardgenerator)
				map.Add(new Rock(map, x, y));
				
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
					game.startCoreGame();
					break;
				case 2:
					game.stopCoreGame();
					break;
				case 3:
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

	public void Update()
	{
		if (paused)
		{
			UpdatePause();
			return;
		}
		if (ended)
		{
			UpdateEnd();
			return;
		}

		map.Update();
		
		for (MapObject o : map.objects)
		{
			if (o instanceof Player){
				if(((Player) o).hasreachedexit==true){
				winner=(((Player) o).player_id + 1);
				ended=true;	
				map.Update();
		}
		}
		}
		;
		if (map.num_of_players < 2)
		{
			for (MapObject o : map.objects)
			{
				if (o instanceof Player)
					winner=(((Player) o).player_id + 1);
					ended=true;
					map.Update();
			}
			;
		}
		if (input.keys[KeyEvent.VK_ESCAPE].clicked)
			Pause();

	}

	public void Render(Graphics2D g)
	{
		map.Render(g);
		

		if (paused)
			RenderPause(g);
		if (ended)
			RenderEnd(g);
	/*	if (start)
			RenderStart(g);
			start=false;
			*/
	}
	
	/*public void RenderStart(Graphics2D g){			// soll countdown zum anfang des spieles ausgeben
		
		try
		{
			one = ImageIO.read(new File("data/sprites/eins.png"));
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		int w = one.getWidth(), h = one.getHeight();
		g.drawImage(one,
					(Game.WIDTH / 2) - (w / 2),
					80,
					(Game.WIDTH / 2) - (w / 2) + w,
					80 + h,
					0,
					0,
					w,
					h,
					null);
		
		try
		{
			g.wait(1);
		}
		catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		
	}	*/
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
					game.startCoreGame();
					break;
				case 1:		
					System.exit(0);
					break;
				
			}
		}
		else if (input.keys[KeyEvent.VK_ESCAPE].clicked)
			System.exit(0);
	}
	public void RenderEnd(Graphics2D g)
	{
		String len = "";
		g.fillRect(Game.WIDTH*107/320, Game.HEIGHT*93/320,Game.WIDTH*108/320 , Game.HEIGHT*93/320);
		g.setColor(Color.gray);
		g.drawString("Der Sieger ist Spieler Nummer "+ winner, Game.WIDTH*31/80,Game.HEIGHT*31/80);


		for (int i = 0; i < end.length; i++)
		{
			String s = end[i];
			if (s.length() > len.length()) len = s;
		}

		int x1 = (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(len) / 2) - 15, x2 = (Game.WIDTH / 2) + (g.getFontMetrics().stringWidth(len) / 2) + 9;
		//int y1 = (Game.HEIGHT / 2) - end.length * 4 - 24, y2 = (Game.HEIGHT / 2) - end.length * 4 + end.length * 24;

		//g.fillRect(x1, y1, x2 - x1 + g.getFontMetrics().stringWidth("<"), y2 - y1);
		

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
