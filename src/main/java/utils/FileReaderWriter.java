package utils;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.commons.math3.complex.Complex;

import basic.MyArrayList;


/**
 * The Class FileReaderWriter.
 *
 * @param <T> the generic type
 */
/*
 * the instances of this class are used in order to
 * read elements from a specified file and to put them 
 * in a storage that can be used then to create a list(basiclist, powerlist)
 * the read functions could be used for any type of elements
 * provided that the elements stored into the corresponding file 
 * were saved into this using byte serialization
 * for creating files of Real numbers the function 'writeRealData'
 * could be used
 * (if for the type T a generator of values is provided
 * a general writing function could be given0
 */
public class FileReaderWriter<T> {
	
	/** The file. */
	private RandomAccessFile file ;

/**
 * Instantiates a new file reader writer.
 *
 * @param file_name the file name
 * @throws IOException Signals that an I/O exception has occurred.
 */
/////////////////////////////////////////////////////////////////////////////	
	public FileReaderWriter(String file_name) throws IOException
	{
			file = new RandomAccessFile(file_name, "r");
	}
	
	/**
	 * Instantiates a new file reader writer.
	 *
	 * @param file_name the file name
	 * @param mode the mode
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public FileReaderWriter(String file_name, String mode) throws IOException
	{
			file = new RandomAccessFile(file_name, "rws");
	}
	
	/**
	 * Close.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void close()  throws IOException
	{
		file.close();
	}
/////////////////////////////////////////////////////////////////////////////	
	/*
	 * read the data specific to process with the number nrProces
	 * the data are read based on the tie rule
	 * the elements were stored in the file  as an array of bytes
	 *  which are obtained based on serialization 
	 *  so when they are read a deserialization process is called
	 */
	/**
 * Read tie data.
 *
 * @param nrProces the nr proces
 * @param elem_size the elem size
 * @param n the n
 * @return the my array list
 * @throws IOException Signals that an I/O exception has occurred.
 */
/////////////////////////////////////////////////////////////////////////////	
	public MyArrayList<T> readTieData(int nrProces, int elem_size, int n)
					throws IOException
	{
		MyArrayList<T> base = new MyArrayList<T>(n);
		byte elem_bytes[] = new byte[elem_size];
		try{							  
			file.seek(n*nrProces*elem_size);
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<elem_size;j++) 
					elem_bytes[j] = file.readByte();
				T elem = (T)ByteSerialization.byte_deserialization(elem_bytes);
				base.add(elem);
			}
		  }
		catch ( EOFException e){
			System.out.print("exception EoF reading file");
		}
		return base;
	}
	/////////////////////////////////////////////////////////////////////////////	
	/*
	 * read the data specific to process with the number nrProces
	 * the data are read based on the zip rule
	 * the elements were stored in the file  as an array of bytes
	 *  which are obtained based on serialization 
	 *  so when they are read a deserialization process is called
	 */
	/**
	 * Read zip data.
	 *
	 * @param nrProcese the nr procese
	 * @param nrProces the nr proces
	 * @param elem_size the elem size
	 * @param n the n
	 * @return the my array list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/////////////////////////////////////////////////////////////////////////////	
	public  MyArrayList<T> readZipData(int nrProcese, int nrProces, int elem_size, int n)
			throws IOException
	{
	MyArrayList<T> base = new MyArrayList<T>(n);
	byte elem_bytes[] = new byte[elem_size];
	int steps =BitFunctions.log(nrProcese) ;//(int)( Math.log10(nrProcese-1)/Math.log10(2)) +1;
	try{			
//		System.out.println(nrProces+"<->"+reverse(nrProces,steps));
		file.seek(BitFunctions.reverse(nrProces,steps)*elem_size);
//		System.out.println(nrProces+"seek"+reverse(nrProces,steps)*elem_size);
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<elem_size;j++) 
				elem_bytes[j] = file.readByte();
			
			T elem = (T)ByteSerialization.byte_deserialization(elem_bytes);
//			System.out.println(nrProces+"="+elem);
			base.add(elem);
			file.skipBytes(elem_size*(nrProcese-1));
		}
//		System.out.println(nrProces+"="+base);
	  }
	catch ( EOFException e){
		System.out.print("exception EoF reading file");
	}
	return base;
	}
/////////////////////////////////////////////////////////////////////////////	
	/**
 * Write real data.
 *
 * @param elem_size the elem size
 * @param n the n
 * @param start the start
 * @throws IOException Signals that an I/O exception has occurred.
 */
