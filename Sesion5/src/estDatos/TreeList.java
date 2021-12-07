package estDatos;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TreeList <E> implements Tree <E> {

	private E labelRoot;
	private LinkedList <Tree<E>> children;
	
	public TreeList (E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		this.labelRoot = e;
		this.children = new LinkedList <Tree<E>> ();
	}
	
	@SafeVarargs
	public TreeList (E e, Tree<E> ...trees) {
		if (e == null || trees == null) {
			throw new NullPointerException();
		}
		this.labelRoot = e;
		this.children = new LinkedList <Tree<E>> ();
		for (Tree<E> t : trees) {
			this.children.add(new TreeList<E>(t));
		}
	}
	
	public TreeList (E e, List<Tree<E>> children) {
		if (e == null || children == null) {
			throw new NullPointerException();
		}
		this.labelRoot = e;
		this.children = new LinkedList <Tree<E>> ();
		this.children.addAll(children);
	}
	
	@SuppressWarnings("unchecked")
	public TreeList (Tree<E> t) {
		if (t == null) {
			throw new NullPointerException();
		}
		this.labelRoot = (E) new Object ();
		this.labelRoot = t.label();
		this.children = new LinkedList <Tree<E>> ();
		Iterator <Tree<E>> itr = t.iteratorChildren();
		while (itr.hasNext()) {
			this.children.add(itr.next());
		}
	}
	
	@Override
	public boolean isLeaf() {
		return this.children.isEmpty();
	}

	@Override
	public E label() {
		return this.labelRoot;
	}

	@Override
	public void setLabel (E e) {
		this.labelRoot = e;
	}
	
	@Override
	public IteratorChildren<Tree<E>> iteratorChildren() {
		return new IteratorChildrenTree();
	}
	
	private final class IteratorChildrenTree implements IteratorChildren<Tree<E>> {

		private ListIterator<Tree<E>> itr;
		
		public IteratorChildrenTree () {
			this.itr = children.listIterator();
		}

		@Override
		public boolean hasNext() {
			return itr.hasNext();
		}

		@Override
		public Tree<E> next() {
			return itr.next();
		}
		
		public void set (Tree<E> t) {
			itr.set(t);
		}
		
		public void remove () {
			itr.remove();
		}
		
	};
	
}
