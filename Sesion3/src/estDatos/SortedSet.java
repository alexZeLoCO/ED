package estDatos;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

public class SortedSet<E> extends SetNoMod<E> {

	private Comparator<? super E> cmp;
	/*
	public SortedSet (Collection <? extends E> c) {
		super(c);
	}
	
	@SafeVarargs
	public SortedSet (E... e) {
		super(e);
	}
	
	@Override
	public boolean add (final E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		int idx = 0;
		while (cmp.compare(e, super.data.get(idx))>0 && idx<super.data.size()) {
			idx++;
		}
		if (idx==super.data.size()) {
			super.add(e);
		} else {
			for (int i = super.data.size(); i>idx; i--) {
				super.data.add(i,super.data.get(i+1));
			}
			super.data.add(idx,e);
		}
		return true;
	}
	*/
	
	public SortedSet (E... e) {
		super();
		this.cmp = null;
		for (E elem : e) {
			this.addItem(elem);
		}
	}
	
	public SortedSet (Collection <? extends E> c) {
		super();
		this.cmp = null;
		c.forEach(item -> addItem(item));
	}
	
	public SortedSet (final Comparator <? super E> comp, final E... e) {
		super();
		this.cmp = comp;
		for (E elem : e) {
			this.addItem(elem);
		}
	}
	
	public SortedSet (Comparator <? super E> comp, Collection <? extends E> c) {
		super();
		this.cmp = comp;
		c.forEach(item -> addItem(item));
	}
	
	public void addItem (E item) {
		ListIterator<E> itr = super.data.listIterator();
			
		if (itr.hasNext()) {
			E next;
			do {
				next = itr.next();
			} while (itr.hasNext() && (next.equals(item) || cmp.compare(item,next)>0));
				itr.previous();
			}
		itr.add(item);
	}
}
