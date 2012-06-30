package bomberman.objects.bomb;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Moveable;
import bomberman.players.Player;
import bomberman.sound.Sound;

/**
 * Bomb class extends moveable.
 * 
 * @see bomberman.objects.Moveable
 * 
 */
public class Bomb extends Moveable {
	/**
	 * The player who started this bomb.
	 */
	private Player player;
	/*
	 * The bomb timer.
	 */
	public int seconds;
	/**
	 * The explosion radius.
	 */
	public int radius;
	/**
	 * 
	 */
	public boolean isExploding;
	/**
	 * Is true if the bomb was kicked.
	 */
	private boolean moving = false;
	private int move_x, move_y;
	/**
	 * The animation showing the ticking bomb.
	 */
	private Animation img;

	/**
	 * 
	 * @param player
	 *            - The player who owns this bomb.
	 * @param map
	 *            - The map the bomb has to be added to.
	 * @param tile_x
	 *            - x position on the map.
	 * @param tile_y
	 *            - y position on the map.
	 */
	public Bomb(Player player, Map map, int tile_x, int tile_y) {
		super(map, tile_x, tile_y);

		this.player = player;
		this.movement_speed += 6;

		try {
			img = new Animation(
					ImageIO.read(new File("data/sprites/bomb.png")), 0, 0,
					WIDTH, HEIGHT, 3, 250, true, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		OnSpawn();
	}

	/**
	 * Update method for bombs, updates the animation, moves the bomb if it was
	 * kicked and blows it up if the time is up. There will be created a flame
	 * object and the bomb is removed.
	 */
	public void Update() {
		img.Update();

		if (moving) {
			if (Move(move_x, move_y)[1] == 0) {
				moving = false;
				move_y = 0;
				move_x = 0;
			}
		}

		int radius = this.radius + 1;

		if (System.currentTimeMillis() - creationTime > seconds * 1000) {
			int tile_x = getXTile(), tile_y = getYTile();

			isExploding = true;

			explode(tile_x, tile_y);
			map.Add(new Flame(map, tile_x, tile_y, Flame.FLAMES_MID));

			for (int xx = tile_x - 1; xx > tile_x - radius; xx--) {
				if (!explode(xx, tile_y))
					break;
				else
					map.Add(new Flame(map, xx, tile_y, (xx > tile_x - radius
							+ 1) ? Flame.FLAMES_LEFT : Flame.FLAMES_LEFT_EDGE));
			}

			for (int xx = tile_x + 1; xx < tile_x + radius; xx++) {
				if (!explode(xx, tile_y))
					break;
				else
					map.Add(new Flame(map, xx, tile_y, (xx < tile_x + radius
							- 1) ? Flame.FLAMES_RIGHT : Flame.FLAMES_RIGHT_EDGE));
			}

			for (int yy = tile_y - 1; yy > tile_y - radius; yy--) {
				if (!explode(tile_x, yy))
					break;
				else
					map.Add(new Flame(map, tile_x, yy, (yy > tile_y - radius
							+ 1) ? Flame.FLAMES_TOP : Flame.FLAMES_TOP_EDGE));
			}

			for (int yy = tile_y + 1; yy < tile_y + radius; yy++) {
				if (!explode(tile_x, yy))
					break;
				else
					map.Add(new Flame(map, tile_x, yy, (yy < tile_y + radius
							- 1) ? Flame.FLAMES_BOT : Flame.FLAMES_BOT_EDGE));
			}

			Die();
		}
	}

	/**
	 * Renders the bomb by rendering the animation.
	 */
	public void Render(Graphics2D g) {
		img.Render(g, x, y);
	}

	/**
	 * Blows up any removeable object at the selected position. This method is
	 * called up several times by update() when a bomb explodes.
	 * 
	 * @param tile_x
	 *            - x position on the map.
	 * @param tile_y
	 *            - y position on the map.
	 * @return false if impossible (blocked by a wall) or else true.
	 * 
	 * @see bomberman.objects.bomb.Bomb#Update()
	 */
	private boolean explode(int tile_x, int tile_y) {
		if (tile_x < 0 || tile_x > Map.TILES_COUNT_X - 1 || tile_y < 0
				|| tile_y > Map.TILES_COUNT_Y - 1)
			return false;

		boolean ret = true;
		MapObject[] ol = map.getObjectsOnTile(tile_x, tile_y);

		for (MapObject o : ol) {
			if (o == this)
				continue;
			if (o.isBlocking(this))
				ret = false;
			o.OnHurt();
		}

		return ret;
	}

	/**
	 * Is blocking method, always returns true because a bomb will block other
	 * ma objects trying to pass it.
	 */
	public boolean isBlocking(Moveable m) {
		return true;
	}

	public void OnHurt() {
		this.seconds = 0;
	}

	/**
	 * Initializes the bomb, sets the timer to three seconds and sets the radius
	 * to the player's bomb strength.
	 */
	public void OnSpawn() {
		this.seconds = 3;
		this.radius = player.strength;
	}

	/**
	 * Die method for bomb, calls up super method and plays the explosion sound.
	 * The players number of available bombs is increased by one.
	 * 
	 * @see bomberman.sound.Sound#play()
	 */
	public void Die() {
		super.Die();
		Sound.explosion.play();
		this.player.bombs++;
	}

	/**
	 * Method used when colliding with another object. Bomb will be moved if the
	 * object is a player and can kick bombs.
	 */
	public void OnCollide(Moveable m, int side) {
		if (moving || (m instanceof Player && !((Player) m).canKickBombs))
			return;

		this.moving = true;

		if (side == MapObject.TOUCHED_TOP)
			move_y++;
		else if (side == MapObject.TOUCHED_BOT)
			move_y--;
		else if (side == MapObject.TOUCHED_LEFT)
			move_x++;
		else
			move_x--;
	}
}
