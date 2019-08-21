package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import types.IPowerList;

/**
 * The Class ByteSerialization.
 */
public class ByteSerialization {

	/**
	 * Byte serialization.
	 *
	 * @param object the object
	 * @return the byte[]
	 */
	public static byte[] byte_serialization(Object object) {
		/*
		 * serialize the object into an array of bytes
		 */
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte b[] = bs.toByteArray();
		return b;
	}

	/**
	 * Byte serialization len.
	 *
	 * @param object the object
	 * @return the int
	 */
	public static int byte_serialization_len(Object object) {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte b[] = bs.toByteArray();
		return b.length;
	}

	/**
	 * Byte deserialization.
	 *
	 * @param b the b
	 * @return the object
	 */
	public static Object byte_deserialization(byte[] b) {
		/*
		 * deserialize an object from an array of bytes
		 */
		Object o = null;
		try {
			ByteArrayInputStream bs = new ByteArrayInputStream(b);
			ObjectInputStream is = new ObjectInputStream(bs);
			o = is.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * Byte list serialization.
	 *
	 * @param list the list
	 * @return the byte[]
	 */
	public static byte[] byte_list_serialization(IPowerList<?> list) {
		/*
		 * serialize a list into an array of bytes takes each element of the list
		 * serialize the element and add the result to the final vector
		 */
		int n = list.getLen();
		Object e = list.getValue(0);// first element
		byte[] byte_elem = byte_serialization(e);
		int ne = byte_elem.length;
		byte[] result = new byte[n * ne];
		for (int i = 0; i < ne; i++)
			result[i] = byte_elem[i];
		int k = ne;
		for (int j = 1; j < n; j++) {
			e = list.getValue(j); // take the next element from the list
			byte_elem = byte_serialization(e); // serialize the element
			for (int i = 0; i < byte_elem.length; i++) { // add the serialized element to the array of bytes
				result[k++] = byte_elem[i];
			}
		}
		return result;
	}

	/**
	 * Byte list deserialization.
	 *
	 * @param <T>   the generic type
	 * @param list  the list
	 * @param array the array
	 * @param n     the n
	 * @param ne    the ne
	 */
	public static <T> void byte_list_deserialization(IPowerList<T> list, byte[] array, int n, int ne) {
		/*
		 * deserialize a list from an array of bytes pre: all the elements have the same
		 * serialized size - ne se pun elementele deserializate in lista pe pozitiile
		 * corespunzatoare care depind de tipul listei pre: the list exists and, pre:
		 * list.getLen()>= n there is space in the list where to put the value
		 */
		int k = 0;
		byte[] byte_elem = new byte[ne];
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < ne; i++)
				byte_elem[i] = array[k++];
			T e = (T) byte_deserialization(byte_elem);
//			System.out .println("elem index="+j+ "elem="+e);
			list.setValue(e, j);
		}
	}

}
