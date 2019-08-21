package jplf.factory;

import java.util.List;

import P.PList;
import P.TiePList;
import P.ZipPList;
import basic.MyArrayList;
import basic.Transformer;
import utils.ParesUtils;

/**
 * A factory for creating PList objects.
 *
 * @param <T> the generic type
 */
public class PListFactory<T> {

	/** The transformer. */
	private final Transformer<T> transformer;

	/**
	 * Instantiates a new p list factory.
	 */
	public PListFactory() {
		transformer = new Transformer<T>();
	}

	/**
	 * To tie P list.
	 *
	 * @param list      the list
	 * @param arityList the arity list
	 * @return the tie P list
	 */
	@SuppressWarnings("unchecked")
	public TiePList<T> toTiePList(List<T> list, List<Integer> arityList) {

		return new TiePList<T>(new MyArrayList<T>((T[]) list.toArray()), 0, list.size() - 1, 1, arityList);
	}

	/**
	 * To tie P list.
	 *
	 * @param list the list
	 * @return the tie P list
	 */
	public TiePList<T> toTiePList(List<T> list) {

		return toTiePList(list, ParesUtils.getPrimeFactors(list.size()));
	}

	/**
	 * To tie distributed P list.
	 *
	 * @param list        the list
	 * @param arityList   the arity list
	 * @param ditribution the ditribution
	 * @return the tie P list
	 */
	@SuppressWarnings("unchecked")
	public TiePList<T> toTieDistributedPList(List<T> list, List<Integer> arityList, int ditribution) {

		final TiePList<T> dList = (TiePList<T>) transformer.toTieDepthList(toTiePList(list, arityList), ditribution)
				.toTiePList();
		return dList;
	}

	/**
	 * To tie distributed P list.
	 *
	 * @param list        the list
	 * @param ditribution the ditribution
	 * @return the tie P list
	 */
	@SuppressWarnings("unchecked")
	public TiePList<T> toTieDistributedPList(List<T> list, int ditribution) {

		final TiePList<T> dList = (TiePList<T>) transformer
				.toTieDepthList(toTiePList(list, ParesUtils.getPrimeFactors(list.size())), ditribution).toTiePList();
		return dList;
	}

	/**
	 * To tie distributed P list.
	 *
	 * @param list        the list
	 * @param ditribution the ditribution
	 * @return the tie P list
	 */
	@SuppressWarnings("unchecked")
	public TiePList<T> toTieDistributedPList(PList<T> list, int ditribution) {

		final TiePList<T> dList = (TiePList<T>) transformer.toTieDepthList(list, ditribution).toTiePList();
		return dList;
	}

	/**
	 * To zip P list.
	 *
	 * @param list      the list
	 * @param arityList the arity list
	 * @return the zip P list
	 */
	@SuppressWarnings("unchecked")
	public ZipPList<T> toZipPList(List<T> list, List<Integer> arityList) {

		return new ZipPList<T>(new MyArrayList<T>((T[]) list.toArray()), 0, list.size() - 1, 1, arityList);
	}

	/**
	 * To zip P list.
	 *
	 * @param list the list
	 * @return the zip P list
	 */
	public ZipPList<T> toZipPList(List<T> list) {

		return toZipPList(list, ParesUtils.getPrimeFactors(list.size()));
	}

	/**
	 * To zip distributed P list.
	 *
	 * @param list        the list
	 * @param arityList   the arity list
	 * @param ditribution the ditribution
	 * @return the zip P list
	 */
	@SuppressWarnings("unchecked")
	public ZipPList<T> toZipDistributedPList(List<T> list, List<Integer> arityList, int ditribution) {

		final ZipPList<T> dList = (ZipPList<T>) transformer.toZipDepthList(toZipPList(list, arityList), ditribution)
				.toZipPList();
		return dList;
	}

	/**
	 * To zip distributed P list.
	 *
	 * @param list        the list
	 * @param ditribution the ditribution
	 * @return the zip P list
	 */
	@SuppressWarnings("unchecked")
	public ZipPList<T> toZipDistributedPList(List<T> list, int ditribution) {

		final ZipPList<T> dList = (ZipPList<T>) transformer
				.toZipDepthList(toZipPList(list, ParesUtils.getPrimeFactors(list.size())), ditribution).toZipPList();
		return dList;
	}

	/**
	 * To zip distributed P list.
	 *
	 * @param list        the list
	 * @param ditribution the ditribution
	 * @return the zip P list
	 */
	@SuppressWarnings("unchecked")
	public ZipPList<T> toZipDistributedPList(PList<T> list, int ditribution) {

		final ZipPList<T> dList = (ZipPList<T>) transformer.toZipDepthList(list, ditribution).toZipPList();
		return dList;
	}

	/**
	 * To zip distributed emtpy P list.
	 *
	 * @param size        the size
	 * @param ditribution the ditribution
	 * @return the zip P list
	 */
	@SuppressWarnings("unchecked")
	public ZipPList<T> toZipDistributedEmtpyPList(int size, int ditribution) {
		ZipPList<T> result = new ZipPList<T>(new MyArrayList<T>(size), 0, size - 1, 1,
				ParesUtils.getPrimeFactors(size));
		final ZipPList<T> dList = (ZipPList<T>) transformer.toZipDepthList(result, ditribution).toZipPList();
		return dList;
	}

	/**
	 * To zip emtpy P list.
	 *
	 * @param size the size
	 * @return the zip P list
	 */
	public ZipPList<T> toZipEmtpyPList(int size) {
		ZipPList<T> result = new ZipPList<T>(new MyArrayList<T>(size), 0, size - 1, 1,
				ParesUtils.getPrimeFactors(size));
		return result;
	}

	/**
	 * To zip distributed emtpy P list.
	 *
	 * @param list        the list
	 * @param ditribution the ditribution
	 * @return the zip P list
	 */
	@SuppressWarnings("unchecked")
	public ZipPList<T> toZipDistributedEmtpyPList(PList<T> list, int ditribution) {

		final ZipPList<T> dList = (ZipPList<T>) transformer.toZipDepthList(list, ditribution).toZipPList();
		return dList;
	}

	/**
	 * To tie distributed emtpy P list.
	 *
	 * @param size        the size
	 * @param ditribution the ditribution
	 * @return the tie P list
	 */
	@SuppressWarnings("unchecked")
	public TiePList<T> toTieDistributedEmtpyPList(int size, int ditribution) {
		TiePList<T> result = new TiePList<T>(new MyArrayList<T>(size), 0, size - 1, 1,
				ParesUtils.getPrimeFactors(size));
		final TiePList<T> dList = (TiePList<T>) transformer.toTieDepthList(result, ditribution).toTiePList();
		return dList;
	}

	/**
	 * To tie emtpy P list.
	 *
	 * @param size the size
	 * @return the tie P list
	 */
	public TiePList<T> toTieEmtpyPList(int size) {
		TiePList<T> result = new TiePList<T>(new MyArrayList<T>(size), 0, size - 1, 1,
				ParesUtils.getPrimeFactors(size));
		return result;
	}

	/**
	 * To tie distributed emtpy P list.
	 *
	 * @param list        the list
	 * @param ditribution the ditribution
	 * @return the tie P list
	 */
	@SuppressWarnings("unchecked")
	public TiePList<T> toTieDistributedEmtpyPList(PList<T> list, int ditribution) {

		final TiePList<T> dList = (TiePList<T>) transformer.toTieDepthList(list, ditribution).toTiePList();
		return dList;
	}

}
