

public class spielfeld {

	// zwar ein komplett gezeichnetes spielfeld, aber es w�re nicht schlecht, wenn endlich das �bungsblatt rauskommen
	// w�rde, damit man die genaue aufgabenstellung sehen kann.    und ich muss mal richtig gucken, was bomberman 
	// �berhaupt ist
	
	// man k�nnte sp�ter den konstruktor mit einer path-variable f�r bilder versehen, damit man durch die optionen
	// andere skins f�r den bomberman ausw�hlen kann und diese dann dort gezeichnet werde.....
		spielfeld(){
		StdDraw.setXscale(0.0,1024.0);
		StdDraw.setYscale(0.0,1024.0);

		StdDraw.picture(110.0,110.0,"/bilder/maenchen1.1.jpg");





		StdDraw.picture(110.0,220.0,"/bilder/mauerfestklein1.1.jpg");
		StdDraw.picture(110.0,330.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(110.0,440.0,"/bilder/mauerfestklein1.1.jpg");
		StdDraw.picture(110.0,550.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(110.0,660.0,"/bilder/mauerfestklein1.1.jpg");
		StdDraw.picture(110.0,770.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(110.0,880.0,"/bilder/mauerfestklein1.1.jpg");


		StdDraw.picture(220.0,220.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(220.0,330.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(220.0,440.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(220.0,550.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(220.0,660.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(220.0,770.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(220.0,880.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");




		StdDraw.picture(330.0,220.0,"/bilder/mauerfestklein1.1.jpg");
		StdDraw.picture(330.0,330.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(330.0,440.0,"/bilder/mauerfestklein1.1.jpg");
		StdDraw.picture(330.0,550.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(330.0,660.0,"/bilder/mauerfestklein1.1.jpg");
		StdDraw.picture(330.0,770.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(330.0,880.0,"/bilder/mauerfestklein1.1.jpg");



		StdDraw.picture(440.0,220.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(440.0,330.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(440.0,440.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(440.0,550.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(440.0,660.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(440.0,770.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(440.0,880.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");


		StdDraw.picture(550.0,220.0,"/bilder/mauerfestklein1.1.jpg");
		StdDraw.picture(550.0,330.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(550.0,440.0,"/bilder/mauerfestklein1.1.jpg");
		StdDraw.picture(550.0,550.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(550.0,660.0,"/bilder/mauerfestklein1.1.jpg");
		StdDraw.picture(550.0,770.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(550.0,880.0,"/bilder/mauerfestklein1.1.jpg");



		StdDraw.picture(660.0,220.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(660.0,330.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(660.0,440.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(660.0,550.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(660.0,660.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(660.0,770.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(660.0,880.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");


		StdDraw.picture(770.0,220.0,"/bilder/mauerfestklein1.1.jpg");
		StdDraw.picture(770.0,330.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(770.0,440.0,"/bilder/mauerfestklein1.1.jpg");
		StdDraw.picture(770.0,550.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(770.0,660.0,"/bilder/mauerfestklein1.1.jpg");
		StdDraw.picture(770.0,770.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(770.0,880.0,"/bilder/mauerfestklein1.1.jpg");



		StdDraw.picture(880.0,220.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(880.0,330.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(880.0,440.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(880.0,550.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(880.0,660.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(880.0,770.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");
		StdDraw.picture(880.0,880.0,"/bilder/mauerfzerstoerbarklein1.1.jpg");



	
		}
}
