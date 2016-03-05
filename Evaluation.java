


public class Evaluation 
{
	private Board board;
	private int cpu;
	private int player;
	
	
	private final int[][] posWeight = {{99,-8,8,6,6,8,-8,99},
		 	   {-8,-24,-4,-3,-3,-4,-24,-8},
			   {8,-4,7,4,4,7,-4,8},
			   {6,-3,4,0,0,4,-3,6},
			   {6,-3,4,0,0,4,-3,6},
			   {8,-4,7,4,4,7,-4,8},
			   {-8,-24,-4,-3,-3,-4,-24,-8},
			   {99,-8,8,6,6,8,-8,99}};
	
	public Evaluation(Board board,int c,int p)
	{
		this.board = board;
		this.cpu = c;
		this.player = p;
	}
	
	public double evaluate()
	{			
			return sum()+3*mobility()+0.1*frontierSquares();// To 3 kai to 0.1 einai empeirika 
	}
	
	
	
	/*
	 * Metraei tis kinhseis thn cpu kai tou paixth
	 * gurizei thn diafora tous 
	 */
	public int mobility()
	{
		int movesAvCpu=0;
		int movesAvPlayer=0;
		
		for(int i = 0;i < 8; i++) 
		{
			for(int j = 0;j < 8; j++)
			{
				if(board.checkValidMove(i,j,  cpu,  player,true)){//Einai valid move gia thn cpu?
					movesAvCpu++;
				}
				if(board.checkValidMove(i,j, player,cpu,true)){//Einai valid move gia ton player?
					movesAvPlayer++;
				}
			}
		}
		return movesAvCpu-movesAvPlayer;
	}
	
	private int sum()
	{
		int sum = 0;
		
		int sum2=0;
		
		for(int i = 0;i < 8;i++)
		{
			for(int j = 0;j < 8;j++)
			{
				if(board.getElement(i, j) == cpu)
				{
					sum = sum + posWeight[i][j];
				}
				if(board.getElement(i, j) == player)
				{
					sum2=sum2+posWeight[i][j];
				}
			}		
		}
		return sum-sum2;
	}
	
	/*
	 * Briskei posa geitonika adeia uparxoun sunolika gurw apo ta poulia ths cpu
	 * apo ta opoia afairei ta antistoixa tou player 
	 */
	private int frontierSquares(){
		int counter=0;
		int counter2=0;
		for(int i = 0;i < 8; i++)
		{
			for(int j = 0;j < 8; j++)
			{
				if(board.getElement(i, j) == cpu){ //Sthn 8esh p exoume exei pouli h cpu?
					counter=counter+adjacentEmptySquares(i,j); //Metra ta geitonika adeia
				}
				if(board.getElement(i, j) == player){ // Sthn 8esh p exoume exei pouli o player?
					counter2=counter2+adjacentEmptySquares(i,j);//Metra ta geitonika
				}
			}
		}
		return counter2-counter;
	}
	
	/*
	 * Metraei ta geitonika poulia p einai adeia 
	 */
	public int adjacentEmptySquares(int i,int j){//H idia logikh me thn valid moves,apla koitazei mono ta 8 pi8ana geitonika 
		int counter=0;
		if(i + 1 < 8)	
		{
			if(board.getElement(i+1, j) == 0)//An einai empty +1 
			{
				counter++;
			}
		}
		if(i - 1 > -1)
		{
			if(board.getElement(i-1, j) == 0)
			{
				counter++;
			}
		}
		if(j-1>-1)
		{
			if(board.getElement(i, j-1) == 0)
			{
				counter++;
			}	
		}
		if(j+1 < 8)
		{
			if(board.getElement(i, j+1) == 0)
			{
				counter++;
			}
		}
		if(j+1<8 && i-1>-1)
		{
			if(board.getElement(i-1, j+1) == 0)
			{
				counter++;
			}
		}
		if(j-1>-1 && i-1>-1)
		{
			if(board.getElement(i-1, j-1) == 0){
				counter++;
			}
		}
		if((j+1)<8 && (i+1)<8)
		 {
			if(board.getElement(i+1, j+1) == 0)
			{
				counter++;
			}
		 }
		if(j-1>-1 && i+1<8)
		{
			if(board.getElement(i+1, j-1) == 0)
			{
				counter++;
			}
		}
		return counter;
	}
	
}
