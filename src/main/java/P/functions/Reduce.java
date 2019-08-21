package P.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import P.PFunction;
import P.PList;
import types.IPList;

/**
 * The Class Reduce.Performs a sequential reduction of the elements of the input
 * list based on the BiFunction given as parameter.
 *
 * @param <T> the generic type
 */
public class Reduce<T> extends PFunction<T> {

	/** The operator. */
	protected BiFunction<T, T, T> operator;

	/** The sub results. */
	protected List<Object> sub_results = new ArrayList<Object>();

	/**
	 * Instantiates a new reduce.
	 *
	 * @param operator the operator
	 * @param p_arg    the p arg
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public Reduce(BiFunction<T, T, T> operator, IPList<T> p_arg) throws IllegalArgumentException {
		super(p_arg);
		this.operator = operator;
	}

	/**
	 * Combine.
	 *
	 * @param results the results
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T combine(List<Object> results) {

		T res = (T) results.get(0);

		if (this.operator != null) {
			for (int i = 1; i < results.size(); i++) {
				final T right = (T) results.get(i);
				res = this.operator.apply(res, right);
			}
		}
		result = res;
		return res;
	}

	/**
	 * Basic case.
	 *
	 * @return the object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object basic_case() {
		final PList<T> s = (PList<T>) super.basic_case();
		if (s.getValue() instanceof types.IBasicList) {

			final types.IBasicList<T> list = (types.IBasicList<T>) s.getValue();
			basic.functions.Reduce<T> rf = new basic.functions.Reduce<T>(this.operator, list);
			final Object result = rf.compute();
			s.setValue((T) result);
		}
		return s.getValue();
	}

	/**
	 * Creates the sublists function.
	 *
	 * @return the list
	 */
	@Override
	public List<PFunction<T>> create_sublists_function() {
		final List<PFunction<T>> functions = new ArrayList<PFunction<T>>();

		for (int i = 0; i < this.sublists_p_arg.get(0).size(); i++) {
			functions.add(new Reduce<T>(this.operator, this.sublists_p_arg.get(0).get(i)));
		}
		return functions;
	}

	/**
	 * Split arg.
	 */
	@Override
	public void split_arg() {
		super.split_arg();
		final List<IPList<T>> sublists_p = this.p_arg.get(0).getSubLists();

		if (sublists_p != null) {
			for (int i = 0; i < sublists_p.size(); i++) {
				this.sub_results.add(i, sublists_p.get(i));
			}
		}
	}

}
