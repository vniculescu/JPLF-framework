package usability.tests.multithreading.fft;

import org.apache.commons.math3.complex.Complex;

import Power.PowerResultFunction;
import Power.TiePowerList;
import Power.ZipPowerList;
import Power.functions.FFT;
import basic.MyArrayList;
import execution.FJ_PowerFunctionExecutor;
import types.IPowerList;
/*
 * test FFT in one node
 * shared memory
 */
public class TestFFT {
	public static void main(String[] args) {

//init data
		int n = 1<<4	;
		long timei, timef;
		Object result = null;

		MyArrayList<Complex> base1 = new MyArrayList<Complex>(n);
		MyArrayList<Complex> base2 = new MyArrayList<Complex>(n);
		for(int i=0;i< n;i++){
			base1.add(	new Complex(i+1,0)		);
			base2.add(	new Complex(1,0)		);
		}
	
//Sequential recursive computation - fft on powerlist
				ZipPowerList<Complex> pl = new ZipPowerList<Complex> (base1, 0, base1.size()-1,1,0);
				TiePowerList<Complex> r_pl = new TiePowerList<Complex>(base2,0, base2.size()-1, 1,0); 
				FFT fft = new FFT(pl, r_pl);
				timei = System.nanoTime();
				IPowerList<Complex> s_res = fft.compute();
				timef = System.nanoTime();
				System.out.println("S Power TimeS    = "+(timef-timei));
				System.out.println("secvRec="+ s_res);
				
//Parallel recursive computation - ForkJoinPool	

				PowerResultFunction<Complex> mf = new FFT(pl, r_pl);
				FJ_PowerFunctionExecutor<Complex> executor = new FJ_PowerFunctionExecutor<Complex>(mf);
				
				timei = System.nanoTime();
				IPowerList<Complex> p_res = (IPowerList<Complex>)executor.compute();
				timef = System.nanoTime();
				System.out.println("P Power TimeP    = "+(timef-timei));
				System.out.println("parFJ="+ p_res);
	}	
}
