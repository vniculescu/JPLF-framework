package basic;

import java.util.List;

import P.PList;
import P.TiePList;
import P.ZipPList;
import Power.PowerList;
import Power.TiePowerList;
import Power.ZipPowerList;
import types.AList;
import types.IBasicList;
import utils.ParesUtils;

/**
 * The Class Transformer. Transformer is an utility class that is used to
 * transofrm a PowerList or a Plist into a list of lists based on the parameters
 * given to the Transformer method
 *
 * @param <T> the generic type
 */
public class Transformer<T> {

	/**
	 * Instantiates a new transformer.
	 */
	public Transformer() {
	}

	/**
	 * To tie depth list. Transform the list into a list of sublist - based on
	 * concatenation p = the number of sublists
	 * 
	 * @param list the list
	 * @param p    the p
	 * @return the i basic list
	 */
	public IBasicList<BasicList<T>> toTieDepthList(IBasicList<T> list, int p) {

		final int n = list.getLen();
		final int sublist_len = n / p;

		final AList<BasicList<T>> base_list = new MyArrayList<BasicList<T>>();
		BasicList<T> sl;
		int s = list.getStart();
		for (int i = 0; i < (p - 1); i++) {
			sl = new BasicList<T>(list.getBase(), s, (s + sublist_len) - 1, list.getIncr(), 0);// list.getOffset());
			s += sublist_len * list.getIncr();
			base_list.add(sl);
		}
		// !!! rare => p+1 lists
		// if !(p|n) the rest of the elements go into the last sublist
		sl = new BasicList<T>(list.getBase(), s, list.getEnd(), 1);
		base_list.add(sl);
		final IBasicList<BasicList<T>> result = new BasicList<BasicList<T>>(base_list, 0, p - 1, 1);
		return result;
	}

	/**
	 * To tie depth list. Transforms the list into a list of sublist - based on
	 * concatenation p = the number of sublists, the sublists are of type BasicLists
	 *
	 * @param list the list
	 * @param p    the p
	 * @return the tie power list
	 */
	public TiePowerList<BasicList<T>> toTieDepthList(PowerList<T> list, int p) {

		final int n = list.getLen();
		final int sublist_len = n / p;

		final AList<BasicList<T>> base_list = new MyArrayList<BasicList<T>>();
		BasicList<T> sl;
		int s = list.getStart();
		for (int i = 0; i < (p - 1); i++) {
			sl = new BasicList<T>(list.getBase(), s, (s + sublist_len) - 1, list.getIncr(), 0);// list.getOffset());
			s += sublist_len * list.getIncr();
			base_list.add(sl);
		}
		// !!! rare => p+1 lists
		// if !(p|n) the rest of the elements go into the last sublist
		sl = new BasicList<T>(list.getBase(), s, list.getEnd(), 1);
		base_list.add(sl);
		final TiePowerList<BasicList<T>> result = new TiePowerList<BasicList<T>>(base_list, 0, p - 1, 1, 0);
		return result;
	}

	/**
	 * To tie depth power list. Transforms the list into a list of sublist - based
	 * on concatenation p = the number of sublists, the sublists are PowerLists
	 * 
	 * @param list the list
	 * @param p    the p
	 * @return the tie power list
	 */
	public TiePowerList<PowerList<T>> toTieDepthPowerList(PowerList<T> list, int p) {
		final int n = list.getLen();
		final int sublist_len = n / p;

		final AList<PowerList<T>> base_list = new MyArrayList<PowerList<T>>();
		PowerList<T> sl;
		int s = list.getStart();
		for (int i = 0; i < (p - 1); i++) {
			sl = new PowerList<T>(list.getBase(), s, s + ((sublist_len - 1) * list.getIncr()), list.getIncr(), 0);// list.getOffset());
			s += sublist_len * list.getIncr();
			base_list.add(sl);
		}
		// !!! rare => p+1 lists
		// if !(p|n) the rest of the elements go into the last sublist
		sl = new PowerList<T>(list.getBase(), s, list.getEnd(), 1);
		base_list.add(sl);
		final TiePowerList<PowerList<T>> result = new TiePowerList<PowerList<T>>(base_list, 0, p - 1, 1, 0);
		return result;
	}

