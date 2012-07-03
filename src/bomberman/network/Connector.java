package bomberman.network;

import java.io.PrintWriter;
import java.util.Scanner;

import bomberman.game.Game;
import bomberman.input.Input;
import bomberman.input.Keyboard;

public abstract class Connector implements Input, Connection
{

	public static final int			DEFAULT_PORT	= 5000;
	protected static final String	HELLO			= "HelloBomb";
	protected static final String	READY			= "Ready";
	protected static final String	START			= "Sei Epsilon kleiner Null...";
	protected static final String	END				= "goodbye";

	public static int				currentPort		= DEFAULT_PORT;

	/**
	 * Status: 0 = not active (object could not be created), 1 = active, but not connected (server only) or found server who didn't answer, 2 = connected, but client not ready, 3 = ready to start the game, 3 = playing.
	 */
	protected int					status;
	protected Keyboard				keys;
	protected Game					game;
	protected int					port;
	protected PrintWriter			out;
	protected Scanner				in;
	protected Thread				thread_in;

	protected boolean[]				input;
	protected boolean[]				output;

	protected Connector(Keyboard keys)
	{
		this.port = currentPort;
		this.keys = keys;
		this.status = 0;
		input = new boolean[6];
		output = new boolean[6];
		for (int i = 0; i < 6; i++)
		{
			input[i] = false;
			output[i] = false;
		}
	}

	public int getPort()
	{
		return this.port;
	}

	public int getStatus()
	{
		return this.status;
	}

	public void sayStart()
	{
		out.println(START);
	}

	public boolean use(int key)
	{
		return false;
	}

	public boolean get(int key)
	{
		return false;
	}

	public boolean netget(int type)
	{
		try
		{
			return input[type];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
	}

	public void clear()
	{
		for (int i = 0; i < 6; i++)
			input[i] = false;
	}
}
