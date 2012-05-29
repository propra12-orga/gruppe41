package bomberman.objects.terrain;

	import java.io.File;
	import java.io.IOException;

	import javax.imageio.ImageIO;

	import bomberman.map.Map;
import bomberman.map.MapObject;
import bomberman.objects.Moveable;
import bomberman.players.Player;

public class Exit extends MapObject
{
	
		public Exit(Map map, int tile_x, int tile_y)
		{
			super(map, tile_x, tile_y, false, false, true);

			try
			{
				img = ImageIO.read(new File("data/maps/basic/exit.png"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}}
			public void OnTouch(Moveable m, int side)
			{
				if (m instanceof Player)
				{
					((Player) m).hasreachedexit = true;
					OnHurt();
		}
	}
}


