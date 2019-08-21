package types;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.math3.FieldElement;

import P.functions.RectangularFormula;
import Power.PowerList;
import Power.functions.FFT;
import Power.functions.Map;
import Power.functions.Reduce;
import basic.BasicList;

/**
 * The Interface IPowerList.
 *
 * @param <T> the generic type
 */
public interface IPowerList<T> extends IBasicList<T> {

	/**
	 * Checks if is singleton.
	 *
	 * @return true, if is singleton
	 */
	public boolean isSingleton();

	/**
	 * Checks if is power list.
	 *
	 * @return true, if is power list
	 */
	default public boolean isPowerList() {
		return true;
	}

	/**
	 * Loglen.
	 *
	 * @return the int
	 */
	public int loglen();

	/**
	 * To tie power list.
	 *
	 * @return the log length of the this list
	 */
	public IPowerList<T> toTiePowerList();

	/**
	 * To zip power list.
	 *
	 * @return a new object that represents a list equal to this the returned list
	 *         has the type TiePowerList and so split method will use concatenation
	 *         operator getLeft and getRight functions are based on the same
	 *         operator
	 */

	public IPowerList<T> toZipPowerList();

	/**
	 * Gets the left.
	 *
	 * @return a new object that represents a list equal to this the returned list
	 *         has the type TiePowerList and so split method will use zip operator
	 *         getLeft and getRight functions are based on the same operator
	 */

	/**
	 * @return the left
	 */
	public IPowerList<T> getLeft();

	/**
	 * Gets the right.
	 *
	 * @return the right
	 */
	public IPowerList<T> getRight();

	/**
	 * Sets the.
	 *
	 * @param l the l
	 */
	public void set(IPowerList<T> l);

	/**
	 * set this list to the value of the arg - l list -.
	 *
	 * @param l the l
	 * @param r the r
	 */

	public void setFrom2Lists(PowerList<T> l, PowerList<T> r);

	/**
	 * this list is set to equal to ( l 'op' r ).
	 */

	public void normalization();

	/**
	 * Sets the elements.
	 *
	 * @param myArrayList the new elements
	 */
	public void setElements(AList<T> myArrayList);

	/**
	 * returns the number of elements of the list.
	 *
	 * @return the len
	 */
	default public int getLen() {
		return 0;
	}

	/**
	 * returns the number of levels of the list if all the elements of the list are
	 * simple atomic elements then getDepthLen=0 if all the elements of the list are
	 * also list getDepthLen increases with one this rule is applied recursively.
	 *
	 * @return the depth len
	 */
	default public int getDepthLen() {
		int len = 1;
		Object e = this.getValue();
		if (e instanceof BasicList)
			len = ((BasicList<?>) e).getLen();
		return len * getLen();
	}

	/**
	 * Sets the values.
	 *
	 * @param list the new values
	 */
	public void setValues(List<T> list);

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public T getValue();

	/**
	 * Sets the value.
	 *
	 * @param e the new value
	 */
	public void setValue(T e);

	/**
	 * Gets the value.
	 *
	 * @param i the i
	 * @return the value
	 */
	public T getValue(int i);

	/**
	 * Sets the value.
	 *
	 * @param e the e
	 * @param i the i
	 */
	public void setValue(T e, int i);

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString();

	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	public Iterator<T> iterator();

}