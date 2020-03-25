package be.gyrbo.checked.util;

import java.util.Collection;

import be.gyrbo.checked.Checked;
import be.gyrbo.checked.function.CheckedConsumer;
import be.gyrbo.checked.function.CheckedPredicate;
import be.gyrbo.checked.stream.CheckedStream;

public class CheckedCollection<T> {
	private final Collection<T> delegate;

	private CheckedCollection(Collection<T> delegate) {
		this.delegate = delegate;
	}
	
	public static <T> CheckedCollection<T> of(Collection<T> delegate) {
		return new CheckedCollection<T>(delegate);
	}

	public <EX extends Exception> void forEach(CheckedConsumer<? super T, EX> action) throws EX {
		delegate.forEach(action.sneakyThrow());
	}

	public <EX extends Exception> boolean removeIf(CheckedPredicate<? super T, EX> filter) throws EX {
		return delegate.removeIf(filter.sneakyThrow());
	}

	public <EX extends Exception> CheckedStream<T, EX> stream(Class<EX> exception) {
		return Checked.of(delegate.stream(), exception);
	}

}
