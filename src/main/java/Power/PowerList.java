package Power;

import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;
import java.util.function.Function;

import Power.functions.FFT;
import Power.functions.Map;
import Power.functions.Reduce;
import basic.BasicList;
import basic.MyArrayList;
import types.AList;
import types.IPowerList;

/**
 * The Class PowerList.
 *
 * @param <T> the generic type
 */
public class PowerList<T> extends BasicList<T> implements IPowerList<T> {

	/** The Constant ZIP. */
	/*
	 * A PowerList is a BasicList that has len = a power of two it also has a field
	 * loglen = log (len)
	 */
	public static final int ZIP = 1;

	/** The Constant TIE. */
	public static final int TIE = 0;

	/** The loglen. */
	protected int loglen;//

	/**
	 * Instantiates a new power list.
	 */
	////////////////////////////////////////////////////////////////////////////////////////
	public PowerList() {
	}

	/**
	 * Instantiates a new power list.
	 *
	 * @param a the a
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public PowerList(AList<T> a) throws IllegalArgumentException {
		super(a);
		// this.incr = 1;
		int len = (end - start + incr) / incr;

		if (Integer.bitCount(len) != 1) // if len is not a power of two - exception!!!
			throw new IllegalArgumentException(
					"Power list has to have the length a power of two (" + loglen + " " + len + ")");
		else
			loglen = Integer.numberOfTrailingZeros(len);
	}

	/**
	 * Instantiates a new power list.
	 *
	 * @param a     the a
	 * @param start the start
	 * @param end   the end
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public PowerList(AList<T> a, int start, int end) throws IllegalArgumentException {
		super(a, start, end);
		// this.incr = 1;
		int len = (end - start + incr) / incr;

		if (Integer.bitCount(len) != 1) // if len is not a power of two - exception!!!
			throw new IllegalArgumentException(
					"Power list has to have the length a power of two (" + loglen + " " + len + ")");
		else
			loglen = Integer.numberOfTrailingZeros(len);
	}

	/**
	 * Instantiates a new power list.
	 *
	 * @param a     the a
	 * @param start the start
	 * @param end   the end
	 * @param incr  the incr
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public PowerList(AList<T> a, int start, int end, int incr) throws IllegalArgumentException {
		super(a, start, end, incr);
		int len = (end - start + incr) / incr;

		if (Integer.bitCount(len) != 1) // if len is not a power of two - exception!!!
			throw new IllegalArgumentException(
					"Power list has to have the length a power of two (" + loglen + " " + len + ")");
		else
			loglen = Integer.numberOfTrailingZeros(len);
	}

	/**
	 * Instantiates a new power list.
	 *
	 * @param a      the a
	 * @param start  the start
	 * @param end    the end
	 * @param incr   the incr
	 * @param offset the offset
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public PowerList(AList<T> a, int start, int end, int incr, int offset) throws IllegalArgumentException {
		super(a, start, end, incr, offset);
		int len = (end - start + incr) / incr;

		if (Integer.bitCount(len) != 1) // if len is not a power of two - exception!!!
			throw new IllegalArgumentException("Power list has to have the length a power of two ( " + len + ")");
		else
			loglen = Integer.numberOfTrailingZeros(len);
	}

	/**
	 * Instantiates a new power list.
	 *
	 * @param list the list
	 */
	public PowerList(PowerList<T> list) {
		super(list);
		loglen = list.loglen;
	}

	/**
	 * Instantiates a new power list.
	 *
	 * @param p the p
	 */
	public PowerList(IPowerList<T> p) {
		super((BasicList<T>) p);
		loglen = p.loglen();
	}

	/**
	 * Gets the power list type.
	 *
	 * @param list the list
	 * @return the power list type
	 */
	public static int getPowerListType(IPowerList<?> list) {
		if (list instanceof ZipPowerList<?>)
			return ZIP;
		if (list instanceof TiePowerList<?>)
			return TIE;
		return -1;
	}

////////////////////////////////////////////////////////////////////////////////////////	
//	public IPowerList<T> create_copy(){
//		return super.create_copy().toPowerList();
//	}
	/**
	 * Gets the len.
	 *
	 * @return the len
	 */
////////////////////////////////////////////////////////////////////////////////////////	
	public int getLen() {
		return (end - start + incr) / incr;
	}

