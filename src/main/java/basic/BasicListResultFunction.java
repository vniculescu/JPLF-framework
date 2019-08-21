package basic;

import types.IBasicList;

/**
 * The Class BasicListResultFunction.
 *
 * @param <T> the generic type
 */
public class BasicListResultFunction<T> extends BasicListFunction<T> {

	/** The list result. */
	protected IBasicList<T> list_result;

	/**
	 * Instantiates a new basic list result function.
	 *
	 * @param list_result the list result
	 * @param args        the args
	 */
	public BasicListResultFunction(IBasicList<T> list_result, Object... args) {
		super(args);
		this.list_result = list_result;
	}

	/**
	 * Instantiates a new basic list result function.
	 *
	 * @param a the a
	 */
	public BasicListResultFunction(IBasicList<T> a) {
		super(a);
	}

	/**
	 * Compute.
	 *
	 * @return the i basic list
	 */
	public IBasicList<T> compute() {
		return list_result;
	}

}
