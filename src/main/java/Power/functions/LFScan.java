package Power.functions;

import java.util.function.BiFunction;

import org.apache.commons.math3.FieldElement;

import Power.PowerList;
import Power.PowerResultFunction;
import types.IPowerList;

/**
 * The Class LFScan.
 *
 * @param <T> the generic type
 */
public class LFScan<T extends FieldElement<T>> extends PowerResultFunction<T> {

	/** The op. */
	private BiFunction<T, T, T> op;

	/** The identity. */
	T identity;

	/**
	 * Instantiates a new LF scan.
	 *
	 * @param e    the e
	 * @param op   the op
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	public LFScan(T e, BiFunction<T, T, T> op, IPowerList<T> arg0, IPowerList<T> arg1) {
		super(arg1, arg0);
		this.op = op;
		this.identity = e;
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
			// q)] + (p,0)
			// =[(LF.(p + q)) # LF.(p + q)]* + (p# '0')
//System.out.println("in LFScan compute");
			split_arg(); // left_result, right_result and left, right args
//compute (p+q)
			BiFunction<T, T, T> sumOp = (a, b) -> a.add(b);
			PowerAsocBinOperator<T> sum_op = new PowerAsocBinOperator<T>(sumOp, left_power_arg.get(0),
					right_power_arg.get(0), left_result);
			left_result = sum_op.compute();
			// left_result.copyElems(right_result);
			System.out.println("left after p+q " + left_result);
			System.out.println("right after p+q  " + right_result);

//compute LF(p+q)		
			System.out
					.println(power_result + "before LF partial for " + left_result + " with result in " + right_result);
			LFScan<T> sub_function = new LFScan<T>(identity, op, left_result, right_result);
			right_result = sub_function.compute();
			System.out.println("after LF partial" + power_result);

			int off = Math.abs(((PowerList) right_result).getOffset());
			while (left_result.getLen() > 1 && off != 0) {
				((PowerList) left_result).rotateRight();
				off--;
			}

			((PowerList) right_result).copyElems((PowerList) left_result); // !!!!!!!!!!!!!!!copiere!!!!!!!!!!
			// se poate face in paralel - fiecare element din right isi copiaza vecinul din
			// base
			System.out.println("left " + left_result);
			System.out.println("right " + right_result);
			System.out.println("power " + power_result);
			// power_result = new ZipPowerList<T>(left_result, right_result);
			System.out.println("power" + power_result);

			// compute shift operation : we use
// (p#q)* = q*#p
// (p#p)* = p*#p

			((PowerList) power_result).shiftRight(identity);

			System.out.println("after shift" + power_result);
			if (power_result.getLen() > 1) {
				left_result = power_result.getLeft();
				right_result = power_result.getRight();
			}

			System.out.println("left " + left_result);
			System.out.println("right " + right_result);
			PowerAsocBinOperator<T> sum_op1 = new PowerAsocBinOperator<T>(sumOp, left_result, left_power_arg.get(0),
					left_result);
			sum_op1.compute();
			System.out.println(
					"res power_result for" + left_result + "and " + left_power_arg.get(0) + "is " + power_result);
		}

		return power_result;
	}

//		public void split_arg(){
//				super.split_arg(); 		
//		}

	/**
	 * Basic case.
	 *
	 * @return the i power list
	 */
	@Override
	public IPowerList<T> basic_case() {
		System.out.println("in LFScan basic case " + power_arg.get(0));
		IPowerList s = power_arg.get(0);

		if (s.getValue() instanceof types.IBasicList) {
			System.out.println("in LFScan basic case -is BAsicLIst()" + s.getValue());
			types.IBasicList<T> list = (types.IBasicList<T>) s.getValue();

			basic.functions.Scan<T> rf = new basic.functions.Scan<T>(this.op, list, list);
			Object result = rf.compute();
			s.setValue(result);
			System.out.println("in LFScan basic case result " + s.getValue());
		}
		return s;
	}

}