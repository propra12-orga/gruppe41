package bomberman.objects.bomb;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Moveable;
import bomberman.objects.terrain.Rock;
import bomberman.players.Player;
import bomberman.powerups.*;
import bomberman.sound.Sound;

public class Bomb extends Moveable
{
	private Player		player;

	public int			seconds;
	public int			strength;

	private boolean		moving	= false;
	private int			move_x, move_y;

	private Animation	img;

	public Bomb(Player player, Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y, true, true, false);

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
			if (o.vulnerable){
				if (o instanceof Rock)
				{
				o.OnHurt();
				double test=Math.random();
				if(test<0.2&&test>0.1)
				{
				map.Add(new Kickup(map,tile_x,tile_y));
				}
				if(test<0.6&&test>0.4)
				{
				map.Add(new Bombup(map,tile_x,tile_y));
				}
				if(test<0.3&&test>0.2)
				{
				map.Add(new Speedup(map,tile_x,tile_y));
				}
				}
				o.OnHurt();}
			if (o.blocking)
				ret = false;
		}

		return ret;
	}

	public void Update()
	{
		img.Update();

		if (moving)
		{
			Move(move_x, move_y);
		}

		int strength = this.strength + 1;

		if (System.currentTimeMillis() - creationTime > seconds * 1000)
		{
			explode(getXTile(), getYTile());
			map.Add(new Flame(map, getXTile(), getYTile(), Flame.FLAMES_CENTER));

			for (int xx = getXTile() - 1; xx > getXTile() - strength; xx--)
			{
				if (!explode(xx, getYTile()))
					break;
				else
					map.Add(new Flame(map, xx, getYTile(), (xx > getXTile() - strength + 1) ? Flame.FLAMES_HORIZONTAL : Flame.FLAMES_LEFT_EDGE));
			}

			for (int xx = getXTile() + 1; xx < getXTile() + strength; xx++)
			{
				if (!explode(xx, getYTile()))
					break;
				else
					map.Add(new Flame(map, xx, getYTile(), (xx < getXTile() + strength - 1) ? Flame.FLAMES_HORIZONTAL : Flame.FLAMES_RIGHT_EDGE));
			}

			for (int yy = getYTile() - 1; yy > getYTile() - strength; yy--)
			{
				if (!explode(getXTile(), yy))
					break;
				else
					map.Add(new Flame(map, getXTile(), yy, (yy > getYTile() - strength + 1) ? Flame.FLAMES_VERTICAL : Flame.FLAMES_TOP_EDGE));
			}

			for (int yy = getYTile() + 1; yy < getYTile() + strength; yy++)
			{
				if (!explode(getXTile(), yy))
					break;
				else
					map.Add(new Flame(map, getXTile(), yy, (yy < getYTile() + strength - 1) ? Flame.FLAMES_VERTICAL : Flame.FLAMES_BOT_EDGE));
			}

			OnDeath();
		}
	}

	public void Render(Graphics2D g)
	{
		img.Render(g, x, y);
		g.drawString(String.valueOf(seconds - (System.currentTimeMillis() - creationTime) / 1000), x, y);
	}

	public void OnHurt()
	{
		this.seconds = 0;
	}

	public void OnSpawn()
	{
		this.seconds = 3;
		this.strength = player.strength;
	}

	public void OnDeath()
	{
		super.OnDeath();
		this.player.bombs++;
		Sound.explosion.play();
	}

	public void OnTouch(Moveable m, int side)
	{
		if (moving || !((Player) m).canKickBombs)
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
