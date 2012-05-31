package bomberman;

import bomberman.game.Game;

public class Bomberman
{
	private static final int		MS_PER_FRAME	= (1000 / 60);
	private static final boolean	show_fps		= false;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException
	{
		Game game = new Game();

		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();

		while (game.running)
		{
			long timer1 = System.currentTimeMillis();

			game.Update();
			game.Render();

			frames++;
			ticks++;

			long timer2 = System.currentTimeMillis();
			long timerdiff = timer2 - timer1;

			if (timerdiff > MS_PER_FRAME)
			{
				for (int i = 0; i < timerdiff / MS_PER_FRAME; i++)
				{
					game.Update();
					ticks++;
				}
			}
			else if (timerdiff < MS_PER_FRAME)
			{
				int sleep = (int) (MS_PER_FRAME - timerdiff);
				Thread.sleep(sleep);
			}

			if (show_fps && (System.currentTimeMillis() - lastTimer) > 1000)
			{
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
				lastTimer = System.currentTimeMillis();
			}

			Thread.sleep(1);
		}
	}
}
