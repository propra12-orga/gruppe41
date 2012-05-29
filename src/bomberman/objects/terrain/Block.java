package bomberman.objects.terrain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.map.Map;
import bomberman.map.MapObject;

public class Block extends MapObject
{
	public Block(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y, false, true, true);

		try
		{
			img = ImageIO.read(new File("data/sprites/block.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
