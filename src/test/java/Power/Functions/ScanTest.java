package Power.Functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import execution.FJ_PowerFunctionExecutor;
import jplf.factory.PowerFunctionFactory;
import jplf.factory.PowerListFactory;
import utils.Real;
import utils.ResultBuilder;

class ScanTest {

	private int LIMIT = 1 << 10;
	private List<Real> inputDataReal;
	private List<Real> inputResult;

	@BeforeEach
	public void setUp() {
		inputDataReal = new ArrayList<Real>();
		inputResult = new ArrayList<Real>();
		for (int i = 1; i <= LIMIT; i++) {
			inputDataReal.add(new Real(i));
			inputResult.add(new Real(0));
		}
	}

	@AfterEach
	public void tearDwon() {
		inputDataReal = null;
		inputResult = null;
	}

	@Test
	public void testPowerMapTieAddReal() {

		List<Real> result = getResultTie(inputDataReal, inputResult, (a, b) -> a.add(b));
		List<Real> expected = getExpectedResult(inputDataReal, (a, b) -> a.add(b));
		Assertions.assertEquals(expected, result);
	}

	private <T> List<T> getExpectedResult(List<T> inputList, BiFunction<T, T, T> func) {
		List<T> result = new ArrayList<T>();
		result.add(inputList.get(0));
		for (int i = 1; i < inputList.size(); i++) {
			result.add(func.apply(result.get(i - 1), inputList.get(i)));
		}
		return result;
	}

	private <T> List<T> getResultTie(List<T> inputList, List<T> result, BiFunction<T, T, T> func) {
		PowerListFactory<T> pfactory = new PowerListFactory<T>();
		PowerFunctionFactory<T> pFunctionFactory = new PowerFunctionFactory<T>();
		FJ_PowerFunctionExecutor<T> exec = new FJ_PowerFunctionExecutor<T>(
				pFunctionFactory.scan(func, pfactory.toTiePowerList(inputList), pfactory.toTiePowerList(result)));
		ResultBuilder<T> rb = new ResultBuilder<T>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

}
