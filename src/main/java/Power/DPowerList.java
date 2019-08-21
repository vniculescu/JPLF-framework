package Power;

import java.util.Iterator;

import types.AList;
import types.IPowerList;


/**
 * The Class DPowerList.
 *
 * @param <T> the generic type
 */
/*
 * This class defines a powerlist defined by the 2 sublists
 * Instances cannot be  Singletons
 */
public abstract class DPowerList<T> implements IPowerList<T>{
	
	/** The right. */
	protected IPowerList<T> left, right;
	
	/**
	 * Instantiates a new d power list.
	 *
	 * @param left the left
	 * @param right the right
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public DPowerList(IPowerList<T> left, IPowerList<T> right) throws IllegalArgumentException
	{
		if (left.getLen()!=right.getLen()) 
			throw new IllegalArgumentException("The two component lists has to have equal length");
		this.left = left;
		this.right = right;
		
	}

	/**
	 * Checks if is singleton.
	 *
	 * @return true, if is singleton
	 */
	@Override
	public boolean isSingleton() {
		return false;
	}


	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	@Override
	public Iterator<T> iterator() {
		return null;
	}


	/**
	 * Loglen.
	 *
	 * @return the int
	 */
	@Override
	public int loglen() {
		return left.loglen()+1;
	}
	
	/**
	 * Gets the len.
	 *
	 * @return the len
	 */
	@Override
	public int getLen() {
		return left.getLen()*2;
	}

	/**
	 * Sets the.
	 *
	 * @param l the l
	 */
	@Override
	public void set(IPowerList<T> l) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Sets the from 2 lists.
	 *
	 * @param l the l
	 * @param r the r
	 */
	@Override
	public void setFrom2Lists(PowerList<T> l, PowerList<T> r) {
		this.left = l;
		this.right = r;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	@Override
	public T getValue() {
		return left.getValue();
	}

	/**
	 * Sets the value.
	 *
	 * @param e the new value
	 */
	@Override
	public void setValue(T e) {
		left.setValue(e);
	}
	
	/**
	 * Sets the elements.
	 *
	 * @param myArrayList the new elements
	 */
	@Override
	public void setElements(AList<T> myArrayList) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Gets the left.
	 *
	 * @return the left
	 */
	@Override
	public IPowerList<T> getLeft() {return left;}
	
	/**
	 * Gets the right.
	 *
	 * @return the right
	 */
	@Override
	public IPowerList<T> getRight() {return right;}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString(){
		String s= new String();
		s+= "[";
		Iterator<T> it = iterator();
		while( it.hasNext() ){
			s+=it.next()+ " ";
		}
		s+= "]";
		return s;
	}
	
	/**
	 * To tie power list.
	 *
	 * @return the i power list
	 */
	public IPowerList<T> toTiePowerList(){
		return new DTiePowerList<T>(left, right);
	}
	
	/**
	 * To zip power list.
	 *
	 * @return the i power list
	 */
	public  IPowerList<T> toZipPowerList(){
		return 	 new DZipPowerList<T>(left, right);
	}
}
