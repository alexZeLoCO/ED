package estDatos;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Tree<E> {

	/**
	 * Retorna cierto si la raíz de este árbol es una hoja
	 * 
	 * @return {@code true} si la raíz de este árbol es una hoja
	 */
	boolean isLeaf();

	/**
	 * Retorna la etiqueta de la raíz de este árbol.
	 * 
	 * @return la raíz del árbol
	 */
	E label();
	
	/**
	 * Iterador de los subárboles hijos de la raíz de este árbol.
	 * 
	 * @return un iterador de los nodos hijos de la raíz de
	 * este árbol
	 */
	IteratorChildren<Tree<E>> iteratorChildren();

	/**
	 * Cambia la etiqueta de la raíz de este árbol al objeto
	 * especificado (operación opcional).
	 * 
	 * @param e la nueva etiqueta de la raíz
	 * @throws UnsupportedOperationException si la operación
	 * no está soportada por este árbol.
	 * @throws NullPointerException si este árbol no admite etiquetas
	 * de valor {@code null}
	 */
	default void setLabel(E e) {
		throw new UnsupportedOperationException();
	}
	
	static <E> void preorder(Tree<E> tree,
			Consumer<? super E> block, Predicate<? super E> p) {
		if (p.test(tree.label())) {
			block.accept(tree.label());
		}
		
		IteratorChildren<Tree<E>> itr = tree.iteratorChildren();
		while (itr.hasNext()) {
			preorder(itr.next(), block, p);
		}
	}

	static <E> void postorder(Tree<E> tree,
			Consumer<? super E> block, Predicate<? super E> p) {
		IteratorChildren<Tree<E>> itr = tree.iteratorChildren();
		
		while (itr.hasNext()) {
			postorder(itr.next(), block, p);
		}

		if (p.test(tree.label())) {
			block.accept(tree.label());
		}		
	}

	static <E> void inorder(Tree<E> tree,
			Consumer<? super E> block, Predicate<? super E> p) {
		IteratorChildren<Tree<E>> itr = tree.iteratorChildren();
		
		if (itr.hasNext()) { // inorden del primer hijo
			inorder(itr.next(), block, p);
		}
		
		if (p.test(tree.label())) {
			block.accept(tree.label());
		}		

		while (itr.hasNext()) {
			inorder(itr.next(), block, p);
		}
	}
	
	static <E> void levelorder(Tree<E> tree,
			Consumer<? super E> block, Predicate<? super E> p) {
		LinkedList<Tree<E>> queue = new LinkedList<>();
		
		// añadir el árbol
		queue.add(tree);
		
		while (!queue.isEmpty()) {
			Tree<E> current = queue.remove();
			
			if (p.test(current.label())) {
				block.accept(current.label());
			}
			
			IteratorChildren<Tree<E>> itr = current.iteratorChildren();
			while (itr.hasNext()) {
				queue.add(itr.next());
			}
		}		
	}
	
	static <E> List<E> camino(Tree<E> t, E e) {
		List<E> l = new LinkedList<>();
		IteratorChildren<Tree<E>> itr = t.iteratorChildren();
			
		while (itr.hasNext() && l.isEmpty()) {
			l = camino(itr.next(), e);
		}
			
		if (l.isEmpty()) { // ¿ t.label() = e ?
			if (t.label().equals(e)) {
				l.add(e);
			}
		} else { // añadir la etiqueta de la raíz al principio
				l.add(0, t.label());
		}

		return l;
	}
	
}
