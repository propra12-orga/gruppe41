package bomberman.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import bomberman.core.BattleGame;
import bomberman.core.ClientGame;
import bomberman.core.CoreGame;
import bomberman.core.ServerGame;
import bomberman.core.SingleGame;
import bomberman.core.TutorialGame;
import bomberman.input.Keyboard;
import bomberman.menu.Menu;
import bomberman.network.Client;
import bomberman.network.Server;

/**
 * This class and its methods are responsible for the whole game. An instance of this class is created in main method. The constructor and the methods update() and render() are used there, too. The Game class extends Canvas, showing all graphics.
 * 
 * @see bomberman.Bomberman#main
 * 
 */
public class Game extends Canvas implements Runnable
{
	/**
	 * Eclipse dislikes not setting this static parameter when using a class extending any awt component. This long integer is never used by the game.
	 */
	private static final long	serialVersionUID	= 1L;
	/**
	 * 25 milliseconds per frame, that's 40 frames per second. Using this constant slows the game and prevents it from being unequal fast on different computers.
	 */
	private static final int	MS_PER_FRAME		= 25;												// (1000 / 60)
	/**
	 * Frames per second will be printed to console if this boolean is true.
	 */
	private static boolean		show_fps			= false;
	/**
	 * A buffer strategy is the way how images are painted and saved while rendering. This game uses double buffering.
	 */
	private BufferStrategy		strategy;
	/**
	 * The width of the game screen.
	 */
	public static final int		WIDTH				= 800;
	/**
	 * The height of the game screen.
	 */
	public static final int		HEIGHT				= 600;
	/**
	 * Used to exit the main-loop
	 */
	private boolean				running				= true;
	/**
	 * playing = true: The core game is active. playing = false: The menu is active.
	 */
	private boolean				playing				= false;
	/**
	 * Instance of the core game.
	 * 
	 * @see bomberman.coregame.CoreGame
	 */
	private CoreGame			coregame;
	/**
	 * This is an instance of the keyboard class which defines the keyboard input.
	 */
	private Keyboard			input;
	/**
	 * Reference to the menu.
	 */
	private Menu				menu;
	/**
	 * This two dimensional array saves the player controls. First index states the player, second one the type of order: (0) move up, (1) move down, (2) move left, (3) move right, (4) bomb.
	 * 
	 * @see bomberman.input.Input
	 */
	public static int[][]		controls			= {
													{ KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_CONTROL },
													{ KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, Keyboard.VK_LCONTROL },
													{ KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_V },
													{ KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD6, KeyEvent.VK_ADD }
													};
	/**
	 * Two dimensional array, first index is the player, the second is x or y position.
	 */
	public static int[][]		spawns				= { { 0, 0 }, { 16, 0 }, { 0, 10 }, { 16, 10 } };

	/**
	 * This pseudo-constructor is executed right before entering the main-loop. It creates a needed buffer strategy, as well as an input instance and a menu instance. It's not a real constructor as its execution has to be delayed since createBufferStrategy only works when
	 * associated to a visible window or applet.
	 * 
	 * @see bomberman.menu.Menu
	 * @see bomberman.input.Input
	 */
	public void Init()
	{
		setBounds(0, 0, Game.WIDTH, Game.HEIGHT);
		setIgnoreRepaint(true);

		requestFocus();
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		input = new Keyboard(this);
		menu = new Menu(this, input);
	}

	public void start()
	{
		running = true;
		new Thread(this).start();
	}

	public void stop()
	{
		running = false;
	}

	public Menu getMenu()
	{
		return this.menu;
	}

	public boolean isPlaying()
	{
		return this.playing;
	}

	/**
	 * The update method is (lust like the render method) passed down to other objects. In this case, the keyboard is also updated.
	 * 
	 * @see bomberman.input.Input#update()
	 * @see bomberman.core.CoreGame#Update()
	 * @see bomberman.menu.Menu#Update()
	 * @see bomberman.game.Game#playing
	 */
	public void Update()
	{
		if (playing)
			coregame.Update();
		else
			menu.Update();
	}

	/**
	 * Renders the currently selected item: coregame or menu, depending on the boolean "playing".
	 * 
	 * @see bomberman.core.CoreGame#Render(Graphics2D)
	 * @see bomberman.menu.Menu#Render(Graphics2D)
	 * @see bomberman.game.Game#playing
	 */
	public void Render()
	{
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		if (playing)
			coregame.Render(g);
		else
			menu.Render(g);

		g.dispose();
		strategy.show();
	}

	public void startNormalGame()
	{
		coregame = new SingleGame(this, input);
		playing = true;
	}

	public void startBattleGame(boolean[] players)
	{
		coregame = new BattleGame(this, input, players);
		playing = true;
	}

	public void startTutorial()
	{
		coregame = new TutorialGame(this, input);
		playing = true;
	}

	public void startServerGame(Server con)
	{
		coregame = new ServerGame(this, input, con);
		playing = true;
	}

	public void startClientGame(Client con)
	{
		coregame = new ClientGame(this, input, con);
		playing = true;
	}

	/**
	 * Ends the core game by setting the boolean "playing". The methods update() and render() will now update and show the menu. The game data is not cleared, will be done when starting a new game.
	 */
	public void stopCoreGame()
	{
		playing = false;
	}

	@Override
	public void run()
	{
		long startTime = System.currentTimeMillis();
		long gameTime = 0;
		long realTime;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();

		Init();

		while (running)
		{
			Update();
			Render();

			gameTime += MS_PER_FRAME;
			frames++;
			ticks++;

			realTime = System.currentTimeMillis() - startTime;

			if (gameTime < realTime)
			{
				for (int i = 0; i < (realTime - gameTime) / MS_PER_FRAME; i++)
				{
					Update();
					gameTime += MS_PER_FRAME;
					ticks++;
				}
			}
			else if (realTime < gameTime)
			{
				int sleep = (int) (gameTime - realTime);
				try
				{
					Thread.sleep(sleep);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}

			if (show_fps && (System.currentTimeMillis() - lastTimer) > 1000)
			{
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
				lastTimer = System.currentTimeMillis();
			}
			try
			{
				Thread.sleep(1);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
