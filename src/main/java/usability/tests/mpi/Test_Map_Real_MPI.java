package usability.tests.mpi;
import Power.PowerList;
import Power.PowerResultFunction;
import basic.MyArrayList;
import mpi.MPI;
import mpi_ct.MPI_PowerResultFunctionCT;
import utils.Real;

public class Test_Map_Real_MPI
	{ 	
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

			MyArrayList<Real> base = null;
			Object result = null;
			PowerList<Real> list = null, rlist=null;
			PowerResultFunction<Real> mf =null;

			int n=limit/nrProcese;
			base = new MyArrayList<Real>(n);
			MyArrayList<Real> base2 = new MyArrayList<Real>(n);
			for(int i=nrProces*n; i<n*(nrProces+1);i++){   //tie
//			for(int i=nrProces;i<limit;i+=nrProcese){		//zip
					base.add(
							//new Real((i%4+1.0)/22340111)
							new Real(i)
							);
//					base2.add(	new Real( 0)	);
				}
			//input list
				list  = new PowerList<Real>(base, 0, n-1);
			//output list
				rlist = new PowerList<Real>(base2, 0, n-1);
			// function to be applied on elements
			// powerlist function 
				mf  = 	new Power.functions.Map<Real>(
						t -> t.multiply(t),
						list.toTiePowerList(), 
						rlist.toTiePowerList());

		// MPIExecution
				MPI_PowerResultFunctionCT<Real> exec = 	new MPI_PowerResultFunctionCT<Real>(mf);
				long timei = System.nanoTime();
				result = exec.compute();
//				System.out.println(nrProces+ " finished the execution");
		
		// assure the fact that all the processes finishes the execution
//			    char [] ready= new char [1];
//			    ready[0]='t';
//			    if (nrProces == 0)
//			    	for (int i=1; i<nrProcese; ++i)
//			    		MPI.COMM_WORLD.Send(ready, 0, 1, MPI.CHAR, i, 1000);
//
//				if (nrProces>0)
//					MPI.COMM_WORLD.Recv(ready,  0, 1, MPI.CHAR, 0, 1000);
				long timef = System.nanoTime();
		//printing the result and the performance
			if(nrProces == 0)
				System.out.println("\n" + (timef-timei));	
			System.out.println(nrProces+ " sublist: "+result);

		MPI.Finalize();
	}	
}


