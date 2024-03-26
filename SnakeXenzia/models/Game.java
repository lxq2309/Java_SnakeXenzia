package snakexenzia.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import snakexenzia.utils.PlayerScoreRepository;
import snakexenzia.view.GameGraphics;
import snakexenzia.view.GameWindows;

public class Game
{
	private Snake snake;
	private Food food;
	private String name;
	private GameGraphics graphics;
	private boolean enableThroughWall = false;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public static final int DIMENSION = 20;
	public static final int SPEED[] = new int[] { 100, 75, 50 };
	public static int DELAY = SPEED[2];

	public Game()
	{
		loadSetting();
		System.out.println("Load setting done !");

		snake = new Snake();
		food = new Food(snake);
		name = "";

		graphics = new GameGraphics(this);
		new GameWindows(this, graphics);
	}

	public void start()
	{
		graphics.state = "ENTER YOUR NAME";
	}

	public void running()
	{
		graphics.state = "RUNNING";
	}

	public void pause(boolean ps)
	{
		if (ps)
		{
			graphics.state = "PAUSE";
		}
		else
		{
			graphics.state = "RUNNING";
		}
	}

	public void goToListScore()
	{
		graphics.state = "LIST HIGHEST SCORE";
	}

	public void goToMenuStart()
	{
		graphics.state = "MENU START";
	}

	public void goToMenuSetting()
	{
		graphics.state = "MENU SETTING";
	}

	public void update()
	{
		if (graphics.state == "RUNNING")
		{
			if (checkFoodCollision())
			{
				food.randomSpawn(snake);
				snake.grow();
			}
			else if (checkWallCollision() || checkSelfCollision())
			{
				graphics.state = "END";
			}
			else
			{
				snake.move();
			}
		}
		if (graphics.state == "END")
		{
			try
			{
				PlayerScoreRepository.add(new PlayerScore(name, snake.getScore()));
			}
			catch (ClassNotFoundException | SQLException e)
			{
				System.err.println("Error");
			}
			System.out.println("Add (" + name + " " + ", " + snake.getScore() + ") to database" );
		}
	}

	private boolean checkWallCollision()
	{
		if (enableThroughWall)
		{
			throughWall();
			return (snake.getHead().x < 0 || snake.getHead().x > WIDTH || snake.getHead().y < 0
					|| snake.getHead().y > HEIGHT);
		}
		else
		{
			return (snake.getHead().x <= 0 || snake.getHead().x >= WIDTH || snake.getHead().y <= 0
					|| snake.getHead().y >= HEIGHT);
		}

	}

	private void throughWall()
	{
		if (snake.getHead().x < 0)
		{
			snake.getHead().x = WIDTH;
		}
		else if (snake.getHead().x > WIDTH)
		{
			snake.getHead().x = 0;
		}
		else if (snake.getHead().y < 0)
		{
			snake.getHead().y = HEIGHT;
		}
		else if (snake.getHead().y > HEIGHT)
		{
			snake.getHead().y = 0;
		}
	}

	private boolean checkFoodCollision()
	{
		return (snake.getHead().x == food.getX() && snake.getHead().y == food.getY());
	}

	private boolean checkSelfCollision()
	{
		for (int i = 1; i < snake.getBody().size(); i++)
		{
			if (snake.getHead().x == snake.getBody().get(i).x && snake.getHead().y == snake.getBody().get(i).y)
			{
				return true;
			}
		}
		return false;
	}

	public Snake getSnake()
	{
		return snake;
	}

	public void setSnake(Snake snake)
	{
		this.snake = snake;
	}

	public Food getFood()
	{
		return food;
	}

	public void setFood(Food food)
	{
		this.food = food;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isEnableThroughWall()
	{
		return enableThroughWall;
	}

	public void setEnableThroughWall(boolean enableThroughWall)
	{
		this.enableThroughWall = enableThroughWall;
	}

	public void saveSetting() throws IOException
	{
		String path = "res/setting.txt";
		FileWriter fw = null;
		BufferedWriter bw = null;
		try
		{
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(DELAY + "\n" + enableThroughWall);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			if (bw != null)
				bw.close();
			if (fw != null)
				fw.close();
		}
	}

	public void loadSetting()
	{
		String content = "";
		try
		{
			byte[] bytes = Files.readAllBytes(Paths.get("res/setting.txt"));
			content = new String(bytes);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		String[] settings = content.split("\n");
		DELAY = Integer.valueOf(settings[0]);
		enableThroughWall = Boolean.valueOf(settings[1]);
	}

}
