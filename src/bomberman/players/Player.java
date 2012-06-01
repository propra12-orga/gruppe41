package bomberman.players;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
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

public class Player extends Mob
{
	public static final int	WIDTH			= 40;
	public static final int	HEIGHT			= 60;

	private Input			input;
	public int[]			controls;

	private long			plantTimer		= 0;
	private boolean			moving			= false;

	public int				bombs			= 1;
	public int				strength		= 1;
	public boolean			canKickBombs	= false;
	public boolean			hasReachedExit	= false;

	public int				player_id;

	private Animation[]		ani				= new Animation[4];

	public Player(Input input, Map map, int tile_x, int tile_y, int player)
	{
		super(map, tile_x, tile_y);

		this.input = input;
		this.controls = Game.controls[player];
		this.player_id = player;

		try
		{
			BufferedImage temp = ImageIO.read(new File("data/sprites/player_" + player + ".png"));

			img = temp.getSubimage(0, 0, WIDTH, 4 * HEIGHT);

			for (int i = 0; i < 4; i++)
			{
				ani[i] = new Animation(temp, 1, i, WIDTH, HEIGHT, 10, 125, false);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		OnSpawn();
	}

	public void increaseSpeed(int n)
	{
		if (movement_speed + n < 1 || movement_speed + n > 3)
			return;

		this.movement_speed += n;

		for (int i = 0; i < 4; i++)
		{
			ani[i].interval = 150 - (25 * movement_speed);
		}
	}

	public void Update()
	{
		if (moving)
			ani[dir].Update();

		int xa = 0, ya = 0;

		if (input.keys[controls[0]].down) ya--;
		if (input.keys[controls[1]].down) ya++;
		if (input.keys[controls[2]].down) xa--;
		if (input.keys[controls[3]].down) xa++;

		int dir = Move(xa, ya)[0];
		if (dir != FACE_DIRECTION_NONE)
		{
			this.dir = dir;
			startMoving();
		}
		else
			stopMoving();

		if (input.keys[controls[4]].down)
			plantBomb();
	}

	public void Render(Graphics2D g)
	{
		if (!moving)
		{
			g.drawImage(img,
						x,
						y + Map.TILE_SIZE - HEIGHT - 1,
						x + Map.TILE_SIZE,
						y + Map.TILE_SIZE - 1,
						0,
						dir * HEIGHT,
						WIDTH,
						dir * HEIGHT + HEIGHT,
						null);
		}
		else
			ani[dir].Render(g, x, y + Map.TILE_SIZE - HEIGHT - 1);
	}

	public void startMoving()
	{
		moving = true;
	}

	public void stopMoving()
	{
		moving = false;
		ani[dir].Reset();
	}

	public void plantBomb()
	{
		if (bombs < 1 || System.currentTimeMillis() - plantTimer < 200)
			return;

		plantTimer = System.currentTimeMillis();

		int tile_x = getXTile(), tile_y = getYTile();
		MapObject[] ol = map.getObjectsOnTile(tile_x, tile_y);
		for (MapObject o : ol)
		{
			if (o instanceof Bomb)
				return;
		}
		map.Add(new Bomb(this, map, tile_x, tile_y));
		bombs--;
		Sound.plantBomb.play();
	}

	public boolean isBlocking(Moveable m)
	{
		if (m instanceof Bomb && !((Bomb) m).isExploding)
			return true;
		else
			return false;
	}

	public void OnSpawn()
	{
		this.map.num_of_players++;
		this.movement_speed++;
	}

	public void Die()
	{
		super.Die();
		map.Add(new PlayerBurnt(map, x, y));
		map.num_of_players--;
	}

	public void OnHurt()
	{
		Die();
	}
}