	/**
	 * Loglen.
	 *
	 * @return the int
	 */
	public int loglen() {
		return loglen;
	}

	/**
	 * To tie power list.
	 *
	 * @return the i power list
	 */
////////////////////////////////////////////////////////////////////////////////////////		
	public IPowerList<T> toTiePowerList() {
		/**
		 * a new object is created - but on the same base; start, end, incr and offset
		 * are equal, but the lists are not identical
		 */
		return new TiePowerList<T>(base, start, end, incr, offset);
	}

	/**
	 * To zip power list.
	 *
	 * @return the i power list
	 */
	public IPowerList<T> toZipPowerList() {
		/**
		 * a new object is created - but on the same base; start, end, incr and offset
		 * are equal, but the lists are not identical
		 */
		return new ZipPowerList<T>(base, start, end, incr, offset);
	}

	/**
	 * Gets the middle.
	 *
	 * @return the middle
	 */
////////////////////////////////////////////////////////////////////////////////////////		
	private int getMiddle() {
		int middle = start;
		if (getOffset() == 0)
			middle = (start + end) / 2;
		else {
			int start_pos = this.getStartPos();
			int len = this.getLen();
			len /= 2;
			middle = start_pos;
			for (int i = 0; i < len - 1; i++)
				middle += incr;
		}
		return middle;
	}

	/**
	 * Gets the left.
	 *
	 * @return the left
	 */
	public IPowerList<T> getLeft() {
		if (this.isSingleton())
			return this;
		TiePowerList<T> l = new TiePowerList<T>(getBase(), start, getMiddle(), incr, offset);
		return l;
	}

	/**
	 * Gets the right.
	 *
	 * @return the right
	 */
	public IPowerList<T> getRight() {
		if (this.isSingleton())
			return this;
		TiePowerList<T> r = new TiePowerList<T>(getBase(), getMiddle() + 1, end, incr, offset);
		return r;
	}

	/**
	 * Sets the.
	 *
	 * @param il the il
	 */
//////////////////////////////////////////////////////////
	public void set(IPowerList<T> il) {
		PowerList<T> l = (PowerList<T>) il;
		this.base = l.base;
		this.end = l.end;
		this.start = l.start;
		this.incr = l.incr;
		this.offset = l.offset;
		this.loglen = l.loglen;
	}
//////////////////////////////////////////////////////////
	/*
	 * the following methods are used for setting the base and so the elements of
	 * the 'this' list by combining two lists (tie or zip list) the two argument
	 * lists either have the same storage or they have different storages depending
	 * on this the approapriate method is used.
	 */

	/**
	 * Sets the from 2 lists.
	 *
	 * @param l the l
	 * @param r the r
	 */
	public void setFrom2Lists(PowerList<T> l, PowerList<T> r) // throws IllegalArgumentException
	{

		/*
		 * we verify first that the lists we need to combine are sharing the same base
		 * if yes (shared memory/array) we just need to set the attributes(start, end,
		 * incr) if not we need to copy the elements from first and second into the
		 * power_result list
		 */
		if (l.getBase() != r.getBase()) {
			if (l.getBase() == this.getBase()) {
				// this.storage=l.storage !=r.storage
				setFrom2DLists_left(l, r);
			} else if (r.getBase() == this.getBase()) {
				// this.storage=r.storage !=l.storage
				setFrom2DLists_right(l, r);
			} else {
				// this.storage!=l.storage and this.storage!=r.storage
				setFrom2DLists_new(l, r);
			}
		} else {
//			System.out.println("SAME left base="+ l.getBase()+"right base="+r.getBase() );
			// this.storage=l.storage=r.storage
			this.start = l.getStart();
			this.end = r.getEnd();
			this.offset = l.getOffset();
			this.loglen = l.loglen + 1;
			if (this instanceof TiePowerList<?>)
				incr = l.getIncr();
			else
				incr = l.getIncr() / 2;
		}
	}

