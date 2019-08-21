package execution;

import java.util.concurrent.RecursiveTask;

import Power.PowerFunction;

/**
 * The Class FJ_PowerFunctionComputationTask. Represents recursive tasks that
 * are created for PowerLists executors
 *
 * @param <T> the generic type
 */
public class FJ_PowerFunctionComputationTask<T> extends RecursiveTask<Object> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The function. */
	private final PowerFunction<T> function;

	/** The recursion depth. */
	private final int recursion_depth;

	/**
	 * Instantiates a new f J power function computation task.
	 *
	 * @param function        the function
	 * @param recursion_depth the recursion depth
	 */
	public FJ_PowerFunctionComputationTask(PowerFunction<T> function, int recursion_depth) {
		this.function = function;
		this.recursion_depth = recursion_depth;
	}

	/**
	 * Instantiates a new f J power function computation task.
	 *
	 * @param function the function
	 */
	public FJ_PowerFunctionComputationTask(PowerFunction<T> function) {
		this.function = function;
		this.recursion_depth = function.getPowerArg().get(0).loglen();
	}

	/**
	 * Compute.
	 *
	 * @return the object
	 */
	@Override
	public Object compute() {
		Object result = null;
		if (function.test_basic_case()) {
			result = function.basic_case();
		} else {
			function.split_arg();

			PowerFunction<T> left_function = function.create_left_function();
			PowerFunction<T> right_function = function.create_right_function();
			// wrap the functions into executor

			if (recursion_depth == 0) {
//				System.out.println("rec="+recursion_depth +"  ");
				result = function.compute();
			} else {
				FJ_PowerFunctionComputationTask<T> left_function_exec = new FJ_PowerFunctionComputationTask<T>(
						left_function, recursion_depth - 1);

				FJ_PowerFunctionComputationTask<T> right_function_exec = new FJ_PowerFunctionComputationTask<T>(
						right_function, recursion_depth - 1);

				right_function_exec.fork();
				Object result_left = left_function_exec.compute();

				Object result_right = right_function_exec.join();

				result = function.combine(result_left, result_right);
			}
		}

		return result;
	}
}
