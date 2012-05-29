package bomberman.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;

import bomberman.game.Game;
import bomberman.objects.Moveable;
import bomberman.objects.terrain.*;

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

	int							ground_tiles_count;
	public int[][]				ground				= new int[TILES_COUNT_Y][TILES_COUNT_X];
	public Vector<MapObject>	objects				= new Vector<MapObject>();

	public Map(String mapname)
	{
		try
		{
			String path = "data/maps/" + mapname;

			tile_ground = ImageIO.read(new File(path + "/tile_ground.png"));
			border_top = ImageIO.read(new File(path + "/border_top.png"));
			border_bottom = ImageIO.read(new File(path + "/border_bot.png"));
			border_left = ImageIO.read(new File(path + "/border_left.png"));
			border_right = ImageIO.read(new File(path + "/border_right.png"));
			tile_ground = ImageIO.read(new File(path + "/tile_ground.png"));
			

			FileInputStream map = new FileInputStream(new File(path + "/" + mapname + ".map"));

			// ground_tiles_count = map.read();

			for (int y = 0; y < TILES_COUNT_Y; y++)
			{
				for (int x = 0; x < TILES_COUNT_X; x++)
				{
					ground[y][x] = map.read();
				}
			}

			int map_fields_num = map.read();
			for (int i = 0; i < map_fields_num; i++)
			{
				// objects.add(new MapObject(map.read(), map.read(), map.read(),
				// ((map.read() == 0) ? false : true), (((map.read() == 0) ?
				// false : true))));
			}
			for (int y = 1; y < 11; y += 2)
			{
				for (int x = 1; x < 17; x += 2)
				{
					Add(new Block(this, x, y));
				}
			}
		
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void Add(MapObject o)
	{
		objects.add(o);
	}

	public void Remove(MapObject o)
	{
		objects.remove(o);
	}

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

	public void touchObjectsOnTile(int tile_x, int tile_y, Moveable m, int side)
	{
		MapObject[] ol = getObjectsOnTile(tile_x, tile_y);

		for (MapObject o : ol)
		{
			o.OnTouch(m, side);
		}
	}

	public MapObject isNewPosGood(MapObject p, int x, int y)
	{
		for (MapObject o : objects)
		{
			// else if (getTileByX(self.x) == getTileByX(o.x) &&
			// getTileByY(self.y) == getTileByY(o.y))
			// else if (p.getXTile() == o.getXTile() && p.getYTile() ==
			// o.getYTile())
			if (o == p || !o.blocking)
				continue;
			else if (p.x + p.width > o.x && o.x + o.width > p.x && p.y + p.height > o.y && o.y + o.height > p.y)
				continue;
			else if (x + p.width > o.x && o.x + o.width > x && y + p.height > o.y && o.y + o.height > y)
				return o;
		}
		return null;
	}

	public void Update()
	{
		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).Update();
		}
	}

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
							BORDER_LEFT_WIDTH + (x * TILE_SIZE),
							BORDER_TOP_HEIGHT + (y * TILE_SIZE),
							BORDER_LEFT_WIDTH + (x * TILE_SIZE) + TILE_SIZE,
							BORDER_TOP_HEIGHT + (y * TILE_SIZE) + TILE_SIZE,
							(ground[y][x] * TILE_SIZE),
							0,
							(ground[y][x] * TILE_SIZE) + TILE_SIZE,
							TILE_SIZE,
							null);
			}
		}
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < Game.HEIGHT; j++)
			{
				for (MapObject o : objects)
				{
					if (o.y == j - 1 && o.render_priority == i + 1)
					{
						o.Render(g);
					}
				}
			}
		}
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
