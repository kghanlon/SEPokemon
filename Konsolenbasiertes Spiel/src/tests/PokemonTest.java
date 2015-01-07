package tests;

import java.util.ArrayList;

import org.junit.Test;

import pokemon.Ball;
import pokemon.Item;
import pokemon.ItemNamen;
import pokemon.Kampf;
import pokemon.PokeNamen;
import pokemon.Pokemon;
import pokemon.Trainer;

public class PokemonTest {
	/**
	 * Ein Test bei dem der Trainer ein Pokemon besitzt und einen Kampf gegen ein Wildes Pokemon austraegt
	 * 
	 * Man sieht dass es erheblich leichter ist Pokemon zu fangen, die geschwaecht sind
	 * 
	 * ausserdem wird Pokemon direkt eingefaegt
	 * 
	 */
	@Test
	public static void testFangen1(){
		Pokemon [] tmp = {new Pokemon(PokeNamen.ARKANI,  20), null , null};
		ArrayList<Pokemon> tmp2 = new ArrayList<Pokemon>();
		tmp2.add(new Pokemon(PokeNamen.HABITAK, 3));
		ArrayList<Item> tmp3 = new ArrayList<>();
		tmp3.add(new Ball(ItemNamen.POKEBALL, 200));
		Kampf.start(new Trainer(0, null, "name", tmp3, tmp, 0), tmp2 ,true);
	}
	
	/**
	 * Ein Test bei dem der Trainer drei Pokemon besitzt und einen Kampf gegen ein Wildes Pokemon austraegt
	 * 
	 * Man sieht dass es erheblich leichter ist Pokemon zu fangen, die geschwaecht sind
	 * 
	 * ausserdem wird Pokemon nicht direkt eingefuegt man muss also auswaehlen, wen man freilaesst oder ob man das Pokemon nicht moechte
	 *  
	 */
	@Test
	public static void testFangen2(){
		Pokemon [] tmp = {new Pokemon(PokeNamen.ARKANI,  20),new Pokemon(PokeNamen.ARKANI,  20)  , new Pokemon(PokeNamen.ARKANI,  20)};
		ArrayList<Pokemon> tmp2 = new ArrayList<Pokemon>();
		tmp2.add(new Pokemon(PokeNamen.HABITAK, 3));
		ArrayList<Item> tmp3 = new ArrayList<>();
		tmp3.add(new Ball(ItemNamen.POKEBALL, 200));
		Kampf.start(new Trainer(0, null, "name", tmp3, tmp, 0), tmp2 ,true);
	}
	
	/**
	 * Ein Pokemon erhaelt Exp und steigt in den Level erlernt automatisch neue Attacken bis es 4 hat von da an muss man auswaehlen, welche Attacke weicht
	 * oder das die Attacke nicht erlent wird
	 * 
	 * Ausserdem sieht man das ein Pokemon sich entwickeln kann	
	 */
	@Test
	public static void testLevelaufstieg(){
		Pokemon tmp = new Pokemon(PokeNamen.GLUMANDA, 4);
		tmp.expGewinn(20000);
	}
	
	/**
	 * Test mit schwachen Pokemon um das Balancing zu pruefen
	 */
	@Test
	public static void testKampfSchwach(){
		Pokemon [] tmp = {new Pokemon(PokeNamen.BISASAM,  5),null, null};
		ArrayList<Pokemon> tmp2 = new ArrayList<Pokemon>();
		tmp2.add(new Pokemon(PokeNamen.RATTFRATZ, 3));
		ArrayList<Item> tmp3 = new ArrayList<>();
		tmp3.add(new Ball(ItemNamen.POKEBALL, 200));
		Kampf.start(new Trainer(0, null, "name", tmp3, tmp, 0), tmp2 ,true);
	}
}
