package usability.tests.multithreading.reduce.preformance.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import P.ZipPList;
import execution.FJ_PFunctionExecutor;
import jplf.factory.PFunctionFactory;
import jplf.factory.PListFactory;
import utils.Matrix;
import utils.ParesUtils;

public class TestReduceMatrixPZipD {
	public static void main(String[] args) throws IOException {

		final int limit = ParesUtils.getLimitFromgArgs(args);
		List<Matrix> matrixList = new ArrayList<Matrix>();
		for (int i = 0; i < limit; i++) {
			matrixList.add(new Matrix(50));
		}
		PListFactory<Matrix> pFactory = new PListFactory<Matrix>();
		ZipPList<Matrix> list = pFactory.toZipDistributedPList(matrixList, 50);
		PFunctionFactory<Matrix> pfunctionFactory = new PFunctionFactory<Matrix>();
		FJ_PFunctionExecutor<Matrix> exec = new FJ_PFunctionExecutor<Matrix>(
				pfunctionFactory.reduce((a, b) -> a.multiply(b), list));
		long timei = System.nanoTime();
		exec.compute();
		long timef = System.nanoTime();
		ParesUtils.log(TestReduceMatrixPZipD.class.getSimpleName() + Integer.toString(limit),
				Long.toString(timef - timei));
	}
}
