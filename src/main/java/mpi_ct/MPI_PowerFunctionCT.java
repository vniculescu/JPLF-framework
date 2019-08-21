package mpi_ct;
import java.util.concurrent.ForkJoinPool;

import Power.PowerFunction;
import execution.FJ_PowerFunctionExecutor;


/**
 * The Class MPI_PowerFunctionCT.
 *
 * @param <T> the generic type
 */
public class MPI_PowerFunctionCT<T> extends MPI_CTOperations<T> {

	/** The executor. */
	protected FJ_PowerFunctionExecutor<T> executor;

	/** The recursion depth. */
	protected int recursion_depth;
	
	/**
	 * Instantiates a new MP I power function CT.
	 *
	 * @param function the function
	 * @param recursion_depth the recursion depth
	 */
	public MPI_PowerFunctionCT(PowerFunction<T> function, int recursion_depth) {
		super(function);
		executor = 	new FJ_PowerFunctionExecutor<T>(function, recursion_depth);
		this.recursion_depth = recursion_depth;
	}
	
	/**
	 * Instantiates a new MP I power function CT.
	 *
	 * @param function the function
	 */
	public MPI_PowerFunctionCT(PowerFunction<T> function) {
		super(function);
		executor = 	new FJ_PowerFunctionExecutor<T>(function);
	}
	
	/**
	 * Instantiates a new MP I power function CT.
	 *
	 * @param function the function
	 * @param exec the exec
	 * @param recursion_depth the recursion depth
	 */
	public MPI_PowerFunctionCT(PowerFunction<T> function, ForkJoinPool exec, int recursion_depth) {
		super(function);
		executor = 	new FJ_PowerFunctionExecutor<T>(function, exec, recursion_depth);
		this.recursion_depth = recursion_depth;
	}
	
	/**
	 * Instantiates a new MP I power function CT.
	 *
	 * @param function the function
	 * @param exec the exec
	 */
	public MPI_PowerFunctionCT(PowerFunction<T> function, ForkJoinPool exec) {
		super(function);
		executor = 	new FJ_PowerFunctionExecutor<T>(function, exec);
	}
	
	/**
	 * Sets the power function.
	 *
	 * @param function the new power function
	 */
	public void setPowerFunction(PowerFunction<T> function) {
		this.function = (PowerFunction<T>)function;
		executor = 	new FJ_PowerFunctionExecutor<T>(function);
	}

	/**
	 * Leaf.
	 *
	 * @return the object
	 */
	public Object leaf(){
		return executor.compute();
	}


}