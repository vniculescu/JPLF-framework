package usability.tests.multithreading.fft;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ForkJoinPool;

import org.apache.commons.math3.complex.Complex;

import Power.PowerList;
import Power.TiePowerList;
import Power.ZipPowerList;
import Power.functions.FFT;
import basic.BasicList;
import basic.MyArrayList;
import execution.FJ_PowerFunctionComputationTask;
import types.IPowerList;
import utils.ByteSerialization;
import utils.Real;
//2^15  secvential 5344712662
//                 5165952217
//                 3016050732
//      threads    5107583995
public class Test_FFT_Secv_File {
	public static void main(String[] args) throws IOException {
		int limit =1<<4	;
//		5393182625 2^15
//		3153242451
		
		BasicList<Real> list = null;
		int n=limit;
		MyArrayList<Complex> base = null, base2 =null;
		base2 = new MyArrayList<Complex>(n);
		base = new MyArrayList<Complex>(n);
		int elem_size = ByteSerialization.byte_serialization_len(new Complex(1));
		
		
		long timei = System.nanoTime();
//read data from file
		RandomAccessFile file = new RandomAccessFile("date_complex.in", "r");
		byte elem_bytes[] = new byte[elem_size];
		
		
		  try{							  
			file.seek(0);
			for(int i=0;i<limit;i++)
			{
					for(int j=0;j<elem_size;j++) 
						elem_bytes[j] = file.readByte();
					Complex elem = (Complex)ByteSerialization.byte_deserialization(elem_bytes);
//				Complex elem = new Complex(i+1,0);
					base.add(elem);
//					System.out.println("elem="+elem);		
					base2.add(new Complex(1));
			}
		  }
		catch ( EOFException e){
			System.out.print("EoF read");
		}
		file.close();
		
		TiePowerList<Complex> r_pl = new TiePowerList<Complex>(base2,0, base2.size()-1, 1,0);  
		ZipPowerList<Complex> pl = new ZipPowerList<Complex> (base, 0, base.size()-1,1,0);
		FFT fft = new FFT(pl, r_pl);
		ForkJoinPool executor = ForkJoinPool.commonPool();
		FJ_PowerFunctionComputationTask<Complex> exec = 	new FJ_PowerFunctionComputationTask<Complex>(fft);
		IPowerList<Complex> s_res=(PowerList<Complex>)executor.invoke(exec); //threads
//		IPowerList<Complex> s_res = fft.compute();
		long timef = System.nanoTime();
		System.out.println("S Power TimeS    = "+(timef-timei));
//		System.out.println("secvRec="+ s_res);
}
}