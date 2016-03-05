


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener
{
	private Game game = null;
	
	public Mouse(Game game)
	{
		this.game = game;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{		
		/* mouse coordinates */
		int x = e.getX();
		int y = e.getY();
		
		if(game.getIngame() && game.checkBorder(x, y) && game.getPlayerTurn() )
		{
			Game.xx = x/50;
			Game.yy = y/50;
			Game.played = true;
		}
		else	/* listen to intro screen mouse action */
		{
			if(!Game.game_ended)
			{
				if(x >= 125 && x <= 155)
				{
					if(y >= 255 && y <= 305)
					{
						//player chooses black and plays first
						game.init(1,2,true,true);
					}
				}
				if(x >= 255 && x <= 305)
				{
					if(y >= 255 && y <= 305)
					{
						//player chooses white and CPU plays first
						game.init(2,1,true,false);
					}
				}
			}	
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

}
