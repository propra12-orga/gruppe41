package bomberman.objects.bomb;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Moveable;

/**
 * Objects of this class show flames and kill players if they collide.
 * 
 * @see bomberman.map.MapObject
 */
public class Flame extends MapObject {
	public final static int FLAMES_MID = 0;
	public final static int FLAMES_LEFT = 1;
	public final static int FLAMES_LEFT_EDGE = 2;
	public final static int FLAMES_TOP = 3;
	public final static int FLAMES_TOP_EDGE = 4;
	public final static int FLAMES_RIGHT = 5;
	public final static int FLAMES_RIGHT_EDGE = 6;
	public final static int FLAMES_BOT = 7;
	public final static int FLAMES_BOT_EDGE = 8;

	private boolean ready_to_die;

	Animation img;

	/**
	 * Places a new flame onto the map.
	 * 
	 * @param map
	 *            - The map to be added to.
	 * @param tile_x
	 *            - x position on the map.
	 * @param tile_y
	 *            - y position on the map.
	 * @param type
	 *            - The type determines how the flame is painted (for example
	 *            zero is the center of an explosion, four the top edge)
	 */
	public Flame(Map map, int tile_x, int tile_y, int type) {
		super(map, tile_x, tile_y);

		try {
			img = new Animation(
					ImageIO.read(new File("data/sprites/flames.png")), 0, type,
					Map.TILE_SIZE, Map.TILE_SIZE, 12, 50, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update method for flames. The animation is updated and the flames are
	 * removed is necessary.
	 */
	public void Update() {
		img.Update();

		if (img.current == 0 && ready_to_die)
			Die();
		else if (img.current == 11)
			ready_to_die = true;
	}

	/**
	 * Renders the flame by rendering the animation.
	 * 
	 * @see bomberman.animation.Animation#Render(Graphics2D, int, int)
	 */
	public void Render(Graphics2D g) {
		img.Render(g, x, y);
	}

	/**
	 * This method calls up "onHurt" method for any map object touching the
	 * flame. Players die, other bombs will blow up.
	 */
	public void OnTouch(Moveable m, int side) {
		m.OnHurt();
	}
}
