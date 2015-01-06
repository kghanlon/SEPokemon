package pokemon;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author Kai
 * @class Statisches Alle Daten die mehrfach gebraucht werden, werden hier zentral gespeichert, sodass man �nderungen nur einmalig vornehmen muss
 */
public class Statisches {
	private static HashMap<PokeNamen, String> pokehash = new HashMap<>();
	private static HashMap <AttackenNamen, String> atthash = new HashMap<>();
	private static HashMap<ItemNamen, String> itemhash = new HashMap<>();
	private static Scanner sc;
	
	/**
	 * Liest Werte f�r Pokemon, Attacken und Items ein um sie in den entsprechenden HashMaps zu speichern
	 */
	public static void einlesen(){		
		Document doc = null;
	    
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse( new File("./Pokemon.xml") );
		} catch(ParserConfigurationException e){
		   	System.out.println("Parserprobleme");
		} catch (SAXException e){
			System.out.println("SAXEprobleme");
		}catch (IOException e){
			System.out.println("IOprobleme");
		}
		pokemoneinlesen(doc.getChildNodes().item(0).getChildNodes());
		try{		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse( new File("./Attacken.xml") );
		} catch(ParserConfigurationException e){
		   	System.out.println("Parserprobleme");
		} catch (SAXException e){
			System.out.println("SAXEprobleme");
		}catch (IOException e){
			System.out.println("IOprobleme");
		}
		attackeneinlesen(doc.getChildNodes().item(0).getChildNodes());
		
		
		try{		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse( new File("./Items.xml") );
		} catch(ParserConfigurationException e){
		   	System.out.println("Parserprobleme");
		} catch (SAXException e){
			System.out.println("SAXEprobleme");
		}catch (IOException e){
			System.out.println("IOprobleme");
		}
		itemeinlesen(doc.getChildNodes().item(0).getChildNodes());
			
	}
	
	private static void pokemoneinlesen(NodeList n){
		StringBuilder sb = new StringBuilder();
		String name;
		for(int i=1; i<n.getLength(); i+=2){
			name = n.item(i).getAttributes().getNamedItem("id").getNodeValue();
			sb = new StringBuilder();
			sb.append(n.item(i).getChildNodes().item(1).getAttributes().getNamedItem("id").getNodeValue() + "#");
			sb.append(n.item(i).getChildNodes().item(3).getAttributes().getNamedItem("id").getNodeValue() + "#");
			sb.append(n.item(i).getChildNodes().item(5).getAttributes().getNamedItem("wert").getNodeValue() + "#");
			for(int j=1; j<n.item(i).getChildNodes().item(7).getChildNodes().getLength(); j+=2){
				sb.append(n.item(i).getChildNodes().item(7).getChildNodes().item(j).getAttributes().getNamedItem("lvl").getNodeValue()+";");
				sb.append(n.item(i).getChildNodes().item(7).getChildNodes().item(j).getAttributes().getNamedItem("id").getNodeValue());
				if(j!=n.item(i).getChildNodes().item(7).getChildNodes().getLength()-2){
					sb.append("%");
				}
			}
			sb.append("#");
			for(int j=1; j<n.item(i).getChildNodes().item(9).getChildNodes().getLength(); j+=2){
				sb.append(n.item(i).getChildNodes().item(9).getChildNodes().item(j).getAttributes().getNamedItem("Typ").getNodeValue()+";");
				sb.append(n.item(i).getChildNodes().item(9).getChildNodes().item(j).getAttributes().getNamedItem("Faktor").getNodeValue());
				if(j!=n.item(i).getChildNodes().item(9).getChildNodes().getLength()-2){
					sb.append("%");
				}
			}
			sb.append("#" + n.item(i).getAttributes().getNamedItem("entwicklung").getNodeValue());
			pokehash.put(strToPokeNamen(name), sb.toString());
			
		}
	}
	
	
	
	
	
	private static void attackeneinlesen(NodeList n){
		StringBuilder sb = new StringBuilder();
		String name;
		for(int i=1; i<n.getLength(); i+=2){
			name = n.item(i).getAttributes().getNamedItem("id").getNodeValue();
			sb = new StringBuilder();
			sb.append(n.item(i).getChildNodes().item(1).getAttributes().getNamedItem("id").getNodeValue() + "#");
			sb.append(n.item(i).getChildNodes().item(3).getAttributes().getNamedItem("wert").getNodeValue() + "#");
			sb.append(n.item(i).getChildNodes().item(5).getAttributes().getNamedItem("wert").getNodeValue());			
			atthash.put(strToAttName(name), sb.toString());
		}
	}
	
	private static void itemeinlesen(NodeList n){
		String name;
		String sb;
		for(int i=1; i<n.getLength(); i+=2){
			name = n.item(i).getAttributes().getNamedItem("id").getNodeValue();
			sb = n.item(i).getChildNodes().item(1).getAttributes().getNamedItem("wert").getNodeValue();
			itemhash.put(strToItemName(name), sb);
		}
	}
	
	/**
	 * liest aus Trainer.xml den gespeicherten Trainer ein
	 * @return zuletzt genutzer Trainer, der gepspeichert wurde
	 */
	public static Trainer gespeicherterTrainer(){
		Trainer t;
		Document doc = null;
	    
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse( new File("./Trainer.xml") );
		} catch(ParserConfigurationException e){
		   	System.out.println("Parserprobleme");
		} catch (SAXException e){
			System.out.println("SAXEprobleme");
		}catch (IOException e){
			System.out.println("IOprobleme");
		}
		NodeList n = doc.getChildNodes();
		Pokemon [] pok = new Pokemon[3];
		List<Item> item = new ArrayList<>();
		List<String> token = new ArrayList<>();
		
		String name = n.item(0).getAttributes().getNamedItem("name").getNodeValue();//name
		int locid = Integer.parseInt(n.item(0).getAttributes().getNamedItem("locationId").getNodeValue());
		int geld = Integer.parseInt(n.item(0).getAttributes().getNamedItem("geld").getNodeValue());
		n=n.item(0).getChildNodes();
		int pokcounter=0;
		for(int i=1; i<n.item(1).getChildNodes().getLength(); i+=2){//pokemon einlesen
			PokeNamen id = strToPokeNamen(n.item(1).getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue());
			int lvl = Integer.parseInt(n.item(1).getChildNodes().item(i).getAttributes().getNamedItem("lvl").getNodeValue());
			int exp = Integer.parseInt(n.item(1).getChildNodes().item(i).getAttributes().getNamedItem("exp").getNodeValue());
			int maxkp = Integer.parseInt(n.item(1).getChildNodes().item(i).getAttributes().getNamedItem("maxkp").getNodeValue());
			int kp = Integer.parseInt(n.item(1).getChildNodes().item(i).getAttributes().getNamedItem("kp").getNodeValue());
			int staerke = Integer.parseInt(n.item(1).getChildNodes().item(i).getAttributes().getNamedItem("staerke").getNodeValue());
			int vert = Integer.parseInt(n.item(1).getChildNodes().item(i).getAttributes().getNamedItem("vert").getNodeValue());
			int tempo = Integer.parseInt(n.item(1).getChildNodes().item(i).getAttributes().getNamedItem("tempo").getNodeValue());
			AttackenNamen[] at = new AttackenNamen[4];
			int counter=0;
			for(int j =1; j<n.item(1).getChildNodes().item(i).getChildNodes().item(1).getChildNodes().getLength(); j+=2){
				at[counter++] = strToAttName(n.item(1).getChildNodes().item(i).getChildNodes().item(1).getChildNodes().item(j).getAttributes().getNamedItem("id").getNodeValue());
			}
			pok[pokcounter++] = new Pokemon(id, lvl, exp, at, maxkp, kp, staerke, vert, tempo);
		}
		for(int i=1; i<n.item(3).getChildNodes().getLength(); i+=2){//item einlesen
			ItemNamen id = strToItemName(n.item(3).getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue());
			int anzahl = Integer.parseInt(n.item(3).getChildNodes().item(i).getAttributes().getNamedItem("anzahl").getNodeValue());
			Item it = new Ball(ItemNamen.POKEBALL, 0);
			switch(n.item(3).getChildNodes().item(i).getAttributes().getNamedItem("typ").getNodeValue()){
			case "BALL": it= new Ball(id, anzahl); break;
			case "HEILEN": it=new Heilungsitem(id, anzahl);break;
			}
			item.add(it);
		}		
		for(int i=1; i<n.item(5).getChildNodes().getLength(); i+=2){//item einlesen
			String toki = n.item(5).getChildNodes().item(i).getAttributes().getNamedItem("name").getNodeValue();
			token.add(toki);
		}	
		t = new Trainer(locid, token, name, item, pok, geld);
		return t;
	}
	
	
	/**
	 * Speichern des grade genutzten Trainers
	 * @param t Trainer der gespeichert werden soll
	 */
	public static void trainerSpeichern(Trainer t){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");//Headzeile der xml
		sb.append("<Trainerelemente locationId=\""+ t.getLocationId() + "\" name=\""+ t.getName() + "\" geld=\"" + t.getGeld()  +"\">\n");
		
		sb.append("<PokemonListe>\n");
		for(int i=0; i<t.getTeam().length; i++){//auslesen der Pokemon
			if(t.getTeam()[i]!=null){
			sb.append("<Pokemon id=\""+t.getTeam()[i].getName() +"\" lvl=\"" +t.getTeam()[i].getLvl() + "\" exp=\"" + t.getTeam()[i].getExp() + "\" maxkp=\"" + t.getTeam()[i].getMaxkp() + "\" kp=\"" + t.getTeam()[i].getKp() + "\" staerke=\"" + t.getTeam()[i].getStaerke() + "\" vert=\"" + t.getTeam()[i].getVert() + "\" tempo=\"" + t.getTeam()[i].getTempo() + "\">\n");
			sb.append("<AttackenListe>\n");
			for(int j=0; j<t.getTeam()[i].getAttacken().length; j++){//attacken auslesen
				if(t.getTeam()[i].getAttacken()[j]!=null)
				sb.append("<Attacke id=\"" + t.getTeam()[i].getAttacken()[j].getName() + "\"/>\n");
			}
			sb.append("</AttackenListe>\n");
			
			sb.append("</Pokemon>\n");
			}
		}
		sb.append("</PokemonListe>\n");
		
		sb.append("<ItemListe>\n");
		for(int i=0; i<t.getItems().size(); i++){//auslesen der items
			sb.append("<Item id=\""+t.getItems().get(i).getName() +"\" anzahl=\"" +t.getItems().get(i).getAnzahl() + "\" typ=\"" + t.getItems().get(i).getTyp()  + "\"/>\n");			
		}
		sb.append("</ItemListe>\n");		
		sb.append("<TokenListe>\n");
		for(int i=0; i<t.getTokens().size(); i++){//auslesen der items
			sb.append("<Token name=\""+t.getTokens().get(i) + "\"/>\n");
		}
		sb.append("</TokenListe>\n");
		sb.append("</Trainerelemente>\n");

		try{
			FileWriter fw = new FileWriter(new File("./Trainer.xml"));
			fw.write(sb.toString());
			fw.close();
		}catch(IOException io){
			System.out.println("Datei konnte nicht geöffnet werden.");
		}
		System.out.println("Speichern erfolgreich.");
	}
	
	/**
	 * 
	 * @param name String von dem ein ItemNamen gesucht wird
	 * @return Enum ItemName
	 */
	public static ItemNamen strToItemName(String name) {
		for(ItemNamen i : ItemNamen.values()){
			if(name.equals(i+""))
				return i;
			
		}
		return null;
	}

	/**
	 * 
	 * @param name String von dem ein PokeNamen gesucht wird
	 * @return Enum PokeNamen
	 */
	public static PokeNamen strToPokeNamen(String name) {
		for(PokeNamen p : PokeNamen.values()){
			if(name.equals(p+""))
				return p;
			
		}
		return PokeNamen.KARPADOR;
	}

	/**
	 * 
	 * @return HashMap die AttackenNamen und String der Eigenschaften zur�ckgibt
	 */
	public static HashMap <AttackenNamen, String> getAtthash() {
		return atthash;
	}

	/**
	 * 
	 * @return HashMap die PokeNamen und String der Eigenschaften zur�ckgibt
	 */
	public static HashMap<PokeNamen, String> getPokehash() {
		return pokehash;
	}
	
	/**
	 * 
	 * @return HashMap die ItemNamen und String der Eigenschaften zur�ckgibt
	 */
	public static HashMap<ItemNamen, String> getItemhash() {
		return itemhash;
	}

	/**
	 * 
	 * Wird genutzt um auf Eingabe des Spielers zu warten, dass er die Nachricht gelesen hat
	 */
	public static void sleep(){
		try {
			sc.nextLine();
			
		} catch (Exception e) {
			System.out.println("Fehler bei Lesebstätigung");
		}
	}
	
	/**
	 * 
	 * @param s String der in einem Typ umgewandelt werden soll
	 * @return Enum Typ
	 */
	public static Typ stringToTyp(String s){
		for(Typ t :Typ.values()){
			if(s.equals(t+"")){
				return t;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param name String von dem ein AttackenNamen gesucht wird
	 * @return Enum AttackeneNamen
	 */
	public static AttackenNamen strToAttName(String s){
		for(AttackenNamen t :AttackenNamen.values()){
			if(s.equals(t+"")){
				return t;
			}
		}
		return AttackenNamen.PLATSCHER;
	}
	
	
	/**
	 * 
	 * @param t Typ der ausgegeben werden soll
	 * @return Enum + ""
	 */
	public static String typToString(Typ t){//todo: ändern in dynamische abfrage wir attacke und items
		for(Typ s :Typ.values()){
			if(t==s){
				return s+"";
			}
		}
		return null;		
	}
	 
	/**
	 * 
	 * @param a double 
	 * @param b double
	 * @return min von a und b
	 */
	public static double min(double a, double b){
		if(a<=b){
			return a;
		}
		return b;
	}
	
	/**
	 * 
	 * @param a double 
	 * @param b double
	 * @return max von a und b
	 */
	public static double max(double a, double b){
		if(a>=b){
			return a;
		}
		return b;
	}
	
	/**
	 * 
	 * @param t Wert von 0-1 in dem True zur�ckgegeben werden soll 
	 * @return true wenn t �ber zuf�llig generiertem r liegt oder gleich ist, sonst false
	 */
	public static boolean random(float t){
		double r = Math.random();
		if(r<=t) 
			return true;
		return false;
	}

	/**
	 * gemeinsamer Scanner ben�tigt, da sonst sich meherere Scanner den Input weglesen und man keine Einagebn mehr t�tigen kann
	 * @return Zentralen Scanner 
	 */
	public static Scanner getScanner() {
		return sc;
	}
	
	/**
	 * Zentrasler Scanner sc wird initialisert 
	 */
	public static void setScanner(){
		sc = new Scanner(System.in);
	}
	
	/**
	 * Zentrales Schlie�en des Scanners
	 */
	public static void closeScanner(){
		sc.close();
	}
	
	
}
