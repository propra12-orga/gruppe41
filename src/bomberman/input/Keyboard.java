package bomberman.input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class implements a key listener and the methods needed to control menu and players. Will be used by game class. Completely rewritten for network use (compatibility with Interface Input). This class does not need the update function any longer.
 * 
 * @see bomberman.game.Game
 * @see bomberman.input.Input
 */
public class Keyboard implements KeyListener, Input
{

	public static final int			VK_LSHIFT	= KeyEvent.VK_SHIFT - 3;
	public static final int			VK_LCONTROL	= KeyEvent.VK_CONTROL - 3;
	public static final int			VK_LALT		= KeyEvent.VK_ALT - 3;
	/**
	 * This String array contains the names of the keys sorted by their key code. Will be used in menu/settings to show the player controls.
	 */
	public static final String[]	KEYS		= {
												/* 0x00 */"",
												/* 0x01 */"",
												/* 0x02 */"",
												/* 0x03 */"",
												/* 0x04 */"",
												/* 0x05 */"",
												/* 0x06 */"",
												/* 0x07 */"",
												/* 0x08 */"Back",
												/* 0x09 */"",
												/* 0x0A */"Enter",
												/* 0x0B */"",
												/* 0x0C */"",
												/* 0x0D */"L-Shift",
												/* 0x0E */"L-Ctrl",
												/* 0x0F */"L-Alt",
												/* 0x10 */"R-Shift",
												/* 0x11 */"R-Ctrl",
												/* 0x12 */"R-Alt",
												/* 0x13 */"Pause",
												/* 0x14 */"",
												/* 0x15 */"",
												/* 0x16 */"",
												/* 0x17 */"",
												/* 0x18 */"",
												/* 0x19 */"",
												/* 0x1A */"",
												/* 0x1B */"Esc",
												/* 0x1C */"",
												/* 0x1D */"",
												/* 0x1E */"",
												/* 0x1F */"",
												/* 0x20 */"Space",
												/* 0x21 */"",
												/* 0x22 */"",
												/* 0x23 */"",
												/* 0x24 */"",
												/* 0x25 */"Left",
												/* 0x26 */"Up",
												/* 0x27 */"Right",
												/* 0x28 */"Down",
												/* 0x29 */"",
												/* 0x2A */"",
												/* 0x2B */"",
												/* 0x2C */"",
												/* 0x2D */"",
												/* 0x2E */"",
												/* 0x2F */"",
												/* 0x30 */"0",
												/* 0x31 */"1",
												/* 0x32 */"2",
												/* 0x33 */"3",
												/* 0x34 */"4",
												/* 0x35 */"5",
												/* 0x36 */"6",
												/* 0x37 */"7",
												/* 0x38 */"8",
												/* 0x39 */"9",
												/* 0x3A */"",
												/* 0x3B */"",
												/* 0x3C */"",
												/* 0x3D */"",
												/* 0x3E */"",
												/* 0x3F */"",
												/* 0x40 */"",
												/* 0x41 */"A",
												/* 0x42 */"B",
												/* 0x43 */"C",
												/* 0x44 */"D",
												/* 0x45 */"E",
												/* 0x46 */"F",
												/* 0x47 */"G",
												/* 0x48 */"H",
												/* 0x49 */"I",
												/* 0x4A */"J",
												/* 0x4B */"K",
												/* 0x4C */"L",
												/* 0x4D */"M",
												/* 0x4E */"N",
												/* 0x4F */"O",
												/* 0x50 */"P",
												/* 0x51 */"Q",
												/* 0x52 */"R",
												/* 0x53 */"S",
												/* 0x54 */"T",
												/* 0x55 */"U",
												/* 0x56 */"V",
												/* 0x57 */"W",
												/* 0x58 */"X",
												/* 0x59 */"Y",
												/* 0x5A */"Z",
												/* 0x5B */"",
												/* 0x5C */"",
												/* 0x5D */"",
												/* 0x5E */"",
												/* 0x5F */"",
												/* 0x60 */"Num0",
												/* 0x61 */"Num1",
												/* 0x62 */"Num2",
												/* 0x63 */"Num3",
												/* 0x64 */"Num4",
												/* 0x65 */"Num5",
												/* 0x66 */"Num6",
												/* 0x67 */"Num7",
												/* 0x68 */"Num8",
												/* 0x69 */"Num9",
												/* 0x6A */"*",
												/* 0x6B */"+",
												/* 0x6C */"",
												/* 0x6D */"-",
												/* 0x6E */"",
												/* 0x6F */"/",
												/* 0x70 */"F1",
												/* 0x71 */"F2",
												/* 0x72 */"F3",
												/* 0x73 */"F4",
												/* 0x74 */"F5",
												/* 0x75 */"F6",
												/* 0x76 */"F7",
												/* 0x77 */"F8",
												/* 0x78 */"F9",
												/* 0x79 */"F10",
												/* 0x7A */"F11",
												/* 0x7B */"F12",
												/* 0x7C */"",
												/* 0x7D */"",
												/* 0x7E */"",
												/* 0x7F */"",
												/* 0x80 */"",
												/* 0x81 */"",
												/* 0x82 */"",
												/* 0x83 */"",
												/* 0x84 */"",
												/* 0x85 */"",
												/* 0x86 */"",
												/* 0x87 */"",
												/* 0x88 */"",
												/* 0x89 */"",
												/* 0x8A */"",
												/* 0x8B */"",
												/* 0x8C */"",
												/* 0x8D */"",
												/* 0x8E */"",
												/* 0x8F */"",
												/* 0x90 */"Numlock",
												/* 0x91 */"Scrolllock",
												/* 0x92 */"",
												/* 0x93 */"",
												/* 0x94 */"",
												/* 0x95 */"",
												/* 0x96 */"",
												/* 0x97 */"",
												/* 0x98 */"",
												/* 0x99 */"",
												/* 0x9A */"",
												/* 0x9B */"",
												/* 0x9C */"",
												/* 0x9D */"",
												/* 0x9E */"",
												/* 0x9F */"",
												/* 0xA0 */"",
												/* 0xA1 */"",
												/* 0xA2 */"",
												/* 0xA3 */"",
												/* 0xA4 */"",
												/* 0xA5 */"",
												/* 0xA6 */"",
												/* 0xA7 */"",
												/* 0xA8 */"",
												/* 0xA9 */"",
												/* 0xAA */"",
												/* 0xAB */"",
												/* 0xAC */"",
												/* 0xAD */"",
												/* 0xAE */"",
												/* 0xAF */"",
												/* 0xB0 */"",
												/* 0xB1 */"",
												/* 0xB2 */"",
												/* 0xB3 */"",
												/* 0xB4 */"",
												/* 0xB5 */"",
												/* 0xB6 */"",
												/* 0xB7 */"",
												/* 0xB8 */"",
												/* 0xB9 */"",
												/* 0xBA */"",
												/* 0xBB */"",
												/* 0xBC */"",
												/* 0xBD */"",
												/* 0xBE */"",
												/* 0xBF */"",
												/* 0xC0 */"",
												/* 0xC1 */"",
												/* 0xC2 */"",
												/* 0xC3 */"",
												/* 0xC4 */"",
												/* 0xC5 */"",
												/* 0xC6 */"",
												/* 0xC7 */"",
												/* 0xC8 */"",
												/* 0xC9 */"",
												/* 0xCA */"",
												/* 0xCB */"",
												/* 0xCC */"",
												/* 0xCD */"",
												/* 0xCE */"",
												/* 0xCF */"",
												/* 0xD0 */"",
												/* 0xD1 */"",
												/* 0xD2 */"",
												/* 0xD3 */"",
												/* 0xD4 */"",
												/* 0xD5 */"",
												/* 0xD6 */"",
												/* 0xD7 */"",
												/* 0xD8 */"",
												/* 0xD9 */"",
												/* 0xDA */"",
												/* 0xDB */"",
												/* 0xDC */"",
												/* 0xDD */"",
												/* 0xDE */"",
												/* 0xDF */"",
												/* 0xE0 */"",
												/* 0xE1 */"",
												/* 0xE2 */"",
												/* 0xE3 */"",
												/* 0xE4 */"",
												/* 0xE5 */"",
												/* 0xE6 */"",
												/* 0xE7 */"",
												/* 0xE8 */"",
												/* 0xE9 */"",
												/* 0xEA */"",
												/* 0xEB */"",
												/* 0xEC */"",
												/* 0xED */"",
												/* 0xEE */"",
												/* 0xEF */"",
												/* 0xF0 */"",
												/* 0xF1 */"",
												/* 0xF2 */"",
												/* 0xF3 */"",
												/* 0xF4 */"",
												/* 0xF5 */"",
												/* 0xF6 */"",
												/* 0xF7 */"",
												/* 0xF8 */"",
												/* 0xF9 */"",
												/* 0xFA */"",
												/* 0xFB */"",
												/* 0xFC */"",
												/* 0xFD */"",
												/* 0xFE */"",
												/* 0xFF */""
												};

