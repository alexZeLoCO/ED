package estDatos;

import java.util.Comparator;

public class ComparatorString implements Comparator <String> {
	
	public int compare (String o1, String o2) {
		//return o2.compareTo(o1);
		return o1.length()-o2.length();
	}
}
