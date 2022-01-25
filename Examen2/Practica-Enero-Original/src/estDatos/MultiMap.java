/*
 * Rellenar todo lo que falta. (7.5 Pt)
 * 
 * Al eliminar todos los values de una key, se queda la key con un set vacío.
 * Si se hacer un equals con otro MultiMap que no tenga esa key, resultará falso.
 * Esto se puede solucionar eliminando las entradas que no tengan ningún value.
 * Hacer los cambios necesarios para que el equals sea correcto. (2.5 Pt)
 */

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
	}
		
	/**
	 * Crea un multidiccionario vacío. Las comparaciones entre
	 * claves se llevan a cabo mediante el comparador especificado.
	 * @param cmp el comparador de las claves del multidiccionario
	 */
	public MultiMap(Comparator<? super K> cmp) {
		// COMPLETAR
	}

	/**
	 * Crea un multidiccionario copia del diccionario especificado.
	 * @param m el diccionario a copiar
	 */
	public MultiMap(Map<K, V> m) {
		// COMPLETAR
	}
	
	/**
	 * Crea un multidiccionario copia del diccionario especificado.
	 * @param m el diccionario a copiar
	 */
	public MultiMap(SortedMap<K, V> m) {
		// COMPLETAR
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
		return null;
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
			return null;
		}

		@Override
		public int size() {
			// COMPLETAR
			return 0;
		}

		@Override
		public boolean add(Map.Entry<K, V> e) {
			// COMPLETAR
			return false;
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
				itrMap = entryKeySet().iterator();	
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
				return new SimpleEntry<>(currentKeySet.getKey(),		
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
