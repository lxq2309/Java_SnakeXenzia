package snakexenzia.models;

import java.awt.Color;
import java.awt.Rectangle;

public class Food
{
	private int x;
	private int y;
	Color color;

	public Food(Snake snake)
	{
		this.randomSpawn(snake);
	}

	public void randomSpawn(Snake snake)
	{
		boolean onSnake = true;
		while (onSnake)
		{
			x = ((int) (Math.random() * (Game.WIDTH - Game.DIMENSION)) + Game.DIMENSION) / Game.DIMENSION
					* Game.DIMENSION;
			y = ((int) (Math.random() * (Game.WIDTH - Game.DIMENSION)) + Game.DIMENSION) / Game.DIMENSION
					* Game.DIMENSION;
			onSnake = false;

			for (Rectangle rect : snake.getBody())
			{
				if (rect.x == x && rect.y == y)
				{
					onSnake = true;
				}
			}
		}

		color = new Color(((int) (Math.random() * 255) + 50) % 255, ((int) (Math.random() * 255) + 50) % 255,
				((int) (Math.random() * 255) + 50) % 255);
		snake.getColors().add(color.brighter());

		System.out.println("Food is spawn at (" + x + ", " + y + ")");
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

}
