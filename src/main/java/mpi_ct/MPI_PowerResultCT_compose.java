package mpi_ct;
import Power.PowerList;
import Power.PowerResultFunction;
import Power.TiePowerList;
import Power.ZipPowerList;
/**
 * compose method is defined for the case when the result of the function is a powerlist
 * tree-composition involves communication between processes
 * but the values need to be send are lists (type = PowerList)
 */
import basic.MyArrayList;
import basic.Transformer;
import mpi.MPI;
import mpi.Status;
import types.IPowerList;
import utils.BitFunctions;
import utils.ByteSerialization;


/**
 * The Class MPI_PowerResultCT_compose.
 *
 * @param <T> the generic type
 */
public class MPI_PowerResultCT_compose<T> extends MPI_DecoCTOperations<T> {
	
	/** The Constant MAX_MEM. */
	//////////////////////////////////////////////////////////////////////////////////////////
	private static final long MAX_MEM = 1<<31-1;
	
	/** The steps. */
	protected  int steps = BitFunctions.log(nrProcese);
	
	/** The step result. */
	protected  PowerList<T> step_result[] = new PowerList[steps+1];
	
	/** The function. */
	protected  PowerResultFunction<T> function;
	
	/**
	 * Instantiates a new MP I power result C T compose.
	 *
	 * @param ct the ct
	 * @throws Exception the exception
	 */
	//////////////////////////////////////////////////////////////////////////////////////////
	public MPI_PowerResultCT_compose(MPI_CTOperations<T> ct) throws Exception
	{
		super(ct);
		try{
			function = (PowerResultFunction<T> )ct.function;
		}
		catch(ClassCastException e){
			throw new Exception("not A powerResultFunction");
		}
	}

	/**
	 * Inv.
	 *
	 * @param p the p
	 * @return the int
	 */
	//////////////////////////////////////////////////////////////////////////////////////////
	int inv(int p){
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
		IPowerList<T> result = function.getResult();
		step_result[steps]=(PowerList<T>) result;
		int ne = ByteSerialization.byte_serialization(step_result[steps].getValue(0)).length;
		/////////////////////////////////////////////////////////////////////////////////////////// 		 
		//go up
		/////////////
		//tree computation
		int type = PowerList.getPowerListType(step_result[steps]);
		int rev_nrProces = inv(nrProces);
		
		if (type==PowerList.ZIP ){//ZIP variant
			result = composeZIP(ne, rev_nrProces); 
		}
		else {//TIE variant
			result = composeTIE(ne, rev_nrProces);
		}
		function.setResult(result);
//		System.out.println(nrProces +" in compose "+result);
		return result;
	}
	
	/**
	 * Compose TIE.
	 *
	 * @param ne the ne
	 * @param rev_nrProces the rev nr proces
	 * @return the i power list
	 */
	/////////////////////////////////////////////////////////////////////////////////////////// 		
	private IPowerList<T> composeTIE(int ne, int rev_nrProces) {
		byte[] bytes;
		int pow = 1, pow2=2, j=steps;
		for (int i=0;i<steps;i++)
		{
		if (rev_nrProces % pow2 != 0 && rev_nrProces  % pow == 0 )
		{   
			int n = step_result[j].getLen();
			long size_max = ne * (long)n;
			if(size_max > MAX_MEM){ // split the message if is longer than MAX_MEM
				System.out.println(nrProces+ " TIE depasire memorie"+size_max+" "+MAX_MEM);
				int split = 2;
				while ( size_max /split > MAX_MEM && n/split>2) split *= 2;
				Transformer<T> t = new Transformer<T>();
				PowerList<PowerList<T>> list_of_lists = t.toTieDepthPowerList(step_result[j], split);
				for (int x=0;x<split;x++){
					bytes = ByteSerialization.byte_list_serialization(list_of_lists.getValue(x));
					int size = bytes.length; //no of bytes of the buffer 
					MPI.COMM_WORLD.Send(bytes, 0, size,  MPI.BYTE , inv(rev_nrProces-pow), 100+x);
				}
			}
			else{
				bytes = ByteSerialization.byte_list_serialization(step_result[j]);
				int size = bytes.length; //no of bytes of the buffer 
				MPI.COMM_WORLD.Send(bytes, 0, size,  MPI.BYTE , inv(rev_nrProces-pow), 100);
			}
		}
		if (rev_nrProces % pow2 == 0  ){
			// n = the length of the existing result which represents
			// the right_result for the next computation
			int n = step_result[j].getLen();
			//the size of the one serialized element
			
			step_result[j-1]= new PowerList<T>(new MyArrayList<T>(2*n), 0, 2*n-1);
			step_result[j-1]  = (TiePowerList<T>) step_result[j-1].toTiePowerList();
			long size_max = ne* (long)n;
			
			if(size_max > MAX_MEM){
					//the message(list) to be received is split
					int split = 2;
					while (size_max/split > MAX_MEM && n/split>2) split *= 2;
					
					PowerList<T> left = (PowerList<T>) step_result[j-1].getLeft();
					Transformer<T> t = new Transformer<T>();
					PowerList<PowerList<T>> list_of_lists = t.toTieDepthPowerList(left, split);
					bytes  = new byte[list_of_lists.getValue(0).getLen()*ne];
					for (int x=0;x<split;x++){
						Status s  = MPI.COMM_WORLD.Recv(bytes, 0, list_of_lists.getValue(x).getLen()*ne, MPI.BYTE, inv(rev_nrProces + pow), 100+x);	
						ByteSerialization.byte_list_deserialization(list_of_lists.getValue(x) , bytes, list_of_lists.getValue(x).getLen(), ne);
					}
			}
		else{
			bytes  = new byte[n*ne];
			Status s  = MPI.COMM_WORLD.Recv(bytes, 0, n*ne, MPI.BYTE, inv(rev_nrProces+pow), 100);
			//the message(list) to be received is single (not-split)
			ByteSerialization.byte_list_deserialization(step_result[j-1] , bytes, n, ne);
			}
		function.setResult(step_result[j-1]);
		step_result[j-1] = 	(PowerList<T>)function.combine(step_result[j-1] , step_result[j] );
		step_result[j]=null;
		}
		// prepare next step
		pow *= 2;
		pow2 *= 2;
		j--;
		}	//~end for
		return  step_result[0];
	}//~ end of the method composeTIE
	
