package bomberman.powerups;

import java.awt.Graphics2D;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.resource.Sound;

/**
 * Abstract superclass for power-ups. Has several subclasses.
 * 
 * @see bomberman.map.MapObject
 * @see bomberman.powerups.Bombup
 * @see bomberman.powerups.Kickup
 * @see bomberman.powerups.Speedup
 * @see bomberman.powerups.Flameup
 */
public abstract class Powerup extends MapObject
{
	protected static final int	ANI_COUNT		= 2;
	protected static final int	ANI_INTERVAL	= 300;
	/**
	 * The animation shown whenever an instance of a subclass is shown on the map.
	 */
	protected Animation			img;

	/**
	 * Constructor will be used by subclasses. Calls up super constructor in map object class.
	 * 
	 * @param map - The map to be added to.
	 * @param tile_x - ...
	 * @param tile_y - ... position
	 */
	public Powerup(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);
	}

	/**
	 * Update method. Only changes the animation image, nothing else happens.
	 */
	public void Update()
	{
		img.Update();
	}

	/**
	 * Renders the animation.
	 * 
	 * @see bomberman.animation.Animation#Render(Graphics2D, int, int)
	 */
	public void Render(Graphics2D g)
	{
		img.Render(g, x, y);
	}

	/**
	 * Map object method. If a power-up is hit by an explosion, it vanishes.
	 */
	public void OnHurt()
	{
		Die();
	}

	/**
	 * Method will be used when a player picks up the item. The item is removed and the pickup sound will be played. This method is called up by the onTouch method of the subclasses.
	 */
	public void pickedUp()
	{
		Sound.pickup.play();
		Die();
	}
}
