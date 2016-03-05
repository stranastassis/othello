

import java.util.ArrayList;

public class MiniMax 
{
	/**
	 * maximum depth to be reached during search
	 */
	private int maxDepth;
	
	/**
	 * CPU Color
	 */
	private int cpuColor;
	
	/**
	 * Player Color
	 */
	private int playerColor;
	
	/**
	 * Constructor
	 */
	public MiniMax(int depth,int p,int c)
	{
		setDepth(depth);
		this.cpuColor = c;
		this.playerColor = p;
		System.out.println(maxDepth);
	}
	
	private void setDepth(int depth)
	{
		if(depth <= 0)	
		{
			maxDepth = 1;
		}
		else	
		{
			maxDepth = depth;
		}
	}
	
	public Move makeMove(Board board)
	{
		return max(board,0,Integer.MIN_VALUE,Integer.MAX_VALUE);
	}
	
	/**
	 * Max Moves
	 */
	public Move max(Board board,int depth,double a,double b)
	{
		double lwrbound=a;
		double upbound=b;
		if((!(board.checkForValidMoves(cpuColor, playerColor))) || (depth == maxDepth))
		{
			Evaluation ev = new Evaluation(board,cpuColor,playerColor);
			Move lastMove = new Move(board.getLastMove().getRow(),board.getLastMove().getCol(),ev.evaluate());
			return lastMove;
		}
		
		//System.out.println("--------------------------------------------------------------------");
		
		/**
		 * list that contains all possible moves for MAX starting from the current state
		 */
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(cpuColor,playerColor));
		
		//System.out.println("--------------------------------------------------------------------");
		
		Move maxMove = new Move(Integer.MIN_VALUE);
		
		for(Board child : children)
		{
			Move move = min(child, depth + 1,lwrbound,upbound);
			
			if(move.getValue() >= maxMove.getValue())
			{
				maxMove.setRow(child.getLastMove().getRow());
				maxMove.setCol(child.getLastMove().getCol());
				maxMove.setValue(move.getValue());
			}
			if(move.getValue()>=upbound){
				return maxMove;
			}
			if(lwrbound<move.getValue()){
				lwrbound=move.getValue();
			}
		}
		return maxMove;	
	}

	/**
	 * Min Moves 
	 */
	private Move min(Board board, int depth,double a,double b) 
	{
		double lwrbound=a;
		double upbound=b;
		
		if((!(board.checkForValidMoves(playerColor,cpuColor))) || (depth == maxDepth))
		{
			Evaluation ev = new Evaluation(board,cpuColor,playerColor);
			Move lastMove = new Move(board.getLastMove().getRow(),board.getLastMove().getCol(),ev.evaluate());
			return lastMove;
		}
		
		//System.out.println("--------------------------------------------------------------------");
		
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(playerColor,cpuColor));
		
		//System.out.println("--------------------------------------------------------------------");
		
		Move minMove = new Move(Integer.MAX_VALUE);
		
		for(Board child : children)
		{
			Move move = max(child,depth + 1,lwrbound,upbound);
			
			if(move.getValue() <= minMove.getValue())
			{
				minMove.setRow(child.getLastMove().getRow());
				minMove.setCol(child.getLastMove().getCol());
				minMove.setValue(move.getValue());
			}
			if(move.getValue()<=lwrbound){
				return minMove;
			}
			if(move.getValue()<upbound){
				upbound=move.getValue();
			}
		}
		return minMove;
	}
}
