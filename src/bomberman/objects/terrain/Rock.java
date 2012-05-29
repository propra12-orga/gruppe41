package bomberman.objects.terrain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.map.Map;
import bomberman.map.MapObject;

public class Rock extends MapObject
{
	public Rock(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y, true, true, true);

		try
		{
			img = ImageIO.read(new File("data/sprites/rock.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
