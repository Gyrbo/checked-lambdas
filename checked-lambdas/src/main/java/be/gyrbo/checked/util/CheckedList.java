package be.gyrbo.checked.util;

import java.util.List;

import be.gyrbo.checked.Checked;
import be.gyrbo.checked.function.CheckedConsumer;
import be.gyrbo.checked.function.CheckedPredicate;
import be.gyrbo.checked.function.CheckedUnaryOperator;
import be.gyrbo.checked.stream.CheckedStream;

public class CheckedList<T> {
	private final List<T> delegate;

	private CheckedList(List<T> delegate) {
		this.delegate = delegate;
	}
	
	public static <T> CheckedList<T> of(List<T> delegate) {
		return new CheckedList<T>(delegate);
	}

	public <EX extends Exception> void forEach(CheckedConsumer<? super T, EX> action) throws EX {
		delegate.forEach(action.sneakyThrow());
	}

	public <EX extends Exception> void replaceAll(CheckedUnaryOperator<T, EX> operator) throws EX {
		delegate.replaceAll(operator.sneakyThrow());
	}

	public <EX extends Exception> boolean removeIf(CheckedPredicate<? super T, EX> filter) throws EX {
		return delegate.removeIf(filter.sneakyThrow());
	}

	public <EX extends Exception> CheckedStream<T, EX> stream(Class<EX> exception) {
		return Checked.of(delegate.stream(), exception);
	}

}
