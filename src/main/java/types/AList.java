package types;

import java.util.List;


/**
 * The Interface AList.
 *
 * @param <E> the element type
 */
public interface AList<E> extends List<E> {
	
	/**
	 * Gets the storage.
	 *
	 * @return the storage
	 */
	public E[] getStorage();

	/**
	 * Gets the byte storage.
	 *
	 * @param start the start
	 * @param incr the incr
	 * @param len the len
	 * @return the byte storage
	 */
	public byte[] getByteStorage(int start, int incr, int len);
	
	/**
	 * Sets the byte storage.
	 *
	 * @param bstorage the bstorage
	 * @param esize the esize
	 * @param start the start
	 * @param incr the incr
	 */
	public void  setByteStorage(byte[] bstorage, int esize, int start, int incr);
	
	/**
	 * Sets the storage elements.
	 *
	 * @param base the base
	 * @param offset the offset
	 * @param incr the incr
	 * @param len the len
	 */
	public void  setStorageElements( E[] base, int offset, int incr, int len);
	
	/**
	 * Capacity.
	 *
	 * @return the int
	 */
	public int capacity();
}
