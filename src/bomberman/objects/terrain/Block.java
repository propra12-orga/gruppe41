package bomberman.objects.terrain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Moveable;

/**
 * Blocks are completely solid. They do not have an update function (because
 * they cannot change at all) but blocks other map objects and is rendered by
 * the map.
 * 
 * @see bomberman.map.MapObject
 */
public class Block extends MapObject {
	/**
	 * Places a new blck onto the selcted position at the map.
	 * 
	 * @param map
	 *            - The map to be added to.
	 * @param tile_x
	 *            - x position on the map.
	 * @param tile_y
	 *            - y position on the map.
	 */
	public Block(Map map, int tile_x, int tile_y) {
		super(map, tile_x, tile_y);

		try {
			img = ImageIO.read(new File(map.path + "/block.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Always retusn true because no object can move through a wall.
	 */
	public boolean isBlocking(Moveable m) {
		return true;
	}
}
