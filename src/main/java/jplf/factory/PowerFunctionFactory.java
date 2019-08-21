package jplf.factory;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.math3.complex.Complex;

import Power.PowerFunction;
import Power.TiePowerList;
import Power.ZipPowerList;
import Power.functions.FFT;
import Power.functions.Map;
import Power.functions.Reduce;
import Power.functions.Scan;
import types.IPowerList;
import utils.Real;

/**
 * A factory for creating PowerFunction objects.
 *
 * @param <T> the generic type
 */
public class PowerFunctionFactory<T> {

	/**
	 * Map.
	 *
	 * @param simpleFunction the simple function
	 * @param powerList      the power list
	 * @return the power map
	 */
	public Map<T> map(Function<T, T> simpleFunction, IPowerList<T> powerList) {
		return new Map<T>(simpleFunction, powerList);
	}

	/**
	 * Map.
	 *
	 * @param simpleFunction the simple function
	 * @param powerList      the power list
	 * @param res            the res
	 * @return the power map
	 */
	public Map<T> map(Function<T, T> simpleFunction, IPowerList<T> powerList, IPowerList<T> res) {
		return new Map<T>(simpleFunction, powerList, res);
	}

	/**
	 * Reduce.
	 *
	 * @param operator  the operator
	 * @param powerList the power list
	 * @return the power reduce
	 */
	public Reduce<T> reduce(BiFunction<T, T, T> operator, IPowerList<T> powerList) {
		return new Reduce<T>(operator, powerList);
	}

	/**
	 * Fft.
	 *
	 * @param list   the list
	 * @param result the result
	 * @return the fft
	 */
	public FFT fft(IPowerList<Complex> list, IPowerList<Complex> result) {
		return new FFT(list, result);
	}

	/**
	 * Scan.
	 *
	 * @param op  the op
	 * @param arg the arg
	 * @return the scan
	 */
	public Scan<T> scan(BiFunction<T, T, T> op, IPowerList<T> arg) {
		return new Scan<T>(op, arg);
	}

	/**
	 * Scan.
	 *
	 * @param op   the op
	 * @param list the list
	 * @param res  the res
	 * @return the scan
	 */
	public Scan<T> scan(BiFunction<T, T, T> op, IPowerList<T> list, IPowerList<T> res) {
		return new Scan<T>(op, list, res);
	}

}
