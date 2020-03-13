/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedBiPredicate<T, U, EX extends Exception> {

	boolean testOrThrow(T t, U u) throws EX;
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the BiPredicate is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default BiPredicate<T, U> sneakyThrow() {
		return (t, u) -> {
			@SuppressWarnings("unchecked")
			CheckedBiPredicate<T, U, RuntimeException> sneaky = (CheckedBiPredicate<T, U, RuntimeException>) this;
			return sneaky.testOrThrow(t, u);
		};
	}
	
	
	@FunctionalInterface
	public interface Adapter<T, U, EX extends Exception> extends CheckedBiPredicate<T, U, EX>, BiPredicate<T, U> {
		default boolean testOrThrow(T t, U u) throws EX {
			return test(t, u);
		}
		
		default BiPredicate<T, U> sneakyThrow() {
			return this;
		}
	}
	

	public interface Helper<T, U, EX extends Exception> extends CheckedBiPredicate<T, U, EX> {
		
		/// Methods to deal with exceptions
		
		// Convert to a regular BiPredicate
		
		/**
		 * In case an exception occurs, an empty Optional is returned
		 */
		default BiFunction<T, U, Optional<Boolean>> optional() {
			return (t, u) -> {
				try {
					return Optional.of(testOrThrow(t, u));
				} catch (Exception e) {
					return Optional.empty();
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied BiPredicate will be used as the
		 * result. Otherwise the supplied BiPredicate is never evaluated.
		 */
		default CheckedBiPredicate.Adapter<T, U, EX> fallbackTo(BiPredicate<? super T, ? super U> other) {
	        Objects.requireNonNull(other);
	        
			return (t, u) -> {
				try {
					return testOrThrow(t, u);
				} catch (Exception e) {
					return other.test(t, u);
				}
			};
		}
		
		/**
		 * This functions as a short-circuiting or. When an exception occurs during the
		 * evaluation of this BiPredicate, the supplied BiPredicate will be used as the
		 * result.
		 */
		default CheckedBiPredicate.Adapter<T, U, EX> fallbackToOr(BiPredicate<? super T, ? super U> other) {
	        Objects.requireNonNull(other);
	        
			return (t, u) -> {
				try {
					return testOrThrow(t, u) || other.test(t, u);
				} catch (Exception e) {
					return other.test(t, u);
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied value will be used as the
		 * result.
		 */
		default CheckedBiPredicate.Adapter<T, U, EX> orReturn(boolean value) {
			return (t, u) -> {
				try {
					return testOrThrow(t, u);
				} catch (Exception e) {
					return value;
				}
			};
		}
		
		/**
		 * Rethrow any checked exceptions as unchecked exceptions by passing through the supplied mapper. 
		 */
		default CheckedBiPredicate.Adapter<T, U, EX> rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return (t, u) -> {
				try {
					return this.testOrThrow(t, u);
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
		default <EX2 extends Exception> CheckedBiPredicate.Helper<T, U, EX2> rethrow(Function<? super EX, EX2> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return (t, u) -> {
				try {
					return this.testOrThrow(t, u);
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
		default CheckedBiPredicate.Helper<T, U, EX> except(Consumer<? super EX> exceptionHandler) {	
	        Objects.requireNonNull(exceptionHandler);
	        
			return (t, u) -> {
				try {
					return this.testOrThrow(t, u);
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
		default <EX2 extends EX> CheckedBiPredicate.Helper<T, U, EX> except(
				Class<EX2> cls, Consumer<? super EX2> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);
	        
			return (t, u) -> {
				try {
					return this.testOrThrow(t, u);
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
		default CheckedBiPredicate.Helper<T, U, EX> exceptUnchecked(
				Consumer<? super RuntimeException> exceptionHandler) {
			return exceptUnchecked(RuntimeException.class, exceptionHandler);
		}
		
		default <REX extends RuntimeException> CheckedBiPredicate.Helper<T, U, EX> exceptUnchecked(
				Class<REX> cls, Consumer<? super REX> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);        
	        
			return (t, u) -> {
				try {
					return this.testOrThrow(t, u);
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
		default CheckedBiPredicate.Helper<T, U, EX> orTry(CheckedBiPredicate<? super T, ? super U, ? extends EX> other) {
	        Objects.requireNonNull(other);
	        
			return (t, u) -> {
				try {
					return testOrThrow(t, u);
				} catch (Exception e) {
					return other.testOrThrow(t, u);
				}
			};
		}
		
		/// Utility methods from BiPredicate
		
	    /**
	     * Short-circuiting AND operator. Any thrown exceptions are not influenced.
	     */
	    default CheckedBiPredicate.Helper<T, U, EX> and(CheckedBiPredicate<? super T, ? super U, ? extends EX> other) {
	        Objects.requireNonNull(other);
	        
	        return (t, u) -> testOrThrow(t, u) && other.testOrThrow(t, u);
	    }
	
	    /**
	     * Negates the result. Any thrown exceptions are not influenced.
	     */
	    default CheckedBiPredicate.Helper<T, U, EX> negate() {
	        return (t, u) -> !testOrThrow(t, u);
	    }
	
	    /**
		 * Short-circuiting OR operator. Any thrown exceptions are not influenced.
		 * <p>
		 * See fallbackToOr() if you want the supplied
		 *      BiPredicate to also be used  as an alternative in case of exceptions.
		 * <p>
		 * See orTry() if you want the supplied BiPredicate to
		 *      be used <b>only</b> in case of exceptions.
		 */
	    default CheckedBiPredicate.Helper<T, U, EX> or(CheckedBiPredicate<? super T, ? super U, ? extends EX> other) {
	        Objects.requireNonNull(other);
	        
	        return (t, u) -> testOrThrow(t, u) || other.testOrThrow(t, u);
	    }
	}
}
