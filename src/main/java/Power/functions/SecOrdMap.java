package Power.functions;

import java.util.function.Function;

import Power.PowerResultFunction;
import types.IPList;
import types.IPowerList;


/**
 * The Class SecOrdMaP.
 *
 * @param <T> the generic type
 */
public class SecOrdMap<T> extends PowerResultFunction<IPList<T>> {
	
	/** The op. */
	private Function<T, T> op;

	/**
	 * Instantiates a new sec ord maP.
	 *
	 * @param op the op
	 * @param arg the arg
	 */
	public SecOrdMap(Function<T, T> op, IPowerList<IPList<T>> arg) {
		super(arg, arg);
		this.op = op;
	}

	/**
	 * Basic case.
	 *
	 * @return the i power list
	 */
	@Override
	public IPowerList<IPList<T>> basic_case() {
		IPowerList<IPList<T>> s = super.basic_case();
		P.functions.Map<T> mf = new P.functions.Map<T>(op, s.getValue());
		IPList<T> result = mf.compute();
		s.setValue(result);
		return s;
	}

	/**
	 * Creates the left function.
	 *
	 * @return the power result function
	 */
	@Override
	public PowerResultFunction<IPList<T>> create_left_function() {
		return new SecOrdMap<T>(op, left_result);
	}

	/**
	 * Creates the right function.
	 *
	 * @return the power result function
	 */
	@Override
	public PowerResultFunction<IPList<T>> create_right_function() {
		return new SecOrdMap<T>(op, right_result);
	}

}
