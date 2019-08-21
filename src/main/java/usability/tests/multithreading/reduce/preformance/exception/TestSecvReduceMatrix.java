package usability.tests.multithreading.reduce.preformance.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.Matrix;
import utils.ParesUtils;

public class TestSecvReduceMatrix {
	public static void main(String[] args) throws IOException {

		final int limit = ParesUtils.getLimitFromgArgs(args);
		List<Matrix> matrixList = new ArrayList<Matrix>();
		for (int i = 0; i < limit; i++) {
			matrixList.add(new Matrix(50));
		}

		long timei = System.nanoTime();
		Matrix result = new Matrix(50);
		for (int i = 0; i < matrixList.size(); i++) {
			result = result.multiply(matrixList.get(i));
		}
		long timef = System.nanoTime();
		ParesUtils.log(TestSecvReduceMatrix.class.getSimpleName() + Integer.toString(limit),
				Long.toString(timef - timei));
	}
}
