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
	
//	public static Map<Integer, Location> locations = new HashMap<Integer, Location>();
	
	public static void main(String args[]){
		Statisches.setScanner();
		Statisches.einlesen();
		
//		Document doc = null;
//		    
//		try{
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			doc = builder.parse( new File("./Locations.xml") );
//		} catch(ParserConfigurationException e){
//		   	e.printStackTrace();
//		} catch (SAXException e){
//		   	e.printStackTrace();
//		}catch (IOException e){
//		   	e.printStackTrace();
//		}
		
		//Locations aus XML laden:
		LocationFactory factory = new LocationFactory("./Locations.xml");
		factory.getAllLocations();
//		getAllLocations(doc.getFirstChild().getChildNodes());
		
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
//		Statisches.closScanner();
		    
	}
	
	//Methoden:
	
	public static void kaitest(){		
//		Statisches.einlesen();
		Trainer t = Statisches.gespeicherterTrainer();
		//szenario mit wildem pokemon wild=true
		List<Pokemon> g = new ArrayList<>();
		g.add(new Pokemon(PokeNamen.PIKACHU, 15));
		Kampf.start(t, g, true);
		//ab hier szenario mit gengerischem trainer deswegen wild auf false
		System.out.println("AB HIER TRAINERKAMPF");
		g = new ArrayList<>();
		g.add(new Pokemon(PokeNamen.PIKACHU, 20));
		g.add(new Pokemon(PokeNamen.GLUMANDA, 20));
		Kampf.start(t, g, false);
	}
	
//	public static void getAllLocations(NodeList nodes){ //nodes = alle locations in der welt.
//		//Hier werden alle Locations die in der XML stehen in die
//		//HashMap geschrieben.
//		
//		//Geht alle location Tags durch
//		for(int i = 1; i < nodes.getLength(); i+=2){
//			
//			//Namen der Location:
//			String locName = nodes.item(i).getAttributes().getNamedItem("name").getNodeValue();
//			
//			//Location ID:
//			int locId = Integer.parseInt(nodes.item(i).getAttributes().getNamedItem("id").getNodeValue());
//			
//			//Events fuer die location holen:
//			List <Event> events = getAllEvents(nodes, i);
//
//			locations.put(locId, new Location(locId, locName, events));
//		}
//	}
	
