package execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import P.PFunction;

/**
 * The Class FJ_PFunctionComputationTask. Represents recursive tasks that are
 * created for PLists executors
 *
 * @param <T> the generic type
 */
public class FJ_PFunctionComputationTask<T> extends RecursiveTask<Object> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The function. */
	protected PFunction<T> function;

	/** The recursion depth. */
	private final int recursion_depth;

	/**
	 * Instantiates a new f J P function computation task.
	 *
	 * @param function        the function
	 * @param recursion_depth the recursion depth
	 */
	public FJ_PFunctionComputationTask(PFunction<T> function, int recursion_depth) {
		this.function = function;
		this.recursion_depth = recursion_depth;
	}

	/**
	 * Instantiates a new f J P function computation task.
	 *
	 * @param function the function
	 */
	public FJ_PFunctionComputationTask(PFunction<T> function) {
		this.function = function;
		this.recursion_depth = this.function.getPArg().get(0).getArityList().size();
	}

	/**
	 * Compute.
	 *
	 * @return the object
	 */

	@Override
	protected Object compute() {
		Object result = null;
		if (this.function.test_basic_case()) {
			result = this.function.basic_case();
		} else {
			this.function.split_arg();

			final List<PFunction<T>> sub_functions = this.function.create_sublists_function();

			if (this.recursion_depth == 0) {
				result = this.function.compute();
			} else {

				final List<FJ_PFunctionComputationTask<T>> sub_func_exec = new ArrayList<FJ_PFunctionComputationTask<T>>();
				for (final PFunction<T> f : sub_functions) {
					sub_func_exec.add(new FJ_PFunctionComputationTask<T>(f, this.recursion_depth - 1));
				}
				final List<Object> sub_results = new ArrayList<Object>(sub_functions.size());

				for (int i = 1; i < sub_func_exec.size(); i++) {
					sub_func_exec.get(i).fork();
				}
				sub_results.add(0, sub_func_exec.get(0).compute());
				for (int i = 1; i < sub_func_exec.size(); i++) {
					sub_results.add(i, sub_func_exec.get(i).join());
				}

				result = this.function.combine(sub_results);
			}
		}
		return result;
	}

}
