package bomberman.animation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Animation
{
	public BufferedImage	sheet;
	public int				width, height, start, current, num, interval;
	public boolean			back_and_forth, forward;
	private long			timer;

	public Animation(BufferedImage sheet, int sprite_x, int sprite_y, int width, int height, int num_of_sprites, int interval, boolean back_and_forth, int start)
	{	
		this.sheet = sheet.getSubimage(sprite_x * width, sprite_y * height, width * num_of_sprites, height);
		this.width = width;
		this.height = height;
		this.num = num_of_sprites;
		this.interval = interval;
		this.back_and_forth = back_and_forth;
		this.forward = true;
		this.start = start - 1;
		this.current = this.start;
		this.timer = System.currentTimeMillis();
	}

	public Animation(BufferedImage sheet, int sprite_x, int sprite_y, int width, int height, int num_of_sprites, int interval, boolean back_and_forth)
	{
		this(sheet, sprite_x, sprite_y, width, height, num_of_sprites, interval, back_and_forth, 1);
	}

	public void Reset()
	{
		this.current = this.start;
		this.timer = System.currentTimeMillis();
	}
	
	public void Update()
	{
		if (System.currentTimeMillis() - timer > interval)
		{
			timer = System.currentTimeMillis();

			if (forward || !back_and_forth)
				current++;
			else
				current--;

			if (current > num - 1)
			{
				if (back_and_forth)
				{
					current -= 2;
					forward = false;
				}
				else
					current -= num;
			}
			else if (current < 0)
			{
				current += 2;
				forward = true;
			}
		}
	}

	public void Render(Graphics2D g, int x, int y)
	{
		g.drawImage(sheet, x, y, x + width, y + height, current * width, 0, current * width + width, height, null);
	}
}
