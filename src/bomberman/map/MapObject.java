package bomberman.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import bomberman.objects.Mob;
import bomberman.objects.Moveable;

public abstract class MapObject
{
	public static final int	WIDTH			= Map.TILE_SIZE;
	public static final int	HEIGHT			= Map.TILE_SIZE;

	public static final int	TOUCHED_TOP		= Mob.FACE_DIRECTION_DOWN;
	public static final int	TOUCHED_BOT		= Mob.FACE_DIRECTION_UP;
	public static final int	TOUCHED_LEFT	= Mob.FACE_DIRECTION_RIGHT;
	public static final int	TOUCHED_RIGHT	= Mob.FACE_DIRECTION_LEFT;

	public int				x, y, width, height, render_priority;
	public long				creationTime;
	
	public BufferedImage	img;
	protected Map			map;

	public MapObject(Map map, int tile_x, int tile_y)
	{
		this.x = map.getXByTile(tile_x);
		this.y = map.getYByTile(tile_y);
		this.map = map;
		this.width = WIDTH;
		this.height = HEIGHT;
		this.render_priority = 1;
		this.creationTime = System.currentTimeMillis();
	}

	public void Update()
	{

	}
	
	public void draw(Graphics2D g, int sprite_x, int sprite_y)
	{
		draw(g, x, y, sprite_x, sprite_y);
	}

	public void draw(Graphics2D g, int x, int y, int sprite_x, int sprite_y)
	{
		g.drawImage(img,
					x,
					y,
					x + WIDTH,
					y + HEIGHT,
					sprite_x * WIDTH,
					sprite_y * HEIGHT,
					sprite_x * WIDTH + WIDTH,
					sprite_y * HEIGHT + HEIGHT,
					null);
	}

	public void Render(Graphics2D g)
	{
		draw(g, 0, 0);
	}

	public int getXTile()
	{
		return map.getTileByX(x + (width / 2));
	}

	public int getYTile()
	{
		return map.getTileByY(y + (height / 2));
	}
	
	public boolean isBlocking(Moveable m)
	{
		return false;
	}

	public void OnTouch(Moveable m, int side)
	{

	}
	
	public void OnCollide(Moveable m, int side)
	{

	}

	public void OnHurt()
	{
		
	}

	public void Die()
	{
		map.Remove(this);
	}
}