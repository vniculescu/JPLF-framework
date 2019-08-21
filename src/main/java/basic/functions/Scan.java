package basic.functions;

import java.util.function.BiFunction;

import basic.BasicList;
import basic.BasicListResultFunction;
import types.IBasicList;

/**
 * The Class Scan.
 *
 * @param <T> the generic type
 */
public class Scan<T> extends BasicListResultFunction<T> {

	/** The operator. */
	private final BiFunction<T, T, T> operator;

	/**
	 * Instantiates a new scan.
	 *
	 * @param operator the operator
	 * @param l        the l
	 * @param res      the res
	 */
	public Scan(BiFunction<T, T, T> operator, IBasicList<T> l, IBasicList<T> res) {
		super(res, l);
		this.operator = operator;
	}

	/**
	 * Compute.
	 *
	 * @return the i basic list
	 */
	public IBasicList<T> compute() {
		// sequential non-recursive computation
		BasicList.LIterator<T> it = (BasicList.LIterator<T>) list_arg.get(0).iterator();
		BasicList.LIterator<T> it2 = (BasicList.LIterator<T>) list_result.iterator();
		T e_prev = it.next(), e = e_prev;
		it2.setCurrent(e);
		it2.next();
		while (it.hasNext()) {
			T e_current = it.next();
			e = operator.apply(e_prev, e_current);
			it2.setCurrent(e);
			it2.next();
			e_prev = e;
		}
		return list_result;
	}

}
