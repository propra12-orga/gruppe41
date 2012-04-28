import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout.Alignment;
import javax.swing.*;
import java.awt.*;

public class bomberman extends Canvas{
	
	
		
	public bomberman() {
		
		
		// Spielfläche
		final JFrame spielflaeche = new JFrame("bomber");
		JPanel panel = (JPanel) spielflaeche.getContentPane();
		panel.setPreferredSize(new Dimension(800,600));
		panel.setLayout(null);
		setBounds(0,0,800,600);
		panel.add(this);
		setIgnoreRepaint(false);
		spielflaeche.setLocation(new Point(300, 120));
		spielflaeche.pack();
		spielflaeche.setResizable(false);
		spielflaeche.setVisible(true);
		spielflaeche.addWindowListener(new WindowAdapter(){
			// versuche es hinzubekommen, dass das menü unsichtbar wird, wenn ich das spiel start und wenn ich spiel schließe, dass das menu wierder
			// erscheint
			public void windowClosing(WindowEvent e) {
				
				// dispose() müsste doch das gleiche machen wie System.exit(0) oder?
				spielflaeche.dispose();
			}});
		
		
			
		}
	
		
	public static void main(String[] args) {
		
		
		bomberman bomber= new bomberman();
	}
}