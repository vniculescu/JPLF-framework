package basic.functions;

import java.util.function.BinaryOperator;

import basic.BasicListFunction;
import types.IBasicList;

/**
 * The Class L_Reduce.
 *
 * @param <T> the generic type
 */
public class L_Reduce<T>
		/*
		 * reduce function on a basic list - sequential, non-recursive
		 */
		extends BasicListFunction<T> {

	/** The op. */
	protected BinaryOperator<T> op;

	/**
	 * Instantiates a new l reduce.
	 *
	 * @param op       the op
	 * @param list_arg the list arg
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public L_Reduce(BinaryOperator<T> op, IBasicList<T> list_arg) throws IllegalArgumentException {
		super(list_arg);
		this.op = op;
	}

	/**
	 * Compute.
	 *
	 * @return the object
	 */
	public Object compute() {
		IBasicList<T> list = list_arg.get(0);

		int i = 0;
		int n = list.getLen();
		result = list.getValue(i);
		i++;
		for (; i < n; i++) {
			result = op.apply((T) result, list.getValue(i));
		}
		return result;
	}
}
