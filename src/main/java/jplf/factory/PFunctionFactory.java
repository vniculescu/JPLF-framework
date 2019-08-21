package jplf.factory;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.commons.math3.FieldElement;

import P.PFunction;
import P.PList;
import P.functions.BiPMap;
import P.functions.Map;
import P.functions.PFilter;
import P.functions.RectangularFormula;
import P.functions.Reduce;
import types.IPList;

/**
 * A factory for creating PFunction objects.
 *
 * @param <T> the generic type
 */
public class PFunctionFactory<T> {

	/**
	 * Map.
	 *
	 * @param simpleFunction the simple function
	 * @param pList          the list
	 * @return the p map
	 */
	public Map<T> map(Function<T, T> simpleFunction, PList<T> pList) {
		return new Map<T>(simpleFunction, pList);
	}

	/**
	 * Map.
	 *
	 * @param simpleFunction the simple function
	 * @param pList          the list
	 * @param res            the res
	 * @return the p map
	 */
	public Map<T> map(Function<T, T> simpleFunction, PList<T> pList, PList<T> res) {
		return new Map<T>(simpleFunction, pList, res);
	}

	/**
	 * Reduce.
	 *
	 * @param operator the operator
	 * @param pList    the list
	 * @return the p reduce
	 */
	public Reduce<T> reduce(BiFunction<T, T, T> operator, PList<T> pList) {
		return new Reduce<T>(operator, pList);
	}

	/**
	 * Rectangular formula.
	 *
	 * @param <T>   the generic type
	 * @param k     the k
	 * @param h     the h
	 * @param pList the list
	 * @return the rectangular formula
	 */
	@SuppressWarnings({ "hiding" })
	public <T extends FieldElement<T>> RectangularFormula<T> rectangularFormula(int k, T h, PList<T> pList) {
		return new RectangularFormula<T>((IPList<T>) pList, k, h);
	}

	/**
	 * Bi map.
	 *
	 * @param <R>            the generic type
	 * @param simpleFunction the simple function
	 * @param pList          the list
	 * @param resultList     the result list
	 * @return the bi P map
	 */
	public <R> BiPMap<T, R> biMap(Function<T, R> simpleFunction, PList<T> pList, Supplier<PList<R>> resultList) {
		return new BiPMap<T, R>(simpleFunction, pList, (IPList<R>) resultList.get());
	}

	/**
	 * Filter.
	 *
	 * @param predicate the predicate
	 * @param pList     the list
	 * @return the p function
	 */
	public PFunction<T> filter(Predicate<T> predicate, PList<T> pList) {
		return new PFilter<T>(predicate, pList);
	}

	/**
	 * Filter.
	 *
	 * @param predicate the predicate
	 * @param pList     the list
	 * @param res       the res
	 * @return the p function
	 */
	public PFunction<T> filter(Predicate<T> predicate, PList<T> pList, PList<T> res) {
		return new PFilter<T>(predicate, pList, res);
	}
}
