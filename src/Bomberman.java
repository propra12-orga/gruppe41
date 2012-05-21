import javax.swing.JFrame;


import java.util.Iterator;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.lang.Thread;
import java.util.ArrayList;

public class Bomberman extends JFrame{

	public static Map map0;
	public static boolean up = false;
	public static boolean left = false;
	public static boolean down = false;
	public static boolean right = false;
	public static boolean bomb = false;
	
	
	public Bomberman(Map ma){
		super("MasterBomb!");
		this.map0 = ma;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(400,300);
		setLocation(100,100);
		setVisible(true);
		addKeyListener(new MyKeyListener());
	}
	
	private class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_UP){
				Bomberman.up = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				Bomberman.right = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				Bomberman.down = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				Bomberman.left = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				Bomberman.bomb = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				System.exit(0);
			}
		}
	}
	public static class Bomber implements Runnable{
		private Minion mini;
		
		public Bomber(Minion mi){
			this.mini = mi;
		}
		
		public void run(){
			map0.draw();
			mini.draw();
			Iterator <Bomb> it;
			Bomb b;
			
			while(true){
				left = false;
				right = false;
				up = false;
				down = false;
				bomb = false;
				boolean explodiert=false;	//ist eine Bombe in einer Iteration explodiert?
				try{
					Thread.sleep(10);
				}
				catch(Exception e){
				}
				
				//Bomben explodieren
				synchronized (map0.bombs) {
					//durchläuft alle Bomben in der Liste
					it = map0.bombs.iterator();	
					while (it.hasNext()) {
						b = it.next();
						if (b.getTimePlaced()+b.getTimer() <= System.currentTimeMillis()) {		//Zeit abgelaufen?
							explodiert = true;
							b.explosion();			//explodieren	
							it.remove();			//Bombe aus der Liste entfernen
						}
					}
					
					//durchläuft alle Bomben in der Liste
					while (explodiert) {
						explodiert = false;
						it = map0.bombs.iterator();
						while (it.hasNext()) {
							b = it.next();
							if (b.getReady()) {			//Bombe soll explodieren?
								explodiert = true;
								b.explosion();			//explodieren	
								it.remove();			//Bombe aus der Liste entfernen
							}
						}
					}
				}
				//Bombe wird gelegt
				if(bomb==true){
					if (map0.getType(mini.getX(), mini.getY()) != 1) {		//es darf keine Bombe auf dem Feld liegen
						map0.setType(1,mini.getX(),mini.getY());
						b = new Bomb(mini,map0);
						synchronized (map0.bombs) {
							map0.bombs.add(b);
						}
					}
				}
				if(up==true){
					mini.move(1);
					mini.draw();
					if(mini.getY()>0)map0.draw(mini.getX(),mini.getY()-1);
				}
				if(right==true){
					mini.move(2);
					mini.draw();
					if(mini.getX()>0)map0.draw(mini.getX()-1,mini.getY());
				}
				if(down==true){
					mini.move(3);
					mini.draw();
					if(mini.getY()<map0.getSize()-1)map0.draw(mini.getX(),mini.getY()+1);
				}
				if(left==true){
					mini.move(4);
					mini.draw();
					if(mini.getX()<map0.getSize()-1)map0.draw(mini.getX()+1,mini.getY());
				}
			}
		}
	}
		
	public void spiele(int i) {                                                  //hier eigentlich main methode 
		
		Map Map1 = new Map(i);
		Map1.cleanMap();
		Map1.generateBlocks();
		Map1.generateExit();
		Minion Spieler1 = new Minion(1,0,0,Map1);


		
		new Bomberman(Map1);
		Thread Player1 = new Thread(new Bomberman.Bomber(Spieler1));
		Player1.start();

		//addPlayer(Spieler);
		//---------------------------------------Ende
	}
}