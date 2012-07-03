package bomberman.powerups;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.objects.Moveable;
import bomberman.players.Player;
import bomberman.resource.Image;

/**
 * The kick-up makes it possible to kick bombs whenever touching them.
 * 
 * @see bomberman.powerups.Powerup
 * 
 */
public class Kickup extends Powerup
{
	/**
	 * See super constructor.
	 * 
	 * @param map
	 * @param tile_x
	 * @param tile_y
	 */
	public Kickup(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);

		img = new Animation(Image.read("data/sprites/kickup.png"), 0, 0, WIDTH, HEIGHT, ANI_COUNT, ANI_INTERVAL, false);
	}

	/**
	 * Map object method. If the bomb-up is touched by a player, the players canKickBombs boolean is set true and the pickedUp method is called up.
	 * 
	 * @see #pickedUp()
	 */
	public void OnTouch(Moveable m, int side)
	{
		if (m instanceof Player)
		{
			((Player) m).canKickBombs = true;
			pickedUp();
		}
	}
}
