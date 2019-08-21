package P.Functions;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import P.TiePList;
import P.functions.RectangularFormula;
import execution.FJ_PFunctionExecutor;
import jplf.factory.PFunctionFactory;
import jplf.factory.PListFactory;
import utils.Real;
import utils.ResultBuilder;

class RectangularFormulaTest {

	private final int limit = (int) Math.pow(3, 5);
	private List<Real> inputDataReal;
	private Real h;

	@BeforeEach
	public void setUp() {
		inputDataReal = new ArrayList<Real>();
		int a = 0, b = 27;
		double dh = ((double) (b - a)) / limit;
		for (int i = 0; i < limit; i++) {
			Real val = new Real(a + dh * i);
			inputDataReal.add(val.multiply(val));
		}
		h = new Real(dh);
	}

	@Test
	public void testRectangularFormula() {

		Real result = getResult();
		Real expected = getExpectedResult();
		System.err.println(result);
		Assertions.assertEquals(expected, result);
	}

	private <T> T getExpectedResult() {
		PListFactory<Real> pfactory = new PListFactory<Real>();
		TiePList<Real> list = pfactory.toTiePList(inputDataReal);
		PFunctionFactory<Real> pFunctionFactory = new PFunctionFactory<Real>();
		RectangularFormula<Real> form = pFunctionFactory.rectangularFormula(3, h, list);
		return (T) form.compute();
	}

	private Real getResult() {
		PListFactory<Real> pfactory = new PListFactory<Real>();
		TiePList<Real> list = pfactory.toTiePList(inputDataReal);
		PFunctionFactory<Real> pFunctionFactory = new PFunctionFactory<Real>();
		FJ_PFunctionExecutor<Real> exec = new FJ_PFunctionExecutor<Real>(
				pFunctionFactory.rectangularFormula(3, h, list));
		ResultBuilder<Real> rb = new ResultBuilder<Real>(exec);
		rb.computeResult();
		return rb.getResultValue();
	}
}
