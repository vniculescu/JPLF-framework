package basic.functions;

import org.apache.commons.math3.FieldElement;

import utils.Function;

/**
 * The Class AddTo.
 *
 * @param <T> the generic type
 */
public class AddTo<T extends FieldElement<T>> extends Function implements java.util.function.Function<T, T> {

	/** The a. */
	private T arg, a;

	/** The result. */
	private T result;

	/**
	 * Instantiates a new adds the to.
	 *
	 * @param a   the a
	 * @param arg the arg
	 */
	public AddTo(T a, T arg) {
		this.a = a;
		this.arg = arg;
		this.result = null;
		setArg(arg);
	}

	/**
	 * Compute.
	 *
	 * @return the object
	 */
	public Object compute() {
		result = a.add(arg);
		return result;
	}

	/**
	 * Sets the arg.
	 *
	 * @param arg the new arg
	 */
	public void setArg(Object... arg) {
		this.arg = (T) arg[0];
		if (arg.length > 1)
			this.a = (T) arg[1];
	}

	/**
	 * Copy the function.
	 *
	 * @return the function
	 */
	public Function copy() {
		return new AddTo<T>(a, arg);
	}

	/**
	 * Apply the operator.
	 *
	 * @param t the t
	 * @return the t
	 */
	@Override
	public T apply(T t) {
		return a.add(arg);
	}

}
