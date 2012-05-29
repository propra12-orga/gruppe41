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
import bomberman.input.Input;
import bomberman.menu.Menu;

public class Game extends Canvas
{
	private static final long	serialVersionUID	= 1L;
	private BufferStrategy		strategy;

	public static final int		WIDTH				= 800;
	public static final int		HEIGHT				= 600;

	public boolean				running				= true;
	private boolean				playing				= false;
	
	private CoreGame			coregame;
	private Input				input;
	private Menu				menu;

	public static int[][]		controls			= {
													{ KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_CONTROL },
													{ KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, Input.VK_LCONTROL },
													{ KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_V },
													{ KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD6, KeyEvent.VK_ADD }
													};

	public Game()
	{
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
		window.setVisible(true);

		requestFocus();
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		input = new Input(this);
		menu = new Menu(this, input);
	}

	public void Update()
	{
		input.Update();

		if (playing)
			coregame.Update();
		else
			menu.Update();
	}

	public void Render() throws InterruptedException 
	{
		try{
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		if (playing){
			coregame.Render(g);
		g.dispose();
		strategy.show();}
		else{
			menu.Render(g);
		g.dispose();
		strategy.show();}}
		catch (Exception e) {
			e.printStackTrace();}

	}
	

	public void startCoreGame()
	{
		coregame = new CoreGame(this, input, "basic");
		playing = true;
	}

	public void stopCoreGame()
	{
		playing = false;
	}
}
