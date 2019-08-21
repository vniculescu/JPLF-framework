package P.Functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import P.TiePList;
import P.ZipPList;
import execution.FJ_PFunctionExecutor;
import jplf.factory.PFunctionFactory;
import jplf.factory.PListFactory;
import utils.Real;
import utils.ResultBuilder;

class PFilterTest {

	private int LIMIT = 1000;
	private List<Real> inputDataReal;

	@BeforeEach
	public void setUp() {
		inputDataReal = new ArrayList<Real>();
		for (int i = 0; i < LIMIT; i++) {
			inputDataReal.add(new Real(i));
		}
	}

	@AfterEach
	public void tearDown() {
		inputDataReal = null;
	}

	@Test
	public void testPMapTieMultiplyReal() {

		List<Real> result = getResultTie(inputDataReal, t -> t.getV() % 2 == 0);
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.getV() % 2 == 0);
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPMapZipAddMatrix() {

		List<Real> result = getResultZip(inputDataReal, t -> t.getV() % 2 == 0);
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.getV() % 2 == 0);
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapTieComplex() {

		List<Real> result = getResultTieDistrib(inputDataReal, t -> t.getV() % 2 == 0, 3);
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.getV() % 2 == 0);
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testDistributedPMapZipMultiplyReal() {

		List<Real> result = getResultZipDistrib(inputDataReal, t -> t.getV() % 2 == 0, 5);
		List<Real> expected = getExpectedResult(inputDataReal, t -> t.getV() % 2 == 0);
		Assertions.assertEquals(expected, result);
	}

	private <T> List<T> getExpectedResult(List<T> inputList, Predicate<T> predicate) {
		List<T> result = new ArrayList<T>();
		for (T number : inputList) {
			if (predicate.test(number)) {
				result.add(number);
			}

		}
		return result;
	}

	private <T> List<T> getResultTie(List<T> inputList, Predicate<T> predicate) {
		PListFactory<T> pfactory = new PListFactory<T>();
		TiePList<T> list = pfactory.toTiePList(inputList);
		PFunctionFactory<T> pFunctionFactory = new PFunctionFactory<T>();
		FJ_PFunctionExecutor<T> exec = new FJ_PFunctionExecutor<T>(pFunctionFactory.filter(predicate, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	private <T> List<T> getResultZip(List<T> inputList, Predicate<T> predicate) {
		PListFactory<T> pfactory = new PListFactory<T>();
		ZipPList<T> list = pfactory.toZipPList(inputList);
		PFunctionFactory<T> pFunctionFactory = new PFunctionFactory<T>();
		FJ_PFunctionExecutor<T> exec = new FJ_PFunctionExecutor<T>(pFunctionFactory.filter(predicate, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	private <T> List<T> getResultTieDistrib(List<T> inputList, Predicate<T> predicate, int distrib) {
		PListFactory<T> pfactory = new PListFactory<T>();
		TiePList<T> list = pfactory.toTieDistributedPList(inputList, distrib);
		PFunctionFactory<T> pFunctionFactory = new PFunctionFactory<T>();
		FJ_PFunctionExecutor<T> exec = new FJ_PFunctionExecutor<T>(pFunctionFactory.filter(predicate, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	private <T> List<T> getResultZipDistrib(List<T> inputList, Predicate<T> predicate, int distrib) {
		PListFactory<T> pfactory = new PListFactory<T>();
		ZipPList<T> list = pfactory.toZipDistributedPList(inputList, distrib);
		PFunctionFactory<T> pFunctionFactory = new PFunctionFactory<T>();
		FJ_PFunctionExecutor<T> exec = new FJ_PFunctionExecutor<T>(pFunctionFactory.filter(predicate, list));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}
}
