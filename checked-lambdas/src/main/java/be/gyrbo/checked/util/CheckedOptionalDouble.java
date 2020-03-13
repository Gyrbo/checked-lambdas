package be.gyrbo.checked.util;

import java.util.OptionalDouble;
import java.util.function.Supplier;

import be.gyrbo.checked.function.CheckedDoubleConsumer;
import be.gyrbo.checked.function.CheckedDoubleSupplier;

public class CheckedOptionalDouble {
	private final OptionalDouble delegate;

	private CheckedOptionalDouble(OptionalDouble delegate) {
		this.delegate = delegate;
	}
	
	public static  CheckedOptionalDouble of(OptionalDouble delegate) {
		return new CheckedOptionalDouble(delegate);
	}
	
	public OptionalDouble toOptional() {
		return delegate;
	}

	public double getAsDouble() {
		return delegate.getAsDouble();
	}

	public boolean isPresent() {
		return delegate.isPresent();
	}

	public <EX extends Exception> void ifPresent(CheckedDoubleConsumer<EX> consumer) throws EX {
		delegate.ifPresent(consumer.sneakyThrow());
	}

	public double orElse(double other) {
		return delegate.orElse(other);
	}

	public <EX extends Exception> double orElseGet(CheckedDoubleSupplier<EX> other) throws EX {
		return delegate.orElseGet(other.sneakyThrow());
	}

	public <X extends Throwable> double orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
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
