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
	public boolean			hasreachedexit	= false;

	public int				player_id;

	private Animation		player_front, player_back, player_right, player_left;

	public Player(Input input, Map map, int tile_x, int tile_y, int player)
	{
		super(map, tile_x, tile_y);

		this.input = input;
		this.controls = Game.controls[player];
		this.player_id = player;

		this.movement_speed++;

		try
		{
			BufferedImage temp = ImageIO.read(new File("data/sprites/player_" + player + ".png"));

			player_front = new Animation(temp, 0, 0, WIDTH, HEIGHT, 8, 150, false, 2);
			player_back = new Animation(temp, 0, 1, WIDTH, HEIGHT, 8, 150, false, 2);
			player_left = new Animation(temp, 0, 2, WIDTH, HEIGHT, 8, 150, false, 2);
			player_right = new Animation(temp, 0, 3, WIDTH, HEIGHT, 8, 150, false, 2);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		OnSpawn();
	}

	public void Update()
	{
		if (dir == FACE_DIRECTION_DOWN)
			player_front.Update();
		else if (dir == FACE_DIRECTION_UP)
			player_back.Update();
		else if (dir == FACE_DIRECTION_LEFT)
			player_left.Update();
		else
			player_right.Update();

		int xa = 0, ya = 0;

		if (input.keys[controls[0]].down) ya--;
		if (input.keys[controls[1]].down) ya++;
		if (input.keys[controls[2]].down) xa--;
		if (input.keys[controls[3]].down) xa++;

		int dir = Move(xa, ya);
		if (dir != Mob.FACE_DIRECTION_NONE)
		{
			this.dir = dir;
			startMoving();
		}
		else
			stopMoving();

		if (input.keys[controls[4]].down)
			plantBomb();
	}

	public void startMoving()
	{
		if (!moving)
		{
			player_front.Reset();
			player_back.Reset();
			player_left.Reset();
			player_right.Reset();
		}
		moving = true;
	}

	public void stopMoving()
	{
		moving = false;
	}

	public void Render(Graphics2D g)
	{
		if (!moving)
		{
			if (dir == FACE_DIRECTION_DOWN)
				img = player_front.sheet;
			else if (dir == FACE_DIRECTION_UP)
				img = player_back.sheet;
			else if (dir == FACE_DIRECTION_LEFT)
				img = player_left.sheet;
			else
				img = player_right.sheet;

			g.drawImage(img,
						x,
						y + Map.TILE_SIZE - HEIGHT - 1,
						x + Map.TILE_SIZE,
						y + Map.TILE_SIZE - 1,
						0,
						0,
						WIDTH,
						HEIGHT,
						null);
		}
		else
		{
			Animation img;

			if (dir == FACE_DIRECTION_DOWN)
				img = player_front;
			else if (dir == FACE_DIRECTION_UP)
				img = player_back;
			else if (dir == FACE_DIRECTION_LEFT)
				img = player_left;
			else
				img = player_right;

			img.Render(g, x, y + Map.TILE_SIZE - HEIGHT - 1);
		}
	}

	public void plantBomb()
	{
		if (bombs < 1 || System.currentTimeMillis() - plantTimer < 250)
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

	public void OnSpawn()
	{
		map.num_of_players++;
	}

	public void OnDeath()
	{
		super.OnDeath();
		map.num_of_players--;
	}

	public void OnHurt()
	{
		OnDeath();
	}
}
