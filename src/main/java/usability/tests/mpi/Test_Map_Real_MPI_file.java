package usability.tests.mpi;

import java.util.concurrent.ForkJoinPool;

import Power.PowerResultFunction;
import Power.ZipPowerList;
import basic.BasicList;
import basic.BasicListResultFunction;
import basic.MyArrayList;
import mpi.MPI;
import mpi_ct.MPI_CTOperations;
import mpi_ct.MPI_PowerCT_read;
import mpi_ct.MPI_PowerResultCT_write;
import mpi_ct.MPI_PowerResultFunctionCT;
import utils.ByteSerialization;
import utils.Real;

public class Test_Map_Real_MPI_file {
	public static void main(String[] args) throws Exception {
		MPI.Init(args);
		int nrProces = MPI.COMM_WORLD.Rank();
		int nrProcese = MPI.COMM_WORLD.Size();
//init
		int limit = 1 << 4;
		int n = limit / nrProcese;
		// each process will work with list of n elements
		MyArrayList<Real> base1 = new MyArrayList<Real>(n);
		MyArrayList<Real> base2 = new MyArrayList<Real>(n);

		int elem_size = ByteSerialization.byte_serialization_len(new Real(0));

// create the lists which represent arguments of the function map	
		for (int i = 0; i < n; i++) {
			base1.add(new Real(i + nrProces * n));
		}
		ZipPowerList<Real> pow_list = new ZipPowerList<Real>(base1, 0, base1.size() - 1, 1, 0);
		PowerResultFunction<Real> mf = new Power.functions.Map<Real>(t -> t.multiply(t), pow_list);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////			
//MPIExecution
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

		int[] sizes = new int[1];
		sizes[0] = n;
		int[] elem_sizes = new int[1];
		elem_sizes[0] = elem_size;
		String[] files = new String[1];
		files[0] = "src/main/resources/date_real.in";

		MPI_CTOperations<Real> exec = new MPI_PowerResultCT_write<Real>(
				new MPI_PowerCT_read<Real>(new MPI_PowerResultFunctionCT<Real>(mf, 
						new ForkJoinPool(4)), files, sizes,	elem_sizes),
				"src/main/resources/date_real.out");

		long timei = System.nanoTime();
		Object result = exec.compute();
		long timef = System.nanoTime();

		// printing the result and the performance
		/*
		 * the final result is computed in only one process -> the last one!
		 */
		System.out.println(nrProces + "sublist  =  " + result);

		if (nrProces == 0) {

			System.out.println("\n timp paralel    = " + (timef - timei));

		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//sequential computation	- just to verify the performance	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (nrProces == 0) {
			for (int i = 0; i < limit; i++) {
				base2.add(new Real(i));
			}
			// define the list
			BasicList<Real> list = new BasicList<Real>(base2, 0, limit - 1);
			// define the function
			BasicListResultFunction<Real> bmf = new basic.functions.Map<Real>(t -> t.multiply(t), list);

			// compute map
			timei = System.nanoTime();
			result = bmf.compute();
			timef = System.nanoTime();
//				 System.out.println("s = "+result);
			System.out.println("sequential time =  " + (timef - timei));
		}
		MPI.Finalize();
	}
}
