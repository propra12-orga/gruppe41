import java.awt.Toolkit;


public class festemauer extends mauern{{
	
	
	
	bezeichner="Feste Mauer";
	zerstörbar=false;
	
	// wenn sie nicht zerstörbar ist, kann man ihr kein leben abziehen
	leben=1;
	aussehen=Toolkit.getDefaultToolkit().getImage( "/bilder/mauerfestklein.jpg" );
			

}}
