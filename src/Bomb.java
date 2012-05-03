public class Bomb{
	private int X;
	private int Y;
	private int timer;
	private int strength;
	public Bomb (int x, int y, int s){
		this.X = x;
		this.Y = y;
		this.timer = 0;
		this.strength = s;
	}
	public void draw(){
	}
	public void blowup(){
		//StdAudio.play(Boom.wav);
	}
	public void fly(){
	}
}