	/**
	 * A boolean for every key (0-255). True as long as the key is pressed. Will be used for moving.
	 */
	private boolean[]				down		= new boolean[256];
	/**
	 * A boolean for every key. Will be set true if pressed, will be set false when the program uses the information. Will stay true until the program asks for the information, always clear (resetAll) before startimg a game!
	 */
	private boolean[]				click		= new boolean[256];

	/**
	 * Adds the keyboard input to the component.
	 * 
	 * @param c - The <code>awt.Component</code> using the key listener.
	 */
	public Keyboard(Component c)
	{
		c.addKeyListener(this);

		for (int i = 0; i < 256; i++)
		{
			down[i] = false;
			click[i] = false;
		}
	}

	/**
	 * Get method for keys. Cannot throw ArrayIndexOutOfBoundsExceptions.
	 * 
	 * @param k - The key.
	 * @return True if down.
	 */
	public boolean get(int k)
	{
		if (-1 < k && k < 256)
		{
			return down[k];
		}
		else
		{
			return false;
		}
	}

	/**
	 * Get method for keys. Cannot throw ArrayIndexOutOfBoundsExceptions. 'Pressed' is changed to false.
	 * 
	 * @param k - The key.
	 * @return True if pressed.
	 */
	public boolean use(int k)
	{
		if (-1 < k && k < 256)
		{
			boolean ret = click[k];
			click[k] = false;
			return ret;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Used for network games. Returns the click information, but does not consume it.
	 * @param k
	 * @return
	 */
	public boolean getClick(int k)
		{
			if (-1 < k && k < 256)
			{
				return click[k];
			}
			else
			{
				return false;
			}
	}
	/**
	 * Method has to be implemented because of the interface input. This method is only used by players controlled via network. Always returns false.
	 */
	public boolean netget(int type)
	{
		return false;
	}

	/**
	 * All key information false.
	 */
	public void clear()
	{
		for (int i = 0; i < 256; i++)
		{
			this.down[i] = false;
			this.click[i] = false;
		}
	}

	/**
	 * KeyListener method. 'Pressed' and 'down' will change to true.
	 */
	public void keyPressed(KeyEvent ke)
	{
		int k = ke.getKeyCode();

		if (-1 < k && k < 256)
		{
			if (ke.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT && (k == KeyEvent.VK_CONTROL || k == KeyEvent.VK_SHIFT || k == KeyEvent.VK_ALT)) k -= 3;
			if (k == Keyboard.VK_LALT || k == KeyEvent.VK_F10) ke.consume();
			down[k] = true;
			click[k] = true;
		}
	}

	/**
	 * When a key is released, 'down' will change to false.
	 */
	public void keyReleased(KeyEvent ke)
	{
		int k = ke.getKeyCode();
		if (-1 < k && k < 256)
		{
			if (ke.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT && (k == KeyEvent.VK_CONTROL || k == KeyEvent.VK_SHIFT || k == KeyEvent.VK_ALT)) k -= 3;
			if (k == Keyboard.VK_LALT || k == KeyEvent.VK_F10) ke.consume();
			down[k] = false;
		}
	}

	/**
	 * KeyListener method. Does exactly nothing, 'pressed' is activated as soon as the key is pressed, not after pressing and releasing it.
	 */
	public void keyTyped(KeyEvent k)
	{
	}
}
