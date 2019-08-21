package Power;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;
import java.util.function.Function;

import Power.functions.FFT;
import Power.functions.Map;
import Power.functions.Reduce;
import types.IBasicList;
import types.IPowerList;
import types.IZipPowerList;

/**
 * The Class DZipPowerList.
 *
 * @param <T> the generic type
 */
public class DZipPowerList<T> extends DPowerList<T> implements IZipPowerList<T> {

	/**
	 * Instantiates a new d zip power list.
	 *
	 * @param left  the left
	 * @param right the right
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public DZipPowerList(IPowerList<T> left, IPowerList<T> right) throws IllegalArgumentException {
		super(left, right);
	}

	/**
	 * Normalization.
	 */
	@Override
	public void normalization() {
		// TODO Auto-generated method stub

	}

//	@Override
//	public IPowerList<T> getLeft() {return left.toZipPowerList();}
//	@Override
//	public IPowerList<T> getRight() {return right.toZipPowerList();}
	/**
	 * Sets the values.
	 *
	 * @param list the new values
	 */
//	
	@Override
	public void setValues(List<T> list) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the value.
	 *
	 * @param i the i
	 * @return the value
	 */
	@Override
	public T getValue(int i) {
		int d = i / 2;
		int r = i % 2;
		if (i % 2 == 0)
			return left.getValue(d);
		else
			return right.getValue(d);
	}

	/**
	 * Sets the value.
	 *
	 * @param e the e
	 * @param i the i
	 */
	@Override
	public void setValue(T e, int i) {
		int d = i / 2;
		int r = i % 2;
		if (i % 2 == 0)
			left.setValue(e, d);
		else
			right.setValue(e, d);
	}

	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	@Override
	public Iterator<T> iterator() {
		return new DLIterator<T>(this);
	}

	/**
	 * The Class DLIterator.
	 *
	 * @param <T> the generic type
	 */
	public static class DLIterator<T> implements Iterator<T> {

		/** The list. */
		DPowerList<T> list;

		/** The right. */
		Iterator<T> left, right;

		/** The l r. */
		boolean l_r = false;

		/**
		 * Instantiates a new DL iterator.
		 *
		 * @param basicList the basic list
		 */
		public DLIterator(DZipPowerList<T> basicList) {
			list = basicList;
			left = basicList.left.iterator();
			right = basicList.right.iterator();
		}

		/**
		 * Checks for next.
		 *
		 * @return true, if successful
		 */
		@Override
		public boolean hasNext() {
			return (left.hasNext() || right.hasNext());
		}

		/**
		 * Next.
		 *
		 * @return the t
		 */
		@Override
		public T next() {
			if (!l_r) {
				l_r = true;
				return left.next();
			} else {
				l_r = false;
				return right.next();
			}
		}

	}

	/**
	 * Creates the copy.
	 *
	 * @return the i basic list
	 */
	@Override
	public IBasicList<T> create_copy() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Copy elems.
	 *
	 * @param dest the dest
	 */
	@Override
	public void copyElems(IBasicList<T> dest) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the prev pos.
	 *
	 * @param pos the pos
	 * @return the prev pos
	 */
	@Override
	public int getPrevPos(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Gets the next pos.
	 *
	 * @param pos the pos
	 * @return the next pos
	 */
	@Override
	public int getNextPos(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
