package bomberman.players;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.animation.Animation;
import bomberman.game.Game;
import bomberman.input.Input;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Mob;
import bomberman.objects.Moveable;
import bomberman.objects.bomb.Bomb;
import bomberman.sound.Sound;

/**
 * The player class, extends mob extends moveable extends map object.
 */
public class Player extends Mob {
	public static final int WIDTH = 40;
	public static final int HEIGHT = 60;

	/**
	 * Needs a connection to the keyboard input.
	 */
	private Input input;
	/**
	 * The controls used for this player. Will be setted in main menu.
	 */
	public int[] controls;

	private long plantTimer = 0;
	/**
	 * States whether the player is moving or not.
	 */
	private boolean moving = false;
	/**
	 * Number of bombs. Can be increased by bombups. There is no maximum limit.
	 * 
	 * @see bomberman.powerups.Bombup
	 */
	public int bombs = 1;
	/**
	 * The radius of the planted bombs.
	 */
	public int strength = 1;
	/**
	 * If this is true, the player can kick bombs away by touching them.
	 */
	public boolean canKickBombs = false;
	/**
	 * Only used in single player mode. If this boolean is true, the game ends
	 * with a won message and a menu.
	 */
	public boolean hasReachedExit = false;

	public int player_id;
	/**
	 * An array of animations, one for each possibility of face direction.
	 */
	private Animation[] ani = new Animation[4];

	/**
	 * Player constructor.
	 * 
	 * @param input
	 *            - Keyboard input.
	 * @param map
	 *            - The map to play on.
	 * @param tile_x
	 *            - x position on the map.
	 * @param tile_y
	 *            - y position on the map.
	 * @param player
	 *            - number of the player.
	 */
	public Player(Input input, Map map, int tile_x, int tile_y, int player) {
		super(map, tile_x, tile_y);
		this.input = input;
		this.controls = Game.controls[player];
		this.player_id = player;

		try {
			BufferedImage temp = ImageIO.read(new File("data/sprites/player_"
					+ player + ".png"));

			img = temp.getSubimage(0, 0, WIDTH, 4 * HEIGHT);

			for (int i = 0; i < 4; i++) {
				ani[i] = new Animation(temp, 1, i, WIDTH, HEIGHT, 10, 125,
						false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		OnSpawn();
	}

	/**
	 * Changes the player's speed. Can not exceed three or become less than 1.
	 * 
	 * @param n
	 *            - Speed change.
	 */
	public void increaseSpeed(int n) {
		if (movement_speed + n < 1 || movement_speed + n > 3)
			return;

		this.movement_speed += n;

		for (int i = 0; i < 4; i++) {
			ani[i].interval = 150 - (25 * movement_speed);
		}
	}

	/**
	 * Update method for player. Moves the player if necessary and plants bombs.
	 * Also updates th players animation.
	 */
	public void Update() {
		if (moving)
			ani[dir].Update();

		int xa = 0, ya = 0;

		if (input.get(controls[0]) || input.netget(0))
			ya--;
		if (input.get(controls[1]) || input.netget(1))
			ya++;
		if (input.get(controls[2]) || input.netget(2))
			xa--;
		if (input.get(controls[3]) || input.netget(3))
			xa++;

		int dir = Move(xa, ya)[0];
		if (dir != FACE_DIRECTION_NONE) {
			this.dir = dir;
			startMoving();
		} else
			stopMoving();

		if (input.get(controls[4])|| input.netget(4))
			plantBomb();
		if(input.netget(5))
			Die();
	}

	/**
	 * Draws the player The used animation depends on the moving boolean and the
	 * face direction.
	 */
	public void Render(Graphics2D g) {
		if (!moving) {
			g.drawImage(img, x, y + Map.TILE_SIZE - HEIGHT - 1, x
					+ Map.TILE_SIZE, y + Map.TILE_SIZE - 1, 0, dir * HEIGHT,
					WIDTH, dir * HEIGHT + HEIGHT, null);
		} else
			ani[dir].Render(g, x, y + Map.TILE_SIZE - HEIGHT - 1);
	}

	/**
	 * Starts moving by setting moving boolean true.
	 */
	public void startMoving() {
		moving = true;
	}

	/**
	 * Stops moving by setting moving boolean false.
	 */
	public void stopMoving() {
		moving = false;
		ani[dir].Reset();
	}

	/**
	 * Creates a new bomb. Des not have any parameters. Position on the field is
	 * just the player's position.
	 */
	public void plantBomb() {
		if (bombs < 1 || System.currentTimeMillis() - plantTimer < 200)
			return;

		plantTimer = System.currentTimeMillis();

		int tile_x = getXTile(), tile_y = getYTile();
		MapObject[] ol = map.getObjectsOnTile(tile_x, tile_y);
		for (MapObject o : ol) {
			if (o instanceof Bomb)
				return;
		}
		map.Add(new Bomb(this, map, tile_x, tile_y));
		bombs--;
		Sound.plantBomb.play();
	}

	/**
	 * Blocking method, usually returns false, returns true if the other object
	 * is a bomb.
	 */
	public boolean isBlocking(Moveable m) {
		if (m instanceof Bomb && !((Bomb) m).isExploding)
			return true;
		else
			return false;
	}

	/**
	 * Spawning the player increases the number of players saved on the map.
	 * Also ses movement speed to one.
	 */
	public void OnSpawn() {
		this.map.num_of_players++;
		this.movement_speed++;
	}

	/**
	 * Die method, called up by onHurt method. The player is removed and a burnt
	 * player is shown. Doesn't work properly.
	 */
	public void Die() {
		super.Die();
		map.Add(new PlayerBurnt(map, x, y));
		map.num_of_players--;
	}

	/**
	 * Map object method. The plyaer dies if he got hit.
	 * 
	 * @see #Die()
	 */
	public void OnHurt() {
		Die();
	}
}
