package be.gyrbo.checked.util;

import java.util.OptionalLong;
import java.util.function.Supplier;

import be.gyrbo.checked.function.CheckedLongConsumer;
import be.gyrbo.checked.function.CheckedLongSupplier;

public class CheckedOptionalLong {
	private final OptionalLong delegate;

	private CheckedOptionalLong(OptionalLong delegate) {
		this.delegate = delegate;
	}
	
	public static  CheckedOptionalLong of(OptionalLong delegate) {
		return new CheckedOptionalLong(delegate);
	}
	
	public OptionalLong toOptional() {
		return delegate;
	}

	public long getAsLong() {
		return delegate.getAsLong();
	}

	public boolean isPresent() {
		return delegate.isPresent();
	}

	public <EX extends Exception> void ifPresent(CheckedLongConsumer<EX> consumer) throws EX {
		delegate.ifPresent(consumer.sneakyThrow());
	}

	public long orElse(long other) {
		return delegate.orElse(other);
	}

	public <EX extends Exception> long orElseGet(CheckedLongSupplier<EX> other) throws EX {
		return delegate.orElseGet(other.sneakyThrow());
	}

	public <X extends Throwable> long orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
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
