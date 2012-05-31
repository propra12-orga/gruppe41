package bomberman.objects.bomb;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Moveable;

public class Flame extends MapObject
{
	public final static int	FLAMES_MID			= 0;
	public final static int	FLAMES_LEFT			= 1;
	public final static int	FLAMES_LEFT_EDGE	= 2;
	public final static int	FLAMES_TOP			= 3;
	public final static int	FLAMES_TOP_EDGE		= 4;
	public final static int	FLAMES_RIGHT		= 5;
	public final static int	FLAMES_RIGHT_EDGE	= 6;
	public final static int	FLAMES_BOT			= 7;
	public final static int	FLAMES_BOT_EDGE		= 8;

	private boolean			ready_to_die;

	Animation				img;

	public Flame(Map map, int tile_x, int tile_y, int type)
	{
		super(map, tile_x, tile_y);

		try
		{
			img = new Animation(ImageIO.read(new File("data/sprites/flames.png")), 0, type, Map.TILE_SIZE, Map.TILE_SIZE, 12, 50, false);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void Update()
	{
		img.Update();

		if (img.current == 0 && ready_to_die)
			Die();
		else if (img.current == 11)
			ready_to_die = true;
	}

	public void Render(Graphics2D g)
	{
		img.Render(g, x, y);
	}

	public void OnTouch(Moveable m, int side)
	{
		m.OnHurt();
	}
}
