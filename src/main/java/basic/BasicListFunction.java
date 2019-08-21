package basic;

import java.util.ArrayList;
import java.util.List;

import types.IBasicList;
import utils.Function;

/**
 * The Class BasicListFunction. This is the parent/template function for simple
 * (non-recursive) sequential definition of a function defined on lists
 *
 * @param <T> the generic type
 */

public class BasicListFunction<T> extends Function {

	/** The list arg. */
	protected List<IBasicList<T>> list_arg = new ArrayList<IBasicList<T>>();

	/** The no list arg. */
	protected int no_list_arg;

	/**
	 * Instantiates a new basic list function.
	 *
	 * @param args the args
	 */
	public BasicListFunction(Object... args) {
		super(args);
		setArg(args); // setArg is also called in 'super' but the values for the members (of 'this')
						// are not saved
	}

	/**
	 * Instantiates a new basic list function.
	 *
	 * @param a the a
	 */
	public BasicListFunction(IBasicList<T> a) {
		// a function with one argument of type BasicList<T>
		arg = null;
		no_arg = 0; // there are no simple object arguments
		no_list_arg = 1;
		list_arg.add(a);
	}

	/**
	 * Sets the arg.
	 *
	 * @param args the new arg
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setArg(Object... args) throws IllegalArgumentException {
		no_list_arg = 0;
		no_arg = 0;
		if (list_arg == null)
			list_arg = new ArrayList<IBasicList<T>>();
		for (Object o : args) {
			if (o instanceof IBasicList<?>) {
				list_arg.add((IBasicList<T>) o);
				no_list_arg++;
			} else {
				arg.add(o);
				no_arg++;
			}
		}
		if (no_list_arg == 0) // in this case we don't have a BasicListFunction
			throw new IllegalArgumentException();
		if (no_arg == 0)
			arg = null;

	}

}
