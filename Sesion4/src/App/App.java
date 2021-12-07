package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

import estDatos.ComparatorStrings;
import estDatos.PriorityQueueList;

public class App {

	/**
	 * Añade a la colección especificada todas las palabras contenidas en el archivo
	 * de nombre dado.
	 * 
	 * @param nombre el nombre del archivo especificado
	 * @throws IOException si el archivo de datos es erróneo
	 */
	private static void obtenerPalabras(Collection<String> c, String nombre) throws IOException {
		File f = new File(nombre);
		Scanner sc = new Scanner(f);
		// [\n] es una expresión regular que indica los
		// delimitadores utilizados en el archivo: el
		// caracter ';' y el salto de línea
		// sc.useDelimiter("[\n]");
		// procesar el archivo línea a línea
		while (sc.hasNextLine()) {
			String linea = sc.nextLine();
			String[] v = linea.split("[.,;:¿?¡!\"() ]");

			for (String palabra : v) {
				if ((palabra != null) && (!palabra.equals(""))) {
					c.add(palabra.toLowerCase());
				}
			}
		}

		sc.close();
		
	} // obtenerPalabras

	public static void main(String[] args) {
		PriorityQueueList<String> q1 = new PriorityQueueList<String>();
		PriorityQueueList<String> q2 = new PriorityQueueList<String>(new ComparatorStrings());
		try {
			
			obtenerPalabras(q1, "texto.txt");
			obtenerPalabras(q2, "texto.txt");		
			
			System.out.printf("Prioridad (caso 1): %s\n", q1.toString());
			System.out.println();
			System.out.printf("Prioridad (caso 2): %s\n", q2.toString());
			System.out.println();
			
			q1.poll();
			System.out.printf("Prioridad (caso 1) primero elim.: %s\n", q1.toString());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Se requiere el nombre del archivo.");
		} catch (FileNotFoundException e) {
		    System.err.printf("Error abriendo archivo: %s", e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (ClassCastException e) {
			System.err.println(e.getMessage());
		}
		
	} // main

}
