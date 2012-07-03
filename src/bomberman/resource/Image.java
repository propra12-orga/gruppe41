package bomberman.resource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import bomberman.Bomberman;

public class Image
{
	public static BufferedImage read(String path)
	{
		BufferedImage img = null;

		try
		{
			if (Bomberman.isApplication)
			{
				img = ImageIO.read(new File(path));
			}
			else
			{
				img = ImageIO.read(new URL(Bomberman.appletDir + path));
			}
		}
		catch (IOException e)
		{
			System.out.println("An Exception occurred when trying to read \"" + path + "\" as an " + (Bomberman.isApplication ? "Application" : "Applet") + ".");
		}

		return img;
	}
}
