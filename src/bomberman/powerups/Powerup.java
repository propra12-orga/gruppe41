package bomberman.powerups;

import java.awt.Graphics2D;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.map.MapObject;

public class Powerup extends MapObject
{
	protected Animation	img;

	public Powerup(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y, true, false, true);
	}

	public void Update()
	{
		img.Update();
	}

	public void Render(Graphics2D g)
	{
		img.Render(g, x, y);
	}
}
