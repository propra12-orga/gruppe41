package bomberman.animation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Instances of this class create animations by using several buffered images.
 * 
 */
public class Animation
{
	public BufferedImage	sheet;
	public int				width, height, start, num, interval;
	/**
	 * The number of the image that has to be rendered at the moment. Will be changed by update and used by render method.
	 */
	public int				current;
	public boolean			back_and_forth, forward;
	private long			timer;

	/**
	 * Creates a new animation with the following information:
	 * 
	 * @param sheet - The image to read.
	 * @param sprite_x - x position of the first image.
	 * @param sprite_y - y position of the first image.
	 * @param width
	 * @param height
	 * @param num_of_sprites
	 * @param interval - Duration of a single image.
	 * @param back_and_forth - If this is true, the animation will run back after finishing.
	 * @param start
	 */
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

	/**
	 * The animation is restarted from the beginning.
	 */
	public void Reset()
	{
		this.current = this.start;
		this.timer = System.currentTimeMillis();
	}

	/**
	 * Update method for an animation, changes the integer "current" if necessary.
	 * 
	 * @see #current
	 */
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

	/**
	 * Render method. Draws the current image of the animation to the map position. The selected image is not switched, this will be done by update method.
	 * 
	 * @param g - Graphics used for painting.
	 * @param x - position on the map.
	 * @param y - position on the map.
	 * @see #Update()
	 */
	public void Render(Graphics2D g, int x, int y)
	{
		g.drawImage(sheet, x, y, x + width, y + height, current * width, 0, current * width + width, height, null);
	}
}
