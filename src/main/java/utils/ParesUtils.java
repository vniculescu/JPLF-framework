package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class ParesUtils.
 */
public class ParesUtils {

	/**
	 * Gets the prime factors.
	 *
	 * @param numbers the numbers
	 * @return the prime factors
	 */
	public static List<Integer> getPrimeFactors(int numbers) {
		int n = numbers;
		final List<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i <= (n / i); i++) {
			while ((n % i) == 0) {
				factors.add(i);
				n /= i;
			}
		}
		if (n > 1) {
			factors.add(n);
		}
		return factors;
	}

	/**
	 * Gets the longest prime factors.
	 *
	 * @param numbers the numbers
	 * @return the longest prime factors
	 */
	public static List<Integer> getLongestPrimeFactors(int numbers) {
		final List<Integer> factorsPlusOne = getPrimeFactors(numbers + 1);
		List<Integer> result = getPrimeFactors(numbers - 1);
		if (factorsPlusOne.size() > result.size()) {
			result = factorsPlusOne;
		}
		return result;
	}

	/**
	 * Gets the diference.
	 *
	 * @param primeFactors the prime factors
	 * @param limit        the limit
	 * @return the diference
	 */
	public static int getDiference(List<Integer> primeFactors, int limit) {
		int p = 1;
		for (final Integer i : primeFactors) {
			p = p * i;
		}
		return p - limit;
	}

	/**
	 * Gets the power fromg args.
	 *
	 * @param args the args
	 * @return the power fromg args
	 */
//
	public static int getLimitFromgArgs(String[] args) {
		String power = "1000";
		if ((args != null) && (args.length != 0) && (args[0] != null)) {
			power = args[0];
		}
		return Integer.parseInt(power);
	}

	/**
	 * Log.
	 *
	 * @param fileName the file name
	 * @param message  the message
	 */
	public static void log(final String fileName, final String message) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName + ".log", true)))) {
			out.println(message);
		} catch (final IOException e) {
			System.err.println(e);
		}
	}

	/**
	 * Gets the factor string.
	 *
	 * @param arityList the arity list
	 * @return the factor string
	 */
	public static String getFactorString(List<Integer> arityList) {
		final List<Integer> distinct = arityList.stream().distinct().collect(Collectors.toList());
		String factorString = "";
		for (final Integer factor : distinct) {
			factorString += factor + "^" + Collections.frequency(arityList, factor) + " ";
		}
		return factorString;
	}

	public static int product(List<Integer> list) {
		int n = 1;
		for (int i = 0; i < list.size(); i++)
			n *= list.get(i);
		return n;
	}
}
