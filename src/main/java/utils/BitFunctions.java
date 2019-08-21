package utils;

/**
 * The Class BitFunctions.
 */
public class BitFunctions {

	/**
	 * Reverse.
	 *
	 * @param num   the num
	 * @param cifre the cifre
	 * @return the int
	 */
/////////////////////////////////////////////////////////////////////////////	
	public static int reverse(int num, int cifre) {
		int res = 0, i, temp;
		for (i = 0; i < cifre; i++) {
			temp = (num & (1 << i));
			if (temp != 0)
				res |= (1 << ((cifre - 1) - i));
		}
		return res;
	}

	/**
	 * Log.
	 *
	 * @param nrProcese the nr procese
	 * @return the int
	 */
/////////////////////////////////////////////////////////////////////////////		
	public static int log(int nrProcese) {
		return (32 - Integer.numberOfLeadingZeros(nrProcese - 1));
	}

	/**
	 * Rotate.
	 *
	 * @param i   the i
	 * @param max the max
	 * @return the int
	 */
/////////////////////////////////////////////////////////////////////////////	
	public static int rotate(int i, int max) {
		byte b = (byte) i;
		byte x = (byte) (max);

		int res = (b >> 1) | ((b << 2) & x);
		System.out.println("i=" + i + " res=" + res);
		return res;
	}
/////////////////////////////////////////////////////////////////////////////	
}
