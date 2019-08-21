package mpi_ct;

import java.util.concurrent.ForkJoinPool;

import Power.PowerResultFunction;
import execution.FJ_PowerFunctionExecutor;


/**
 * The Class MPI_PowerResultFunctionCT.
 *
 * @author virginia
 * 
 * This assure that the function to be computed is a PowerResultFunction
 * @param <T> the generic type
 */
public class MPI_PowerResultFunctionCT<T> extends MPI_PowerFunctionCT<T>{
	
	/** The function. */
	/*
	 * this is another reference <> from the inherited one
	 * that its created in order avoid type casting
	 * from PowerFunction to PowerResultFunction
	 * ech time
	 */
	protected PowerResultFunction<T> function;
	
	/**
	 * Instantiates a new MP I power result function CT.
	 *
	 * @param function the function
	 * @param recursion_depth the recursion depth
	 */
	public MPI_PowerResultFunctionCT(PowerResultFunction<T> function, int recursion_depth) {
		super(function, recursion_depth);
		this.function = function;
		executor = 	new FJ_PowerFunctionExecutor<T>(function, recursion_depth);
	}
	
	/**
	 * Instantiates a new MP I power result function CT.
	 *
	 * @param function the function
	 * @param exec the exec
	 * @param recursion_depth the recursion depth
	 */
	public MPI_PowerResultFunctionCT(PowerResultFunction<T> function, ForkJoinPool exec, int recursion_depth) {
		super(function, recursion_depth);
		this.function =  function;
		executor = 	new FJ_PowerFunctionExecutor<T>(function, exec, recursion_depth);
	}
	
	/**
	 * Instantiates a new MP I power result function CT.
	 *
	 * @param function the function
	 */
	public MPI_PowerResultFunctionCT(PowerResultFunction<T> function) {
		super(function);
		this.function = function;
		executor = 	new FJ_PowerFunctionExecutor<T>(function);
	}
	
	/**
	 * Instantiates a new MP I power result function CT.
	 *
	 * @param function the function
	 * @param exec the exec
	 */
	public MPI_PowerResultFunctionCT(PowerResultFunction<T> function, ForkJoinPool exec) {
		super(function);
		this.function =  function;
		executor = 	new FJ_PowerFunctionExecutor<T>(function, exec);
	}
}
