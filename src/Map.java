public class Map{
	private int size;
	private Square[][] fields;
	public Map (int s, Square[][] f){
		this.size = s;
		this.fields = f;
	}
	public void draw(){
	}
	public boolean getfree(int x, int y){
		return fields[x][y].getfree();
	}
	public boolean getfragile(int x, int y){
		return fields[x][y].getfragile();
	}
	public int getsize(){
		return this.size;
	}
}
