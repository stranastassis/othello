


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu 
{
	public Rectangle blackChecker = new Rectangle(125,255,50,50);
	public Rectangle whiteChecker = new Rectangle(255,255,50,50);
	
	public void drawIntroScreen(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		
		//OTHELLO
		Font name = new Font("menu",Font.BOLD,48);
		g.setFont(name);
		g.setColor(Color.BLACK);
		g.drawString("OTHELLO", 100, 50);
		
		//black always play first
		Font black = new Font("menu",Font.BOLD,20);
		g.setFont(black);
		g.setColor(Color.BLACK);
		g.drawString("Black always play first!!!",100, 125);
		
		//choose color
		Font choose = new Font("menu",Font.BOLD,20);
		g.setFont(choose);
		g.setColor(Color.BLACK);
		g.drawString("Choose color", 155, 200);
		
		//buttons to choose color
		 g2d.draw(blackChecker);
		 g2d.setColor(Color.BLACK);
		 g2d.fillRect((int)blackChecker.getX(),(int)blackChecker.getY(), (int)blackChecker.getWidth(), (int)blackChecker.getHeight());
		 g2d.draw(whiteChecker);
		 g2d.setColor(Color.WHITE);
		 g2d.fillRect((int)whiteChecker.getX(),(int)whiteChecker.getY(), (int)whiteChecker.getWidth(), (int)whiteChecker.getHeight());
	}

	public void drawScores(Graphics g,int score_black,int score_white) 
	{
		String black;
		String white;
		
		Font small = new Font("Helvetica", Font.BOLD, 16);
		g.setFont(small);
		g.setColor(Color.BLACK);
		
		black = "BLACK: "+score_black;
		white = "WHITE: "+score_white;
		
		g.drawString(black, 0, 425);
		g.drawString(white, 330, 425);
		
	}
	
	public void drawWinnerScreen(Graphics graphics,boolean black_wins,boolean white_wins)
	{
		Graphics g = graphics;
		String s;
		
		if(black_wins) s = "BLACK WINS!!!";
		else if(white_wins) s = "WHITE WINS!!!";
		else s = "DRAW";
		
		Font f = new Font("Dialog", Font.BOLD, 20);
		g.setFont(f);
		
		g.drawString(s, 125, 200);
	}
}
