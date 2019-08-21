package types;

import utils.Function;


/**
 * The Interface IFunctionExecutor.
 *
 * @param <T> the generic type
 */
public interface IFunctionExecutor<T> {

	/**
	 * Compute.
	 *
	 * @return the object
	 */
	Object compute();

	/**
	 * Gets the function.
	 *
	 * @return the function
	 */
	Function getFunction();

}