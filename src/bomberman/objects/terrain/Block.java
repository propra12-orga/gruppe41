package bomberman.objects.terrain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Moveable;

public class Block extends MapObject
{
	public Block(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);

		try
		{
			img = ImageIO.read(new File(map.path + "/block.png"));
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
}
