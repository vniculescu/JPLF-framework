package usability.tests.multithreading.map;

import java.io.IOException;
import java.io.RandomAccessFile;

import basic.BasicList;
import basic.BasicListResultFunction;
import basic.MyArrayList;
import basic.functions.Map;
import utils.ByteSerialization;
import utils.FileReaderWriter;
import utils.Matrix;

public class Test_Map_Matrix_Secv_file {
	public static void main(String[] args) throws IOException {

		int limit = 1 << 4;
		MyArrayList<Matrix> base1 = new MyArrayList<Matrix>(limit);

//		for(int i=0;i<limit;i++){   
//				base1.add(new Matrix(Matrix.DIM, i));	
//			}

		long timei = System.nanoTime();
//read the data
		FileReaderWriter<Matrix> file = null;
		int elem_size = ByteSerialization.byte_serialization_len(new Matrix(Matrix.DIM, 0));
		try {
			file = new FileReaderWriter<Matrix>("date_matrix_real_" + Matrix.DIM + ".in");
			base1 = file.readTieData(0, elem_size, limit);
			file.close();
		} catch (IOException e) {
			System.out.println("exception: data could not be read!");
		}

//define the list
		BasicList<Matrix> list = new BasicList<Matrix>(base1, 0, limit - 1);
//		System.out.println(list);
//define the function
		BasicListResultFunction<Matrix> bmf = new Map<Matrix>(t -> t.multiply(t), list);

//compute map

		Object result = bmf.compute();

//write the result into the result file
		BasicList<Matrix> b_result = (BasicList<Matrix>) result;
		try {
			RandomAccessFile file_simple = new RandomAccessFile("date_matrix_secv" + Matrix.DIM + ".out", "rw");
			file_simple.seek(0);
			for (int i = 0; i < limit; i++) {
				byte elem_bytes[] = ByteSerialization.byte_serialization(b_result.getValue(i));
				for (int j = 0; j < elem_size; j++)
					file_simple.writeByte(elem_bytes[j]);
			}
			file_simple.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long timef = System.nanoTime();

		System.out.println("s = " + result);

		System.out.println("sequential time =" + (timef - timei));
	}
}