	/**
	 * Sets the from 2 D lists right.
	 *
	 * @param l the l
	 * @param r the r
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	private void setFrom2DLists_right(PowerList<T> l, PowerList<T> r) throws IllegalArgumentException {
		// pre : lungimi egale
		// this.storage == right.storage
		System.out.print("set to the right; left=" + l + "right=" + r);
//		System.out.println("RIGHT left base="+ l.getBase()+"right base="+r.getBase() );
		T[] elems = r.getBase().getStorage();
		int n = r.getLen();
		int incr_r = r.getIncr();
		this.end = r.getEnd();

		if (r instanceof ZipPowerList<?>) {
			if (incr_r == 1 || n * incr_r != elems.length)
				throw new IllegalArgumentException();// if there is not enough space
			incr_r /= 2;
			this.incr = incr_r;
			this.start = this.end - this.incr * (2 * n - 1);

			for (int i = 0, j = 0; i < n; i++, j++) {
				this.setValue(l.getBase().get(j), i * 2);
			}
		} else // if (r instanceof TiePowerList<?>)
		{

			if (2 * n * incr_r != elems.length)
				throw new IllegalArgumentException();// if there is not enough space

			this.incr = r.getIncr();
			this.start = this.end - this.incr * (2 * n - 1);
			// copy the elements from the left starting from 0
			for (int i = 0, j = 0; i < n; i++, j++)
				this.setValue(l.getBase().get(j), i);
		}

	}

	/**
	 * Sets the from 2 D lists left.
	 *
	 * @param l the l
	 * @param r the r
	 */
	private void setFrom2DLists_left(PowerList<T> l, PowerList<T> r) {
		// pre : lungimi egale;
		// this.storage==l.storage
		T[] elems = l.getBase().getStorage();
		int n = r.getLen();
		int incr_l = l.getIncr();
		this.start = l.getStart();
		if (r instanceof ZipPowerList<?>) {
			if (incr_l == 1 || n * incr_l != elems.length)
				throw new IllegalArgumentException();
			incr_l /= 2;
			this.incr = incr_l;
			this.end = this.start + this.incr * (2 * n - 1);
			for (int i = 0, i_r = 0; i < n; i++, i_r++) {
				this.setValue(r.getBase().get(i_r), i * 2 + 1);
			}
		} else
		// if (r instanceof TiePowerList<?>)
		{
			if (2 * n * incr_l != elems.length)
				throw new IllegalArgumentException();
			// copy the elements from the right starting after the last elem from left
			this.incr = incr_l;
			this.end = this.start + this.incr * (2 * n - 1);
			for (int i = n, j = 0; i < 2 * n; i++, j++) {
				this.setValue(r.getBase().get(j), i);
			}
		}
	}

	/**
	 * Sets the from 2 D lists new.
	 *
	 * @param l the l
	 * @param r the r
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setFrom2DLists_new(PowerList<T> l, PowerList<T> r) throws IllegalArgumentException {
		/*
		 * a new storage is created and then populated with the elements from the two
		 * lists this.incr==1
		 */
		if (l.getLen() != r.getLen())
			throw new IllegalArgumentException();
		int n = l.getLen();
		AList<T> base = new MyArrayList<T>(2 * n);
		int incr_l = l.getIncr();
		int incr_r = l.getIncr();
		int n_l = l.getEnd();
		int n_r = r.getEnd();
		if (l instanceof TiePowerList<?>) {
			for (int i = l.getStart(); i <= n_l; i += incr_l)
				base.add(l.getBase().get(i));
			for (int i = r.getStart(); i <= n_r; i += incr_r)
				base.add(r.getBase().get(i));
		} else {
			int i_l = l.getStart(), i_r = r.getStart();
			for (int i = 0; i < n; i++) {
				base.add(l.getBase().get(i_l));
				i_l += incr_l;
				base.add(r.getBase().get(i_r));
				i_r += incr_r;
			}
		}
		this.setElements(base);
		this.incr = 1;
	}

////////////////////////////////////////////////////////////////////////////////////////
}
