package bomberman.network;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import bomberman.game.Game;
import bomberman.input.Keyboard;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.terrain.Rock;
import bomberman.players.Player;

public class Server extends Connector
{

	private ServerSocket	server;
	private Socket			client;

	public static Server createServer(Keyboard keys)
	{
		Server ret = new Server(keys);
		if (ret.status == 0)
			return null;
		else
			return ret;
	}

	protected Server(Keyboard keys)
	{
		super(keys);
		try
		{
			server = new ServerSocket(port);
			status++;
			counter = 0;
			createGame();
			thread_in = new Thread()
			{
				String	current;

				public void run()
				{
					try
					{
						client = server.accept();
						in = new Scanner(new InputStreamReader(client.getInputStream()));
						out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
						while (true)
						{
							current = in.nextLine();
							//System.out.println("Client: " + current);// for debugging and for fun
							switch (status)
							{
								case 1:
									if (current.equals(HELLO))
									{
										sayHello();
										status++;
									}
									else
									{
										disconnect();
									}
									break;
								case 2:
									if (current.equals(READY))
									{
										status++;
										transferMap();
									}
									else if (current.equals(END))
										disconnect();
									break;
								case 3:
									if (current.equals(START))
										startGame();
									else if (current.equals(END))
										disconnect();
									break;
								default:
									if (current.equals(START))
										startGame();
									else
									{
										switch (current.charAt(0))
										{
											case 'W':
												input[0] = true;
												System.out.println("top...");
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
												Scanner readInt = new Scanner(current);
												if(readInt.hasNextInt()){
													int pos = readInt.nextInt();
													Player otherplayer = core.getMap().getPlayer(1);
													otherplayer.x = pos % 10000;
													otherplayer.y = pos / 10000;
												}
												break;
										}
									}
									break;
							}
						}
					}
					catch (NoSuchElementException e)
					{
						status = 1;
						disconnect();
					}
					catch (IOException e1)
					{
						status = 0;
						disconnect();
						System.err.println();
					}
				}
			};
			thread_in.start();
		}
		catch (IOException e)
		{
			System.out.println("Creating Server on port " + this.port
			+ " not successful.");
		}
	}

	public void update()
	{
		if (output[0] && !keys.get(Game.controls[0][0]))
		{
			out.println('w');
			output[0] = false;
		}
		if (!output[0] && keys.get(Game.controls[0][0]))
		{
			out.println('W');
			output[0] = true;
		}
		if (output[1] && !keys.get(Game.controls[0][1]))
		{
			out.println('s');
			output[1] = false;
		}
		if (!output[1] && keys.get(Game.controls[0][1]))
		{
			out.println('S');
			output[1] = true;
		}
		if (output[2] && !keys.get(Game.controls[0][2]))
		{
			out.println('a');
			output[2] = false;
		}
		if (!output[2] && keys.get(Game.controls[0][2]))
		{
			out.println('A');
			output[2] = true;
		}
		if (output[3] && !keys.get(Game.controls[0][3]))
		{
			out.println('d');
			output[3] = false;
		}
		if (!output[3] && keys.get(Game.controls[0][3]))
		{
			out.println('D');
			output[3] = true;
		}
		if (output[4] && !keys.get(Game.controls[0][4]))
		{
			out.println('b');
			output[4] = false;
		}
		if (!output[4] && keys.get(Game.controls[0][4]))
		{
			out.println('B');
			output[4] = true;
		}
		if (counter == 40)
		{
			counter = 0;
			Player player = core.getMap().getPlayer(0);
			try
			{
				out.println('U');
				out.println(player.x + player.y * 10000 + " ");
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
			}
		}
		counter++;
	}

	public void disconnect()
	{
		if (out != null && status != 0)
		{
			out.println(END);
			status = 1;
		}
		try
		{
			if (client != null)
				client.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			status = 0;
		}
		if (game.isPlaying())
			game.stopCoreGame();
		thread_in.interrupt();
		if (status != 0)
			thread_in.run(); // restart thread for new client
		else
			close();
	}

	public void close()
	{
		try
		{
			if (client != null)
				client.close();
			server.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		status = 0;
	}

	public void sayHello()
	{
		out.println(HELLO);
	}

	public void transferMap()
	{
		int rockPosition;
		for (MapObject o : core.getMap().objects)
		{
			if (o instanceof Rock)
			{
				rockPosition = (o.x - Map.X_OFFS) / Map.TILE_SIZE + (o.y - Map.Y_OFFS) / Map.TILE_SIZE * Map.TILES_COUNT_X;
				out.println(rockPosition);
			}
		}
		out.println(-1);
	}

	public void createGame()
	{
		core = game.createServerGame(this);
	}

	public void startGame()
	{
		game.startPlaying();
		status = 5;
	}

	public void Restart(){
		out.println(RESTART);
		core = game.createServerGame(this);
		status = 2;
	}
	
	public void sayReady()
	{
		out.println(READY);
	}
}
