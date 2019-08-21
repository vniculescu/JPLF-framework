package jplf.factory;

import java.util.List;

import Power.PowerList;
import Power.TiePowerList;
import Power.ZipPowerList;
import basic.MyArrayList;
import basic.Transformer;

/**
 * A factory for creating PowerList objects.
 *
 * @param <T> the generic type
 */
public class PowerListFactory<T> {

	/** The transformer. */
	private final Transformer<T> transformer;

	/**
	 * Instantiates a new power list factory.
	 */
	public PowerListFactory() {
		transformer = new Transformer<T>();
	}

	/**
	 * To tie power list.
	 *
	 * @param list the list
	 * @return the tie power list
	 */
	@SuppressWarnings("unchecked")
	public TiePowerList<T> toTiePowerList(List<T> list) {

		return new TiePowerList<T>(new MyArrayList<T>((T[]) list.toArray()), 0, list.size() - 1, 1, 0);
	}

	/**
	 * To tie distributed power list.
	 *
	 * @param list        the list
	 * @param ditribution the ditribution
	 * @return the tie power list
	 */
	@SuppressWarnings("unchecked")
	public TiePowerList<T> toTieDistributedPowerList(List<T> list, int ditribution) {

		final TiePowerList<T> dList = (TiePowerList<T>) transformer.toTieDepthList(toTiePowerList(list), ditribution)
				.toTiePowerList();
		return dList;
	}

	/**
	 * To tie distributed power list.
	 *
	 * @param list        the list
	 * @param ditribution the ditribution
	 * @return the tie power list
	 */
	@SuppressWarnings("unchecked")
	public TiePowerList<T> toTieDistributedPowerList(PowerList<T> list, int ditribution) {

		final TiePowerList<T> dList = (TiePowerList<T>) transformer.toTieDepthList(list, ditribution).toTiePowerList();
		return dList;
	}

	/**
	 * To zip power list.
	 *
	 * @param list the list
	 * @return the zip power list
	 */
	@SuppressWarnings("unchecked")
	public ZipPowerList<T> toZipPowerList(List<T> list) {

		return new ZipPowerList<T>(new MyArrayList<T>((T[]) list.toArray()), 0, list.size() - 1, 1, 0);
	}

	/**
	 * To zip distributed power list.
	 *
	 * @param list        the list
	 * @param arityList   the arity list
	 * @param ditribution the ditribution
	 * @return the zip power list
	 */
	@SuppressWarnings("unchecked")
	public ZipPowerList<T> toZipDistributedPowerList(List<T> list, List<Integer> arityList, int ditribution) {

		final ZipPowerList<T> dList = (ZipPowerList<T>) transformer.toZipDepthList(toZipPowerList(list), ditribution)
				.toZipPowerList();
		return dList;
	}

	/**
	 * To zip distributed power list.
	 *
	 * @param list        the list
	 * @param ditribution the ditribution
	 * @return the zip power list
	 */
	@SuppressWarnings("unchecked")
	public ZipPowerList<T> toZipDistributedPowerList(List<T> list, int ditribution) {

		final ZipPowerList<T> dList = (ZipPowerList<T>) transformer.toZipDepthList(toZipPowerList(list), ditribution)
				.toZipPowerList();
		return dList;
	}

	/**
	 * To zip distributed power list.
	 *
	 * @param list        the list
	 * @param ditribution the ditribution
	 * @return the zip power list
	 */
	@SuppressWarnings("unchecked")
	public ZipPowerList<T> toZipDistributedPowerList(PowerList<T> list, int ditribution) {

		final ZipPowerList<T> dList = (ZipPowerList<T>) transformer.toZipDepthList(list, ditribution).toZipPowerList();
		return dList;
	}

	/**
	 * To zip distributed emtpy power list.
	 *
	 * @param size        the size
	 * @param ditribution the ditribution
	 * @return the zip power list
	 */
	@SuppressWarnings("unchecked")
	public ZipPowerList<T> toZipDistributedEmtpyPowerList(int size, int ditribution) {
		ZipPowerList<T> result = new ZipPowerList<T>(new MyArrayList<T>(size), 0, size - 1, 1, 0);
		final ZipPowerList<T> dList = (ZipPowerList<T>) transformer.toZipDepthList(result, ditribution)
				.toZipPowerList();
		return dList;
	}

	/**
	 * To zip emtpy power list.
	 *
	 * @param size the size
	 * @return the zip power list
	 */
	public ZipPowerList<T> toZipEmtpyPowerList(int size) {
		ZipPowerList<T> result = new ZipPowerList<T>(new MyArrayList<T>(size), 0, size - 1, 1, 0);
		return result;
	}

	/**
	 * To zip distributed emtpy power list.
	 *
	 * @param list        the list
	 * @param ditribution the ditribution
	 * @return the zip power list
	 */
	@SuppressWarnings("unchecked")
	public ZipPowerList<T> toZipDistributedEmtpyPowerList(PowerList<T> list, int ditribution) {

		final ZipPowerList<T> dList = (ZipPowerList<T>) transformer.toZipDepthList(list, ditribution).toZipPowerList();
		return dList;
	}

	/**
	 * To tie distributed emtpy power list.
	 *
	 * @param size        the size
	 * @param ditribution the ditribution
	 * @return the tie power list
	 */
	@SuppressWarnings("unchecked")
	public TiePowerList<T> toTieDistributedEmtpyPowerList(int size, int ditribution) {
		TiePowerList<T> result = new TiePowerList<T>(new MyArrayList<T>(size), 0, size - 1, 1, 0);
		final TiePowerList<T> dList = (TiePowerList<T>) transformer.toTieDepthList(result, ditribution)
				.toTiePowerList();
		return dList;
	}

	/**
	 * To tie emtpy power list.
	 *
	 * @param size the size
	 * @return the tie power list
	 */
	public TiePowerList<T> toTieEmtpyPowerList(int size) {
		TiePowerList<T> result = new TiePowerList<T>(new MyArrayList<T>(size), 0, size - 1, 1, 0);
		return result;
	}

	/**
	 * To tie distributed emtpy power list.
	 *
	 * @param list        the list
	 * @param ditribution the ditribution
	 * @return the tie power list
	 */
	@SuppressWarnings("unchecked")
	public TiePowerList<T> toTieDistributedEmtpyPowerList(PowerList<T> list, int ditribution) {

		final TiePowerList<T> dList = (TiePowerList<T>) transformer.toTieDepthList(list, ditribution).toTiePowerList();
		return dList;
	}

}
