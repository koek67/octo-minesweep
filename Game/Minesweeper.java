package Game;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Grid.*;
public class Minesweeper {
	//	SwitchGrid for holding buttons. If a button was clicked
    //	the spot is true
	private SwitchGrid buttons;
	//	SwitchGrid for flags. Spots that are true have flags.
	private SwitchGrid flags;
	//	SwitchGrid for bombs. Spots that are true have bombs.
	private SwitchGrid bombs;
	//	IntGrid for the numbers in the game. Each number tells
	//	how many bombs are neighbors
	private IntGrid bombNums;
	//	number of rows,columns in the game
	private int numRows,numCols;

	private int flagCount;
	private int bombCount;
	private boolean win = false;
	private boolean endGame = false;
	public Minesweeper(int row, int col, int bombIndex){
		buttons = new SwitchGrid(row, col);
		flags = new SwitchGrid(row, col);
		bombs = new SwitchGrid(row, col);
		bombNums = new IntGrid(row, col);
		bombCount = bombIndex;
		numRows = row;
		numCols = col;
	}
	/*
	 * flag methods
	 */
	public void setFlag(int row, int col){
		flags.set(row,col,true);
		flagCount++;
	}
	public void removeFlag(int row, int col){
		flags.set(row, col, false);
	}
	public boolean hasFlag(int row, int col){
		return flags.get(row, col);
	}
	public void pressFlag(int row, int col){
		flags.set(row,col,true);
		buttons.set(row,col,true);
	}
	public void toggleFlag(int row, int col){
		if(flags.get(row, col)){
			flags.set(row,col,false);
			flagCount++;
		}
		else{
			flags.set(row,col,true);
		}
	}
	/*
	 * bomb methods
	 */
	public void setBomb(int row,int col){
		bombs.set(row,col,true);
	}
	public boolean hasBomb(int row, int col){
		return bombs.get(row, col);
	}
	public void toggleBomb(int row, int col){
		bombs.toggle(row, col);
	}
	/*
	 * button methods
	 */
	public void setPressed(int row, int col){
		buttons.set(row, col, true);
	}
	public boolean isPressed(int row, int col){
		return buttons.get(row, col);
	}
	/*
	 * bomb number methods
	 */
	public int getBombNum(int row,int col){
		return bombNums.get(row, col);
	}
	public void setBombNum(int row, int col, int value){
		bombNums.set(row,col,value);
	}
	/*
	 * flag number methods
	 */
	public int getFlagCount(){
		return flagCount;
	}
	/*
	 * row/col methods
	 */
	public int getNumRows(){
		return numRows;
	}
	public int getNumCols(){
		return numCols;
	}
	/*
	 * bomb count methods
	 */
	public int getBombCount(){
		return bombCount;
	}
	public int getUnflaggedBombs(){
		return bombCount - flagCount;
	}
	
