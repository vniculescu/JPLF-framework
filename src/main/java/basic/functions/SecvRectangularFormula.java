package basic.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.math3.FieldElement;

import P.PFunction;
import P.PList;
import P.functions.PIdentity;
import P.functions.RectangularFormula;
import basic.BasicList;
import types.IPList;
import utils.Real;

/**
 * The Class SecvRectangularFormula. Computes the approximation of an integral
 * value using the rectangular formula and sequential computation
 *
 * @param <T> the generic type
 */
public class SecvRectangularFormula<T extends FieldElement<T>> extends PFunction<T> {

	/** The sub results. */
	private final List<Object> sub_results = new ArrayList<Object>();

	/** The times op. */
	private final BiFunction<T, T, T> timesOp = (a, b) -> a.multiply(b);

	/** The h. */
	private final T h;

	/** The k. */
	private final int k;

	/**
	 * Instantiates a new secv rectangular formula.
	 *
	 * @param power_arg the power arg
	 * @param k         the k
	 * @param h         the h
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public SecvRectangularFormula(IPList<T> power_arg, int k, T h) throws IllegalArgumentException {
		super(power_arg);
		this.k = k;
		this.h = h;
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
		res = timesOp.apply(res, (T) new Real(1.0 / 3.0));
//			System.out.println(h+"he--"+res);
		T s1 = (T) results.get(1);
		T s2 = (T) results.get(2);
		s1 = s1.add(s2);
		s1 = s1.multiply(h);
		res = res.add(s1);
		result = res;
		return res;
	}

	/**
	 * Basic case.
	 *
	 * @return the object
	 */
	@Override
	public Object basic_case() {
		@SuppressWarnings("unchecked")
		final PList<T> s = (PList<T>) super.basic_case();
		if (s.getValue() instanceof types.IBasicList<?>) {
			BasicList<T> basiclist = (BasicList<T>) s.getValue();
			IPList<T> sublist = basiclist.toPList().toZipPList();
			RectangularFormula<T> basicFunction = new RectangularFormula<T>(sublist, this.k, this.h);
			result = basicFunction.compute();
		} else {
			result = timesOp.apply(s.getValue(0), this.h);
		}
//			System.out.println(result);
		return result;
	}

	/**
	 * Creates the sublists function.
	 *
	 * @return the list
	 */
	@Override
	public List<PFunction<T>> create_sublists_function() {
		final List<PFunction<T>> functions = new ArrayList<PFunction<T>>();

		functions.add(new RectangularFormula<T>(this.sublists_p_arg.get(0).get(1), this.k - 1, this.h.multiply(3)));

		BasicList<T> basicList1 = (BasicList<T>) this.sublists_p_arg.get(0).get(0);
		BasicList<T> basicList2 = (BasicList<T>) this.sublists_p_arg.get(0).get(2);
		basic.functions.Reduce<T> bf1 = new basic.functions.Reduce<T>((a, b) -> a.add(b), basicList1);
		basic.functions.Reduce<T> bf2 = new basic.functions.Reduce<T>((a, b) -> a.add(b), basicList2);
		Object s1 = bf1.compute();
		Object s2 = bf1.compute();
		functions.add(new PIdentity<T>(new PList<T>((PList<T>) this.getPArg().get(0)), s1));
		functions.add(new PIdentity<T>(new PList<T>((PList<T>) this.getPArg().get(0)), s2));

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
//					System.out.println("list "+ i+ "  "+sublists_P.get(i));
			}
		}
	}

}
