package P.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import P.PFunction;
import P.PResultFunction;
import types.IPList;

/**
 * The Class PFilter.This represents a filter operation, is iterates the input
 * list and based on a Predicate decides if the result list should contain or
 * not the iterated element.
 *
 * @param <T> the generic type
 */
public class PFilter<T> extends PResultFunction<T> {

	/** The predicate. */
	private Predicate<T> predicate;

	/**
	 * Instantiates a new p filter.
	 *
	 * @param predicate the predicate
	 * @param list      the list
	 */
	public PFilter(Predicate<T> predicate, IPList<T> list) {
		super(list, list);
		this.predicate = predicate;
	}

	/**
	 * Instantiates a new p filter.
	 *
	 * @param predicate the predicate
	 * @param arg       the arg
	 * @param res       the res
	 */
	public PFilter(Predicate<T> predicate, IPList<T> arg, IPList<T> res) {
		super(arg, res);
		this.predicate = predicate;
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
			final basic.functions.Filter<T> mf = new basic.functions.Filter<T>(predicate, list);
			s.setValue(mf.compute());
		} else {
			if (!predicate.test((T) s.getValue())) {
				s.setValue(Optional.empty());
			} else {
				s.setValue(Optional.of((T) s.getValue()));
			}
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
			functions.add(i, new PFilter<T>(this.predicate, this.sublists_p_arg.get(0).get(i)));
		}
		return functions;
	}

}
