import java.awt.Toolkit;


public class festemauer extends mauern{{
	
	
	
	bezeichner="Feste Mauer";
	zerst�rbar=false;
	
	// wenn sie nicht zerst�rbar ist, kann man ihr kein leben abziehen
	leben=1;
	aussehen=Toolkit.getDefaultToolkit().getImage( "/bilder/mauerfestklein.jpg" );
			

}}
