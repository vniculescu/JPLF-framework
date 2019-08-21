package mpi_ct;
import Power.PowerList;
import mpi.MPI;
import mpi.Status;
import utils.BitFunctions;
import utils.ByteSerialization;

	
/**
	 * The Class MPI_PowerCT_compose.
	 *
	 * @param <T> the generic type
	 */
	public class MPI_PowerCT_compose<T> extends MPI_DecoCTOperations<T> {
		
		/** The steps. */
		//////////////////////////////////////////////////////////////////////////////////////////
		protected  int steps = BitFunctions.log(nrProcese);
		
		/** The step result. */
		protected  Object step_result[] = new Object[steps+1];
		
		/**
		 * Instantiates a new MP I power C T compose.
		 *
		 * @param ct the ct
		 */
		//////////////////////////////////////////////////////////////////////////////////////////
		public MPI_PowerCT_compose(I_MPI_CTOperations<T> ct) {
			super(ct);
		}

		/**
		 * Inv.
		 *
		 * @param p the p
		 * @return the int
		 */
		//////////////////////////////////////////////////////////////////////////////////////////
		private int inv(int p){
			return nrProcese-p-1;
		}
		
		/**
		 * Compose.
		 *
		 * @return the object
		 */
		//////////////////////////////////////////////////////////////////////////////////////////
		@Override
		public Object compose() {
			Object result = function.getResult();
			step_result[steps]=result;
			/////////////
			//go up
			/////////////
			//tree computation
			int type = PowerList.getPowerListType(function.getPowerArg().get(0));
			int rev_nrProces = inv(nrProces);
			
			if (type==PowerList.ZIP ){//ZIP variant
				result = composeZIP(steps, step_result, rev_nrProces); 
				}
			else {//TIE variant
				result = composeTIE(steps, step_result, rev_nrProces);
			}
			return result;
		}//~ end of the method compose
		
		/**
		 * Compose ZIP.
		 *
		 * @param steps the steps
		 * @param step_result the step result
		 * @param rev_nrProces the rev nr proces
		 * @return the object
		 */
		//////////////////////////////////////////////////////////////////////////////////////////
		private Object composeZIP(int steps, Object[] step_result, int rev_nrProces) {
			Object left_result;
			int pow = nrProcese/2;

			byte[] bytes = ByteSerialization.byte_serialization(step_result[steps]);
			int size = bytes.length; //no of bytes of the buffer 
			
			for (int j=steps;j>0;j--)
			{
				if (rev_nrProces >= pow && rev_nrProces <  2*pow)
				{   
					bytes = ByteSerialization.byte_serialization(step_result[j]);
					size = bytes.length; //no of bytes of the buffer 
					MPI.COMM_WORLD.Send(bytes, 0, size,  MPI.BYTE , inv(rev_nrProces-pow), 100);
				}
				if (rev_nrProces < pow){
					Status s  = MPI.COMM_WORLD.Recv(bytes, 0, size, MPI.BYTE, inv(rev_nrProces + pow), 100);
					left_result  = ByteSerialization.byte_deserialization(bytes);		
					step_result[j-1] = 	function.combine(left_result , step_result[j] );
				}
					// prepare next step
					pow/=2;
			}
			 return step_result[0];
		}//~end of the method composeZIP
		
		/**
		 * Compose TIE.
		 *
		 * @param steps the steps
		 * @param step_result the step result
		 * @param rev_nrProces the rev nr proces
		 * @return the object
		 */
		//////////////////////////////////////////////////////////////////////////////////////////
		private Object composeTIE(int steps, Object[] step_result, int rev_nrProces) {
			Object left_result;

			int pow = 1, pow2 = 2, j=steps;
			byte[] bytes = ByteSerialization.byte_serialization(step_result[steps]);
			int size = bytes.length; //no of bytes of the buffer 
			
			for (int i=0; i<steps; i++)
			{
				
				if (rev_nrProces % pow2 != 0 && rev_nrProces  % pow == 0 )
				{   
					bytes = ByteSerialization.byte_serialization(step_result[j]);
					System.out.println(nrProces+ " send "+ step_result[j]);
					MPI.COMM_WORLD.Send(bytes, 0, size,  MPI.BYTE , inv(rev_nrProces-pow), 100);
				}
				if (rev_nrProces % pow2 == 0  ){
					bytes  = new byte[size];
					Status s  = MPI.COMM_WORLD.Recv(bytes, 0, size, MPI.BYTE, inv(rev_nrProces+pow), 100);
					left_result = ByteSerialization.byte_deserialization( bytes);
					System.out.println(nrProces+ " received"+left_result);
					// if the combine step does NOT depend on arguments!!
					step_result[j-1] = 	function.combine(left_result , step_result[j] );
				}
				// prepare the next step
				pow = pow2; pow2 *= 2; j--;
			 }
		 return step_result[0];
		}//~end of the method composeTie
	//////////////////////////////////////////////////////////////////////////////////////////
	}//~end of the class

