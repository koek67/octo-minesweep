package Grid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/*
 * The SwitchGrid class will have a 2D array of boolean values. The SwitchGrid is constructed by 
 * passing in the number of rows and columns as arguments.		
 */
public class SwitchGrid{
	
	//	2D array of boolean
	private boolean[][] board;
	//	number of rows and columns in the 2D array
	private int numRows,numCols;
	/*
	 * Constructor: provide number of rows and columns
	 */
	public SwitchGrid(int rows, int cols){
		board = new boolean[rows][cols];
		numRows = rows;
		numCols = cols;
	}
	/*
	 * Will iterate through board and return the number
	 * of values that are true;
	 */
	public int getNumTrue(){
		int numTrue = 0;
		for(int i = 0; i < numRows; i++)
			for(int j = 0; j < numCols; j++)
				if(board[i][j] == true) 
					numTrue++;
		return numTrue;
	}	
	/*
	 * will return the number of false elements in board
	 */
	public int getNumFalse(){
		return (numRows*numCols) - getNumTrue();
	}
	
	/*
	 * will return the number of true, valid neighbors of a given location
	 */
	public int getNeighbors(int row, int col){
		int neighbors = 0;
		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++) {
				try {
					if (board[row + i][col + j])
						neighbors++;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		
		return neighbors;
	}
	public ArrayList<Point> getNeighborsPoints(int row, int col){
		 ArrayList<Point> points = new ArrayList<Point>();
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(isValid(row,col))
					points.add(new Point(row+i,col+j));
			}
		}
		return points;
	}
	
	/*
	 * will return true is the given location is within bounds of the 
	 * gird
	 */
	public boolean isValid(int r, int c){
		return r < numRows && c < numCols && r >= 0 && c >= 0;
	}
	/*
	 * precondition: row,col is valid
	 */
	public boolean get(int row, int col){
		return board[row][col];
	}
	/*
	 * precondition: row,col is valid
	 */
	public void set(int row, int col, Boolean value){
		board[row][col] = value;
	}
	/*
	 * precondition: row,col is valid
	 */
	public void toggle(int row, int col){
		board[row][col] = !board[row][col];
	}
	/*
	 * returns a String representation of the SwitchGrid
	 */
	public String toString(){
		String s = "";
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(board[i][j])
					s += " | " + board[i][j];
				else s += " | " + "    ";
			}
			s = s + " |";
			s = s + '\n';
		}
		return s;
	}
	public static void main(String[] args){
		SwitchGrid s = new SwitchGrid(10,10);
		Random numGen = new Random();
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				int val = numGen.nextInt(2);
				if(val == 1)
					s.set(i, j, true);
				else s.set(i,j,false);
				
			}
		}
		System.out.println(s);
	}
	public int getRows() {
		return numRows;
	}
	public int getCols() {
		return numCols;
	}
	
} // SwitchGrid
