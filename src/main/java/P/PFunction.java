package P;

import java.util.ArrayList;
import java.util.List;

import types.IPList;
import utils.Function;

/**
 * The Class PFunction.
 *
 * @param <T> the generic type
 */
public abstract class PFunction<T> extends Function implements Comparable<PFunction<T>> {
	// this is a function that has at least
	// a plist argument

	/** The p arg. */
	protected List<IPList<T>> p_arg = new ArrayList<IPList<T>>();

	/** The no p arg. */
	protected int no_p_arg;

	/** The sublists p arg. */
	protected List<List<IPList<T>>> sublists_p_arg;

	/** The s arg. */
	protected List<List<Object>> s_arg;

	/**
	 * Instantiates a new p function.
	 *
	 * @param args the args
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public PFunction(Object... args) throws IllegalArgumentException {
		setArg(args);
		// if no object arguments then arg will be set to null
		// setArg(args); this is implicitely called by super(args)
	}

	/**
	 * Instantiates a new p function.
	 *
	 * @param p_arg the p arg
	 * @param arg   the arg
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public PFunction(List<PList<T>> p_arg, List<Object> arg) throws IllegalArgumentException {
		setArg(p_arg, arg);
	}

	/**
	 * Sets the arg.
	 *
	 * @param args the new arg
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	@Override
	public void setArg(Object... args) throws IllegalArgumentException {
		this.no_p_arg = 0;
		this.no_arg = 0;
		// this.p_arg= new ArrayList<IPList<T>>();
		// this.arg= new ArrayList<Object>();

		for (final Object o : args) {
			if (o instanceof PList) {
				this.p_arg.add((IPList<T>) o);
				this.no_p_arg++;
			} else {
				this.arg.add(o);
				this.no_arg++;
			}
		}
		if (this.no_p_arg == 0) {
			throw new IllegalArgumentException();// in this case we don't have a PListFunction
		}

		if (this.no_arg == 0) {
			this.arg = null;
		}

		// result = compute();

	}

	/**
	 * Sets the arg.
	 *
	 * @param p_arg the p arg
	 * @param arg   the arg
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setArg(List<IPList<T>> p_arg, List<Object> arg) throws IllegalArgumentException {
		if (p_arg == null) {
			throw new IllegalArgumentException();
		}
		if (p_arg.size() == 0) {
			throw new IllegalArgumentException();
		}
		this.p_arg = p_arg;
		this.no_p_arg = p_arg.size();
		this.arg = arg;
		if (arg != null) {
			this.no_arg = arg.size();
			// result =compute();
		}
	}

	/**
	 * Test basic case.
	 *
	 * @return true, if successful
	 */
	public boolean test_basic_case() {
		/*
		 * even if there are more than one plist args it is enough to very the first for
		 * being a Singleton
		 */
		final IPList<T> arg0 = this.p_arg.get(0);
		if (arg0.isSingleton()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Basic case.
	 *
	 * @return the object
	 */
	////////////////////////////////////////////////////////////////////////////
	public Object basic_case() {
		final IPList<T> arg0 = this.p_arg.get(0);
		return arg0;
	}

	/**
	 * Split arg.
	 */
	public void split_arg() {
		// precond: power_arg is non_singleton
		// postcond: sublists_p_arg and sublists_arg were computed

		this.sublists_p_arg = new ArrayList<List<IPList<T>>>();
		for (int i = 0; i < this.p_arg.size(); i++) {
			this.sublists_p_arg.add(this.p_arg.get(i).getSubLists());
		}
		// for (int i = 0; i < p_arg.size(); i++) {
		// // NonSingletonPList<T> p_arg_ns = (NonSingletonPList<T>)p_arg.get(i);
		// sublists_p_arg.add(i, p_arg.get(i).getSubLists());
		// }
		if (this.arg != null) {
			for (int i = 0; i < this.arg.size(); i++) {
				this.arg.set(i, null); // ????????????
			}
		}

	}

	/**
	 * Combine.
	 *
	 * @param results the results
	 * @return the object
	 */
	public Object combine(List<Object> results) {
		return results;
	}

	/**
	 * Creates the sublists function.
	 *
	 * @return the list
	 */
	// Factory method - create the sublists_functions
	public abstract List<PFunction<T>> create_sublists_function();

	/**
	 * Compute.
	 *
	 * @return the object
	 */
	/////////////////////////////////////////////////////////////////////////
	@Override
	public Object compute() {// template method
		if (test_basic_case()) {
			this.result = basic_case();
		} else {
			split_arg();
			final List<PFunction<T>> sublists_functions = create_sublists_function();
			final List<Object> res_sublist = new ArrayList<Object>();
			for (int i = 0; i < sublists_functions.size(); i++) {
				res_sublist.add(sublists_functions.get(i).compute());
			}

			this.result = combine(res_sublist);
		}
		return this.result;
	}
	/////////////////////////////////////////////////////////////////////////

	/**
	 * Compare to.
	 *
	 * @param pf the pf
	 * @return the int
	 */
	@Override
	public int compareTo(PFunction<T> pf) {
		// based on the size of the first plist argument
		final PList<T> a = (PList<T>) this.p_arg.get(0);
		final PList<T> pa = (PList<T>) pf.p_arg.get(0);
		return -(a.getLen() - pa.getLen());
	}

	/**
	 * Gets the p arg.
	 *
	 * @return the p arg
	 */
	public List<IPList<T>> getPArg() {
		return this.p_arg;
	}

}
