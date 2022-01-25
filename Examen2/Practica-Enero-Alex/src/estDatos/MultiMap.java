package estDatos;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import java.util.TreeMap;
import java.util.TreeSet;

public class MultiMap<K, V> extends AbstractMap<K, V> {
	private SortedMap<K, SortedSet<V>> data;	// diccionario de pares
												// clave/conjunto_de_valores_asociados
	private Set<Map.Entry<K, V>> entrySet;		// vista del multidiccionario como un
												// conjunto de pares clave/valor

	/**
	 * Crea un multidiccionario vacío. La comparación de las claves
	 * se realiza según el orden natural de éstas.
	 */
	public MultiMap() {
		entrySet = new EntrySet();
		// COMPLETAR
		this.data = new TreeMap<K, SortedSet<V>> ();
	}
		
	/**
	 * Crea un multidiccionario vacío. Las comparaciones entre
	 * claves se llevan a cabo mediante el comparador especificado.
	 * @param cmp el comparador de las claves del multidiccionario
	 */
	public MultiMap(Comparator<? super K> cmp) {
		// COMPLETAR
		this.entrySet = new EntrySet();
		this.data = new TreeMap<K, SortedSet<V>> (cmp);
	}

	/**
	 * Crea un multidiccionario copia del diccionario especificado.
	 * @param m el diccionario a copiar
	 */
	public MultiMap(Map<K, V> m) {
		// COMPLETAR
		this.data = new TreeMap<K, SortedSet<V>> ();
		this.entrySet =  new EntrySet();
		for (Map.Entry<K, V> e : m.entrySet()) {
			this.put(e.getKey(), e.getValue());
			/*
			 * Otra posibilidad
			this.data.put(e.getKey(), new TreeSet<V> ());		// Key + Slot
			this.data.get(e.getKey()).add(e.getValue());		// Value
			*/
		}
	}
	
	/**
	 * Crea un multidiccionario copia del diccionario especificado.
	 * @param m el diccionario a copiar
	 */
	public MultiMap(SortedMap<K, V> m) {
		// COMPLETAR
		if (m.comparator() != null) {
			this.data = new TreeMap<K, SortedSet<V>> (m.comparator());		// Copia con comparador
		} else {
			this.data = new TreeMap<K, SortedSet<V>> ();		// Copia sin comparador. Llamada a constructor por defecto para
																// evitar confusión con constructor(null)
		}
		this.entrySet =  new EntrySet();
		for (Map.Entry<K, V> e : m.entrySet()) {
			this.put(e.getKey(), e.getValue());
			/*
			 * Otra posibilidad
			if (!this.data.containsKey(e.getKey())) {		// Key puede estar repetida
				this.data.put(e.getKey(), new TreeSet<V> ());	//Si no existente, Key + Slot
			}
			this.data.get(e.getKey()).add(e.getValue()); // Value
			*/
		}
	}

