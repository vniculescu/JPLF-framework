package basic.functions;

import java.util.function.BiFunction;

import basic.BasicListFunction;
import types.IBasicList;

/**
 * The Class Reduce. Performs a sequential reduction of the elements of the
 * input list based on the BiFunction given as parameter
 *
 * @param <T> the generic type
 */
public class Reduce<T> extends BasicListFunction<T> {

	/** The op. */
	protected BiFunction<T, T, T> op;

	/**
	 * Instantiates a new reduce.
	 *
	 * @param operatorLambda the operator lambda
	 * @param list_arg       the list arg
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public Reduce(java.util.function.BiFunction<T, T, T> operatorLambda, IBasicList<T> list_arg)
			throws IllegalArgumentException {
		super(list_arg);
		this.op = operatorLambda;
	}

	/**
	 * Compute.
	 *
	 * @return the object
	 */
	public Object compute() {
		IBasicList<T> list = list_arg.get(0);
		int i = 0;
		T res = list.getValue(0);
		i++;
		int n = list.getLen();
		for (; i < n; i++) {
			T right = (T) list.getValue(i);
			res = this.op.apply(res, right);
		}

		result = res;
		return res;
	}
}
