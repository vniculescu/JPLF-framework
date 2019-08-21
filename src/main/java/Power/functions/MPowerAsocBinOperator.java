package Power.functions;

import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.math3.FieldElement;

import Power.MPowerResultFunction;
import basic.BasicAsocBinOperator;
import basic.BasicList;
import types.IBasicList;
import types.IPowerList;

/**
 * The Class MPowerAsocBinOperator.
 *
 * @param <T> the generic type
 */
/*
 * multiple operator compute simultaneously more binary operators all operators
 * are on the same arguments
 */
public class MPowerAsocBinOperator<T extends FieldElement<T>> extends MPowerResultFunction<T> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5632710390256694849L;

	/** The operator. */
	protected List<BiFunction<T, T, T>> operator;

	/** The n. */
	protected int n = 1;

	/**
	 * Instantiates a new m power asoc bin operator.
	 *
	 * @param operator the operator
	 * @param arg1     the arg 1
	 * @param arg2     the arg 2
	 * @param result   the result
	 */
	public MPowerAsocBinOperator(List<BiFunction<T, T, T>> operator, IPowerList<T> arg1, IPowerList<T> arg2,
			List<IPowerList<T>> result) {
		super(result, arg1, arg2);
		this.operator = operator;
		this.n = operator.size();
	}

	/**
	 * Test basic case.
	 *
	 * @return true, if successful
	 */
////////////////////////////////////////////////////////////////////////////
	public boolean test_basic_case() {
		IPowerList<T> arg0 = power_arg.get(0);
		IPowerList<T> arg1 = power_arg.get(1);
		if (arg0.isSingleton() && arg1.isSingleton()) {
			return true;
		} else
			return false;
	}

	/**
	 * Basic case.
	 *
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<IPowerList<T>> basic_case() {
		for (int i = 0; i < n; i++) {
			IPowerList<T> a = power_arg.get(0);
			IPowerList<T> b = power_arg.get(1);
			IPowerList<T> res = power_result.get(i);// super.basic_case();

			if (res.getValue() instanceof BasicList) {
				IBasicList<T> al = (IBasicList<T>) a.getValue();
				IBasicList<T> bl = (IBasicList<T>) b.getValue();
				IBasicList<T> rl = (IBasicList<T>) res.getValue();

				BasicAsocBinOperator<T> bop = new BasicAsocBinOperator<T>(operator.get(i), al, bl, rl);
				bop.compute();
			} else {
				power_result.get(i).setValue(operator.get(i).apply(a.getValue(), b.getValue()));
			}
		}
		return power_result;
	}

	/**
	 * Creates the left function.
	 *
	 * @return the m power result function
	 */
//Factory methods
	@Override
	public MPowerResultFunction<T> create_left_function() {
		MPowerAsocBinOperator<T> fleft = new MPowerAsocBinOperator<T>(operator, left_power_arg.get(0),
				left_power_arg.get(1), left_result);
		return fleft;
	}

	/**
	 * Creates the right function.
	 *
	 * @return the m power result function
	 */
	@Override
	public MPowerResultFunction<T> create_right_function() {
		MPowerAsocBinOperator<T> fright = new MPowerAsocBinOperator<T>(this.operator, right_power_arg.get(0),
				right_power_arg.get(1), right_result);
		return fright;
	}

}