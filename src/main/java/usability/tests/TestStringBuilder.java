package usability.tests;

import java.util.stream.Stream;

public class TestStringBuilder {
	public static void main(String[] args) {

		Stream<String> stringStream = Stream.of("a", "b", "c", "d", "e", "f");

		String result = stringStream.parallel().reduce(new StringBuilder(),
				(builder, s) -> new StringBuilder(builder).append(s), (builder, s) -> builder.append(s)).toString();
		System.err.println(result);

	}
}
