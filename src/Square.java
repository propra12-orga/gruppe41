public class Square{
	private boolean free; // WICHTIG: Soll false sein, falls dort eine Bombe liegt // Parameter neu gestalten //
	private boolean fragile;
	public Square (){
		this.free = true;
		this.fragile = false;
	}
	public boolean getfree(){
		return this.free;
	}
	public boolean getfragile(){
		return this.fragile;
	}
	public void setfree(boolean t){
		this.free = t;
	}
	public void setfragile(boolean t){
		this.fragile = t;
	}
	public void draw(){
	}
	public void destroy(){
		if(this.free==false){
			if(this.fragile==true){
				this.free=true;
			}
		}
	}
}