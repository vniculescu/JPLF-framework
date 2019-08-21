package Power;
import java.io.Serializable;

import types.AList;
import types.ITiePowerList;



/**
 * The Class TiePowerList.
 *
 * @param <T> the generic type
 */
/*
 * TiePowerList<T> structure knows how to split itself using tie operator
 * It could be created from to "similar" TiePowerList: left and right
 *  meaning that they have (the same base and left.end +1 = right.begin)
 */
public class TiePowerList<T> extends PowerList<T> implements  ITiePowerList<T>, Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6444407337032088097L;


	/**
	 * Instantiates a new tie power list.
	 */
	public TiePowerList(){}
	
	/**
	 * Instantiates a new tie power list.
	 *
	 * @param a the a
	 * @param begin the begin
	 * @param end the end
	 * @param incr the incr
	 * @param offset the offset
	 */
	////////////////////////////////////////////////////////////////////////////////////////
	public TiePowerList(AList<T> a, final int begin, final int end, final int incr,int offset){
		super(a, begin,end,incr, offset);
		//System.out.println(" constr of TieList begin="+begin+" end ="+end+" incr="+incr);
	}

	/**
	 * Instantiates a new tie power list.
	 *
	 * @param p the p
	 */
	public TiePowerList(PowerList<T> p){
		super(p);
	}
	
	/**
	 * Instantiates a new tie power list.
	 *
	 * @param l the l
	 * @param r the r
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	////////////////////////////////////////////////////////////////////////////////////////
	public TiePowerList(PowerList<T> l, PowerList<T> r)
			throws IllegalArgumentException
	{//transform two neighbors powerlist into one
		//pre: left.base = right.base && left.end +1 = right.start && left.incr = right.incr
		
		super(l.getBase(),0,0,1);
		
			if (l.getBase() != r.getBase())		throw new IllegalArgumentException();
			if (!l.isSingleton() && !r.isSingleton()){
				if (l.getEnd()+1 != r.getStart() ||	l.getIncr()!= r.getIncr()) throw new IllegalArgumentException();
				base = l.getBase();
				start = l.getStart();
				end = r.getEnd();
				incr = l.getIncr();
			}
			else{
				if (l.isSingleton() && r.isSingleton()){
					if (l.getStart()+l.getIncr()!= r.getStart()) throw new IllegalArgumentException();
					base = l.getBase();
					start = l.getStart();
					end = r.getStart();
					incr = l.getIncr();
			}
			else{
				throw new IllegalArgumentException();
			}
			}
		loglen += 1;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////	
//	private int getMiddle(){
//		int middle = start;
//		if (getOffset() ==0)
//			middle = (start+end)/2;
//		else{
//			int start_pos = this.getStartPos();
//			int len = this.getLen();
//			len /=2 ;
//			 middle = start_pos;
//			 for (int i= 0; i<len-1; i++)
//				 middle += incr;
//		}
//		return middle;
//	}
//	
//	public IPowerList<T> getLeft(){
//		if (this.isSingleton())
//			return this;
//
//			TiePowerList<T> l = new TiePowerList<T>(getBase(),start, getMiddle(), incr, offset);	
//			return l;
//	}
//	public IPowerList<T> getRight(){
//		if (this.isSingleton())
//		   return this;
//		else
//		{
//			//System.out.println("in getRight"+((start+end)/2+1) +" "+end);
//			TiePowerList<T> r = new TiePowerList<T>(getBase(),getMiddle()+1, end, incr, offset);
//			return r;
//		}
//		}
	/**
	 * Creates the copy.
	 *
	 * @return the power list
	 */
	////////////////////////////////////////////////////////////////////////////////////////
	public PowerList<T> create_copy(){

		return new TiePowerList<T>((PowerList<T>)super.create_copy());
	}
		
}
