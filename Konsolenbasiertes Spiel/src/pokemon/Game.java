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
		Statisches.setScanner();
		/*
		Document doc = null;
		    
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse( new File("./Locations.xml") );
		} catch(ParserConfigurationException e){
		   	e.printStackTrace();
		} catch (SAXException e){
		   	e.printStackTrace();
		}catch (IOException e){
		   	e.printStackTrace();
		}
		
		//Locations aus XML laden:
		getAllLocations(doc.getFirstChild().getChildNodes());
		
		//Hier muss Abfrage stattfinden: erstes laden oder gibt es einen Speicherstand?
		//Trainer laden:
		Trainer t = new Trainer(1, new ArrayList<Integer>(), null, null, null);
		//Start Tokens laden:
		for(int key : locations.keySet()){
			t.addToken(key);
		}
		
		
		//Spiel starten:
		while(true){
			locations.get(t.getLocationId()).runLocation(t);
		}*/
		kaitest();
		Statisches.closeScanner();
		    
	}
	
	//Methoden:
	
	public static void kaitest(){		
		Statisches.einlesen();
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
	
	public static void getAllLocations(NodeList nodes){
		//Hier werden alle Locations die in der XML stehen in die
		//HashMap geschrieben.
		
		//Geht alle location Tags durch
		for(int i = 1; i < nodes.getLength(); i+=2){
			
			//Namen der Location:
			String locName = nodes.item(i).getAttributes().getNamedItem("name").getNodeValue();
			
			//Location ID:
			int locId = Integer.parseInt(nodes.item(i).getAttributes().getNamedItem("id").getNodeValue());
			
			//Events fuer die location holen:
			List <Event> events = new ArrayList<Event>();
			
		
			locations.put(locId, new Location(locId, locName, events));
		}
	}
	
	//Gets all the events that belong to the i-th item of nodes:
	public static List<Event> getAllEvents(NodeList nodes, int i){
		List<Event> events = new ArrayList<Event>();
		//Geht alle Event Tags durch:
		for(int j = 1; j < nodes.item(i).getChildNodes().getLength(); j+=2){
			
			//Und f�gt das richte Event zur Liste hinzu.
			int typ = Integer.parseInt(nodes.item(i).getChildNodes().item(j)
					 .getAttributes().getNamedItem("typ").getNodeValue());
			
			switch (typ) {
			case 0:
				events.add(getInformationEvent(nodes, i, j));
				break;
			case 1:
				events.add(getChangeLocationEvent(nodes, i, j));
				break;
			case 2:
				events.add(getCompoundEvent(nodes, i, j));
				break;
			case 3: 
				events.add(getRemoveTokenEvent(nodes, i, j));
				break;
			case 4:
				events.add(getGrantTokenEvent(nodes, i, j));
				break;
			default:
				break;
			}
		}
		return events;
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
	
	public static CompoundEvent getCompoundEvent(NodeList nodes, int i, int j){
		//Get Required Token List:
		List<Integer> reqTokens = getReqTokenList(nodes, i, j);
		//Get Command:
		String command = getCommand(nodes, i, j);
		//Get Events that belong to the CompountEven:
		List<Event> compEvents = getAllEvents(nodes.item(i).getChildNodes(), j);
		return new CompoundEvent(reqTokens, command, compEvents);
	}
	
	public static RemoveTokenEvent getRemoveTokenEvent(NodeList nodes, int i, int j){
		//Get Required Token List:
		List<Integer> reqTokens = getReqTokenList(nodes, i, j);
		//Get Command:
		String command = getCommand(nodes, i, j);
		//Get Token that should be removed:
		int token = Integer.parseInt(nodes.item(i).getChildNodes().item(j)
					.getAttributes().getNamedItem("token").getNodeValue());
		
		return new RemoveTokenEvent(reqTokens, command, token);
	}
	
	public static GrantTokenEvent getGrantTokenEvent(NodeList nodes, int i, int j){
		//Get Required Token List:
		List<Integer> reqTokens = getReqTokenList(nodes, i, j);
		//Get Command:
		String command = getCommand(nodes, i, j);
		//Get Token that should be added:
		int token = Integer.parseInt(nodes.item(i).getChildNodes().item(j)
				.getAttributes().getNamedItem("token").getNodeValue());
		
		return new GrantTokenEvent(reqTokens, command, token);
	}
	
	
	public static List<Integer> getReqTokenList(NodeList nodes, int i, int j){

		List<Integer> reqTokens = new ArrayList<Integer>();
		//String mit allen required Tokens:
		String tokString = nodes.item(i).getChildNodes().item(j).getAttributes()
						  .getNamedItem("reqToken").getNodeValue();
		//Diese einzeln zur Liste hinzufuegen:
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
		String info = nodes.item(i).getChildNodes().item(j).getTextContent().trim().replaceAll("\\t+", "");
		return info;
	}
	
}
