package Grid;

import java.awt.Point;
import java.util.ArrayList;

/*
 * IntGrid is a grid that stores integer values.
 * Methods:
 * 		get,set
 * 		getNeighbors - will the number of neighbors with the given int value around a given location
 * 		getCount - will return the number of occurrences of the given integer in the grid
 */
public class IntGrid{
	
	// 2D array to store the grid
	private int[][] board;
	// number of rows and columns
	private int numRows,numCols;
	
	public IntGrid(int rows, int cols){
		numRows = rows;
		numCols = cols;
		board = new int[numRows][numCols];
	}
	
	// returns the int at this location
	// precondition: (row,col) is inbounds
	public int get(int row, int col){
		return board[row][col];
	}
	
	// set this location to value
	// precondition: (row,col) is inbounds
	public void set(int row, int col, int value){
		board[row][col] = value;
	}
	
	/*
	 * will return the number of occurrences of the given value in the grid
	 */
	public int getCount(int value){
		int counter = 0;
		
		for(int i = 0; i < numRows; i++)
			for(int j = 0; j < numCols; j++)	
				if(board[i][j] == value)
					counter++;
		
		return counter;
	}
	
	/*
	 * will return the number of valid neighbors that equals value in the grid
	 */
	public int getNeighbors(int row, int col, int value){
		int counter = 0;
		
		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++)
				if(isValid(i+row,j+col) && board[i+row][j+col] == value)
					counter++;
		
		return counter;
	}
	public ArrayList<Point> getNeighbors(int row, int col){
		 ArrayList<Point> points = new ArrayList<Point>();
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(isValid(row,col))
					points.add(new Point(row+i,col+j));
			}
		}
		return points;
	}
	public boolean isValid(int row, int col){
		return row < numRows && col < numCols && row >= 0 && col >= 0;
	}
	
	public String toString(){
		String s = "";
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(board[i][j] < 10 && board[i][j] > -10)
					s += "| " + board[i][j];
				else if(board[i][j] < 100 && board[i][j] > -100)
					s += "|" + board[i][j];
			}
			s += "|" + '\n';
		}
		return s;
		
	}
	public int getRows(){return numRows;}
	public int getCols(){return numCols;}
//	public static void main(String[] args){
//		IntGrid s = new IntGrid(5,5);
//		Random numGen = new Random();
//		for(int i = 0; i < s.getRows(); i++){
//			for(int j = 0; j < s.getCols(); j++){
//				int val = numGen.nextInt(20);
//				s.set(i, j, 1);
//				
//			}
//		}
//		s.set(2, 2, 7);
//		s.set(2,3,5);
//		System.out.println(s);
//		System.out.println(s.getNeighbors(2,2,1));
//	}

	
	
}
