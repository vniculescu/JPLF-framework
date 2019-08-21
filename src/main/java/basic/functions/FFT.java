package basic.functions;

import org.apache.commons.math3.complex.Complex;

import basic.BasicListResultFunction;
import types.IBasicList;

/**
 * The Class FFT. This function computes sequentially the Fast Fourier Transform
 * on a given PowerList that contains Complex numbers
 */
public class FFT extends BasicListResultFunction<Complex> {

	/**
	 * Instantiates a new fft.
	 *
	 * @param list   the list
	 * @param result the result
	 */
	public FFT(IBasicList<Complex> list, IBasicList<Complex> result) {
		super(result, list);
	}

	/**
	 * Val pol.
	 *
	 * @param list the list
	 * @param x    the x
	 * @return the complex
	 */
	private Complex valPol(IBasicList<Complex> list, Complex x) {
		Complex v = new Complex(0, 0);
		Complex y = new Complex(1, 0);
		Complex z;
		// System.out.println("in basiclist valpol =list"+list+"in pct="+x);
		for (int i = list.getStart(); i <= list.getEnd(); i += list.getIncr()) {
			z = y.multiply(list.getValue(i));
			v = v.add(z); // v=v+y*a[i]
			y = y.multiply(x); // y=y*x
		}
		return v;
	}

	/**
	 * Compute.
	 *
	 * @return the i basic list
	 */
	public IBasicList<Complex> compute() {
		double eps = 1.0E-15;
		int len = this.list_result.getLen();
		// List<Complex> roots = new ArrayList<Complex>();
		// Complex unu = new Complex(1,0);
		// roots = unu.nthRoot(len);
		// System.out.println("unity roots:"+roots);
		for (int i = 0; i < len; i++) {
			// compute root of unity
			double x = Math.cos(-2.0 * Math.PI * i / len);
			double y = Math.sin(-2.0 * Math.PI * i / len);
			if (Math.abs(x) < eps)
				x = 0;
			if (Math.abs(y) < eps)
				y = 0;
			Complex x1 = new Complex(x, y);
			// Complex xx = roots.get(i);
			Complex v = valPol(this.list_arg.get(0), x1);
			list_result.setValue(v, list_result.getStartPos() + i * list_result.getIncr());
		}

		return list_result;
	}

}
