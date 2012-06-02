package bomberman.objects.terrain;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Moveable;
import bomberman.powerups.Bombup;
import bomberman.powerups.Flameup;
import bomberman.powerups.Kickup;
import bomberman.powerups.Speedup;

public class Rock extends MapObject
{
	public boolean		broken, ready_to_die;
	private Animation	ani;

	public Rock(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);

		try
		{
			BufferedImage temp = ImageIO.read(new File(map.path + "/rock.png"));

			img = temp.getSubimage(0, 0, Map.TILE_SIZE, Map.TILE_SIZE);
			ani = new Animation(temp, 1, 0, Map.TILE_SIZE, Map.TILE_SIZE, 6, 100, false);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void Update()
	{
		if (broken)
		{
			ani.Update();

			if (ani.current == 0 && ready_to_die)
				Die();
			else if (ani.current == 5)
				ready_to_die = true;
		}
	}

	public void Render(Graphics2D g)
	{
		if (!broken)
			draw(g, 0, 0);
		else
			ani.Render(g, x, y);
	}

	public boolean isBlocking(Moveable m)
	{
		return broken ? false : true;
	}

	public void OnHurt()
	{
		broken = true;
	}

	public void Die()
	{
		super.Die();

		MapObject[] ol = map.getObjectsOnTile(getXTile(), getYTile());

		for (MapObject o : ol)
		{
			if (o instanceof Exit)
				return;
		}

		if (Math.random() < map.droprate)
		{
			int powerup = (int) (Math.random() * 4 + 1);

			switch (powerup)
			{
				case 1:
					map.Add(new Bombup(map, getXTile(), getYTile()));
					break;
				case 2:
					map.Add(new Flameup(map, getXTile(), getYTile()));
					break;
				case 3:
					map.Add(new Kickup(map, getXTile(), getYTile()));
					break;
				case 4:
					map.Add(new Speedup(map, getXTile(), getYTile()));
					break;
			}
		}
	}
}
