import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class bomberman extends Canvas{
	
	
		
	public bomberman() {
		
		
		// Spielfl�che
		final JFrame spielflaeche = new JFrame("bomber");
		JPanel panel = (JPanel) spielflaeche.getContentPane();
		panel.setPreferredSize(new Dimension(800,600));
		panel.setLayout(null);
		setBounds(0,0,800,600);
		panel.add(this);
		setIgnoreRepaint(false);
		spielflaeche.pack();
		spielflaeche.setResizable(false);
		spielflaeche.setVisible(true);
		spielflaeche.addWindowListener(new WindowAdapter(){
			// versuche es hinzubekommen, dass das men� unsichtbar wird, wenn ich das spiel start und wenn ich spiel schlie�e, dass das menu wierder
			// erscheint
			public void windowClosing(WindowEvent e) {
				
				// dispose() m�sste doch das gleiche machen wie System.exit(0) oder?
				spielflaeche.dispose();
			}});
		
		
			
		}
	
		
	public static void main(String[] args) {
		
		
		bomberman bomber= new bomberman();
	}
}