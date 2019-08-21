package P.Functions;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import P.TiePList;
import P.ZipPList;
import execution.FJ_PFunctionExecutor;
import jplf.factory.PFunctionFactory;
import jplf.factory.PListFactory;
import utils.ParesUtils;
import utils.ResultBuilder;

class BiPMapTest {

	private static final int LIMIT = 500;
	private List<String> inputDataString;

	@SuppressWarnings("resource")
	@BeforeEach
	public void setUp() throws IOException, URISyntaxException {

		final Stream<String> lines = Files.lines(Paths.get(BiPMapTest.class.getResource("war_and_peace.txt").toURI()));
		inputDataString = lines.map(t -> t.split(" ")).flatMap(Arrays::stream).filter(t -> !t.isEmpty()).limit(LIMIT)
				.collect(Collectors.toList());
	}

	@Test
	public void testPBiMapTieDistributed() {
		List<Integer> result = getResultTie(inputDataString, t -> t.length(), 5);
		List<Integer> expected = getExpectedResult(inputDataString, t -> t.length());
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPBiMapTie() {
		List<Integer> result = getResultTie(inputDataString, t -> t.length());
		List<Integer> expected = getExpectedResult(inputDataString, t -> t.length());
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPBiMapZipDistributed() {
		List<Integer> result = getResultZip(inputDataString, t -> t.length(), 5);
		List<Integer> expected = getExpectedResult(inputDataString, t -> t.length());
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPBiMapZip() {
		List<Integer> result = getResultZip(inputDataString, t -> t.length());
		List<Integer> expected = getExpectedResult(inputDataString, t -> t.length());
		Assertions.assertEquals(expected, result);
	}

	private List<Integer> getExpectedResult(List<String> inputList, Function<String, Integer> simpleFunction) {
		List<Integer> result = new ArrayList<Integer>();
		for (String s : inputList) {
			result.add(simpleFunction.apply(s));
		}
		return result;
	}

	private List<Integer> getResultTie(List<String> inputList, Function<String, Integer> simpleFunction, int dist) {
		PListFactory<String> pfactory = new PListFactory<String>();
		TiePList<String> list = pfactory.toTieDistributedPList(inputList, dist);
		PFunctionFactory<String> pFunctionFactory = new PFunctionFactory<String>();
		FJ_PFunctionExecutor<Integer> exec = new FJ_PFunctionExecutor<Integer>(pFunctionFactory.biMap(t -> t.length(),
				list, () -> new PListFactory<Integer>().toTieDistributedEmtpyPList(inputList.size(), dist)));
		ResultBuilder<Integer> rb = new ResultBuilder<Integer>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	private List<Integer> getResultTie(List<String> inputList, Function<String, Integer> simpleFunction) {
		PListFactory<String> pfactory = new PListFactory<String>();
		TiePList<String> list = pfactory.toTiePList(inputList);
		PFunctionFactory<String> pFunctionFactory = new PFunctionFactory<String>();
		FJ_PFunctionExecutor<Integer> exec = new FJ_PFunctionExecutor<Integer>(pFunctionFactory.biMap(t -> t.length(),
				list, () -> new PListFactory<Integer>().toTieEmtpyPList(inputList.size())));
		ResultBuilder<Integer> rb = new ResultBuilder<Integer>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	private List<Integer> getResultZip(List<String> inputList, Function<String, Integer> simpleFunction, int dist) {
		PListFactory<String> pfactory = new PListFactory<String>();
		ZipPList<String> list = pfactory.toZipDistributedPList(inputList, dist);
		PFunctionFactory<String> pFunctionFactory = new PFunctionFactory<String>();
		FJ_PFunctionExecutor<Integer> exec = new FJ_PFunctionExecutor<Integer>(pFunctionFactory.biMap(t -> t.length(),
				list, () -> new PListFactory<Integer>().toZipDistributedEmtpyPList(inputList.size(), dist)));
		ResultBuilder<Integer> rb = new ResultBuilder<Integer>(exec);
		rb.computeResult();
		return rb.getResultList();
	}

	private List<Integer> getResultZip(List<String> inputList, Function<String, Integer> simpleFunction) {
		PListFactory<String> pfactory = new PListFactory<String>();
		ZipPList<String> list = pfactory.toZipPList(inputList);
		PFunctionFactory<String> pFunctionFactory = new PFunctionFactory<String>();
		FJ_PFunctionExecutor<Integer> exec = new FJ_PFunctionExecutor<Integer>(pFunctionFactory.biMap(t -> t.length(),
				list, () -> new PListFactory<Integer>().toZipEmtpyPList(inputList.size())));
		ResultBuilder<Integer> rb = new ResultBuilder<Integer>(exec);
		rb.computeResult();
		return rb.getResultList();
	}



}

/*
 * @startuml abstract class "PFunction<T>" as PFunction_T_ [[java:P.PFunction]]
 * { #List<IPList<T>> p_arg #int no_p_arg #List<List<IPList<T>>> sublists_p_arg
 * #List<List<Object>> s_arg +PFunction(Object[] args) +PFunction(List<PList<T>>
 * p_arg, List<Object> arg) +boolean test_basic_case() +Object basic_case()
 * +void split_arg() +Object combine(List<Object> results)
 * +{abstract}List<PFunction<T>> create_sublists_function() +Object compute() }
 * class Function [[java:utils.Function]] { } Function <|-- PFunction_T_
 * interface "Comparable<PFunction<T>>" as Comparable_PFunction_T__ { }
 * Comparable_PFunction_T__ <|.. PFunction_T_ class "PResultFunction<T>" as
 * PResultFunction_T_ [[java:P.PResultFunction]] { #IPList<T> p_result
 * #List<Object> sub_results +PResultFunction(IPList<T> p_result, Object[] args)
 * +PResultFunction(List<PList<T>> p_arg, List<Object> arg, IPList<T> p_result)
 * +void split_arg() +List<PFunction<T>> create_sublists_function() +IPList<T>
 * compute() +IPList<T> basic_case() +IPList<T> combine(List<Object> o) } class
 * "PFunction<T>" as PFunction_T_ { } PFunction_T_ <|-- PResultFunction_T_ class
 * "BiPMap<T,R>" as BiPMap_T_R_ [[java:P.Functions.BiPMap]] { -Function<T,R>
 * function -IPList<T> list -List<IPList<T>> sub_lsits +BiPMap(Function<T,R>
 * simpleFunction, IPList<T> list, IPList<R> result) +IPList<R> basic_case()
 * +List<PFunction<R>> create_sublists_function() +void split_arg() }
 * 
 * PResultFunction_T_ <|-- BiPMap_T_R_ class "PFilter<T>" as PFilter_T_
 * [[java:P.Functions.PFilter]] { -Predicate<T> predicate +PFilter(Predicate<T>
 * predicate, IPList<T> list) +PFilter(Predicate<T> predicate, IPList<T> arg,
 * IPList<T> res) +IPList<T> basic_case() +List<PFunction<T>>
 * create_sublists_function() } class "PResultFunction<T>" as PResultFunction_T_
 * { } PResultFunction_T_ <|-- PFilter_T_ class "PIdentity<T>" as PIdentity_T_
 * [[java:P.Functions.PIdentity]] { -Object x +PIdentity(IPList<T> list, Object
 * x) +Object compute() +List<PFunction<T>> create_sublists_function() } class
 * "PFunction<T>" as PFunction_T_ { } PFunction_T_ <|-- PIdentity_T_ class
 * "PMap<T>" as PMap_T_ [[java:P.Functions.PMap]] { -Function<T,T> function
 * +PMap(Function<T,T> simpleFunction, IPList<T> arg) +PMap(Function<T,T>
 * simpleFunction, IPList<T> list, IPList<T> res) +IPList<T> basic_case()
 * +List<PFunction<T>> create_sublists_function() } class "PResultFunction<T>"
 * as PResultFunction_T_ { } PResultFunction_T_ <|-- PMap_T_ class "PReduce<T>"
 * as PReduce_T_ [[java:P.Functions.PReduce]] { #BiFunction<T,T,T> operator
 * #List<Object> sub_results +PReduce(BiFunction<T,T,T> operator, IPList<T>
 * p_arg) +T combine(List<Object> results) +Object basic_case()
 * +List<PFunction<T>> create_sublists_function() +void split_arg() } class
 * "PFunction<T>" as PFunction_T_ { } PFunction_T_ <|-- PReduce_T_ class
 * "RectangularFormula<T>" as RectangularFormula_T_
 * [[java:P.Functions.RectangularFormula]] { -List<Object> sub_results
 * -BiFunction<T,T,T> timesOp -T h -int k +RectangularFormula(IPList<T>
 * power_arg, int k, T h) +T combine(List<Object> results) +Object basic_case()
 * +List<PFunction<T>> create_sublists_function() +void split_arg() } class
 * "PFunction<T>" as PFunction_T_ { } PFunction_T_ <|-- RectangularFormula_T_
 * class Function [[java:utils.Function]] { #Object result #List<Object> arg
 * #int no_arg +Function() +Function(Object[] args) +Object compute() +void
 * setArg(Object[] args) +Object getResult() }
 * 
 * @enduml
 */
