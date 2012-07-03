package bomberman.objects.terrain;

import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Moveable;
import bomberman.players.Player;
import bomberman.resource.Image;

/**
 * The game exit. This map object exists only once per game and only in single player mode.
 * 
 * @see bomberman.map.MapObject
 * 
 */
public class Exit extends MapObject
{
	/**
	 * Creates the game exit at the selected position. The render priority is zero, meaning it can be hided under a rock.
	 * 
	 * @param map - The map to be added to.
	 * @param tile_x - x position on the map.
	 * @param tile_y - y position on the map.
	 */
	public Exit(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);

		this.render_priority = 0;

		img = Image.read(map.path + "/exit.png");
	}

	/**
	 * Map object method. If a player touches the exit, the game is won. If anything else touches the exit, nothing will happen.
	 */
	public void OnTouch(Moveable m, int side)
	{
		if (m instanceof Player)
		{
			((Player) m).hasReachedExit = true;
		}
	}
}
