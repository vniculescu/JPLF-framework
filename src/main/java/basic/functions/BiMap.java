package basic.functions;

import java.util.function.Function;

import basic.BasicListResultFunction;
import types.IBasicList;

/**
 * The Class BiMaP represents a Map function that takes as generic types two
 * type T and R, it computes the map operatopn on the list of type T and saves
 * the result into the list of type R. The operation is done sequentially.
 *
 * @param <T> the generic type
 * @param <R> the generic type
 */
public class BiMap<T, R> extends BasicListResultFunction<R> {

	/** The simple function. */
	private final Function<T, R> simpleFunction;

	/** The given list. */
	IBasicList<T> givenList;

	/**
	 * Instantiates a new bi maP.
	 *
	 * @param simpleFunction the simple function
	 * @param l              the l
	 * @param l_res          the l res
	 */
	@SuppressWarnings("unchecked")
	public BiMap(Function<T, R> simpleFunction, IBasicList<T> l, IBasicList<R> l_res) {
		super(l_res, l);
		this.simpleFunction = simpleFunction;
		givenList = (IBasicList<T>) l.getValue();
	}

	/**
	 * Compute.
	 *
	 * @return the i basic list
	 */
	public IBasicList<R> compute() {
		int n = list_result.getLen();
		for (int i = 0; i < n; i++) {
			T e = givenList.getValue(i);
			list_result.setValue(simpleFunction.apply(e), i);
		}
		return list_result;
	}
}