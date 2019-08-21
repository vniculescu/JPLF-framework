package usability.tests.multithreading.map;

import java.io.IOException;

import Power.PowerResultFunction;
import Power.TiePowerList;
import Power.functions.Map;
import basic.BasicList;
import basic.BasicListResultFunction;
import basic.MyArrayList;
import execution.FJ_PowerFunctionExecutor;
import utils.Matrix;

public class Test_Map_Matrix_secv {
	public static void main(String[] args) throws IOException {

		int limit = 1 << 4;
		MyArrayList<Matrix> base1 = new MyArrayList<Matrix>(limit);

		for (int i = 0; i < limit; i++) {
			base1.add(new Matrix(Matrix.DIM, i));
		}

//define the list
		BasicList<Matrix> list = new BasicList<Matrix>(base1, 0, limit - 1);
//		System.out.println(list);
//define the function
		BasicListResultFunction<Matrix> bmf = new basic.functions.Map<Matrix>(t -> t.multiply(t), list);

//compute map
		long timei = System.nanoTime();
//		 Object result = bmf.compute();
		long timef = System.nanoTime();
//		 System.out.println("s = "+result);
//		 System.out.println("sequential time ="+(timef-timei));
		MyArrayList<Matrix> base2 = new MyArrayList<Matrix>(limit);
		for (int i = 0; i < limit; i++) {
			base2.add(new Matrix(Matrix.DIM, i));
		}
		TiePowerList<Matrix> pow_list = new TiePowerList<Matrix>(base2, 0, base2.size() - 1, 1, 0);
		PowerResultFunction<Matrix> mf = new Map<Matrix>(t -> t.multiply(t), pow_list);
		timei = System.nanoTime();
		FJ_PowerFunctionExecutor<Matrix> executor = new FJ_PowerFunctionExecutor<Matrix>(mf);
		Object result = executor.compute();
		timef = System.nanoTime();
		System.out.println("p = " + result);
		System.out.println("parallel time =  " + (timef - timei));

	}
}
