package bomberman.objects;

import bomberman.map.Map;

public class Mob extends Moveable
{
	public Mob(Map map, int tile_x, int tile_y)
	{
		super(map, tile_x, tile_y, true, false, false);
		this.render_priority = 3;
	}
}
