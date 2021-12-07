package estDatos;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class SetNoMod<E> extends AbstractCollection<E> {
	
	protected LinkedList <E> data;
	
	@SuppressWarnings("unchecked")
	public SetNoMod (Collection <? extends E> c) {
		if (c instanceof SetNoMod) {		//Si somos lo mismo
			this.data = ((SetNoMod<E>) c).data;			//Compartimos los elementos
		} else {		//Si no
			this.data = new LinkedList<> ();		//Nuevo yo
			for (Object o : c) {		//Para cada elemento del otro
				if (! this.data.contains(o)) {		//Si es nuevo para mí
					this.data.add((E)o);		//Lo añado
				}
			}
		}
 	}
	
	@SafeVarargs
	public SetNoMod (final E... e) {
		this.data = new LinkedList<> ();
		for (E elem : e) {
			if (! this.data.contains(elem)) {
				this.data.add(elem);
			}
		}
	}
	
	@Override
	public int size() {
		return data.size();
	}

	@Override
	public Iterator<E> iterator() {
		return new SetNoModIterator();
	}
	
	private final class SetNoModIterator implements Iterator<E> {
		private Iterator<E> itr;
		
		private SetNoModIterator() {
				this.itr = data.iterator();
		}

		@Override
		public boolean hasNext() {
			return itr.hasNext();
		}

		@Override
		public E next() {
			return itr.next();
		}
	};


}
