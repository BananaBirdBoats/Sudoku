import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import acm.graphics.*;
import acm.program.*;
public class SudokuBoard{
	public int[][]sudBoard=new int[9][9];
	public MyStack undoStack=new MyStack();
	public int[][] cantChange=new int[9][9];
	public MyStack redoStack=new MyStack();
	
	public SudokuBoard(){
		
	}
	public SudokuBoard(int[][] grid){
		this.sudBoard=grid;		
	}
	
/**
 * this undo function uses a custom stack class called MyStack, allows the last saved numeric change to be reverted. 
 * Uses a try catch block to account for case of trying to undo before making any changes.
 */
public void undo(){
	try{
		Coordinates u=this.undoStack.peek();
		this.redoStack.push(new Coordinates(u.xCord,u.yCord,this.sudBoard[u.xCord][u.yCord],true));
			
		
	//	System.out.println("Removed the value of "+this.getValue(u.xCord, u.yCord)+" with the value of "+u.value);
		
		u=this.undoStack.pop();
		this.setValue(u);
		Sudoku.sudcan.glabel.setVisible(false);
		Sudoku.sudcan.showMessage("Undo performed.");

		
	}catch(ArrayIndexOutOfBoundsException e){
		Sudoku.sudcan.glabel.setVisible(false);
		Sudoku.sudcan.showMessage("You can not undo any further");
		return;
	}
	
}
public void redo(){
	
	try{
		Coordinates u=this.redoStack.peek();
				
			u=this.redoStack.pop();
			this.undoStack.push(new Coordinates(u.xCord,u.yCord,this.sudBoard[u.xCord][u.yCord],true));
			
	
	//	System.out.println("Removed the value of "+this.getValue(u.xCord, u.yCord)+" with the value of "+u.value);
		
		this.setValue(u);
		Sudoku.sudcan.glabel.setVisible(false);
		Sudoku.sudcan.showMessage("Redo performed");
		
	}catch(ArrayIndexOutOfBoundsException e){
		Sudoku.sudcan.glabel.setVisible(false);
		Sudoku.sudcan.showMessage("You cannot redo any further.");
		return;
	}
}
/**
 * this should return true if there are any zeros left on the board.
 * @return
 */
public boolean isFull(){
	for(int i=0;i<9;i++){
		for(int j=0;j<9;j++){
			if(this.sudBoard[i][j]==0){
				Sudoku.sudcan.glabel.setVisible(false);
				Sudoku.sudcan.showMessage("This board is not full");
				return false;
			}
		}
	
		
	}
	Sudoku.sudcan.glabel.setVisible(false);
	Sudoku.sudcan.showMessage("This board is full");
	return true;
}
	
/**
 * returns the numeric value at x,y
 * @param x
 * @param y
 * @return
 */
public int getValue(int x, int y){
	if( x>8 || x<0|| y>8||y<0){
		//System.out.println("Invalid input");
		return 0;
	}
	return sudBoard[x][y];
		
	}
/**
 * this method is used to change the values of the board and to add them to the undostack.
 * @param x
 * @param y
 * @param changeTo
 */
public void setValue(int y, int x, int changeTo){
	if(changeTo>9 || x>8 || x<0|| y>8||y<0||changeTo<0){
		//System.out.println("Invalid input");
		Sudoku.sudcan.glabel.setVisible(false);

		return;
	}
	
	
	if(this.cantChange[x][y]==0){
		this.undoStack.push(new Coordinates(x,y,getValue(x,y),true));
		//this.redoStack.push(new Coordinates(x,y,changeTo,true));
		this.sudBoard[x][y]=changeTo;
	//	System.out.println("");
	//	printGrid(this.sudBoard);
		Sudoku.sudcan.glabel.setVisible(false);
		
		return;
	}
	else if(this.cantChange[x][y]!=0){
		//System.out.println("Cannot change that coordinate");
	//	printGrid(this.sudBoard);
		Sudoku.sudcan.glabel.setVisible(false);
		
		return;
	}
	
	this.sudBoard[x][y]=changeTo;
	//System.out.println("");
	//printGrid(this.sudBoard);
}
/**
 * This method is only used within undo() in order to change the values without adding that operation to the undostack.
 * @param x
 */
public void setValue(Coordinates x){
	if(x.value>9 || x.xCord>8 || x.xCord<0|| x.yCord>8||x.yCord<0||x.value<0){
		//System.out.println("Invalid input");
		Sudoku.sudcan.glabel.setVisible(false);

		return;
	}
	
	
	if(this.cantChange[x.xCord][x.yCord]==0){
		
		this.sudBoard[x.xCord][x.yCord]=x.value;
		//System.out.println("");
		//printGrid(this.sudBoard);
		Sudoku.sudcan.glabel.setVisible(false);

		return;
	}
	else if(this.cantChange[x.xCord][x.yCord]!=0){
		System.out.println("Cannot change that coordinate");
		//printGrid(this.sudBoard);
		Sudoku.sudcan.glabel.setVisible(false);

		return;

	}
	
	this.sudBoard[x.xCord][x.yCord]=x.value;
	//System.out.println("");
	//printGrid(this.sudBoard);
}
/**
 * This method is run once before any changes are made to the board. Its purpose is to figure out
 * the initial board values and add them to an array to make them unchangeable
 */

public void checkChange(){
	int count=0;
	for(int i=0;i<9;i++){
		for(int j=0;j<9;j++){			
			
			if(this.sudBoard[i][j]!=0){
				this.cantChange[i][j]=this.sudBoard[i][j];
			}
			else{
				this.cantChange[i][j]=this.sudBoard[i][j];
			}
			count++;
		}
	}
	Sudoku.sudcan.glabel.setVisible(false);
	Sudoku.sudcan.showMessage("Board's unchangeales loaded.");

}
//self explanatory
	public void printGrid(int[][] grid){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				System.out.print(grid[i][j]+" ");
				
			}	
		System.out.println();
		}
	}
	//self explanatory. Note: Does not require parameters
	public void printGrid(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				System.out.print(this.sudBoard[i][j]+" ");
				
			}	
		System.out.println();
		}
	}
}
/**
 * This subclass stores the location and values of the numbers on the board
 * Using this subclass has made finding the values easier
 */

class Coordinates{
	public int xCord=0;
	public int yCord=0;
	public int value=0;
	public boolean changeable=false;
	public Coordinates(int x, int y){
		xCord=x;
		yCord=y;
		
	}
	public Coordinates(int x, int y, int value, boolean changeable){
		xCord=x;
		yCord=y;
		this.value=value;
		this.changeable=changeable;
	}
	//overwrites the equals method from parent class Object
	//To make sure that the two coordinates are equal it compares the x and y coordinates
	//As of thursday, comparing the actual values of coordinates is unneeded.
	public boolean equals(Coordinates v){
		if(v.xCord==this.xCord && v.yCord==this.yCord){
			return true;
		}
		return false;
	}
	}