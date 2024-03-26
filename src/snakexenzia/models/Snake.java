package snakexenzia.models;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Snake
{
	private ArrayList<Rectangle> body;
	private Rectangle head;
	private String direction; // NOTHING, UP, DOWN, LEFT, RIGHT
	private List<Color> colors;

	public Snake()
	{
		body = new ArrayList<>();
		colors = new ArrayList<>();

		Rectangle temp = new Rectangle(Game.DIMENSION, Game.DIMENSION);
		temp.setLocation(Game.WIDTH / 2, Game.HEIGHT / 2);
		body.add(temp);
		colors.add(Color.red);

		temp = new Rectangle(Game.DIMENSION, Game.DIMENSION);
		temp.setLocation((Game.WIDTH / 2 - 1 * Game.DIMENSION), (Game.HEIGHT / 2));
		body.add(temp);
		colors.add(Color.green);

		head = body.get(0);

		direction = "NOTHING";
	}

	public void move()
	{
		if (direction != "NOTHING")
		{

			Rectangle temp = new Rectangle(Game.DIMENSION, Game.DIMENSION);

			if (direction == "UP")
			{
				temp.setLocation(head.x, head.y - Game.DIMENSION);
			}
			else if (direction == "DOWN")
			{
				temp.setLocation(head.x, head.y + Game.DIMENSION);
			}
			else if (direction == "LEFT")
			{
				temp.setLocation(head.x - Game.DIMENSION, head.y);
			}
			else
			{
				temp.setLocation(head.x + Game.DIMENSION, head.y);
			}

			body.add(0, temp);
			head = temp;
			body.remove(body.size() - 1);
			System.out.println("Snake move to (" + head.x + ", " + head.y + ")");
		}
	}

	public void grow()
	{
		Rectangle temp = new Rectangle(Game.DIMENSION, Game.DIMENSION);

		if (direction == "UP")
		{
			temp.setLocation(head.x, head.y - Game.DIMENSION);
		}
		else if (direction == "DOWN")
		{
			temp.setLocation(head.x, head.y + Game.DIMENSION);
		}
		else if (direction == "LEFT")
		{
			temp.setLocation(head.x - Game.DIMENSION, head.y);
		}
		else
		{
			temp.setLocation(head.x + Game.DIMENSION, head.y);
		}

		body.add(0, temp);
		head = temp;
	}

	public ArrayList<Rectangle> getBody()
	{
		return body;
	}

	public void setBody(ArrayList<Rectangle> body)
	{
		this.body = body;
	}

	public Rectangle getHead()
	{
		return head;
	}

	public void setHead(Rectangle head)
	{
		this.head = head;
	}

	public String getDirection()
	{
		return direction;
	}
	
	public void setDirection(String d)
	{
		direction = d;
	}

	public int getScore()
	{
		return body.size() - 2;
	}

	public List<Color> getColors()
	{
		return colors;
	}

	public void setColors(List<Color> colors)
	{
		this.colors = colors;
	}

	public void up()
	{
		direction = "UP";
	}

	public void down()
	{
		direction = "DOWN";
	}

	public void left()
	{
		direction = "LEFT";
	}

	public void right()
	{
		direction = "RIGHT";
	}
}