import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.lang.Thread;

public class Bomberman extends JFrame{

	public static Map map0;
	public static boolean up = false;
	public static boolean left = false;
	public static boolean down = false;
	public static boolean right = false;
	
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
			while(true){
				left = false;
				right = false;
				up = false;
				down = false;		
				try{
					Thread.sleep(10);
				}
				catch(Exception e){
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
		
	public static void main(String[] args) {
		int i = 8;
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