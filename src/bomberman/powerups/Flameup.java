package bomberman.powerups;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.objects.Moveable;
import bomberman.players.Player;
import bomberman.resource.Image;

/**
 * The flame-up increases the strength of the player and thus the radius of his bombs.
 * 
 * @see bomberman.powerups.Powerup
 * 
 */
public class Flameup extends Powerup
{
	/**
	 * Creates a new flame-up item. See super constructor.
	 * 
	 * @param map
	 * @param tile_x
	 * @param tile_y
	 */
	public Flameup(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);

		img = new Animation(Image.read("data/sprites/flameup.png"), 0, 0, WIDTH, HEIGHT, ANI_COUNT, ANI_INTERVAL, false);
	}

	/**
	 * Map object method. If the bomb-up is touched by a player, the players strength is increased and the pickedUp method is called up.
	 * 
	 * @see #pickedUp()
	 */
	public void OnTouch(Moveable m, int side)
	{
		if (m instanceof Player)
		{
			((Player) m).strength++;
			pickedUp();
		}
	}
}
