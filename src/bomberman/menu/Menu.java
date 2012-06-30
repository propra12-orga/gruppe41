package bomberman.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.core.CoreGame;
import bomberman.game.Game;
import bomberman.input.Input;

/**
 * Class represents the main menu, called up by game.
 * 
 */
public class Menu {
	private static final int TITLE_SCREEN = 0;
	private static final int GAME_SCREEN = 1;
	private static final int BATTLE_SCREEN = 2;
	private static final int SETTINGS_SCREEN = 3;
	private static final int KEYBOARD_SETTINGS = 4;
	
	/**
	 * The game screen, important to use the startCoreGame method in game class.
	 */
	private Game game;
	/**
	 * The keyboard input used by the game.
	 */
	private Input input;
	/**
	 * The logo is shown in the left bottom corner of the menu.
	 */
	private BufferedImage logo;
	/**
	 * The title "Bomberman" on the start screen.
	 */
	private BufferedImage title;
	/**
	 * Two dimensional array containing the menu items. First index is the
	 * selected menu, the second one is the number on the current menu screen.
	 */
	private static final String[][] items = {
			{ "Start  Game", "Settings", "Exit" },
			{ "Singleplayer", "Battle Game", "Tutorial" , "" , "Back" },
			{ "Player 1   ", "Player 2   ", "Player 3   ", "Player 4   " },
			{ "Player ", "Back" },
			{ "Up", "Down", "Left", "Right", "Action", "", "O.K." } };
	/**
	 * I dunno what these pairs of integers do... TODO
	 */
	private static final int[][] left_right_choice = { { BATTLE_SCREEN, 0 },
			{ BATTLE_SCREEN, 1 }, { BATTLE_SCREEN, 2 }, { BATTLE_SCREEN, 3 },
			{ SETTINGS_SCREEN, 0 } };
	/**
	 * Saves which players are selected for starting a multiplayer game.
	 */
	private static boolean[] players;
	/**
	 * 
	 */
	private int selected = 0;
	/**
	 * Number of elements at the currently used menu.
	 */
	private int count = items[0].length;
	/**
	 * The menu in foreground, game starts which main menu.
	 */
	private int menu = 0;
	/**
	 * The player selected in key settings.
	 */
	private int player_sel = 1;
	/**
	 * Wait for key is used when the key settings are changed. Then the next key
	 * will be used.
	 */
	private boolean wait_for_key = false;
	/**
	 * Used for the animation when changing key settings.
	 */
	private long blink_timer;
	/**
	 * Current char for the animation when changing key settings.
	 */
	private char blink_char;
	/**
	 * The array of chars for the animation when changing the key settings.
	 */
	private char blink_chars[] = { '/', '-', '\\', '|' };