	/**
	 * Compose ZIP.
	 *
	 * @param ne the ne
	 * @param rev_nrProces the rev nr proces
	 * @return the i power list
	 */
	/////////////////////////////////////////////////////////////////////////////////////////// 		
	private IPowerList<T> composeZIP(int ne, int rev_nrProces) {
		byte[] bytes;
		int pow = nrProcese/2;
		for (int j=steps;j>0;j--)
		{
		if (rev_nrProces >= pow && rev_nrProces <  2*pow)
		{   
			// n= the length of the existing result which represents
			// the right_result for the next computation
			int n = step_result[j].getLen();
			long size_max = ne* (long)n;
			if(size_max > MAX_MEM){
				System.out.println(nrProces+ "ZIP depasire memorie"+size_max);
				int split = 2;
				while ( size_max /split > MAX_MEM && n/split>2) split *= 2;
				Transformer<T> t = new Transformer<T>();
				PowerList<PowerList<T>> list_of_lists = t.toTieDepthPowerList(step_result[j], split);
				System.out.println("\n"+nrProces+ " list of lists"+ list_of_lists);
				for (int x=0;x<split;x++){
					bytes = ByteSerialization.byte_list_serialization(list_of_lists.getValue(x));
					int size = bytes.length; //no of bytes of the buffer 
					MPI.COMM_WORLD.Send(bytes, 0, size,  MPI.BYTE , inv(rev_nrProces-pow), 100*j+x);
				}
				}
				else{
					bytes = ByteSerialization.byte_list_serialization(step_result[j]);
					int size = bytes.length; //no of bytes of the buffer 
					MPI.COMM_WORLD.Send(bytes, 0, size,  MPI.BYTE , inv(rev_nrProces-pow), 100);
				}
			bytes=null;
		}
		if (rev_nrProces < pow){
			// n= the length of the existing result which represents
			// the right_result for the next computation
			int n = step_result[j].getLen();
			//ne= the size of the one serialized element
			
			// prepare the new list
			step_result[j-1]= new PowerList<T>(new MyArrayList<T>(2*n), 0, 2*n-1);
			step_result[j-1] = (ZipPowerList)step_result[j-1].toZipPowerList();
			step_result[j-1].setIncr(2); //in order to refer to the left sublist
			// receive the elements size_max
			long size_max = ne* (long)n;
			if(size_max > MAX_MEM){
			System.out.println("the message(list) to be received is split");
			int split = 2;
			while ( n*ne/split > MAX_MEM && n/split>2) split *= 2;
			PowerList<T> left = step_result[j-1];// (PowerList<T>) step_result[j-1].getLeft();////
			Transformer<T> t = new Transformer<T>();
			PowerList<PowerList<T>> list_of_lists = t.toTieDepthPowerList(left, split);
			
			for (int x=0;x<split;x++){
				bytes  = new byte[list_of_lists.getValue(x).getLen()*ne];
				Status s  = MPI.COMM_WORLD.Recv(bytes, 0, list_of_lists.getValue(x).getLen()*ne, MPI.BYTE, inv(rev_nrProces + pow), 100*j+x);	
				ByteSerialization.byte_list_deserialization(list_of_lists.getValue(x) , bytes, list_of_lists.getValue(x).getLen(), ne);
			}
			}
			else{
				//the message(list) to be received is single (not-split)
				bytes  = new byte[n*ne];
				Status s  = MPI.COMM_WORLD.Recv(bytes, 0, n*ne, MPI.BYTE, inv(rev_nrProces + pow), 100);	
				ByteSerialization.byte_list_deserialization(step_result[j-1] , bytes, n, ne);
			}
			bytes = null;
			function.setResult(step_result[j-1]);			
			step_result[j-1] = 	(PowerList<T>)function.combine(step_result[j-1] , step_result[j] );
			step_result[j]=null;
		 }
		// prepare next step
		pow/=2;
		}
		return step_result[0];
	}//~ end of the method composeTIE

}
