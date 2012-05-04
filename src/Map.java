/**************************************
 * 
 * 0 Freies Feld
 * 1 Bombe
 * 2 Entfernbare Mauer
 * 3 Statische Mauer
 * 4 Ausgang (Wichtig für ersten Meilenstein)
 *
 *************************************/
public class Map{
	// Variablen
	private int size;
	private int[][] fields;
	public Map (int s, int[][] f){
		this.size = s;
		this.fields = f;
	}
	// Draw-Methode - Strukturiert, aber unfertig
	public void draw(){
		for(int j=0;j<this.getSize();j++){
			for(int k=0;k<this.getSize();k++){
				if(fields[j][k]==0 || fields[j][k]==1){
					// LEERES FELD ODER LEERES FELD MIT BOMBE Weisses oder hellbraunes Quadrat zeichnen
				}
				if(fields[j][k]==2){
					// FRAGILE BARRIKADE Orangenes Quadrat zeichnen oder Bild einfugen
				}
				if(fields[j][k]==3){
					// BARRIKADE Rotes Quadrat zeichnen oder Bild einfugen
				}
				if(fields[j][k]==4){
					// AUSGANG Blaues Quadrat zeichnen oder Bild einfugen
				}				
			}
		}
	}
	// Get-Methoden
	public int getType(int x, int y){
		return fields[x][y];
	}
	public int getSize(){
		return this.size;
	}
	// Destroy
	public void destroy(int x, int y){
		if(this.fields[x][y]==1){
			// Bombe auf diesem Feld aktivieren
		}
		if(this.fields[x][y]==2){
			this.fields[x][y]=0;
		}		
	}
}