	/**
	 * Creates the menu. The logo will be created by randomness.
	 * 
	 * @param game
	 *            - The game displaying the menu.
	 * @param input
	 *            - The keyboard input to control the menu.
	 */
	public Menu(Game game, Input input) {
		this.game = game;
		this.input = input;

		try {
			title = ImageIO.read(new File("data/sprites/title.png"));
			logo = ImageIO.read(new File("data/sprites/logo_"
					+ ((int) (Math.random() * 3 + 1)) + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Changes the selected menu.
	 * 
	 * @param menu
	 *            - The menu to go to.
	 */
	private void Change(int menu) {
		this.menu = menu;
		this.selected = 0;
		this.count = items[menu].length;
	}

	/**
	 * Calls up other menu methods depending on the status of the keys. Is
	 * called up by update method in game class.
	 * 
	 * @see bomberman.game.Game#Update()
	 */
	public void Update() {
		if (menu == BATTLE_SCREEN) {

			if (input.keys[KeyEvent.VK_RIGHT].clicked)
				players[selected] = true;
			else if (input.keys[KeyEvent.VK_LEFT].clicked)
				players[selected] = false;
		} else if (menu == SETTINGS_SCREEN) {
			if (selected == 0) {
				if (input.keys[KeyEvent.VK_RIGHT].clicked)
					player_sel++;
				else if (input.keys[KeyEvent.VK_LEFT].clicked)
					player_sel--;

				if (player_sel > 4)
					player_sel -= 1;
				else if (player_sel < 1)
					player_sel += 1;
			}
		} else if (menu == KEYBOARD_SETTINGS) {
			if (wait_for_key) {
				int key = -1;

				for (int i = 0; i < 256; i++) {
					if (input.keys[i].clicked)
						key = i;
				}

				if (key > -1) {
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

		do {
			selected += sel;

			if (selected >= count)
				selected -= count;
			else if (selected < 0)
				selected += count;

		} while (items[menu][selected] == "");

		if (input.keys[KeyEvent.VK_ENTER].clicked) {
			switch (menu) {
			case TITLE_SCREEN:
				switch (selected) {
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
				switch (selected) {
				case 0:
					game.startCoreGame(CoreGame.NORMAL_GAME, null);
					Change(TITLE_SCREEN);
					break;
				case 1:
					players = new boolean[] { true, true, false, false };
					Change(BATTLE_SCREEN);
					break;
				case 2:
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
				for (int counter = 0; counter < 4; counter++) {
					if (players[counter] == true) {
						headcounter++;
					}
				}
				if (headcounter >= 2) {
					game.startCoreGame(CoreGame.BATTLE_GAME, players);
					Change(TITLE_SCREEN);
					break;
				} else {
					
					break;
				}

			case SETTINGS_SCREEN:
				switch (selected) {
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
				switch (selected) {
				case 5:
					break;
				case 6:
					Change(SETTINGS_SCREEN);
					break;
				default:
					wait_for_key = true;
					blink_timer = System.currentTimeMillis();
					blink_char = blink_chars[0];
					break;
				}
				break;
			}
		} else if (input.keys[KeyEvent.VK_ESCAPE].clicked) {
			switch (menu) {
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
			}
		}
	}

	/**
	 * Displays the menu on the game screen.
	 * 
	 * @param g
	 *            - Graphics used to draw.
	 */
	public void Render(Graphics2D g) {
		String[] strs = new String[items[menu].length];
		String len = "";

		for (int i = 0; i < items[menu].length; i++) {
			String s = items[menu][i];

			if (menu == BATTLE_SCREEN) {
				s += (players[i] ? "On" : "Off");
			} else if (menu == SETTINGS_SCREEN) {
				if (s == items[SETTINGS_SCREEN][0])
					s += player_sel + " Keys";
			} else if (menu == KEYBOARD_SETTINGS) {
				if (i < 5) {
					if (wait_for_key && i == selected) {
						if (System.currentTimeMillis() - blink_timer > 100) {
							blink_timer = System.currentTimeMillis();
							int p = new String(blink_chars).indexOf(blink_char);
							blink_char = (p + 1 == blink_chars.length) ? blink_chars[0]
									: blink_chars[p + 1];
						}
						s = String.valueOf(blink_char);
					} else {
						String k = Input.KEYS[Game.controls[player_sel - 1][i]];
						s = k != "" ? k : String.format("0x%02X",
								Game.controls[player_sel - 1][i]);
					}
				}
			}

			if (s.length() > len.length())
				len = s;
			strs[i] = s;
		}

		for (int i = 0; i < strs.length; i++) {
			String msg = strs[i];
			int y = (Game.HEIGHT / 2) - strs.length * 4 + i * 24;
			g.setColor(Color.gray);
			if (i == selected) {
				String left = ">", right = "<";

				for (int[] menu_item : left_right_choice) {
					if (menu == menu_item[0] && i == menu_item[1]) {
						left = "<";
						right = ">";
						break;
					}
				}

				g.setColor(Color.white);
				g.drawString(left, (Game.WIDTH / 2)
						- (g.getFontMetrics().stringWidth(len) / 2) - 15, y);
				g.drawString(right, (Game.WIDTH / 2)
						+ (g.getFontMetrics().stringWidth(len) / 2) + 9, y);
			}
			g.drawString(msg, (Game.WIDTH / 2)
					- (g.getFontMetrics().stringWidth(msg) / 2), y);
		}

		int w = title.getWidth(), h = title.getHeight();
		g.drawImage(title, (Game.WIDTH / 2) - (w / 2), 80, (Game.WIDTH / 2)
				- (w / 2) + w, 80 + h, 0, 0, w, h, null);

		w = logo.getWidth();
		h = logo.getHeight();
		g.drawImage(logo, 0, Game.HEIGHT - h, w, Game.HEIGHT, 0, 0, w, h, null);
	}
}
