package bomberman.powerups;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.objects.Moveable;
import bomberman.players.Player;
import bomberman.resource.Image;

/**
 * Increases the number of bombs a player can use.
 * 
 * @see bomberman.powerups.Powerup
 * 
 */
public class Bombup extends Powerup
{
	/**
	 * Creates a new bomb-up item at the map position.
	 * 
	 * @param map - The map to be added to.
	 * @param tile_x - x position on the map.
	 * @param tile_y - y position on the map.
	 */
	public Bombup(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);

		img = new Animation(Image.read("data/sprites/bombup.png"), 0, 0, WIDTH, HEIGHT, ANI_COUNT, ANI_INTERVAL, false);
	}

	/**
	 * Map object method. If the bomb-up is touched by a player, the players bomb number is increased and the pickedUp method is called up.
	 * 
	 * @see #pickedUp()
	 */
	public void OnTouch(Moveable m, int side)
	{
		if (m instanceof Player)
		{
			((Player) m).bombs++;
			pickedUp();
		}
	}
}
