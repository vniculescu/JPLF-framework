package usability.tests.mpi;
import Power.PowerList;
import Power.PowerResultFunction;
import Power.functions.Map;
import basic.MyArrayList;
import mpi.MPI;
import mpi_ct.I_MPI_CTOperations;
import mpi_ct.MPI_PowerCT_read;
import mpi_ct.MPI_PowerResultCT_write;
import mpi_ct.MPI_PowerResultFunctionCT;
import utils.ByteSerialization;
import utils.Matrix;
/*
 *  timp paralel    = 8521052279 (1<<14, DIM=80, p=8)
 *  timp secvential = 8956928624
 *  timp secvential = 17884608388 (1<<15, DIM=80, p=8)
 * 1<<5
 *  timp paralel    = 14749881001 
 *  secvential      = 17296844144
 *  speed-up = 1.17
 * 1<<10
 *  timp paralel    = 314020551392
 *       secvential = 464575103509
 *       
 *       speed=up = 1,47
 * parallel   3675341522 (DIM=10 limit 1<<10)
 * secvential 5046646349
 * speed_up = 11
 *  matrici cu elemente de tip double
 */
public class Test_Map_Matrix_MPI_file
	{ 	public static final int DIM = 10;
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
			int limit = 1<<5;

			MyArrayList<Matrix> base = null;
			Object result = null;
			PowerList<Matrix> list = null, rlist=null;
			PowerResultFunction<Matrix> mf =null;

			int n=limit/nrProcese;
			base = new MyArrayList<Matrix>(n);
			MyArrayList<Matrix> base2 = new MyArrayList<Matrix>(n);
			for(int i=nrProces*n;i<n*(nrProces+1);i++){   //tie
			//for(int i=nrProces;i<limit;i+=nrProcese){		//zip
					base.add(
							//new Matrix((i%4+1.0)/22340111)
							new Matrix(DIM, i)
							);
//					base2.add(	new Matrix(DIM, 0)	);
				}
				list  = new PowerList<Matrix>(base, 0, n-1);
				rlist = new PowerList<Matrix>(base2, 0, n-1);



				mf  = 	new Map<Matrix>(t -> t.multiply(t),
						list.toTiePowerList(), 
						rlist.toTiePowerList());

				int elem_size = ByteSerialization.byte_serialization_len(new Matrix(DIM, 0));
				
				int [] sizes = new int[1];
				sizes[0] = n;
				int [] elem_sizes = new int[1]; 
				elem_sizes[0] = elem_size;
				String [] files = new String[1]; 
				files[0] = "src/main/resources/date_matrix_par_"+DIM+"_"+limit+".in";
				
//				//MPIExecution
				I_MPI_CTOperations<Matrix>  exec = 
						new MPI_PowerResultCT_write<Matrix>(
								new MPI_PowerCT_read<Matrix>(
										new MPI_PowerResultFunctionCT<Matrix>(mf),
											files, sizes, elem_sizes),
						"src/main/resources/date_matrix_par_"+DIM+"_"+limit+".out");
				long timei = System.nanoTime();
				result = exec.compute();
				long timef = System.nanoTime();
			    System.out.println(nrProces+ " sublist: "+result);
			    if (nrProces==nrProcese-1)
					{
					System.out .println("\n timp paralel    = " + (timef-timei));
					}
			//printing the result and the performance
////////////////////////////////////////////////////////////////////////
//				base=null;
//				base2=null;
//				result=null;rlist=null; list=null;mf=null;f=null;
////////////////////////////////////////////////////////////////////////
//				if (nrProces==nrProcese-1)
//					{
//					System.out .println("\n timp paralel    = " + (timef-timei));
//					MyArrayList<Matrix> base1 = new MyArrayList<Matrix>(limit);
//					for(int i=0;i<limit;i++){
//						base1.add(
//								//new Matrix((i%4+1.0)/22340111)
//								new Matrix(DIM, i)
//								);
//					}
//					timei = System.nanoTime();
//					BasicListFunction<Matrix>  bf = new Basic.Functions.Map<>(f, new BasicList<Matrix>(base1));
//					BasicList<Matrix> res = (BasicList<Matrix>)bf.compute();
//					timef = System.nanoTime();
////					System.out.println(res);
//					System.out.println("\n timp secvential = " + (timef-timei));
//					}
				
				MPI.Finalize();
	}	
}