	/**
	 * To tie flat list. Inverse operation of toDepthList pre: all sublist have the
	 * same base, the same incr, and they are stored in order all offsets = 0
	 * 
	 * @param list the list
	 * @return the i basic list
	 */
	public IBasicList<T> toTieFlatList(IBasicList<BasicList<T>> list) {

		final IBasicList<T> first_list = list.getValue(0);
		final IBasicList<T> last_list = list.getValue(list.getLen() - 1);
		final IBasicList<T> result = new BasicList<T>(first_list.getBase(), first_list.getStart(), last_list.getEnd(),
				last_list.getIncr(), first_list.getOffset());
		// problem if offset != 0
		return result;
	}

	/**
	 * To zip depth list. Transforms the list into a list of sublists - based on zip
	 * operator p = the number of sublists pre: p is a power of two and p less than len. The
	 * sublists are BasicLists
	 * 
	 * @param list the list
	 * @param p    the p
	 * @return the zip power list
	 */
	public ZipPowerList<BasicList<T>> toZipDepthList(PowerList<T> list, int p) {

		if (Integer.bitCount(p) != 1) {
			throw new IllegalArgumentException(
					"Power list has to have the length a power of two (" + list.loglen() + " " + p + ")");
		}
		// int sublist_loglen = loglen - ploglen;
		final int sublist_len = list.getLen() / p;
		final AList<BasicList<T>> base_list = new MyArrayList<BasicList<T>>();
		BasicList<T> sl;
		int s = list.getStart();
		for (int i = 0; i < p; i++) {
			sl = new BasicList<T>(list.getBase(), s, s + ((sublist_len - 1) * list.getIncr() * p), p * list.getIncr(),
					0);// list.getOffset());//?????????????
			s += list.getIncr();
			base_list.add(sl);
		}

		final ZipPowerList<BasicList<T>> result = new ZipPowerList<BasicList<T>>(base_list, 0, p - 1, 1, 0);
		return result;
	}

	/**
	 * To zip depth power list. transform the list into a list of sublists - based
	 * on zip operator p = the number of sublists pre: p is a power of two and p less than
	 * len. The sublists are PowerLists
	 *
	 * @param list the list
	 * @param p    the p
	 * @return the zip power list
	 */
	public ZipPowerList<PowerList<T>> toZipDepthPowerList(PowerList<T> list, int p) {
		if (Integer.bitCount(p) != 1) {
			throw new IllegalArgumentException(
					"Power list has to have the length a power of two (" + list.loglen() + " " + p + ")");
		}
		final int sublist_len = list.getLen() / p;
		final AList<PowerList<T>> base_list = new MyArrayList<PowerList<T>>();
		PowerList<T> sl;
		int s = list.getStart();
		for (int i = 0; i < p; i++) {
			sl = new PowerList<T>(list.getBase(), s, s + ((sublist_len - 1) * list.getIncr() * p), p * list.getIncr(),
					0);// list.getOffset());//?????????????
			s += list.getIncr();
			base_list.add(sl);
		}
		final ZipPowerList<PowerList<T>> result = new ZipPowerList<PowerList<T>>(base_list, 0, p - 1, 1, 0);
		return result;
	}

	/**
	 * To zip flat list. Inverse operation of toDepthList pre: all sublist have the
	 * same base, the same incr, and they are stored in zip order if the offsets of
	 * the sublists are different it not possible to solve just by setting list
	 * parameters - values have to copied into a new storage if the offsets are all
	 * equal o, the final offset is equal to Offset= O*sublist.getLen()+o; O =
	 * offset of the big list
	 *
	 * @param list the list
	 * @return the i basic list
	 */
	public IBasicList<T> toZipFlatList(IBasicList<BasicList<T>> list) {
		final BasicList<T> first_list = list.getValue(0);
		final BasicList<T> last_list = list.getValue(list.getLen() - 1);
		final IBasicList<T> result = new BasicList<T>(first_list.getBase(), first_list.getStart(), last_list.getEnd(),
				first_list.getIncr() / list.getLen(),
				(list.getOffset() * first_list.getLen()) + first_list.getOffset());
		return result;
	}

