package bomberman.objects;

import bomberman.map.Map;
import bomberman.map.MapObject;

/**
 * Movable objects on the map. This is the super class of mob and subclass of map object. TODO: explain methods
 * 
 * @see bomberman.map.MapObject
 * @see bomberman.objects.Mob
 * 
 */
public class Moveable extends MapObject
{

	public static final int	FACE_DIRECTION_NONE		= -1;
	public static final int	FACE_DIRECTION_DOWN		= 0;
	public static final int	FACE_DIRECTION_UP		= 1;
	public static final int	FACE_DIRECTION_LEFT		= 2;
	public static final int	FACE_DIRECTION_RIGHT	= 3;

	public int				movement_speed			= 1;
	public int				dir						= FACE_DIRECTION_DOWN;
	public boolean			ignoring				= false;

	/**
	 * Constructor, using super constructor map object, used by sub constructor bomb/mop
	 */
	public Moveable(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y);
	}

	private boolean changeX(int new_value)
	{
		if (x != new_value)
		{
			x = new_value;
			return false;
		}
		else
			return false;
	}

	private boolean MoveX(int offs)
	{
		MapObject[] ol = map.getBlockingObjects(this, x + offs, y);

		int wl = -1, wr = -1;

		for (MapObject o : ol)
		{
			if (o.x + o.width > wl || wl == -1)
				wl = o.x + o.width;
			if (o.x < wr || wr == -1)
				wr = o.x;
		}

		if (ol.length == 0 || this.ignoring)
		{
			if (offs < 0)
			{
				if (x + offs < Map.X_OFFS)
					return changeX(Map.X_OFFS);
			}
			else if (offs > 0)
			{
				if (x + offs + width > Map.X2_OFFS)
					return changeX(Map.X2_OFFS - width);
			}
			else
				return false;

			x += offs;
			return true;
		}
		else
		{
			for (int yy = y - (movement_speed - 1); yy < y + movement_speed; yy++)
			{
				if (yy != y && map.getBlockingObjects(this, x + offs, yy).length == 0)
				{
					y = yy;
					return MoveX(offs);
				}
			}

			if (offs < 0)
				return changeX(wl);
			else if (offs > 0)
				return changeX(wr - width);

			return false;
		}
	}

	private boolean changeY(int new_value)
	{
		if (y != new_value)
		{
			y = new_value;
			return false;
		}
		else
			return false;
	}

	private boolean MoveY(int offs)
	{
		MapObject[] ol = map.getBlockingObjects(this, x, y + offs);

		int wt = -1, wb = -1;

		for (MapObject o : ol)
		{
			if (o.y + o.height > wt || wt == -1)
				wt = o.y + o.height;
			if (o.y < wb || wb == -1)
				wb = o.y;
		}

		if (ol.length == 0 || this.ignoring)
		{
			if (offs < 0)
			{
				if (y + offs < Map.Y_OFFS)
					return changeY(Map.Y_OFFS);
			}
			else if (offs > 0)
			{
				if (y + offs + height > Map.Y2_OFFS)
					return changeY(Map.Y2_OFFS - height);
			}
			else
				return false;

			y += offs;
			return true;
		}
		else
		{
			for (int xx = x - (movement_speed - 1); xx < x + movement_speed; xx++)
			{
				if (xx != x && map.getBlockingObjects(this, xx, y + offs).length == 0)
				{
					x = xx;
					return MoveY(offs);
				}
			}

			if (offs < 0)
				return changeY(wt);
			else if (offs > 0)
				return changeY(wb - height);

			return false;
		}
	}

	/**
	 * Moves a moveable map-object on the x- and/or y-axis. Returns the direction the object is facing afterwards and additionally, whether a full step was possible (if not it means the object would have moved e.g. 4 pixels, but couldn't do so as a wall would have blocked it and
	 * therefore it moved only 3 pixels)
	 * 
	 * @param offs_x
	 * @param offs_y
	 * @return
	 */
	public int[] Move(int offs_x, int offs_y)
	{
		int ret = FACE_DIRECTION_NONE;

		if (Math.abs(offs_x) > 1 || Math.abs(offs_y) > 1)
			return new int[] { ret, 0 };

		int tile_x_prev = getXTile(), tile_y_prev = getYTile();
		int x_prev = x, y_prev = y;

		offs_x = offs_x * movement_speed;
		offs_y = offs_y * movement_speed;

		if (offs_x != 0 && offs_y != 0)
		{
			if (MoveX(offs_x))
			{
				if (offs_x < 0)
					ret = FACE_DIRECTION_LEFT;
				else
					ret = FACE_DIRECTION_RIGHT;
			}
			else
			{
				if (offs_y < 0)
					ret = FACE_DIRECTION_UP;
				else
					ret = FACE_DIRECTION_DOWN;
			}
			MoveY(offs_y);
		}
		else if (offs_x != 0)
		{
			MoveX(offs_x);

			if (offs_x < 0)
				ret = FACE_DIRECTION_LEFT;
			else
				ret = FACE_DIRECTION_RIGHT;
		}
		else if (offs_y != 0)
		{
			MoveY(offs_y);

			if (offs_y < 0)
				ret = FACE_DIRECTION_UP;
			else
				ret = FACE_DIRECTION_DOWN;
		}

		boolean full_step = true;

		if (ret != FACE_DIRECTION_NONE)
		{
			int tile_x = getXTile(), tile_y = getYTile();

			if (tile_x != tile_x_prev || tile_y != tile_y_prev)
				map.touchObjectsOnTile(tile_x, tile_y, this, ret);

			if (x != x_prev + offs_x && y == y_prev + offs_y)
				full_step = false;
		}

		return new int[] { ret, full_step ? 1 : 0 };
	}
}
