package mpi_ct;
/*
 * the function comes with the argument lists allocated
 * but not initialized
 * 		
 * the size of input_file[], no_elems[], elem_size[]
 *  arrays should be equal to the number of powerlist arguments
 */
import java.io.IOException;

import Power.PowerList;
import basic.MyArrayList;
import types.IPowerList;
import utils.FileReaderWriter;


/**
 * The Class MPI_PowerCT_read.
 *
 * @param <T> the generic type
 */
public class MPI_PowerCT_read<T> 
									extends MPI_DecoCTOperations<T>{
	
	/** The input file. */
	protected String input_file[];
	
	/** The elem size. */
	protected int no_elems[], elem_size[]; 
	
	/**
	 * Instantiates a new MP I power C T read.
	 *
	 * @param ct the ct
	 * @param input_file the input file
	 * @param no_elems the no elems
	 * @param elem_size the elem size
	 */
	public MPI_PowerCT_read( I_MPI_CTOperations<T> ct, 
													 String input_file[], 
												     int no_elems[], 
													 int elem_size[]) {

		super(ct);
//		function = ct.getFunction();
		this.input_file = input_file;
		this.no_elems = no_elems;
		this.elem_size = elem_size;
	}
	
	/**
	 * Read.
	 */
	public  void read(){
		/*
		 * input data are read from files
		 */
		int no_power_arg = input_file.length;//function.get_no_power_arg();
		for (int i=0;i< no_power_arg; i++){
			IPowerList<T> list  = function.getPowerArg().get(i);
			int type = PowerList.getPowerListType(list);
			MyArrayList<T> base = new MyArrayList<T>();
			try{
				FileReaderWriter<T> file_in = new FileReaderWriter<T>(input_file[i]);
				if (type==PowerList.ZIP ) //ZIP variant
						base = file_in.readZipData(nrProcese, nrProces, elem_size[i], no_elems[i]);
				else					  //TIE variant
					    base = file_in.readTieData( nrProces, elem_size[i], no_elems[i]);
				
				list.setElements(base);
				file_in.close();
				file_in = null;
			}
			catch(IOException e){
				System.out.println(e+ "exception: data could not be read");
			}
		}
	}
}
