package Power.functions;

import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;

import org.apache.commons.math3.FieldElement;

import Power.PowerList;
import Power.PowerResultFunction;
import Power.ZipPowerList;
import execution.FJ_PowerFunctionComputationTask;
import types.IPowerList;

/**
 * The Class LFScan_par.
 *
 * @param <T> the generic type
 */
public class LFScan_par<T extends FieldElement<T>> extends PowerResultFunction<T> {

	/** The op. */
	private BiFunction<T, T, T> op;

	/** The identity. */
	T identity;

	/** The executor. */
	ForkJoinPool executor;

	/** The d op. */
	int dop;

	/**
	 * Instantiates a new LF scan par.
	 *
	 * @param e        the e
	 * @param op       the op
	 * @param arg0     the arg 0
	 * @param arg1     the arg 1
	 * @param executor the executor
	 * @param dop      the dop
	 */
	public LFScan_par(T e, BiFunction<T, T, T> op, IPowerList<T> arg0, IPowerList<T> arg1, ForkJoinPool executor,
			int dop) {
		super(arg1, arg0);
		this.op = op;
		this.identity = e;
		this.executor = executor;
		this.dop = dop;
	}

	/**
	 * Compute.
	 *
	 * @return the i power list
	 */
	public IPowerList<T> compute() {
		if (test_basic_case()) {
			power_result = basic_case();// .toPowerList();
		} else {
			// LF.(p#q) = (LF.(p + q))* + p # LF.(p + q)= [(LF.(p + q))* # LF.(p +
			// q)] + (p#0)
			// =[(LF.(p + q)) # LF.(p + q)]* + (p# '0') =
			// recursivitatea are doar o singura ramura => (log2 n) niveluri
			// dar la fiecare nivel sunt 2 apeluri de tip PowerAsocBinOperator<T>:
			// unul la coborare si unul la urcare (combine)
			// System.out.println("in LFScan compute");
			split_arg(); // left_result, right_result and left, right args
//compute (p+q)
			PowerAsocBinOperator<T> sum_op = new PowerAsocBinOperator<T>((a, b) -> a.add(b), left_power_arg.get(0),
					right_power_arg.get(0), left_result);
			// left_result = sum_ op.compute();
			FJ_PowerFunctionComputationTask<T> exec = new FJ_PowerFunctionComputationTask<T>(sum_op);
			executor.invoke(exec);

			LFScan_Parallel<T> sub_function = new LFScan_Parallel<T>(identity, op, left_result, right_result, executor,
					dop);
			right_result = sub_function.compute();

			int off = Math.abs(((PowerList) right_result).getOffset());
			while (right_result.getLen() > 1 && off != 0) {
				((PowerList) left_result).rotateRight();
				off--;
			}
			// !!!!!!!!!!!!!!!copiere!!!!!!!!!!
			// right_result.copyElems(left_result);
			// !!!!!!!!!!!!!!!copiere!!!!!!!!!!
			// s-ar putea face in paralel - fiecare element din 'left_result' isi copiaza
			// vecinul din base
			Power.functions.CopyOperator<T> cp = new Power.functions.CopyOperator<T>((PowerList) right_result,
					(PowerList) left_result);
			cp.compute();
			ForkJoinPool executor = ForkJoinPool.commonPool();
			FJ_PowerFunctionComputationTask<T> exec1 = new FJ_PowerFunctionComputationTask<T>(cp);
			executor.invoke(exec1);

			power_result = new ZipPowerList<T>((PowerList) left_result, (PowerList) right_result);

//compute shift opertion : we use
// (p#q)* = q*#p
// (p#p)* = p*#p
			((PowerList) power_result).shiftRight(identity);

			if (power_result.getLen() > 1) {
				left_result = power_result.getLeft();
				right_result = power_result.getRight();
			}
			PowerAsocBinOperator<T> sum_op1 = new PowerAsocBinOperator<T>((a, b) -> a.add(b), left_result,
					left_power_arg.get(0), left_result);
			// sum_op1.compute();
			FJ_PowerFunctionComputationTask<T> exec2 = new FJ_PowerFunctionComputationTask<T>(sum_op1);
			executor.invoke(exec2);

		}

		return power_result;
	}

	/**
	 * Split arg.
	 */
	public void split_arg() {
		super.split_arg();
	}

	/**
	 * Basic case.
	 *
	 * @return the i power list
	 */
	@Override
	public IPowerList<T> basic_case() {

		IPowerList s = power_result;
		System.out.println("basic case " + power_result);
		if (s.getValue() instanceof types.IBasicList) {
			types.IBasicList<T> list = (types.IBasicList<T>) power_arg.get(0).getValue();
			basic.functions.Scan<T> rf = new basic.functions.Scan<T>(op, list, list);
			Object result = rf.compute();
			s.setValue(result);
		}
		return s;
	}

}
