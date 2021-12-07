package estDatos;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Tipo de dato no modificable Monomio. Un monomio consta de dos partes:
 * <ul><li>Un número real denominado coeficiente</li>
 * <li>Un literal constituido por productos de variables con un
 * exponente natural</li></ul>
 */
public class Monomio {
	// área de datos (representación)
	private double coeficiente;
	private Map <Character, Integer> literal;

	/**
	 * Crea el monomio de coeficiente 1 y literal vacío.
	 */
	public Monomio() {
		this.coeficiente=1;
		this.literal = new TreeMap<Character, Integer> ();
	}

	public Monomio (double coeficiente, Map<Character, Integer> literal) {
		if (literal == null || coeficiente == 0) {
			throw new NullPointerException ();
		}
		
		this.coeficiente = coeficiente;
		this.literal = new TreeMap <Character, Integer>(literal);
	}
	
	/**
	 * Crea el monomio que se proporciona mediante la cadena de
	 * caracteres especificada.
	 * @param str la cadena dada
	 * @throws IllegalArgumentException si la cadena de caracteres no casa
	 * con el patrón {@code ((?:\+|-)?\d*(?:\.\d*)?)?(\p{Alpha}\^?\d*)*}
	 */
	public Monomio (String str) {
		// se proporciona la parte de validación mediante
		// la expresión regular de patrón indicado
		this();
		String patternCoef = "((?:\\+|-)?\\d*(?:\\.\\d*)?)?";
		String patternLiteral = "(\\p{Alpha}\\^?\\d*)*";
		if (str.isEmpty() ||
				!str.matches(patternCoef + patternLiteral)) {
			throw new IllegalArgumentException("Monomio mal formado");
		}

		Pattern pattern = Pattern.compile(patternCoef);
		Matcher matcher = pattern.matcher(
				str.subSequence(0, str.length()));

		this.coeficiente = 1;  // coeficiente del monomio por defecto
		int startLiteral = 0;
		if (matcher.find() && !matcher.group().isEmpty()) {
			// obtener el coeficiente
			this.coeficiente = Double.parseDouble(matcher.group());
			startLiteral = matcher.end();
		}

		// string del literal del monomio
		String literal = str.substring(startLiteral);

        pattern = Pattern.compile("\\p{Alpha}\\^?\\d*");
        matcher = pattern.matcher(literal);
        while (matcher.find()) { // obtener una variable y su exponente
        	String s = matcher.group();
        	// variable (un carácter)
        	char variable = s.charAt(0);
        	// exponente de la variable
        	int exponente =
        			s.length() == 1 ? 1 : Integer.parseInt(s.substring(1));

			// Completar
        	Integer valor =  this.literal.get(variable);
        	if (valor != null) {
        		exponente += valor;
        	}
        	this.literal.put(variable, exponente);
        }
	}

	// Completar con todas las operaciones que se especifican
	// en la documentación proporcionada (monomio.html)
	
	public Monomio (Monomio m) {
		if (m == null) {
			throw new NullPointerException ();
		}
		this.coeficiente = m.coeficiente;
		this.literal = m.literal;
	}
	
	public int grado () {
		return this.literal.get(((TreeMap<Character, Integer>) this.literal).lastKey());
	}

	public static Monomio porEscalar (double x, Monomio m) {
		return new Monomio(x*m.coeficiente, m.literal);
	}
	
	public static Monomio potencia (Monomio m, int x) {
		if (x < 0) {
			throw new IllegalArgumentException ();
		}
		Monomio output = new Monomio (Math.pow(m.coeficiente, x), new TreeMap<Character, Integer> ());
		for (Entry<Character, Integer> s : m.literal.entrySet()) {
			output.literal.put(s.getKey(), s.getValue()*x);
		}
		return output;
	}
	
	public static Monomio producto (Monomio m1, Monomio m2) {
		Monomio output = new Monomio (m1.coeficiente * m2.coeficiente, new TreeMap<Character, Integer> ());
		PeekingIterator <Entry<Character, Integer>> itr1 = new PeekingIterator <> (m1.literal.entrySet().iterator());
		PeekingIterator <Entry<Character, Integer>> itr2 = new PeekingIterator <> (m2.literal.entrySet().iterator());
		
		while (itr1.hasNext() && itr2.hasNext()) {
			Map.Entry<Character, Integer> c1 = itr1.peek();
			Map.Entry<Character, Integer> c2 = itr2.peek();
			
			int x = c1.getKey().compareTo(c2.getKey());
			if (x==0) {
				output.literal.put(c1.getKey(), c1.getValue()+c2.getValue());
				itr1.next();
				itr2.next();
			} else {
				Map.Entry<Character, Integer> par; 
				if (x<0) {
					par = c1;
					itr1.next();
				} else {
					par = c2;
					itr2.next();
				}
				output.literal.put(par.getKey(), par.getValue());
			}
		}
		while (itr1.hasNext()) {
			Map.Entry<Character, Integer> c = itr1.peek();
			output.literal.put(c.getKey(), c.getValue());
			itr1.next();
		}
		while (itr2.hasNext()) {
			Map.Entry<Character, Integer> c = itr2.peek();
			output.literal.put(c.getKey(), c.getValue());
			itr2.next();
		}
		/*
		while (itr1.hasNext() && itr2.hasNext()) {
			if (itr1.peek().getKey().equals(itr2.peek().getKey())) {
				output.literal.put(itr1.peek().getKey(), itr1.peek().getValue()+itr2.peek().getValue());
				itr1.next();
				itr2.next();
			} else {
				output.literal.put(itr1.peek().getKey(), itr1.peek().getValue());
				itr1.next();
			}
		}
		while (itr1.hasNext()) {
			itr1.next();
			output.literal.put(itr1.peek().getKey(), itr1.peek().getValue());
		}
		while (itr2.hasNext()) {
			itr2.next();
			output.literal.put(itr2.peek().getKey(), itr2.peek().getValue());
		}
		*/
		return output;
	}
	
	public boolean semejante (Monomio m) {
		return this.literal.equals(m.literal);
	}
	
	public static Monomio suma (Monomio m1, Monomio m2) throws Exception {
		if (!m1.semejante(m2)) {
			throw new Exception (String.format("\nLos monomios no son semejantes:\n\t%s\t%s", m1, m2));
		}
		return new Monomio (m1.coeficiente+m2.coeficiente, m1.literal);
	}
	
	@Override
	public String toString () {
		return String.format("Monomio de grado %d: %.2f %s\n",this.grado(), this.coeficiente, this.literal.toString());
	}
}
