package usability.tests.multithreading.map;

import java.io.IOException;

import Power.PowerResultFunction;
import Power.TiePowerList;
import Power.functions.Map;
import basic.BasicList;
import basic.BasicListResultFunction;
import basic.MyArrayList;
import execution.FJ_PowerFunctionExecutor;
import utils.Real;

public class Test_Map_Real {
	public static void main(String[] args) throws IOException {

		int limit = 1 << 5;
		MyArrayList<Real> base1 = new MyArrayList<Real>(limit);

		for (int i = 0; i < limit; i++) {
			base1.add(new Real(i));
		}

//define the list
		BasicList<Real> list = new BasicList<Real>(base1, 0, limit - 1);
//		System.out.println(list);
//define the function
		BasicListResultFunction<Real> bmf = new basic.functions.Map<Real>(t -> t.multiply(t), list);

//compute map
		long timei = System.nanoTime();
		Object result = bmf.compute();
		long timef = System.nanoTime();
//		 System.out.println("s = "+result);
		System.out.println("sequential time =" + (timef - timei));
		MyArrayList<Real> base2 = new MyArrayList<Real>(limit);
		for (int i = 0; i < limit; i++) {
			base2.add(new Real(i));
		}
		TiePowerList<Real> pow_list = new TiePowerList<Real>(base2, 0, base2.size() - 1, 1, 0);
		PowerResultFunction<Real> mf = new Map<Real>(t -> t.multiply(t), pow_list);
		FJ_PowerFunctionExecutor<Real> executor = new FJ_PowerFunctionExecutor<Real>(mf);
		timei = System.nanoTime();
		result = executor.compute();
		timef = System.nanoTime();
//		 System.out.println("p = "+result);
		System.out.println("parallel time =  " + (timef - timei));

	}
}
