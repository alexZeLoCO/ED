package app;
import estDatos.*;

public class App {
	
	public static void main(String[] args) {
		
		Range<Integer> ri = new Range<Integer> (-40, 60);
		FiniteSet<Integer> c0 = new FiniteSet<> (ri);
		ri.forEach(n -> {
			if (n%7 == 0)  {
				c0.add(n);
			}
		});
		
		c0.remove(0);
		c0.remove(7);
		c0.remove(14);
		
		Range<Character> rc = new Range <Character> ('a', 'z');
		
		FiniteSet<Character> c1 = new FiniteSet<Character> (rc, 'c', 'k', 'r', 'x');
		FiniteSet<Character> c2 = new FiniteSet<Character> (rc, 'b', 'f', 'k', 'r', 'z');
		
		FiniteSet<Character> c3 = new FiniteSet<Character> (c1);
		
		System.out.printf("Conjunto c0: %s\nConjunto c1: %s\nConjunto c2: %s\n\n", c0, c1, c2);
		//System.out.println(c3);
		
		FiniteSet<Character> union = new FiniteSet<Character>(rc);
		for (Character c : c1) {
			union.add(c);
		}
		for (Character c : c2) {
			union.add(c);
		}
		
		FiniteSet<Character> interseccion = new FiniteSet<Character> (rc);
		rc.forEach(c -> {
			if (c1.contains(c) && c2.contains(c))  {
				interseccion.add(c);
			}
		});
		
		FiniteSet<Character> diferencia = new FiniteSet<Character> (rc);
		rc.forEach(c -> {
			if (c1.contains(c) && !c2.contains(c))  {
				interseccion.add(c);
			}
		});
		
		System.out.printf("Conjunto union: %s\nConjunto interseccion: %s\nConjunto diferencia: %s\n\n", union, interseccion, diferencia);
	}

}
