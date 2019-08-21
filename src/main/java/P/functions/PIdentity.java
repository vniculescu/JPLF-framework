package P.functions;

import java.util.ArrayList;
import java.util.List;

import P.PFunction;
import types.IPList;

/**
 * The Class PIdentity.
 *
 * @param <T> the generic type
 */
public class PIdentity<T> extends PFunction<T> {

	/** The x. */
	private Object x;

	/**
	 * Instantiates a new p identity.
	 *
	 * @param list the list
	 * @param x    the x
	 */
	public PIdentity(IPList<T> list, Object x) {
		super(list);
		this.x = x;
	}

	/**
	 * Compute.
	 *
	 * @return the object
	 */
	public Object compute() {
		return x;
	}

	@Override
	public List<PFunction<T>> create_sublists_function() {
		final List<PFunction<T>> functions = new ArrayList<PFunction<T>>();

		for (int i = 0; i < this.p_arg.size(); i++) {
			functions.add(new PIdentity<T>(this.p_arg.get(i), x));
		}
		return functions;

	}
}
