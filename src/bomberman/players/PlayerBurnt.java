package bomberman.players;

import java.awt.Graphics2D;

import bomberman.animation.Animation;
import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.resource.Image;

/**
 * An object of this type shows a dying player. Does not work in the game.
 * 
 * @see bomberman.map.MapObject
 * 
 */
public class PlayerBurnt extends MapObject
{
	public static final int	WIDTH	= 46;
	public static final int	HEIGHT	= 56;

	private boolean			ready_to_die;

	Animation				img;

	public PlayerBurnt(Map map, int x, int y)
	{
		super(map, 0, 0);

		this.x = x;
		this.y = y;

		this.render_priority = 3;

		img = new Animation(Image.read("data/sprites/burnt.png"), 0, 0, WIDTH, HEIGHT, 5, 100, false);
	}

	public void Update()
	{
		img.Update();

		if (img.current == 0 && ready_to_die)
			Die();
		else if (img.current == 4)
			ready_to_die = true;
	}

	public void Render(Graphics2D g)
	{
		img.Render(g, x + ((Player.WIDTH - WIDTH) / 2), y + Map.TILE_SIZE - Player.HEIGHT + (Player.HEIGHT - HEIGHT));
	}
}
