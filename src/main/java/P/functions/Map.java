package P.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import P.PFunction;
import P.PResultFunction;
import types.IPList;

/**
 * The Class MaP.Map function that applies a Function  to each
 * elements of the input list.
 *
 * @param <T> the generic type
 */
public class Map<T> extends PResultFunction<T> {

	/** The function. */
	private Function<T, T> function;

	/**
	 * Instantiates a new maP.
	 *
	 * @param simpleFunction the simple function
	 * @param arg            the arg
	 */
	public Map(Function<T, T> simpleFunction, IPList<T> arg) {
		super(arg, arg);
		this.function = simpleFunction;
	}

	/**
	 * Instantiates a new p map.
	 *
	 * @param simpleFunction the simple function
	 * @param list           the list
	 * @param res            the res
	 */
	public Map(Function<T, T> simpleFunction, IPList<T> list, IPList<T> res) {
		super(list, res);
		this.function = simpleFunction;
	}

	/**
	 * Basic case.
	 *
	 * @return the IP list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPList<T> basic_case() {
		final IPList s = super.basic_case();
		if (s.getValue() instanceof types.IBasicList) {
			final types.IBasicList<T> list = (types.IBasicList<T>) s.getValue();
			final basic.functions.Map<T> mf = new basic.functions.Map<T>(function, list);
			s.setValue(mf.compute());
		} else {
			s.setValue(this.function.apply((T) s.getValue()));
		}
		return s;
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

			functions.add(i, new Map<T>(this.function, this.sublists_p_arg.get(0).get(i)));
		}
		return functions;
	}

}
