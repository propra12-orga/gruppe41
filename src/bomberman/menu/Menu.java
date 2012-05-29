package bomberman.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.game.Game;
import bomberman.input.Input;



public class Menu
{
	private static final int		TITLE_SCREEN		= 0;
	private static final int		SETTINGS_SCREEN		= 1;
	private static final int		KEYBOARD_SETTINGS	= 2;
	private static final int		HIGHSCORE_SCREEN	= 3;
	private static final int		GRAPHIC_SCREEN		= 4;
	private static final int		FIGURE_SELECTION	= 5;
	private static final int		MAP_SELECTION		= 6;
	private static final int		GAME_SELECTION		= 7;

	
	//highscore-liste
	
	private String erster="";
	private String zweiter="";
	private String dritter="";
	private String ersterpunkte="";
	private String zweiterpunkte="";
	private String dritterpunkte="";
	private String spielername="";
	
	
	private Game					game;
	private Input					input;

	private BufferedImage			title, logo;

	BufferedReader br = null;
	
	private static final String[][]	items				= {
														{ "Start  Game", "Highscore", "Settings", "Exit" },
														{ "Player ", "Graphics" ,"Back" },
														{ "Up", "Down", "Left", "Right", "Action", "", "Back" },
														{ "Back" },
														{"Figuredesign", "Mapdesign","Back"},
														{},
														{},
														{"Singelplayer","Multiplayer","Back"}
														};

	private int						selected			= 0, count = items[0].length, menu = 0, player_sel = 1;
	private boolean					wait_for_key		= false;
	
	{try {
        br = new BufferedReader(new FileReader(new File("besten.txt")));
        String line = null;
        while((line = br.readLine()) != null) {               
            String []parts= line.split(";");
            erster=parts[0];
            ersterpunkte=parts[1];
            zweiter=parts[2];
            zweiterpunkte=parts[3];
            dritter=parts[4];
            dritterpunkte=parts[5];
            spielername=parts[6];
           
        }
    } catch(FileNotFoundException f) {
        f.printStackTrace();
    } catch(IOException f) {
        f.printStackTrace();
    } finally {
        if(br != null) {
            try {
                br.close();
            } catch(IOException f) {
                f.printStackTrace();
            }
        }
    }}



