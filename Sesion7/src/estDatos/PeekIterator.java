/**
 * 
 */
package estDatos;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Interfaz que extiende la interfaz {@code Iterator<E>}
 * que añade la operación {@code peek()}.
 * @param <E> el tipo de los elementos que proporcionará,
 * uno a uno, el iterador.
 */
public interface PeekIterator<E> extends Iterator<E> {

	/**
	 * Permite consultar, pero no dar, el siguiente elemento a iterar.
	 * @return el valor del siguiente elemento de la iteración;
	 * es decir, el elemento que retornará la operación next()
	 * @throws NoSuchElementException si la iteración no tiene
	 * más elementos.
	 */
	E peek();

}
