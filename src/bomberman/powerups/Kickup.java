package bomberman.powerups;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.objects.Moveable;
import bomberman.players.Player;

public class Kickup extends Powerup
{
	public Kickup(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);

		try
		{
			img = new Animation(ImageIO.read(new File("data/sprites/kickup.png")), 0, 0, WIDTH, HEIGHT, ANI_COUNT, ANI_INTERVAL, false);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void OnTouch(Moveable m, int side)
	{
		if (m instanceof Player)
		{
			((Player) m).canKickBombs = true;
			pickedUp();
		}
	}
}
