package mpi_ct;

import java.io.IOException;
import java.io.RandomAccessFile;

import Power.PowerList;
import Power.PowerResultFunction;
import mpi.MPI;
import mpi.Status;
import utils.BitFunctions;
import utils.ByteSerialization;


/**
 * The Class MPI_PowerResultCT_write.
 *
 * @author virginia
 * 
 * write the results to the same file
 * all the process write into the same file but on different positions
 * the final result is a powerlist which is distributed through the processes
 * if the combine operator is 'tie' the sublists stored in each process are written in order in the result file
 * if the operator is 'zip' the sublists are written in the result file by merging the elements 
 *                           (based on bit representation of the indeces)
 * !!! Assume that the byte array representation of each element has the same length
 * @param <T> the generic type
 */		
////////////////////////////////////////////////////////////////////////////////////////// 

public class MPI_PowerResultCT_write<T> extends MPI_DecoCTOperations<T> {
	//////////////////////////////////////////////////////////////////////////////////////////

	/** The function. */
	protected  PowerResultFunction<T> function;
	
	/** The output file. */
	protected String output_file;
	
	/**
	 * Instantiates a new MP I power result C T write.
	 *
	 * @param ct the ct
	 * @param output_file the output file
	 * @throws Exception the exception
	 */
	//////////////////////////////////////////////////////////////////////////////////////////
	public MPI_PowerResultCT_write (I_MPI_CTOperations<T> ct, String output_file ) throws Exception
	{
		super(ct);
		this.output_file = output_file;
		try{
			function = (PowerResultFunction<T> )ct.getFunction();
		}
		catch(ClassCastException e){
			throw new Exception("not A powerResultFunction");
		}
	}
	
	/**
	 * Write.
	 */
	/////////////////////////////////////////////////////////////////////////////	
	public void write() {
		Object result = function.getResult();
//		System.out.println(nrProces+" in write "+result);
		PowerList<T> p_result = (PowerList<T>) result;
		int elem_size = ByteSerialization.byte_serialization_len(p_result.getValue(0));

		int type = PowerList.getPowerListType(p_result);
		int i, j, n = p_result.getLen();
		long pos = 0;
		byte elem_bytes[] = null;

		if (type==PowerList.ZIP ) //ZIP variant
		{   
			int steps = (int)( Math.log10(nrProcese-1)/Math.log10(2)) +1;
			pos=BitFunctions.reverse(nrProces, steps) * elem_size;	
			try {
				RandomAccessFile file = new RandomAccessFile(output_file, "rw");
				file.seek(pos);
				for(i=0; i<n; i++)
				{
						elem_bytes = ByteSerialization.byte_serialization(p_result.getValue(i));
						for(j=0;j<elem_size;j++) file.writeByte(elem_bytes[j]);
						file.skipBytes((nrProcese-1) * elem_size);
				}
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else//type==TIE
		{	
		  pos = nrProces * (n) * elem_size;
		  try {
			RandomAccessFile file = new RandomAccessFile(output_file, "rw");
			file.seek(pos);
			for(i=0; i< n; i++)
			{
				elem_bytes = ByteSerialization.byte_serialization(p_result.getValue(i));
				for(j=0;j<elem_size;j++) file.writeByte(elem_bytes[j]);
			}
			file.close();
		  }catch (IOException e) {
				e.printStackTrace();
			}
		}
		//verify the end of saving!
		// barrier could be used instead (if the JAva MPI distribution implements it!
		if(nrProces>0){
			MPI.COMM_WORLD.Send(elem_bytes, 0, 1,  MPI.BYTE , 0 , 100);
		}
		else{
			Status s = null;
			for( i=1; i<nrProcese;i++){
				s  = MPI.COMM_WORLD.Recv(elem_bytes, 0, 1, MPI.BYTE, i, 100);	
			}
		}
//		return result;
	}
/////////////////////////////////////////////////////////////////////////////	
	
}
