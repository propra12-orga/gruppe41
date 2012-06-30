package bomberman;

import bomberman.game.Game;

/**
 * This class contains the main method and starts the game. This class does not
 * have a constructor or any methods but the main method. This game does not use
 * multiple threads.
 * 
 * @see bomberman.Bomberman#main
 */
public class Bomberman {
	/**
	 * 25 milliseconds per frame, that's 40 frames per second. Using this
	 * constant slows the game and prevents it from being unequal fast on
	 * different computers.
	 */
	private static final int MS_PER_FRAME = 25;
	/**
	 * Frames per second will be printed to console if this boolean is true.
	 */
	private static boolean show_fps = false;

	private static Game bomberman;
	/**
	 * Makes the game accessable in all classes.
	 */
	public static Game getGame(){
		return bomberman;
	}
	/**
	 * Creates a Game instance and starts the main loop. The methods
	 * game.update() and game.render() are used. The important information about
	 * the game are in class Game.
	 * 
	 * @param args
	 *            - Java standard but not used in this game.
	 * @throws InterruptedException
	 * @see bomberman.game.Game
	 * @see bomberman.game.Game#Update()
	 * @see bomberman.game.Game#Render()
	 */
	public static void main(String[] args) throws InterruptedException {
		bomberman = new Game();

		long startTime = System.currentTimeMillis();
		long gameTime = 0;
		long realTime;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();

		while (bomberman.running) {
			
			bomberman.Update();
			bomberman.Render();

			gameTime += MS_PER_FRAME;
			frames++;
			ticks++;

			realTime = System.currentTimeMillis()-startTime;

			if (gameTime < realTime) {
				for (int i = 0; i < (realTime-gameTime) / MS_PER_FRAME; i++) {
					bomberman.Update();
					gameTime += MS_PER_FRAME;
					ticks++;
				}
			} else if (realTime < gameTime) {
				int sleep = (int) (gameTime-realTime);
				Thread.sleep(sleep);
			}

			if (show_fps && (System.currentTimeMillis() - lastTimer) > 1000) {
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
				lastTimer = System.currentTimeMillis();
			}
			Thread.sleep(1);
		}
	}
}
