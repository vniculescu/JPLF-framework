package usability.tests.mpi;

import java.util.concurrent.ForkJoinPool;

import org.apache.commons.math3.complex.Complex;

import Power.PowerList;
import Power.PowerResultFunction;
import Power.functions.FFT;
import basic.MyArrayList;
import mpi.MPI;
import mpi_ct.I_MPI_CTOperations;
import mpi_ct.MPI_PowerCT_read;
import mpi_ct.MPI_PowerResultCT_compose;
import mpi_ct.MPI_PowerResultFunctionCT;
import utils.BitFunctions;
import utils.ByteSerialization;

/*
 * test FFT when the data are read from a file
 */
public class Test_FFT_MPI_File {

	public static void main(String[] args) throws Exception {
		MPI.Init(args);
		int nrProces = MPI.COMM_WORLD.Rank();
		int nrProcese = MPI.COMM_WORLD.Size();
//init

		System.out.println(nrProces + "=>" + BitFunctions.reverse(nrProces, 3));
			int limit = 1 << 5; 
			int n = limit / nrProcese;
			// each process will work with list of n elements
			MyArrayList<Complex> base1 = new MyArrayList<Complex>(n);
			MyArrayList<Complex> base2 = new MyArrayList<Complex>(n);
			long timei = System.nanoTime();

			int elem_size = ByteSerialization.byte_serialization_len(new Complex(0));

// create the lists which represent arguments of the function fft	
			PowerList<Complex> list = new PowerList<Complex>(base1, 0, n - 1); // input list (zip)
			PowerList<Complex> rlist = new PowerList<Complex>(base2, 0, n - 1); // result list (tie)
// create the	function that compute fft	
			PowerResultFunction<Complex> mf = 
					new FFT(
							list.toZipPowerList(), // input list
							rlist.toTiePowerList() // outputlist
			);
//													, 1<<3,							
//													new ForkJoinPool(4)); 90609046

/////////////////////////////////////////////////////////////////////////////////////////////////////////////			
//MPIExecution
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// the FFT function is wrapped inside MPI_PowerResultFunction_CT

			int[] sizes = new int[1];
			sizes[0] = n;
			int[] elem_sizes = new int[1];
			elem_sizes[0] = elem_size;
			String[] files = new String[1];
			files[0] = "src/main/resources/date_complex.in";
			I_MPI_CTOperations<Complex> exec = 
					new MPI_PowerCT_read<Complex>(
							new MPI_PowerResultCT_compose<Complex>(
									new MPI_PowerResultFunctionCT<Complex>(mf, 10)), 
					files,	sizes, elem_sizes);

			Object result = exec.compute();
			long timef = System.nanoTime();

			// printing the result and the performance
			/*
			 * the final result is computed in only one process -> the last one!
			 */
			if (nrProces == nrProcese - 1) {
				System.out.println("\n timp paralel    = " + (timef - timei));
				 System.out.println("par = "+result);
			}
			base1 = null;
			base2 = null;

/////////////////////////////////////////////////////////////////////////////////////////////////

		MPI.Finalize();
	}
}
