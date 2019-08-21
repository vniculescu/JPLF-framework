package Power.Functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Power.TiePowerList;
import Power.ZipPowerList;
import execution.FJ_PowerFunctionExecutor;
import jplf.factory.PowerFunctionFactory;
import jplf.factory.PowerListFactory;
import utils.Matrix;
import utils.Real;
import utils.ResultBuilder;

class PowerReduceTest {

	private int LIMIT = 1 << 6;
	private List<Real> inputDataReal;

	private List<Matrix> inputDataMatrix;

	private List<Complex> inputDataComplex;

	@BeforeEach
	public void setUp() {
		inputDataReal = new ArrayList<Real>();
		inputDataMatrix = new ArrayList<Matrix>();
		inputDataComplex = new ArrayList<Complex>();
		for (int i = 0; i < LIMIT; i++) {
			inputDataReal.add(new Real(i));
			inputDataMatrix.add(new Matrix(10, i));
			inputDataComplex.add(new Complex(i, i));
		}
	}

	@Test
	public void testPowerReduceTieMultiplyReal() {

		Real result = getResultTie(inputDataReal, (a, b) -> a.multiply(b));
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.multiply(b), () -> new Real(1));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerReduceTieAddReal() {

		Real result = getResultTie(inputDataReal, (a, b) -> a.add(b));
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.add(b), () -> new Real(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerReduceTieMultiplyMatrix() {

		Matrix result = getResultTie(inputDataMatrix, (a, b) -> a.multiply(b));
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.multiply(b), () -> new Matrix(10));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerReduceTieAddMatrix() {

		Matrix result = getResultTie(inputDataMatrix, (a, b) -> a.add(b));
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.add(b), () -> new Matrix(10));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerReduceTieMultiplyComplex() {

		Complex result = getResultTie(inputDataComplex, (a, b) -> a.multiply(b));
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.multiply(b), () -> new Complex(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerReduceTieAddComplex() {

		Complex result = getResultTie(inputDataComplex, (a, b) -> a.add(b));
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.add(b), () -> new Complex(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerReduceZipMultiplyReal() {

		Real result = getResultZip(inputDataReal, (a, b) -> a.multiply(b));
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.multiply(b), () -> new Real(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerReduceZipAddReal() {

		Real result = getResultZip(inputDataReal, (a, b) -> a.add(b));
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.add(b), () -> new Real(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerReduceZipMultiplyMatrix() {

		Matrix result = getResultZip(inputDataMatrix, (a, b) -> a.multiply(b));
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.multiply(b), () -> new Matrix(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerReduceZipAddMatrix() {

		Matrix result = getResultZip(inputDataMatrix, (a, b) -> a.add(b));
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.add(b), () -> new Matrix(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerReduceZipMultiplyComplex() {

		Complex result = getResultZip(inputDataComplex, (a, b) -> a.multiply(b));
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.multiply(b), () -> new Complex(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerReduceZipAddComplex() {

		Complex result = getResultZip(inputDataComplex, (a, b) -> a.add(b));
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.add(b), () -> new Complex(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerReduceTieMultiplyReal() {

		Real result = getResultTieDistrib(inputDataReal, (a, b) -> a.multiply(b), 2);
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.multiply(b), () -> new Real(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerReduceTieAddReal() {

		Real result = getResultTieDistrib(inputDataReal, (a, b) -> a.add(b), 2);
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.add(b), () -> new Real(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerReduceTieMultiplyMatrix() {

		Matrix result = getResultTieDistrib(inputDataMatrix, (a, b) -> a.multiply(b), 2);
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.multiply(b), () -> new Matrix(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerReduceTieAddMatrix() {

		Matrix result = getResultTieDistrib(inputDataMatrix, (a, b) -> a.add(b), 2);
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.add(b), () -> new Matrix(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerReduceTieMultiplyComplex() {

		Complex result = getResultTieDistrib(inputDataComplex, (a, b) -> a.multiply(b), 2);
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.multiply(b), () -> new Complex(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerReduceTieAddComplex() {

		Complex result = getResultTieDistrib(inputDataComplex, (a, b) -> a.add(b), 2);
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.add(b), () -> new Complex(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerReduceZipMultiplyReal() {

		Real result = getResultZipDistrib(inputDataReal, (a, b) -> a.multiply(b), 2);
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.multiply(b), () -> new Real(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerReduceZipAddReal() {

		Real result = getResultZipDistrib(inputDataReal, (a, b) -> a.add(b), 2);
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.add(b), () -> new Real(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerReduceZipMultiplyMatrix() {

		Matrix result = getResultZipDistrib(inputDataMatrix, (a, b) -> a.multiply(b), 2);
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.multiply(b), () -> new Matrix(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerReduceZipAddMatrix() {

		Matrix result = getResultZipDistrib(inputDataMatrix, (a, b) -> a.add(b), 2);
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.add(b), () -> new Matrix(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerReduceZipMultiplyComplex() {

		Complex result = getResultZipDistrib(inputDataComplex, (a, b) -> a.multiply(b), 2);
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.multiply(b), () -> new Complex(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerReduceZipAddComplex() {

		Complex result = getResultZipDistrib(inputDataComplex, (a, b) -> a.add(b), 2);
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.add(b), () -> new Complex(0));
		Assertions.assertEquals(expected, result);
	}

	private <T> T getExpectedResult(List<T> inputList, BiFunction<T, T, T> simpleFunction, Supplier<T> sup) {
		T result = sup.get();
		for (T number : inputList) {
			result = simpleFunction.apply(result, number);
		}
		return result;
	}

	private <T> T getResultTie(List<T> inputList, BiFunction<T, T, T> simpleFunction) {
		PowerListFactory<T> pfactory = new PowerListFactory<T>();
		TiePowerList<T> list = pfactory.toTiePowerList(inputList);
		PowerFunctionFactory<T> pFunctionFactory = new PowerFunctionFactory<T>();
		FJ_PowerFunctionExecutor<T> exec = new FJ_PowerFunctionExecutor<T>(
				pFunctionFactory.reduce(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultValue();
	}

	private <T> T getResultZip(List<T> inputList, BiFunction<T, T, T> simpleFunction) {
		PowerListFactory<T> pfactory = new PowerListFactory<T>();
		ZipPowerList<T> list = pfactory.toZipPowerList(inputList);
		PowerFunctionFactory<T> pFunctionFactory = new PowerFunctionFactory<T>();
		FJ_PowerFunctionExecutor<T> exec = new FJ_PowerFunctionExecutor<T>(
				pFunctionFactory.reduce(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultValue();
	}

	private <T> T getResultTieDistrib(List<T> inputList, BiFunction<T, T, T> simpleFunction, int distrib) {
		PowerListFactory<T> pfactory = new PowerListFactory<T>();
		TiePowerList<T> list = pfactory.toTieDistributedPowerList(inputList, distrib);
		PowerFunctionFactory<T> pFunctionFactory = new PowerFunctionFactory<T>();
		FJ_PowerFunctionExecutor<T> exec = new FJ_PowerFunctionExecutor<T>(
				pFunctionFactory.reduce(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultValue();
	}

	private <T> T getResultZipDistrib(List<T> inputList, BiFunction<T, T, T> simpleFunction, int distrib) {
		PowerListFactory<T> pfactory = new PowerListFactory<T>();
		ZipPowerList<T> list = pfactory.toZipDistributedPowerList(inputList, distrib);
		PowerFunctionFactory<T> pFunctionFactory = new PowerFunctionFactory<T>();
		FJ_PowerFunctionExecutor<T> exec = new FJ_PowerFunctionExecutor<T>(
				pFunctionFactory.reduce(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultValue();
	}

}
