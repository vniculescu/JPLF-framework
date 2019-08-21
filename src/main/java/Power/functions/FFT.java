package Power.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;

import org.apache.commons.math3.complex.Complex;

import Power.PowerList;
import Power.PowerResultFunction;
import Power.TiePowerList;
import Power.ZipPowerList;
import basic.BasicList;
import basic.MyArrayList;
import types.AList;
import types.IBasicList;
import types.IPowerList;

/**
 * FFT derives from the raw type PowerResultFunction just to assure that it
 * could be also used when the input list is a distributed (DepthList) list.
 */
public class FFT extends PowerResultFunction {

	/** The eps. */
	protected static double eps = 1.0E-15;

	/** The recursion maxim. */
	protected int recursionMaxim = 1 << 2;

	/** The executor. */
	protected ForkJoinPool executor = null;
//	protected PowerFunctionExecution<Complex> exec = null;
//parallel intrisec computation for combine function

	/**
	 * Instantiates a new fft.
	 *
	 * @param list           the list
	 * @param result         the result
	 * @param recursionMaxim the recursion maxim
	 * @param executor       the executor
	 */
	public FFT(IPowerList list, IPowerList result, int recursionMaxim, ForkJoinPool executor) {
//		pre: list: ZipPowerList 
//		pre: result: TiePowerList 
		super(result, list);
		this.executor = executor;
		this.recursionMaxim = recursionMaxim;
	}

	/**
	 * Instantiates a new fft.
	 *
	 * @param list   the list
	 * @param result the result
	 */
	public FFT(IPowerList list, IPowerList result) {
//		pre: list: ZipPowerList 
//		pre: result: TiePowerList 
		super(result, list);
	}

	/**
	 * Gets the powers.
	 *
	 * @param len the len
	 * @return the powers
	 */
	PowerList<Complex> getPowers(int len) {
		/*
		 * this function sequentially creates the list of the powers of the principal
		 * root of order len of the unity powers go from 0..len/2+1 a
		 * PowerResultFunction could be used instead
		 */
		AList<Complex> roots = new MyArrayList<Complex>();
		for (int i = 0; i < len / 2 + 1; i++) {
			// compute root of unity
			double x = Math.cos(-2.0 * Math.PI * i / len);
			double y = Math.sin(-2.0 * Math.PI * i / len);

			if (Math.abs(x) < eps)
				x = 0;
			if (Math.abs(y) < eps)
				y = 0;
			roots.add(new Complex(x, y));
		}

		if (len > 1)
			return new TiePowerList<Complex>(roots, 0, len / 2 - 1, 1, 0);
		else
			return new TiePowerList<Complex>(roots, 0, 1, 1, 0);
	}

	/**
	 * Basic case.
	 *
	 * @return the i power list
	 */
	@Override
	public IPowerList<Complex> basic_case() {

		IPowerList s0 = ((IPowerList) power_arg.get(0));
//		System.out.println(s0.getValue());	
		if ((s0.getValue() instanceof IBasicList) || (s0.getValue() instanceof PowerList)) {
//			IBasicList list = (IBasicList)s0.getValue() ;
//			IBasicList result_list = (IBasicList)power_result.getValue() ;
//			basic.functions.FFT rf = new basic.functions.FFT(list, result_list);

			ZipPowerList list = (ZipPowerList) ((BasicList) s0.getValue()).toPowerList().toZipPowerList();
			TiePowerList result_list = (TiePowerList) ((BasicList) power_result.getValue()).toPowerList()
					.toTiePowerList();
			Power.functions.FFT rf = new Power.functions.FFT(list, result_list);
			rf.compute();
		} else
			power_result.setValue(s0.getValue());

		return power_result;

	}

