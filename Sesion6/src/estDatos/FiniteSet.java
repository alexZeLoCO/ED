package estDatos;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Tipo de dato modificable de conjuntos finitos. Los conjuntos
 * sólo contendrán elementos de un rango dado. Por tanto, el 
 * conjunto universal (tiene todos los elementos posibles) está
 * constituido por todos los valores de dicho rango.
 */
public class FiniteSet<E> extends AbstractSet<E> {
	private Range<E> universal;		// conjunto universal
	private List<Boolean> vbool;	// secuencia de booleanos
	private int numItems;			// número de elementos del conjunto
	
	/**
	 * Crea un conjunto finito para el rango especificado
	 * que contiene los  elementos dados.
	 * @param r el rango asociado
	 * @param items los elementos del conjunto
	 */
	@SafeVarargs
	public FiniteSet(Range<E> r, E...items) {
		this.universal = r;
		this.numItems = 0;
		this.vbool = new ArrayList<Boolean>(r.size());
		
		for (int i=0; i<r.size();i++) {
			this.vbool.add(false);
		}
		
		for (E e : items) {
			this.add(e);
			//vbool.set(r.eToInt(e), true);
		}
	}
	
	public FiniteSet(Range<E> r) {
		this.universal = r;
		this.numItems = 0;
		this.vbool = new ArrayList<Boolean>(r.size());
		for (int i=0; i<r.size();i++) {
			this.vbool.add(false);
		}
	}
	
	/**
	 * Crea un conjunto finito copia del especificado.
	 * @param c el conjunto finito a copiar
	 */
	public FiniteSet(FiniteSet<E> c) {
		this.universal = c.universal;
		this.numItems = 0;
		this.vbool = new ArrayList<Boolean>(this.numItems);
		for (int i = 0; i<this.size(); i++) {
			this.vbool.set(i, c.vbool.get(i));
			this.numItems++;
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new IteratorFS();
	}

	private final class IteratorFS implements Iterator<E> {

		private int idx;
		private boolean opNext;
		
		public IteratorFS () {
			this.idx=-1;
			this.opNext = false;
		}
		
		@Override
		public boolean hasNext() {
			if (idx>=vbool.size()) { 
				return false;
			}
			for (int i = idx+1; i<vbool.size() ; i++) {
				if (vbool.get(i)) {
					return true;
				}
			}
			return false;
		}
		

		@Override
		public E next() {
			
			if (!find()) {
				throw new IndexOutOfBoundsException();
			}
			this.opNext = true;
			return universal.intToE(idx);
		}
		
		@Override
		public void remove () {
			if (!opNext) {
				throw new IllegalStateException();
			}
			vbool.set(idx, false);
		}
		
		private boolean find () {
			for (int i = idx+1; i<vbool.size() ; i++) {
				if (vbool.get(i)) {
					this.idx=i;
					return true;
				}
			}
			return false;
		}
		
	};
	
	@Override
	public int size() {
		return this.numItems;
	}
	
	public boolean add (E e) {
		if (this.vbool.get(universal.eToInt(e))) {
			return false;
		}
		this.vbool.set(universal.eToInt(e), true);
		this.numItems++;
		return true;
	}

	@Override
	public String toString () {
		return String.format("%s (%d elementos)", super.toString() , this.size());
	}
	
} // class FiniteSet
