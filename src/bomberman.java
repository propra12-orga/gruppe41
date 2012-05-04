public class bomberman {
	public static void main(String[] args) {
		// Boolean GameRunning
		boolean gamerunning = true;
		// Kartengrosse festlegen - Fehler behoben, 4<Size<16
		int i = 8;
		// Erstelle Objekt vom Typ Map
		Map Map1 = new Map(i);
		// Bearbeite Map
		Map1.cleanMap();
		//Map1.generateChaos(1);
		Map1.generateBlocks();
		Map1.cleanEdges();
		Map1.generateExit();
		// Erstelle Spielerfigur auf der Karte
		Minion Spieler = new Minion(1,0,0,Map1); //   <== Hier wird der Spieler erzeugt, ab hier kannst du die Move-Methode anwenden
		//---------------------------------------Testlauf
		// Bewege Spielfigur nach oben und rechts, schreibe Koordinaten auf Konsole
		for(int m=0;m<7;m++){
			Spieler.move(1);
			Map1.draw();
			Spieler.draw();
			Spieler.move(2);
			try{
				Thread.sleep(250);
			}
			catch(Exception e){
			}
			Map1.draw();
			Spieler.draw();
			try{
				Thread.sleep(250);
			}
			catch(Exception e){
			}
		}
		// Nach dem Durchlaufen dieser Methoden sollte die Konsole etwas ausgeben...bei mir lauft es!
		//---------------------------------------Ende Testlauf
	}
}