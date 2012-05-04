/**************************************
 * 
 * 0 Freies Feld
 * 1 Bombe
 * 2 Entfernbare Mauer
 * 3 Statische Mauer
 * 4 Ausgang (Wichtig fur ersten Meilenstein)
 * 5 Item (kommt noch)
 *
 *************************************/
public class Map{
	// Variablen
	private int size;
	private final int minSize = 5;
	private final int maxSize = 15;
	private int[][] fields;
	// Konstruktor
	public Map (int s){
		if(s<this.minSize) this.size = this.minSize;
		if(s>=this.minSize && s<=this.maxSize) this.size = s;
		if(s>this.maxSize) this.size = this.maxSize;
		this.fields = new int[this.size][this.size];
	}
	// Draw-Methode
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
	public int getMaxSize(){
		return this.maxSize;
	}
	public int getMinSize(){
		return this.minSize;
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
	// Bearbeitungsmethoden
	public void setType(int t, int x, int y){
		if((t>1 && t<5) || t==0){
			try{
				fields[x][y] = t;
			}
			catch(ArrayIndexOutOfBoundsException e){
				System.out.println("Ungultiger Zugriff auf die Karte: ("+x+","+y+") existiert nicht.");
			}
		}
	}
	public void cleanMap(){
		for(int j=0;j<this.size;j++){
			for(int k=0;k<this.size;k++){
				fields[j][k] = 0;
			}
		}

	}
	public void generateBlocks(){
		for(int f=0;f<this.size/2;f++){
			for(int g=0;g<this.size/2;g++){
				this.setType(3,2*f+1,2*g+1);
			}	
		}
	}
	public void generateChaos(double c){
		for(int f=0;f<this.size;f++){
			for(int g=0;g<this.size;g++){
				double q = Math.random();
				if(q<c)this.setType(2,f,g);
			}
		}
	}
	public void cleanEdges(){
		int s = this.size-1;
		this.setType(0,0,0);
		this.setType(0,1,0);
		this.setType(0,0,1);
		this.setType(0,s,s);
		this.setType(0,s-1,s);
		this.setType(0,s,s-1);
		this.setType(0,0,s);
		this.setType(0,1,s);
		this.setType(0,0,s-1);
		this.setType(0,s,0);
		this.setType(0,s-1,0);
		this.setType(0,s,1);
	}
	public void generateExit(){
		this.setType(4,this.size-1,this.size-1);
	}
}
