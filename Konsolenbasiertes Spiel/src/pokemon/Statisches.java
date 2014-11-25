package pokemon;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
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
		
		for(ItemNamen n : itemhash.keySet()){
			System.out.println("Name: " + n + " String: " + itemhash.get(n));
		}
			
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
