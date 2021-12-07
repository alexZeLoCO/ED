package estDatos;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class OpenHash<E> extends AbstractSet <E> implements Set <E> {
	
	private static final int DEFAULT_INITIAL_CAPACITY = 11;
	private static final double DEFAULT_LOAD_FACTOR_LIMIT = 0.75;
	private static final int DEFAULT_CAPACITY_MULTIPLIER = 2;
	
	private ArrayList<TreeSet<E>> table; 		//tabla de colecciones de elementos
	private int elements; 						//número de elementos
	private int tablesize; 						//tamaño de la tabla
	private double loadFactorLimit; 			//límite del factor de carga
	private LinkedList<E> elemList; 			//elementos guardados en forma de lista
	
	public OpenHash () {
		this(DEFAULT_INITIAL_CAPACITY ,DEFAULT_LOAD_FACTOR_LIMIT);
	}
	
	public OpenHash (int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR_LIMIT);
	}
	
	public OpenHash (int initialCapacity, double theLoadFactor) {
		this.table = new ArrayList <TreeSet<E>> (initialCapacity);
		this.tablesize = initialCapacity;
		for (int i = 0; i < this.tablesize ; i++) {
			this.table.add(i, new TreeSet<E> ());
		}
		this.elements = 0;
		this.loadFactorLimit = theLoadFactor;
		this.elemList = new LinkedList <E> ();
	}
	
	public OpenHash (Collection <? extends E> c) {
		this();
		this.addAll(c);
	}
	
	@Override
	public boolean add (E e) {
		if (e == null) {
			throw new NullPointerException ();
		}
		if (this.contains(e)) {
			return false;
		}
		if (this.checkOverloadFactor()) {
			this.resize(DEFAULT_CAPACITY_MULTIPLIER);
		}
		this.table.get(hash(e)).add(e);
		this.elemList.add(e);
		this.elements++;
		return true;
	}
	
	private boolean checkOverloadFactor () {
		return ((double) this.elements) / this.size() >= this.loadFactorLimit;
	}
	
	private void resize (int capacityMultiplier) {
		OpenHash aux = new OpenHash (this.size()*capacityMultiplier, this.loadFactorLimit);
		aux.addAll(this);
		this.table = aux.table;
		this.elements = aux.elements;
		this.tablesize = aux.size();
		/*
		this.table = new ArrayList <TreeSet<E>> (this.size() * capacityMultiplier);
		this.tablesize = this.size() * capacityMultiplier;
		this.addAll(elemList);
		*/
	}
	
	@Override
	public boolean contains (Object e) {
		if (e == null) {
			throw new NullPointerException ();
		}
		return table.get(hash((E) e)).contains(e);
		/*
		if (this.table.get(hash((E) e)) == null) {
			return false;
		}
		if (this.table.get(hash((E) e)).contains(e)) {
			return true;
		}
		return false;
		*/
	}
	
	@Override
	public boolean remove (Object e) {
		if (e == null) {
			throw new NullPointerException ();
		}
		if (this.contains(e)) {
			this.table.get(hash((E) e)).remove(e);
			this.elemList.remove(e);
			this.elements--;
			return true;
		}
		return false;
	}
	
	private int hash (E e) {
		if (e == null) {
			throw new NullPointerException ();
		}
		return e.hashCode() % this.size();
	}
	
	@Override
	public Iterator<E> iterator() {
		return new OHI();
	}

	private final class OHI implements Iterator <E> {
		
		private Iterator<E> itr;
		
		public OHI () {
			this.itr = elemList.iterator();
		}
		
		@Override
		public boolean hasNext() {
			return this.itr.hasNext();
		}

		@Override
		public E next() {
			return this.itr.next();
		}
		
	}
	
	@Override
	public int size() {
		return this.tablesize;
	}

	public String printTable () {
		String out = "";
		for (int i = 0; i<this.size(); i++) {
			out = out + i + ":\t";
			for (E e : this.table.get(i)) {
				out = out + e.toString();
			}
			out = out + "\n";
		}
		return out;
	}
}
