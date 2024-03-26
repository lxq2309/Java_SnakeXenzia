package snakexenzia.view;

import javax.swing.JFrame;
import snakexenzia.models.Game;

public class GameWindows extends JFrame
{
	public GameWindows(Game game, GameGraphics graphics)
	{
		this.add(graphics);

		this.setTitle("Snake Xenzia");
		this.setSize(Game.WIDTH + Game.DIMENSION * 2 - 5, Game.HEIGHT + Game.DIMENSION * 3 - 5);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}
}
