package bomberman.powerups;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.objects.Moveable;
import bomberman.players.Player;

/**
 * This power-up increases the players speed (surprise!).
 * @see bomberman.powerups.Powerup
 *
 */
public class Speedup extends Powerup {
	public Speedup(Map map, int tile_x, int tile_y) {
		super(map, tile_x, tile_y);

		try {
			img = new Animation(ImageIO.read(new File(
					"data/sprites/speedup.png")), 0, 0, WIDTH, HEIGHT,
					ANI_COUNT, ANI_INTERVAL, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Map object method. If the bomb-up is touched by a player, the players
	 * speed is increased and the pickedUp method is called up.
	 * 
	 * @see #pickedUp()
	 */
	public void OnTouch(Moveable m, int side) {
		if (m instanceof Player) {
			((Player) m).increaseSpeed(1);
			pickedUp();
		}
	}
}
