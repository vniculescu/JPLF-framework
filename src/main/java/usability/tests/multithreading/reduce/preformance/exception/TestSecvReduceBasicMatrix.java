package usability.tests.multithreading.reduce.preformance.exception;

import java.io.IOException;

import basic.BasicList;
import basic.MyArrayList;
import basic.functions.Reduce;
import utils.Matrix;
import utils.ParesUtils;

public class TestSecvReduceBasicMatrix {
	public static void main(String[] args) throws IOException {

		final int limit = ParesUtils.getLimitFromgArgs(args);
		MyArrayList<Matrix> matrixList = new MyArrayList<Matrix>();
		for (int i = 0; i < limit; i++) {
			matrixList.add(new Matrix(50));
		}

		BasicList<Matrix> list = new BasicList<Matrix>(matrixList, 0, limit - 1);
		Reduce<Matrix> bmf = new basic.functions.Reduce<Matrix>((a, b) -> a.multiply(b), list);

		long timei = System.nanoTime();
		bmf.compute();
		long timef = System.nanoTime();
		ParesUtils.log(TestSecvReduceBasicMatrix.class.getSimpleName() + Integer.toString(limit),
				Long.toString(timef - timei));
	}
}
