package bomberman.powerups;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.objects.Moveable;
import bomberman.players.Player;

/**
 * Increases the number of bombs a player can use.
 * @see bomberman.powerups.Powerup
 * 
 */
public class Bombup extends Powerup {
	/**
	 * Creates a new bombup item at the map position.
	 * @param map - The map to be added to.
	 * @param tile_x - x position on the map.
	 * @param tile_y - y position on the map.
	 */
	public Bombup(Map map, int tile_x, int tile_y) {
		super(map, tile_x, tile_y);

		try {
			img = new Animation(
					ImageIO.read(new File("data/sprites/bombup.png")), 0, 0,
					WIDTH, HEIGHT, ANI_COUNT, ANI_INTERVAL, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Map object method. If the bombup is touched by a player, the players bomb
	 * number is increaed and the pickedUp method is called up.
	 * 
	 * @see #pickedUp()
	 */
	public void OnTouch(Moveable m, int side) {
		if (m instanceof Player) {
			((Player) m).bombs++;
			pickedUp();
		}
	}
}
