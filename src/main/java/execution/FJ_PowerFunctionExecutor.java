package execution;

import java.util.concurrent.ForkJoinPool;

import Power.PowerFunction;
import types.IFunctionExecutor;
import utils.Function;

/**
 * The Class FJ_PowerFunctionExecutor.Represents the executor used in
 * computations with PowerLists
 *
 * @param <T> the generic type
 */
public class FJ_PowerFunctionExecutor<T> implements IFunctionExecutor<T> {

	/** The executor. */
	private final ForkJoinPool executor;

	/** The exec. */
	private final FJ_PowerFunctionComputationTask<T> exec;

	/** The function. */
	private final PowerFunction<T> function;

	/**
	 * Instantiates a new f J power function executor.
	 *
	 * @param function        the function
	 * @param executor        the executor
	 * @param recursion_depth the recursion depth
	 */
	public FJ_PowerFunctionExecutor(PowerFunction<T> function, ForkJoinPool executor, int recursion_depth) {
		super();
		this.executor = executor;
		this.function = function;
		exec = new FJ_PowerFunctionComputationTask<T>(function, recursion_depth);
	}

	/**
	 * Instantiates a new f J power function executor.
	 *
	 * @param function        the function
	 * @param recursion_depth the recursion depth
	 */
	public FJ_PowerFunctionExecutor(PowerFunction<T> function, int recursion_depth) {
		super();
		this.executor = new ForkJoinPool(ForkJoinPool.getCommonPoolParallelism());
		this.function = function;
		exec = new FJ_PowerFunctionComputationTask<T>(function, recursion_depth);
	}

	/**
	 * Instantiates a new f J power function executor.
	 *
	 * @param function the function
	 * @param executor the executor
	 */
	public FJ_PowerFunctionExecutor(PowerFunction<T> function, ForkJoinPool executor) {
		super();
		this.executor = executor;
		this.function = function;
		exec = new FJ_PowerFunctionComputationTask<T>(function);
	}

	/**
	 * Instantiates a new f J power function executor.
	 *
	 * @param function the function
	 */
	public FJ_PowerFunctionExecutor(PowerFunction<T> function) {
		super();
		this.executor = new ForkJoinPool(ForkJoinPool.getCommonPoolParallelism());
		this.function = function;
		exec = new FJ_PowerFunctionComputationTask<T>(function);
	}

	/**
	 * Compute.
	 *
	 * @return the object
	 */
	@Override
	public Object compute() {
		return executor.invoke(exec);
	}

	/**
	 * Gets the function.
	 *
	 * @return the function
	 */
	@Override
	public Function getFunction() {
		return function;
	}

}
