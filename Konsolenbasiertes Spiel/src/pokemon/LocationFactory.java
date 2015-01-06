package pokemon;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import Events.BuyItemEvent;
import Events.ChangeLocationEvent;
import Events.CompoundEvent;
import Events.Event;
import Events.GrantMoneyEvent;
import Events.GrantTokenEvent;
import Events.InformationEvent;
import Events.RemoveMoneyEvent;
import Events.RemoveTokenEvent;
import Events.TrainerFightEvent;
import Events.WildPokemonEvent;

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
				break;
			case "GrantMoneyEvent":
				events.add(getGrantMoneyEvent(i, j));
				break;
			case "RemoveMoneyEvent":
				events.add(getRemoveMoneyEvent(i, j));
				break;
			case "TrainerFightEvent":
				events.add(getTrainerFightEvent(i, j));
				break;
			case "BuyItemEvent":
				events.add(getBuyItemEvent(i, j));
				break;
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
		List<PokeNamen> pokemon = getPokeNames(i, j);
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
	
	public GrantMoneyEvent getGrantMoneyEvent(int i, int j){
		//Get Required Token List:
		List<String> reqTokens = getReqTokenList(i, j);
		//Get Required NonToken List:
		List<String> reqNonTokens = getReqNonTokenList(i, j);
		//Get Command:
		String command = getCommand(i, j);
		//Get the amount of money:
		int amount = Integer.parseInt(nodes.item(i).getChildNodes().item(j).getAttributes()
				.getNamedItem("amount").getNodeValue());
		
		return new GrantMoneyEvent(reqTokens, reqNonTokens, command, amount);
	}
	
	public RemoveMoneyEvent getRemoveMoneyEvent(int i, int j){
		//Get Required Token List:
		List<String> reqTokens = getReqTokenList(i, j);
		//Get Required NonToken List:
		List<String> reqNonTokens = getReqNonTokenList(i, j);
		//Get Command:
		String command = getCommand(i, j);
		//Get the amount of money:
		int amount = Integer.parseInt(nodes.item(i).getChildNodes().item(j).getAttributes()
				.getNamedItem("amount").getNodeValue());
		
		return new RemoveMoneyEvent(reqTokens, reqNonTokens, command, amount);
	}
	
	public TrainerFightEvent getTrainerFightEvent(int i, int j){
		//Get Required Token List:
		List<String> reqTokens = getReqTokenList(i, j);
		//Get Required NonToken List:
		List<String> reqNonTokens = getReqNonTokenList(i, j);
		//Get the Token you get after you win:
		String tokenAtWin = nodes.item(i).getChildNodes().item(j).getAttributes()
									.getNamedItem("tokenAtWin").getNodeValue();
		//Get Command:
		String command = getCommand(i, j);
		//Get the pokenames:
		List<PokeNamen> poke = getPokeNames(i, j);
		//Get the levels:
		List<Integer> levels = getPokeLevels(i, j);
		//Get the Location of the nearest PokeCenter:
		int nextPokeCenterLocId = Integer.parseInt(nodes.item(i).getChildNodes().item(j).getAttributes()
									.getNamedItem("nextPokeCenterLocId").getNodeValue());
		//Get the amount of money you can win:
		int money = Integer.parseInt(nodes.item(i).getChildNodes().item(j).getAttributes()
						.getNamedItem("money").getNodeValue());
		
		return new TrainerFightEvent(reqTokens, reqNonTokens, tokenAtWin, command, poke, levels, nextPokeCenterLocId, money);
	}
	
	public BuyItemEvent getBuyItemEvent(int i, int j){
		//Get Required Token List:
		List<String> reqTokens = getReqTokenList(i, j);
		//Get Required NonToken List:
		List<String> reqNonTokens = getReqNonTokenList(i, j);
		//Get Command:
		String command = getCommand(i, j);
		//Get the Item Name:

		ItemNamen name = ItemNamen.valueOf(nodes.item(i).getChildNodes().item(j).getAttributes()
										.getNamedItem("item").getNodeValue().toUpperCase());
		//Get the Item Type:
		String type = nodes.item(i).getChildNodes().item(j).getAttributes()
									.getNamedItem("type").getNodeValue();
		//Create the Item Object, depending on the type:
		Item item;
		switch(type){
		case "ball":
			item = new Ball(name); break;
		case "heal":
			item = new Heilungsitem(name); break;
		default:
			//This should not happen!!
			item = new Ball(ItemNamen.DUMMYBALL);
			System.out.println("Fehler in Itemerzeugung, Item hatte keinen Typ!!!!");
		}
		//Cost for one Item:
		int kosten = Integer.parseInt(nodes.item(i).getChildNodes().item(j).getAttributes()
				.getNamedItem("cost").getNodeValue());
		
		return new BuyItemEvent(reqTokens, reqNonTokens, command, kosten, item);
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
	public List<PokeNamen> getPokeNames(int i, int j){
		List<PokeNamen> pokemon = new ArrayList<PokeNamen>();
		//String mit allen vorhandenen pokemon:
		String pokString = nodes.item(i).getChildNodes().item(j).getAttributes()
				  .getNamedItem("pokemon").getNodeValue();
		if(pokString.equals("")){
			//Es gibt keine Pokemon, dass darf nicht sein!!:
			System.out.println("FEHLER! Es müssenPokemon in einem FightEvent existieren! in getPokeNames");
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
	
	public List<Integer> getPokeLevels(int i, int j){
		List<Integer> levels = new ArrayList<Integer>();
		//String mit allen leveln:
		String lvlString = nodes.item(i).getChildNodes().item(j).getAttributes()
				  .getNamedItem("level").getNodeValue();
		if(lvlString.equals("")){
			//Es gibt keien Level, dass darf nicht sein!
			System.out.println("FEHLER! es müssen level in einem TrainerFight existieren! in getPokeLevels");
			System.exit(1);
			return levels;
		} else {
			for(int k = 0; k < lvlString.split(";").length; k++){
				levels.add(Integer.parseInt(lvlString.split(";")[k]));
			}
			return levels;
		}
	}
	
}
