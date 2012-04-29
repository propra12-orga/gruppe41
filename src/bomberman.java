import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.GroupLayout.Alignment;
import javax.swing.*;
import java.awt.*;
import java.net.URL;



public class bomberman extends Canvas{
	
	private BufferStrategy bildstrategie;
		
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
		maennchen man=new maennchen();
		
		
		
		
		// versuche auf das panel die grafik zu laden, auf der dann die einzelnen komponenten gezeichnet werden können
		// createBufferStrategy(2);
		//bildstrategie = getBufferStrategy();
		//bildstrategie.show();
		//Graphics2D grafik = (Graphics2D) bildstrategie.getDrawGraphics();
		//grafik.setColor(Color.black);
		//grafik.fillRect(0,0,800,600);
		//man.draw(grafik,400,300);
		//panel.paintComponents(grafik);
		//spielflaeche.add(grafik);
		//JLabel lab = new JLabel(new ImageIcon("/bilder/maenchen1.jpg"));
		//lab.setVisible(true);
		
		
		
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