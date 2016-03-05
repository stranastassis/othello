


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

public class Board 
{
	/* 
	 * 0 --> empty
	 * 1 --> black
	 * 2 --> white
	 */
	private final int ROWS = 8;
	private final int COLUMNS = 8;
	
	private Move lastMove = new Move();
	
	private int[][] board = new int[ROWS][COLUMNS];
	
	public Board()
	{
		initialize();
	}
	
	
	/* Copy Constructor */
	public Board(Board gameBoard)
	{
		this.lastMove.setRow(gameBoard.getLastMove().getRow());
		this.lastMove.setCol(gameBoard.getLastMove().getCol());
		
		this.board = new int[ROWS][COLUMNS];
		
		for(int i = 0;i < ROWS;i++)
		{
			for(int j = 0;j < COLUMNS; j++)
			{
				this.board[i][j] = gameBoard.board[i][j];
			}
		}
	}
	
	/* initialize board */
	public void initialize()
	{
		//lastMove = new Move();
		
		for(int i = 0;i < 8;i++)
		{
			for(int j = 0;j < 8;j++)
			{
				board[i][j] = 0;
			}
		}
		
		board[3][3] = 2;
		board[3][4] = 1;
		board[4][3] = 1;
		board[4][4] = 2;
	}
	
	public void updateLastMove(int i,int j)
	{
		lastMove = new Move(i,j);
	}
	
	private void setLastMove(int i,int j)
	{
		lastMove.setRow(i);
		lastMove.setCol(j);
	}
	
	/* update Board -- when a new checker is placed */
	public void updateBoard(int i,int j,int value)
	{
		board[i][j] = value;
		setLastMove(i,j);
	}

	public void updateBoard(int block,int value)
	{
		int i = block / 8;
		int j = block % 8;
		setLastMove(i,j);
		updateBoard(i,j,value);
		
	}
	
	/* draw current state of board */
	public void drawBoard(Graphics g) 
	{
		for(int i = 0;i < 8;i++)
		{
			for(int j = 0;j < 8;j++)
			{
				if(board[i][j] == 1)
				{
					//draw black checker
					drawChecker(g,Color.BLACK,i,j);
				}
				if(board[i][j] == 2)
				{
					//draw white checker
					drawChecker(g,Color.WHITE,i,j);
				}
			}
		}
	}
	
	
	
	/* count black/white checkers on board */
	public int scoreCalculator(int player)
	{
		int counter = 0;
		
		for(int i = 0;i < 8;i++)
		{
			for(int j = 0;j < 8;j++)
			{
				if(board[i][j] == player)
					counter++;
			}
		}
		return counter;
	}
	
	/* given the i,j coordinates determine the block */
	public int findBlock(int i,int j)
	{
		int block;
		block = 8*i + j - 1;
		return block;
	}
	
	/* checks if given block is empty */
	public boolean isEmpty(int i,int j)
	{
		
		return board[i][j] == 0;
	}
	
