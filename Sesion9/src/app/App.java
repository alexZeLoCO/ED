package app;

import java.util.Iterator;

import estDatos.LPCHash;

public class App {

	public static void main(String[] args) {
		try {
			System.out.println("Creating Hash...");
			LPCHash <Integer> H1 = new LPCHash<Integer> ();
			System.out.println("Creating custom-sized Hash...");
			LPCHash <Integer> H2 = new LPCHash<Integer> (10);
			System.out.println("Creating fully-custom Hash...");
			LPCHash <Integer> H3 = new LPCHash<Integer> (10, 0.75);
			System.out.println("Adding elements to Hash1, to force resize...");
			H1.add(7);
			H1.add(6);
			H1.add(3);
			H1.add(9);
			H1.add(124);
			H1.add(53);
			H1.add(64);
			H1.add(69);
			System.out.println("Hash1");
			System.out.println(H1.toString());
			LPCHash <Integer> H4 = new LPCHash<Integer> (H1);
			System.out.println(H4.toString());
			Iterator <Integer> itr = H4.iterator();
			itr.next();
			itr.remove();
			System.out.println(H4.toString());
			H2.addAll(H1);
			H2.remove(7);
			System.out.println(H2.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

}
