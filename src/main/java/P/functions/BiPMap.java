package P.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import P.PFunction;
import P.PResultFunction;
import types.IPList;

/**
 * The Class BiMaP.Represents a Map function that takes as generic types two
 * type T and R, it computes the map operator pn on the list of type T and saves
 * the result into the list of type R.
 *
 * @param <T> the generic type
 * @param <R> the generic type
 */
public class BiPMap<T, R> extends PResultFunction<R> {

	/** The function. */
	private Function<T, R> function;

	/** The list. */
	private IPList<T> list;

	/** The sub lsits. */
	private List<IPList<T>> sub_lsits = new ArrayList<IPList<T>>();

	/**
	 * Instantiates a new bi maP.
	 *
	 * @param simpleFunction the simple function
	 * @param list           the list
	 * @param result         the result
	 */
	public BiPMap(Function<T, R> simpleFunction, IPList<T> list, IPList<R> result) {
		super(result, result);
		this.function = simpleFunction;
		this.list = list;
	}

	/**
	 * Basic case.
	 *
	 * @return the IP list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPList<R> basic_case() {
		final IPList s = super.basic_case();
		if (s.getValue() instanceof types.IBasicList) {
			final types.IBasicList<R> bLlist = (types.IBasicList<R>) s.getValue();
			final basic.functions.BiMap<T, R> mf = new basic.functions.BiMap<T, R>(function, list, bLlist);
			s.setValue(mf.compute());
		} else {
			s.setValue(this.function.apply(list.getValue(0)));
		}
		return s;
	}

	/**
	 * Creates the sublists function.
	 *
	 * @return the list
	 */
	@Override
	public List<PFunction<R>> create_sublists_function() {

		final List<PFunction<R>> functions = new ArrayList<PFunction<R>>();

		for (int i = 0; i < this.sublists_p_arg.get(0).size(); i++) {

			functions.add(i, new BiPMap<T, R>(this.function, this.sub_lsits.get(i), this.sublists_p_arg.get(0).get(i)));
		}
		return functions;
	}

	/**
	 * Split arg.
	 */
	public void split_arg() {
		super.split_arg();
		List<IPList<T>> lists = list.getSubLists();
		if (lists != null)
			for (int i = 0; i < lists.size(); i++) {
				sub_lsits.add(i, lists.get(i));
			}
	}
}
