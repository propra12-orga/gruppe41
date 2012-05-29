package bomberman.input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener
{
	public class Key
	{
		public int	presses, absorbs;
		public boolean	down, clicked;

		public void toggle(boolean pressed)
		{
			if (pressed != down) down = pressed;
			if (pressed) presses++;
		}

		public void Update()
		{
			if (absorbs < presses)
			{
				absorbs++;
				clicked = true;
			}
			else
				clicked = false;
		}
	}

	public Key[]	keys	= new Key[256];

	public Input(Component x)
	{
		x.addKeyListener(this);

		for (int i = 0; i < 256; i++)
		{
			keys[i] = new Key();
		}
	}

	public void releaseAll()
	{
		for (Key k : keys)
			k.down = false;
	}

	public void Update()
	{
		for (Key k : keys)
			k.Update();
	}

	private void toggle(KeyEvent ke, boolean pressed)
	{
		int k = ke.getKeyCode();

		if (k < 256)
		{
			if (ke.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT && (k == KeyEvent.VK_CONTROL || k == KeyEvent.VK_SHIFT || k == KeyEvent.VK_ALT)) k -= (k == KeyEvent.VK_ALT) ? 5 : 2;
			keys[k].toggle(pressed);
		}
	}

	public void keyPressed(KeyEvent ke)
	{
		toggle(ke, true);
	}

	public void keyReleased(KeyEvent ke)
	{
		toggle(ke, false);
	}

	public void keyTyped(KeyEvent ke)
	{
	}

	public static final int			VK_LCONTROL	= KeyEvent.VK_CONTROL - 2;
	public static final int			VK_LSHIFT	= KeyEvent.VK_SHIFT - 2;
	public static final int			VK_LALT		= KeyEvent.VK_ALT - 4;

	public static final String[]	KEYS		= {
												"", "LMOUSE", "RMOUSE", "", "MMOUSE", "XMOUSE1",
												"XMOUSE2", "", "BACK", "TAB", "ENTER", "",
												"", "LALT", "LSHIFT", "LCTRL", "RSHIFT", "RCTRL",
												"ALT", "PAUSE", "CAPSL", "", "", "",
												"", "", "", "ESC", "", "",
												"", "", "SPACE", "PGUP", "PGDN", "END",
												"HOME", "LEFT", "UP", "RIGHT", "DOWN", "",
												"", "", "", "INS", "DEL", "",
												"0", "1", "2", "3", "4", "5",
												"6", "7", "8", "9", "", "",
												"", "", "", "", "", "A",
												"B", "C", "D", "E", "F", "G",
												"H", "I", "J", "K", "L", "M",
												"N", "O", "P", "Q", "R", "S",
												"T", "U", "V", "W", "X", "Y",
												"Z", "", "", "", "", "",
												"NUM0", "NUM1", "NUM2", "NUM3", "NUM4", "NUM5",
												"NUM6", "NUM7", "NUM8", "NUM9", "*", "+",
												",", "-", "?", "/", "F1", "F2",
												"F3", "F4", "F5", "F6", "F7", "F8",
												"F9", "F10", "F11", "F12", "", "",
												"", "", "", "", "", "",
												"", "", "", "", "", "",
												"", "", "", "", "", "",
												"NUMLOCK", "", "", "", "", "",
												"", "", "", "", "", "",
												"", "", "", "", "LSHIFT", "RSHIFT",
												"LCTRL", "RCTRL", "LALT", "RALT"
												};
}
