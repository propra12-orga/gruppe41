package bomberman.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bomberman.core.CoreGame;
import bomberman.input.Keyboard;
import bomberman.menu.Menu;
import bomberman.network.Connector;

/**
 * This class and its methods are responsible for the whole game. An instance of
 * this class is created in main method. The constructor and the methods
 * update() and render() are used there, too. The Game class extends Canvas,
 * showing all graphics.
 * 
 * @see bomberman.Bomberman#main
 * 
 */
public class Game extends Canvas {
	/**
	 * Eclipse dislikes not setting this static parameter when using a class
	 * extending any awt component. This long integer is never used by the game.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A buffer strategy is the way how images are painted and saved while
	 * rendering. This game uses double buffering.
	 */
	private BufferStrategy strategy;
	/**
	 * Graphics, used to render.
	 */
	private Graphics2D graphics;
	/**
	 * The width of the game screen.
	 */
	public static final int WIDTH = 800;
	/**
	 * The heigth of the game screen.
	 */
	public static final int HEIGHT = 600;
	/*
	 * Defines if the game is running. // TODO - what precisly?
	 */
	public boolean running = true;
	/**
	 * playing = true: The core game is active. playing = false: The menu is
	 * active.
	 */
	private boolean playing = false;
	/**
	 * Instance of the core game.
	 * 
	 * @see bomberman.coregame.CoreGame
	 */
	private CoreGame coregame;
	/**
	 * This is an instance of the keyboard class which defines the keyboard
	 * input.
	 */
	private Keyboard input;
	/**
	 * The connector used for network games.
	 */
	private Connector connector;
	/**
	 * Reference to the menu.
	 */
	private Menu menu;
	/**
	 * This two dimensional array saves the player controls. First index states
	 * the player, second one the type of order: (0) move up, (1) move down, (2)
	 * move left, (3) move right, (4) bomb.
	 * 
	 * @see bomberman.input.Input
	 */
	public static int[][] controls = {
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
					KeyEvent.VK_RIGHT, KeyEvent.VK_CONTROL },
			{ KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D,
					Keyboard.VK_LCONTROL },
			{ KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L,
					KeyEvent.VK_V },
			{ KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD4,
					KeyEvent.VK_NUMPAD6, KeyEvent.VK_ADD } };
	/**
	 * Two dimensional array, first index is the player, the second is x or y
	 * position.
	 */
	public static int[][] spawns = { { 0, 0 }, { 16, 0 }, { 0, 10 }, { 16, 10 } };

	/**
	 * This constructor is used exactly once. There aren't any parameters. The
	 * constructor creates a new JFrame (parameter, cannot be addressed from any
	 * class), and a panel. The Game (being a Canvas subclass) is added to the
	 * panel. The keyboard input is included here, too.
	 * 
	 * @see bomberman.Bomberman#main
	 * @see bomberman.menu.Menu
	 * @see bomberman.input.Input
	 */
	public Game() {
		JFrame window = new JFrame("Bomberman");
		JPanel panel = (JPanel) window.getContentPane();

		panel.setPreferredSize(new Dimension(WIDTH - 12, HEIGHT - 12));
		panel.setLayout(new BorderLayout());

		setBounds(0, 0, WIDTH, HEIGHT);
		setIgnoreRepaint(true);

		panel.add(this);
		window.pack();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);

		requestFocus();
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		this.graphics = (Graphics2D) strategy.getDrawGraphics();

		input = new Keyboard(this);
		connector = null;
		this.connector = null;
		menu = new Menu(this, input);
		this.Render();
		window.setVisible(true);
	}

	public Menu getMenu() {
		return this.menu;
	}

	public boolean isPlaying() {
		return this.playing;
	}

	
	/**
	 * The update method is (lust like the render method) passed down to other
	 * objects. In this case, the keyboard is also updated.
	 * 
	 * @see bomberman.input.Input#update()
	 * @see bomberman.core.CoreGame#Update()
	 * @see bomberman.menu.Menu#Update()
	 * @see bomberman.game.Game#playing
	 */
	public void Update() {
		if (playing)
			coregame.Update();
		else
			menu.Update();
	}

	/**
	 * Renders the currently selected item: coregame or screen, depending on the
	 * boolean "playing".
	 * 
	 * @see bomberman.core.CoreGame#Render(Graphics2D)
	 * @see bomberman.menu.Menu#Render(Graphics2D)
	 * @see bomberman.game.Game#playing
	 */
	public void Render() {
		graphics = (Graphics2D) strategy.getDrawGraphics();
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);

		if (playing)
			coregame.Render(graphics);
		else
			menu.Render(graphics);

		graphics.dispose();
		strategy.show();
	}

	/**
	 * This method starts the core game by setting the "playing" boolean true
	 * and ceating a new CoreGame instance.
	 * 
	 * @param gametype
	 *            This <code>int</code> defines the game type, 0 = single player
	 *            game, 1 = multiplayer game. Another game type will come soon.
	 * @param players
	 *            This <code>boolean</code> array decides which players are
	 *            used. Must contain exactly four elements.
	 */
	public void startCoreGame(int gametype, boolean[] players) {
		if (gametype==2){
			coregame = new CoreGame(this, input, connector, "tutorium", gametype, players);
		}
		else {coregame = new CoreGame(this, input, connector, "basic", gametype, players);};
		playing = true;
	}

	/**
	 * Ends the core game by setting the boolean "playing". The methods update()
	 * and render() will now update and show the menu. The game data is not
	 * cleared, will be done when starting a new game.
	 */
	public void stopCoreGame() {
		playing = false;
	}

	/**
	 * Sets a new connector, used by the menu when starting network games.
	 * 
	 * @param c
	 *            - The new connector.
	 */
	public void setConnector(Connector c) {
		this.connector = c;
	}
}
