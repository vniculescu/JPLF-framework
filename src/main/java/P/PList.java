package P;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.commons.math3.FieldElement;

import P.functions.BiPMap;
import P.functions.Map;
import P.functions.PFilter;
import P.functions.RectangularFormula;
import P.functions.Reduce;
import Power.PowerList;
import basic.BasicList;
import types.AList;
import types.IPList;
import utils.ParesUtils;

/**
 * The Class PList.
 *
 * @param <T> the generic type
 */
public class PList<T> extends BasicList<T> implements IPList<T> {
	/*
	 * A PList is a BasicList together with a list o arities with the property that
	 * the product of these arities = list length
	 */
	// invariant cond: len = Prod (arityList[i])
	/////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The len. */
	protected int len; // we have 'start' and 'end' but is useful to have also variable len updated

	/** The arity list. */
	// appropriately
	protected List<Integer> arityList;

	/**
	 * Instantiates a new p list.
	 *
	 * @param a         the a
	 * @param start     the start
	 * @param end       the end
	 * @param incr      the incr
	 * @param arityList the arity list
	 */
	/////////////////////////////////////////////////////////////
	public PList(AList<T> a, int start, int end, int incr, List<Integer> arityList) {
		super(a, start, end);

		len = (end - start + incr) / incr;
		if ((start != end && start + incr > end) || (ParesUtils.product(arityList) != len))
			throw new IllegalArgumentException("arity list is not correct");
		this.arityList = arityList;
		this.incr = incr;

	}

	/**
	 * Gets the len.
	 *
	 * @return the len
	 */
	public int getLen() {
		return len;// (end-start+incr)/incr;
	}

	/**
	 * Instantiates a new p list.
	 *
	 * @param list the list
	 */
	public PList(PList<T> list) {
		super(list); // !!!! the same base!!!!
		this.arityList = list.arityList;
		this.incr = list.incr;
		this.len = list.len;
	}

	/**
	 * Gets the arity list.
	 *
	 * @return the arity list
	 */
	/////////////////////////////////////////////////////////////
	public List<Integer> getArityList() {
		return arityList;
	}

	/**
	 * To tie P list.
	 *
	 * @return the tie P list
	 */
	/////////////////////////////////////////////////////////////
	public TiePList<T> toTiePList() {
		return new TiePList<T>(base, 0, len - 1, 1, arityList);
	}

	/**
	 * To zip P list.
	 *
	 * @return the zip P list
	 */
	public ZipPList<T> toZipPList() {
		return new ZipPList<T>(base, 0, len - 1, 1, arityList);
	}

	/**
	 * To power list.
	 *
	 * @return the power list
	 */
	/////////////////////////////////////////////////////////////
	public PowerList<T> toPowerList() {
		if (!isPowerList())
			return null;
		else {
			return new PowerList<T>(base, start, end, incr);
		}
	}

	/**
	 * Gets the sub lists.
	 *
	 * @return the sub lists
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	/////////////////////////////////////////////////////////////
	public List<IPList<T>> getSubLists() throws IllegalArgumentException {
		// the implicit decomposition is tie decomposition
		TiePList<T> tpl = this.toTiePList();
		return tpl.getSubLists();
	}

}
