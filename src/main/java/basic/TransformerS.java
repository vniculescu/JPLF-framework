package basic;

import Power.PowerList;
import Power.ZipPowerList;
import types.AList;
import types.IBasicList;

//////////////////////////////////////////////////////////
/*
* transform a list into a list of lists
*/
/**
 * The Class TransformerS.
 *
 * @param <T> the generic type
 */
//////////////////////////////////////////////////////////
public class TransformerS<T> {

	/**
	 * Instantiates a new transformer S.
	 */
	private TransformerS() {
	}

	/** The instance. */
	private static TransformerS instance;

	/**
	 * Gets the single instance of TransformerS.
	 *
	 * @return single instance of TransformerS
	 */
	public static TransformerS getInstance() {
		if (instance == null)
			instance = new TransformerS();
		return instance;
	}

	//////////////////////////////////////////////////////////
	/*
	 * transform a list into a list of lists
	 */
	/**
	 * To tie depth list.
	 *
	 * @param list the list
	 * @param p    the p
	 * @return the i basic list
	 */
	//////////////////////////////////////////////////////////
	public IBasicList<BasicList<T>> toTieDepthList(IBasicList<T> list, int p) {
		// transform the list into a list of sublist - based on concatenation
		// p = the number of sublists

		int n = list.getLen();
		int sublist_len = n / p;

		AList<BasicList<T>> base_list = new MyArrayList<BasicList<T>>();
		BasicList<T> sl;
		int s = list.getStart();
		for (int i = 0; i < p - 1; i++) {
			sl = new BasicList<T>(list.getBase(), s, s + sublist_len - 1, list.getIncr(), 0);// list.getOffset());
			s += sublist_len * list.getIncr();
			base_list.add(sl);
		}
		// !!! rare => p+1 lists
		// if !(p|n) the rest of the elements go into the last sublist
		sl = new BasicList<T>(list.getBase(), s, list.getEnd(), 1);
		base_list.add(sl);

		IBasicList<BasicList<T>> result = new BasicList<BasicList<T>>(base_list, 0, p - 1, 1);
		return result;
	}

	/**
	 * To tie flat list.
	 *
	 * @param list the list
	 * @return the i basic list
	 */
	//////////////////////////////////////////////////////////
	public IBasicList<T> toTieFlatList(IBasicList<BasicList<T>> list) {
		// inverse operation of toDepthList
		// pre: all sublist have the same base, the same incr, and they are stored in
		// order
		// all offsets = 0

		IBasicList<T> first_list = (IBasicList<T>) list.getValue(0);
		IBasicList<T> last_list = (IBasicList<T>) list.getValue(list.getLen() - 1);

//			 int off = first_list.getOffset();
//			 while (off > 0){
//				 Iterator<BasicList<T>> it = list.iterator();
//				 while (it.hasNext()){
//					 IBasicList<T> sublist = it.next();
//					 sublist.rotateRight();
//				 }
//				 off--;
//			 }
//			 while (off < 0){
//				 Iterator<BasicList<T>> it = list.iterator();
//				 while (it.hasNext()){
//					 IBasicList<T> sublist = it.next();
//					 sublist.rotateLeft();
//				 }
//				 off++;
//			 }

		IBasicList<T> result = new BasicList<T>(first_list.getBase(), first_list.getStart(), last_list.getEnd(),
				last_list.getIncr(), first_list.getOffset());
		// problem if offset != 0
		return result;
	}

	//////////////////////////////////////////////////////////

	/**
	 * To zip depth list.
	 *
	 * @param list the list
	 * @param p    the p
	 * @return the zip power list
	 */
	public ZipPowerList<BasicList<T>> toZipDepthList(PowerList<T> list, int p) {
		// transform the list into a list of sublists - based on zip operator
		// p = the number of sublists
		// pre: p is a power of two and p < len
		// sublistele nu tin cont de offset !!!
		int ploglen;
		if (Integer.bitCount(p) != 1) // if p is not a power of two - exception!!!
			throw new IllegalArgumentException(
					"Power list has to have the length a power of two (" + list.loglen() + " " + p + ")");
		else
			ploglen = Integer.numberOfTrailingZeros(p);
		// int sublist_loglen = loglen - ploglen;
		int sublist_len = list.getLen() / p;
		AList<BasicList<T>> base_list = new MyArrayList<BasicList<T>>();
		BasicList<T> sl;
		int s = list.getStart();
		for (int i = 0; i < p; i++) {
			sl = new BasicList<T>(list.getBase(), s, s + (sublist_len - 1) * list.getIncr() * p, p * list.getIncr(), 0);// list.getOffset());//?????????????
			s += list.getIncr();
			base_list.add(sl);
		}

		ZipPowerList<BasicList<T>> result = new ZipPowerList<BasicList<T>>(base_list, 0, p - 1, 1, 0);
		return result;
	}

	/**
	 * To zip flat list.
	 *
	 * @param list the list
	 * @return the i basic list
	 */
	//////////////////////////////////////////////////////////
	public IBasicList<T> toZipFlatList(IBasicList<BasicList<T>> list) {
		// inverse operation of toDepthList
		// pre: all sublist have the same base, the same incr, and they are stored in
		// zip order
		// if the offsets of the sublists are different it not possible to solve just by
		// setting list parameters - values have to copied into a new storage
		// if the offsets are all equal o, the final offset is equal to Offset=
		// O*sublist.getLen()+o; O = offset of the big list

		int start_pos = ((IBasicList) list).getStartPos();
		int end_pos = ((IBasicList) list).getEndPos();
		BasicList<T> first_list = (BasicList<T>) list.getValue(0);
		BasicList<T> last_list = (BasicList<T>) list.getValue(list.getLen() - 1);

		// System.out.println( "before off
		// first_list.getIncr()"+first_list.getIncr()+"/first_list.getLen()"+first_list.getLen()+"first
		// list"+first_list);
//				 int off = first_list.getOffset();
//				 while (off > 0){
//					 Iterator<BasicList<T>> it = list.iterator();
//					 while (it.hasNext()){
//						 IBasicList<T> sublist = it.next();
//						 sublist.rotateRight();
//					 }
//					 off--;
//				 }
//				 while (off < 0){
//					 Iterator<BasicList<T>> it = list.iterator();
//					 while (it.hasNext()){
//						 IBasicList<T> sublist = it.next();
//						 sublist.rotateLeft();
//					 }
//					 off++;
//				 }
		// System.out.println( "after off
		// first_list.getIncr()"+first_list.getIncr()+"/first_list.getLen()"+first_list.getLen()+"first
		// list"+first_list);

		IBasicList<T> result = new BasicList<T>(first_list.getBase(), first_list.getStart(), last_list.getEnd(),
				first_list.getIncr() / list.getLen(), list.getOffset() * first_list.getLen() + first_list.getOffset());
		return result;
	}

}
