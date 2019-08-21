package basic;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import types.AList;

/**
 * The Class MPIStorage.
 *
 * @param <E> the element type
 */
public class MPIStorage<E> implements AList<E> {

	/** The storage. */
	protected ByteBuffer storage;

	/**
	 * Instantiates a new MPI storage.
	 *
	 * @param capacity the capacity
	 */
	public MPIStorage(int capacity) {
		storage = ByteBuffer.allocate(capacity);
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	@Override
	public int size() {
		return storage.limit();
	}

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	@Override
	public boolean isEmpty() {
		return storage.limit() == 0;
	}

	/**
	 * Contains.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * To array.
	 *
	 * @return the object[]
	 */
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * To array.
	 *
	 * @param <T> the generic type
	 * @param a   the a
	 * @return the t[]
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Adds the.
	 *
	 * @param e the e
	 * @return true, if successful
	 */
	@Override
	public boolean add(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Removes the.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Contains all.
	 *
	 * @param c the c
	 * @return true, if successful
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Adds the all.
	 *
	 * @param c the c
	 * @return true, if successful
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Adds the all.
	 *
	 * @param index the index
	 * @param c     the c
	 * @return true, if successful
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Removes the all.
	 *
	 * @param c the c
	 * @return true, if successful
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Retain all.
	 *
	 * @param c the c
	 * @return true, if successful
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Clear.
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the.
	 *
	 * @param index the index
	 * @return the e
	 */
	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the.
	 *
	 * @param index   the index
	 * @param element the element
	 * @return the e
	 */
	@Override
	public E set(int index, E element) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Adds the.
	 *
	 * @param index   the index
	 * @param element the element
	 */
	@Override
	public void add(int index, E element) {
		// TODO Auto-generated method stub

	}

	/**
	 * Removes the.
	 *
	 * @param index the index
	 * @return the e
	 */
	@Override
	public E remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Index of.
	 *
	 * @param o the o
	 * @return the int
	 */
	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Last index of.
	 *
	 * @param o the o
	 * @return the int
	 */
	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * List iterator.
	 *
	 * @return the list iterator
	 */
	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * List iterator.
	 *
	 * @param index the index
	 * @return the list iterator
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sub list.
	 *
	 * @param fromIndex the from index
	 * @param toIndex   the to index
	 * @return the list
	 */
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the storage.
	 *
	 * @return the storage
	 */
	@Override
	public E[] getStorage() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the byte storage.
	 *
	 * @param start the start
	 * @param incr  the incr
	 * @param len   the len
	 * @return the byte storage
	 */
	@Override
	public byte[] getByteStorage(int start, int incr, int len) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the byte storage.
	 *
	 * @param bstorage the bstorage
	 * @param esize    the esize
	 * @param start    the start
	 * @param incr     the incr
	 */
	@Override
	public void setByteStorage(byte[] bstorage, int esize, int start, int incr) {
		// TODO Auto-generated method stub

	}

	/**
	 * Sets the storage elements.
	 *
	 * @param base   the base
	 * @param offset the offset
	 * @param incr   the incr
	 * @param len    the len
	 */
	@Override
	public void setStorageElements(E[] base, int offset, int incr, int len) {
		// TODO Auto-generated method stub

	}

	/**
	 * Capacity.
	 *
	 * @return the int
	 */
	public int capacity() {
		return 0;
	}

}