	public SwitchGrid getFlagsGrid(){
		return flags;
	}
	public SwitchGrid getBombsGrid(){
		return bombs;
	}
	public IntGrid getBombNumsGrid(){
		return bombNums;
	}
	public SwitchGrid getButtonsGrid(){
		return buttons;
	}
	public void placeNumbers(){
		for(int i = 0; i < bombNums.getRows(); i++){
			for(int j = 0; j < bombNums.getCols(); j++){
				int n = bombs.getNeighbors(i,j);
				if(n != 0 && bombs.get(i,j) == false){
					bombNums.set(i,j,n);
				}
			}
		}
	}
	public void fillWithBombs(int i, int j){
		Random numGen = new Random();
		int bombsPlaced = 0;
		while(bombsPlaced < bombCount){
			int r = numGen.nextInt(bombs.getRows());
			int c = numGen.nextInt(bombs.getCols());
			if(r != i && c != j && bombs.get(r, c) == false){
				bombs.set(r,c, true);
				bombsPlaced++;
			}	
		}
	}
	public void fillWithBombs(){
		Random numGen = new Random();
		int bombsPlaced = 0;
		while(bombsPlaced < bombCount){
			int r = numGen.nextInt(bombs.getRows());
			int c = numGen.nextInt(bombs.getCols());
			if(bombs.get(r,c) == false){
				bombs.set(r,c, true);
				bombsPlaced++;
			}
				
		}
	}
	public void fillWithFlags(){
		Random numGen = new Random();
		int bombsPlaced = 0;
		while(bombsPlaced <= bombCount){
			int r = numGen.nextInt(bombs.getRows());
			int c = numGen.nextInt(bombs.getCols());
			if(bombs.get(r,c) == false){
				flags.set(r,c, true);
				bombsPlaced++;
			}
				
		}
	}
	public boolean isLegal(int row, int col){
		return row < numRows && col < numCols && row >= 0 && col >= 0;
	}
	public void expand(int row, int col){
		// first check if the spot is valid
		if(!isLegal(row,col))
			return;
		// check if the spot was clicked
		if(buttons.get(row, col) == true)
			return;
		// check if there is a flag there
		if(flags.get(row, col) == true)
			return;
		// check if the spot already contains a number
		if(bombNums.get(row, col) != 0){
			// set the spot clicked
			buttons.set(row, col, true);
			return;
		}
		if(bombs.get(row,col)){
			buttons.set(row, col, true);
			endGame = true;
			win = false;
		}

		else if(bombNums.get(row, col) == 0){
			buttons.set(row, col, true);
			// get an arrayList of the valid neighbors
			ArrayList<Point> points = bombNums.getNeighbors(row, col);

			for(Point p: points){
				if(p.equals(new Point(row,col)) == false)
					try {
						expand(p.x,p.y);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						return;
					}
			}
		}
		
	}
	public boolean getWin(){
		return win;
	}
	public boolean getEndGame(){
		return endGame;
	}
	public void clearAll(){
		int row = numRows;
		int col = numCols;
		buttons = new SwitchGrid(row, col);
		flags = new SwitchGrid(row, col);
		bombs = new SwitchGrid(row, col);
		bombNums = new IntGrid(row, col);
		win = false;
		endGame = false;
	}
	public void calculateWin(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				// check whether all bombs are not clicked on
				// and that all other spots are clicked on
				
				// if the spot has a bomb
				if(bombs.get(i, j)){
					// check if it has been clicked
					if(buttons.get(i, j)){
						// player looses
						win = false;
						endGame = true;
						return;
					}
				}
				// no bomb
				else{
					// if not clicked, has not won yet
					if(buttons.get(i, j) == false){
						win = false;
						return;
					}
				}
			}
		}
		win = true;
		endGame = true;
	}
	public void showAllBombs(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(bombs.get(i, j))
					buttons.set(i, j, true);
			}
		}
	}
	public void dualClick(int row, int col){
		if(!isLegal(row,col))
			return;
		if(bombNums.get(row,col) == 0)
			return;
		if(flags.getNeighbors(row, col) == bombNums.get(row, col)){
			ArrayList<Point> points = bombs.getNeighborsPoints(row, col);
			for(Point p: points){
				expand(p.x,p.y);
			}
		}
	}
	
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		Minesweeper m = new Minesweeper(10,10,10);
		m.fillWithBombs();
		m.placeNumbers();
		System.out.println("BombNums");
		System.out.println(m.getBombNumsGrid());
		System.out.println("Bombs");
		System.out.println(m.getBombsGrid());
		System.out.println("Buttons");
		System.out.println(m.getButtonsGrid());
		System.out.println("Flags");
		System.out.println(m.getFlagsGrid());
		
		while (true) {
			int x = s.nextInt();
			int y = s.nextInt();
			if(x < 0 || y < 0) break;
			System.out.println(x + ", " + y);
			m.toggleFlag(x, y);
			System.out.println("-------------");
			System.out.println("BombNums");
			System.out.println(m.getBombNumsGrid());
			System.out.println("Bombs");
			System.out.println(m.getBombsGrid());
			System.out.println("Buttons");
			System.out.println(m.getButtonsGrid());
			System.out.println("Flags");
			System.out.println(m.getFlagsGrid());
		}
	}
	
	
}
