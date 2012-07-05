package bomberman.resource;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MediaTracker;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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
				// img = ImageIO.read(Bomberman.applet.getCodeBase(), path); // only works with a signed applet or with eclipse's applet viewer

				MediaTracker tracker = new MediaTracker(Bomberman.applet);
				java.awt.Image image = Bomberman.applet.getImage(Bomberman.applet.getCodeBase(), path);
				tracker.addImage(image, 0);

				try
				{
					tracker.waitForAll();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				img = toBufferedImage(image);
			}
		}
		catch (IOException e)
		{
			System.out.println("An Exception occurred when trying to read \"" + path + "\" as an " + (Bomberman.isApplication ? "Application" : "Applet") + ".");
		}

		return img;
	}

	public static boolean hasAlpha(java.awt.Image image)
	{
		if (image instanceof BufferedImage)
			return ((BufferedImage) image).getColorModel().hasAlpha();

		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);

		try
		{
			pg.grabPixels();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		return pg.getColorModel().hasAlpha();
	}

	public static BufferedImage toBufferedImage(java.awt.Image image)
	{
		if (image instanceof BufferedImage)
			return (BufferedImage) image;

		image = new ImageIcon(image).getImage();

		boolean hasAlpha = hasAlpha(image);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		GraphicsDevice gs = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gs.getDefaultConfiguration();
		BufferedImage img = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), hasAlpha ? Transparency.TRANSLUCENT : Transparency.BITMASK);
		// img = new BufferedImage(image.getWidth(null), image.getHeight(null), hasAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);

		Graphics g = img.createGraphics();

		g.drawImage(image, 0, 0, null);
		g.dispose();

		return img;
	}
}
