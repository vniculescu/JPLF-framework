package P.Functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import P.TiePList;
import P.ZipPList;
import execution.FJ_PFunctionExecutor;
import jplf.factory.PFunctionFactory;
import jplf.factory.PListFactory;
import utils.Matrix;
import utils.ParesUtils;
import utils.Real;
import utils.ResultBuilder;

public class PMapTest {

	private int LIMIT = 1000;
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

	@AfterEach
	public void tearDown() {
		inputDataReal = null;
		inputDataMatrix = null;
		inputDataComplex = null;
	}

	@Test
	public void testPMapTieAddReal() {

		List<Real> result = getResultTie(inputDataReal, t -> t.add(t));
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPMapZipAddReal() {

		List<Real> result = getResultZip(inputDataReal, t -> t.add(t));
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapTieAddReal() {

		List<Real> result = getResultTieDistrib(inputDataReal, t -> t.add(t), 2);
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPMapTieMultiplyMatrix() {

		List<Matrix> result = getResultTie(inputDataMatrix, t -> t.multiply(t));
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPMapTieAddMatrix() {

		List<Matrix> result = getResultTie(inputDataMatrix, t -> t.add(t));
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPMapTieMultiplyComplex() {

		List<Complex> result = getResultTie(inputDataComplex, t -> t.multiply(t));
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPMapTieAddComplex() {

		List<Complex> result = getResultTie(inputDataComplex, t -> t.add(t));
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPMapZipMultiplyReal() {

		List<Real> result = getResultZip(inputDataReal, t -> t.multiply(t));
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPMapZipMultiplyMatrix() {

		List<Matrix> result = getResultZip(inputDataMatrix, t -> t.multiply(t));
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPMapTieMultiplyReal() {

		List<Real> result = getResultTie(inputDataReal, t -> t.multiply(t));
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPMapZipAddMatrix() {

		List<Matrix> result = getResultZip(inputDataMatrix, t -> t.add(t));
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPMapZipMultiplyComplex() {

		List<Complex> result = getResultZip(inputDataComplex, t -> t.multiply(t));
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPMapZipAddComplex() {

		List<Complex> result = getResultZip(inputDataComplex, t -> t.add(t));
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapTieMultiplyReal() {

		List<Real> result = getResultTieDistrib(inputDataReal, t -> t.multiply(t), 2);
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapTieMultiplyMatrix() {

		List<Matrix> result = getResultTieDistrib(inputDataMatrix, t -> t.multiply(t), 2);
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapTieAddMatrix() {

		List<Matrix> result = getResultTieDistrib(inputDataMatrix, t -> t.add(t), 2);
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapTieMultiplyComplex() {

		List<Complex> result = getResultTieDistrib(inputDataComplex, t -> t.multiply(t), 2);
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapTieAddComplex() {

		List<Complex> result = getResultTieDistrib(inputDataComplex, t -> t.add(t), 2);
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapZipMultiplyReal() {

		List<Real> result = getResultZipDistrib(inputDataReal, t -> t.multiply(t), 2);
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapZipAddReal() {

		List<Real> result = getResultZipDistrib(inputDataReal, t -> t.add(t), 2);
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapZipMultiplyMatrix() {

		List<Matrix> result = getResultZipDistrib(inputDataMatrix, t -> t.multiply(t), 2);
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapZipAddMatrix() {

		List<Matrix> result = getResultZipDistrib(inputDataMatrix, t -> t.add(t), 2);
		List<Matrix> expected = getExpectedResult(inputDataMatrix, t -> t.add(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapZipMultiplyComplex() {

		List<Complex> result = getResultZipDistrib(inputDataComplex, t -> t.multiply(t), 2);
		List<Complex> expected = getExpectedResult(inputDataComplex, t -> t.multiply(t));
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapZipAddComplex() {

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
		PListFactory<T> pfactory = new PListFactory<T>();
		TiePList<T> list = pfactory.toTiePList(inputList);
		PFunctionFactory<T> pFunctionFactory = new PFunctionFactory<T>();
		FJ_PFunctionExecutor<T> exec = new FJ_PFunctionExecutor<T>(pFunctionFactory.map(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	private <T> List<T> getResultTieDistrib(List<T> inputList, Function<T, T> simpleFunction, int distrib) {
		PListFactory<T> pfactory = new PListFactory<T>();
		TiePList<T> list = pfactory.toTieDistributedPList(inputList, distrib);
		PFunctionFactory<T> pFunctionFactory = new PFunctionFactory<T>();
		FJ_PFunctionExecutor<T> exec = new FJ_PFunctionExecutor<T>(pFunctionFactory.map(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	private <T> List<T> getResultZip(List<T> inputList, Function<T, T> simpleFunction) {
		PListFactory<T> pfactory = new PListFactory<T>();
		ZipPList<T> list = pfactory.toZipPList(inputList);
		PFunctionFactory<T> pFunctionFactory = new PFunctionFactory<T>();
		FJ_PFunctionExecutor<T> exec = new FJ_PFunctionExecutor<T>(pFunctionFactory.map(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	private <T> List<T> getResultZipDistrib(List<T> inputList, Function<T, T> simpleFunction, int distrib) {
		PListFactory<T> pfactory = new PListFactory<T>();
		ZipPList<T> list = pfactory.toZipDistributedPList(inputList, distrib);
		PFunctionFactory<T> pFunctionFactory = new PFunctionFactory<T>();
		FJ_PFunctionExecutor<T> exec = new FJ_PFunctionExecutor<T>(pFunctionFactory.map(simpleFunction, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	public static void main(String[] args) {
		
		int limit = ParesUtils.getLimitFromgArgs(args);

		List<Matrix> matrixList = new ArrayList<Matrix>();
		for (int i = 0; i < limit; i++) {
			matrixList.add(new Matrix(10, i));
		}
		
		PListFactory<Matrix> pfactory = new PListFactory<Matrix>();
		TiePList<Matrix> list = pfactory.toTieDistributedPList(matrixList, 7);
		PFunctionFactory<Matrix> pFunctionFactory = new PFunctionFactory<Matrix>();
		FJ_PFunctionExecutor<Matrix> exec = new FJ_PFunctionExecutor<Matrix>(pFunctionFactory.map(a -> a.add(a), list));
		ResultBuilder<Matrix> rb = new ResultBuilder<Matrix>(exec);
		rb.computeResult();
		System.err.println(rb.getResultList());
		
	}
}
