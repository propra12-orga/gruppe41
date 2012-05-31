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

public class Bomb extends Moveable
{
	private Player		player;

	public int			seconds;
	public int			radius;
	public boolean		isExploding;

	private boolean		moving	= false;
	private int			move_x, move_y;

	private Animation	img;

	public Bomb(Player player, Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);

		this.player = player;
		this.movement_speed += 2;

		try
		{
			img = new Animation(ImageIO.read(new File("data/sprites/bomb.png")), 0, 0, WIDTH, HEIGHT, 3, 250, true, 1);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		OnSpawn();
	}

	public void Update()
	{
		img.Update();

		if (moving)
		{
			if (Move(move_x, move_y)[1] == 0)
			{
				moving = false;
				move_y = 0;
				move_x = 0;
			}
		}

		int radius = this.radius + 1;

		if (System.currentTimeMillis() - creationTime > seconds * 1000)
		{
			int tile_x = getXTile(), tile_y = getYTile();

			isExploding = true;

			explode(tile_x, tile_y);
			map.Add(new Flame(map, tile_x, tile_y, Flame.FLAMES_CENTER));

			for (int xx = tile_x - 1; xx > tile_x - radius; xx--)
			{
				if (!explode(xx, tile_y))
					break;
				else
					map.Add(new Flame(map, xx, tile_y, (xx > tile_x - radius + 1) ? Flame.FLAMES_HORIZONTAL : Flame.FLAMES_LEFT_EDGE));
			}

			for (int xx = tile_x + 1; xx < tile_x + radius; xx++)
			{
				if (!explode(xx, tile_y))
					break;
				else
					map.Add(new Flame(map, xx, tile_y, (xx < tile_x + radius - 1) ? Flame.FLAMES_HORIZONTAL : Flame.FLAMES_RIGHT_EDGE));
			}

			for (int yy = tile_y - 1; yy > tile_y - radius; yy--)
			{
				if (!explode(tile_x, yy))
					break;
				else
					map.Add(new Flame(map, tile_x, yy, (yy > tile_y - radius + 1) ? Flame.FLAMES_VERTICAL : Flame.FLAMES_TOP_EDGE));
			}

			for (int yy = tile_y + 1; yy < tile_y + radius; yy++)
			{
				if (!explode(tile_x, yy))
					break;
				else
					map.Add(new Flame(map, tile_x, yy, (yy < tile_y + radius - 1) ? Flame.FLAMES_VERTICAL : Flame.FLAMES_BOT_EDGE));
			}

			Die();
		}
	}

	public void Render(Graphics2D g)
	{
		img.Render(g, x, y);
	}

	private boolean explode(int tile_x, int tile_y)
	{
		if (tile_x < 0 || tile_x > Map.TILES_COUNT_X - 1 || tile_y < 0 || tile_y > Map.TILES_COUNT_Y - 1)
			return false;

		boolean ret = true;
		MapObject[] ol = map.getObjectsOnTile(tile_x, tile_y);

		for (MapObject o : ol)
		{
			if (o == this)
				continue;
			if (o.isBlocking(this))
				ret = false;
			o.OnHurt();
		}

		return ret;
	}

	public boolean isBlocking(Moveable m)
	{
		return true;
	}

	public void OnHurt()
	{
		this.seconds = 0;
	}

	public void OnSpawn()
	{
		this.seconds = 3;
		this.radius = player.strength;
	}

	public void Die()
	{
		this.player.bombs++;
		Sound.explosion.play();
		super.Die();
	}

	public void OnCollide(Moveable m, int side)
	{
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
