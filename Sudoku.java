/*
  THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
  A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Ian Francis
*/ 
import java.util.Scanner;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;

import acm.graphics.*;
import acm.program.*;

public class Sudoku extends Program{
	public MyStack stack=new MyStack();
	public  SudokuBoard current=new SudokuBoard();
	public static Scanner inputz=new Scanner(System.in);
	public static JTextField fileField;
	public static SudokuCanvas sudcan;

	public void init(){
		fileField=new JTextField();
		
		resize(370,340);
		sudcan= new SudokuCanvas();
		sudcan.drawBoard(current);
		sudcan.setBackground(Color.darkGray);
		sudcan.addMouseListener(this);
		add(sudcan);
		add(new JButton("UNDO"), WEST);
		add(new JButton("REDO"),WEST);
		add(new JButton("CHECK"),WEST);
		add(fileField,WEST);
		fileField.addActionListener(this);
		add(new JButton("CREATE BOARD"),NORTH);
		add(new JButton("LOAD"),NORTH);
		add(new JButton("SAVE"),NORTH);
	
		addActionListeners();
	//	sudcan.paint();
	}
	public void mouseClicked(MouseEvent e) {
		//println(e.getX() + " " + e.getY());
		int mouseClicked=e.getButton();
	//	println(mouseClicked);
		GObject g = sudcan.getElementAt(e.getX(), e.getY());
		if (g != null) {
			if (g instanceof GLabel) {
				if(e.getX()>270 || e.getY()>270){
					return;
				}
				g.setVisible(false);
				sudcan.redrawRect(current,(GLabel)g, mouseClicked, (int) sudcan.getElementAt(g.getX()-10, g.getY()).getX(),(int) sudcan.getElementAt(g.getX()-10, g.getY()).getY());				
			}else if(g instanceof GRect){
				if(g.getHeight()==270 || g.getWidth()==270)
					return;
				sudcan.getElementAt(g.getX(), g.getY());
				sudcan.getElementAt(g.getX()+g.getWidth()/2,g.getY()+g.getHeight()/2).setVisible(false);
				sudcan.redrawRect(current,sudcan.getElementAt(g.getX()+g.getWidth()/2,g.getY()+g.getHeight()/2),mouseClicked,(int)g.getX(), (int)g.getY());
				
			}
			else{
				//println("Not a GRect");
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("CREATE BOARD")){
		//	System.out.println(fileField.getText()+" was loaded");
			try {
				current.checkChange();

				savePuzzle(current);
				savePuzzleChangeables(current);
				Sudoku.sudcan.glabel.setVisible(false);
				Sudoku.sudcan.showMessage("Board created: "+fileField.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			sudcan.drawBoard(current);
		}
		if(e.getActionCommand().equals("SAVE")){
			try {
				savePuzzle(current);				
				savePuzzleChangeables(current);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getActionCommand().equals("LOAD")){
			
				loadPuzzle(fileField.getText()+".txt",current);	
				loadPuzzleChangeables(fileField.getText(),current);
				sudcan.drawBoard(current);
		}
		if(e.getActionCommand().equals("UNDO")){
			current.undo();
			sudcan.drawBoard(current);
			
		}
		if(e.getActionCommand().equals("CHECK")){
			//println(isValid(current.sudBoard));
			isValid(current.sudBoard);
		}
		if(e.getActionCommand().equals("REDO")){
			
			current.redo();
			sudcan.drawBoard(current);
			
		}
	}

	/*
public static void main(String[] args) {
	//int [][] grid= readAPuzzle();
	SudokuBoard boardz=new SudokuBoard();
	boardz.checkChange();
	
	Scanner input=new Scanner(System.in);
	System.out.println(isValid(boardz.sudBoard));
	
	while(!boardz.isFull()){
			System.out.println("");
			boardz.printGrid();
			System.out.println("Please enter a coordinate");
			String a=input.nextLine();
			if(a.equals("undo")){
				boardz.undo();
				continue;
			}
			else if(a.equals("save")){
				try {
					savePuzzle(boardz);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			else if(a.equals("load")){
				loadPuzzle(inputz.nextLine()+".txt", boardz);
				continue;
			}
			String[] b=a.split(" ");
			int x=Integer.parseInt(b[0]);
			int y=Integer.parseInt(b[1]);
			int z=Integer.parseInt(b[2]);
			boardz.setValue(x, y, z);
			
			
		}
		System.out.println("You filled the board!");
		System.out.println(isValid(boardz.sudBoard));

		//boardz.setValue(0, 3, 9);

		System.out.print(boardz.getValue(0, 0));
	
	
}
	*/
	/**
	 * Allows for user to enter a text based board through the console.
	 * Note: Once the graphics are introduced this might become obsolete?
	 */
	public static int[][] readAPuzzle(){
		Scanner input=new Scanner(System.in);
		int[][] grid=new int[9][9];
		System.out.println("Enter a Sudoku puzzle:");		
		
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				grid[i][j]=input.nextInt();
			}	
		}
		return grid;
	

	}
/**
 * This method allows the user to save the progress on their board.
 * @param board this is the board you wish to have saved
 * @throws IOException
 */
public static void savePuzzle(SudokuBoard board) throws IOException{
	
	File puzzleFile=new File(fileField.getText()+".txt");
	PrintWriter puzzleWrite=new PrintWriter(puzzleFile);
	for(int i=0;i<9;i++){
		puzzleWrite.println("");
		for(int j=0;j<9;j++){
			puzzleWrite.print(board.sudBoard[i][j]);
			puzzleWrite.print(' ');
			
			
		}	
	}
	puzzleWrite.close();
	sudcan.glabel.setVisible(false);
	sudcan.showMessage("Board: "+fileField.getText()+" saved");
}
public static void savePuzzleChangeables(SudokuBoard board) throws FileNotFoundException{
	File cantChangeFile=new File("cantChange"+fileField.getText()+".txt");
	PrintWriter puzzleWrite=new PrintWriter(cantChangeFile);	
	
	for(int i=0;i<9;i++){
		puzzleWrite.println("");
		for(int j=0;j<9;j++){
			puzzleWrite.print(board.cantChange[i][j]);
			puzzleWrite.print(' ');
			
			
		}	
	}
	puzzleWrite.close();
}

/**
 * This allows you to load a textual board from your computer.
 * @param fileName this is what file from your computer you wish to load
 * @param targetBoard this is the board loadPuzzle should write the board integer values to
 * @throws FileNotFoundException
 */
public static void loadPuzzle(String fileName, SudokuBoard targetBoard) {
	
	try{
		File puzzleFile=new File(fileName);
		
		
		Scanner puzzleReader=new Scanner(puzzleFile);
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				targetBoard.sudBoard[i][j]=puzzleReader.nextInt();
			}	
		}
		sudcan.glabel.setVisible(false);
		sudcan.showMessage("Board: "+fileName+" loaded");	
	}catch(FileNotFoundException e){
		sudcan.glabel.setVisible(false);
		sudcan.showMessage("File: "+fileName+" does not exist");
	}
	
}
public static void loadPuzzleChangeables(String fileName, SudokuBoard targetBoard) {
	
	try{
		File puzzleFile=new File("cantChange"+fileName+".txt");
		
		
		Scanner puzzleReader=new Scanner(puzzleFile);
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				targetBoard.cantChange[i][j]=puzzleReader.nextInt();
			}	
		}
			
	}catch(FileNotFoundException e){
		
	}
	
}