/*
	 * this function is used to create a file that contains Real numbers
	 * each number is stored as an array of bytes which are obtained
	 * based on serialization 
	 * The stored numbers are in the interval [start, start+n-1]
	 */
	public void writeRealData(int elem_size, int n, double start)
	throws IOException
	{
	/*
	* write n data to a file
	*/
	byte elem_bytes[] = new byte[elem_size];
	try{							  
		file.seek(0);
		Real elem = new Real(start);
		for(int i=0;i<n;i++)
		{
			elem_bytes = ByteSerialization.byte_serialization(elem);
			for(int j=0;j<elem_size;j++) 
				 file.writeByte(elem_bytes[j]);
			elem = new Real(++start);
		}
	}
	catch ( IOException e){
		System.out.print("exceptie IO on writing !!!!");
	}
	}
	
	/**
	 * Write matrix data.
	 *
	 * @param matrix_size the matrix size
	 * @param n the n
	 * @param start the start
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void writeMatrixData(int matrix_size, int n, double start)
			throws IOException
			{
			/*
			* write n data to a file
			*/
		    Matrix elem = new Matrix(matrix_size, start);
			int elem_size = ByteSerialization.byte_serialization_len(elem);
			byte elem_bytes[] = new byte[elem_size];
			try{							  
				file.seek(0);
				
				for(int i=0;i<n;i++)
				{
					elem_bytes = ByteSerialization.byte_serialization(elem);
					for(int j=0;j<elem_size;j++) 
						 file.writeByte(elem_bytes[j]);
					elem = new Matrix(matrix_size, ++start);
				}
			}
			catch ( IOException e){
				System.out.print("exceptie IO on writing !!!!");
			}
			}
	/////////////////////////////////////////////////////////////////////////////	
	/**
	 * Write complex data.
	 *
	 * @param elem_size the elem size
	 * @param n the n
	 * @param start the start
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/*
	* this function is used to create a file that contains Real numbers
	* each number is stored as an array of bytes which are obtained
	* based on serialization 
	* The stored numbers are in the interval [start, start+n-1]
	*/
	public void writeComplexData(int elem_size, int n, Complex start)
		throws IOException
		{
			byte elem_bytes[] = new byte[elem_size];
			try{							  
			file.seek(0);
			for(int i=0;i<n;i++)
			{
				elem_bytes = ByteSerialization.byte_serialization(start);
				for(int j=0;j<elem_size;j++) 
					file.writeByte(elem_bytes[j]);
					start=start.add(1.0);
			}
			}
			catch ( IOException e){
				System.out.print("exceptie IO on writing !!!!");
			}
	}
	/////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The main method.
	 *
	 * @param a the arguments
	 */
	public static void main(String a[]){
		int limit =1<<15;
		/*
		 * create a file with real values
		 */
		int elem_size = ByteSerialization.byte_serialization_len(new Matrix(Matrix.DIM,  0));
		FileReaderWriter<Matrix> file = null;
			try{
				file = new FileReaderWriter<Matrix>("date_matrix_real_"+Matrix.DIM+".in", "create");
				file.writeMatrixData(Matrix.DIM, limit, 1);
				file.close();
				System.out.println("File created");
			}
			catch(IOException e){
				System.out.println("exception: data could not be written!");
			}
//			try{
//				file = new FileReaderWriter<Matrix>("date_matrix_real_"+Matrix.DIM+".in");
//				MyArrayList<Matrix> base = file.readTieData(0, elem_size, limit);
//				file.close();
//				System.out.println(base);
//	
//			}
//			catch(IOException e){
//				System.out.println("exception: data could not be read!");
//			}
		/*
		 * create a file with complex values
		 */
//		int elem_size = ByteSerialization.byte_serialization_len(new Complex(0));
//		MyArrayList<Complex> base = new MyArrayList<Complex>(limit);
//		try{
//			FileReaderWriter<Complex> file = new FileReaderWriter<Complex>("date_complex.in", "create");
//			file.writeComplexData(elem_size, limit, new Complex(1));
//			file.close();
//			file = new FileReaderWriter<Complex>("date_complex.in");
//			base = file.readTieData(0, elem_size, limit);
//			file.close();
////			for(Complex e: base)
////				System.out.println(e);
//		}
//			catch(IOException e){
//				System.out.println("exception: data could not be written!");
//			}
	}
	/////////////////////////////////////////////////////////////////////////////
}
