package basic.functions;

import java.util.function.Function;

import basic.BasicListResultFunction;
import types.IBasicList;

/**
 * The Class MaP. Map function that applies a Function sequentially to each
 * elements of the input list.
 *
 * @param <T> the generic type
 */
public class Map<T> extends BasicListResultFunction<T> {

	/** The simple function. */
	private final Function<T, T> simpleFunction;

	/**
	 * Instantiates a new maP.
	 *
	 * @param simpleFunction the simple function
	 * @param l              the l
	 */
	public Map(Function<T, T> simpleFunction, IBasicList<T> l) {
		super(l, l);
		this.simpleFunction = simpleFunction;
		list_result = list_arg.get(0);
	}

	/**
	 * Instantiates a new maP.
	 *
	 * @param simpleFunction the simple function
	 * @param l              the l
	 * @param l_res          the l res
	 */
	public Map(Function<T, T> simpleFunction, IBasicList<T> l, IBasicList<T> l_res) {
		super(l_res, l);
		this.simpleFunction = simpleFunction;
	}

	/**
	 * Compute.
	 *
	 * @return the i basic list
	 */
	public IBasicList<T> compute() {
		int n = list_result.getLen();
		for (int i = 0; i < n; i++) {
			T e = list_result.getValue(i);
			e = simpleFunction.apply(e);
			list_result.setValue(e, i);
		}
		return list_result;
	}
}
