package bomberman.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import bomberman.core.CoreGame;
import bomberman.game.Game;
import bomberman.input.Keyboard;
import bomberman.network.Client;
import bomberman.network.Connector;
import bomberman.network.Server;
import bomberman.resource.Image;

/**
 * Class represents the main menu, called up by game.
 * 
 */
public class Menu
{

	private static final int		TITLE_SCREEN		= 0;
	private static final int		GAME_SCREEN			= 1;
	private static final int		BATTLE_SCREEN		= 2;
	private static final int		SETTINGS_SCREEN		= 3;
	private static final int		KEYBOARD_SETTINGS	= 4;
	private static final int		NETWORK_SCREEN		= 5;
	private static final int		SERVER_SCREEN		= 6;
	private static final int		CLIENT_SCREEN		= 7;
	private static final int		NET_SETTINGS		= 8;
	/**
	 * The game screen, important to use the startCoreGame method in game class.
	 */
	private Game					game;
	/**
	 * The keyboard input used by the game.
	 */
	private Keyboard				input;
	/**
	 * The logo is shown in the left bottom corner of the menu.
	 */
	private BufferedImage			logo;
	/**
	 * The title "Bomberman" on the start screen.
	 */
	private BufferedImage			title;
	/**
	 * Two dimensional array containing the menu items. First index is the selected menu, the second one is the number on the current menu screen.
	 */
	private static final String[][]	items				= {
														{ "Start  Game", "Key Settings", "Exit" },
														{ "Singleplayer", "Local Battle", "Network Duel", "Tutorial", "Back" },
														{ "Player 1   ", "Player 2   ", "Player 3   ", "Player 4   " },
														{ "Player ", "Back" },
														{ "Up", "Down", "Left", "Right", "Action", "", "O.K." },
														{ "Host Game", "Find Game", "Settings", "Back" },
														{ "Start", "Back" },
														{ "I'm ready!", "Back" },
														{ "Reset Port", "Port++", "Set IP", "Back" }
														};
	/**
	 * These are pairs of integers. First integer states the menu, the second one is the selected item. If an item is listed here, the player can change it with the right/left arrows.
	 */
	private static final int[][]	left_right_choice	= {
														{ BATTLE_SCREEN, 0 },
														{ BATTLE_SCREEN, 1 },
														{ BATTLE_SCREEN, 2 },
														{ BATTLE_SCREEN, 3 },
														{ SETTINGS_SCREEN, 0 }
														};
	/**
	 * Messages shown when trying to host a network game.
	 */
	private static final String[]	SERVER_MESSAGES		= {
														"Server was not able to start on the selected port. Choose a different one and try again.",
														"Server started successfully.",
														"Client found. Waiting for answer",
														"Client ready. You can start the game now.", "Starting..."
														};
	/**
	 * Messages shown when trying to connect to a network game.
	 */
	private static final String[]	CLIENT_MESSAGES		= {
														"No server found.",
														"Waiting for an answer from the server...",
														"Server authenticated.",
														"Ready. Waiting for server to start the game.",
														"Starting..."
														};
	/**
	 * Saves which players are selected when starting a multiplayer game.
	 */
	private static boolean[]		players;
	/**
	 * The number of the currently selected item at the menu.
	 */
	private int						selected			= 0;
	/**
	 * Number of elements at the currently used menu.
	 */
	private int						count				= items[0].length;
	/**
	 * The menu in foreground, game starts with main menu.
	 */
	private int						menu				= 0;
	/**
	 * The player selected in key settings. Used in network settings, too.
	 */
	private int						player_sel			= 1;
	/**
	 * Wait for key is used when the key settings are changed. Then the next key will be used.
	 */
	private boolean					wait_for_key		= false;
	/**
	 * Used for the animation when changing key settings.
	 */
	private long					blink_timer;
	/**
	 * Current char for the animation when changing key settings.
	 */
	private char					blink_char;
	/**
	 * The array of chars for the animation when changing the key settings.
	 */
	private char					blink_chars[]		= { '/', '-', '\\', '|' };
	/**
	 * The information about the network, if any.
	 * 
	 * @see bomberman.network.Connector
	 */
	private int						net_status;
	/**
	 * Connector, will be initializes when starting network games.
	 */
	private Connector				network;

	/**
	 * Creates the menu. The logo will be created by randomness.
	 * 
	 * @param game - The game displaying the menu.
	 * @param input - The keyboard input to control the menu.
	 */
	public Menu(Game game, Keyboard input)
	{
		this.game = game;
		this.input = input;
		this.network = null;
		this.net_status = 0;

		title = Image.read("data/sprites/title.png");
		logo = Image.read("data/sprites/logo_" + ((int) (Math.random() * 3 + 1)) + ".png");
	}

