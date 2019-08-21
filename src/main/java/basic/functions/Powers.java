package basic.functions;

import java.util.function.BiFunction;

import basic.BasicListResultFunction;
import types.IBasicList;

/**
 * The Class Powers.
 *
 * @param <T> the generic type
 */
public class Powers<T> extends BasicListResultFunction<T> {

	/** The unity. */
	private T unity;

	/** The z. */
	private T z;

	/** The bf. */
	private BiFunction<T, T, T> bf;

	/**
	 * Instantiates a new powers.
	 *
	 * @param bf    the bf
	 * @param unity the unity
	 * @param z     the z
	 * @param list  the list
	 */
	public Powers(BiFunction<T, T, T> bf, T unity, T z, IBasicList<T> list) {
		super(list, list);
		this.unity = unity;
		this.z = z;
		this.bf = bf;
	}

	/**
	 * Compute.
	 *
	 * @return the i basic list
	 */
	public IBasicList<T> compute() {
		T x = unity;
		int n = list_result.getLen();
		for (int i = 0; i < n; i++) {
			list_result.setValue(x, i);
			// x.multiply(z);
			x = bf.apply(x, z);
		}
		return list_result;
	}

}