	public Menu(Game game, Input input)
	{
		this.game = game;
		this.input = input;
		//System.out.println(spielername);

		try
		{
			title = ImageIO.read(new File("data/sprites/title.png"));
			logo = ImageIO.read(new File("data/sprites/logo_" + ((int) (Math.random() * 3 + 1)) + ".png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void Change(int menu)
	{
		this.menu = menu;
		this.selected = 0;
		this.count = items[menu].length;
	}

	public void Update()
	{
		if (menu == SETTINGS_SCREEN)
		{
			if (selected == 0)
			{
				if (input.keys[KeyEvent.VK_RIGHT].clicked)
					player_sel++;
				else if (input.keys[KeyEvent.VK_LEFT].clicked)
					player_sel--;

				if (player_sel > 4)
					player_sel -= 1;
				else if (player_sel < 1)
					player_sel += 1;
			}
		}
		else if (menu == KEYBOARD_SETTINGS)
		{
			if (wait_for_key)
			{
				int key = -1;

				for (int i = 0; i < 256; i++)
				{
					if (input.keys[i].clicked)
						key = i;
				}

				if (key > -1)
				{
					Game.controls[player_sel - 1][selected] = key;
					wait_for_key = false;
				}

				return;
			}
		}

		int sel = 0;

		if (input.keys[KeyEvent.VK_DOWN].clicked)
			sel++;
		else if (input.keys[KeyEvent.VK_UP].clicked)
			sel--;

		do
		{
			selected += sel;

			if (selected >= count)
				selected -= count;
			else if (selected < 0)
				selected += count;

		} while (items[menu][selected] == "");

		if (input.keys[KeyEvent.VK_ENTER].clicked)
		{
			switch (menu)
			{
				case TITLE_SCREEN:
					switch (selected)
					{
						case 0:
							Change(GAME_SELECTION);
							//game.startCoreGame();
							break;
						case 1:
							Change(HIGHSCORE_SCREEN);
							break;
						case 2:
							Change(SETTINGS_SCREEN);
							break;
						case 3:
							System.exit(0);
							break;
					}
					break;
				case SETTINGS_SCREEN:
					switch (selected)
					{
						case 0:
							Change(KEYBOARD_SETTINGS);
							break;
						case 1:
							Change(GRAPHIC_SCREEN);
							break;
						case 2:
							Change(TITLE_SCREEN);
							break;
					}
					break;
				case KEYBOARD_SETTINGS:
					switch (selected)
					{
						case 5:				// warum ist hier 5 und nicht 0?
							break;
						case 6:				// warum 6?
							Change(SETTINGS_SCREEN);
							break;
						default:
							wait_for_key = true;
							break;
					}
					break;
				case HIGHSCORE_SCREEN:
					switch (selected)
					{
						case 0:
							Change(TITLE_SCREEN);
							break;
					}
					break;
				case GRAPHIC_SCREEN:
					switch (selected)
					{
						case 0:
							//Change (SETTINGS_SCREEN);
							break;
						case 1:
							//Change (SETTINGS_SCREEN);
							break;
						case 2:
							Change (SETTINGS_SCREEN);
							break;
					}
					break;
				case GAME_SELECTION:
					switch (selected)
					{
						case 0:
							game.startCoreGame();
							break;
						case 1:
							break;
						case 2:
							Change (TITLE_SCREEN);
							break;
							
					}
					break;
			}
		}
		else if (input.keys[KeyEvent.VK_ESCAPE].clicked)
		{
			switch (menu)
			{
				case TITLE_SCREEN:
					System.exit(0);
					break;
				case SETTINGS_SCREEN:
					Change(TITLE_SCREEN);
					break;
				case KEYBOARD_SETTINGS:
					Change(SETTINGS_SCREEN);
					break;
				case HIGHSCORE_SCREEN:
					Change (TITLE_SCREEN);
					break;
				case GRAPHIC_SCREEN:
					Change(SETTINGS_SCREEN);
					break;
				case GAME_SELECTION:
					Change(TITLE_SCREEN);
					break;
			}
		}
	}

	public void Render(Graphics2D g)
	{
		
		
		String[] strs = new String[items[menu].length];
		String len = "";

		
		for (int i = 0; i < items[menu].length; i++)
		{
			String s = items[menu][i];

			if (menu == SETTINGS_SCREEN)
			{
				if (s == items[SETTINGS_SCREEN][0])
					s += player_sel + " Keys";
			}
			else if (menu == KEYBOARD_SETTINGS)
			{
				if (i < 5)
				{
					if (wait_for_key && i == selected)
						s = "-";
					else
						s = Input.KEYS[Game.controls[player_sel - 1][i]];
				}
			}
			else if (menu==HIGHSCORE_SCREEN){
				g.setColor(Color.gray);
				g.drawString("Spielername", game.getWidth()*7/24, game.getHeight()*11/27);
				g.drawString("Punkte", game.getWidth()*4/6, game.getHeight()*11/27);
				g.setColor(Color.gray);
				g.drawString(erster, game.getWidth()*7/24, game.getHeight()*13/27);
				g.drawString(zweiter, game.getWidth()*7/24, game.getHeight()*14/27);
				g.drawString(dritter, game.getWidth()*7/24, game.getHeight()*15/27);
				g.drawString(ersterpunkte, game.getWidth()*4/6, game.getHeight()*13/27);
				g.drawString(zweiterpunkte, game.getWidth()*4/6, game.getHeight()*14/27);
				g.drawString(dritterpunkte, game.getWidth()*4/6, game.getHeight()*15/27);
			
			}

			if (s.length() > len.length()) len = s;
			strs[i] = s;
		}

		for (int i = 0; i < strs.length; i++)
		{
			if(menu==HIGHSCORE_SCREEN){
				String msg = strs[i];
				int y = (Game.HEIGHT *2 / 3) - strs.length * 4 + i * 24;
				g.setColor(Color.gray);
				
				if (i == selected )
				{
					g.setColor(Color.white);
					g.drawString(">", (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(len) / 2) - 15, y);
					g.drawString("<", (Game.WIDTH / 2) + (g.getFontMetrics().stringWidth(len) / 2) + 9, y);
				}
				g.drawString(msg, (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(msg) / 2), y);
			
			}
			else{
			String msg = strs[i];
			int y = (Game.HEIGHT / 2) - strs.length * 4 + i * 24;
			g.setColor(Color.gray);
			
			if (i == selected )
			{
				g.setColor(Color.white);
				g.drawString(">", (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(len) / 2) - 15, y);
				g.drawString("<", (Game.WIDTH / 2) + (g.getFontMetrics().stringWidth(len) / 2) + 9, y);
			}
			g.drawString(msg, (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(msg) / 2), y);
		}
		}

		int w = title.getWidth(), h = title.getHeight();
		g.drawImage(title,
					(Game.WIDTH / 2) - (w / 2),
					80,
					(Game.WIDTH / 2) - (w / 2) + w,
					80 + h,
					0,
					0,
					w,
					h,
					null);

		w = logo.getWidth();
		h = logo.getHeight();
		g.drawImage(logo,
					0,
					Game.HEIGHT - h,
					w,
					Game.HEIGHT,
					0,
					0,
					w,
					h,
					null);
	}
		
}


