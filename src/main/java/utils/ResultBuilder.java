package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import P.functions.PFilter;
import basic.BasicList;
import types.IFunctionExecutor;

/**
 * The Class ResulutBuilder.
 *
 * @param <T> the generic type
 */
public class ResultBuilder<T> {

	/** The executor. */
	IFunctionExecutor<T> executor;

	Object resultObject;

	/**
	 * Instantiates a new resulut builder.
	 *
	 * @param executor the executor
	 */
	public ResultBuilder(IFunctionExecutor<T> executor) {
		this.executor = executor;
	}

	public ResultBuilder(Supplier<IFunctionExecutor<T>> executorSupplier) {
		this.executor = executorSupplier.get();
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public Object computeResult() {
		this.resultObject = executor.compute();
		return resultObject;
	}

	/**
	 * Gets the result value.
	 *
	 * @return the result value
	 */
	@SuppressWarnings("unchecked")
	public T getResultValue() {
		if (FunctionResultType.valueOf(executor.getFunction()).hasValueResult()) {
			return (T) resultObject;
		}
		throw new InvalidReturnTypeException("Function " + executor.getFunction().getClass().getSimpleName()
				+ " does not return a value as result!");
	}

	/**
	 * Gets the result list.
	 *
	 * @return the result list
	 */
	@SuppressWarnings("unchecked")
	public List<T> getResultList() {
		if (FunctionResultType.valueOf(executor.getFunction()).hasValueResult()) {
			throw new InvalidReturnTypeException("Function " + executor.getFunction().getClass().getSimpleName()
					+ "does not return a list as result!");
		}
		List<T> result = null;
		BasicList<T> list = (BasicList<T>) resultObject;
		T element = list.getValue();
		if (element instanceof BasicList) {
			result = (Arrays.asList(((BasicList<T>) element).getStorage()));
		} else {
			result = Arrays.asList(list.getStorage());
		}
		if (executor.getFunction() instanceof PFilter) {
			return handleFilterResult(result);
		}
		return result;
	}

	/**
	 * Handle filter result.
	 *
	 * @param result the result
	 * @return the list
	 */
	private List<T> handleFilterResult(List<T> result) {
		List<T> filterResult = new ArrayList<T>();
		for (T elem : result) {
			if (elem instanceof Optional) {
				Optional<T> opt = (Optional) elem;
				if (opt.isPresent()) {
					filterResult.add(opt.get());
				}
			}
		}
		return filterResult;
	}

}
