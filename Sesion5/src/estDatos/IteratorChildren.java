package estDatos;

import java.util.Iterator;

public interface IteratorChildren<E> extends Iterator<E> {
	
	/**
	 * Reemplaza el último elemento retornado por {@code next()} por
	 * el elemento especificado (opcional).
	 * @param e el elemeto de reemplazo
	 * @throws IllegalStateException si no se ha llamado a {@code next()}
	 * o se ha reemplazado o borrado el elemento después de la
	 * última llamada a {@code next()}
	 * @throws NullPointerException si el árbol no admite etiquetas
	 * de valor {@code null}
	 */
	default void set(E e) {
		throw new UnsupportedOperationException();
	}

}
