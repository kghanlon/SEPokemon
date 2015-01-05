package tests;

import java.util.ArrayList;
import java.util.Scanner;

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
	 * Ein Test bei dem der Trainer ein Pokemon besitzt und einen Kampf gegen ein Wildes Pokemon austr�gt
	 * 
	 * Man sieht dass es erheblich leichter ist Pokemon zu fangen, die geschw�cht sind
	 * 
	 * au�er dem wird Pokemon direkt eingef�gt
	 * 
	 * @param sc Scanner �bergabe
	 */
	@Test
	public static void testFangen1(Scanner sc){
		Pokemon [] tmp = {new Pokemon(PokeNamen.ARKANI,  100), null , null};
		ArrayList<Pokemon> tmp2 = new ArrayList<Pokemon>();
		tmp2.add(new Pokemon(PokeNamen.HABITAK, 20));
		ArrayList<Item> tmp3 = new ArrayList<>();
		tmp3.add(new Ball(ItemNamen.POKEBALL, 200));
		Kampf.start(new Trainer(0, null, "name", tmp3, tmp, 0), tmp2 ,true);
	}
	
	/**
	 * Ein Test bei dem der Trainer drei Pokemon besitzt und einen Kampf gegen ein Wildes Pokemon austr�gt
	 * 
	 * Man sieht dass es erheblich leichter ist Pokemon zu fangen, die geschw�cht sind
	 * 
	 * au�er dem wird Pokemon nicht direkt eingef�gt man muss also ausw�hlen, wen man freil�sst oder ob man das Pokemon nicht m�chte
	 * 
	 * @param sc Scanner �bergabe
	 */
	@Test
	public static void testFangen2(Scanner sc){
		Pokemon [] tmp = {new Pokemon(PokeNamen.ARKANI,  100), null , null};
		ArrayList<Pokemon> tmp2 = new ArrayList<Pokemon>();
		tmp2.add(new Pokemon(PokeNamen.HABITAK, 20));
		ArrayList<Item> tmp3 = new ArrayList<>();
		tmp3.add(new Ball(ItemNamen.POKEBALL, 200));
		Kampf.start(new Trainer(0, null, "name", tmp3, tmp, 0), tmp2 ,true);
	}
	
	/**
	 * Ein Pokemon erh�lt Exp und steigt in den Level erlent automatisch neue Attacken bis es 4 hat von da an muss man ausw�hlen, welche Attacke weicht
	 * oder das die Attacke nicht erlent wird
	 * 
	 * Au�erdem sieht man das ein Pokemon sich entwickeln kann	
	 * @param sc Scanner �bergabe
	 */
	@Test
	public static void testLevelaufstieg(Scanner sc){
		Pokemon tmp = new Pokemon(PokeNamen.GLUMANDA, 4);
		tmp.expGewinn(20000);
	}
}
