package be.gyrbo.checked.stream;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class CheckedBaseStream<S, EX extends Exception> {
	protected final S delegate;
	
	protected CheckedBaseStream(S delegate) {
		this.delegate = delegate;
	}
	
	protected <U> CheckedStream<U, EX> fromStream(Stream<U> delegate) {
		return new CheckedStream<U, EX>(delegate);
	}
	
	protected CheckedIntStream<EX> fromStream(IntStream delegate) {
		return new CheckedIntStream<EX>(delegate);
	}
	
	protected CheckedLongStream<EX> fromStream(LongStream delegate) {
		return new CheckedLongStream<EX>(delegate);
	}
	
	protected CheckedDoubleStream<EX> fromStream(DoubleStream delegate) {
		return new CheckedDoubleStream<EX>(delegate);
	}
}
