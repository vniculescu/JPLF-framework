package basic;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import P.PList;
import Power.PowerList;
import types.AList;
import types.IBasicList;
import types.IPList;
import types.IPowerList;
import utils.ParesUtils;

/**
 * This is a simple basic list that use a storage of type java.util.List the
 * list's elements are stored between 'start' and 'end' (inclusive) on 'base'
 * could not be an empty list a list with only one element is called singleton
 *
 * @param <T> the generic type
 */
public class BasicList<T> implements IBasicList<T>, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8781102791846705234L;

	/** The base. */
	protected AList<T> base;// storage

	/** The start. */
	protected int start;

	/** The end. */
	protected int end;

	/** The incr. */
	protected int incr = 1;

	/** The offset. */
	protected int offset = 0;

	/**
	 * the elements could be stored circularly so the first element will be at the
	 * position "pos= start+offset*incr" if "start+offset*incr less than start"
	 * (offset negative) then "pos = end -(offset-1)*incr" problem :iteration??? an
	 * Iterator that knows how iterate correct depending on offset could be used
	 * ???issue??? performance - without Iterator it is better.
	 */

	public BasicList() {
	}

	/**
	 * Instantiates a new basic list.
	 *
	 * @param a the a
	 * @throws IllegalArgumentException the illegal argument exception
	 */
///////////////////////////////////////////////////////
	public BasicList(AList<T> a) throws IllegalArgumentException {
		if (a == null)
			throw new IllegalArgumentException("null storage for the list");
		this.base = a;
		this.start = 0;
		end = base.size() - 1;
	}

	/**
	 * Instantiates a new basic list.
	 *
	 * @param a     the a
	 * @param start the start
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public BasicList(AList<T> a, int start) throws IllegalArgumentException {
		if (a == null)
			throw new IllegalArgumentException("null storage for the list");
		this.base = a;
		this.start = start;
		end = base.size() - 1;
		if (start < 0)
			throw new IllegalArgumentException(
					"the start index greater than the end of the storage (" + start + ")(" + end + ")");
	}

	/**
	 * Instantiates a new basic list.
	 *
	 * @param a     the a
	 * @param start the start
	 * @param end   the end
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public BasicList(AList<T> a, int start, int end) throws IllegalArgumentException {
		this(a, start);
		if (end > a.capacity())
			throw new IllegalArgumentException(
					"the length of the list is greater then the base length (" + end + ")(" + a.size() + ")");
		if (start > end)
			throw new IllegalArgumentException(
					"the start index greater than the end index  (" + start + "+" + end + ")(" + end + ")");
		this.end = end;
	}

	/**
	 * Instantiates a new basic list.
	 *
	 * @param a     the a
	 * @param start the start
	 * @param end   the end
	 * @param incr  the incr
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public BasicList(AList<T> a, int start, int end, int incr) throws IllegalArgumentException {
		this(a, start);
		if (end > a.capacity())
			throw new IllegalArgumentException(
					"the length of the list is greater then the base length (" + end + ")(" + a.size() + ")");
		if (start > end)
			throw new IllegalArgumentException(
					"the start index greater than the end index  (" + start + "+" + end + ")(" + end + ")");
		this.incr = incr;
		this.end = end;
	}

	/**
	 * Instantiates a new basic list.
	 *
	 * @param a      the a
	 * @param start  the start
	 * @param end    the end
	 * @param incr   the incr
	 * @param offset the offset
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public BasicList(AList<T> a, int start, int end, int incr, int offset) throws IllegalArgumentException {
		this(a, start, end, incr);
		setOffset(offset);
	}

	/**
	 * Instantiates a new basic list.
	 *
	 * @param list the list
	 */
	public BasicList(BasicList<T> list) {
		// copy constructor=!!!! the same base!!!!
		this(list.base, list.start, list.end, list.incr, list.offset);
	}

	/**
	 * Creates the copy.
	 *
	 * @return the i basic list
	 */
///////////////////////////////////////////////////////
	public IBasicList<T> create_copy() {
		// create a copy of the list in a new storage that has start = 0 (normalization)
		AList<T> temp_base = new MyArrayList<T>(end - start);
		for (T e : base)
			temp_base.add(e);
		return new BasicList<T>(temp_base, start, end, incr, offset);
	}

	/**
	 * Copy elems.
	 *
	 * @param dest the dest
	 */
	public void copyElems(IBasicList<T> dest) {
		Iterator<T> it1 = iterator();
		LIterator<T> it2 = (LIterator<T>) dest.iterator();
		while (it1.hasNext()) {
			it2.setCurrent(it1.next());
			it2.next();
		}
	}

	/**
	 * Gets the base.
	 *
	 * @return the base
	 */
