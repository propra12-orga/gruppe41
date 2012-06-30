package bomberman.network;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import bomberman.Bomberman;
import bomberman.core.CoreGame;
import bomberman.game.Game;
import bomberman.input.Keyboard;

public class Server extends Connector {

	private ServerSocket server;
	private Socket client;

	public static Server createServer(Keyboard keys) {
		Server ret = new Server(keys);
		if (ret.status == 0)
			return null;
		else
			return ret;
	}

	protected Server(Keyboard keys) {
		super(keys);
		try {
			server = new ServerSocket(port);
			status++;
			thread_in = new Thread() {
				String current;

				public void run() {
					try {
						client = server.accept();
						in = new Scanner(new InputStreamReader(
								client.getInputStream()));
						out = new PrintWriter(new OutputStreamWriter(
								client.getOutputStream()), true);
						while (true) {
							current = in.nextLine();
							switch (status) {
							case 1:
								if (current.equals(HELLO)) {
									sayHello();
									status++;
								} else {
									disconnect();
								}
								break;
							case 2:
								if (current.equals(READY))
									status++;
								else if (current.equals(END))
									disconnect();
								break;
							case 3:
								if (current.equals(START))
									startGame();
								else if (current.equals(END))
									disconnect();
								break;
							case 4:
								if (current.equals(START))
									startGame();
								else {
									switch (current.charAt(0)) {
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
							}
						}
					} catch (NoSuchElementException e) {
						status = 1;
						disconnect();
					} catch(IOException e1){
						status = 0;
						disconnect();
						e1.printStackTrace();
					}
				}
			};
			thread_in.start();
		} catch (IOException e) {
			Connector.currentPort++;
			System.out.println("Creating Server on port " + this.port
					+ " not successful.");
		}
	}

	public void update() {
		if(output[0] && !keys.get(Game.controls[0][0])){
			out.println('w');
			output[0] = false;
		}
		if(!output[0] && keys.get(Game.controls[0][0])){
			out.println('W');
			output[0] = true;
		}
		if(output[1] && !keys.get(Game.controls[0][1])){
			out.println('s');
			output[1] = false;
		}
		if(!output[1] && keys.get(Game.controls[0][1])){
			out.println('S');
			output[1] = true;
		}
		if(output[2] && !keys.get(Game.controls[0][2])){
			out.println('a');
			output[2] = false;
		}
		if(!output[2] && keys.get(Game.controls[0][2])){
			out.println('A');
			output[2]= true;
		}
		if(output[3] && !keys.get(Game.controls[0][3])){
			out.println('d');
			output[3] = false;
		}
		if(!output[3] && keys.get(Game.controls[0][3])){
			out.println('D');
			output[3] = true;
		}
		if(output[4] && !keys.get(Game.controls[0][4])){
			out.println('b');
			output[4] = false;
		}
		if(!output[4] && keys.get(Game.controls[0][4])){
			out.println('B');
			output[4] = true;
		}
	}

	public void disconnect() {
		if (out != null && status != 0) {
			out.println(END);
			status = 1;
		}
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
			status = 0;
		}
		if (Bomberman.getGame().isPlaying())
			Bomberman.getGame().stopCoreGame();
		thread_in.interrupt();
		if (status != 0)
			thread_in.run(); // restart thread for new client
		else
			close();
	}

	public void close() {
		try {
			client.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		status = 0;
	}

	public void sayHello() {
		out.println(HELLO);
	}

	public void transferMap() {
		// TODO will be written later, not implemented in test version
	}

	public void startGame() {
		status = 4;
		Bomberman.getGame().startCoreGame(CoreGame.NETWORK_GAME_HOST,
				new boolean[] { true, true, false, false });
	}

	public void sayReady() {
		out.println(READY);
	}

}
