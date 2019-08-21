package Power.functions;

import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;

import org.apache.commons.math3.FieldElement;

import Power.PowerList;
import Power.PowerResultFunction;
import basic.functions.AddTo;
import execution.FJ_PowerFunctionComputationTask;
import types.IBasicList;
import types.IPowerList;

/**
 * The Class Scan_2017.
 *
 * @param <T> the generic type
 */
public class Scan_2017<T extends FieldElement<T>> extends PowerResultFunction<T> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5424059504981848893L;
	/** The op. */
	private BiFunction<T, T, T> op;

	/**
	 * Instantiates a new scan 2017.
	 *
	 * @param op  the op
	 * @param arg the arg
	 */
	public Scan_2017(BiFunction<T, T, T> op, IPowerList<T> arg) {
		super(arg, arg);
		this.op = op;
	}

	/**
	 * Basic case.
	 *
	 * @return the i power list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IPowerList<T> basic_case() {
		IPowerList s = power_arg.get(0); // (PowerList) super.basic_case();

		if (s.getValue() instanceof types.IBasicList) {
			types.IBasicList<T> list = (types.IBasicList<T>) s.getValue();
			basic.functions.Scan<T> rf = new basic.functions.Scan<T>(this.op, list, list);
			Object result = rf.compute();
			s.setValue(result);
		}
		return s;
	}

	/**
	 * Combine.
	 *
	 * @param left  the left
	 * @param right the right
	 * @return the i power list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IPowerList<T> combine(Object left, Object right) {
		// the last element of left list should be added to all the right elements
		// power_result is the same (identity) to argument list!!! (from constructor)

		PowerList<T> l = (PowerList<T>) left;
		PowerList<T> r = (PowerList<T>) right;

		if (l.isSingleton())// enough-> && r.isSingleton())
		{
			if (l.getValue() instanceof types.IBasicList) {
				IBasicList<T> l_list = (IBasicList<T>) l.getValue();
				IBasicList<T> r_list = (IBasicList<T>) r.getValue();

				T last = l_list.getValue(l_list.getEnd());
				r_list.setValue(op.apply(last, r_list.getValue()));

				for (int i = r_list.getStart() + r_list.getIncr(); i <= r_list.getEnd(); i += r_list.getIncr()) {
					r_list.setValue(op.apply(last, r_list.getValue()), i);
				}
			} else {
				r.setValue(op.apply(l.getValue(), r.getValue()));
			}

		} else // not singleton
		{

			if (l.getValue() instanceof types.IBasicList) {// there are more sublists for which we have to update the
															// elements

				IBasicList<T> l_list = (IBasicList<T>) l.getValue(l.getEnd());
				T last = l_list.getValue(l_list.getEnd());

				for (int j = r.getStart(); j <= r.getEnd(); j += r.getIncr()) {
					// for each sublist in the right update the values

					IPowerList<T> r_list = ((IBasicList<T>) r.getValue(j)).toPowerList();
					// System.out.println("new MAp");
					Power.functions.Map<T> pmf = new Power.functions.Map<T>(t -> last.add(last), r_list);
					ForkJoinPool executor = ForkJoinPool.commonPool();
					FJ_PowerFunctionComputationTask<T> exec = new FJ_PowerFunctionComputationTask<T>(pmf);
					executor.invoke(exec);

				}
			}

			else {

				T e = l.getValue(l.getEndPos());
				// System.out.println("AddTo"+e);
				AddTo adder = new AddTo(e, r.getValue());
				Power.functions.Map<T> pmf = new Power.functions.Map<T>(adder, r);
				ForkJoinPool executor = ForkJoinPool.commonPool();
				FJ_PowerFunctionComputationTask<T> exec = new FJ_PowerFunctionComputationTask<T>(pmf);
				executor.invoke(exec);
			}
		}
		return power_result; // power_result is identical to list argument
	}

	/**
	 * Creates the left function.
	 *
	 * @return the power result function
	 */
	@Override
	public PowerResultFunction<T> create_left_function() {
		return new Scan_2017<T>(op, left_result);
	}

	/**
	 * Creates the right function.
	 *
	 * @return the power result function
	 */
	@Override
	public PowerResultFunction<T> create_right_function() {
		return new Scan_2017<T>(op, right_result);
	}

}
