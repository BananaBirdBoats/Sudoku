import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import acm.graphics.*;
import acm.program.*;

public class SudokuCanvas extends GCanvas{
	public GLabel glabel=new GLabel("");
	public SudokuCanvas(){
		
	}
		public void init() {
			
			
			
			
			
			
			/* TODO: Create connection between what numbers are in the 
			 * real SudokuBoard and on the canvas. Some type of looping system
			 * with if statements declaring what numbers to place in it. 
			 * 
			 */
			
			
			
			
			
			
			
			
			
			
		
	}
		public void drawBoard(SudokuBoard board){
			GRect r;
			GLabel l;
			r = new GRect(179,0,2,270);
			r.setFilled(true);
			r.setFillColor(Color.cyan);
			add(r);
			
			r = new GRect(0,179,270,2);
			r.setFilled(true);
			r.setFillColor(Color.cyan);
			add(r);
			r = new GRect(89,0,2,270);
			r.setFilled(true);
			r.setFillColor(Color.cyan);
			add(r);
			r = new GRect(0,89,270,2);
			r.setFilled(true);
			r.setFillColor(Color.cyan);
			add(r);
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					r= new GRect(0+30*i,0+30*j,30,30);
					r.setFilled(true);
					r.setFillColor(Color.LIGHT_GRAY);
	
					add(r);
					if(board.cantChange[j][i]!=0 && board.cantChange[j][i]==board.getValue(j, i)){
					l= new GLabel(Integer.toString(board.getValue(j,i)),10+30*i,24+30*j);
					l.setFont("Sans Serif-Bold-20");
					l.setColor(Color.BLACK);
					
					add(l);
					}else{
						l= new GLabel(Integer.toString(board.getValue(j, i)),10+30*i,24+30*j);
						l.setFont("Sans Serif-Bold-20");
						l.setColor(Color.RED);
						
						add(l);
					}
				}
			}
			r = new GRect(179,0,2,270);
			r.setFilled(true);
			r.setFillColor(Color.cyan);
			add(r);
			
			r = new GRect(0,179,270,2);
			r.setFilled(true);
			r.setFillColor(Color.cyan);
			add(r);
			r = new GRect(89,0,2,270);
			r.setFilled(true);
			r.setFillColor(Color.cyan);
			add(r);
			r = new GRect(0,89,270,2);
			r.setFilled(true);
			r.setFillColor(Color.cyan);
			add(r);
		}
		public void redrawRect(SudokuBoard board,GObject obj, int mouseClick, int x, int y){
		int t=Integer.parseInt(((GLabel) obj).getLabel());
		int b=0;
		
			if(mouseClick==1){
				if(obj instanceof GLabel && obj.getColor()!=Color.BLACK){
	
						if(t==9){
							t=0; 
						}
						b=t+1;
						board.setValue(x/30, y/30, b);

						obj=new GLabel(Integer.toString(b),obj.getX(),obj.getY());
						
						((GLabel) obj).setFont("Sans Serif-Bold-20");
						obj.setColor(Color.RED);
						add(obj);
				}else{
					obj.setVisible(true);
				}
			}else if(mouseClick==3){
					if(obj instanceof GLabel && obj.getColor()!=Color.BLACK){
						
						
							if(t==1 || t==0){
								t=10; 
							}
							b=t-1;
							board.setValue(x/30, y/30, b);
							obj=new GLabel(Integer.toString(b),obj.getX(),obj.getY());
							
							((GLabel) obj).setFont("Sans Serif-Bold-20");
							obj.setColor(Color.RED);
							add(obj);
					}else{
						obj.setVisible(true);
					}
			
				}
			}
	public void showMessage(String msg){
			glabel=new GLabel(msg);
			glabel.setFont("Sans Serif-Bold-20");
			glabel.setColor(Color.WHITE);
			Sudoku.sudcan.add(glabel,0, 290);
		}
	
		
}
