package bomberman.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import bomberman.Bomberman;
import bomberman.game.Game;
import bomberman.objects.Moveable;
import bomberman.players.Player;
import bomberman.resource.Image;

/**
 * Class for the map in the core game. The terrain is saved in ground[][], all objects on the map are saved in a vector.
 * 
 */
public class Map
{
	public static final int		TILE_SIZE			= 40;
	public static final int		TILES_COUNT_X		= 17;
	public static final int		TILES_COUNT_Y		= 11;
	private static final int	BORDER_LEFT_WIDTH	= 60;
	private static final int	BORDER_RIGHT_WIDTH	= 60;
	private static final int	BORDER_TOP_HEIGHT	= 110;
	private static final int	BORDER_BOT_HEIGHT	= 50;
	public static final int		X_OFFS				= BORDER_LEFT_WIDTH;
	public static final int		Y_OFFS				= BORDER_TOP_HEIGHT;
	public static final int		X2_OFFS				= Game.WIDTH - BORDER_RIGHT_WIDTH;
	public static final int		Y2_OFFS				= Game.HEIGHT - BORDER_BOT_HEIGHT;

	public BufferedImage		tile_ground;
	public BufferedImage		border_left;
	public BufferedImage		border_top;
	public BufferedImage		border_right;
	public BufferedImage		border_bottom;

	public int					num_of_players		= 0;
	/**
	 * The chance of setting an item when destroying a wall.
	 */
	public double				droprate;

	public String				name, path;

	int							ground_tiles_count;
	/**
	 * The information about the ground is saved here.
	 */
	public int[][]				ground				= new int[TILES_COUNT_Y][TILES_COUNT_X];
	/**
	 * A vector containing all map objects (map object is an abstract class). Players are included.
	 */
	public Vector<MapObject>	objects				= new Vector<MapObject>();

	/**
	 * Creates a new map, using the MapParse class to get a map from an XML file.
	 * 
	 * @param name - Map name.
	 */
	public Map(String name)
	{
		this.name = name;
		this.path = "data/maps/basic";

		tile_ground = Image.read(path + "/tile_ground.png");
		border_top = Image.read(path + "/border_top.png");
		border_bottom = Image.read(path + "/border_bot.png");
		border_left = Image.read(path + "/border_left.png");
		border_right = Image.read(path + "/border_right.png");

		new MapParse(this, (Bomberman.isApplet ? Bomberman.applet.getCodeBase() : "") + path + "/" + name + ".xml");
	}

	/**
	 * Player number 0-3, not 1-4 like shown in the menu. TODO - net tested 
	 * @param number
	 * @return
	 */
	public Player getPlayer(int number){
		Player ret = null;
		for(MapObject o : objects){
			if(o instanceof Player){
				if(((Player) o).player_id==number)
					ret = (Player) o;
			}
		}
		return ret;
	}
	
	/**
	 * Updates the map. The map itself won't change much, but the map objects are updated.
	 */
	public void Update()
	{
		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).Update();
		}
	}

	/**
	 * Renders the map borders, the map ground and at last all map objects.
	 * 
	 * @param g - Graphics to paint.
	 */
	public void Render(Graphics2D g)
	{
		g.drawImage(border_top, 0, 0, null);
		g.drawImage(border_bottom, 0, Game.HEIGHT - BORDER_BOT_HEIGHT, null);
		g.drawImage(border_left, 0, 0, null);
		g.drawImage(border_right, Game.WIDTH - BORDER_RIGHT_WIDTH, 0, null);

		for (int y = 0; y < TILES_COUNT_Y; y++)
		{
			for (int x = 0; x < TILES_COUNT_X; x++)
			{
				g.drawImage(tile_ground,
							BORDER_LEFT_WIDTH + (x * TILE_SIZE), BORDER_TOP_HEIGHT + (y * TILE_SIZE),
							BORDER_LEFT_WIDTH + (x * TILE_SIZE) + TILE_SIZE, BORDER_TOP_HEIGHT + (y * TILE_SIZE) + TILE_SIZE,
							(ground[y][x] * TILE_SIZE), 0,
							(ground[y][x] * TILE_SIZE) + TILE_SIZE, TILE_SIZE,
							null);
			}
		}

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < Game.HEIGHT; j++)
			{
				for (MapObject o : objects)
				{
					if (o.y == j - 1 && o.render_priority == i)
					{
						o.Render(g);
					}
				}
			}
		}
	}

	/**
	 * Adds a new map object.
	 * 
	 * @param o - The map object to be added to the game.
	 */
	public void Add(MapObject o)
	{
		objects.add(o);
	}

	/**
	 * Deletes an existing map object.
	 * 
	 * @param o - The object to be removed.
	 */
	public void Remove(MapObject o)
	{
		objects.remove(o);
	}

	/**
	 * Getter method.
	 * 
	 * @param tile_x - The x position on the map.
	 * @param tile_y - The y position on the map.
	 * @return ... an array of those map objects currently standing at the position specified by x and y.
	 */
	public MapObject[] getObjectsOnTile(int tile_x, int tile_y)
	{
		List<MapObject> l = new ArrayList<MapObject>();

		for (MapObject o : objects)
		{
			if (o.getXTile() == tile_x && o.getYTile() == tile_y)
				l.add(o);
		}

		return l.toArray(new MapObject[l.size()]);
	}

	/**
	 * TODO
	 * 
	 * @param tile_x - The x position on the map.
	 * @param tile_y - The y position on the map.
	 * @param m - The player starting the method.
	 * @param side - ?
	 */
	public void touchObjectsOnTile(int tile_x, int tile_y, Moveable m, int side)
	{
		MapObject[] ol = getObjectsOnTile(tile_x, tile_y);

		for (MapObject o : ol)
		{
			o.OnTouch(m, side);
		}
	}

	/**
	 * Getter method. TODO
	 * 
	 * @param m
	 * @param tile_x - The x position on the map.
	 * @param tile_y - The y position on the map.
	 * @return
	 */
	public MapObject[] getBlockingObjects(Moveable m, int x, int y)
	{
		List<MapObject> l = new ArrayList<MapObject>();

		for (MapObject o : objects)
		{
			if ((o == m) || (m.x + m.width > o.x) && (o.x + o.width > m.x) && (m.y + m.height > o.y) && (o.y + o.height > m.y))
				continue;
			else if ((x + m.width > o.x) && (o.x + o.width > x) && (y + m.height > o.y) && (o.y + o.height > y))
			{
				int side;

				if (m.x != x)
				{
					if (m.x < x)
						side = MapObject.TOUCHED_LEFT;
					else
						side = MapObject.TOUCHED_RIGHT;
				}
				else
				{
					if (m.y < y)
						side = MapObject.TOUCHED_TOP;
					else
						side = MapObject.TOUCHED_BOT;
				}

				o.OnCollide(m, side);

				if (o.isBlocking(m))
					l.add(o);
			}
		}

		return l.toArray(new MapObject[l.size()]);
	}

	public int getXByTile(int tile_x)
	{
		return X_OFFS + (tile_x * TILE_SIZE);
	}

	public int getYByTile(int tile_y)
	{
		return Y_OFFS + (tile_y * TILE_SIZE);
	}

	public int getTileByX(int x)
	{
		return (x - X_OFFS) / TILE_SIZE;
	}

	public int getTileByY(int y)
	{
		return (y - Y_OFFS) / TILE_SIZE;
	}
}
