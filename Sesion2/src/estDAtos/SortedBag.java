package estDatos;

import java.util.Collection;
import java.util.Comparator;

public class SortedBag <E> extends BagMod <E> {

	private Comparator <? super E> cmp;

	public SortedBag (final Collection <? extends E> c, Comparator<? super E> cmp) {
		super(c);
		this.cmp = cmp;
	}
	
	public SortedBag() {
		super();
		this.cmp = null;
	}

	public SortedBag (final int capacity) {
		super(capacity);
		this.cmp = null;
	}

	public SortedBag (final Collection<? extends E> c) {
		super(c);
		//El constructor de BagMod, llama a addAll() para añadir todo, addAll() va llamando a add() para cada elemento ==> cada elemento va a estar ya ordenado
		this.cmp = null;
	}

	
	@SuppressWarnings("unchecked")
	private int micompare(final E a, final E b) {
		if (this.cmp == null && !(a instanceof Comparable<?>))  {
			throw new ClassCastException (String.format("El tipo %s no es comparable", a.getClass().getName()));
		}
		return cmp == null ? ((Comparable<E>) a).compareTo(b) : this.cmp.compare(a, b);
	}
	 

	@Override
	public boolean add (final E e) {
		if (e == null) {
			throw new NullPointerException();
		}

		if (super.numItems == super.data.length) {
			super.resize();
		}

		int idx = 0;
		while (cmp.compare(e, super.data[idx])>0 && idx<super.numItems) {
			idx++;
		}
		if (idx==super.numItems) {
			super.add(e);
		} else {
			for (int i = super.numItems; i>idx; i--) {
				super.data[i] = super.data[i+1];
			}
			super.data[idx] = e;
			numItems++;
		}
		return true;
	}
}
