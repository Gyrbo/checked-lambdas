/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongToDoubleFunction;

@FunctionalInterface
public interface CheckedLongToDoubleFunction<EX extends Exception> {

	double applyAsDouble(long t) throws EX;
	
	/// Methods to deal with exceptions
	
	// Convert to a regular Predicate
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the function is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default LongToDoubleFunction sneakyThrow() {
		return t -> {
			@SuppressWarnings("unchecked")
			CheckedLongToDoubleFunction<RuntimeException> sneaky = (CheckedLongToDoubleFunction<RuntimeException>) this;
			return sneaky.applyAsDouble(t);
		};
	}
	
	/**
	 * In case an exception occurs, an empty Optional is returned
	 */
	default LongFunction<OptionalDouble> optional() {
		return t -> {
			try {
				return OptionalDouble.of(applyAsDouble(t));
			} catch (Exception e) {
				return OptionalDouble.empty();
			}
		};
	}
	
	/**
	 * In case an exception occurs, the supplied function will be used as the
	 * result. Otherwise the supplied function is never evaluated.
	 */
	default LongToDoubleFunction fallbackTo(LongToDoubleFunction other) {
        Objects.requireNonNull(other);
        
		return t -> {
			try {
				return applyAsDouble(t);
			} catch (Exception e) {
				return other.applyAsDouble(t);
			}
		};
	}
	
	/**
	 * In case an exception occurs, the supplied value will be used as the
	 * result.
	 */
	default LongToDoubleFunction orReturn(double value) {
		return t -> {
			try {
				return applyAsDouble(t);
			} catch (Exception e) {
				return value;
			}
		};
	}
	
	/**
	 * Rethrow any checked exceptions as unchecked exceptions by passing through the supplied mapper. 
	 */
	default LongToDoubleFunction rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
        Objects.requireNonNull(mapper);
        
		return t -> {
			try {
				return this.applyAsDouble(t);
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
	default <EX2 extends Exception> CheckedLongToDoubleFunction<EX2> rethrow(Function<? super EX, EX2> mapper) {
        Objects.requireNonNull(mapper);
        
		return t -> {
			try {
				return this.applyAsDouble(t);
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
	default CheckedLongToDoubleFunction<EX> except(Consumer<? super EX> exceptionHandler) {	
        Objects.requireNonNull(exceptionHandler);
        
		return t -> {
			try {
				return this.applyAsDouble(t);
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
	default <EX2 extends EX> CheckedLongToDoubleFunction<EX> except(
			Class<EX2> cls, Consumer<? super EX2> exceptionHandler) {
        Objects.requireNonNull(cls);
        Objects.requireNonNull(exceptionHandler);
        
		return t -> {
			try {
				return this.applyAsDouble(t);
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
	default CheckedLongToDoubleFunction<EX> exceptUnchecked(
			Consumer<? super RuntimeException> exceptionHandler) {
		return exceptUnchecked(RuntimeException.class, exceptionHandler);
	}
	
	default <REX extends RuntimeException> CheckedLongToDoubleFunction<EX> exceptUnchecked(
			Class<REX> cls, Consumer<? super REX> exceptionHandler) {
        Objects.requireNonNull(cls);
        Objects.requireNonNull(exceptionHandler);        
        
		return t -> {
			try {
				return this.applyAsDouble(t);
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
	default CheckedLongToDoubleFunction<EX> orTry(CheckedLongToDoubleFunction<? extends EX> other) {
        Objects.requireNonNull(other);
        
		return t -> {
			try {
				return applyAsDouble(t);
			} catch (Exception e) {
				return other.applyAsDouble(t);
			}
		};
	}
}
