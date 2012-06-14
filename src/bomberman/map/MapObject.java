package bomberman.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bomberman.objects.Mob;
import bomberman.objects.Moveable;

/**
 * Abstract class. The following classes (and their subclasses) are sublasses of MapObject:
 * 
 * @see bomberman.objects.Moveable
 * @see bomberman.objects.terrain.Block
 * @see bomberman.objects.terrain.Rock
 * @see bomberman.objects.terrain.Exit
 * @see bomberman.objects.bomb.Flame
 */
public abstract class MapObject {
	public static final int WIDTH = Map.TILE_SIZE;
	public static final int HEIGHT = Map.TILE_SIZE;

	public static final int TOUCHED_TOP = Mob.FACE_DIRECTION_DOWN;
	public static final int TOUCHED_BOT = Mob.FACE_DIRECTION_UP;
	public static final int TOUCHED_LEFT = Mob.FACE_DIRECTION_RIGHT;
	public static final int TOUCHED_RIGHT = Mob.FACE_DIRECTION_LEFT;

	public int x, y, width, height, render_priority;
	public long creationTime;

	public BufferedImage img;
	protected Map map;

	/**
	 * Creates a new map object, but does not add it to the maps object vector.
	 * 
	 * @param map
	 *            - The map the object has to be added to.
	 * @param tile_x
	 *            - x position on the map.
	 * @param tile_y
	 *            - y position on the map.
	 */
	public MapObject(Map map, int tile_x, int tile_y) {
		this.x = map.getXByTile(tile_x);
		this.y = map.getYByTile(tile_y);
		this.map = map;
		this.width = WIDTH;
		this.height = HEIGHT;
		this.render_priority = 1;
		this.creationTime = System.currentTimeMillis();
	}

	/**
	 * Update method, to be overwritten by subclasses.
	 */
	public void Update() {

	}
	/**
	 * Short draw version, calls up ful method.
	 * @param g - Graphics.
	 * @param sprite_x - x position.
	 * @param sprite_y - y position.
	 */
	public void draw(Graphics2D g, int sprite_x, int sprite_y) {
		draw(g, x, y, sprite_x, sprite_y);
	}

	public void draw(Graphics2D g, int x, int y, int sprite_x, int sprite_y) {
		g.drawImage(img, x, y, x + WIDTH, y + HEIGHT, sprite_x * WIDTH,
				sprite_y * HEIGHT, sprite_x * WIDTH + WIDTH, sprite_y * HEIGHT
						+ HEIGHT, null);
	}
	/**
	 * Render method, draws object with x = y = 0.
	 * @param g
	 */
	public void Render(Graphics2D g) {
		draw(g, 0, 0);
	}

	public int getXTile() {
		return map.getTileByX(x + (width / 2));
	}

	public int getYTile() {
		return map.getTileByY(y + (height / 2));
	}

	public boolean isBlocking(Moveable m) {
		return false;
	}

	public void OnTouch(Moveable m, int side) {

	}

	public void OnCollide(Moveable m, int side) {

	}

	public void OnHurt() {

	}

	public void Die() {
		map.Remove(this);
	}
}
