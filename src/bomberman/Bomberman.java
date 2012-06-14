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
	 * 16 milliseconds per frame, that's 60 frames per second. Using this
	 * constant slows the game and prevents it from being unequal fast on
	 * different computers.
	 */
	private static final int MS_PER_FRAME = (1000 / 60);
	/**
	 * Frames per second will be printed to console if this boolean is true.
	 */
	private static boolean show_fps = false;

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
		Game game = new Game();

		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();

		while (game.running) {
			long timer1 = System.currentTimeMillis();

			game.Update();
			game.Render();

			frames++;
			ticks++;

			long timer2 = System.currentTimeMillis();
			long timerdiff = timer2 - timer1;

			if (timerdiff > MS_PER_FRAME) {
				for (int i = 0; i < timerdiff / MS_PER_FRAME; i++) {
					game.Update();
					ticks++;
				}
			} else if (timerdiff < MS_PER_FRAME) {
				int sleep = (int) (MS_PER_FRAME - timerdiff);
				Thread.sleep(sleep);
			}

			if (show_fps && (System.currentTimeMillis() - lastTimer) > 1000) {
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
				lastTimer = System.currentTimeMillis();
			}//*/
			Thread.sleep(1);
		}
	}
}
