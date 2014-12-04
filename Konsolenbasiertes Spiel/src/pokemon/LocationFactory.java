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

public class LocationFactory {
	
	public static Map<Integer, Location> locations = new HashMap<Integer, Location>();
	
	private NodeList nodes;
	Document doc;
	String s;
	
	public LocationFactory(String s){
		this.s = s;
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse( new File(s) );
		} catch(ParserConfigurationException e){
		   	e.printStackTrace();
		} catch (SAXException e){
		   	e.printStackTrace();
		}catch (IOException e){
		   	e.printStackTrace();
		}
		
		this.nodes = doc.getFirstChild().getChildNodes();
		
	}
	
	public void getAllLocations(){ //nodes = alle locations in der welt.
		//Hier werden alle Locations die in der XML stehen in die
		//HashMap geschrieben.
		
		//Geht alle location Tags durch
		for(int i = 1; i < nodes.getLength(); i+=2){
			
			//Namen der Location:
			String locName = nodes.item(i).getAttributes().getNamedItem("name").getNodeValue();
			
			//Location ID:
			int locId = Integer.parseInt(nodes.item(i).getAttributes().getNamedItem("id").getNodeValue());
			
			//Events fuer die location holen:
			List <Event> events = getAllEvents(i);

			locations.put(locId, new Location(locId, locName, events));
		}
	}
	
	//Gets all the events that belong to the i-th item of nodes:
	public List<Event> getAllEvents(int i){
		List<Event> events = new ArrayList<Event>();
		//Geht alle Event Tags durch:
		for(int j = 1; j < nodes.item(i).getChildNodes().getLength(); j+=2){
			
			//Und fuegt das richte Event zur Liste hinzu.
			String typ = nodes.item(i).getChildNodes().item(j).getNodeName();
			
			switch (typ) {
			case "InformationEvent":
				events.add(getInformationEvent(i, j));
				break;
			case "ChangeLocationEvent":
				events.add(getChangeLocationEvent(i, j));
				break;
			case "CompoundEvent":
				events.add(getCompoundEvent(i, j));
				break;
			case "RemoveTokenEvent": 
				events.add(getRemoveTokenEvent(i, j));
				break;
			case "GrantTokenEvent":
				events.add(getGrantTokenEvent(i, j));
				break;
			case "WildPokemonEvent":
				events.add(getWildPokemonEvent(i, j));
			default:
				break;
			}
		}
		return events;
	}
	
	public InformationEvent getInformationEvent(int i, int j){
		//Get Required Token List:
		List<String> reqTokens = getReqTokenList(i, j);
		//Get Required NonToken List:
		List<String> reqNonTokens = getReqNonTokenList(i, j);
		//Get Command:
		String command = getCommand(i, j);
		//Get Info:
		String info = getInfo(i, j);
		return new InformationEvent(reqTokens, reqNonTokens, command, info);
	}
	
	public ChangeLocationEvent getChangeLocationEvent(int i, int j){
		//Get Required Token List:
		List<String> reqTokens = getReqTokenList(i, j);
		//Get Required NonToken List:
		List<String> reqNonTokens = getReqNonTokenList(i, j);
		//Get Command:
		String command = getCommand(i, j);
		//Get Info:
		String info = getInfo(i, j);
		//Get Next Location ID:
		int nextLocId = getNextLocId(i, j);
		return new ChangeLocationEvent(reqTokens, reqNonTokens, command, info, nextLocId);
	}
	
	public CompoundEvent getCompoundEvent(int i, int j){
		//Get Required Token List:
		List<String> reqTokens = getReqTokenList(i, j);
		//Get Required NonToken List:
		List<String> reqNonTokens = getReqNonTokenList(i, j);
		//Get Command:
		String command = getCommand(i, j);
		//Get Events that belong to the CompountEven:
		
		//Workaround, etwas dreckig: nodes verändern, und zwar auf die elemente des Compound event, und danach zurück setzten!
		nodes = doc.getFirstChild().getChildNodes().item(i).getChildNodes();
		List<Event> compEvents = getAllEvents(j);
		//Schnell wieder zurück:
		nodes = doc.getFirstChild().getChildNodes();
		
		return new CompoundEvent(reqTokens, reqNonTokens, command, compEvents);
	}
	
	public RemoveTokenEvent getRemoveTokenEvent(int i, int j){
		//Get Required Token List:
		List<String> reqTokens = getReqTokenList(i, j);
		//Get Required NonToken List:
		List<String> reqNonTokens = getReqNonTokenList(i, j);
		//Get Command:
		String command = getCommand(i, j);
		//Get Token that should be removed:
		String token = nodes.item(i).getChildNodes().item(j)
					.getAttributes().getNamedItem("token").getNodeValue();
		
		return new RemoveTokenEvent(reqTokens, reqNonTokens, command, token);
	}
	
	public GrantTokenEvent getGrantTokenEvent(int i, int j){
		//Get Required Token List:
		List<String> reqTokens = getReqTokenList(i, j);
		//Get Required NonToken List:
		List<String> reqNonTokens = getReqNonTokenList(i, j);
		//Get Command:
		String command = getCommand(i, j);
		//Get Token that should be added:
		String token = nodes.item(i).getChildNodes().item(j)
				.getAttributes().getNamedItem("token").getNodeValue();
		
		return new GrantTokenEvent(reqTokens, reqNonTokens, command, token);
	}
	
	public WildPokemonEvent getWildPokemonEvent(int i, int j){
		//Get Required Token List:
		List<String> reqTokens = getReqTokenList(i, j);
		//Get Required NonToken List:
		List<String> reqNonTokens = getReqNonTokenList(i, j);
		//Get Command:
		String command = getCommand(i, j);
		//Get the available Pokemon:
		List<PokeNamen> pokemon = getWildPokemon(i, j);
		//Get the min level for the pokemon here
		int minlvl = Integer.parseInt(nodes.item(i).getChildNodes().item(j).getAttributes()
										.getNamedItem("minlvl").getNodeValue());
		//get the max level for the pokemon here
		int maxlvl = Integer.parseInt(nodes.item(i).getChildNodes().item(j).getAttributes()
				.getNamedItem("maxlvl").getNodeValue());
		//Get the Location of the nearest PokeCenter:
		int nextPokeCenterLocId = Integer.parseInt(nodes.item(i).getChildNodes().item(j).getAttributes()
				.getNamedItem("nextPokeCenterLocId").getNodeValue());
		
		return new WildPokemonEvent(reqTokens, reqNonTokens, command, pokemon, minlvl, maxlvl, nextPokeCenterLocId);
	}
	
	
	//HILFS METHODEN:
	
	//Attribute optional
	public List<String> getReqTokenList(int i, int j){

		List<String> reqTokens = new ArrayList<String>();
		//String mit allen required Tokens:
		String tokString = "";
		try{
			tokString = nodes.item(i).getChildNodes().item(j).getAttributes()
							  .getNamedItem("reqToken").getNodeValue();
		} catch (NullPointerException e){
			//The event does not have a reqToken Attribute. That should be ok!
			//Do nothing, tokString is empty.
		}
		if(tokString.equals("")){
			//Es gibt keine req Tokens:
			return reqTokens;
		} else {
			//Es gibt required Tokens:
			//Diese einzeln zur Liste hinzufuegen:
			for(int k = 0; k < tokString.split(";").length; k++){
				reqTokens.add(tokString.split(";")[k]);
			}
			return reqTokens;
		}
	}
	
	//Attribute optional
	public List<String> getReqNonTokenList(int i, int j){

		List<String> reqNonTokens = new ArrayList<String>();
		//String mit allen required NonTokens:
		String tokString = "";
		try{
			tokString = nodes.item(i).getChildNodes().item(j).getAttributes()
							  .getNamedItem("reqNonToken").getNodeValue();
		} catch (NullPointerException e){
			//The event does not have a reqNonToken Attribute. That should be ok!
			//Do nothing, tokString is empty.
		}
		if(tokString.equals("")){
			//Es gibt keine req NonTokens:
			return reqNonTokens;
		} else {
			//Es gibt required NonTokens:
			//Diese einzeln zur Liste hinzufuegen:
			for(int k = 0; k < tokString.split(";").length; k++){
				reqNonTokens.add(tokString.split(";")[k]);
			}
			return reqNonTokens;
		}
	}
	
	
	//Attribute optional.
	public String getCommand(int i, int j){
		String command = "##"; //shouldn't really be used by mistake
		try{
			command = nodes.item(i).getChildNodes().item(j).getAttributes()
					.getNamedItem("command").getNodeValue();
		} catch (NullPointerException e){
			//The event does not have a Command Attribute, That should be ok!
			//Do nothing, the default command ist set
		}
		return command;
	}
	
	//Attribute required!
	public String getInfo(int i, int j){
		String info = nodes.item(i).getChildNodes().item(j).getTextContent().trim().replaceAll("\\t+", "");
		return info;
	}
	
	//Attribute required!
	public int getNextLocId(int i, int j){
		int ret = Integer.parseInt(nodes.item(i).getChildNodes().item(j)
				.getAttributes().getNamedItem("nextLocId").getNodeValue());
		return ret;
	}
	
	//Attribute required!
	public List<PokeNamen> getWildPokemon(int i, int j){
		List<PokeNamen> pokemon = new ArrayList<PokeNamen>();
		//String mit allen vorhandenen pokemon:
		String pokString = nodes.item(i).getChildNodes().item(j).getAttributes()
				  .getNamedItem("pokemon").getNodeValue();
		if(pokString.equals("")){
			//Es gibt keine Pokemon, dass darf nicht sein!!:
			System.out.println("FEHLER! Es müssen Wilde Pokemon in einem WildPokemonEvent existieren!");
			System.exit(1);
			return pokemon;
		} else {
			//Es gibt pokemon(gut!):
			//Diese einzeln zur Liste hinzufuegen:
			for(int k = 0; k < pokString.split(";").length; k++){
				pokemon.add(PokeNamen.valueOf(pokString.split(";")[k].toUpperCase()));
			}
			return pokemon;
		}
	}
	
}
