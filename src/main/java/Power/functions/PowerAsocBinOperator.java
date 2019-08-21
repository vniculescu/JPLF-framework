package Power.functions;

import java.util.function.BiFunction;

import org.apache.commons.math3.FieldElement;

import Power.PowerResultFunction;
import basic.BasicAsocBinOperator;
import basic.BasicList;
import types.IBasicList;
import types.IPowerList;

/**
 * The Class PowerAsocBinOperator.
 *
 * @param <T> the generic type
 */
public class PowerAsocBinOperator<T extends FieldElement<T>> extends PowerResultFunction<T> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7934750273290597976L;
	/** The operator. */
	protected BiFunction<T, T, T> operator;

	/**
	 * Instantiates a new power asoc bin operator.
	 *
	 * @param operator the operator
	 * @param arg1     the arg 1
	 * @param arg2     the arg 2
	 * @param result   the result
	 */
	public PowerAsocBinOperator(BiFunction<T, T, T> operator, IPowerList<T> arg1, IPowerList<T> arg2,
			IPowerList<T> result) {
		super(result, arg1, arg2);
		this.operator = operator;
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
	 * @return the i power list
	 */
	@SuppressWarnings("unchecked")
	public IPowerList<T> basic_case() {

		IPowerList<T> a = power_arg.get(0);
		IPowerList<T> b = power_arg.get(1);
		IPowerList<T> res = power_result;// super.basic_case();

//	System.out.println("a="+a+"  b= "+b+"   res="+res);

		if (a.getValue() instanceof BasicList)

		{
			IBasicList<T> al = (IBasicList<T>) a.getValue();
			IBasicList<T> bl = (IBasicList<T>) b.getValue();
			IBasicList<T> rl = (IBasicList<T>) res.getValue();

			BasicAsocBinOperator<T> bop = new BasicAsocBinOperator<T>(this.operator, al, bl, rl);
			bop.compute();
		} else {
			power_result.setValue(this.operator.apply(a.getValue(), b.getValue()));
		}
		return power_result;
	}

	/**
	 * Creates the left function.
	 *
	 * @return the power result function
	 */
//Factory methods
	public PowerResultFunction<T> create_left_function() {
		PowerAsocBinOperator<T> fleft = new PowerAsocBinOperator<T>(operator, left_power_arg.get(0),
				left_power_arg.get(1), left_result);
		return fleft;
	}

	/**
	 * Creates the right function.
	 *
	 * @return the power result function
	 */
	public PowerResultFunction<T> create_right_function() {
		PowerAsocBinOperator<T> fright = new PowerAsocBinOperator<T>(operator, right_power_arg.get(0),
				right_power_arg.get(1), right_result);
		return fright;
	}

}