///////////////////////////////////////////////////////
	public AList<T> getBase() { // should be protected - but this way wouldn't be accessible from PowerList,
								// etc.
		return base;
	}

	/**
	 * Sets the base.
	 *
	 * @param base the new base
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setBase(AList<T> base) throws IllegalArgumentException {
		this.base = base;
		if (end >= base.size())
			throw new IllegalArgumentException(
					"the length of the new list is smaller then the list length (" + end + ")(" + base.size() + ")");
	}

	/**
	 * Sets the elements.
	 *
	 * @param base the new elements
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setElements(AList<T> base) throws IllegalArgumentException {
		this.base = base;
		start = 0;
		end = base.size() - 1;
		incr = 1;
	}

	/**
	 * Gets the len.
	 *
	 * @return the len
	 */
///////////////////////////////////////////////////////	
	public int getLen() {
		return (end - start + incr) / incr;
	}

	/**
	 * Gets the depth len.
	 *
	 * @return the depth len
	 */
	public int getDepthLen() {
		int len = 1;
		Object e = this.getValue();
		if (e instanceof BasicList)
			len = ((BasicList) e).getLen();
		return len * (end - start + incr) / incr;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
///////////////////////////////////////////////////////			
	public int getStart() {
		// return the index of the first element
		return start;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public int getEnd() {
		// return the index of the end element
		return end;
	}

	/**
	 * Gets the incr.
	 *
	 * @return the incr
	 */
	public int getIncr() {
		// return increment between two elements
		return incr;
	}

	/**
	 * Gets the offset.
	 *
	 * @return the offset
	 */
	public int getOffset() {
		// return the no of shifts of the elements in the storage
		return offset;
	}

	/**
	 * Sets the start.
	 *
	 * @param index the new start
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setStart(int index) throws IllegalArgumentException {
		if (index < 0)
			throw new IllegalArgumentException("negative increment");
		start = index;
	}

	/**
	 * Sets the end.
	 *
	 * @param index the new end
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setEnd(int index) throws IllegalArgumentException {
		if (index < 0)
			throw new IllegalArgumentException("negative increment");
		end = index;
	}

	/**
	 * Sets the incr.
	 *
	 * @param i the new incr
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setIncr(int i) throws IllegalArgumentException {
		if (i < 1)
			throw new IllegalArgumentException("negative increment");
		incr = i;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
///////////////////////////////////////////////////////	
	public String toString() {
		String s = new String();
		s += "start=" + start + "end=" + end + "incr=" + incr + "offset=" + offset + " [";
		Iterator<T> it = (LIterator<T>) iterator();
		while (it.hasNext()) {
			s += it.next() + " ";
		}
		s += "]";
//			s+=" offset="; s+=offset;
//			s+="start="+start;
//			s+="incr="+incr;
//			s+="end="+end;
		s += "  indices (";
		LIterator<T> lit = (LIterator<T>) iterator();
		while (lit.hasNext()) {
			s += lit.getCurrentPos() + " ";
			lit.next();
		}
		s += ")";

		return s;
	}

	/**
	 * Checks if is singleton.
	 *
	 * @return true, if is singleton
	 */
//////////////////////////////////////////////////////////
	public boolean isSingleton() {
		return (start == end);
	}

//////////////////////////////////////////////////////////
	/**
	 * Checks if is power list.
	 *
	 * @return true, if is power list
	 */
	/*
	 * PowerList related methods if the length of the list is a power of two then
	 * could be transformed into a PowewrList
	 */
	public boolean isPowerList() {
		int n = getLen();
		// double loglen = Math.log(n)/Math.log(2);
		// return (Math.round(loglen) == loglen);
		return Integer.bitCount(n) == 1;
	}

	/**
	 * To power list.
	 *
	 * @return the i power list
	 */
	public IPowerList<T> toPowerList() {
		return new PowerList<T>(base, start, end, incr);
	}

	/**
	 * Sets the values.
	 *
	 * @param list the new values
	 */
//////////////////////////////////////////////////////////
	public void setValues(List<T> list) {
		base.clear();
		int index = this.start;
		for (T e : list) {
			base.add(index, e);
			index += incr;
		}
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
//////////////////////////////////////////////////////////
	public T getValue() {
		return base.get(getStartPos());
	}

	/**
	 * Sets the value.
	 *
	 * @param e the new value
	 */
	public void setValue(T e) {
		base.set(getStartPos(), e);
	}

	/**
	 * Gets the value.
	 *
	 * @param i the i
	 * @return the value
	 */
	public T getValue(int i) {
		// return the i element of the base
		int index = start + i * incr;
		// System.out.println("base"+base+"index="+index);
		return base.get(index);
	}

	/**
	 * Sets the value.
	 *
	 * @param e the e
	 * @param i the i
	 */
	public void setValue(T e, int i) {
		// set the i element of the base
		// base.set(i, e);
		int index = start + i * incr;
		base.set(index, e);
	}

	/**
	 * Shift right.
	 *
	 * @param e the e
	 */
//////////////////////////////////////////////////////////
	public void shiftRight(T e) {
		offset--;
		int n = getLen();
		if (-offset >= n)
			offset += n;
		// the first element =e
		setValue(e, getStartPos());
	}

	/**
	 * Shift left.
	 *
	 * @param e the e
	 */
	public void shiftLeft(T e) {
		// the last element =e
		offset++;
		int n = getLen();
		if (offset >= n)
			offset -= n;
		setValue(e, getEndPos());
	}

	/**
	 * Rotate right.
	 */
//////////////////////////////////////////////////////////
	public void rotateRight() {
		offset--;
		if (Math.abs(offset) == getLen())
			offset = 0;
	}

	/**
	 * Rotate left.
	 */
	public void rotateLeft() {
		offset++;
		if (Math.abs(offset) == getLen())
			offset = 0;
	}

	/**
	 * Sets the offset.
	 *
	 * @param i the new offset
	 */
//////////////////////////////////////////////////////////
	public void setOffset(int i) {
		int n = getLen();
		if (i >= n)
			i -= n;
		if (Math.abs(i) >= n)
			i += n;
		offset = i;
	}

	/**
	 * Gets the start pos.
	 *
	 * @return the start pos
	 */
//////////////////////////////////////////////////////////
	public int getStartPos() {
		int start_pos = start + offset * incr;
		if (start_pos < start) // (offset negative)
			start_pos = end + (offset + 1) * incr;
		return start_pos;
	}

	/**
	 * Gets the end pos.
	 *
	 * @return the end pos
	 */
//////////////////////////////////////////////////////////
	public int getEndPos() {
		int final_pos = end + offset * incr;
		;
		if (final_pos > end) // (offset positive)
			final_pos = start + (offset - 1) * incr;
		return final_pos;
	}

	/**
	 * Gets the next pos.
	 *
	 * @param pos the pos
	 * @return the next pos
	 */
//////////////////////////////////////////////////////////
	public int getNextPos(int pos) {
		pos += this.getIncr();
		if (this.offset != 0 && pos > this.end)
			pos = this.start;
		return pos;
	}

	/**
	 * Gets the prev pos.
	 *
	 * @param pos the pos
	 * @return the prev pos
	 */
	public int getPrevPos(int pos) {
		pos -= this.getIncr();
		if (this.offset != 0 && pos < this.start)
			pos = this.end;
		return pos;
	}

	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
//////////////////////////////////////////////////////////
	@Override
	public Iterator<T> iterator() {
		return new LIterator<T>(this);
	}

	/**
	 * The Class LIterator.
	 *
	 * @param <T> the generic type
	 */
	public static class LIterator<T> implements Iterator<T> {

		/** The list. */
		BasicList<T> list;

		/** The start pos. */
		int current, final_pos, start_pos;

		/** The iter. */
		boolean iter = false;

		/**
		 * Instantiates a new l iterator.
		 *
		 * @param basicList the basic list
		 */
		public LIterator(BasicList<T> basicList) {
			list = basicList;
			final_pos = basicList.getEndPos();
			start_pos = basicList.getStartPos();
			current = start_pos;
		}

		/**
		 * Checks for next.
		 *
		 * @return true, if successful
		 */
		@Override
		public boolean hasNext() {
			if (list.offset == 0)
				return current <= final_pos;
			else
				return (!(current == start_pos && iter));

		}

		/**
		 * Next.
		 *
		 * @return the t
		 */
		@Override
		public T next() {
			iter = true; // the iteration started
			T e = list.base.get(current);
			// the movement is circular
			current += list.incr;
			if (list.offset != 0 && current > list.end)
				current = list.start;
			return e;
		}

		/**
		 * Gets the current pos.
		 *
		 * @return the current pos
		 */
		public int getCurrentPos() {
			return current;
		}

		/**
		 * Sets the current.
		 *
		 * @param e the new current
		 */
		public void setCurrent(T e) {
			list.setValue(e, current);
		}
	}

	/**
	 * Gets the storage.
	 *
	 * @return the storage
	 */
	public T[] getStorage() {
		return base.getStorage();
	}

	/**
	 * Normalization.
	 */
//////////////////////////////////////////////////////////
	public void normalization() {
		AList<T> new_base = new MyArrayList<T>((end - start + incr) / incr);// new storage
		for (int i = start; i <= end; i += incr)
			new_base.add(base.get(i));
		end = (end - start) / incr;
		start = 0;
		incr = 1;
		base = new_base;// =null
	}

//////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
	/**
	 * To P list.
	 *
	 * @return the IP list
	 */
	/*
	 * PList related methods any BasicList could be a PList
	 * 
	 */
	public IPList<T> toPList() {
		List<Integer> arity_list = ParesUtils.getPrimeFactors(getLen());
		return new PList<T>(base, start, end, 1, arity_list);
	}
//public  IPList<T> toFlatPList(){
//List<Integer> arity_list = new ArrayList<Integer>();
//arity_list.add(getLen());
//return new PList<T>(base,start,end,1,arity_list);
//}

}