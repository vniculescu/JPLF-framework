package Power.functions;

import java.util.function.BiFunction;
import java.util.function.Function;

import Power.PowerResultFunction;
import types.IPowerList;


/**
 * The Class Powers.
 *
 * @param <T> the generic type
 */
public class Powers<T> extends PowerResultFunction<T> {
	
	/** The unity. */
	private T z, unity;
	
	/** The bf. */
	private BiFunction<T, T, T> bf;
	
	/** The f. */
	private Function<T, T> f;

	/**
	 * Instantiates a new powers.
	 *
	 * @param bf the bf
	 * @param f the f
	 * @param unity the unity
	 * @param z the z
	 * @param arg the arg
	 * @param result the result
	 */
	public Powers(BiFunction<T, T, T> bf, Function<T, T> f, T unity, T z, IPowerList arg, IPowerList result) {
		super(result, arg); // power_result is identical to list argument
		this.z = z;
		this.unity = unity;
		this.bf = bf;
		this.f = f;
	}

	/**
	 * Basic case.
	 *
	 * @return the i power list
	 */
	@Override
	public IPowerList<T> basic_case() {
		IPowerList s = power_result;
		if (s.getValue() instanceof types.IBasicList) {
			basic.functions.Powers pf = new basic.functions.Powers(bf, unity, z, (types.IBasicList<T>) s.getValue());
			pf.compute();
		} else {
			s.setValue(unity);
		}
		return power_result; // power_result is identical to list argument
	}

	/**
	 * Combine.
	 *
	 * @param first the first
	 * @param second the second
	 * @return the i power list
	 */
	@Override
	public IPowerList<T> combine(Object first, Object second) {
		Map<T> mf = new Map(f, right_result, right_result);
		mf.compute();
		// power_result.setFrom2Lists((IPowerList<T>)left_result,
		// (IPowerList<T>)right_result);
		return power_result;
	}

	/**
	 * Creates the left function.
	 *
	 * @return the power result function
	 */
	@Override
	public PowerResultFunction<T> create_left_function() {
		T x = bf.apply(z, z);

		return new Powers<T>(bf, f, unity, x, left_power_arg.get(0), left_result);
	}

	/**
	 * Creates the right function.
	 *
	 * @return the power result function
	 */
	@Override
	public PowerResultFunction<T> create_right_function() {
		T x = bf.apply(z, z);
		return new Powers<T>(bf, f, unity, x, right_power_arg.get(0), right_result);
	}

}
