/*
 * Die Haupt Klasse
 * Von hier aus werden alle anderen Klassen gestartet.
 */

package pokemon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Game {
		
	public static void main(String args[]){
		Statisches.setScanner();
		Statisches.einlesen();
		
		
		//Locations aus XML laden:
		LocationFactory factory = new LocationFactory("./Locations.xml");
		factory.getAllLocations();
		
		//Hier muss Abfrage stattfinden: erstes laden oder gibt es einen Speicherstand?
		//Trainer laden:
		Trainer t = Statisches.gespeicherterTrainer();
		t.setLocation(1);
		//Start Tokens laden:
		for(int key : LocationFactory.locations.keySet()){
			t.addToken(LocationFactory.locations.get(key).getName()+"Default");
		}
		
		
		//Spiel starten:
		while(true){
			LocationFactory.locations.get(t.getLocationId()).runLocation(t);
		}
		
		
//		kaitest();
//		Statisches.closeScanner();
		    
	}
	
	//Methoden:
	
	public static void kaitest(){		
		Pokemon p = new Pokemon(PokeNamen.MEWTU, 24);
		System.out.println(p);
	}
}
