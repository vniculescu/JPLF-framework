package Power.Functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Power.PowerList;
import Power.functions.FFT;
import execution.FJ_PowerFunctionExecutor;
import jplf.factory.PowerFunctionFactory;
import jplf.factory.PowerListFactory;
import utils.ResultBuilder;

class FFTTest {
	private int LIMIT = 1 << 10;
	private List<Complex> inputDataComplex;
	private List<Complex> result;

	@BeforeEach
	public void setUp() {
		inputDataComplex = new ArrayList<Complex>();
		result = new ArrayList<Complex>();
		for (int i = 0; i < LIMIT; i++) {
			inputDataComplex.add(new Complex(i + 1, 0));
			result.add(new Complex(1, 0));
		}
	}

	@Test
	public void testFFT() {

		List<Complex> result = getResult();
		List<Complex> expected = getExpectedResult();
		Assertions.assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> getExpectedResult() {
		PowerListFactory<Complex> pfactory = new PowerListFactory<Complex>();
		PowerFunctionFactory<Complex> pFunctionFactory = new PowerFunctionFactory<Complex>();
		FFT form = pFunctionFactory.fft(pfactory.toZipPowerList(inputDataComplex), pfactory.toTiePowerList(result));
		PowerList<T> res = (PowerList<T>) form.compute();
		return Arrays.asList(res.getStorage());
	}

	private List<Complex> getResult() {
		PowerListFactory<Complex> pfactory = new PowerListFactory<Complex>();
		PowerFunctionFactory<Complex> pFunctionFactory = new PowerFunctionFactory<Complex>();
		FFT form = pFunctionFactory.fft(pfactory.toZipPowerList(inputDataComplex), pfactory.toTiePowerList(result));
		FJ_PowerFunctionExecutor<Complex> exec = new FJ_PowerFunctionExecutor<Complex>(form);

		ResultBuilder<Complex> rb = new ResultBuilder<Complex>(exec);
		rb.computeResult();
		return rb.getResultList();
	}
}
