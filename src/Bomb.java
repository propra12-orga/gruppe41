
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.Toolkit.*;
import java.awt.Graphics.*;
import javax.swing.JPanel;
import java.applet.*;




public class Bomb {
	private Minion Min;
	private Map map0;
	private int X;
	private int Y;
	private long timer;
	private long timePlaced;
	private int bomben;
	private int strength;
	private boolean ready;			//soll explodieren, falls ready = true
//	public static boolean layBomb = false;
	
	// Konstruktor
	
	public Bomb (Minion min,Map ma)
	{
		this.Min=min;
		this.map0 = ma;
		this.X = min.getX();   // holt die x-Position vom Spieler/Minion
		this.Y = min.getY();   // holt die y-Position vom Spieler
		this.timer = 2000;
		this.timePlaced = System.currentTimeMillis();
		this.strength = 3;
		System.out.println("Bombe gelegt auf ("+X+","+Y+")");
	//	addKeyListener(new MyKeyListener());
	}
	  
/*	private class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_CONTROL){
				//Min.layBomb()=true;
			}
		}
	}
	public void bombelegen(){
		int x_pos=this.X;
		int y_pos=this.Y;
		//drawImage("bombeklein.jpg",x_pos,y_pos);
	}*/

	
	// Explosionsmethode
	public void explosion(){
		map0.setType(0, X, Y);
		map0.draw(X, Y);
		int type;
		
		//rechts
		for( int i=1;i<=strength;i++) {
			if (X+i < map0.getSize()){			//ARBEITEN
			type = map0.getType(X+i, Y);
			if (type == 3){		//Mauer
				break;
			}
			
			if (type == 1){
				System.out.println(X+i + ", " + Y);
				map0.getBomb(X+i, Y).setReady(true);	//Bombe im Explosionsweg als ready markieren
			}
			if ( type == 2 || type == 5){		//entferne Mauern und Items, danach breche ab
				map0.setType(0, X+i, Y);
				map0.draw(X,Y);
				break;
			}	
		}
		}
		
		//links
		for( int i=1;i<=strength;i++) {
			if (X-i >= 0){			//ARBEITEN
			type = map0.getType(X-i, Y);
			if (type == 3){		//Mauer
				break;
			}
			
			if (type == 1){
				System.out.println(X-i + ", " + Y);
				map0.getBomb(X-i, Y).setReady(true);	//Bombe im Explosionsweg als ready markieren
			}
			if ( type == 2 || type == 5){		//entferne Mauern und Items, danach breche ab
				map0.setType(0, X-i, Y);
				map0.draw(X,Y);
				break;
			}	
		}
		}
		//oben
		for( int i=1;i<=strength;i++) {
			if (Y+i < map0.getSize()){			//ARBEITEN
			type = map0.getType(X, Y+i);
			if (type == 3){		//Mauer
				break;
			}
			
			if (type == 1){
				System.out.println(X + ", " + Y+i);
				map0.getBomb(X, Y+i).setReady(true);	//Bombe im Explosionsweg als ready markieren
			}
			if ( type == 2 || type == 5){		//entferne Mauern und Items, danach breche ab
				map0.setType(0, X, Y+i);
				map0.draw(X, Y);
				break;
			}	
		}
		}		
		
		//unten
				for( int i=1;i<=strength;i++) {
			if (Y-i >= 0){			//ARBEITEN
			type = map0.getType(X, Y-i);
			if (type == 3){		//Mauer
				break;
			}
			
			if (type == 1){
				System.out.println(X + ", " + Y+-i);
				map0.getBomb(X, Y-i).setReady(true);	//Bombe im Explosionsweg als ready markieren
			}
			if (type == 2 || type == 5){		//entferne Mauern und Items, danach breche ab
				map0.setType(0, X, Y-i);
				map0.draw(X,Y);
				break;
			}	
		}
		}
		
		/*
		int a=this.X;	
		int b=this.Y;
		int s=this.strength;
		
		boolean rechtsok[]=new boolean[s];
		boolean linksok[]=new boolean[s];
		boolean obenok[]=new boolean[s];
		boolean untenok[]=new boolean[s];;

							
	
		for( int i=1;i<=s;i++) {
			rechtsok[i]=false;
			if (map0.getType(a+i, b)==3) {break;	}
			else {rechtsok[i]=true; }
		}
		
		for( int i=1;i<=s;i++) {
			obenok[i]=false;
			if(map0.getType(a, b+i)==3) {break;	}
			else {obenok[i]=true;};
		}
		
		for( int i=1;i<=s;i++) {			
			linksok[i]=false;
			if(map0.getType(a-i, b)==3) {break;	}
			else {linksok[i]=true;};
		}
		
		for( int i=1;i<=s;i++) {		
			untenok[i]=false;
			if(map0.getType(a, b-i)==3) {break;	}
			else {untenok[i]=true;};
			}
		
		for(int i=1;i<=s;i++ ){
			Thread th= new Thread();
			if(rechtsok[i]==true){
				map0.destroy(x, y)
			}
			if(linksok[i]==true){}
			if(obenok[i]==true){}
			if(untenok[i]==true){}
			
			th.start();
				try{
					Thread.sleep(2);
					}
				catch(Exception e ) {
					//nichts
					}
			}
		*/
	}
		
		//StdAudio.play(Boom.wav);
	
	// Wurfmethode (Bomben schieben mit dem Handschuh)
	public void fly(){
	}
	
	
	public void setReady(boolean a){
		ready = a;
	}
	
	public boolean getReady() {
		return ready;
	}
	
	public int getX(){
		return X;
	}
	
	public int getY(){
		return Y;
	}
	
	public long getTimePlaced(){
		return timePlaced;
	}
	public long getTimer(){
		return timer;
	}
	public int getBomben(){
		return bomben;
	}
}