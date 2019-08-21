package P;

import java.util.ArrayList;
import java.util.List;

import types.IPList;

/**
 * The Class PResultFunction.
 *
 * @param <T> the generic type
 */
public class PResultFunction<T> extends PFunction<T> {

	/** The p result. */
	// the recipient where the result will be computed is given in the constructor
	protected IPList<T> p_result;

	/** The sub results. */
	protected List<Object> sub_results = new ArrayList();
	// not List<IPList<T> > because would lead to some problems

	/**
	 * Instantiates a new p result function.
	 *
	 * @param p_result the p result
	 * @param args     the args
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public PResultFunction(IPList<T> p_result, Object... args) throws IllegalArgumentException {
		super(args);
		// setArg(args);
		this.p_result = p_result;
	}

	/**
	 * Instantiates a new p result function.
	 *
	 * @param p_arg    the p arg
	 * @param arg      the arg
	 * @param p_result the p result
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public PResultFunction(List<PList<T>> p_arg, List<Object> arg, IPList<T> p_result) throws IllegalArgumentException {
		super(p_arg, arg);
		setArg(p_arg, arg);
		this.p_result = p_result;
	}

	/**
	 * Split arg.
	 */
	public void split_arg() {
		// p_arg and p_result are nonsingleton lists
		// the places for the results are created
		super.split_arg(); // p_arg is split inside

		// p_result is split here in results list

		// NonSingletonPList<T> p_result_ns = (NonSingletonPList<T>)p_result;
		List<IPList<T>> results_p = p_result.getSubLists();

		if (results_p != null)
			for (int i = 0; i < results_p.size(); i++)
				sub_results.add(i, results_p.get(i));

	}

	/**
	 * Creates the sublists function.
	 *
	 * @return the list
	 */
	// Factory methods
	public List<PFunction<T>> create_sublists_function() {
		List<PFunction<T>> functions = new ArrayList<PFunction<T>>();
		for (int i = 0; i < p_arg.size(); i++)
			functions.add(new PResultFunction<T>(p_arg.get(i), s_arg.get(i), sub_results.get(i)));

		return functions;
	}

	/**
	 * Compute.
	 *
	 * @return the IP list
	 */
	public IPList<T> compute() {// template method
		if (test_basic_case()) {
			p_result = (IPList<T>) basic_case();
		} else {
			split_arg();
			List<PFunction<T>> functions = create_sublists_function();
			for (int i = 0; i < functions.size(); i++)
				sub_results.set(i, ((PResultFunction<T>) (functions.get(i))).compute());

			p_result = combine(sub_results);
		}
		return p_result;
	}

	/**
	 * Basic case.
	 *
	 * @return the IP list
	 */
	@Override
	public IPList<T> basic_case() {
		return p_result;
	}

	/**
	 * Combine.
	 *
	 * @param o the o
	 * @return the IP list
	 */
	@Override
	public IPList<T> combine(List<Object> o) {
		return p_result;
	}

}
