package estDatos;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


public class PriorityQueueList <E> extends AbstractQueue <E> {

	private LinkedList <E> data;
	private Comparator <? super E> cmp;
	
	public PriorityQueueList () {
		data = new LinkedList();
		this.cmp = null;
	}
	
	public PriorityQueueList (final Comparator <? super E> cmp) {
		this.cmp = cmp;
		this.data = new LinkedList();
	}
	
	public PriorityQueueList (Collection <? extends E> c) {
		data = new LinkedList();
		this.cmp = null;
		data.addAll(c);
	}
	
	public PriorityQueueList (final Comparator <? super E> cmp, Collection <? extends E> c) {
		this.cmp = cmp;
		data = new LinkedList();
		this.cmp = cmp;
		data.addAll(c);
	}
		
	private final class iterador implements Iterator<E> {

		private int idx;

		public iterador () {
			this.idx = 0;
		}
		
		@Override
		public boolean hasNext() {
			return idx < data.size();
		}

		@Override
		public E next() {
			return data.get(idx++);
		}
		
		@Override
		public void remove () {		}
	};

	
	private int micompare(final E a, final E b) {
		if (this.cmp == null && !(a instanceof Comparable<?>))  {
			throw new ClassCastException (String.format("El tipo %s no es comparable", a.getClass().getName()));
		}
		return cmp == null ? ((Comparable<E>) a).compareTo(b) : this.cmp.compare(a, b);
	}
	
	@Override
	public boolean offer(E e) {
		/*
		 * Opción 2
		Iterator<E> itr = iterator();
		
		int idx = 0;
		while (itr.hasNext() && micompare(itr.next(),e) < 0) {
				idx++;
			}
		data.add(idx, e);
		
		*/
		
		//Testing - WIP
		ListIterator<E> itr = data.listIterator();
		
		if (itr.hasNext()) {
			E next;
			do {
				next = itr.next();
			} while (itr.hasNext() && micompare(next,e)<0);
				itr.previous();
			}
		itr.add(e);
		
		return true;
	}

	@Override
	public E poll() {
		if (this.isEmpty()) {
			return null;
		}
		return data.removeFirst();
	}

	@Override
	public E peek() {
		if (this.isEmpty()) {
			return null;
		}
		return this.data.get(0);
	}

	@Override
	public Iterator<E> iterator() {
		return new iterador();
	}
	
	@Override
	public int size() {
		return data.size();
	}

}