/**
 * this method checks the row and the column to see if numbers appear more than once in that row or column.
 * @param i
 * @param j
 * @param grid
 * @return
 */
	public static boolean isValid(int i, int j, int[][] grid){
		for(int column=0;column<9;column++)
			if(column!=j && grid[i][column]==grid[i][j]){
				sudcan.glabel.setVisible(false);
				sudcan.showMessage("You still have errors");
				return false;
			}
		for(int row=0;row<9;row++)
				if(row!=i && grid[row][j]==grid[i][j]){
					sudcan.glabel.setVisible(false);
					sudcan.showMessage("You still have errors");
					return false;
				}
		for(int row=(i/3)*3;row<(i/3)*3+3;row++)
			for(int column=(j/3)*3;column<(j/3)*3+3;column++)
				if(row!=i && column!=j && grid[row][column]==grid[i][j]){
					sudcan.glabel.setVisible(false);
					sudcan.showMessage("You still have errors");
					return false;
				}
						
		sudcan.glabel.setVisible(false);
		sudcan.showMessage("You Win!!");

		return true;
	}
	/**
	 * this method checks the row and the column to see if numbers appear more than once in that row or column.
	 * @param grid
	 * @return
	 */
	public static boolean isValid(int[][] grid){
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				if(grid[i][j]==0){
					sudcan.glabel.setVisible(false);
					sudcan.showMessage("You still have errors");
					return false;
				}else if(grid[i][j]!=0 && !isValid(i,j,grid)){
					sudcan.glabel.setVisible(false);
					sudcan.showMessage("You still have errors");
					return false;
				}
					
					
				
			return true;
			
		
	}
}
