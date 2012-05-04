/**************************************
 * 
 * FACEDIRECTION/MOVE
 * 1 Oben
 * 2 Rechts
 * 3 Unten
 * 4 Links
 *
 *************************************/
public class Minion{
	// Variablen
	int number;
	private boolean shield = false;
	private boolean glove = false;
	private int X;
	private int Y;
	private int strength = 1;
	private int bombs = 1;
	private int currentbombs = 1;
	private int bombcounter = 0;
	private Map Map1;
	private int facedirection = 1;
	// Konstruktor
	public Minion (int z, int x, int y, Map map1){
		this.number = z;
		this.X = x;
		this.Y = y;
		this.Map1 = map1;
	}
	// Get-Methoden
	public boolean getShield(){
		return this.shield;
	}
	public boolean getGlove(){
		return this.glove;
	}
	public int getX(){
		return this.X;
	}
	public int getY(){
		return this.Y;
	}
	public int getStrength(){
		return this.strength;
	}
	public int getBombs(){
		return this.bombs;
	}
	public int getFacedirection(){
		return this.facedirection;
	}
	// Allgemeine Move-Methode - noch nicht auf Bugs getestet //
	public void move(int z){
		if(z==1||z==2||z==3||z==4){
			boolean gamerunning = true;
			facedirection = z;
			int a = this.X;
			int b = this.Y;
			if(z==1){b++;}
			if(z==2){a++;}
			if(z==3){b--;}
			if(z==4){a--;}
			if((0<=a && a<Map1.getSize())&&(0<=b && b<Map1.getSize())){
				if(Map1.getType(a,b)==0){
					this.X = a;
					this.Y = b;
				}
				if(Map1.getType(a,b)==1 && this.getGlove()==true){
					this.X = a;
					this.Y = b;
					// Bombe anschubsen
				}
				if(Map1.getType(a,b)==4){
					this.X = a;
					this.Y = b;
					// LEVEL BEENDEN - beim vollstandigen Spiel mussen dafur alle Gegner besiegt sein
					gamerunning = false;
				}
			}
			System.out.println("Nr"+this.number+" moved to ("+this.X+","+this.Y+").");
			if(!gamerunning){
				System.out.println("Sie haben ihr Ziel erreicht!");		
			}
		}
		else{
			System.out.println("Move mit ungultigem Parameter");
		}
	}
	// Methode Bombenlegen - NOCH NICHT EINSATZBEREIT
	public void layBomb(){
		if(this.currentbombs>0){
			//Bomb Bomb1 = new Bomb(this.X,this.Y,this.strength); // Noch zu korrigieren, Problem in Mail beschrieben
			this.currentbombs--;
			this.bombcounter++;
		}
	}
	// Methode DIE
	public void die(){
		if(this.shield==true){
			this.shield = false;
		}
		else{
			// Spiel mit Meldung "Verloren!" Abbrechen
			// Animation oder Anderung der Methode Draw?
		}
	}
	// Methoden Itemaufnahme
	public void takeShield(){
		this.shield = true;
	}
	public void takeGlove(){
		this.glove = true;
	}
	public void takePotion(){
		if(this.strength<8){
			this.strength++;
		}
	}
	public void takeBomb(){
		if(this.bombs<8){
			this.bombs++;
			this.currentbombs++;
		}
	}
	// Draw-Methode
	public void draw(){
		// Furs Erste Reicht schon ein Kreis
	}
}