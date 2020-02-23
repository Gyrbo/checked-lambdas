/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunction<T, R, EX extends Exception> {

	R apply(T t) throws EX;
	
	/// Methods to deal with exceptions
	
	// Convert to a regular Predicate
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the function is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default Function<T, R> sneakyThrow() {
		return t -> {
			@SuppressWarnings("unchecked")
			CheckedFunction<T, R, RuntimeException> sneaky = (CheckedFunction<T, R, RuntimeException>) this;
			return sneaky.apply(t);
		};
	}
	
	/**
	 * In case an exception occurs, an empty Optional is returned
	 */
	default Function<T, Optional<R>> optional() {
		return t -> {
			try {
				return Optional.of(apply(t));
			} catch (Exception e) {
				return Optional.empty();
			}
		};
	}
	
	/**
	 * In case an exception occurs, the supplied function will be used as the
	 * result. Otherwise the supplied function is never evaluated.
	 */
	default Function<T, R> fallbackTo(Function<? super T, ? extends R> other) {
        Objects.requireNonNull(other);
        
		return t -> {
			try {
				return apply(t);
			} catch (Exception e) {
				return other.apply(t);
			}
		};
	}
	
	/**
	 * In case an exception occurs, the supplied value will be used as the
	 * result.
	 */
	default Function<T, R> orReturn(R value) {
		return t -> {
			try {
				return apply(t);
			} catch (Exception e) {
				return value;
			}
		};
	}
	
	/**
	 * Rethrow any checked exceptions as unchecked exceptions by passing through the supplied mapper. 
	 */
	default Function<T, R> rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
        Objects.requireNonNull(mapper);
        
		return t -> {
			try {
				return this.apply(t);
			} catch(RuntimeException ex) {
				throw ex;
			} catch (Exception e) {
				@SuppressWarnings("unchecked")
				EX ex = (EX) e;
				throw mapper.apply(ex);
			}
		};
	}
	
	// Convert in some way, but still not fully handled
	/**
	 * Rethrow any checked exceptions by passing through the supplied mapper. 
	 */
	default <EX2 extends Exception> CheckedFunction<T, R, EX2> rethrow(Function<? super EX, EX2> mapper) {
        Objects.requireNonNull(mapper);
        
		return t -> {
			try {
				return this.apply(t);
			} catch(RuntimeException ex) {
				throw ex;
			} catch (Exception e) {
				@SuppressWarnings("unchecked")
				EX ex = (EX) e;
				throw mapper.apply(ex);
			}
		};
	}
	
	/**
	 * Passes any checked exceptions to the supplied exception handler. Afterwards,
	 * the exception is rethrown.
	 */
	default CheckedFunction<T, R, EX> except(Consumer<? super EX> exceptionHandler) {	
        Objects.requireNonNull(exceptionHandler);
        
		return t -> {
			try {
				return this.apply(t);
			} catch(RuntimeException ex) {
				throw ex;
			} catch (Exception e) {
				@SuppressWarnings("unchecked")
				EX ex = (EX) e;
				exceptionHandler.accept(ex);
				throw ex;
			}
		};
	}
	
	/**
	 * Passes any checked exceptions that match the specified type to the supplied
	 * exception handler. Afterwards, the exception is rethrown.
	 */
	default <EX2 extends EX> CheckedFunction<T, R, EX> except(
			Class<EX2> cls, Consumer<? super EX2> exceptionHandler) {
        Objects.requireNonNull(cls);
        Objects.requireNonNull(exceptionHandler);
        
		return t -> {
			try {
				return this.apply(t);
			} catch(RuntimeException ex) {
				throw ex;
			} catch (Exception e) {
				if(cls.isInstance(e)) {
					exceptionHandler.accept(cls.cast(e));					
				}
				@SuppressWarnings("unchecked")
				EX ex = (EX) e;
				throw ex;
			}
		};
	}
	
	/**
	 * Passes any unchecked exceptions to the supplied exception handler.
	 * Afterwards, the exception is rethrown.
	 */
	default CheckedFunction<T, R, EX> exceptUnchecked(
			Consumer<? super RuntimeException> exceptionHandler) {
		return exceptUnchecked(RuntimeException.class, exceptionHandler);
	}
	
	default <REX extends RuntimeException> CheckedFunction<T, R, EX> exceptUnchecked(
			Class<REX> cls, Consumer<? super REX> exceptionHandler) {
        Objects.requireNonNull(cls);
        Objects.requireNonNull(exceptionHandler);        
        
		return t -> {
			try {
				return this.apply(t);
			} catch(RuntimeException e) {
				if(cls.isInstance(e)) {
					exceptionHandler.accept(cls.cast(e));					
				}
				throw e;
			} catch (Exception e) {
				@SuppressWarnings("unchecked")
				EX ex = (EX) e;
				throw ex;
			}
		};
	}
	
	/**
	 * Passes any unchecked exceptions that match the specified type to the supplied
	 * exception handler. Afterwards, the exception is rethrown.
	 */
	default CheckedFunction<T, R, EX> orTry(CheckedFunction<? super T, R, ? extends EX> other) {
        Objects.requireNonNull(other);
        
		return t -> {
			try {
				return apply(t);
			} catch (Exception e) {
				return other.apply(t);
			}
		};
	}
	
	/// Utility methods from Function

    default <V> CheckedFunction<V, R, EX> compose(CheckedFunction<? super V, ? extends T, EX> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> CheckedFunction<T, V, EX> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }
}
