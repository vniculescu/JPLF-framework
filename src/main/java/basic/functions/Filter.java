package basic.functions;

import java.util.Optional;
import java.util.function.Predicate;

import basic.BasicListResultFunction;
import types.IBasicList;

/**
 * The Class Filter. This represents a filter operation, that is done
 * sequentially, is iterates the input list and based on a Predicate decides if
 * the result list should contain or not the iterated element.
 *
 * @param <T> the generic type
 */
public class Filter<T> extends BasicListResultFunction<T> {

	/** The predicate. */
	private final Predicate<T> predicate;

	/**
	 * Instantiates a new filter.
	 *
	 * @param predicate the predicate
	 * @param list      the list
	 */
	public Filter(Predicate<T> predicate, IBasicList<T> list) {
		super(list, list);
		this.predicate = predicate;
		list_result = list_arg.get(0);
	}

	/**
	 * Compute.
	 *
	 * @return the i basic list
	 */
	@SuppressWarnings("unchecked")
	public IBasicList<T> compute() {
		int n = list_result.getLen();
		for (int i = 0; i < n; i++) {
			T e = list_result.getValue(i);
			if (!predicate.test(e)) {
				list_result.setValue((T) Optional.empty(), i);
			} else {
				list_result.setValue((T) Optional.of(e), i);
			}
		}
		return list_result;
	}
}