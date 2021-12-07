package estDatos;

import java.util.Iterator;

public class PeekingIterator<E> implements PeekIterator<E> {
	private Iterator<E> itr;	// el iterador de un objeto iterable
	private boolean hasCurrent;	// cierto si se invoca peek() antes de next() 
	private E current;			// el elemento actual

	public PeekingIterator(Iterator<E> iterator) {
		itr = iterator;
		hasCurrent = false;
		current = null;
	}
	
	public E peek() {
		if (!hasCurrent) {
			current = itr.next();
			hasCurrent = true;
		}
		
		return current; 
	}
	
	/**
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return hasCurrent || itr.hasNext();
	}
	
	/**
	 * @see java.util.Iterator#next()
	 */
	@Override
	public E next() {
		boolean temp = hasCurrent;
		hasCurrent = false;
		
		return (temp ? current : itr.next());
	}

}
