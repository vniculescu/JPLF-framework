package Power.functions;

import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;

import org.apache.commons.math3.FieldElement;

import Power.PowerList;
import Power.PowerResultFunction;
import Power.ZipPowerList;
import basic.BasicList;
import basic.Transformer;
import execution.FJ_PowerFunctionComputationTask;
import types.IPowerList;

/**
 * The Class LFScan_Parallel.
 *
 * @param <T> the generic type
 */
public class LFScan_Parallel<T extends FieldElement<T>> extends PowerResultFunction<T> {

	/** The op. */
	private BiFunction<T, T, T> op;

	/** The identity. */
	T identity;

	/** The executor. */
	ForkJoinPool executor;

	/** The d op. */
	int dop;

	/** The t. */
	Transformer<T> t = new Transformer<T>();

	/**
	 * Instantiates a new LF scan parallel.
	 *
	 * @param e        the e
	 * @param op       the op
	 * @param arg0     the arg 0
	 * @param arg1     the arg 1
	 * @param executor the executor
	 * @param dop      the dop
	 */
	public LFScan_Parallel(T e, BiFunction<T, T, T> op, IPowerList<T> arg0, IPowerList<T> arg1, ForkJoinPool executor,
			int dop) {
		/**
		 * arg1 and arg2 are ZipPowerList
		 */
		super(arg1, arg0);
		this.op = op;
		this.identity = e;
		this.executor = executor;
		this.dop = dop;
//			power_result = power_result.toZipPowerList();
//			power_arg.set(0,power_arg.get(0).toZipPowerList());
	}

	/**
	 * Compute.
	 *
	 * @return the i power list
	 */
	public IPowerList<T> compute() {
//System.out.println("input="+power_arg.get(0)+"+result"+power_result);
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

			split_arg(); // left_result, right_result and left, right args are set

//compute (p+q)
			FJ_PowerFunctionComputationTask<T> exec;
			BiFunction<T, T, T> sumOp = (a, b) -> a.add(b);

			PowerAsocBinOperator sum_op;

			if (dop > 1 && dop < left_power_arg.get(0).getLen()) {
				ZipPowerList<BasicList<T>> dleft_power_arg = t.toZipDepthList((PowerList) left_power_arg.get(0), dop);
				ZipPowerList<BasicList<T>> dright_power_arg = t.toZipDepthList((PowerList) right_power_arg.get(0), dop);
				ZipPowerList<BasicList<T>> dleft_result = t.toZipDepthList((PowerList) left_result, dop);
				sum_op = new PowerAsocBinOperator(sumOp, dleft_power_arg, dright_power_arg, dleft_result);
			} else {
				sum_op = new PowerAsocBinOperator(sumOp, left_power_arg.get(0), right_power_arg.get(0), left_result);
				// left_result = sum_ op.compute();
			}
			exec = new FJ_PowerFunctionComputationTask<T>(sum_op);
			executor.invoke(exec);

//					System.out.println("args before LF"+left_power_arg.get(0)+"right"+right_power_arg.get(0)+"result"+left_result);
//					System.out.println("left_result before LF"+left_result+"and  right_result"+right_result);

			LFScan_Parallel<T> sub_function = new LFScan_Parallel<T>(identity, op, left_result, /* contine p+q */
					right_result, /* va contine LG(p+q) */
					executor, dop);
			right_result = sub_function.compute();
//					 System.out.println("LF intermediar"+right_result+"arg"+left_power_arg.get(0));
//					 System.out.println("after LF intermediar left"+left_result);
			// LF poate sa aiba offset diferit de 0

			int off = Math.abs(((PowerList) right_result).getOffset());
			while (right_result.getLen() > 1 && off > 0) {
				((PowerList) left_result).rotateRight();
				off--;
			}

			// !!!!!!!!!!!!!!!copiere!!!!!!!!!!

			// right_result.copyElems(left_result/*dest*/);

			// !!!!!!!!!!!!!!!copiere!!!!!!!!!!
			// s-ar putea face in paralel - fiecare element din 'left_result' isi copiaza
			// vecinul din base
			Power.functions.CopyOperator cp;

//					 System.out.println("left"+left_result);
//					 System.out.println("right"+right_result);
//					 System.out.println("dop"+dop);
			if (dop > 1 && dop < left_result.getLen()) {
				ZipPowerList<BasicList<T>> dright_result = t.toZipDepthList((PowerList) right_result, dop);
				ZipPowerList<BasicList<T>> dleft_result = t.toZipDepthList((PowerList) left_result, dop);
				cp = new Power.functions.CopyOperator(dright_result, dleft_result);
			} else {
				cp = new Power.functions.CopyOperator((PowerList) right_result, (PowerList) left_result);
			}
			// cP.compute();

			exec = new FJ_PowerFunctionComputationTask<T>(cp);
			executor.invoke(exec);
			((PowerList) power_result).setFrom2Lists((PowerList) left_result, (PowerList) right_result);

//compute shift operation : we use
// (p#q)* = q*#p
// (p#p)* = p*#p

			((PowerList) power_result).shiftRight(identity);// ?????????????

			if (power_result.getLen() > 1) {
				left_result = power_result.getLeft();
				right_result = power_result.getRight();
			}

			sum_op = null;
			if (dop > 1 && dop < left_power_arg.get(0).getLen()) {
				ZipPowerList<BasicList<T>> dleft_result = t.toZipDepthList((PowerList) left_result, dop);
				ZipPowerList<BasicList<T>> dleft_power_arg = t.toZipDepthList((PowerList) left_power_arg.get(0), dop);
				sum_op = new PowerAsocBinOperator(sumOp, dleft_result, dleft_power_arg, dleft_result);
			} else {
				sum_op = new PowerAsocBinOperator<T>(sumOp, left_result, left_power_arg.get(0), left_result);
				// sum_op1.compute();
			}

			exec = new FJ_PowerFunctionComputationTask<T>(sum_op);
			executor.invoke(exec);

		}
		return power_result;
	}

	/**
	 * Basic case.
	 *
	 * @return the i power list
	 */
	@Override
	public IPowerList<T> basic_case() {
		IPowerList s = power_result;
		if (s.getValue() instanceof types.IBasicList) {
			types.IBasicList<T> list = (types.IBasicList<T>) power_arg.get(0).getValue();
			types.IBasicList<T> res = (types.IBasicList<T>) power_result.getValue();
			basic.functions.Scan<T> rf = new basic.functions.Scan<T>(op, list, res);
			Object result = rf.compute();
			System.out.println("arg in basic" + power_arg.get(0).getValue());
			System.out.println("res in basic" + result);
			// s.setValue(result);
		} else
			power_result.setValue(power_arg.get(0).getValue());
		return power_result;
	}

}
