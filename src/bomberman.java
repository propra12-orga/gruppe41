public class bomberman {
	public static void main(String[] args) {
		// Boolean GameRunning
		boolean gamerunning = true;
		// Kartengrosse festlegen - ACHTUNG: Absturz in Methode Draw falls i = 0!
		int i = 8;
		// Leeres Kartenarray erstellen
		int[][] map0 = new int[i][i];
		for(int j=0;j<i;j++){
			for(int k=0;k<i;k++){
				map0[j][k] = 0;
			}
		}
		// Ausgang an Stelle 7,7 erstellen
		map0[7][7] = 4;
		// Hindernisse erstellen
		map0[7][1] = 3;
		map0[5][4] = 3;
		map0[6][3] = 2;
		map0[3][6] = 2;
		// Erstelle Objekt vom Typ Map
		Map Map1 = new Map(i,map0);
		// Erstelle Spielerfigur auf der Karte
		Minion Spieler = new Minion(1,0,0,Map1);
		// Male Karte
		Map1.draw();
		// Male Figur
		Spieler.draw();
		//---------------------------------------Testlauf
		// Bewege Spielfigur nach oben und rechts, schreibe Koordinaten auf Konsole
		for(int m=0;m<7;m++){
			Spieler.move(1);
			Map1.draw();
			Spieler.draw();
			Spieler.move(2);
			try{
				Thread.sleep(100);
			}
			catch(Exception e){
			}
			Map1.draw();
			Spieler.draw();
			try{
				Thread.sleep(100);
			}
			catch(Exception e){
			}
		}
		// Nach dem Durchlaufen dieser Methoden sollte die Konsole etwas ausgeben...bei mir lauft es!
		//---------------------------------------Ende Testlauf
	}
}