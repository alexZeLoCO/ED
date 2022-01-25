package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import estDatos.MultiMap;

public class App {

	// Añade al multidiccionario m las palabras de la línea de texto dada y que tiene el
	// número de línea especificado
	private static void agregarPalabras(MultiMap<String, Integer> m, String linea, int numLinea)
			throws InterruptedException {
		String[] v = linea.split("[.,;:¿?¡!\"() ]");

		for (String palabra: v) {
			if ((palabra != null) && (!palabra.equals(""))) {
				m.put(palabra, numLinea);
			}
		}
	}

	// Retorna un multidiccionario con las palabras del archivo de texto cuyo nombre se especifica
	// y que asocia a éstas los números de líneas en que aparecen
	private static MultiMap<String, Integer> obtenerMultiMap(String nombreArchivo) throws IOException {
		MultiMap<String, Integer> m = new MultiMap<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(nombreArchivo));
			int numLinea = 0;
			String linea;
			// añadir las palabras de cada línea del texto
			while ((linea = br.readLine()) != null) {
				numLinea++;
				agregarPalabras(m, linea, numLinea);
			}
		} catch (Exception e) {
			System.out.println("Excepción leyendo el archivo "
					+ nombreArchivo);
			e.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
		}
		
		return m;
	}
	
	// Retorna las palabras del diccionario que tienen asociados más valores de
	// los especificados (o que aparecen en más líneas del texto de las indicadas)
	private static SortedSet<String> enMasLineas(MultiMap<String, Integer> m, int n) {
		SortedSet<String> result = new TreeSet<>();
		Iterator<Map.Entry<String, SortedSet<Integer>>> itr = m.entryKeySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, SortedSet<Integer>> entry = itr.next();
			if (entry.getValue().size() > n) {
				result.add(entry.getKey());
			} else {
				if (entry.getValue().size() > n) {
					result.add(entry.getKey());
				}
			}
		}
		
		return result;
	}
		
	public static void main(String[] args) {
		try {
			MultiMap<String, Integer> m = obtenerMultiMap("texto.txt");
			System.out.println(m);
			System.out.printf("\nPalabras que aparecen en más de 3 líneas: %s\n",
					enMasLineas(m, 3));		

			m.remove("una", 8);
			m.remove("una", 9);

			MultiMap<String, Integer> sinUna = obtenerMultiMap("textoSinUna.txt");
			System.out.printf("\n¿m == sinUna? %b\n", m.equals(sinUna));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

// Salida esperada con las líneas 80 a 83 comentadas:
//Al-Juarismi: [4]
//Dados: [7]
//En: [1]
//Los: [9]
//a: [7, 8]
//actividades: [7]
//algorithmus: [2]
//algoritmia: [10]
//algoritmo: [2]
//algoritmos: [9]
//ambiguas: [5]
//arithmos: [3]
//cabo: [7]
//ciencias: [1]
//computación: [1]
//con: [3]
//conjunto: [4]
//cómputo: [6]
//datos: [6]
//de: [1, 4, 9]
//definidas: [5]
//del: [2, 3, 4]
//disciplinas: [1]
//el: [9]
//entrada: [8]
//es: [4]
//estado: [7, 8]
//este: [2]
//estudio: [9]
//final: [8]
//finitas: [5]
//griego: [2]
//influencia: [3]
//inicial: [7]
//instrucciones: [4]
//la: [1, 9]
//latín: [2]
//llega: [8]
//llevar: [7]
//los: [8]
//lógica: [1]
//matemáticas: [1]
//matemático: [4]
//no: [5]
//nombre: [4]
//número: [3]
//o: [5, 7]
//objeto: [9]
//obtiene: [9]
//ordenadas: [5]
//otras: [7]
//pasos: [8]
//permite: [5]
//persa: [4]
//problema: [6]
//procesar: [6]
//que: [3, 5]
//quizá: [3]
//realizar: [6]
//reglas: [5]
//relacionadas: [2]
//se: [8, 9]
//significa: [3]
//siguiendo: [8]
//solucionar: [6]
//solución: [9]
//son: [9]
//sucesivos: [8]
//también: [3]
//tareas: [7]
//típicamente: [6]
//un: [2, 4, 6, 7, 8]
//una: [8, 9]
//y: [1, 2, 5, 7, 9]
//
//
//Palabras que aparecen en más de 3 líneas: [un, y]