package be.gyrbo.checked.util;

import java.util.OptionalInt;
import java.util.function.Supplier;

import be.gyrbo.checked.function.CheckedIntConsumer;
import be.gyrbo.checked.function.CheckedIntSupplier;

public class CheckedOptionalInt {
	private final OptionalInt delegate;

	private CheckedOptionalInt(OptionalInt delegate) {
		this.delegate = delegate;
	}
	
	public static  CheckedOptionalInt of(OptionalInt delegate) {
		return new CheckedOptionalInt(delegate);
	}
	
	public OptionalInt toOptional() {
		return delegate;
	}

	public int getAsInt() {
		return delegate.getAsInt();
	}

	public boolean isPresent() {
		return delegate.isPresent();
	}

	public <EX extends Exception> void ifPresent(CheckedIntConsumer<EX> consumer) throws EX {
		delegate.ifPresent(consumer.sneakyThrow());
	}

	public int orElse(int other) {
		return delegate.orElse(other);
	}

	public <EX extends Exception> int orElseGet(CheckedIntSupplier<EX> other) throws EX {
		return delegate.orElseGet(other.sneakyThrow());
	}

	public <X extends Throwable> int orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
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
