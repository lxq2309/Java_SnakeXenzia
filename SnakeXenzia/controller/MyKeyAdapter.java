package snakexenzia.controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import snakexenzia.models.Game;
import snakexenzia.view.GameGraphics;

public class MyKeyAdapter implements KeyListener
{
	GameGraphics graphics;
	Game game;

	public MyKeyAdapter(GameGraphics graphics, Game game)
	{
		this.graphics = graphics;
		this.game = game;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// KHONG BIET VIET GI O DAY
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (graphics.state)
		{
		case "MENU START":
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_ENTER:
				game.start();
				break;
			case KeyEvent.VK_L:
				game.goToListScore();
				break;
			case KeyEvent.VK_S:
				game.goToMenuSetting();
				break;
			case KeyEvent.VK_Q:
				System.exit(0);
				break;
			}
			break;
		case "ENTER YOUR NAME":
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_ESCAPE:
				game.goToMenuStart();
				break;

			case KeyEvent.VK_ENTER:
				game.running();
				break;
			case KeyEvent.VK_BACK_SPACE:
				if (game.getName().length() != 0)
				{
					game.setName(game.getName().substring(0, game.getName().length() - 1));
				}

				break;
			default:
				if ('a' <= e.getKeyChar() && e.getKeyChar() <= 'z' || 'A' <= e.getKeyChar() && e.getKeyChar() <= 'Z'
						|| e.getKeyChar() == ' ')
				{
					game.setName(game.getName() + e.getKeyChar());
				}
				break;
			}
			break;

		case "LIST HIGHEST SCORE":
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_ESCAPE:
				game.goToMenuStart();
				break;
			}
			break;
		case "MENU SETTING":
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_ESCAPE:
				try
				{
					game.saveSetting();
					System.out.println("Save setting done !");
				}
				catch (IOException e1)
				{
					System.err.println("Error !");
				}
				game.goToMenuStart();
				break;
			case KeyEvent.VK_1:
				Game.DELAY = Game.SPEED[0];
				break;
			case KeyEvent.VK_2:
				Game.DELAY = Game.SPEED[1];

				break;
			case KeyEvent.VK_3:
				Game.DELAY = Game.SPEED[2];
				break;
			case KeyEvent.VK_E:
				game.setEnableThroughWall(true);
				break;
			case KeyEvent.VK_D:
				game.setEnableThroughWall(false);
				break;
			}
			graphics.timer.setDelay(Game.DELAY);
			break;

		case "RUNNING":
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_W:
				if (game.getSnake().getDirection() != "DOWN")
				{
					game.getSnake().up();
				}
				else
				{
					Rectangle tmp = game.getSnake().getHead();
					Color color = game.getSnake().getColors().get(0);
					game.getSnake().setHead(game.getSnake().getBody().get(game.getSnake().getBody().size() - 1));
					game.getSnake().getColors().set(0, game.getSnake().getColors().get(game.getSnake().getBody().size() - 1));
					game.getSnake().getBody().set(game.getSnake().getBody().size() - 1, tmp);
					game.getSnake().getColors().set(game.getSnake().getBody().size() - 1, color);
					game.getSnake().up();

				}
				break;

			case KeyEvent.VK_S:
				if (game.getSnake().getDirection() != "UP")
				{
					game.getSnake().down();
				}
				else
				{
					Rectangle tmp = game.getSnake().getHead();
					Color color = game.getSnake().getColors().get(0);
					game.getSnake().setHead(game.getSnake().getBody().get(game.getSnake().getBody().size() - 1));
					game.getSnake().getColors().set(0, game.getSnake().getColors().get(game.getSnake().getBody().size() - 1));
					game.getSnake().getBody().set(game.getSnake().getBody().size() - 1, tmp);
					game.getSnake().getColors().set(game.getSnake().getBody().size() - 1, color);
					game.getSnake().down();
					;
				}

				break;

			case KeyEvent.VK_A:
				if (game.getSnake().getDirection() != "RIGHT")
				{
					game.getSnake().left();
				}
				else
				{
					Rectangle tmp = game.getSnake().getHead();
					Color color = game.getSnake().getColors().get(0);
					game.getSnake().setHead(game.getSnake().getBody().get(game.getSnake().getBody().size() - 1));
					game.getSnake().getColors().set(0, game.getSnake().getColors().get(game.getSnake().getBody().size() - 1));
					game.getSnake().getBody().set(game.getSnake().getBody().size() - 1, tmp);
					game.getSnake().getColors().set(game.getSnake().getBody().size() - 1, color);
					game.getSnake().left();
				}
				break;

			case KeyEvent.VK_D:
				if (game.getSnake().getDirection() != "LEFT")
				{
					game.getSnake().right();
				}
				else
				{
					Rectangle tmp = game.getSnake().getHead();
					Color color = game.getSnake().getColors().get(0);
					game.getSnake().setHead(game.getSnake().getBody().get(game.getSnake().getBody().size() - 1));
					game.getSnake().getColors().set(0, game.getSnake().getColors().get(game.getSnake().getBody().size() - 1));
					game.getSnake().getBody().set(game.getSnake().getBody().size() - 1, tmp);
					game.getSnake().getColors().set(game.getSnake().getBody().size() - 1, color);
					game.getSnake().right();
				}
				break;
			case KeyEvent.VK_P:
				game.pause(true);
				break;
			}
			break;

		case "PAUSE":
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_P:
				game.pause(false);
				break;
			case KeyEvent.VK_ESCAPE:
				game = new Game();
				break;

			}
			break;

		case "END":
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_ESCAPE:
				game = new Game();
				break;
			case KeyEvent.VK_Q:
				System.exit(0);
				break;
			case KeyEvent.VK_R:
				String name = game.getName();
				game = new Game();
				game.setName(name);
				game.running();
				break;
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// KHONG BIET VIET GI O DAY
	}

}
