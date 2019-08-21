package Power;
import java.io.Serializable;

import Power.functions.ShiftLeft;
import Power.functions.ShiftRight;
import types.AList;
import types.IPowerList;
import types.IZipPowerList;




/**
 * The Class ZipPowerList.
 *
 * @param <T> the generic type
 */
public class ZipPowerList<T> extends PowerList<T> implements IZipPowerList<T>, Serializable{
	
	/**
	 * Instantiates a new zip power list.
	 *
	 * @param a the a
	 * @param begin the begin
	 * @param end the end
	 * @param incr the incr
	 * @param offset the offset
	 */
	////////////////////////////////////////////////////////////////////////////////////////
	public ZipPowerList(AList<T> a, final int begin, final int end, final int incr,int offset){
		super(a, begin,end,incr,offset);
	}
	
	/**
	 * Instantiates a new zip power list.
	 *
	 * @param p the p
	 */
	public ZipPowerList(IPowerList<T> p){
		super(p);
	}
	
	/**
	 * Instantiates a new zip power list.
	 */
	public ZipPowerList(){}
	
	/**
	 * Instantiates a new zip power list.
	 *
	 * @param l the l
	 * @param r the r
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	////////////////////////////////////////////////////////////////////////////////////////
	public ZipPowerList(PowerList<T> l, PowerList<T> r)	throws IllegalArgumentException
	{//transform two 'neighbor' zip powerlists into one
		//pre: left.base = right.base 
		//pre l.offset = r.offset
		//pre l.incr = r.incr

		super(l.getBase(),0,0,1);
		if (l.getBase()!= r.getBase())	throw new IllegalArgumentException();
		if (l.getOffset()!= r.getOffset())	throw new IllegalArgumentException("diff offset l.o="+l.getOffset()+ "r.o="+ r.getOffset());
		/*
		 * set start and end 
		 * it is possible that left is stored after right
		 * in the storage
		 */
		int v1 = l.getStart();
		int v2 = r.getStart();
		start = v1< v2 ? v1 : v2;
		v1 = l.getEnd();
		v2 = r.getEnd();
		end = v1>v2 ? v1 : v2;
		incr = l.getIncr()/2; 
		loglen += 1;
		setOffset(l.getOffset()+r.getOffset());
	}
	
	/**
	 * Instantiates a new zip power list.
	 *
	 * @param l the l
	 * @param r the r
	 * @param new_offset the new offset
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	////////////////////////////////////////////////////////////////////////////////////////
	public ZipPowerList(PowerList<T> l, PowerList<T> r, int new_offset)
			throws IllegalArgumentException
	{//transform two neighbor powerlists into one
		//pre: left.base = right.base && left.index +1 = right.index && left.end+1 = right.end && left.incr = right.incr
		//pre l.incr = r.incr
		
		super(l.getBase(),0,0,1);
		if (l.getBase()!= r.getBase())	throw new IllegalArgumentException();

		int v1 = l.getStart();
		int v2 = r.getStart();
		start = v1< v2 ? v1 : v2;
		v1 = l.getEnd();
		v2 = r.getEnd();
		end = v1>v2 ? v1 : v2;
		incr = l.getIncr()/2; 
		loglen += 1;
		setOffset(l.getOffset()+r.getOffset()+new_offset);
	//	setOffset(2*l.getOffset()+new_offset); //ignore r.offset
	}
	////////////////////////////////////////////////////////////////////////////////////////
//	public void setFrom2Lists(IPowerList<T> l, IPowerList<T> r, int order)		throws IllegalArgumentException
//	{//transform two neighbor powerlists into one
//		//pre: this.base=left.base = right.base && left.index +1 = right.index && left.end+1 = right.end && left.incr = right.incr
//		//pre l.offset = r.offset
//		//pre l.incr = r.incr
//		if (l.getBase()!= r.getBase() && l.getBase()!= this.getBase())		throw new IllegalArgumentException();
//		int v1 = l.getStart();
//		int v2 = r.getStart();
//		this.start = v1< v2 ? v1 : v2;
//		v1 = l.getEnd();
//		v2 = r.getEnd();
//		this.end = v1>v2 ? v1 : v2;
//		
//		setOffset(l.getOffset()+r.getOffset());
//		this.offset = l.getOffset() + 1; //the first element will be from r list
//		loglen += 1;
//		System.out.println("after set left="+l);
//		System.out.println("after set right="+r);
//		System.out.println("after set zip ="+this);
//	}
	////////////////////////////////////////////////////////////////////////////////////////
//	public void setFrom2Lists(IPowerList<T> l, IPowerList<T> r)		throws IllegalArgumentException
//	{//transform two neighbor powerlists into one
//		//pre: this.base=left.base = right.base && 
//		//&& left.start+left.incr/2 = right.strat || right.start+left.incr/2 = left.strat
//		//&& left.end+left.incr/2 = right.end || right.end+left.incr/2 = left.end
//		//&&left.incr = right.incr
//		//pre l.offset = r.offset
//		//pre l.incr = r.incr
//		
//		if (l.getBase()!= r.getBase() && l.getOffset()!= r.getOffset()&& l.getIncr()!= r.getIncr())		throw new IllegalArgumentException();
//		int v1 = l.getStart();
//		int v2 = r.getStart();
//		
//		incr = l.getIncr()/2; 
//	//	setOffset( l.getOffset()+r.getOffset()); 
//		
//		start = v1< v2 ? v1 : v2;
//		
//		v1 = l.getEnd();
//		v2 = r.getEnd();
//		
//		end = v1>v2 ? v1 : v2;
//		
//		
//		loglen += 1;
////		
////		System.out.println("after set left="+l);
////		System.out.println("after set right="+r);
////		System.out.println("after set zip ="+this);
//
//	}
	/**
	 * Creates the copy.
	 *
	 * @return the power list
	 */
	////////////////////////////////////////////////////////////////////////////////////////	
	public PowerList<T> create_copy(){
		return new ZipPowerList<T>((PowerList<T>)super.create_copy());
	}
	////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Gets the left.
	 *
	 * @return the left
	 */
	public IPowerList<T> getLeft(){
		{ 
			ZipPowerList<T> l ;
//			System.out.println("in getLeft "+start+ " "+end +" "+incr+ " "+offset+this);
			if(Math.abs(offset)%2==0){
				l = new ZipPowerList<T>(getBase(), start, end-incr, incr*2, offset/2);
			}
			else{
				int off = offset/2;
				// off+=(int)(1)*Math.signum(offset);
				if (offset< 0) off-=1;
				l= new ZipPowerList<T>(getBase(), start+incr, end, incr*2, off);
			}
			l.loglen--;
		//	System.out.println("in getLeft result="+l);
			return l;
		}
	}

	/**
	 * Gets the right.
	 *
	 * @return the right
	 */
	public IPowerList<T> getRight(){
		{	
			 ZipPowerList<T> r ;
	//		 System.out.println("in getRight "+start+ " "+end +" "+incr+ " "+offset+this);
			 if(Math.abs(offset)%2==0)
				 r = new ZipPowerList<T>(getBase(), start+incr, end, incr*2, offset/2);
			 else{
				 int off = offset/2;
				if (offset>0) off+=1;
				 r= new ZipPowerList<T>(getBase(), start, end-incr, incr*2, off);
			 }
			 r.loglen--;
			// System.out.println("in getRight result="+r);
			// System.out.println("getRight "+r.start+ " "+r.end +" "+r.incr+ " "+r.offset);
			 return r;
		}
	}
	
	

			
