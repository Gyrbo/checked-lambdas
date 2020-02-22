/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedPredicate<T, EX extends Exception> {

	boolean test(T t) throws EX;
	
	/// Methods to deal with exceptions
	
	// Convert to a regular Predicate
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the predicate is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default Predicate<T> sneakyThrow() {
		return t -> {
			@SuppressWarnings("unchecked")
			CheckedPredicate<T, RuntimeException> sneaky = (CheckedPredicate<T, RuntimeException>) this;
			return sneaky.test(t);
		};
	}
	
	/**
	 * In case an exception occurs, an empty Optional is returned
	 */
	default Function<T, Optional<Boolean>> optional() {
		return t -> {
			try {
				return Optional.of(test(t));
			} catch (Exception e) {
				return Optional.empty();
			}
		};
	}
	
	/**
	 * In case an exception occurs, the supplied predicate will be used as the
	 * result. Otherwise the supplied predicate is never evaluated.
	 */
	default Predicate<T> fallbackTo(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        
		return t -> {
			try {
				return test(t);
			} catch (Exception e) {
				return predicate.test(t);
			}
		};
	}
	
	/**
	 * This functions as a short-circuiting or. When an exception occurs during the
	 * evaluation of this predicate, the supplied predicate will be used as the
	 * result.
	 */
	default Predicate<T> fallbackToOr(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        
		return t -> {
			try {
				return test(t) || predicate.test(t);
			} catch (Exception e) {
				return predicate.test(t);
			}
		};
	}
	
	/**
	 * In case an exception occurs, the supplied value will be used as the
	 * result.
	 */
	default Predicate<T> fallbackTo(boolean value) {
		return t -> {
			try {
				return test(t);
			} catch (Exception e) {
				return value;
			}
		};
	}
	
	/**
	 * Rethrow any checked exceptions as unchecked exceptions by passing through the supplied mapper. 
	 */
	default Predicate<T> rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
        Objects.requireNonNull(mapper);
        
		return t -> {
			try {
				return this.test(t);
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
	default <EX2 extends Exception> CheckedPredicate<T, EX2> rethrow(Function<? super EX, EX2> mapper) {
        Objects.requireNonNull(mapper);
        
		return t -> {
			try {
				return this.test(t);
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
	default CheckedPredicate<T, EX> except(Consumer<? super EX> exceptionHandler) {	
        Objects.requireNonNull(exceptionHandler);
        
		return t -> {
			try {
				return this.test(t);
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
	default <EX2 extends EX> CheckedPredicate<T, EX> except(
			Class<EX2> cls, Consumer<? super EX2> exceptionHandler) {
        Objects.requireNonNull(cls);
        Objects.requireNonNull(exceptionHandler);
        
		return t -> {
			try {
				return this.test(t);
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
	default CheckedPredicate<T, EX> exceptUnchecked(
			Consumer<? super RuntimeException> exceptionHandler) {
		return exceptUnchecked(RuntimeException.class, exceptionHandler);
	}
	
	default <REX extends RuntimeException> CheckedPredicate<T, EX> exceptUnchecked(
			Class<REX> cls, Consumer<? super REX> exceptionHandler) {
        Objects.requireNonNull(cls);
        Objects.requireNonNull(exceptionHandler);        
        
		return t -> {
			try {
				return this.test(t);
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
	default CheckedPredicate<T, EX> orTry(CheckedPredicate<? super T, ? extends EX> predicate) {
        Objects.requireNonNull(predicate);
        
		return t -> {
			try {
				return test(t);
			} catch (Exception e) {
				return predicate.test(t);
			}
		};
	}
	
	/// Utility methods from Predicate
	
    /**
     * Short-circuiting AND operator. Any thrown exceptions are not influenced.
     */
    default CheckedPredicate<T, EX> and(CheckedPredicate<? super T, ? extends EX> predicate) {
        Objects.requireNonNull(predicate);
        
        return t -> test(t) && predicate.test(t);
    }

    /**
     * Negates the result. Any thrown exceptions are not influenced.
     */
    default CheckedPredicate<T, EX> negate() {
        return (t) -> !test(t);
    }

    /**
	 * Short-circuiting OR operator. Any thrown exceptions are not influenced.
	 * 
	 * @see #fallbackToOr(Predicate) fallbackToOr() if you want the supplied
	 *      predicate to also be used  as an alternative in case of exceptions.
	 * @see #orTry(CheckedPredicate) orTry() if you want the supplied predicate to
	 *      be used <b>only</b> in case of exceptions.
	 */
    default CheckedPredicate<T, EX> or(CheckedPredicate<? super T, ? extends EX> predicate) {
        Objects.requireNonNull(predicate);
        
        return (t) -> test(t) || predicate.test(t);
    }
	
}