	/**
	 * Retorna la vista del multidiccionario como un conjunto
	 * de pares clave/valor.
	 * @return la vista del multidiccionario como un conjunto
	 * de pares clave/valor
	 */
	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return entrySet;
	}
	
	/**
	 * Retorna la vista del multidiccionario como un conjunto
	 * de pares clave/valores_asociados, de forma que todos los
	 * valores asociados a una misma clave están contenidos en
	 * un conjunto ordenado.
	 * @return la vista del multidiccionario como un conjunto
	 * de pares clave/conjunto_de_valores_asociados
	 */
	public Set<Map.Entry<K, SortedSet<V>>> entryKeySet() {
		return data.entrySet();
	}
	
	/**
	 * Añade la clave y valor especificados al multidiccionario. Si este ya
	 * contiene el par {@code key/value} retorna el valor especificado, en
	 * caso contrario retorna {@code null}
	 * @param key la clave dada
	 * @param value el valor dado
	 * @return {@code null} si el multidiccionario no contiene el par {@code key/value}
	 * y {@code value} en caso contrario
	 */
	@Override
	public V put(K key, V value) {
		// COMPLETAR
		if (this.data.containsKey(key) && this.data.get(key).equals(value)) {	// Ya contenido ==> value
			return value;
		}
		if (!this.data.containsKey(key)) {	// Key no existente ==> Crear
			this.data.put(key, new TreeSet<V> ());
		}
		this.data.get(key).add(value);	// Key existente ==> Incluir ==> null
		return null;
	}
	
	/**
	 * Quita del multidiccionario la clave y valor especificados. Si esta asociación
	 * no se encuentra retorna {@code false} y {@code true} en caso contrario.
	 * @param key la clave dada
	 * @para value el valor dado
	 * @return {@code true} si el par {@code key/value} se encuentra en el diccionario
	 * y {code false} en caso contrario
	 */
	@Override
	public boolean remove(Object key, Object value) {
		// COMPLETAR
		if (!this.data.containsKey(key) || !this.data.get(key).contains(value)) {	// No existe o no coinciden ==> false
			return false;
		}
		this.data.get(key).remove(value);	// Existe y coincide ==> Retirar Value
		if (this.data.get(key).isEmpty()) {		// No quedan más elementos ==> Retirar Key
			this.data.remove(key);
		}
		return true;
	}
	
	/**
	 * Retorna la representación del multidiccionario como una cadena
	 * de caracteres, en la forma:
	 * <pre>
	 * clave1: conjunto de valores asociados a clave1
	 * clave2: conjunto de valores asociados a clave2
	 * ...
	 * </pre>
	 * @return la representación del multidiccionario como una cadena
	 * de caracteres
	 */
	@Override
	public String toString() {
		// COMPLETAR
		String out = "";	// Output
		Iterator<Map.Entry<K, SortedSet<V>>> itr = this.data.entrySet().iterator();	// Keys
		while (itr.hasNext()) {
			Map.Entry<K, SortedSet<V>> current = itr.next();		// currentKey
			/*
			out+=current.getKey().toString() + ": ";
			Iterator<V> itri = current.getValue().iterator();	// Values
			while (itri.hasNext()) {		// Formato: a b c
				out+=itri.next() + " ";
			}
			out+="\n";
			*/
			out+=current.getKey().toString() + ": " + current.getValue().toString() + "\n";	//Formato: [a, b, c]
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MultiMap<?, ?>)) {
			return false;
		}
		MultiMap<K,V> other = (MultiMap<K, V>) obj;
		if (data == null) {
			return (other.data == null);
		}
		return data.equals(other.data);
	}
	
	/**
	 * Una instancia de esta clase es una vista del multidiccionario
	 * como un conjunto de pares clave/valor (K/V)
	 */
	private final class EntrySet extends AbstractSet<Map.Entry<K, V>> {

		@Override
		public Iterator<Map.Entry<K, V>> iterator() {
			// COMPLETAR
			return new EntrySetIterator ();	// Iterador de entry set que repite Keys por cada Value (Línea 260).
		}

		@Override
		public int size() {
			// COMPLETAR
			return MultiMap.this.data.size();	// nº keys
		}

		@Override
		public boolean add(Map.Entry<K, V> e) {
			// COMPLETAR
			return MultiMap.this.put(e.getKey(), e.getValue()) == null;			// put(K, V) retorna null si se ha incluido el par K V
		}
		
		/**
		 * Una instancia de esta clase es un iterador de la vista del
		 * multidiccionario como un conjunto de pares clave/valor
		 */
		private class EntrySetIterator implements Iterator<Map.Entry<K, V>> {
			private Iterator<Map.Entry<K, SortedSet<V>>> itrMap;  // iterador sobre el diccionario data
																  // (de su vista como un conjunto de
																  // pares clave/valores_asociados)
			private Iterator<V> itrSet;							  // iterador del conjunto de valores
																  // asociados a una misma clave
			private Map.Entry<K, SortedSet<V>> currentKeySet;	  // par clave/valores_asociados en curso
			private boolean opNext;								  // indica si se ha realizado la operación
																  // next()
			
			private void searchNextKey() {
				// siguiente par clave/valores_asociados
				boolean found = false;
				while (!found && itrMap.hasNext()) { // ignorar claves sin valores asociados
					currentKeySet = itrMap.next();
					itrSet = currentKeySet.getValue().iterator();
					found = itrSet.hasNext();
				}
			}
			
			private EntrySetIterator() {
				opNext = false;
				itrMap = entryKeySet().iterator();		//this.data.entrySet().iterator();	==> NO es clase EntrySet
				searchNextKey();
			}

			@Override
			public boolean hasNext() {
				return itrMap.hasNext() || itrSet.hasNext();
			}

			@Override
			public Entry<K, V> next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				
				if (!itrSet.hasNext()) {
					searchNextKey();
				}
				
				opNext = true;				
				return new SimpleEntry<>(currentKeySet.getKey(),		//Par Key, Value.		--> Repite Key para cada value <---
										 itrSet.next());
			}

			@Override
			public void remove() {
				if (!opNext) {
					throw new IllegalStateException();
				}
				
				opNext = false;
				itrSet.remove();
			}	
		}
	}
	
}
