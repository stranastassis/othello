


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 405;
	public static final int HEIGHT = 480;
	public static int turn = 0;
	
	private boolean running;
	private Thread thread;
	
	private Menu menu;
	private Image image;
	private Board board;
	private MiniMax minimax;

	public  static boolean played;
	public  static boolean game_ended;
	
	public static int xx;
	public static int yy;
	public static int lastPlayer;
	
	private boolean ingame;
	private boolean playerTurn;
	private boolean done;
	private boolean black_wins;
	private boolean white_wins;
	private boolean checkP;
	private boolean checkC;
	private boolean hasMoveP; 
	private boolean hasMoveC;
	private boolean draw;
	
	protected static int playerColor;
	protected static int cpuColor;
	private int black_score;
	private int white_score;
	private int depth;
	
	/* constructor */
	public Game()
	{
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setMaximumSize(new Dimension(WIDTH,HEIGHT));
		setMinimumSize(new Dimension(WIDTH,HEIGHT));
		setBackground(Color.LIGHT_GRAY);
		
		running = false;
		
		menu = new Menu();
		board = new Board();
				
		initGame();
	}
	
	/* initialize game variables */
	private void initGame() 
	{
		String msg;
		
		ingame = false;
		playerTurn = false;
		played = false;
		done = false;
		black_wins = false;
		white_wins = false;
		game_ended = false;
		
		checkP = false;
		checkC = false;
		hasMoveP = false;
		hasMoveC = false;
		draw = false;
		
		lastPlayer = 2; 
		
		black_score = 2;
		white_score = 2;
		
		this.addMouseListener(new Mouse(this));
		
		try 
		{
			image = ImageIO.read(new File("othello.jpg"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		msg = JOptionPane.showInputDialog("Give the difficulty level","4");
		if(msg != null && !msg.equals(""))
		{
			depth = Integer.parseInt( msg );
		}
	}

	@Override
	public void run() 
	{
		while(running) //game loop
		{
			/* repaint once when game is started */
			if(!done && ingame)
			{
				repaint();
				done = true;
			}
			
			if(ingame)
			{
				try 
				{
					checkState();
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		
		}
		
		stop();
	}
	
	protected synchronized void start()
	{
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop()
	{
		if(!running)
			return;
		
		running = false;
		
		try 
		{
			thread.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		System.exit(1);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		doDrawing(g);
	}

	/* all drawing activity */
	private void doDrawing(Graphics g) 
	{
		if(ingame)
		{
			//draw background image
			g.drawImage(image,0, 0, null);
			board.drawBoard(g);
			menu.drawScores(g,black_score,white_score);
			if(draw)//draw the valid moves for player
				board.displayValidMoves(1,2,g);
		}
		else if(game_ended)
		{
			menu.drawWinnerScreen(g,black_wins,white_wins);
		}
		else
		{
			menu.drawIntroScreen(g);
		}
		
	}
	
	public void checkState() throws InterruptedException
	{		
		if(playerTurn)
		{
			//check valid moves
			if(!checkP)
			{
				checkP = true;
				
				if(board.checkForValidMoves(playerColor,cpuColor))
				{
					Thread.sleep(1000);
					System.out.println("there are valid moves available for player");
					//-----------------------------------------------------------//
					draw = true;
					repaint();
					//-----------------------------------------------------------//
					hasMoveP  = true;
					
				}
				else
				{
					hasMoveP = false;
					checkP = false;
					playerTurn = false;
					checkEndGame(hasMoveP,hasMoveC);
				}
			}
			if(hasMoveP)
			{	
				
				if(played)//if player clicked 
				{
					//check for valid move
					if(board.checkValidMove(yy, xx, playerColor, cpuColor, false))
					{
						draw = false;
						
						board.updateBoard(yy, xx, playerColor);
						
						calculateScore();
						
						playerTurn = false;
						played = false;
						checkP = false;
					}
				}
			}
		}
		if(!playerTurn)
		{
			//check valid moves
			if(!checkC)
			{
				checkC = true;
				
				if(board.checkForValidMoves(cpuColor, playerColor))
				{
					System.out.println("there are valid moves available for cpu");
					hasMoveC = true;
				}
				else
				{
					hasMoveC = false;
					checkC = false;
					playerTurn = true;
					checkEndGame(hasMoveP,hasMoveC);
				}
			}
			if(hasMoveC)
			{
				System.out.println("Cpu plays");
				
				Thread.sleep(2000);
				
				Move cpuMove = minimax.makeMove(board);
				board.checkValidMove(cpuMove.getRow(), cpuMove.getCol(), cpuColor, playerColor, false);
				board.updateBoard(cpuMove.getRow(), cpuMove.getCol(), cpuColor);
				board.updateLastMove(cpuMove.getRow(), cpuMove.getCol());
				turn++;
				calculateScore();
								
				checkC = false;
				playerTurn = true;
			}

		}
	}
	
	/* if at any time cpu and player have no more valid moves
	 * that means that the game has ended
	 */
	private void checkEndGame(boolean p,boolean c) throws InterruptedException
	{
		if((!p) && (!c))
		{
			Thread.sleep(2000);
			ingame = false;
			game_ended = true;
			findWinner(black_score,white_score);
			repaint();
		}			
	}
	
	private void findWinner(int b_score,int w_score)
	{
		if(b_score > w_score)
			black_wins = true;
		if(w_score > b_score)
			white_wins = true;	
	}
	
	private void calculateScore()
	{
		black_score = board.scoreCalculator(1);
		white_score = board.scoreCalculator(2);
		
		repaint();
	}
	

	public void initializeCpu()
	{
		System.out.println("initialize cpu");
		minimax = new MiniMax(depth,playerColor,cpuColor);
	}
	
	/* called when player chooses color */
	public void init(int player,int cpu,boolean ingame,boolean playerTurn)
	{
		this.playerColor = player;
		this.cpuColor = cpu;
		initializeCpu();
		this.ingame = ingame;
		this.playerTurn = playerTurn;
	}
	
	/* check if player clicks within the board borders during the game */
	public boolean checkBorder(int x,int y)
	{
		return (x > 0 && x < 400 && y > 0 && y < 400);
	}
	
	///////////////////////////// setters & getters /////////////////////////////
	public boolean getIngame()
	{
		return this.ingame;
	}
	
	public boolean getPlayerTurn()
	{
		return this.playerTurn;
	}
		
}
