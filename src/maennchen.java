import java.awt.*;


public class maennchen {
	
	public Image aussehen=Toolkit.getDefaultToolkit().getImage( "/bilder/maenchen1.jpg" );
	
	public int xposition;
	public int yposition;
	public double geschwindigkeit;
	public int leben;
	public String[] gegenstaende;
	
	public void draw(Graphics grafik,int x,int y) {
		grafik.drawImage(aussehen,x,y,null);
	}
	
	
	//Konstruktor, notwendig sollten wir in den optionen die möglichkeit bereitstellen, aus mehreren design wählen zu können
	
	//maennchen(Image image) {
		//aussehen = image;
	//}
	
	
	


}
