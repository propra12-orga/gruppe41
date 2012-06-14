package bomberman.objects;

import bomberman.map.Map;

/**
 * Subclass of moveable, subclass of map object. Players are objects of this
 * type. This class only sets the render priority to 2.
 * 
 * @see bomberman.objects.Moveable
 * @see bomberman.players.Player
 */
public class Mob extends Moveable {
	/**
	 * Calls up the super constructor moveable and sets the render priority to
	 * 2.
	 * 
	 * @param map
	 *            - The map where this object has to be added to.
	 * @param tile_x
	 *            - x position on the map.
	 * @param tile_y
	 *            - y position on the map.
	 */
	public Mob(Map map, int tile_x, int tile_y) {
		super(map, tile_x, tile_y);

		this.render_priority = 2;
	}
}