	/* check for available valid moves before player plays */
	public boolean checkForValidMoves(int attacker,int defender)
	{
		for(int i = 0;i < ROWS;i++)
		{
			for(int j = 0;j < COLUMNS;j++)
			{
				if(!isEmpty(i,j)) continue;
				if(checkValidMove(i,j,attacker,defender,true))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkValidMove(int i, int j, int attacker, int defender, boolean check) 
	{
		boolean result = false;
		
		if(check) //check if there are any valid moves
		{
			if(validUp(i,j,attacker,defender,check) || validDown(i,j,attacker,defender,check)||
				validLeft(i,j,attacker,defender,check) || validRight(i,j,attacker,defender,check) ||
				validUpRight(i,j,attacker,defender,check)||validUpLeft(i,j,attacker,defender,check)||
				validDownRight(i,j,attacker,defender,check)||validDownLeft(i,j,attacker,defender,check))
				return true;
			else
				return false;
		}
		else //check for player's valid move
		{
			if(!isEmpty(i,j)) return false;
			
			if(validUp(i,j,attacker,defender,check))		result = true;
			if(validDown(i,j,attacker,defender,check))		result = true;
			if(validLeft(i,j,attacker,defender,check))		result = true;
			if(validRight(i,j,attacker,defender,check))		result = true;
			if(validUpRight(i,j,attacker,defender,check))	result = true;
			if(validUpLeft(i,j,attacker,defender,check))	result = true;
			if(validDownRight(i,j,attacker,defender,check))	result = true;
			if(validDownLeft(i,j,attacker,defender,check))	result = true;
			
			return result;
		}
				
		
	}

	/* check down */
	private boolean validDown(int i, int j, int attacker, int defender, boolean check)
	{
		boolean valid = false;
		
		if(i + 1 < 8)
		{
			if(board[i+1][j] == defender)
			{
				int counter = 1;
				
				LinkedList<Integer> list = new LinkedList<Integer>();
				
				if(!check)
				{
					list.add((i+1)*8+j);
				}
				
				//while inbounds and at a non empty block
				while(i + counter < 8 && board[i + counter][j] != 0)
				{	
					if(!check)
					{
						list.add((i+counter)*8+j);
					}					
					//An brikame kouti me attacker exoume valid move
					if(board[i + counter][j] == attacker)
					{
						valid = true;
						
						if(!check)
						{
							changeColor(list,attacker);
						}
						
						break;
					}
					counter++;
				}
				
			}
		}
		return valid;
	}
	
	/* check up the column */
	private boolean validUp(int i, int j, int attacker, int defender,boolean check) 
	{
		boolean valid = false;
		
		if(i - 1 > -1)
		{
			if(board[i-1][j] == defender)
			{
				int counter=i-1;
				
				LinkedList<Integer> list = new LinkedList<Integer>();
				
				if(!check)
				{
					list.add((i-1)*8+j);
				}
				
				while(counter>-1 && board[counter][j]!=0)
				{
					
					if(!check)
					{
						list.add((counter)*8+j);
					}
					
					if(board[counter][j]==attacker)
					{
						valid = true;
						
						if(!check)
						{
							changeColor(list,attacker);
						}
						
						break;
					}
					counter--;
				}	
			}
		}
		return valid;
	}
	
	/* check to the left */
	private boolean validLeft(int i, int j, int attacker, int defender,boolean check)
	{
		boolean valid = false;
		
		if(j-1>-1)
		{
			if(board[i][j-1]==defender)
			{
				int counter=j-1;
				
				LinkedList<Integer> list=new LinkedList<Integer>();
				
				if(!check)
				{
					list.add((i)*8+j-1);
				}
				
				while(counter>-1 && board[i][counter]!=0)
				{
					
					if(!check)
					{
						list.add(i*8+counter);
					}
					
					if(board[i][counter]==attacker)
					{
						valid = true;
						
						if(!check)
						{
							changeColor(list,attacker);
						}
						
						break;
					}
					counter--;
				}	
			}
		 }
		return valid;
	}

	/* check right */
	private boolean validRight(int i, int j, int attacker, int defender,boolean check)
	{
		boolean valid = false;
		
		if(j+1 < 8)
		{
			if(board[i][j+1]==defender)
			{
				int counter=1;
				
				LinkedList<Integer> list=new LinkedList<Integer>();
				if(!check)
				{
					list.add((i)*8+j+1);
				}
				
				while(j+counter<8 && board[i][j+counter]!=0)
				{
					
					if(!check)
					{
						list.add((i)*8+j+counter);
					}
					
					if(board[i][j+counter]==attacker)
					{
						valid = true;
						
						if(!check)
						{
							changeColor(list,attacker);
						}
						
						break;
					}
					counter++;
				}
			}	
		}
		return valid;
	}
	
	/* check up right */
	private boolean validUpRight(int i, int j, int attacker, int defender,boolean check)
	{
		boolean valid = false;
		
		if(j+1<8 && i-1>-1)
		{
			if(board[i-1][j+1]==defender)
			{
				int counter1=i-1;
				int counter2=1;
				
				LinkedList<Integer> list=new LinkedList<Integer>();
				if(!check)
				{
					list.add((i-1)*8+j+1);
				}
				
				while(counter1>-1 && j+counter2<8 && board[counter1][counter2+j]!=0)
				{
					
					if(!check)
					{
						list.add(counter1*8+counter2+j);
					}
					
					if (board[counter1][counter2+j]==attacker)
					{
						valid = true;
						
						if(!check)
						{
							changeColor(list,attacker);
						}
						
						break;
					}
					counter1--;
					counter2++;
				}
			}	
		}
		return valid;
	}
	
	/* check up left */
	private boolean validUpLeft(int i, int j, int attacker, int defender,boolean check)
	{
		boolean valid = false;
		
		if(j-1>-1 && i-1>-1)
		{
			if(board[i-1][j-1]==defender)
			{
				int counter1=i-1;
				int counter2=j-1;
				
				LinkedList<Integer> list=new LinkedList<Integer>();
				if(!check)
				{
					list.add((i-1)*8+j-1);
				}
				
				while(counter1>-1 && counter2>-1 && board[counter1][counter2]!=0)
				{
					
					if(!check)
					{
						list.add(counter1*8+counter2);
					}
					
					if(board[counter1][counter2]==attacker)
					{
						valid= true;
						
						if(!check)
						{
							changeColor(list,attacker);
						}
						
						break;
					}
					counter1--;
					counter2--;
				}	
			}
		}
		return valid;
	}
	
	/* check down right */
	private boolean validDownRight(int i, int j, int attacker, int defender,boolean check)
	{
		boolean valid = false;
		
		if((j+1)<8 && (i+1)<8)
		 {
			if(board[i+1][j+1]==defender)
			{
				int counter1=1;
				int counter2=1;
				
				LinkedList<Integer> list=new LinkedList<Integer>();
				if(!check)
				{
					list.add((i+1)*8+j+1);
				}
				
				while(i+counter1<8 && j+counter2<8 && board[i+counter1][j+counter2]!=0)
				{
					
					if(!check)
					{
						list.add((i+counter1)*8+counter2+j);
					}
					
					if(board[i+counter1][j+counter2]==attacker)
					{
						valid=true;
						
						if(!check)
						{
							changeColor(list,attacker);
						}
						
						break;
					}
					counter1++;
					counter2++;
				}
			}	
		}
		return valid;
	}
	
	/* check down left */
	private boolean validDownLeft(int i, int j, int attacker, int defender,boolean check)
	{
		boolean valid = false;
		
		if(j-1>-1 && i+1<8)
		{
			if(board[i+1][j-1]==defender)
			{
				int counter1=1;
				int counter2=j-1;
				
				LinkedList<Integer> list=new LinkedList<Integer>();
				if(!check)
				{
					list.add((i+1)*8+j-1);
				}
				
				while(i+counter1<8 && counter2>-1 && board[counter1+i][counter2]!=0)
				{
					
					if(!check)
					{
						list.add((counter1+i)*8+counter2);
					}
					
					if(board[counter1+i][counter2]==attacker)
					{
						valid = true;
						
						if(!check)
						{
							changeColor(list,attacker);
						}
						
						break;
					}
					counter1++;
					counter2--;	
				}
			}
		}
		return valid;
	}
	
	/* return specified element of the board */
	public int getElement(int i,int j)
	{
		return board[i][j];		
	}
	
	private void changeColor(LinkedList<Integer> list, int attacker) 
	{
		for(Integer pos:list)
		{
			updateBoard(pos, attacker);
		}		
	}
	

	public Move getLastMove() 
	{
		return this.lastMove;
	}
	
	public void displayValidMoves(int player,int cpu,Graphics g)
	{
		for(int i = 0;i < ROWS; i++)
		{
			for(int j = 0;j < COLUMNS; j++)
			{
				if(checkValidMove(i,j,Game.playerColor,Game.cpuColor,true))
				{				
					drawValidMove(i,j,g);
				}
			}
		}
	}
	

	
	public ArrayList<Board> getChildren(int attacker,int defender) 
	{
		ArrayList<Board> children = new ArrayList<Board>();
		
		for(int i = 0;i < ROWS; i++)
		{
			for(int j = 0;j < COLUMNS; j++)
			{
				Board child = new Board(this);
				
				if(child.checkValidMove(i,j,attacker,defender,false))
				{
					child.lastMove = new Move(i,j);
					child.updateBoard(i, j, attacker);
					children.add(child);
				}
			}
		}
		
		return children;
	}

	
	private void drawChecker(Graphics g,Color color,int i,int j)
	{
		int x = j*50+5;
		int y = i*50+5;
		int r = 20;
		g.setColor(color);
		g.fillOval(x,y,2*r,2*r);
	}
	
	private void drawValidMove(int i,int j,Graphics g)
	{
		int x = j*50+5;
		int y = i*50+5;
		int r = 20;
		g.drawOval(x,y,2*r,2*r);
	}

}
