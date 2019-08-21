package Power.Functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

class PowerMapTest {

	private int LIMIT = 1 << 10;
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
	public void testPowerMapTieMultiplyReal() {

		List<Real> result = getResultTie(inputDataReal, t -> t.multiply(t));
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerMapTieAddReal() {

		List<Real> result = getResultTie(inputDataReal, t -> t.add(t));
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerMapTieMultiplyMatrix() {

		List<Matrix> result = getResultTie(inputDataMatrix, t -> t.multiply(t));
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerMapTieAddMatrix() {

		List<Matrix> result = getResultTie(inputDataMatrix, t -> t.add(t));
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerMapTieMultiplyComplex() {

		List<Complex> result = getResultTie(inputDataComplex, t -> t.multiply(t));
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerMapTieAddComplex() {

		List<Complex> result = getResultTie(inputDataComplex, t -> t.add(t));
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerMapZipMultiplyReal() {

		List<Real> result = getResultZip(inputDataReal, t -> t.multiply(t));
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerMapZipAddReal() {

		List<Real> result = getResultZip(inputDataReal, t -> t.add(t));
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerMapZipMultiplyMatrix() {

		List<Matrix> result = getResultZip(inputDataMatrix, t -> t.multiply(t));
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerMapZipAddMatrix() {

		List<Matrix> result = getResultZip(inputDataMatrix, t -> t.add(t));
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerMapZipMultiplyComplex() {

		List<Complex> result = getResultZip(inputDataComplex, t -> t.multiply(t));
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPowerMapZipAddComplex() {

		List<Complex> result = getResultZip(inputDataComplex, t -> t.add(t));
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerMapTieMultiplyReal() {

		List<Real> result = getResultTieDistrib(inputDataReal, t -> t.multiply(t), 2);
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerMapTieAddReal() {

		List<Real> result = getResultTieDistrib(inputDataReal, t -> t.add(t), 2);
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerMapTieMultiplyMatrix() {

		List<Matrix> result = getResultTieDistrib(inputDataMatrix, t -> t.multiply(t), 2);
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerMapTieAddMatrix() {

		List<Matrix> result = getResultTieDistrib(inputDataMatrix, t -> t.add(t), 2);
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerMapTieMultiplyComplex() {

		List<Complex> result = getResultTieDistrib(inputDataComplex, t -> t.multiply(t), 2);
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerMapTieAddComplex() {

		List<Complex> result = getResultTieDistrib(inputDataComplex, t -> t.add(t), 2);
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerMapZipMultiplyReal() {

		List<Real> result = getResultZipDistrib(inputDataReal, t -> t.multiply(t), 2);
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerMapZipAddReal() {

		List<Real> result = getResultZipDistrib(inputDataReal, t -> t.add(t), 2);
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerMapZipMultiplyMatrix() {

		List<Matrix> result = getResultZipDistrib(inputDataMatrix, t -> t.multiply(t), 2);
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerMapZipAddMatrix() {

		List<Matrix> result = getResultZipDistrib(inputDataMatrix, t -> t.add(t), 2);
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerMapZipMultiplyComplex() {

		List<Complex> result = getResultZipDistrib(inputDataComplex, t -> t.multiply(t), 2);
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPowerMapZipAddComplex() {

		List<Complex> result = getResultZipDistrib(inputDataComplex, t -> t.add(t), 2);
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	private <T> List<T> getExpectedResult(List<T> inputList, Function<T, T> simpleFunction) {
		List<T> result = new ArrayList<T>();
		for (T number : inputList) {
			result.add(simpleFunction.apply(number));
		}
		return result;
	}

	private <T> List<T> getResultTie(List<T> inputList, Function<T, T> simpleFunction) {
		PowerListFactory<T> pfactory = new PowerListFactory<T>();
		TiePowerList<T> list = pfactory.toTiePowerList(inputList);
		PowerFunctionFactory<T> pFunctionFactory = new PowerFunctionFactory<T>();
		FJ_PowerFunctionExecutor<T> exec = new FJ_PowerFunctionExecutor<T>(pFunctionFactory.map(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	private <T> List<T> getResultZip(List<T> inputList, Function<T, T> simpleFunction) {
		PowerListFactory<T> pfactory = new PowerListFactory<T>();
		ZipPowerList<T> list = pfactory.toZipPowerList(inputList);
		PowerFunctionFactory<T> pFunctionFactory = new PowerFunctionFactory<T>();
		FJ_PowerFunctionExecutor<T> exec = new FJ_PowerFunctionExecutor<T>(pFunctionFactory.map(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	private <T> List<T> getResultTieDistrib(List<T> inputList, Function<T, T> simpleFunction, int distrib) {
		PowerListFactory<T> pfactory = new PowerListFactory<T>();
		TiePowerList<T> list = pfactory.toTieDistributedPowerList(inputList, distrib);
		PowerFunctionFactory<T> pFunctionFactory = new PowerFunctionFactory<T>();
		FJ_PowerFunctionExecutor<T> exec = new FJ_PowerFunctionExecutor<T>(pFunctionFactory.map(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	private <T> List<T> getResultZipDistrib(List<T> inputList, Function<T, T> simpleFunction, int distrib) {
		PowerListFactory<T> pfactory = new PowerListFactory<T>();
		ZipPowerList<T> list = pfactory.toZipDistributedPowerList(inputList, distrib);
		PowerFunctionFactory<T> pFunctionFactory = new PowerFunctionFactory<T>();
		FJ_PowerFunctionExecutor<T> exec = new FJ_PowerFunctionExecutor<T>(pFunctionFactory.map(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}
}
