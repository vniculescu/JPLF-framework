package basic;

import java.util.function.BiFunction;

import types.IBasicList;

/**
 * The Class BasicAsocBinOperator.
 *
 * @param <T> the generic type
 */
public class BasicAsocBinOperator<T> extends BasicListResultFunction<T> {

	/** The operator. */
	private final BiFunction<T, T, T> operator;

	/**
	 * Instantiates a new basic asoc bin operator.
	 *
	 * @param operator the operator
	 * @param arg1     the arg 1
	 * @param arg2     the arg 2
	 * @param result   the result
	 */
	public BasicAsocBinOperator(BiFunction<T, T, T> operator, IBasicList<T> arg1, IBasicList<T> arg2,
			IBasicList<T> result) {
		super(result, arg1, arg2);
		this.operator = operator;
	}

	/**
	 * Computes the function by applying the BiFunction operator on each elements
	 * for arg1 and arg2 and saving them to the result.
	 *
	 * @return the BasicList wich stores the result list
	 */
	public IBasicList<T> compute() {
		IBasicList<T> a = ((IBasicList<T>) list_arg.get(0));
		IBasicList<T> b = ((IBasicList<T>) list_arg.get(1));
		int n = a.getLen();
		for (int i = 0; i < n; i++) {
			T e1 = a.getValue(i);
			T e2 = b.getValue(i);
			;
			list_result.setValue(operator.apply(e1, e2), i);
		}
		return list_result;
	}
}
