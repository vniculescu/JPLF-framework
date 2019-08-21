package usability.tests.mpi;
import Power.PowerList;
import Power.PowerResultFunction;
import Power.functions.Map;
import basic.MyArrayList;
import mpi.MPI;
import mpi_ct.MPI_PowerResultFunctionCT;
import utils.Matrix;
/* 2^10
 * MPI parallel         542850069
 * sequential time =   1272587459
 * speed-up=2.34
   FJ parallel time =   360309934
   speed-up=3.5
 */
public class Test_Map_Matrix_MPI
	{ 	public static final int DIM=3;
		public static int rotate(int i, int max){
			/*
			 * bit rotation
			 */
			byte b = (byte)i;
			byte x = (byte)( max);
			int res = (b>>1)| (  (b<<2) & x );
			return res;
		}
		public static void main(String[] args) throws Exception{
			MPI.Init(args);
			int nrProces = MPI.COMM_WORLD.Rank();
			int nrProcese = MPI.COMM_WORLD.Size();
	//init
			int limit = 1<<4;


			MyArrayList<Matrix> base = null;
			Object result = null;
			PowerList<Matrix> list = null, rlist=null;
			PowerResultFunction<Matrix> mf =null;

			int n=limit/nrProcese;
			base = new MyArrayList<Matrix>(n);
			MyArrayList<Matrix> base2 = new MyArrayList<Matrix>(n);
			for(int i=nrProces*n; i<n*(nrProces+1);i++){   //tie
//			for(int i=nrProces;i<limit;i+=nrProcese){		//zip
					base.add(
							//new Matrix((i%4+1.0)/22340111)
							new Matrix(DIM, i)
							);
//					base2.add(	new Matrix(DIM, 0)	);
				}
			//input list
				list  = new PowerList<Matrix>(base, 0, n-1);
			//output list
				rlist = new PowerList<Matrix>(base2, 0, n-1);
			// function to be applied on elements
			// powerlist function 
				mf  = 	new Map<Matrix>(t -> t.multiply(t),
						list.toTiePowerList(), 
						rlist.toTiePowerList());

		// MPIExecution
				MPI_PowerResultFunctionCT<Matrix> exec = 	
						new MPI_PowerResultFunctionCT<Matrix>(mf);
				long timei = System.nanoTime();
				result = exec.compute();
				System.out.println(nrProces+ " finished the execution");
		
		// assure the fact that all the processes finishes the execution
			    char [] ready= new char [1];
			    ready[0]='t';
			    if (nrProces == 0)
			    	for (int i=1; i<nrProcese; ++i)
			    		MPI.COMM_WORLD.Send(ready, 0, 1, MPI.CHAR, i, 1000);

				if (nrProces>0)
					MPI.COMM_WORLD.Recv(ready,  0, 1, MPI.CHAR, 0, 1000);
				long timef = System.nanoTime();
		//printing the result and the performance
			if(nrProces == 0)
				System.out.println("\n" + (timef-timei));	
			System.out.println(nrProces+ " sublist: "+result);

		MPI.Finalize();
	}	
}


