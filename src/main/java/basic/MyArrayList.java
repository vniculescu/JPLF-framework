package basic;

import java.io.Serializable;
import java.util.AbstractList;

import types.AList;
import utils.ByteSerialization;

/**
 * The Class MyArrayList.
 *
 * @param <E> the element type
 */
public class MyArrayList<E> extends AbstractList<E> implements AList<E>, Serializable {

	/** The Constant RESIZE_CAPACITY. */
	public static final int RESIZE_CAPACITY = 10;

	/** The storage. */
	protected E storage[];

	/** The size. */
	protected int size;

	/** The capacity. */
	protected int capacity;

	/**
	 * Instantiates a new my array list.
	 */
	public MyArrayList() {
		storage = (E[]) new Object[1];
		this.capacity = 1;
	}

	/**
	 * Instantiates a new my array list.
	 *
	 * @param elements the elements
	 */
	public MyArrayList(E[] elements) {
		storage = elements;
		this.capacity = elements.length;
		size = elements.length;
	}

	/**
	 * Instantiates a new my array list.
	 *
	 * @param capacity the capacity
	 */
	public MyArrayList(int capacity) {
		storage = (E[]) new Object[capacity];
		this.capacity = capacity;
	}

	/**
	 * Gets the storage.
	 *
	 * @return the storage
	 */
	public E[] getStorage() {
		return storage;
	}

	/**
	 * Adds the.
	 *
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean add(E e) {
		if (capacity <= size)
			resize();
		storage[size++] = e;
		return true;
	}

	/**
	 * Adds the.
	 *
	 * @param index   the index
	 * @param element the element
	 */
	public void add(int index, E element) {
		if (capacity <= size)
			resize();
		for (int i = size; i > index; i--)
			storage[i] = storage[i - 1];
		storage[index] = element;
		size++;
	}

	/**
	 * Resize.
	 */
	private void resize() {
		E new_storage[] = (E[]) new Object[capacity + RESIZE_CAPACITY];
		for (int i = 0; i < size; i++)
			new_storage[i] = storage[i];
		storage = new_storage;
		capacity += RESIZE_CAPACITY;

	}

	/**
	 * Gets the.
	 *
	 * @param index the index
	 * @return the e
	 */
	public E get(int index) {
		return storage[index];
//		if (index<size)
//			return storage[index];
//		else 
//			return null;
////			throw new ArrayIndexOutOfBoundsException();
	}

	/**
	 * Removes the.
	 *
	 * @param index the index
	 * @return the e
	 */
	public E remove(int index) {
		E object = storage[index];
		for (int i = index; i < size - 1; i++)
			storage[i] = storage[i + 1];
		size--;
		return object;
	}

	/**
	 * Sets the.
	 *
	 * @param index   the index
	 * @param element the element
	 * @return the e
	 */
	public E set(int index, E element) {
		E old_element = storage[index];
		storage[index] = element;
		return old_element;
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return size;
	}

	/**
	 * Capacity.
	 *
	 * @return the int
	 */
	public int capacity() {
		return capacity;
	}

	/**
	 * Gets the byte storage.
	 *
	 * @param offset the offset
	 * @param incr   the incr
	 * @param len    the len
	 * @return the byte storage
	 */
//////////////////////////////////////////////////////////////////////////////////////////
	public byte[] getByteStorage(int offset, int incr, int len) {
		/*
		 * pre: all the elements have the same size 0 <= offset < size
		 */
		byte[] bstorage = null;
		byte[] belement = ByteSerialization.byte_serialization(storage[offset]);
		int esize = belement.length;
		bstorage = new byte[(len) * esize];
		int k = 0;
		for (int j = 0; j < esize; ++j)
			bstorage[k++] = belement[j];
		for (int i = offset + incr; i < offset + len * incr; i += incr) {
			belement = ByteSerialization.byte_serialization(storage[i]);
			for (int j = 0; j < esize; ++j)
				bstorage[k++] = belement[j];
		}
		return bstorage;
	}

	/**
	 * Sets the byte storage.
	 *
	 * @param bstorage the bstorage
	 * @param esize    the esize
	 * @param offset   the offset
	 * @param incr     the incr
	 */
	public void setByteStorage(byte[] bstorage, int esize, int offset, int incr) {
		/*
		 * pre: esize = sizeof (E) len+offset < capacity
		 */
		byte[] belement = new byte[esize];
		int len = bstorage.length / esize;
		int k = 0;
		for (int i = offset; i < len + offset; i += incr) {
			for (int j = 0; j < esize; ++j)
				belement[j] = bstorage[k++];
			storage[i] = (E) ByteSerialization.byte_deserialization(belement);
		}
		size = len;
	}

	/**
	 * Sets the storage elements.
	 *
	 * @param base   the base
	 * @param offset the offset
	 * @param incr   the incr
	 * @param len    the len
	 */
//////////////////////////////////////////////////////////////////////////////////////////	
	public void setStorageElements(E[] base, int offset, int incr, int len) {
		/*
		 * offset < size len+offset < capacity
		 */
		for (int i = offset; i < len + offset; i += incr) {
			storage[i] = base[i];
		}
		size = (len + offset) > size ? len + offset : size;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
//////////////////////////////////////////////////////////////////////////////////////////
	public String toString() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < size; i++)
			s.append(" " + storage[i]);
		return new String(s);
	}
//////////////////////////////////////////////////////////////////////////////////////////
}
