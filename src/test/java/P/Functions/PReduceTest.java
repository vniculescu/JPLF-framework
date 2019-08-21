package P.Functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import P.TiePList;
import P.ZipPList;
import execution.FJ_PFunctionExecutor;
import jplf.factory.PFunctionFactory;
import jplf.factory.PListFactory;
import utils.Matrix;
import utils.Real;
import utils.ResultBuilder;

class PReduceTest {

	private int LIMIT = 50;
	private List<Real> inputDataReal;

	private List<Matrix> inputDataMatrix;

	private List<Complex> inputDataComplex;

	@BeforeEach
	public void setUp() {
		inputDataReal = new ArrayList<Real>();
		inputDataMatrix = new ArrayList<Matrix>();
		inputDataComplex = new ArrayList<Complex>();
		for (int i = 1; i <= LIMIT; i++) {
			inputDataReal.add(new Real(i));
			inputDataMatrix.add(new Matrix(10, i));
			inputDataComplex.add(new Complex(i, i));
		}
	}

	@Test
	public void testPReduceTieMultiplyReal() {

		Real result = getResultTie(inputDataReal, (a, b) -> a.multiply(b));
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.multiply(b), () -> new Real(1));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPReduceTieAddReal() {

		Real result = getResultTie(inputDataReal, (a, b) -> a.add(b));
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.add(b), () -> new Real(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPReduceTieAddMatrix() {

		Matrix result = getResultTie(inputDataMatrix, (a, b) -> a.add(b));
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.add(b), () -> new Matrix(10));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPReduceTieMultiplyComplex() {

		Complex result = getResultTie(inputDataComplex, (a, b) -> a.multiply(b));
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.multiply(b), () -> new Complex(1));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPReduceTieAddComplex() {

		Complex result = getResultTie(inputDataComplex, (a, b) -> a.add(b));
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.add(b), () -> new Complex(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPReduceZipMultiplyReal() {

		Real result = getResultZip(inputDataReal, (a, b) -> a.multiply(b));
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.multiply(b), () -> new Real(1));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPReduceZipAddReal() {

		Real result = getResultZip(inputDataReal, (a, b) -> a.add(b));
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.add(b), () -> new Real(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPReduceZipMultiplyMatrix() {

		Matrix result = getResultZip(inputDataMatrix, (a, b) -> a.multiply(b));
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.multiply(b), () -> new Matrix(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPReduceZipAddMatrix() {

		Matrix result = getResultZip(inputDataMatrix, (a, b) -> a.add(b));
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.add(b), () -> new Matrix(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPReduceZipMultiplyComplex() {

		Complex result = getResultZip(inputDataComplex, (a, b) -> a.multiply(b));
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.multiply(b), () -> new Complex(1));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPReduceZipAddComplex() {

		Complex result = getResultZip(inputDataComplex, (a, b) -> a.add(b));
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.add(b), () -> new Complex(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPReduceTieMultiplyReal() {

		Real result = getResultTieDistrib(inputDataReal, (a, b) -> a.multiply(b), 2);
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.multiply(b), () -> new Real(1));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPReduceTieAddReal() {

		Real result = getResultTieDistrib(inputDataReal, (a, b) -> a.add(b), 2);
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.add(b), () -> new Real(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPReduceTieMultiplyMatrix() {

		Matrix result = getResultTieDistrib(inputDataMatrix, (a, b) -> a.multiply(b), 2);
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.multiply(b), () -> new Matrix(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPReduceTieAddMatrix() {

		Matrix result = getResultTieDistrib(inputDataMatrix, (a, b) -> a.add(b), 2);
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.add(b), () -> new Matrix(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPReduceTieMultiplyComplex() {

		Complex result = getResultTieDistrib(inputDataComplex, (a, b) -> a.multiply(b), 2);
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.multiply(b), () -> new Complex(1));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPReduceTieAddComplex() {

		Complex result = getResultTieDistrib(inputDataComplex, (a, b) -> a.add(b), 2);
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.add(b), () -> new Complex(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPReduceZipMultiplyReal() {

		Real result = getResultZipDistrib(inputDataReal, (a, b) -> a.multiply(b), 2);
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.multiply(b), () -> new Real(1));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPReduceZipAddReal() {

		Real result = getResultZipDistrib(inputDataReal, (a, b) -> a.add(b), 2);
		Real expected = getExpectedResult(inputDataReal, (a, b) -> a.add(b), () -> new Real(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPReduceZipMultiplyMatrix() {

		Matrix result = getResultZipDistrib(inputDataMatrix, (a, b) -> a.multiply(b), 2);
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.multiply(b), () -> new Matrix(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPReduceZipAddMatrix() {

		Matrix result = getResultZipDistrib(inputDataMatrix, (a, b) -> a.add(b), 2);
		Matrix expected = getExpectedResult(inputDataMatrix, (a, b) -> a.add(b), () -> new Matrix(0));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPReduceZipMultiplyComplex() {

		Complex result = getResultZipDistrib(inputDataComplex, (a, b) -> a.multiply(b), 2);
		Complex expected = getExpectedResult(inputDataComplex, (a, b) -> a.multiply(b), () -> new Complex(1));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPReduceZipAddComplex() {

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
		PListFactory<T> pfactory = new PListFactory<T>();
		TiePList<T> list = pfactory.toTiePList(inputList);
		PFunctionFactory<T> pFunctionFactory = new PFunctionFactory<T>();
		FJ_PFunctionExecutor<T> exec = new FJ_PFunctionExecutor<T>(pFunctionFactory.reduce(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultValue();
	}

	private <T> T getResultZip(List<T> inputList, BiFunction<T, T, T> simpleFunction) {
		PListFactory<T> pfactory = new PListFactory<T>();
		ZipPList<T> list = pfactory.toZipPList(inputList);
		PFunctionFactory<T> pFunctionFactory = new PFunctionFactory<T>();
		FJ_PFunctionExecutor<T> exec = new FJ_PFunctionExecutor<T>(pFunctionFactory.reduce(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultValue();
	}

	private <T> T getResultTieDistrib(List<T> inputList, BiFunction<T, T, T> simpleFunction, int distrib) {
		PListFactory<T> pfactory = new PListFactory<T>();
		TiePList<T> list = pfactory.toTieDistributedPList(inputList, distrib);
		PFunctionFactory<T> pFunctionFactory = new PFunctionFactory<T>();
		FJ_PFunctionExecutor<T> exec = new FJ_PFunctionExecutor<T>(pFunctionFactory.reduce(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultValue();
	}

	private <T> T getResultZipDistrib(List<T> inputList, BiFunction<T, T, T> simpleFunction, int distrib) {
		PListFactory<T> pfactory = new PListFactory<T>();
		ZipPList<T> list = pfactory.toZipDistributedPList(inputList, distrib);
		PFunctionFactory<T> pFunctionFactory = new PFunctionFactory<T>();
		FJ_PFunctionExecutor<T> exec = new FJ_PFunctionExecutor<T>(pFunctionFactory.reduce(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultValue();
	}

}
