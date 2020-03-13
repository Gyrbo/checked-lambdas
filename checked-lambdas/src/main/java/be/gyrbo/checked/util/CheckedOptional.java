package be.gyrbo.checked.util;

import java.util.Optional;
import java.util.function.Supplier;

import be.gyrbo.checked.function.CheckedConsumer;
import be.gyrbo.checked.function.CheckedFunction;
import be.gyrbo.checked.function.CheckedPredicate;
import be.gyrbo.checked.function.CheckedSupplier;

public class CheckedOptional<T> {
	private final Optional<T> delegate;

	private CheckedOptional(Optional<T> delegate) {
		this.delegate = delegate;
	}
	
	public static <T> CheckedOptional<T> of(Optional<T> delegate) {
		return new CheckedOptional<T>(delegate);
	}
	
	public Optional<T> toOptional() {
		return delegate;
	}

	public T get() {
		return delegate.get();
	}

	public boolean isPresent() {
		return delegate.isPresent();
	}

	public <EX extends Exception> void ifPresent(CheckedConsumer<? super T, EX> consumer) throws EX {
		delegate.ifPresent(consumer.sneakyThrow());
	}

	public <EX extends Exception> CheckedOptional<T> filter(CheckedPredicate<? super T, EX> predicate) throws EX {
		return of(delegate.filter(predicate.sneakyThrow()));
	}

	public <U, EX extends Exception> CheckedOptional<U> map(CheckedFunction<? super T, ? extends U, EX> mapper) throws EX {
		return of(delegate.map(mapper.sneakyThrow()));
	}

	public <U, EX extends Exception> CheckedOptional<U> flatMap(CheckedFunction<? super T, Optional<U>, EX> mapper) throws EX {
		return of(delegate.flatMap(mapper.sneakyThrow()));
	}

	public T orElse(T other) {
		return delegate.orElse(other);
	}

	public <EX extends Exception> T orElseGet(CheckedSupplier<? extends T, EX> other) throws EX {
		return delegate.orElseGet(other.sneakyThrow());
	}

	public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
		return delegate.orElseThrow(exceptionSupplier);
	}

	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public String toString() {
		return delegate.toString();
	}
}
