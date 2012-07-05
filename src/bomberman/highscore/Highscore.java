package bomberman.highscore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import bomberman.Bomberman;

public class Highscore
{
	private static final String	APPLET_ERR_MSG	= "Highscore-related functions are ignored when being run in an applet.";	// Applets do not have io-permissions

	public static final int		HSS				= 5;
	public static int[]			points			= new int[HSS];
	public static String[]		names			= new String[HSS];
	public static String		playerName		= "USER";

	public static void initHighscore()
	{
		for (int i = 0; i < HSS; i++)
		{
			points[i] = 0;
			names[i] = "";
		}
	}

	public static void resetHighscore()
	{
		if (Bomberman.isApplet)
		{
			System.out.println(APPLET_ERR_MSG);
			return;
		}

		File high = new File("data/highscore");
		if (high.exists())
		{
			try
			{
				FileWriter out = new FileWriter(high);
				for (int i = 0; i < HSS; i++)
				{
					out.write("0 " + playerName + " ");
				}
				out.flush();
				out.close();
			}
			catch (IOException e)
			{
				System.err.println();
			}
		}
	}

	public static void readHighscore()
	{
		if (Bomberman.isApplet)
		{
			System.out.println(APPLET_ERR_MSG);
			return;
		}

		File high = new File("data/highscore");
		Scanner highread;
		if (high.exists())
		{
			try
			{
				highread = new Scanner(new InputStreamReader(new FileInputStream(high)));
				for (int i = 0; i < HSS; i++)
				{
					points[i] = highread.nextInt();
					names[i] = highread.next();
				}
			}
			catch (IOException | NoSuchElementException e)
			{
				System.err.println(e);
				String name = playerName;
				for (int i = 0; i < 5; i++)
				{
					points[i] = 1000;
					names[i] = name;
				}
			}
		}
		else
		{
			try
			{
				high.createNewFile();
			}
			catch (IOException e)
			{
				System.err.println(e);
			}
		}
	}

	public static void writeHighscore(long millis)
	{
		if (Bomberman.isApplet)
		{
			System.out.println(APPLET_ERR_MSG);
			return;
		}

		boolean changed = false;
		int point = 120 - (int) (millis / 1000);
		int position = HSS;
		for (int i = HSS - 1; i >= 0; i--)
		{
			if (point > points[i])
				position--;
		}
		if (position < HSS - 1)
		{
			for (int i = HSS - 1; i > position; i--)
			{
				points[i] = points[i - 1];
				names[i] = names[i - 1];
			}
			points[position] = point;
			names[position] = playerName;
			changed = true;
		}
		if (changed)
		{
			try
			{
				File high = new File("data/highscore");
				FileWriter out = new FileWriter(high);
				for (int i = 0; i < HSS; i++)
				{
					out.write(points[i] + " " + names[i] + " ");
				}
				out.flush();
				out.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void readName()
	{
		if (Bomberman.isApplet)
		{
			System.out.println(APPLET_ERR_MSG);
			return;
		}

		try
		{
			playerName = (new Scanner(new InputStreamReader(new FileInputStream(new File("data/name"))))).next();
		}
		catch (IOException | NoSuchElementException e)
		{
			playerName = "USER";
		}
	}

	public static void writeName()
	{
		if (Bomberman.isApplet)
		{
			System.out.println(APPLET_ERR_MSG);
			return;
		}

		File name = new File("data/name");
		try
		{
			if (!name.exists())
			{
				name.createNewFile();
			}
			FileWriter out = new FileWriter(name);
			out.write(playerName);
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			System.err.println();
		}

	}

}
