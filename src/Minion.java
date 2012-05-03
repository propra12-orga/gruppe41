public class Minion{
	private boolean alive = true;
	private boolean shield = false;
	private boolean glove = false;
	private int X;
	private int Y;
	private int strength = 1;
	private int bombs = 1;
	private Map Map1;
	public Minion (int x, int y, Map map1){
		this.X = x;
		this.Y = y;
		this.Map1 = map1;
	}
	public boolean getshield(){
		return this.shield;
	}
	public boolean getglove(){
		return this.glove;
	}
	public int getx(){
		return this.X;
	}
	public int gety(){
		return this.Y;
	}
	public int getstrength(){
		return this.strength;
	}
	public int getbombs(){
		return this.bombs;
	}
	public void moveleft(){
		if(Map1.getfree(this.X+1, this.Y)==true){ // WICHTIG: Zugriff auf die Karte - Name der Karte Map1
			this.X++;
		}
	}
	public void moveright(){
		if(Map1.getfree(this.X-1, this.Y)==true){
			this.X--;
		}
	}
	public void moveup(){
		if(Map1.getfree(this.X, this.Y+1)==true){
			this.Y++;
		}
	}
	public void movedown(){
		if(Map1.getfree(this.X, this.Y-1)==true){
			this.Y--;
		}
	} // WICHTIG: ArrayOutOfBoundsException noch nicht behandelt!
	public void laybomb(){
		Bomb Bomb1 = new Bomb(this.X, this.Y, this.strength);// WICHTIG: Wie gestalte ich die Namensgebung der Bomben, so dass diese nicht alle gleich heissen?
	}
	public void die(){
		if(this.shield==true){
			this.shield = false;
		}
		else{
			this.alive = false;
		}
	}
	public void takeshield(){
		this.shield = true;
	}
	public void takeglove(){
		this.glove = true;
	}
	public void takepotion(){
		this.strength++;
	}
	public void takebomb(){
		this.bombs++;
	}
}