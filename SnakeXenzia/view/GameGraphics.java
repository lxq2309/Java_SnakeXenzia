package snakexenzia.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import snakexenzia.controller.MyKeyAdapter;
import snakexenzia.models.Food;
import snakexenzia.models.Game;
import snakexenzia.models.PlayerScore;
import snakexenzia.models.Snake;
import snakexenzia.utils.PlayerScoreRepository;

public class GameGraphics extends JPanel implements ActionListener
{
	public Timer timer = new Timer(Game.DELAY, this);
	public String state;

	private Snake snake;
	private Food food;
	private Game game;
	MyKeyAdapter myKeyAdapter;

	public GameGraphics(Game g)
	{
		timer.start();
		state = "MENU START";

		game = g;
		snake = g.getSnake();
		food = g.getFood();
		myKeyAdapter = new MyKeyAdapter(this, g);

		// add a keyListner
		this.addKeyListener(myKeyAdapter);
		this.setFocusable(true);
		this.setBackground(Color.black);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		switch (state)
		{
		case "MENU START":
			drawMenuStart(g2d);
			break;
		case "ENTER YOUR NAME":
			drawEnterYourName(g2d);
			break;
		case "LIST HIGHEST SCORE":
			drawListHighestScore(g2d);
			break;
		case "MENU SETTING":
			drawMenuSetting(g2d);
			break;
		case "RUNNING":
			drawSnake(g2d);
			if (!game.isEnableThroughWall())
			{
				drawBorder(g2d);
			}
			drawScore(g2d);
			drawFood(g2d);
			break;
		case "PAUSE":
			drawSnake(g2d);
			if (!game.isEnableThroughWall())
			{
				drawBorder(g2d);
			}
			drawScore(g2d);
			drawFood(g2d);
			gamePause(g2d);
			break;
		case "END":
			gameOver(g2d);
			timer.stop();
			break;

		}
	}

	public void drawBorder(Graphics2D g2d)
	{
		g2d.setColor(food.getColor().darker());
		// left border
		g2d.fill3DRect(0, 0, Game.DIMENSION, Game.HEIGHT, true);
		// right border
		g2d.fill3DRect(Game.WIDTH, 0, Game.DIMENSION, Game.WIDTH, true);
		// top border
		g2d.fill3DRect(0, 0, Game.WIDTH + Game.DIMENSION, Game.DIMENSION, true);
		// bottom border;
		g2d.fill3DRect(0, Game.HEIGHT, Game.WIDTH + Game.DIMENSION, Game.DIMENSION, true);
	}

	public void drawMenuStart(Graphics2D g2d)
	{
		g2d.setColor(Color.cyan);
		g2d.setFont(new Font("UTM Copperplate", Font.BOLD, 40));
		g2d.drawString("SNAKE XENZIA", Game.WIDTH / 2 - 150, 50);

		g2d.setFont(new Font("Consolas", Font.PLAIN, 14));
		g2d.setColor(Color.white);
		g2d.drawString("ENTER: Start game", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 - 90);
		g2d.drawString("L: List score", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 - 60);
		g2d.drawString("S: Setting", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 - 30);
		g2d.drawString("Q: Quit game", Game.WIDTH / 2 - 60, Game.HEIGHT / 2);
	}

