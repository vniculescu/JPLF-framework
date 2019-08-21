package execution;

import java.util.concurrent.ForkJoinPool;

import Power.PowerFunction;
import Power.PowerResultFunction;
import mpi_ct.I_MPI_CTOperations;
import mpi_ct.MPI_PowerCT_compose;
import mpi_ct.MPI_PowerCT_read;
import mpi_ct.MPI_PowerFunctionCT;
import mpi_ct.MPI_PowerResultCT_write;
import mpi_ct.MPI_PowerResultFunctionCT;
import types.IFunctionExecutor;
import utils.Function;

/**
 * The Class MPI_PowerFunctionExecutor.
 *
 * @param <T> the generic type
 */
public class MPI_PowerFunctionExecutor<T> implements IFunctionExecutor<T> {

	/** The ct. */
	// MPI computational tasks builder
	protected I_MPI_CTOperations<T> ct;

	/** The function. */
	protected PowerFunction<T> function;

	/**
	 * Instantiates a new MP I power function executor.
	 *
	 * @param function        the function
	 * @param recursion_depth the recursion depth
	 */
	public MPI_PowerFunctionExecutor(PowerFunction<T> function, int recursion_depth) {
		ct = new MPI_PowerFunctionCT<T>(function, recursion_depth);
	}

	/**
	 * Instantiates a new MP I power function executor.
	 *
	 * @param function the function
	 */
	public MPI_PowerFunctionExecutor(PowerResultFunction<T> function) {
		ct = new MPI_PowerResultFunctionCT<T>(function);
	}

	/**
	 * Instantiates a new MP I power function executor.
	 *
	 * @param function        the function
	 * @param executor        the executor
	 * @param recursion_depth the recursion depth
	 */
	public MPI_PowerFunctionExecutor(PowerFunction<T> function, ForkJoinPool executor, int recursion_depth) {
		ct = new MPI_PowerFunctionCT<T>(function, executor, recursion_depth);
	}

	/**
	 * Instantiates a new MP I power function executor.
	 *
	 * @param function the function
	 * @param executor the executor
	 */
	public MPI_PowerFunctionExecutor(PowerResultFunction<T> function, ForkJoinPool executor) {
		ct = new MPI_PowerResultFunctionCT<T>(function, executor);
	}

	/**
	 * Adds the CT.
	 *
	 * @param decorator  the decorator
	 * @param files      the files
	 * @param sizes      the sizes
	 * @param elem_sizes the elem sizes
	 * @return the i MP I CT operations
	 */
	public I_MPI_CTOperations<T> add_CT(int decorator, String[] files, int[] sizes, int[] elem_sizes) {
		if (decorator == I_MPI_CTOperations.READ)
			ct = new MPI_PowerCT_read<T>(ct, files, sizes, elem_sizes);
		return ct;
	}

	/**
	 * Adds the CT.
	 *
	 * @param decorator the decorator
	 * @return the i MP I CT operations
	 */
	public I_MPI_CTOperations<T> add_CT(int decorator) {
		if (decorator == I_MPI_CTOperations.COMPOSE)
			ct = new MPI_PowerCT_compose<T>(ct);
		return ct;
	}

	/**
	 * Adds the CT.
	 *
	 * @param decorator        the decorator
	 * @param output_file_name the output file name
	 * @return the i MP I CT operations
	 */
	public I_MPI_CTOperations<T> add_CT(int decorator, String output_file_name) {
		if (decorator == I_MPI_CTOperations.WRITE)
			try {
				ct = new MPI_PowerResultCT_write<T>(ct, output_file_name);
			} catch (Exception e) {
				System.out.print("write not possible -> not a PowerResultFunction!!!!");
				return ct;
			}
		return ct;
	}

	/**
	 * Compute.
	 *
	 * @return the object
	 */
	public Object compute() {
		return ct.compute();
	}

	/**
	 * Gets the function.
	 *
	 * @return the function
	 */
	@Override
	public Function getFunction() {
		// TODO Auto-generated method stub
		return null;
	}

}
