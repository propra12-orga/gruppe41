package bomberman.objects;

import bomberman.map.Map;
import bomberman.map.MapObject;

public class Moveable extends MapObject
{

	public static final int	FACE_DIRECTION_NONE		= -1;
	public static final int	FACE_DIRECTION_UP		= 0;
	public static final int	FACE_DIRECTION_DOWN		= 1;
	public static final int	FACE_DIRECTION_LEFT		= 2;
	public static final int	FACE_DIRECTION_RIGHT	= 3;

	public int				movement_speed			= 1;
	public int				dir						= FACE_DIRECTION_DOWN;

	public Moveable(Map map, int tile_x, int tile_y, boolean vulnerable, boolean blocking, boolean ignoring)
	{
		super(map, tile_x, tile_y, vulnerable, blocking, ignoring);
	}

	public boolean MoveX(int offs)
	{
		MapObject o = map.isNewPosGood(this, x + offs, y);

		if (o == null || !o.blocking || this.ignoring)
		{
			if (offs < 0)
			{
				if (x + offs < Map.X_OFFS)
				{
					x = Map.X_OFFS;
					return false;
				}
			}
			else if (offs > 0)
			{
				if (x + offs + width > Map.X2_OFFS)
				{
					x = Map.X2_OFFS - width;
					return false;
				}
			}
			else
				return false;

			x += offs;
			return true;
		}
		else
		{
			if (offs < 0)
				x = o.x + o.width;
			else if (offs > 0)
				x = o.x - width;

			return false;
		}
	}

	public boolean MoveY(int offs)
	{
		MapObject o = map.isNewPosGood(this, x, y + offs);

		if (o == null || !o.blocking || this.ignoring)
		{
			if (offs < 0)
			{
				if (y + offs < Map.Y_OFFS)
				{
					y = Map.Y_OFFS;
					return false;
				}
			}
			else if (offs > 0)
			{
				if (y + offs + height > Map.Y2_OFFS)
				{
					y = Map.Y2_OFFS - height;
					return false;
				}
			}
			else
				return false;

			y += offs;
			return true;
		}
		else
		{
			if (offs < 0)
				y = o.y + o.height;
			else if (offs > 0)
				y = o.y - height;

			return false;
		}
	}

	public int Move(int offs_x, int offs_y)
	{
		int ret = FACE_DIRECTION_NONE;
		
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

		if (ret != FACE_DIRECTION_NONE)
		{
			map.touchObjectsOnTile(getXTile(), getYTile(), this, ret);
		}

		return ret;
	}
}