	/**
	 * Changes the selected menu and resets the number of the selected item.
	 * 
	 * @param menu - The menu to go to.
	 */
	private void Change(int menu)
	{
		this.menu = menu;
		this.selected = 0;
		this.count = items[menu].length;
	}

	/**
	 * Calls up other menu methods depending on the status of the keys. Is called up by update method in game class.
	 * 
	 * @see bomberman.game.Game#Update()
	 */
	public void Update()
	{
		if (menu == BATTLE_SCREEN)
		{

			if (input.use(KeyEvent.VK_RIGHT))
				players[selected] = true;
			else if (input.use(KeyEvent.VK_LEFT))
				players[selected] = false;
		}
		else if (menu == SETTINGS_SCREEN)
		{
			if (selected == 0)
			{
				if (input.use(KeyEvent.VK_RIGHT))
					player_sel++;
				else if (input.use(KeyEvent.VK_LEFT))
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
					if (input.use(i))
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

		if (input.use(KeyEvent.VK_DOWN))
			sel++;
		else if (input.use(KeyEvent.VK_UP))
			sel--;

		do
		{
			selected += sel;

			if (selected >= count)
				selected -= count;
			else if (selected < 0)
				selected += count;

		} while (items[menu][selected] == "");

		if (this.net_status != 0)
		{
			// try{
			this.net_status = network.getStatus();
			// } catch(NullPointerException e){
			// this.net_status = 0;
			// }
		}

		if (input.use(KeyEvent.VK_ENTER))
		{
			switch (menu)
			{
				case TITLE_SCREEN:
					switch (selected)
					{
						case 0:
							Change(GAME_SCREEN);
							break;
						case 1:
							Change(SETTINGS_SCREEN);
							break;
						case 2:
							System.exit(0);
							break;
					}
					break;
				case GAME_SCREEN:
					switch (selected)
					{
						case 0:
							game.startCoreGame(CoreGame.NORMAL_GAME, null);
							Change(TITLE_SCREEN);
							break;
						case 1:
							players = new boolean[] { true, true, false, false };
							Change(BATTLE_SCREEN);
							break;
						case 2:
							Change(NETWORK_SCREEN);
							break;
						case 3:
							game.startCoreGame(CoreGame.TUTORIAL, null);
							Change(TITLE_SCREEN);
							break;

						case 4:
							Change(TITLE_SCREEN);
							break;

					}
					break;
				case BATTLE_SCREEN:
					int headcounter = 0;
					for (int counter = 0; counter < 4; counter++)
					{
						if (players[counter] == true)
						{
							headcounter++;
						}
					}
					if (headcounter >= 2)
					{
						game.startCoreGame(CoreGame.BATTLE_GAME, players);
						Change(TITLE_SCREEN);
						break;
					}
					else
					{
						Change(BATTLE_SCREEN);
						break;
					}

				case SETTINGS_SCREEN:
					switch (selected)
					{
						case 0:
							Change(KEYBOARD_SETTINGS);
							break;
						case 1:
							Change(TITLE_SCREEN);
							player_sel = 1;
							break;
					}
					break;
				case KEYBOARD_SETTINGS:
					switch (selected)
					{
						case 5:
							break;
						case 6:
							Change(SETTINGS_SCREEN);
							break;
						default:
							input.clear();
							wait_for_key = true;
							blink_timer = System.currentTimeMillis();
							blink_char = blink_chars[0];
							break;
					}
					break;
				case NETWORK_SCREEN:
					switch (selected)
					{
						case 0:
							this.network = Server.createServer(this.input);
							if (network != null)
							{
								net_status = 1;
								game.setConnector(network);
							}
							Change(SERVER_SCREEN);
							break;
						case 1:
							this.network = Client.createClient(this.input);
							if (network != null)
							{
								net_status = 1;
								game.setConnector(network);
							}
							Change(CLIENT_SCREEN);
							break;
						case 2:
							Change(NET_SETTINGS);
							break;
						case 3:
							Change(GAME_SCREEN);
							break;
					}
					break;
				case SERVER_SCREEN:
					switch (selected)
					{
						case 0:
							if (net_status == 3)
							{
								network.sayStart();
							}
							break;
						case 1:
							leaveNet();
							break;
					}
					break;
				case CLIENT_SCREEN:
					switch (selected)
					{
						case 0:
							if (net_status == 2)
							{
								network.sayReady();
							}
							break;
						case 1:
							leaveNet();
							break;
					}
					break;
				case NET_SETTINGS:
					switch (selected)
					{
						case 0:
							Connector.currentPort = Connector.DEFAULT_PORT;
							break;
						case 1:
							Connector.currentPort++;
							break;
						case 2:
							System.out.println("Not implemented yet.");
							break;
						case 3:
							Change(NETWORK_SCREEN);
							break;
					}
					break;
			}
		}
		else if (input.use(KeyEvent.VK_ESCAPE))
		{
			switch (menu)
			{
				case TITLE_SCREEN:
					System.exit(0);
					break;
				case GAME_SCREEN:
					Change(TITLE_SCREEN);
					break;
				case BATTLE_SCREEN:
					Change(GAME_SCREEN);
					break;
				case SETTINGS_SCREEN:
					Change(TITLE_SCREEN);
					player_sel = 1;
					break;
				case KEYBOARD_SETTINGS:
					Change(SETTINGS_SCREEN);
					break;
				case NETWORK_SCREEN:
					Change(GAME_SCREEN);
					break;
				case SERVER_SCREEN:
					leaveNet();
					break;
				case CLIENT_SCREEN:
					leaveNet();
					break;
				case NET_SETTINGS:
					Change(NETWORK_SCREEN);
					break;
			}
		}
	}

	/**
	 * Displays the menu on the game screen.
	 * 
	 * @param g - Graphics used to draw.
	 */
	public void Render(Graphics2D g)
	{
		String[] strs = new String[items[menu].length];
		String len = "";

		for (int i = 0; i < items[menu].length; i++)
		{
			String s = items[menu][i];

			if (menu == BATTLE_SCREEN)
			{
				s += (players[i] ? "On" : "Off");
			}
			else if (menu == SETTINGS_SCREEN)
			{
				if (s == items[SETTINGS_SCREEN][0])
					s += player_sel + " Keys";
			}
			else if (menu == KEYBOARD_SETTINGS)
			{
				if (i < 5)
				{
					if (wait_for_key && i == selected)
					{
						if (System.currentTimeMillis() - blink_timer > 100)
						{
							blink_timer = System.currentTimeMillis();
							int p = new String(blink_chars).indexOf(blink_char);
							blink_char = (p + 1 == blink_chars.length) ? blink_chars[0] : blink_chars[p + 1];
						}
						s = String.valueOf(blink_char);
					}
					else
					{
						String k = Keyboard.KEYS[Game.controls[player_sel - 1][i]];
						s = k != "" ? k : String.format("0x%02X", Game.controls[player_sel - 1][i]);
					}
				}
			}

			if (s.length() > len.length())
				len = s;
			strs[i] = s;
		}

		for (int i = 0; i < strs.length; i++)
		{
			String msg = strs[i];
			int y = (Game.HEIGHT / 2) - strs.length * 4 + i * 24;
			g.setColor(Color.gray);
			if (i == selected)
			{
				String left = ">", right = "<";

				for (int[] menu_item : left_right_choice)
				{
					if (menu == menu_item[0] && i == menu_item[1])
					{
						left = "<";
						right = ">";
						break;
					}
				}

				g.setColor(Color.white);
				g.drawString(left, (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(len) / 2) - 15, y);
				g.drawString(right, (Game.WIDTH / 2) + (g.getFontMetrics().stringWidth(len) / 2) + 9, y);
			}
			g.drawString(msg, (Game.WIDTH / 2) - (g.getFontMetrics().stringWidth(msg) / 2), y);
		}

		int w = title.getWidth(), h = title.getHeight();
		g.drawImage(title, (Game.WIDTH / 2) - (w / 2), 80, (Game.WIDTH / 2) - (w / 2) + w, 80 + h, 0, 0, w, h, null);

		switch (menu)
		{
			case SERVER_SCREEN:
				g.setColor(Color.white);
				g.drawString(SERVER_MESSAGES[net_status], 25, Game.HEIGHT - 25);
				break;
			case CLIENT_SCREEN:
				g.setColor(Color.white);
				g.drawString(CLIENT_MESSAGES[net_status], 25, Game.HEIGHT - 25);
				break;
			default:
				w = logo.getWidth();
				h = logo.getHeight();
				g.drawImage(logo, 0, Game.HEIGHT - h, w, Game.HEIGHT, 0, 0, w, h, null);
				break;
		}

		if (menu >= NETWORK_SCREEN)
		{
			String showport = "Selected port: " + Integer.toString(Connector.currentPort);

			if (menu == NETWORK_SCREEN)
				g.setColor(Color.gray);
			else
				g.setColor(Color.white);

			g.drawString(showport, Game.WIDTH - (g.getFontMetrics().stringWidth(showport)) - 25, Game.HEIGHT - 25);
		}
	}

	/**
	 * Will be used when leaving server- or client-menu. Resets all network information (except the selected port) and returns to network menu.
	 */
	private void leaveNet()
	{
		if (network != null)
		{
			network.close();
			network = null;
		}
		net_status = 0;
		game.setConnector(null);
		Change(NETWORK_SCREEN);
	}
}
