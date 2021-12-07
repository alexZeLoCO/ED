package app;

import java.util.function.Consumer;
import java.util.function.Predicate;

import estDatos.IteratorChildren;
import estDatos.Tree;
import estDatos.TreeList;

public class App<E> {
	
	public static <E> void preorder(Tree<E> tree,
			Consumer<? super Tree<E>> block, Predicate<? super Tree<E>> p) {
		IteratorChildren<Tree<E>> itr = tree.iteratorChildren();

		// tratar la raíz
		if (p.test(tree)) {
			block.accept(tree);
		}		
		
		// los subárboles hijos de la raíz en preorden
		while (itr.hasNext()) {
			preorder(itr.next(), block, p);
		}
	}
	
	public static <E> void replace (Tree<E> t, E label, E change) {
		preorder(t, tree -> tree.setLabel(change), tree -> tree.label().equals(label));
	}
	
	public static <E> void showAll (Tree <E> t) {
		System.out.print("Preorder:\t");
		Tree.preorder(t,  System.out::print , ch -> true);
		System.out.println();
		System.out.print("Inorder:\t");
		Tree.inorder(t,  System.out::print, ch -> true);
		System.out.println();
		System.out.print("Postorder:\t");
		Tree.postorder(t,  System.out::print, ch -> true);
		System.out.println();
		System.out.print("Level Order:\t");
		Tree.levelorder(t,  System.out::print, ch -> true);
		System.out.println();
		System.out.print("Preorder Main:\t");
		preorder (t, tree -> System.out.print(tree.label()), ch -> true);
	}
	
	public static void main(String[] args) {
		TreeList<Character> tf = new TreeList<>('f');
		TreeList<Character> tg = new TreeList<>('g');
		TreeList<Character> th = new TreeList<>('h');
		TreeList<Character> ti = new TreeList<>('i');
		TreeList<Character> tj = new TreeList<>('j');
		TreeList<Character> tk = new TreeList<>('k');
		TreeList<Character> tl = new TreeList<>('l');
		TreeList<Character> tm = new TreeList<>('m');
		TreeList<Character> tn = new TreeList<>('n');
		TreeList<Character> te = new TreeList<>('e', tf, tg, th);
		TreeList<Character> td = new TreeList<>('d', ti, tj);
		TreeList<Character> tc = new TreeList<>('c', te, tk, tl);
		TreeList<Character> tb = new TreeList<>('b', td, tm, tn);
		TreeList<Character> ta = new TreeList<>('a', tb, tc);
			
		//showAll(ta);
		Tree.preorder(ta, System.out::print, ch -> true);
		System.out.println("\n\nCambiando a por O:");
		replace (ta, 'a', 'O');
		Tree.preorder(ta, System.out::print, ch -> true);
		//showAll(ta);
		
	}
	
}
