package P;
import java.util.ArrayList;
import java.util.List;

import types.AList;
import types.IPList;




/**
 * The Class ZipPList.
 *
 * @param <T> the generic type
 */
public class ZipPList<T> extends PList<T> {
		
		/**
		 * Precond.
		 *
		 * @param sublists the sublists
		 * @param n the n
		 * @return true, if successful
		 */
		/////////////////////////////////////////////////////////////
		protected boolean precond(PList<T>[] sublists, int n){
			boolean cond = true;
			if (sublists[0].getIncr() % n != 0 ) cond = false;
			int new_incr = sublists[0].getIncr()/n;
			int i=1;
			while (i < n && cond){
				if (sublists[i].getBase() != sublists[0].getBase()) cond = false;
				if (sublists[i].len != sublists[0].len) cond = false;
				if (sublists[i].getIncr() != sublists[0].getIncr()) cond = false;
				if (sublists[i].getStart() -  new_incr != sublists[i-1].getStart()) cond = false;
				i++;
			}
			return cond;
		}
		
		/**
		 * Instantiates a new zip P list.
		 *
		 * @param a the a
		 * @param begin the begin
		 * @param end the end
		 * @param incr the incr
		 * @param arityList the arity list
		 */
		/////////////////////////////////////////////////////////////
		public ZipPList(AList<T> a, final int begin, final int end, final int incr, List<Integer> arityList ){
			super(a, begin,end,incr, arityList);
		}
		
		/**
		 * Instantiates a new zip P list.
		 *
		 * @param p the p
		 */
		public ZipPList(PList<T> p){
		super(p);
		}
		
		/**
		 * Instantiates a new zip P list.
		 *
		 * @param sublists the sublists
		 * @param n the n
		 * @throws IllegalArgumentException the illegal argument exception
		 */
		public ZipPList(PList<T>[] sublists, int n)	throws IllegalArgumentException
		{//merge n similar ZipPList into new one
		//pre: sublists[i].len = sublists[j].len
		//pre: sublists[i].base = sublists[j].base && sublist[i].incr = sublist[j].incr
		//&&  sublist[i].start + new_incr  = sublist[i+1].start 
		//pre: sublists[i].arityList = sublists[j].arityList
		
			super(sublists[0].getBase(), sublists[0].getStart(),sublists[n-1].getEnd(),sublists[0].getIncr()/n, sublists[0].arityList);
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
		* split a ZipPList into sublists 
		* based on the first argument of the arity list
		*/
		public ArrayList<IPList<T>> getSubLists() throws IllegalArgumentException{
			if ( this.isSingleton()) return null;
			
			int n = arityList.get(0);
			if (this.getLen()%n != 0) throw new IllegalArgumentException("the list's length should be a multiple of n");
			
			ArrayList<IPList<T>> sublists = new ArrayList<IPList<T>>(n) ;
			
			int sublists_len = this.getLen() / n; 
			int lincr = incr*n;
			int lstart = start;
			int lend = end - incr*(n-1);
			
			for(int i=0; i<n; i++){
				ArrayList<Integer> arList = new ArrayList<Integer>(arityList); //create an arrity list for each sublist
				arList.remove(0);
				
				ZipPList<T> l = new ZipPList<T> (base, lstart, lend,  lincr, arList);
				//the start of each list is given by their length
				l.len = sublists_len;
				
				lstart += incr;
				lend +=  incr;
				
				sublists.add(l);
			}
			return sublists;
			
		}
		/////////////////////////////////////////////////////////////
}
	
