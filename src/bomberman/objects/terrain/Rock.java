package bomberman.objects.terrain;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bomberman.Bomberman;
import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Moveable;
import bomberman.powerups.Bombup;
import bomberman.powerups.Flameup;
import bomberman.powerups.Kickup;
import bomberman.powerups.Speedup;
import bomberman.resource.Image;

/**
 * A destructible rock on the map. Will be randomly placed on the map by map parse. Extends map object.
 * 
 * @see bomberman.map.MapObject
 * @see bomberman.map.MapParse
 * 
 */
public class Rock extends MapObject
{
	public static boolean	spawn_items	= true;
	public static boolean	send_items	= false;

	public boolean			broken, ready_to_die;
	private Animation		ani;

	public Rock(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);

		BufferedImage temp = Image.read(map.path + "/rock.png");

		img = temp.getSubimage(0, 0, Map.TILE_SIZE, Map.TILE_SIZE);
		ani = new Animation(temp, 1, 0, Map.TILE_SIZE, Map.TILE_SIZE, 6, 100, false);
	}

	/**
	 * Updates the rock. Doesn't do anything unless the rock is currently blowing up. The rock will be removed after finishing the animation.
	 */
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

	/**
	 * Renders the rock by rendering either drawing it or drawing the animation for explosion.
	 */
	public void Render(Graphics2D g)
	{
		if (!broken)
			draw(g, 0, 0);
		else
			ani.Render(g, x, y);
	}

	/**
	 * Method from map object. Will return true if the rock is solid and false if the rock was destroyed and isn't removed yet.
	 */
	public boolean isBlocking(Moveable m)
	{
		return broken ? false : true;
	}

	/**
	 * Map object method. When a rock got hit by an explosion, the boolean "broken" is set true. Next update method will start the animation and remove the rock later.
	 */
	public void OnHurt()
	{
		broken = true;
	}

	/**
	 * Map object method. Calls up super method, removing the object from the map. After that, there are items placed onto the map or the exit is shown.
	 */
	public void Die()
	{
		super.Die();

		MapObject[] ol = map.getObjectsOnTile(getXTile(), getYTile());

		for (MapObject o : ol)
		{
			if (o instanceof Exit)
				return;
		}

		if (spawn_items && Math.random() < map.droprate)
		{
			int powerup = (int) (Math.random() * 4 + 1);

			switch (powerup)
			{
				case 1:
					map.Add(new Bombup(map, getXTile(), getYTile()));
					if (send_items)
						Bomberman.getGame().connection.getOut().println("I B " + (getXTile() + getYTile() * 17)+" ");
					break;
				case 2:
					map.Add(new Flameup(map, getXTile(), getYTile()));
					if (send_items)
						Bomberman.getGame().connection.getOut().println("I F " + (getXTile() + getYTile() * 17)+" ");
					break;
				case 3:
					map.Add(new Kickup(map, getXTile(), getYTile()));
					if (send_items)
						Bomberman.getGame().connection.getOut().println("I K " + (getXTile() + getYTile() * 17)+" ");
					break;
				case 4:
					map.Add(new Speedup(map, getXTile(), getYTile()));
					if (send_items)
						Bomberman.getGame().connection.getOut().println("I S " + (getXTile() + getYTile() * 17)+" ");
					break;
			}
		}
	}
}