	/**
	 * Combine.
	 *
	 * @param left  the left
	 * @param right the right
	 * @return the i power list
	 * @throws IllegalArgumentException the illegal argument exception
	 */
////////////////////////////////////////////////////////////////////////////
	@Override
	public IPowerList<Complex> combine(Object left, Object right) throws IllegalArgumentException {
		// pre: l.getBase()= r.getBase() and they are compatible
		// pre: l.getBase()= r.getBase() and they are compatible
		// pre: l= FFT(left) , r =FFT(right)
		super.combine(left, right);

		int n = power_result.getDepthLen();
		int nn = power_result.getLen();
//		System.out.println("n="+n+" nn="+nn);
		PowerList z = getPowers(nn);

		if (n != nn) {
			basic.Transformer<Complex> t = new basic.Transformer<Complex>();
			z = (TiePowerList) (t.toTieDepthList(z, right_result.getLen())).toPowerList().toTiePowerList();
		}

		/**
		 * fft(left#right) = [fft(left) + fft(right) * z] | [fft(left) - fft(right) * z]
		 */

		PowerAsocBinOperator times = new PowerAsocBinOperator((a, b) -> ((Complex) a).multiply((Complex) b), z,
				right_result, z);
		times.compute();/// !!!!!!!!!!!!!!!!

		PowerAsocBinOperator minus, plus;
		List<BiFunction<Complex, Complex, Complex>> ops = new ArrayList<>();
		ops.add((a, b) -> a.subtract(b));
		ops.add((a, b) -> a.add(b));
		/*
		 * the next section should be used only if we want to stop the level of
		 * recursion!
		 */

//		if (executor !=null && left_result.getLen() > recursionMaxim){
//			Transformer<Complex> t = new Transformer<Complex>();
//			
//			IPowerList<PowerList<Complex>> dleft = t.toTieDepthPowerList((PowerList<Complex>)left_result, recursionMaxim);//.toPowerList().toZipPowerList();
//			IPowerList<PowerList<Complex>> dright = t.toTieDepthPowerList((PowerList<Complex>)right_result, recursionMaxim);//.toPowerList().toZipPowerList();
//			IPowerList<PowerList<Complex>> dz = t.toTieDepthPowerList(z, recursionMaxim);
//			
////			minus  = 	new PowerAsocBinOperator (new MinusOperator(), dleft, dz, dright);
////			plus = 		new PowerAsocBinOperator (new SumOperator(),   dleft, dz, dleft);
//			
//			List<IPowerList> results = new ArrayList<IPowerList>();
//			results.add(dright);
//			results.add(dleft);
//			MPowerAsocBinOperator minus_plus = 	new MPowerAsocBinOperator(ops, dleft, dz,  results);
//			minus_plus.compute();
//			
//			//	exec = 	new PowerFunctionExecution<Complex>(minus_plus);
//			//	executor.invoke(exec);
////				exec = 	new PowerFunctionExecution<Complex>(plus);
////				executor.invoke(exec);
//				//exec=null;
//		}
//		else
		{
//			minus = 	new PowerAsocBinOperator<Complex> (new MinusOperator<Complex>(),	
//					left_result , z,  right_result);	
//			plus  = 	new PowerAsocBinOperator<Complex> (new SumOperator<Complex>(), 		
//					left_result , z,  left_result);
//			minus.compute();///!!!!!!!!!!!!!!!
//			plus.compute();///!!!!!!!!!!!!!!!!
			// System.out.println("before minus_plus"+power_result+"z="+z);

			List<IPowerList<Complex>> results = new ArrayList<IPowerList<Complex>>();
			results.add(right_result);
			results.add(left_result);
			MPowerAsocBinOperator<Complex> minus_plus = new MPowerAsocBinOperator<Complex>(ops, left_result, z,
					results);
			minus_plus.compute();
//			System.out.println("after minus_plus"+power_result);
		}

		return power_result;
	}

	/**
	 * Creates the left function.
	 *
	 * @return the power result function
	 */
	public PowerResultFunction create_left_function() {
		return new FFT((IPowerList<Complex>) left_power_arg.get(0), left_result);
	}

	/**
	 * Creates the right function.
	 *
	 * @return the power result function
	 */
	public PowerResultFunction create_right_function() {
		return new FFT((IPowerList<Complex>) right_power_arg.get(0), right_result);
	}
}
