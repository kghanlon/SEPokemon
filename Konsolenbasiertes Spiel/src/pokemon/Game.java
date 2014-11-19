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
	
	public static Map<Integer, Location> locations = new HashMap<Integer, Location>();
	
	public static void main(String args[]){
		/*
		Document doc = null;
		    
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse( new File("Locations.xml") );
		} catch(ParserConfigurationException e){
		   	e.printStackTrace();
		} catch (SAXException e){
		   	e.printStackTrace();
		}catch (IOException e){
		   	e.printStackTrace();
		}
		
		//Locations aus XML laden:
		getAllLocations(doc.getFirstChild().getChildNodes());
		
		//Trainer laden:
		Trainer t = new Trainer(1, new ArrayList<Integer>());
		
		//Spiel starten:
		while(true){
			locations.get(t.getLocationId()).runLocation(t);
		}*/
		HashMap<Integer, String> atthash = new HashMap<>();
		atthash.put(0, "Donnerblitz#elektro#0.9#80");
		atthash.put(1, "Funkensprung#elektro#1.0#40");
		atthash.put(2, "Rutenschlag#normal#1.0#0");
		atthash.put(3, "Ruckzuckhieb#normal#1.0#60");
		atthash.put(4, "Bodyslam#normal#0.8#70");
		HashMap<Integer, String> pokehash = new HashMap<>();
		pokehash.put(0, "Pikachu#elektro#elektro");
		Attacke.setAtthash(atthash);
		Pokemon.setPokehash(pokehash);
		int [] att = {2};
		HashMap<Integer, Attacke> moeglicheAttacken = new HashMap<>();
		moeglicheAttacken.put(5, new Attacke(2));
		moeglicheAttacken.put(10, new Attacke(1));
		moeglicheAttacken.put(15, new Attacke(3));
		moeglicheAttacken.put(20, new Attacke(0));
		moeglicheAttacken.put(25, new Attacke(4));
		Pokemon pikachu = new Pokemon(0,9,8, att, 120, 67, 75, 40, 100, (float)0.95, moeglicheAttacken);
		System.out.println(pikachu);
		System.out.println(pikachu.ausgabeAttacken());
		pikachu.expGewinn(10000);
		System.out.println(pikachu);
		    
	}
	
	//Methoden:
	
	public static void getAllLocations(NodeList nodes){
		//Hier werden alle Locations die in der XML stehen in die
		//HashMap geschrieben.
		
		//Geht alle location Tags durch
		for(int i = 1; i < nodes.getLength(); i+=2){
			
			//Namen der Location:
			String locName = nodes.item(i).getAttributes().getNamedItem("name").getNodeValue();
			
			//Location ID:
			int locId = Integer.parseInt(nodes.item(i).getAttributes().getNamedItem("id").getNodeValue());
			
			//Events f�r die location holen:
			List <Event> events = new ArrayList<Event>();
			
			//Geht alle Event Tags durch:
			for(int j = 1; j < nodes.item(i).getChildNodes().getLength(); j+=2){
				
				int typ = Integer.parseInt(nodes.item(i).getChildNodes().item(j)
						 .getAttributes().getNamedItem("typ").getNodeValue());
				
				switch (typ) {
				case 0:
					events.add(getInformationEvent(nodes, i, j));
					break;
				case 1:
					events.add(getChangeLocationEvent(nodes, i, j));
					break;
				default:
					break;
				}
			}
			locations.put(locId, new Location(locId, locName, events));
		}
	}
	
	public static InformationEvent getInformationEvent(NodeList nodes, int i, int j){
		//Get Required Token List:
		List<Integer> reqTokens = getReqTokenList(nodes, i, j);
		//Get Command:
		String command = getCommand(nodes, i, j);
		//Get Info:
		String info = getInfo(nodes, i, j);
		return new InformationEvent(reqTokens, command, info);
	}
	
	public static ChangeLocationEvent getChangeLocationEvent(NodeList nodes, int i, int j){
		//Get Required Token List:
		List<Integer> reqTokens = getReqTokenList(nodes, i, j);
		//Get Command:
		String command = getCommand(nodes, i, j);
		//Get Info:
		String info = getInfo(nodes, i, j);
		//Get Next Location ID:
		int nextLocId = Integer.parseInt(nodes.item(i).getChildNodes().item(j)
					.getAttributes().getNamedItem("nextLocId").getNodeValue());
		return new ChangeLocationEvent(reqTokens, command, info, nextLocId);
	}
	
	
	public static List<Integer> getReqTokenList(NodeList nodes, int i, int j){

		List<Integer> reqTokens = new ArrayList<Integer>();
		//String mit allen required Tokens:
		String tokString = nodes.item(i).getChildNodes().item(j).getAttributes()
						  .getNamedItem("reqToken").getNodeValue();
		//Diese einzeln zur Liste hinzuf�gen:
		for(int k = 0; k < tokString.split(";").length; k++){
			reqTokens.add(Integer.parseInt(tokString.split(";")[k]));
		}
		return reqTokens;
	}
	
	public static String getCommand(NodeList nodes, int i, int j){
		String command = nodes.item(i).getChildNodes().item(j).getAttributes()
				.getNamedItem("command").getNodeValue();
		return command;
	}
	
	public static String getInfo(NodeList nodes, int i, int j){
		String info = nodes.item(i).getChildNodes().item(j).getTextContent().trim();
		return info;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
