package highscore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Highscore
{
	public static final int	HSS		= 5;
	public static int[]		points	= new int[HSS];
	public static String[]	names	= new String[HSS];

	public static void resetHighscore()
	{
		File high = new File("data/highscore");
		if (high.exists())
			high.delete();
	}

	public static void readHighscore()
	{
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
					names[i] = highread.nextLine();
				}
			}
			catch (IOException e)
			{
				System.err.println(e);
			}
			catch (NoSuchElementException e1)
			{
				System.err.println(e1);
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

	public static void writeHighscore(long highscore)
	{
		File hsfile = new File("highscore.txt");
		boolean nhs = false; // new highscore?
		Scanner in = null;
		FileWriter out = null;
		String hs[] = new String[HSS]; // the highscore-file
		int linesread = -1;

		try
		{
			in = new Scanner(hsfile);
			for (int i = 0; i < HSS; i++)
			{ // Save File in hs
				if (in.hasNextLine())
				{
					linesread++;
					hs[i] = in.nextLine();
				}
				else
					hs[i] = (char) 255 + ""; // Beacause otherwise Arrays.sort() doesn't work

			}
			if (linesread == HSS - 1)// Highscore-List full
			{
				for (int i = 0; i < HSS; i++)
				{ // Check: new highscore?
					if (hs[i] != null)
					{
						Scanner tmp = new Scanner(hs[i]);
						if (tmp.hasNextLong())
						{
							if (tmp.nextLong() > highscore)
							{// New Highscore
								hs[linesread] = highscore + " " + new Date();
								nhs = true;
								break;
							}
						}
					}
				}

			}
			// Highscore-List not full add any highscore
			else
			{
				hs[++linesread] = highscore + " " + new Date();
				nhs = true;
			}
			Arrays.sort(hs);

		}
		catch (FileNotFoundException e)
		{
			// No HS-File exists
			hs[0] = highscore + " " + new Date();
			linesread = 0;
			nhs = true;

		}
		finally
		{
			if (in != null)
			{
				in.close();
			}
		}
		// Write new Highscore-File
		if (nhs)
		{
			if (hsfile.exists())
				if (!hsfile.delete()) return;
			try
			{
				out = new FileWriter(hsfile);
				for (int i = 0; i <= linesread; i++)
				{
					out.write(hs[i] + System.getProperty("line.separator"));
				}

			}
			catch (FileNotFoundException e)
			{
				// No HS-File could created
				return;
			}
			catch (IOException e)
			{
				return;
			}
			finally
			{
				if (out != null)
				{
					try
					{
						out.close();
					}
					catch (IOException e)
					{
						return;
					}
				}
			}
		}
	}

}

// try
// {
// Desktop.getDesktop().open(new File("highscore.txt"));
// }
// catch (Exception /* IOException, URISyntaxException */e)
// {
// e.printStackTrace();
// }
