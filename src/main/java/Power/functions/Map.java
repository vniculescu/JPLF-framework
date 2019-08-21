package Power.functions;

import java.util.function.Function;

import Power.PowerResultFunction;
import types.IPowerList;


/**
 * The Class MaP.
 *
 * @param <T> the generic type
 */
public class Map<T> extends PowerResultFunction<T> {
	
	/** The op. */
	private Function<T, T> op;

	/**
	 * Instantiates a new maP.
	 *
	 * @param op the op
	 * @param arg the arg
	 */
	public Map(Function<T, T> op, IPowerList<T> arg) {
		super(arg, arg); // power_result is identical to the power list argument
		this.op = op;
	}

	/**
	 * Instantiates a new maP.
	 *
	 * @param op the op
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 */
	public Map(Function<T, T> op, IPowerList<T> arg1, IPowerList<T> arg2) {
		super(arg2, arg1); // power_result=arg2
		this.op = op;
	}

	/**
	 * Basic case.
	 *
	 * @return the i power list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IPowerList<T> basic_case() {
		IPowerList s = power_result;
		IPowerList a = power_arg.get(0);
		if (s.getValue() instanceof types.IBasicList) {
			types.IBasicList list = (types.IBasicList) a.getValue();
			basic.functions.Map mf = new basic.functions.Map(op, list);
			list = mf.compute();
			s.setValue(list);
		} else {
			s.setValue(op.apply(power_arg.get(0).getValue()	));
		}
		return power_result; // power_result is identical to list argument
	}

	/**
	 * Creates the left function.
	 *
	 * @return the power result function
	 */
	@Override
	public PowerResultFunction<T> create_left_function() {
		return new Map<T>(op, left_power_arg.get(0), left_result);
	}

	/**
	 * Creates the right function.
	 *
	 * @return the power result function
	 */
	@Override
	public PowerResultFunction<T> create_right_function() {

		return new Map<T>(op, right_power_arg.get(0), right_result);
	}

}
