package Power.functions;

import java.util.function.BiFunction;

import Power.PowerFunction;
import Power.PowerList;
import types.IPowerList;


/**
 * The Class Reduce.
 *
 * @param <T> the generic type
 */
public class Reduce<T> extends PowerFunction<T> {
	
	/** The op. */
	protected BiFunction<T, T, T> op;
	
	/** The left result. */
	protected T right_result, left_result;

	/**
	 * Instantiates a new reduce.
	 */
	public Reduce() {
	}

	/**
	 * Instantiates a new reduce.
	 *
	 * @param op the op
	 * @param power_arg the power arg
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public Reduce(BiFunction<T, T, T> op, IPowerList<T> power_arg) throws IllegalArgumentException {
		super(power_arg);
		this.op = op;
	}

	/**
	 * Combine.
	 *
	 * @param left the left
	 * @param right the right
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T combine(Object left, Object right) {
		return op.apply((T) left, (T) right);
	}

	/**
	 * Basic case.
	 *
	 * @return the object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object basic_case() {
		PowerList s = (PowerList) super.basic_case();
		if (s.getValue() instanceof types.IBasicList) {
			types.IBasicList<T> list = (types.IBasicList<T>) s.getValue();
			basic.functions.Reduce<T> rf = new basic.functions.Reduce<T>(op, list);
			Object result = rf.compute();
			s.setValue(result);
		}
		return s.getValue();
	}

	/**
	 * Creates the left function.
	 *
	 * @return the reduce
	 */
	@Override
	public Reduce<T> create_left_function() {
		return new Reduce<T>(op, left_power_arg.get(0));
	}

	/**
	 * Creates the right function.
	 *
	 * @return the reduce
	 */
	@Override
	public Reduce<T> create_right_function() {
		return new Reduce<T>(op, right_power_arg.get(0));
	}

}
