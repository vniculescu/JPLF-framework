package types;

import java.util.Iterator;
import java.util.List;

/**
 * The Interface IBasicList.
 *
 * @param <T> the generic type
 */
public interface IBasicList<T> extends Iterable<T> {

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
	public boolean isPowerList();

	/**
	 * To power list.
	 *
	 * @return the i power list
	 */
	default public IPowerList<T> toPowerList() {
		/**
		 * 
		 * @return a new object of type IPowerList equal to this
		 */
		return null;
	}

	/**
	 * Creates the copy.
	 *
	 * @return the i basic list
	 */
	public IBasicList<T> create_copy();

	/**	
	 * Copy elems.
	 *
	 * @param dest the dest
	 */
	public void copyElems(IBasicList<T> dest);

	/**
	 * copy all the elements of this into the dest list.
	 *
	 * @return the base
	 */
	//////////////////////////////////////////////////////////
	default public AList<T> getBase() {
		return null;
	}

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
		return 0;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	default public int getStart() {
		// return the index of the first element
		return 0;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	default public int getEnd() {
		// return the index of the end element
		return getLen() - 1;
	}

	/**
	 * Gets the incr.
	 *
	 * @return the incr
	 */
	default public int getIncr() {
		// return the index of the end element
		return 0;
	}

	/**
	 * Sets the values.
	 *
	 * @param list the new values
	 */
	//////////////////////////////////////////////////////////
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
	 * Shift left.
	 *
	 * @param e the e
	 */
	//////////////////////////////////////////////////////////
	default public void shiftLeft(T e) {
	}

	/**
	 * Shift right.
	 *
	 * @param e the e
	 */
	default public void shiftRight(T e) {
	}

	/**
	 * Rotate right.
	 */
	default public void rotateRight() {
	}

	/**
	 * Rotate left.
	 */
	default public void rotateLeft() {
	}

	/**
	 * Sets the offset.
	 *
	 * @param i the new offset
	 */
	//////////////////////////////////////////////////////////
	default public void setOffset(int i) {
	}

	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	public Iterator<T> iterator();

	/**
	 * Gets the prev pos.
	 *
	 * @param pos the pos
	 * @return the prev pos
	 */
	int getPrevPos(int pos);

	/**
	 * Gets the next pos.
	 *
	 * @param pos the pos
	 * @return the next pos
	 */
	int getNextPos(int pos);

	/**
	 * Gets the end pos.
	 *
	 * @return the end pos
	 */
	//////////////////////////////////////////////////////////
	default public int getEndPos() {
		return getLen() - 1;
	}

	/**
	 * Gets the start pos.
	 *
	 * @return the start pos
	 */
	default public int getStartPos() {
		return 0;
	}

	/**
	 * Gets the offset.
	 *
	 * @return the offset
	 */
	default public int getOffset() {
		return 0;
	}
	/**
	 * return the offset of the list offset is an index that gives information about
	 * how the list is represented offset = 0 => first element is on start position
	 * offset > 0 => first element = (start + offset*incr)'%' len offset < 0 =>
	 * first element = (start - offset*incr)'%' len
	 * 
	 * @return
	 */
}