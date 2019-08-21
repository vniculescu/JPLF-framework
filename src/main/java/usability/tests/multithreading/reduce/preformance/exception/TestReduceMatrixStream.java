package usability.tests.multithreading.reduce.preformance.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.Matrix;
import utils.ParesUtils;

public class TestReduceMatrixStream {
	public static void main(String[] args) throws IOException {

		final int limit = ParesUtils.getLimitFromgArgs(args);
		List<Matrix> RealList = new ArrayList<Matrix>();
		for (int i = 0; i < limit ; i++) {
			RealList.add(new Matrix(50));
		}

		long timei = System.nanoTime();
		RealList.stream().reduce(new Matrix(50), (a, b) -> a.multiply(b));
		long timef = System.nanoTime();
		ParesUtils.log(TestReduceMatrixStream.class.getSimpleName() + Integer.toString(limit),
				Long.toString(timef - timei));
	}
}
