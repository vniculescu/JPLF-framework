package P;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import types.AList;
import types.IPList;



/**
 * The Class TiePList.
 *
 * @param <T> the generic type
 */
/*
 * TiePowerList<T> structure knows how to split itself using tie operator
 * It could be created from to "similar" TiePowerList: left and right
 *  meaning that they have (the same base and left.end +1 = right.begin)
 */
public class TiePList<T> extends PList<T> {
	
	/**
	 * Precond.
	 *
	 * @param sublists the sublists
	 * @param n the n
	 * @return true, if successful
	 */
	/////////////////////////////////////////////////////////////
	protected boolean precond(PList<T>[] sublists, int n){
		int i=1;
		boolean cond = true;
		while (i < n && cond){
			if (sublists[i].getBase() != sublists[0].getBase()) cond = false;
			if (sublists[i].len != sublists[0].len) cond = false;
			if (sublists[i].getIncr() != sublists[0].getIncr()) cond = false;
			if (sublists[i].getStart() - sublists[i].getIncr() != sublists[i-1].getEnd()) cond = false;
			i++;
		}
		return cond;
	}
	
	/**
	 * Instantiates a new tie P list.
	 *
	 * @param a the a
	 * @param begin the begin
	 * @param end the end
	 * @param incr the incr
	 * @param arityList the arity list
	 */
	/////////////////////////////////////////////////////////////
	public TiePList(AList<T> a, final int begin, final int end, final int incr, List<Integer> arityList ){
		super(a, begin,end,incr, arityList);
	}

	/**
	 * Instantiates a new tie P list.
	 *
	 * @param p the p
	 */
	public TiePList(PList<T> p){
		super(p);
	}
	
	/**
	 * Instantiates a new tie P list.
	 *
	 * @param sublists the sublists
	 * @param n the n
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public TiePList(PList<T>[] sublists, int n)	throws IllegalArgumentException
	{//merge n similar TiePList into new one
		//pre: sublists[i].len = sublists[j].len
		//pre: sublists[i].base = sublists[j].base && sublist[i].end + sublist[i].incr  = sublist[i+1].start && sublist[i].incr = sublist[j].incr
		//pre: sublists[i].arityList = sublists[j].arityList
							
		super(sublists[0].getBase(), sublists[0].getStart(),sublists[n-1].getEnd(),sublists[0].getIncr(), sublists[0].arityList);
		//precondition verification
		if (! precond(sublists,  n)) throw new IllegalArgumentException("the PLists could not be merged");
		
		arityList.add(0, n);
		len = n* sublists[0].getLen();
	}
	
	/////////////////////////////////////////////////////////////
	/**
	 * Gets the sub lists.
	 *
	 * @return the sub lists
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	/*
	 * split a TiePList into sublists 
	 * based on the first argument of the arity list
	 */
	 public List<IPList<T>> getSubLists() throws IllegalArgumentException{
		 
		 
		 if ( this.isSingleton()) return null;
		
			 int n = arityList.get(0);
			 if (this.getLen()%n != 0) throw new IllegalArgumentException("the list length should be a multiple of n");
			 
			 ArrayList<IPList<T>> sublists = new ArrayList<IPList<T>>(n) ;
			 
			 int sublists_len = this.getLen() / n; 
			 int ldistance = incr*(sublists_len-1);
			 int lstart = start;
			 int lend = lstart+ldistance;
			 
			 for(int i=0; i<n; i++){
				 ArrayList<Integer> arList = new ArrayList<Integer>(arityList); //create an arity list for each sublist
				 arList.remove(0);
				
				 TiePList<T> l = new TiePList<T> (base, lstart, lend,  incr, arList);
				 		//the start of each list is given by their length
				 l.len = sublists_len;
				 
				 lstart = lend+incr;
				 lend =  lstart+ldistance;
				 
				 sublists.add(l);
			 }
		  return sublists;

		}
	 /////////////////////////////////////////////////////////////
}
