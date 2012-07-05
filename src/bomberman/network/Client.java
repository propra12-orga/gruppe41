package bomberman.network;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import bomberman.game.Game;
import bomberman.input.Keyboard;
import bomberman.map.Map;
import bomberman.objects.terrain.Rock;
import bomberman.players.Player;
import bomberman.powerups.Bombup;
import bomberman.powerups.Flameup;
import bomberman.powerups.Kickup;
import bomberman.powerups.Speedup;

public class Client extends Connector
{
	private Socket	connection;
	private String	instruction;

	public static Client createClient(Keyboard keys)
	{
		Client ret;
		ret = new Client(keys);
		if (ret.status != 0)
		{
			return ret;
		}
		else
		{
			return null;
		}
	}

	public Client(Keyboard keys)
	{
		super(keys);
		for (int i = 0; i < 5; i++)
		{
			output[i] = false;
			input[i] = false;
		}
		counter = 0;
		instruction = null;
		try
		{
			connection = new Socket(currentHost, port);
			out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()), true);
			in = new Scanner(new InputStreamReader(connection.getInputStream()));
			status++;
			createGame();
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
					Scanner readCurrent;
					sayHello();
					while (true)
					{
						try
						{
							current = in.nextLine();
							System.out.println("Server: " + current);// for debugging and for fun
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
									Scanner mapread = new Scanner(current);
									int next;
									if ((next = mapread.nextInt()) == -1)
									{
										status++;
										break;
									}
									core.getMap().Add(new Rock(core.getMap(), next % Map.TILES_COUNT_X, next / Map.TILES_COUNT_X));
									if (current.equals(END))
										disconnect();
									break;
								case 4:
									if (current.equals(READY))
									{
										sayStart();
										startGame();
									}
									break;
								case 5:
									if (current.equals(RESTART))
									{
										game.stopCoreGame();
										createGame();
										status = 2;
										try
										{
											Thread.sleep(100);
										}
										catch (Exception e)
										{
										}
										sayReady();
									}
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
											case '-':
												core.endGame(true);
												break;
											case '+':
												core.endGame(false);
												break;
											case 'U':
												current = in.nextLine();
												readCurrent = new Scanner(current);
												if (readCurrent.hasNextInt())
												{
													int pos = readCurrent.nextInt();
													Player otherplayer = core.getMap().getPlayer(0);
													otherplayer.x = pos % 10000;
													otherplayer.y = pos / 10000;
												}
												break;
											case 'I':
												instruction = new String(current);
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
		if (instruction != null)
		{
			Scanner readInstruction = new Scanner(instruction);
			try
			{
				if (instruction.charAt(0) == 'I')
				{
					readInstruction.next();
					readInstruction.next();
					int pos = readInstruction.nextInt();
					switch (instruction.charAt(2))
					{
						case 'B':
							core.getMap().Add(new Bombup(core.getMap(), pos % Map.TILES_COUNT_X, pos / Map.TILES_COUNT_X));
							break;
						case 'F':
							core.getMap().Add(new Flameup(core.getMap(), pos % Map.TILES_COUNT_X, pos / Map.TILES_COUNT_X));
							break;
						case 'K':
							core.getMap().Add(new Kickup(core.getMap(), pos % Map.TILES_COUNT_X, pos / Map.TILES_COUNT_X));
							break;
						case 'S':
							core.getMap().Add(new Speedup(core.getMap(), pos % Map.TILES_COUNT_X, pos / Map.TILES_COUNT_X));
							break;
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				instruction = null;
			}
		}
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
		if (counter == 40)
		{
			counter = 0;
			Player player = core.getMap().getPlayer(1);
			try
			{
				out.println('U');
				out.println(player.x + player.y * 10000);
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
			}
		}
		counter++;
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
		if (game.isPlaying())
			game.stopCoreGame();
	}

	public void disconnect()
	{
		close();
	}

	public void createGame()
	{
		core = game.createClientGame(this);
	}

	public void startGame()
	{
		status++;
		game.startPlaying();
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
