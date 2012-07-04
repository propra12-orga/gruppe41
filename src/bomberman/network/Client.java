package bomberman.network;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import bomberman.Bomberman;
import bomberman.game.Game;
import bomberman.input.Keyboard;

public class Client extends Connector
{
	private Socket	connection;

	public static Client createClient(Keyboard keys)
	{
		Client ret;
		ret = new Client("", keys);
		if (ret.status != 0)
		{
			return ret;
		}
		else
		{
			return null;
		}
	}

	public Client(String host, Keyboard keys)
	{
		super(keys);
		for (int i = 0; i < 5; i++)
		{
			output[i] = false;
			input[i] = false;
		}
		try
		{
			connection = new Socket("localhost", port);
			out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()), true);
			in = new Scanner(new InputStreamReader(connection.getInputStream()));
			status++;
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		if (status != 0)
		{
			thread_in = new Thread()
			{
				public void run()
				{
					String current;
					sayHello();
					while (true)
					{
						try
						{
							current = in.nextLine();
							switch (status)
							{
								case 1:
									if (current.equals(HELLO))
									{
										status++;
									}
									else
									{
										disconnect();
									}
									break;
								case 3:
									if (current.equals(START))
									{
										sayStart();
										startGame();
									}
									else if (current.equals(END))
										disconnect();
									break;
								case 4:
									if (current.equals(START))
										startGame();
									else
									{
										switch (current.charAt(0))
										{
											case 'W':
												input[0] = true;
												break;
											case 'w':
												input[0] = false;
												break;
											case 'S':
												input[1] = true;
												break;
											case 's':
												input[1] = false;
												break;
											case 'A':
												input[2] = true;
												break;
											case 'a':
												input[2] = false;
												break;
											case 'D':
												input[3] = true;
												break;
											case 'd':
												input[3] = false;
												break;
											case 'B':
												input[4] = true;
												break;
											case 'b':
												input[4] = false;
												break;
										}
									}
									break;
								default:
									break;
							}
						}
						catch (NoSuchElementException e)
						{
							disconnect();
							System.out.println("Client closed.");
							break;
						}
					}
				}
			};
			thread_in.start();
		}
	}

	public void update()
	{
		if (output[0] && !keys.get(Game.controls[1][0]))
		{
			out.println('w');
			output[0] = false;
		}
		if (!output[0] && keys.get(Game.controls[1][0]))
		{
			out.println('W');
			output[0] = true;
		}
		if (output[1] && !keys.get(Game.controls[1][1]))
		{
			out.println('s');
			output[1] = false;
		}
		if (!output[1] && keys.get(Game.controls[1][1]))
		{
			out.println('S');
			output[1] = true;
		}
		if (output[2] && !keys.get(Game.controls[1][2]))
		{
			out.println('a');
			output[2] = false;
		}
		if (!output[2] && keys.get(Game.controls[1][2]))
		{
			out.println('A');
			output[2] = true;
		}
		if (output[3] && !keys.get(Game.controls[1][3]))
		{
			out.println('d');
			output[3] = false;
		}
		if (!output[3] && keys.get(Game.controls[1][3]))
		{
			out.println('D');
			output[3] = true;
		}
		if (output[4] && !keys.get(Game.controls[1][4]))
		{
			out.println('b');
			output[4] = false;
		}
		if (!output[4] && keys.get(Game.controls[1][4]))
		{
			out.println('B');
			output[4] = true;
		}
	}

	public void sayHello()
	{
		out.println(HELLO);
	}

	public void close()
	{
		try
		{
			status = 0;
			out.println(END);
			connection.close();
		}
		catch (IOException e)
		{
			System.err.println(e);
		}
		if (Bomberman.getGame().isPlaying())
			Bomberman.getGame().stopCoreGame();
	}

	public void disconnect()
	{
		close();
	}

	public void transferMap()
	{
		// TODO Auto-generated method stub
	}

	public void startGame()
	{
		sayStart();
		status = 4;
		Bomberman.getGame().startClientGame(this);
	}

	public void sayReady()
	{
		out.println(READY);
		if (status == 2)
		{
			status = 3;
		}
	}

}
