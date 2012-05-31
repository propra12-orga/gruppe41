package bomberman.objects.terrain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Moveable;
import bomberman.powerups.Bombup;
import bomberman.powerups.Flameup;
import bomberman.powerups.Kickup;
import bomberman.powerups.Speedup;

public class Rock extends MapObject
{
	public Rock(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);

		try
		{
			img = ImageIO.read(new File(map.path + "/rock.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isBlocking(Moveable m)
	{
		return true;
	}

	public void OnHurt()
	{
		Die();
	}

	public void Die()
	{
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

		super.Die();
	}
}
