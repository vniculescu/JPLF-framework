package usability.tests.mpi;

import java.util.concurrent.ForkJoinPool;

import org.apache.commons.math3.complex.Complex;

import Power.PowerList;
import Power.PowerResultFunction;
import Power.TiePowerList;
import Power.ZipPowerList;
import Power.functions.FFT;
import basic.MyArrayList;
import mpi.MPI;
import mpi_ct.MPI_CTOperations;
import mpi_ct.MPI_PowerResultCT_compose;
import mpi_ct.MPI_PowerResultFunctionCT;
import types.IPowerList;

public class Test_FFT_MPI { 
//////////////////////////////////////////////////////////////
	/*
	 * needed when the split is based on zip
	 * (and combine is based on tie)
	 */
	public static int reverse(int num, int cifre){
		int res = 0, i, temp;
		for (i = 0; i < cifre; i++)
	    {
	        temp = (num & (1 << i));
	        if(temp!=0)
	            res |= (1 << ((cifre - 1) - i));
	    }
		return res;
	}
//////////////////////////////////////////////////////////////
			public static void main(String[] args) throws Exception {
				MPI.Init(args);
				int nrProces = MPI.COMM_WORLD.Rank();
				int nrProcese = MPI.COMM_WORLD.Size();
		//init data
				// each process will work with list of 
				// size = limit/nrProcese
				int limit = 1<<3; 
				int n=limit/nrProcese;
				MyArrayList<Complex> base = new MyArrayList<Complex>(n);
				MyArrayList<Complex> base2 = new MyArrayList<Complex>(n);
				for(int i=0;i<n;i++){
					base.add(	new Complex(i+n*nrProces+1,0)		);
					base2.add(	new Complex(0,0)		);
				}
				// create the lists which represent arguments of the function fft	
				PowerList<Complex> list = new PowerList<Complex>(base, 0, n-1);   //input list (zip)
				PowerList<Complex> rlist = new PowerList<Complex>(base2, 0, n-1); //result list (tie)
//
//				Transformer<Complex> t = new Transformer<Complex>();
//				IPowerList<BasicList<Complex>> dlist = 	 t.toZipDepthList(list, 1<<2);//.toPowerList();
//				IPowerList<BasicList<Complex>> dresult = t.toTieDepthList(rlist, 1<<2).toPowerList();
				// create the	function that compute fft	
					// create the	function that compute fft	
					PowerResultFunction<Complex> mf  = 	new FFT(
															list.toZipPowerList(),  //input list
															rlist.toTiePowerList(), //outputlist
															1<<2, 
															new ForkJoinPool(4));
					long timei = System.nanoTime();
					MPI_CTOperations<Complex> exec = 
//							new MPI_PowerCT_read<Complex>(
								new MPI_PowerResultCT_compose<Complex>(
									new MPI_PowerResultFunctionCT<Complex>(mf)	);
//							                  files, sizes, elem_sizes);

//					Object result = exec.compute();
					long timef = System.nanoTime();
					if (nrProces==nrProcese-1) {
						System.out .println("\n timp paralel    = " + (timef-timei));
//						System.out.println("par  =  "+result);
					}
					timei = System.nanoTime();
					PowerResultFunction<Complex> mf1  = 	new FFT(
							list.toZipPowerList(),  //input list
							rlist.toTiePowerList() //outputlist
//							,1<<2, 
//							new ForkJoinPool(4)
							);
					exec = 
//							new MPI_PowerCT_read<Complex>(
								new MPI_PowerResultCT_compose<Complex>(
									new MPI_PowerResultFunctionCT<Complex>(mf1, 4)	);
//							                  files, sizes, elem_sizes);

					Object result = exec.compute();
					
					 timef = System.nanoTime();
					//printing the result and the performance
					if (nrProces==nrProcese-1) {
						System.out .println("\n timp paralel    = " + (timef-timei));
//						System.out.println("par  =  "+result);
					}
					base=null; base2=null;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//sequential computation
					if (nrProces==nrProcese-1)
						{
						MyArrayList<Complex> base1 = new MyArrayList<Complex>(n);
						MyArrayList<Complex> base3 = new MyArrayList<Complex>(limit);
						for(int i=0;i<limit;i++){
							base1.add(	new Complex(i+1,0)		);
							base3.add(	new Complex(1,  0)		);
						}
//Sequential non-recursive computation - polinomial value computation
//						timei = System.nanoTime();
//						BasicList<Complex> secv_res = new BasicList<Complex>(base3);
//						BasicListResultFunction<Complex>  bf = 
//								new Basic.Functions.FFT( new BasicList<Complex>(base1), secv_res);
//						BasicList<Complex> res = (BasicList<Complex>)bf.compute();
//						timef = System.nanoTime();
//						System.out.println("\n timp secvential = " + (timef-timei));
//						System.out.println("secv =  "+ secv_res);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////			
//Sequential recursive computation - fft on powerlist
						TiePowerList<Complex> r_pl = new TiePowerList<Complex>(base3,0, base3.size()-1, 1,0);  
						ZipPowerList<Complex> pl = new ZipPowerList<Complex> (base1, 0, base1.size()-1,1,0);
						FFT fft = new FFT(pl, r_pl);
						timei = System.nanoTime();
						IPowerList<Complex> s_res = fft.compute();
						timef = System.nanoTime();
						System.out.println("S Power TimeS    = "+(timef-timei));
//						System.out.println("secvRec="+ s_res);
					}
					
					MPI.Finalize();
		}	
	}

