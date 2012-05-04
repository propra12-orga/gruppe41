/**************************************
 * 
 * 0 Freies Feld
 * 1 Bombe
 * 2 Entfernbare Mauer
 * 3 Statische Mauer
 * 4 Ausgang (Wichtig für ersten Meilenstein)
 *
 *************************************/
public class Map{
	// Variablen
	private int size;
	private int[][] fields;
	public Map (int s, int[][] f){
		this.size = s;
		this.fields = f;
	}
	// Draw-Methode - sturzt ab falls this.getSize = 0
	public void draw(){
		StdDraw.setPenColor(StdDraw.CYAN);
		StdDraw.filledSquare(0.5,0.5,0.5);
		for(int j=0;j<this.getSize();j++){
			for(int k=0;k<this.getSize();k++){
				if(fields[j][k]==2){
					StdDraw.setPenColor(StdDraw.ORANGE);
					StdDraw.filledSquare(((2.0*j+1)/(2.0*this.getSize())),(2.0*k+1)/(2.0*this.getSize()),0.5/this.getSize());
				}
				if(fields[j][k]==3){
					StdDraw.setPenColor(StdDraw.RED);
					StdDraw.filledSquare(((2.0*j+1)/(2.0*this.getSize())),(2.0*k+1)/(2.0*this.getSize()),0.5/this.getSize());
				}
				if(fields[j][k]==4){
					StdDraw.setPenColor(StdDraw.BLUE);
					StdDraw.filledSquare(((2.0*j+1)/(2.0*this.getSize())),(2.0*k+1)/(2.0*this.getSize()),0.5/this.getSize());
				}			
			}
		}
	}
	// Get-Methoden
	public int getType(int x, int y){
		return fields[x][y];
	}
	public int getSize(){
		return this.size;
	}
	// Destroy
	public void destroy(int x, int y){
		if(this.fields[x][y]==1){
			// Bombe auf diesem Feld aktivieren
		}
		if(this.fields[x][y]==2){
			this.fields[x][y]=0;
		}		
	}
}
