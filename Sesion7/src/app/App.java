package app;
import java.util.TreeMap;

import estDatos.*;

public class App {

	public static void main(String[] args) {
		
		TreeMap <Character, Integer> t = new TreeMap <Character, Integer> ();
		System.out.println("///----------TESTING CONSTRUCTORES----------///");
		t.put('x', 2);
		Monomio m1 = new Monomio (2.5, t);
		System.out.printf("Monomio 1 (Double, Map): %s", m1);

		t.put('y', 3);
		Monomio m2 = new Monomio (-1, t);
		System.out.printf("Monomio 2 (Double, Map): %s", m2);

		Monomio m3 = new Monomio (m1);
		System.out.printf("Monomio 3 (Copia): %s", m3);
		
		Monomio cadena = new Monomio ("5.4x7");
		System.out.printf("Monomio cadena: %s\n", cadena);
		
		System.out.println("///----------TESTING OPERACIONES----------///");
		System.out.printf("Monomio producto de Monomio 1 y Monomio 2: %s", Monomio.producto(m1, m2));
		System.out.printf("Monomio.porEscalar(2, m1): %s", Monomio.porEscalar(2, m1));
		System.out.printf("Monomio.potencia(m2, 2): %s", Monomio.potencia(m2, 2));
		System.out.printf("Los monomios 1 y 2 son semejantes: %b\n", m1.semejante(m2));
		try {
			System.out.printf("Suma de monomios m1 y m3: %s\n", Monomio.suma(m1, m3));
			System.out.printf("Suma de monomios m1 y m2 (EXCEPCION): %s\n", Monomio.suma(m1, m2));
		} catch (Exception e) {
			System.err.println("ERR: Excepción recogida correctamente en catch (Exception e)...");
			e.printStackTrace();
		}
		
	}
	
}
