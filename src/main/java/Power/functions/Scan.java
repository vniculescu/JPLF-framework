package Power.functions;

import java.util.function.BiFunction;

import Power.PowerList;
import Power.PowerResultFunction;
import types.IPowerList;

/**
 * The Class Scan.
 *
 * @param <T> the generic type
 */
public class Scan<T> extends PowerResultFunction<T> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5803484532938073456L;

	/** The op. */
	private BiFunction<T, T, T> op;

	/**
	 * Instantiates a new scan.
	 *
	 * @param op  the op
	 * @param arg the arg
	 */
	public Scan(BiFunction<T, T, T> op, IPowerList<T> arg) {
		super(arg, arg); // power_result is identical to the power list argument
		this.op = op;
	}

	/**
	 * Instantiates a new scan.
	 *
	 * @param op   the op
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 */
	public Scan(BiFunction<T, T, T> op, IPowerList<T> arg1, IPowerList<T> arg2) {
		// arg2 = result_list, arg1 = arg_list
		super(arg2, arg1);
		this.op = op;
	}

	/**
	 * Basic case.
	 *
	 * @return the i power list
	 */
	@Override
	public IPowerList<T> basic_case() {
		IPowerList s = power_result;
		IPowerList a = power_arg.get(0);
//			System.out.println("in basic case arg="+a +"result="+power_result);
		if (power_result.getValue() instanceof types.IBasicList) {
			types.IBasicList<T> list = (types.IBasicList<T>) a.getValue();
			basic.functions.Scan<T> rf = new basic.functions.Scan<T>(op, list, list);
			Object result = rf.compute();
			s.setValue(result);
		} else {
			s.setValue(a.getValue());
		}
//				System.out.println("basic_case->"+s);
		return s;
	}

	/**
	 * Combine.
	 *
	 * @param left  the left
	 * @param right the right
	 * @return the i power list
	 */
	@Override
	public IPowerList<T> combine(Object left, Object right) {

		PowerList<T> power_left = (PowerList<T>) left;
		PowerList<T> power_right = (PowerList<T>) right;
		T last = power_left.getValue(power_right.getLen() - 1);
		for (int i = 0; i < power_right.getLen(); i++) {
			power_right.setValue( op.apply(power_right.getValue(i), last), i);
		}
		power_result.setFrom2Lists(power_left, power_right);
		return power_result;
	}

	/**
	 * Creates the left function.
	 *
	 * @return the power result function
	 */
	@Override
	public PowerResultFunction<T> create_left_function() {
		return new Scan<T>(op, left_power_arg.get(0), left_result);
	}

	/**
	 * Creates the right function.
	 *
	 * @return the power result function
	 */
	@Override
	public PowerResultFunction<T> create_right_function() {
		return new Scan<T>(op, right_power_arg.get(0), right_result);
	}

}
