public class Bomb{
	// Variablen
	private int X;
	private int Y;
	private int timer;
	private int strength;
	// Konstruktor
	public Bomb (int x, int y, int s){
		this.X = x;
		this.Y = y;
		this.timer = 0;
		this.strength = s;
	}
	// Draw-Methode
	public void draw(){
	}
	// Explosionsmethode
	public void blowup(){
		//StdAudio.play(Boom.wav);
	}
	// Wurfmethode (Bomben schieben mit dem Handschuh)
	public void fly(){
	}
}