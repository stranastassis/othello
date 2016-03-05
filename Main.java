


import javax.swing.JFrame;

public class Main extends JFrame
{
	private static final long serialVersionUID = 1L;
	private Game game;
	
	public final String TITLE = "OTHELLO";

	public Main()
	{		
		game = new Game();
		add(game);
		pack();
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		game.start();
	}
	
	public static void main(String args[])
	{
		new Main();
	}

}
