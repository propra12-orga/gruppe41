import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class bomberman extends Canvas{
	
	
		
	public bomberman() {
		
		
		// Spielfläche
		JFrame spielflaeche = new JFrame("bomber");
		JPanel panel = (JPanel) spielflaeche.getContentPane();
		panel.setPreferredSize(new Dimension(800,600));
		panel.setLayout(null);
		setBounds(0,0,800,600);
		panel.add(this);
		setIgnoreRepaint(true);
		spielflaeche.pack();
		spielflaeche.setResizable(false);
		spielflaeche.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}});
		
		
			
		}
	
		
	public static void main(String[] args) {
		
		
		bomberman BOMBER= new bomberman();
	}
}