///////////////////////////////////////////////////////////////////////////////////////////////////////
	
/**
 * why shiftRight and shiftLeft are needed?
 * for the case when we have list of lists.
 *
 * @param e the e
 */
		public void shiftRight(T e ){
			ShiftRight<T> sf = new ShiftRight<T>(this, e);
			this.set(sf.compute());
		}
		
		/**
		 * Shift left.
		 *
		 * @param e the e
		 */
		public void shiftLeft(T e ){
			ShiftLeft<T> sf = new ShiftLeft<T>(this, e);
			sf.compute();
		}

		//////////////////////////////////////////////////////////
//	public String toString(){
//		if (this.getValue().getClass() == BasicList.class){
//			String s= new String();
//			s+=" [";
//			Iterator<?> it = iterator();
//			int n = getLen();
//			Iterator sub_it [] = new Iterator[n];
//			int i=0;
//			while( it.hasNext() ){
//				BasicList<T> sublist = (BasicList<T>)it.next();
//				sub_it[i++] = sublist.iterator();
//			}
//			while( sub_it[0].hasNext() ){
//				for(i=0;i<n;i++){
//					s+=sub_it[i].next()+" ";
//				}
//			}
//
//			s+= "] offset="; s+=offset;s+="incr="+incr;
//			s+="  indices (";
//			LIterator<T> lit = (LIterator<T>)iterator();
//			while( lit.hasNext() ){
//				s+=lit.getCurrentPos()+ " ";
//				lit.next();
//			}
//			s+= ")";
//					
//			return s;
//		}
//		else
//			return super.toString();
//	}
//////////////////////////////////////////////////////////
/*
* 
* @see basic.BasicList#rotateRight()
* better -> define a PowerList function that rotate the elements
* to assure that the result of this operation is in fact 
* the rotation of the flat list
*/
//////////////////////////////////////////////////////////
/*		public void rotateRight( ){
			offset--;
			if (Math.abs(offset)==getLen()) offset = 0;
			
			if (this.getValue().getClass() == BasicList.class){
				BasicList first_el = (BasicList)this.getValue(this.getStartPos());
				first_el.rotateRight();
			}
}
//////////////////////////////////////////////////////////
		public void rotateLeft(){
			offset++;
			if (Math.abs(offset)==getLen()) offset = 0;
			if (this.getValue().getClass() == BasicList.class){
				BasicList last_el = (BasicList)this.getValue(this.getEndPos());
				last_el.rotateLeft();
			}
		}
*/
}
	
