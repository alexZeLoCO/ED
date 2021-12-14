package estDatos;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Collection;

public class LPCHash<E> extends AbstractSet<E> {

	private static final double DEFAULT_LOAD_FACTOR_LIMIT = 0.5;
	private static final int DEFAULT_CAPACITY = 11;

	private E[] table; // tabla de elementos
	private int[] status; // estados->0:vacío;1:borrado;2:ocupado
	private int tablesize; // tamaño de la tabla
	private double loadFactorLimit; // factor de carga límite
	private int elements; // número de elementos
	private int deleted; // número de posiciones borradas

	public LPCHash() throws IllegalArgumentException {
		this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR_LIMIT);
	}

	public LPCHash(int initialCapacity) throws IllegalArgumentException {
		this(initialCapacity, DEFAULT_LOAD_FACTOR_LIMIT);
	}

	@SuppressWarnings("unchecked")
	public LPCHash(int initialCapacity, double theLoadFactor) throws IllegalArgumentException {
		if (theLoadFactor > 1 || theLoadFactor < 0 || initialCapacity <= 0) {
			throw new IllegalArgumentException();
		}
		this.table = (E[]) new Object[initialCapacity];
		this.elements = 0;
		this.tablesize = initialCapacity;
		this.loadFactorLimit = theLoadFactor;
		this.deleted = 0;
		this.status = new int[initialCapacity];
		for (int i = 0; i < initialCapacity; i++) {
			this.status[i] = 0;
		}
	}

	public LPCHash(Collection<? extends E> c) throws IllegalArgumentException {
		this();
		this.addAll(c);
	}

	/**
	 * Returns the hash value of the element e
	 * 
	 * @param e Element to be calculated
	 * @return Hash of e
	 */
	private int hash(E e) {
		return e.hashCode() % this.tablesize;
	}

	/**
	 * Returns the hash value of e after certain collisions
	 * 
	 * @param e   Element to be calculated
	 * @param col Number of collisions
	 * @return Hash of e
	 */
	private int rehash(E e, int col) {
		return (hash(e) + col) % this.tablesize;
	}

	@Override
	public boolean add(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (this.contains(e)) {
			return false;
		}
		if (this.checkOverLoadFactor()) {
			resize(2);
		}
		int pos = firstEraseEmpty(e);
		if (status[pos] == 1) {
			deleted--;
		}
		this.status[pos] = 2;
		this.table[pos] = e;
		this.elements++;
		return true;
	}

	/**
	 * Checks if the number of values int the Structure over the size of the
	 * Structure is higher than the load factor limit
	 *
	 * @return True if there is Overload
	 */
	private boolean checkOverLoadFactor() {
		return (((double) this.size()) / this.tablesize) > this.loadFactorLimit;
	}

	/**
	 * Resizes the structure given a multiplier
	 * 
	 * @param multiplier Multipliier by which the structure size will be increased
	 */
	public void resize(int multiplier) {
		this.tablesize = multiplier * this.tablesize + 1;
		try {
			LPCHash<E> neu = new LPCHash<E>(this.tablesize, this.loadFactorLimit);
			neu.addAll(this);
			this.status = neu.status;
			this.table = neu.table;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Returns the first position of the structure that is empty or equal to e
	 * 
	 * @param e Element to be searched for
	 * @return Position in the structure
	 */
	private int firstEqualEmpty(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		int pos = hash(e);
		int col = 0;
		while ((this.status[pos] == 2 && !this.table[pos].equals(e)) || this.status[pos] == 1) {
			pos = rehash(e, ++col);
		}
		return pos;
	}

	/**
	 * Returns the first position of the structure that has been erased or is empty
	 * 
	 * @param e Element to be searched with
	 * @return Position in the structure
	 */
	private int firstEraseEmpty(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		int pos = hash(e);
		int col = 0;
		while (this.status[pos] == 2) {
			pos = rehash(e, ++col);
		}
		return pos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object e) {
		if (e == null) {
			throw new NullPointerException();
		}
		return this.status[firstEqualEmpty((E) e)] == 2;
	}

	@SuppressWarnings("unchecked")
	public boolean remove(Object e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (!this.contains(e)) {
			return false;
		}
		this.status[firstEqualEmpty((E) e)] = 1;
		this.deleted++;
		this.elements--;
		return true;
	}

	@Override
	public Iterator<E> iterator() {
		return new LPCHI();
	}

	private final class LPCHI implements Iterator<E> {

		private int idx;
		private int visited;
		private E lastVisited;

		public LPCHI() {
			this.visited = 0;
			this.idx = -1;
			findNext();
		}

		@Override
		public boolean hasNext() {
			return visited < elements && idx < tablesize;
		}

		@Override
		public E next() {
			E e = table[idx];
			this.lastVisited = e;
			visited++;
			findNext();
			return e;
		}

		/**
		 * Finds the next position that has an element
		 * 
		 * @return Next position with element
		 */
		private int findNext() {
			while (hasNext() && status[++idx] != 2) {

			}
			return idx;
		}

		/**
		 * Removes the last visited element
		 */
		public void remove() {
			if (this.lastVisited == null) {
				throw new NoSuchElementException();
			}
			LPCHash.this.remove(lastVisited);
			this.visited--;
			this.lastVisited = null;
		}
	}

	@Override
	public int size() {
		return this.elements + this.deleted;
	}

	@Override
	public String toString() {
		String string = "Table\n";
		for (E e : this) {
			string = string + "\t\t" + e.toString() + "\n";
		}
		return String.format("LPCHash:\t%s", string);
	}

}
