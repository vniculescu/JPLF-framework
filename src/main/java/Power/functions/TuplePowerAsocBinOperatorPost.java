package Power.functions;

import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.math3.FieldElement;

import Power.TuplePowerResultFunction;
import types.IPowerList;

/**
 * The Class TuplePowerAsocBinOperatorPost.
 *
 * @param <T> the generic type
 */
public class TuplePowerAsocBinOperatorPost<T extends FieldElement<T>> extends TuplePowerAsocBinOperator<T> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6332034909550084569L;

	/** The right arg 3. */
	protected IPowerList<T> arg3, left_arg3, right_arg3;

	/** The post op. */
	protected BiFunction<T, T, T> post_op;

	/**
	 * Instantiates a new tuple power asoc bin operator post.
	 *
	 * @param operator the operator
	 * @param arg1     the arg 1
	 * @param arg2     the arg 2
	 * @param arg3     the arg 3
	 * @param post_op  the post op
	 * @param result   the result
	 */
	public TuplePowerAsocBinOperatorPost(List<BiFunction<T, T, T>> operator, IPowerList<T> arg1, IPowerList<T> arg2,
			IPowerList<T> arg3, BiFunction<T, T, T> post_op, List<IPowerList<T>> result) {
		super(operator, arg1, arg2, result);
		this.post_op = post_op;
		this.arg3 = arg3;
	}

	/**
	 * Basic case.
	 *
	 * @return the list
	 */
	public List<IPowerList<T>> basic_case() {
		IPowerList<T> a = power_arg.get(0);
		IPowerList<T> b = power_arg.get(1);
		IPowerList<T> c = arg3;
		T second_value = post_op.apply(b.getValue(), c.getValue());
		Object[] value = new Object[n];
		for (int i = 0; i < n; i++) {
//			IPowerList<T> res= power_result.get(i);//super.basic_case();
			value[i] = operator.get(i).apply(a.getValue(), second_value);
		}
		for (int i = 0; i < n; i++)
			power_result.get(i).setValue((T) value[i]);
		return power_result;
	}

	/**
	 * Split arg.
	 */
	public void split_arg() {
		super.split_arg();
		left_arg3 = arg3.getLeft();
		right_arg3 = arg3.getRight();
	}

	/**
	 * Creates the left function.
	 *
	 * @return the tuple power result function
	 */
	public TuplePowerResultFunction<T> create_left_function() {
		TuplePowerAsocBinOperatorPost<T> fleft = new TuplePowerAsocBinOperatorPost<T>(operator, left_power_arg.get(0),
				left_power_arg.get(1), left_arg3, post_op, left_result);
		return fleft;
	}

	/**
	 * Creates the right function.
	 *
	 * @return the tuple power result function
	 */
	public TuplePowerResultFunction<T> create_right_function() {
		TuplePowerAsocBinOperatorPost<T> fright = new TuplePowerAsocBinOperatorPost<T>(operator, right_power_arg.get(0),
				right_power_arg.get(1), right_arg3, post_op, right_result);
		return fright;
	}
}
