package execution;

import java.util.concurrent.ForkJoinPool;

import P.PFunction;
import types.IFunctionExecutor;
import utils.Function;

/**
 * The Class FJ_PFunctionExecutor. Represents the executor used in computations
 * with PLists
 *
 * @param <T> the generic type
 */
public class FJ_PFunctionExecutor<T> implements IFunctionExecutor<T> {

	/** The executor. */
	private final ForkJoinPool executor;

	/** The main computation task. */
	private final FJ_PFunctionComputationTask<T> exec;

	/** The function. */
	private final PFunction<T> function;

	/**
	 * Instantiates a new f J P function executor.
	 *
	 * @param function        the function
	 * @param executor        the executor
	 * @param recursion_depth the recursion depth
	 */
	public FJ_PFunctionExecutor(PFunction<T> function, ForkJoinPool executor, int recursion_depth) {
		super();
		this.executor = executor;
		this.function = function;
		this.exec = new FJ_PFunctionComputationTask<T>(function, recursion_depth);
	}

	/**
	 * Instantiates a new f J P function executor.
	 *
	 * @param function        the function
	 * @param recursion_depth the recursion depth
	 */
	public FJ_PFunctionExecutor(PFunction<T> function, int recursion_depth) {
		super();
		this.executor = new ForkJoinPool(ForkJoinPool.getCommonPoolParallelism());
		this.function = function;
		this.exec = new FJ_PFunctionComputationTask<T>(function, recursion_depth);
	}

	/**
	 * Instantiates a new f J P function executor.
	 *
	 * @param function the function
	 */
	public FJ_PFunctionExecutor(PFunction<T> function) {
		super();
		this.executor = new ForkJoinPool(ForkJoinPool.getCommonPoolParallelism());
		this.function = function;
		this.exec = new FJ_PFunctionComputationTask<T>(function, function.getPArg().get(0).getArityList().size());
	}

	/**
	 * Compute.
	 *
	 * @return the object
	 */
	@Override
	public Object compute() {
		return this.executor.invoke(this.exec);
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
