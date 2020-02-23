/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

@FunctionalInterface
public interface CheckedLongToIntFunction<EX extends Exception> {

	int applyAsInt(long t) throws EX;
	
	/// Methods to deal with exceptions
	
	// Convert to a regular Predicate
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the function is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default LongToIntFunction sneakyThrow() {
		return t -> {
			@SuppressWarnings("unchecked")
			CheckedLongToIntFunction<RuntimeException> sneaky = (CheckedLongToIntFunction<RuntimeException>) this;
			return sneaky.applyAsInt(t);
		};
	}
	
	/**
	 * In case an exception occurs, an empty Optional is returned
	 */
	default LongFunction<OptionalInt> optional() {
		return t -> {
			try {
				return OptionalInt.of(applyAsInt(t));
			} catch (Exception e) {
				return OptionalInt.empty();
			}
		};
	}
	
	/**
	 * In case an exception occurs, the supplied function will be used as the
	 * result. Otherwise the supplied function is never evaluated.
	 */
	default LongToIntFunction fallbackTo(LongToIntFunction other) {
        Objects.requireNonNull(other);
        
		return t -> {
			try {
				return applyAsInt(t);
			} catch (Exception e) {
				return other.applyAsInt(t);
			}
		};
	}
	
	/**
	 * In case an exception occurs, the supplied value will be used as the
	 * result.
	 */
	default LongToIntFunction orReturn(int value) {
		return t -> {
			try {
				return applyAsInt(t);
			} catch (Exception e) {
				return value;
			}
		};
	}
	
	/**
	 * Rethrow any checked exceptions as unchecked exceptions by passing through the supplied mapper. 
	 */
	default LongToIntFunction rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
        Objects.requireNonNull(mapper);
        
		return t -> {
			try {
				return this.applyAsInt(t);
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
	default <EX2 extends Exception> CheckedLongToIntFunction<EX2> rethrow(Function<? super EX, EX2> mapper) {
        Objects.requireNonNull(mapper);
        
		return t -> {
			try {
				return this.applyAsInt(t);
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
	default CheckedLongToIntFunction<EX> except(Consumer<? super EX> exceptionHandler) {	
        Objects.requireNonNull(exceptionHandler);
        
		return t -> {
			try {
				return this.applyAsInt(t);
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
	default <EX2 extends EX> CheckedLongToIntFunction<EX> except(
			Class<EX2> cls, Consumer<? super EX2> exceptionHandler) {
        Objects.requireNonNull(cls);
        Objects.requireNonNull(exceptionHandler);
        
		return t -> {
			try {
				return this.applyAsInt(t);
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
	default CheckedLongToIntFunction<EX> exceptUnchecked(
			Consumer<? super RuntimeException> exceptionHandler) {
		return exceptUnchecked(RuntimeException.class, exceptionHandler);
	}
	
	default <REX extends RuntimeException> CheckedLongToIntFunction<EX> exceptUnchecked(
			Class<REX> cls, Consumer<? super REX> exceptionHandler) {
        Objects.requireNonNull(cls);
        Objects.requireNonNull(exceptionHandler);        
        
		return t -> {
			try {
				return this.applyAsInt(t);
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
	default CheckedLongToIntFunction<EX> orTry(CheckedLongToIntFunction<? extends EX> other) {
        Objects.requireNonNull(other);
        
		return t -> {
			try {
				return applyAsInt(t);
			} catch (Exception e) {
				return other.applyAsInt(t);
			}
		};
	}
}
