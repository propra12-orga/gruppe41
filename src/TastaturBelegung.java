import java.awt.*;

public class TastaturBelegung {
		
	Minion k=new Minion(1, 1,0, 6);//// Einfach für Map Zahl 6 eingegeben, damit kein fehler ausgegeben wird.
	public boolean keyDown (Event e, int key)
	  {
	    // linke Pfeiltaste
	    if (key == Event.LEFT)
	    {
	      // Minion k bewegt sich dann nach links
	      k.move(1);

	     
	    }
	    // rechte Pfeiltaste
	    else if (key == Event.RIGHT)
	    {
	      // Minion k bewegt sich dann nach rechts
	      k.move(1);

	 
	    }
	    // LeerTaste Bombenlegen
	    else if (key == 32)
	    {
	    	//k.laybomb(); //falls das überhaupt für bombenlegen gedacht war.
	    }
	    else if(key== Event.DOWN)
	    {
	    	// Pfeiltaste nach unten
	    	k.move(1);

	    }
	    else if (key== Event.UP)
	    {
	    	//Pfeiltaste nach oben
	    	k.move(1); 
	    }
	    else
	    {
	      // Falls was anderes getippt wird, kommt dies in die Konsole
	      System.out.println ("Charakter: " + (char)key + " Integer Value: " + key);
	    }

	    return true;
	  }

}

