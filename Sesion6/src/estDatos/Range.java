package estDatos;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Tipo de dato no modificable de rangos de valores escalares:
 * booleanos, caracteres, enteros o enumerados.
 * @param <E> el tipo de los valores del rango
 */
public class Range<E> implements Iterable<E> {
	private E leftmost;	// valor inferior del rango
	private int length;	// longitud del rango

	/**
	 * Crea el rango escalar {@code [left, right]}.
	 * @param left el extremo inferior del rango
	 * @param right el extremo superior del rango
	 * @throws ClassCastException si el tipo del rango no es
	 * escalar: booleano, carácter, entero o enumerado
	 * @throws IllegalArgumentException si el rango está vacío
	 * {@code (left > right)}
	 */
	public Range(E left, E right) {
		if (!(left instanceof Integer) &&
				!(left instanceof Character) &&
				!(left instanceof Boolean) &&
				!(left instanceof Enum)) {
			throw new ClassCastException("Solo tipos escalares");
		}
		
		this.leftmost = left; 		// extremo inferior del rango
		int n = eToInt(right) + 1;	// longitud del rango
		
		if (n <= 0) {
			throw new IllegalArgumentException("Rango vacío");
		}
					
		this.length = n;		// longitud del rango
	}

	/**
	 * Retorna la posición del elemento especificado en este rango.
	 * @param e el elemento proporcionado
	 * @return la {@code 0<=posición<length} que ocupa {@code e} en
	 * este rango, donde {@code length} es su longitud. Si {@code e}
	 * no es un elemento del rango da una posición no válida.
	 */
	public int eToInt(E e) {
		if (e instanceof Integer) {
			return (Integer)e - (Integer)this.leftmost;
		} else if (e instanceof Character) {
			Character other = (Character)e;
			Character left = (Character)this.leftmost;
			return other.charValue() - left.charValue();
		} else if (e instanceof Boolean) {
			Boolean other = (Boolean)e;
			Boolean left = (Boolean)this.leftmost;
			if (left) {
				return 1;
			}
			return other ? 1 : 0;
		} else {
			Enum<?> other = (Enum<?>)e;
			Enum<?> left = (Enum<?>)this.leftmost;
			return other.ordinal() - left.ordinal();
		}
	}
	
	/**
	 * Retorna el valor de este rango que ocupa la posición
	 * especificada.
	 * @param n la posición
	 * @return el valor de este rango que está en la posición
	 * {@code 0<=n<length}
	 * @throws IllegalArgumentException si la posición si
	 * {@code n<0 || n>=length}
	 */
	@SuppressWarnings("unchecked")
	public E intToE(int n) {
		if (n < 0 || n >= this.length) {
			throw new IllegalArgumentException();
		}
		if (leftmost instanceof Integer) {
			return (E)new Integer(n + (Integer)this.leftmost);
		} else if (leftmost instanceof Character) {
			Character left = (Character)this.leftmost;
			return (E)new Character((char)(n + left.charValue()));
		} else if (leftmost instanceof Boolean) {
			Boolean left = (Boolean)this.leftmost;
			if (left) {
				return (E)new Boolean(true);
			}
			return (E)new Boolean(n == 0 ? false : true);
		} else {
			Enum<?> left = (Enum<?>)this.leftmost;
			Class<?> enumerado = this.leftmost.getClass();
			Object[] constantes = enumerado.getEnumConstants();		
			return (E)constantes[n + left.ordinal()];
		}
	}
	
	/**
	 * Retorna el tamaño de este rango.
	 * @return el tamaño de este rango
	 */
	public int size() {
		return this.length;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Range<?>)) {
			return false;
		}
		Range<E> other = (Range<E>) obj;
		if (this.leftmost != other.leftmost) {
			return false;
		}
		return this.length == other.length;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			// posición del elemento del rango que retornará next()
			private int current = 0;

			@Override
			public boolean hasNext() {
				return this.current < length;
			}

			@Override
			public E next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				}
				
				this.current++;
				return intToE(this.current - 1);	
			}
		};
	}
	
} // class Range