//	//Gets all the events that belong to the i-th item of nodes:
//	public static List<Event> getAllEvents(NodeList nodes, int i){
//		List<Event> events = new ArrayList<Event>();
//		//Geht alle Event Tags durch:
//		for(int j = 1; j < nodes.item(i).getChildNodes().getLength(); j+=2){
//			
//			//Und fuegt das richte Event zur Liste hinzu.
//			String typ = nodes.item(i).getChildNodes().item(j).getNodeName();
//			
//			switch (typ) {
//			case "InformationEvent":
//				events.add(getInformationEvent(nodes, i, j));
//				break;
//			case "ChangeLocationEvent":
//				events.add(getChangeLocationEvent(nodes, i, j));
//				break;
//			case "CompoundEvent":
//				events.add(getCompoundEvent(nodes, i, j));
//				break;
//			case "RemoveTokenEvent": 
//				events.add(getRemoveTokenEvent(nodes, i, j));
//				break;
//			case "GrantTokenEvent":
//				events.add(getGrantTokenEvent(nodes, i, j));
//				break;
//			case "WildPokemonEvent":
//				events.add(getWildPokemonEvent(nodes, i, j));
//			default:
//				break;
//			}
//		}
//		return events;
//	}
//	
//	public static InformationEvent getInformationEvent(NodeList nodes, int i, int j){
//		//Get Required Token List:
//		List<String> reqTokens = getReqTokenList(nodes, i, j);
//		//Get Required NonToken List:
//		List<String> reqNonTokens = getReqNonTokenList(nodes, i, j);
//		//Get Command:
//		String command = getCommand(nodes, i, j);
//		//Get Info:
//		String info = getInfo(nodes, i, j);
//		return new InformationEvent(reqTokens, reqNonTokens, command, info);
//	}
//	
//	public static ChangeLocationEvent getChangeLocationEvent(NodeList nodes, int i, int j){
//		//Get Required Token List:
//		List<String> reqTokens = getReqTokenList(nodes, i, j);
//		//Get Required NonToken List:
//		List<String> reqNonTokens = getReqNonTokenList(nodes, i, j);
//		//Get Command:
//		String command = getCommand(nodes, i, j);
//		//Get Info:
//		String info = getInfo(nodes, i, j);
//		//Get Next Location ID:
//		int nextLocId = getNextLocId(nodes, i, j);
//		return new ChangeLocationEvent(reqTokens, reqNonTokens, command, info, nextLocId);
//	}
//	
//	public static CompoundEvent getCompoundEvent(NodeList nodes, int i, int j){
//		//Get Required Token List:
//		List<String> reqTokens = getReqTokenList(nodes, i, j);
//		//Get Required NonToken List:
//		List<String> reqNonTokens = getReqNonTokenList(nodes, i, j);
//		//Get Command:
//		String command = getCommand(nodes, i, j);
//		//Get Events that belong to the CompountEven:
//		List<Event> compEvents = getAllEvents(nodes.item(i).getChildNodes(), j);
//		return new CompoundEvent(reqTokens, reqNonTokens, command, compEvents);
//	}
//	
//	public static RemoveTokenEvent getRemoveTokenEvent(NodeList nodes, int i, int j){
//		//Get Required Token List:
//		List<String> reqTokens = getReqTokenList(nodes, i, j);
//		//Get Required NonToken List:
//		List<String> reqNonTokens = getReqNonTokenList(nodes, i, j);
//		//Get Command:
//		String command = getCommand(nodes, i, j);
//		//Get Token that should be removed:
//		String token = nodes.item(i).getChildNodes().item(j)
//					.getAttributes().getNamedItem("token").getNodeValue();
//		
//		return new RemoveTokenEvent(reqTokens, reqNonTokens, command, token);
//	}
//	
//	public static GrantTokenEvent getGrantTokenEvent(NodeList nodes, int i, int j){
//		//Get Required Token List:
//		List<String> reqTokens = getReqTokenList(nodes, i, j);
//		//Get Required NonToken List:
//		List<String> reqNonTokens = getReqNonTokenList(nodes, i, j);
//		//Get Command:
//		String command = getCommand(nodes, i, j);
//		//Get Token that should be added:
//		String token = nodes.item(i).getChildNodes().item(j)
//				.getAttributes().getNamedItem("token").getNodeValue();
//		
//		return new GrantTokenEvent(reqTokens, reqNonTokens, command, token);
//	}
//	
//	public static WildPokemonEvent getWildPokemonEvent(NodeList nodes, int i, int j){
//		//Get Required Token List:
//		List<String> reqTokens = getReqTokenList(nodes, i, j);
//		//Get Required NonToken List:
//		List<String> reqNonTokens = getReqNonTokenList(nodes, i, j);
//		//Get Command:
//		String command = getCommand(nodes, i, j);
//		//Get the available Pokemon:
//		List<PokeNamen> pokemon = getWildPokemon(nodes, i, j);
//		//Get the min level for the pokemon here
//		int minlvl = Integer.parseInt(nodes.item(i).getChildNodes().item(j).getAttributes()
//										.getNamedItem("minlvl").getNodeValue());
//		//get the max level for the pokemon here
//		int maxlvl = Integer.parseInt(nodes.item(i).getChildNodes().item(j).getAttributes()
//				.getNamedItem("maxlvl").getNodeValue());
//		//Get the Location of the nearest PokeCenter:
//		int nextPokeCenterLocId = Integer.parseInt(nodes.item(i).getChildNodes().item(j).getAttributes()
//				.getNamedItem("nextPokeCenterLocId").getNodeValue());
//		
//		return new WildPokemonEvent(reqTokens, reqNonTokens, command, pokemon, minlvl, maxlvl, nextPokeCenterLocId);
//	}
//	
//	
//	//Attribute optional
//	public static List<String> getReqTokenList(NodeList nodes, int i, int j){
//
//		List<String> reqTokens = new ArrayList<String>();
//		//String mit allen required Tokens:
//		String tokString = "";
//		try{
//			tokString = nodes.item(i).getChildNodes().item(j).getAttributes()
//							  .getNamedItem("reqToken").getNodeValue();
//		} catch (NullPointerException e){
//			//The event does not have a reqToken Attribute. That should be ok!
//			//Do nothing, tokString is empty.
//		}
//		if(tokString.equals("")){
//			//Es gibt keine req Tokens:
//			return reqTokens;
//		} else {
//			//Es gibt required Tokens:
//			//Diese einzeln zur Liste hinzufuegen:
//			for(int k = 0; k < tokString.split(";").length; k++){
//				reqTokens.add(tokString.split(";")[k]);
//			}
//			return reqTokens;
//		}
//	}
//	
//	//Attribute optional
//	public static List<String> getReqNonTokenList(NodeList nodes, int i, int j){
//
//		List<String> reqNonTokens = new ArrayList<String>();
//		//String mit allen required NonTokens:
//		String tokString = "";
//		try{
//			tokString = nodes.item(i).getChildNodes().item(j).getAttributes()
//							  .getNamedItem("reqNonToken").getNodeValue();
//		} catch (NullPointerException e){
//			//The event does not have a reqNonToken Attribute. That should be ok!
//			//Do nothing, tokString is empty.
//		}
//		if(tokString.equals("")){
//			//Es gibt keine req NonTokens:
//			return reqNonTokens;
//		} else {
//			//Es gibt required NonTokens:
//			//Diese einzeln zur Liste hinzufuegen:
//			for(int k = 0; k < tokString.split(";").length; k++){
//				reqNonTokens.add(tokString.split(";")[k]);
//			}
//			return reqNonTokens;
//		}
//	}
//	
//	
//	//Attribute optional.
//	public static String getCommand(NodeList nodes, int i, int j){
//		String command = "##"; //shouldn't really be used by mistake
//		try{
//			command = nodes.item(i).getChildNodes().item(j).getAttributes()
//					.getNamedItem("command").getNodeValue();
//		} catch (NullPointerException e){
//			//The event does not have a Command Attribute, That should be ok!
//			//Do nothing, the default command ist set
//		}
//		return command;
//	}
//	
//	//Attribute required!
//	public static String getInfo(NodeList nodes, int i, int j){
//		String info = nodes.item(i).getChildNodes().item(j).getTextContent().trim().replaceAll("\\t+", "");
//		return info;
//	}
//	
//	//Attribute required!
//	public static int getNextLocId(NodeList nodes, int i, int j){
//		int ret = Integer.parseInt(nodes.item(i).getChildNodes().item(j)
//				.getAttributes().getNamedItem("nextLocId").getNodeValue());
//		return ret;
//	}
//	
//	//Attribute required!
//	public static List<PokeNamen> getWildPokemon(NodeList nodes, int i, int j){
//		List<PokeNamen> pokemon = new ArrayList<PokeNamen>();
//		//String mit allen vorhandenen pokemon:
//		String pokString = nodes.item(i).getChildNodes().item(j).getAttributes()
//				  .getNamedItem("pokemon").getNodeValue();
//		if(pokString.equals("")){
//			//Es gibt keine Pokemon, dass darf nicht sein!!:
//			System.out.println("FEHLER! Es müssen Wilde Pokemon in einem WildPokemonEvent existieren!");
//			System.exit(1);
//			return pokemon;
//		} else {
//			//Es gibt pokemon(gut!):
//			//Diese einzeln zur Liste hinzufuegen:
//			for(int k = 0; k < pokString.split(";").length; k++){
//				pokemon.add(PokeNamen.valueOf(pokString.split(";")[k].toUpperCase()));
//			}
//			return pokemon;
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
