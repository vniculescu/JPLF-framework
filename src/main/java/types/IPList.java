package types;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.math3.FieldElement;

import P.PFunction;
import P.TiePList;
import P.ZipPList;
import P.functions.Map;
import P.functions.RectangularFormula;
import P.functions.Reduce;

/**
 * The Interface IPList.
 *
 * @param <T> the generic type
 */
public interface IPList<T> extends IBasicList<T> {

	/**
	 * To tie P list.
	 *
	 * @return the tie P list
	 */
	public default TiePList<T> toTiePList() {
		return null;
	}

	/**
	 * To zip P list.
	 *
	 * @return the zip P list
	 */
	public default ZipPList<T> toZipPList() {
		return null;
	}

	/**
	 * Gets the arity list.
	 *
	 * @return the arity list
	 */
	public default List<Integer> getArityList() {
		return null;
	}

	/**
	 * Gets the sub lists.
	 *
	 * @return the sub lists
	 */
	public default List<IPList<T>> getSubLists() {
		return null;
	}

}