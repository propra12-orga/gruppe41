package bomberman.powerups;

import java.awt.Graphics2D;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.sound.Sound;

public class Powerup extends MapObject
{
	protected static final int	ANI_COUNT		= 2;
	protected static final int	ANI_INTERVAL	= 300;

	protected Animation			img;

	public Powerup(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);
	}

	public void Update()
	{
		img.Update();
	}

	public void Render(Graphics2D g)
	{
		img.Render(g, x, y);
	}

	public void OnHurt()
	{
		Die();
	}

	public void pickedUp()
	{
		Sound.pickup.play();
		Die();
	}
}
