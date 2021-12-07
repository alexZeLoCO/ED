package estDatos;

import java.util.Comparator;

public class ComparatorIntegers implements Comparator<Integer> {
	public int compare (Integer i1, Integer i2) {
		return i1-i2;
	}

}
