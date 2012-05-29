package bomberman.objects.bomb;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Moveable;

public class Flame extends MapObject
{
	public final static int	FLAMES_CENTER		= 0;
	public final static int	FLAMES_HORIZONTAL	= 1;
	public final static int	FLAMES_VERTICAL		= 2;
	public final static int	FLAMES_TOP_EDGE		= 5;
	public final static int	FLAMES_BOT_EDGE		= 6;
	public final static int	FLAMES_LEFT_EDGE	= 3;
	public final static int	FLAMES_RIGHT_EDGE	= 4;

	public final static int	STAYTIME			= 300;

	public Flame(Map map, int tile_x, int tile_y, int type)
	{
		super(map, tile_x, tile_y, false, false, true);

		try
		{
			img = ImageIO.read(new File("data/sprites/flames.png")).getSubimage(type * Map.TILE_SIZE, 0, Map.TILE_SIZE, Map.TILE_SIZE);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void Update()
	{
		if (System.currentTimeMillis() - creationTime > STAYTIME)
		{
			OnDeath();
		}
	}

	public void OnTouch(Moveable m, int side)
	{
		m.OnHurt();
	}
}