	public void drawListHighestScore(Graphics2D g2d)
	{
		List<PlayerScore> playerScores = null;
		try
		{
			playerScores = PlayerScoreRepository.readTop5HighestScore();
		}
		catch (ClassNotFoundException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int i = 5;
		for (PlayerScore playerScore : playerScores)
		{
			g2d.setColor(Color.cyan);
			g2d.setFont(new Font("UTM Copperplate", Font.BOLD, 40));
			g2d.drawString("LIST HIGHEST SCORE", 90, 50);

			g2d.setFont(new Font("Consolas", Font.PLAIN, 14));
			g2d.setColor(Color.white);
			g2d.drawString(5 - i + 1 + ", " + playerScore.getName() + " " + playerScore.getScore(), Game.WIDTH / 2 - 50,
					Game.HEIGHT / 2 - 30 * i);
			i--;

			if (i == 0)
			{
				break;
			}
		}

		g2d.setFont(new Font("Consolas", Font.PLAIN, 12));
		g2d.drawString("ESC: Back to menu start", Game.WIDTH - 140, 100);

	}

	public void drawMenuSetting(Graphics2D g2d)
	{
		g2d.setColor(Color.cyan);
		g2d.setFont(new Font("UTM Copperplate", Font.BOLD, 40));
		g2d.drawString("MENU SETTING", Game.WIDTH / 2 - 150, 50);

		g2d.setFont(new Font("Consolas", Font.PLAIN, 14));
		g2d.setColor(Color.white);
		g2d.drawString("Speed: ", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 - 60);

		int i = 1;
		for (int speed : Game.SPEED)
		{
			if (speed == Game.DELAY)
			{
				g2d.setColor(Color.red);
				g2d.setFont(new Font("Consolas", Font.BOLD, 14));
			}
			else
			{
				g2d.setColor(Color.white);
				g2d.setFont(new Font("Consolas", Font.PLAIN, 14));
			}
			g2d.drawString(i + "", Game.WIDTH / 2 + 20 * i, Game.HEIGHT / 2 - 60);
			i++;
		}

		g2d.setColor(Color.white);
		g2d.setFont(new Font("Consolas", Font.PLAIN, 14));
		g2d.drawString("Through wall: ", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 - 30);
		if (game.isEnableThroughWall())
		{
			g2d.setColor(Color.red);
		}
		g2d.drawString("[E]nable", Game.WIDTH / 2 + 60, Game.HEIGHT / 2 - 30);
		g2d.setColor(Color.white);

		if (!game.isEnableThroughWall())
		{
			g2d.setColor(Color.red);
		}
		g2d.drawString("[D]isable", Game.WIDTH / 2 + 180, Game.HEIGHT / 2 - 30);
		g2d.setColor(Color.white);

		g2d.setFont(new Font("Consolas", Font.PLAIN, 12));
		g2d.drawString("ESC: Back to menu start", Game.WIDTH - 140, 100);
	}

	public void drawEnterYourName(Graphics2D g2d)
	{
		g2d.setFont(new Font("Consolas", Font.PLAIN, 14));
		g2d.setColor(Color.white);
		g2d.drawString("Enter your name: " + game.getName() + "_", 100, Game.HEIGHT / 2 - 60);
		g2d.drawString("[ENTER] ", Game.WIDTH - 140, Game.HEIGHT / 2 - 60);

		g2d.setFont(new Font("Consolas", Font.PLAIN, 12));
		g2d.drawString("ESC: Back to menu start", Game.WIDTH - 140, 100);
	}

	public void drawScore(Graphics2D g2d)
	{
		g2d.setColor(Color.white);
		g2d.setFont(new Font("Consolas", Font.PLAIN, 12));
		g2d.drawString("Score: " + snake.getScore(), 20, 15);
		g2d.drawString("P: Pause game", Game.WIDTH - 80, 15);
	}

	public void drawFood(Graphics2D g2d)
	{
		g2d.setColor(food.getColor());
		g2d.fillOval(food.getX(), food.getY(), Game.DIMENSION, Game.DIMENSION);
	}

	public void drawSnake(Graphics2D g2d)
	{
		int i = 0;
		for (Rectangle rectangle : snake.getBody())
		{
			g2d.setColor(snake.getColors().get(i));
			if (rectangle == snake.getHead())
			{
				g2d.fillRoundRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 18, 18);
			}
			else
			{
				g2d.fillRoundRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 10, 10);
			}

			i++;
		}
	}

	public void gamePause(Graphics2D g2d)
	{

		g2d.setColor(Color.white);
		g2d.setFont(new Font("Consolas", Font.PLAIN, 40));
		g2d.drawString("PAUSE", Game.WIDTH / 2 - 40, Game.HEIGHT / 2 - 80);
		g2d.setFont(new Font("Consolas", Font.PLAIN, 20));
		g2d.drawString("P: Continue", Game.WIDTH / 2 - 50, Game.HEIGHT / 2 - 20);
		g2d.drawString("ESC: Back to menu start", Game.WIDTH / 2 - 110, Game.HEIGHT - 40);
	}

	public void gameOver(Graphics2D g2d)
	{
		g2d.setColor(Color.red);
		g2d.setFont(new Font("UTM Aircona", Font.PLAIN, 40));
		g2d.drawString("GAME OVER", Game.WIDTH / 2 - 100, Game.HEIGHT / 2 - 80);

		g2d.setColor(Color.white);
		g2d.setFont(new Font("Consolas", Font.PLAIN, 20));
		String strScore = String.valueOf(snake.getScore());
		g2d.drawString("Score: " + strScore, Game.WIDTH / 2 - (30 + strScore.length()), Game.HEIGHT / 2 - 20);

		g2d.setFont(new Font("Consolas", Font.PLAIN, 12));
		g2d.drawString("ESC: Back to menu start", Game.WIDTH - 140, 20);
		g2d.drawString("R: Restart game", Game.WIDTH - 140, 40);
		g2d.drawString("Q: Quit game", Game.WIDTH - 140, 60);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
		game.update();
	}

}