	/**
	 * To tie depth list. Transforms the list into a list of sublist - based on
	 * concatenation p = the number of sublists
	 *
	 * @param list the list
	 * @param p    the p
	 * @return the tie P list
	 */
	public TiePList<BasicList<T>> toTieDepthList(PList<T> list, int p) {

		final int n = list.getLen();
		final int sublist_len = n / p;

		final AList<BasicList<T>> base_list = new MyArrayList<BasicList<T>>();
		BasicList<T> sl;
		int s = list.getStart();
		for (int i = 0; i < (p - 1); i++) {
			sl = new BasicList<T>(list.getBase(), s, (s + sublist_len) - 1, list.getIncr(), 0);// list.getOffset());
			s += sublist_len * list.getIncr();
			base_list.add(sl);
		}
		// !!! rare => p+1 lists
		// if !(p|n) the rest of the elements go into the last sublist
		sl = new BasicList<T>(list.getBase(), s, list.getEnd(), 1);
		base_list.add(sl);
		final TiePList<BasicList<T>> result = new TiePList<BasicList<T>>(base_list, 0, p - 1, 1,
				ParesUtils.getPrimeFactors(p));
		return result;
	}

	/**
	 * To zip depth list.Transforms the list into a list of sublist - based on
	 * concatenation p = the number of sublists if !(p|n) IllegalArgumentException
	 * is thrown.
	 * 
	 * @param list the list
	 * @param p    the p
	 * @return the zip P list
	 */
	public ZipPList<BasicList<T>> toZipDepthList(PList<T> list, int p) {

		if (list.getLen() % p != 0) {
			throw new IllegalArgumentException(
					"Can`t divide the list into " + p + " sublists! Lenght of the list must be multiply of " + p + ".");
		}
		final AList<BasicList<T>> base_list = new MyArrayList<BasicList<T>>();
		BasicList<T> sl;
		int s = list.getStart();
		int e = list.getEnd();
		for (int i = 0; i < p; i++) {
			sl = new BasicList<T>(list.getBase(), s + i, e - p + i + 1, list.getIncr() * p, 0);
			// list.getOffset());
			base_list.add(sl);
//			System.out.println(sublist_len+"("+(s+i)+","+ (e-p+i+2)+","+(list.getIncr()*p)+")");
		}
		final ZipPList<BasicList<T>> result = new ZipPList<BasicList<T>>(base_list, 0, p - 1, 1,
				ParesUtils.getPrimeFactors(p));
		return result;
	}

	/**
	 * To zip depth P list. Transforms the list into a list of sublist - based on
	 * concatenation p = the number of sublists if !(p|n) IllegalArgumentException
	 * is thrown.
	 * 
	 * @param list the list
	 * @param p    the p
	 * @return the zip P list
	 */
	public ZipPList<PList<T>> toZipDepthPList(PList<T> list, int p) {

		if (list.getLen() % p != 0) {
			throw new IllegalArgumentException(
					"Can`t divide the list into " + p + " sublists! Lenght of the list must be multiply of " + p + ".");
		}
		final int n = list.getLen();
		final int sublist_len = n / p;
		List<Integer> arity = ParesUtils.getPrimeFactors(sublist_len);
		final AList<PList<T>> base_list = new MyArrayList<PList<T>>();
		PList<T> sl;
		int s = list.getStart();
		int e = list.getEnd();
		for (int i = 0; i < p; i++) {
			sl = new PList<T>(list.getBase(), s + i, e - p + i + 2, list.getIncr() * p, arity);
			// list.getOffset());
			base_list.add(sl);
			// System.out.println(sublist_len+"("+(s+i)+","+
			// (e-p+i+2)+","+(list.getIncr()*p)+")");
		}
		final ZipPList<PList<T>> result = new ZipPList<PList<T>>(base_list, 0, p - 1, 1, ParesUtils.getPrimeFactors(p));
		return result;
	}
//////////////////////////////////////////////////////////
//
//	public List<TiePList<T>> toSplitListTie(PList<T> pList) {
//		final int p = ForkJoinPool.getCommonPoolParallelism();
//		final int n = pList.getLen();
//		final int sublist_len = n / p;
//
//		final List<TiePList<T>> base_list = new ArrayList<TiePList<T>>();
//		TiePList<T> sl;
//		int s = pList.getStart();
//		for (int i = 0; i < (p - 1); i++) {
//			sl = new TiePList<T>(pList.getBase(), s, (s + sublist_len) - 1, pList.getIncr(), Arrays.asList(sublist_len));// list.getOffset());
//			s += sublist_len * pList.getIncr();
//			base_list.add(sl);
//		}
//		// !!! rare => p+1 lists
//		// if !(p|n) the rest of the elements go into the last sublist
//		sl = new TiePList<T>(pList.getBase(), s, pList.getEnd(), 1, Arrays.asList((pList.getEnd() - s) + 1));
//		base_list.add(sl);
//		return base_list;
//	}

}
