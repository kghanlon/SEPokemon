package pokemon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Statisches {
	private static HashMap<PokeNamen, String> pokehash = new HashMap<>();
	private static HashMap <AttackenNamen, String> atthash = new HashMap<>();
	private static HashMap<ItemNamen, String> itemhash = new HashMap<>();
	
	public static void einlesen(){		
		/*atthash.put(AttackenNamen.DONNERBLITZ, "elektro#0.9#80");
		atthash.put(AttackenNamen.FUNKENSPRUNG, "elektro#1.0#40");
		atthash.put(AttackenNamen.RUTENSCHLAG, "normal#1.0#0");
		atthash.put(AttackenNamen.RUCKZUCKHIEB, "normal#1.0#60");
		atthash.put(AttackenNamen.BODYSLAM, "normal#0.8#70");
		
		pokehash.put(PokeNamen.PIKACHU, "elektro#elektro#0.7#5;RUTENSCHLAG%10;RUCKZUCKHIEB%15;FUNKENSPRUNG%20;BODYSLAM%25;DONNERBLITZ");*/
		Document doc = null;
	    
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse( new File("./Konsolenbasiertes Spiel/Pokemon.xml") );
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
			doc = builder.parse( new File("./Konsolenbasiertes Spiel/Attacken.xml") );
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
			doc = builder.parse( new File("./Konsolenbasiertes Spiel/Items.xml") );
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
	
	public static Trainer gespeicherterTrainer(){
		Trainer t;
Document doc = null;
	    
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse( new File("./Konsolenbasiertes Spiel/Trainer.xml") );
		} catch(ParserConfigurationException e){
		   	System.out.println("Parserprobleme");
		} catch (SAXException e){
			System.out.println("SAXEprobleme");
		}catch (IOException e){
			System.out.println("IOprobleme");
		}
		NodeList n = doc.getChildNodes();
		Pokemon [] pok = new Pokemon[4];
		List<Integer> token = new ArrayList<>();
		List<Item> item = new ArrayList<>();
		
		String name = n.item(0).getAttributes().getNamedItem("name").getNodeValue();//name
		int locid = Integer.parseInt(n.item(0).getAttributes().getNamedItem("locationId").getNodeValue());
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
			switch(n.item(3).getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue()){
			case "BALL": it= new Ball(id, anzahl); break;
			case "HEILEN": it=new Heilungsitem(id, anzahl);break;
			}
			item.add(it);
		}
		for(int i=1; i<n.item(5).getChildNodes().getLength(); i+=2){//tokens einlesen
			token.add(Integer.parseInt(n.item(5).getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue()));
		}
		t = new Trainer(locid, token, name, item, pok);
		return t;
	}
	
	public static void trainerSpeichern(Trainer t){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");//Headzeile der xml
		sb.append("<Trainerelemente locationId=\""+ t.getLocationId() + "\" name=\""+ t.getName() + "\">\n");
		
		sb.append("<PokemonListe>\n");
		for(int i=0; i<t.getTeam().length; i++){//auslesen der Pokemon
			if(t.getTeam()[i]!=null){
			sb.append("<Pokemon id=\""+t.getTeam()[i].getName() +"\" lvl=\"" +t.getTeam()[i].getLvl() + "\" exp=\"" + t.getTeam()[i].getExp() + "\" maxkp=\"" + t.getTeam()[i].getMaxkp() + "\" kp=\"" + t.getTeam()[i].getKp() + "\" staerke=\"" + t.getTeam()[i].getStaerke() + "\" vert=\"" + t.getTeam()[i].getVert() + "\" tempo=\"" + t.getTeam()[i].getTempo() + "\">\n");
			sb.append("<AttackenListe>\n");
			for(int j=0; j<t.getTeam()[i].getAttacken().length; j++){//attacken auslesen
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
			sb.append("<Token id=\""+t.getTokens().get(i) +"\"/>\n");			
		}
		sb.append("</TokenListe>\n");
		
		sb.append("</Trainerelemente>\n");

		try{
			FileWriter fw = new FileWriter(new File("./Konsolenbasiertes Spiel/Trainer.xml"));
			fw.write(sb.toString());
			fw.close();
		}catch(IOException io){
			System.out.println("Datei konnte nicht geöffnet werden.");
		}
		System.out.println("Speichern erfolgreich.");
	}
	
	private static ItemNamen strToItemName(String name) {
		for(ItemNamen i : ItemNamen.values()){
			if(name.equals(i+""))
				return i;
			
		}
		return null;
	}

	private static PokeNamen strToPokeNamen(String name) {
		for(PokeNamen p : PokeNamen.values()){
			if(name.equals(p+""))
				return p;
			
		}
		return PokeNamen.KAPADOR;
	}

	public static HashMap <AttackenNamen, String> getAtthash() {
		return atthash;
	}

	
	public static HashMap<PokeNamen, String> getPokehash() {
		return pokehash;
	}
	
	public static HashMap<ItemNamen, String> getItemhash() {
		return itemhash;
	}

	
	public static void sleep(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Typ stringToTyp(String s){
		switch(s){
		case "WASSER": return Typ.WASSER;
		case "ELEKTRO" : return Typ.ELEKTRO;
		case "FEUER" : return Typ.FEUER;
		case "PFLANZE" : return Typ.PFLANZE;
		case "PSYCHO" : return Typ.PSYCHO;
		case "GESTEIN" : return Typ.GESTEIN;
		case "FLUG" : return Typ.FLUG;
		case "NORMAL" : return Typ.NORMAL;
		}
		return null;
	}
	
	
	public static AttackenNamen strToAttName(String s){
		for(AttackenNamen t :AttackenNamen.values()){
			if(s.equals(t+"")){
				return t;
			}
		}
		return AttackenNamen.PLATSCHER;
	}
	
	
	
	public static String typToString(Typ t){
		switch(t){
		case WASSER : return "WASSER";
		case ELEKTRO : return "ELEKTRO";
		case FEUER : return "FEUER";
		case PFLANZE : return "PFLANZE";
		case PSYCHO : return "PSYCHO";
		case GESTEIN : return "GESTEIN";
		case FLUG : return "FLUG";
		case NORMAL : return "NORMAL";
		}
		return null;
	}
	 
	public static double min(double a, double b){
		if(a<=b){
			return a;
		}
		return b;
	}
	
	public static double max(double a, double b){
		if(a>=b){
			return a;
		}
		return b;
	}
	
	
	
	
}
