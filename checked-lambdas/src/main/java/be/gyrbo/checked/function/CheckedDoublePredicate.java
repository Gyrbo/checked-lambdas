/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;

@FunctionalInterface
public interface CheckedDoublePredicate<EX extends Exception> {

	boolean testOrThrow(double t) throws EX;
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the predicate is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default DoublePredicate sneakyThrow() {
		return t -> {
			@SuppressWarnings("unchecked")
			CheckedDoublePredicate<RuntimeException> sneaky = (CheckedDoublePredicate<RuntimeException>) this;
			return sneaky.testOrThrow(t);
		};
	}
	
	
	@FunctionalInterface
	public interface Adapter<EX extends Exception> extends CheckedDoublePredicate<EX>, DoublePredicate {
		default boolean testOrThrow(double t) throws EX {
			return test(t);
		}
		
		default DoublePredicate sneakyThrow() {
			return this;
		}
	}
	

	public interface Helper<EX extends Exception> extends CheckedDoublePredicate<EX> {
		
		/// Methods to deal with exceptions
		
		// Convert to a regular Predicate
		
		/**
		 * In case an exception occurs, an empty Optional is returned
		 */
		default DoubleFunction<Optional<Boolean>> optional() {
			return t -> {
				try {
					return Optional.of(testOrThrow(t));
				} catch (Exception e) {
					return Optional.empty();
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied predicate will be used as the
		 * result. Otherwise the supplied predicate is never evaluated.
		 */
		default CheckedDoublePredicate.Adapter<EX> fallbackTo(DoublePredicate other) {
	        Objects.requireNonNull(other);
	        
			return t -> {
				try {
					return testOrThrow(t);
				} catch (Exception e) {
					return other.test(t);
				}
			};
		}
		
		/**
		 * This functions as a short-circuiting or. When an exception occurs during the
		 * evaluation of this predicate, the supplied predicate will be used as the
		 * result.
		 */
		default CheckedDoublePredicate.Adapter<EX> fallbackToOr(DoublePredicate other) {
	        Objects.requireNonNull(other);
	        
			return t -> {
				try {
					return testOrThrow(t) || other.test(t);
				} catch (Exception e) {
					return other.test(t);
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied value will be used as the
		 * result.
		 */
		default CheckedDoublePredicate.Adapter<EX> orReturn(boolean value) {
			return t -> {
				try {
					return testOrThrow(t);
				} catch (Exception e) {
					return value;
				}
			};
		}
		
		/**
		 * Rethrow any checked exceptions as unchecked exceptions by passing through the supplied mapper. 
		 */
		default CheckedDoublePredicate.Adapter<EX> rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return t -> {
				try {
					return this.testOrThrow(t);
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
		default <EX2 extends Exception> CheckedDoublePredicate.Helper<EX2> rethrow(Function<? super EX, EX2> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return t -> {
				try {
					return this.testOrThrow(t);
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
		default CheckedDoublePredicate.Helper<EX> except(Consumer<? super EX> exceptionHandler) {	
	        Objects.requireNonNull(exceptionHandler);
	        
			return t -> {
				try {
					return this.testOrThrow(t);
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
		default <EX2 extends EX> CheckedDoublePredicate.Helper<EX> except(
				Class<EX2> cls, Consumer<? super EX2> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);
	        
			return t -> {
				try {
					return this.testOrThrow(t);
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
		default CheckedDoublePredicate.Helper<EX> exceptUnchecked(
				Consumer<? super RuntimeException> exceptionHandler) {
			return exceptUnchecked(RuntimeException.class, exceptionHandler);
		}
		
		default <REX extends RuntimeException> CheckedDoublePredicate.Helper<EX> exceptUnchecked(
				Class<REX> cls, Consumer<? super REX> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);        
	        
			return t -> {
				try {
					return this.testOrThrow(t);
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
		default CheckedDoublePredicate.Helper<EX> orTry(CheckedDoublePredicate<? extends EX> other) {
	        Objects.requireNonNull(other);
	        
			return t -> {
				try {
					return testOrThrow(t);
				} catch (Exception e) {
					return other.testOrThrow(t);
				}
			};
		}
		
		/// Utility methods from Predicate
		
	    /**
	     * Short-circuiting AND operator. Any thrown exceptions are not influenced.
	     */
	    default CheckedDoublePredicate.Helper<EX> and(CheckedDoublePredicate<? extends EX> other) {
	        Objects.requireNonNull(other);
	        
	        return t -> testOrThrow(t) && other.testOrThrow(t);
	    }
	
	    /**
	     * Negates the result. Any thrown exceptions are not influenced.
	     */
	    default CheckedDoublePredicate.Helper<EX> negate() {
	        return t -> !testOrThrow(t);
	    }
	
	    /**
		 * Short-circuiting OR operator. Any thrown exceptions are not influenced.
		 * <p>
		 * See fallbackToOr() if you want the supplied
		 *      predicate to also be used  as an alternative in case of exceptions.
		 * <p>
		 * See orTry() if you want the supplied predicate to
		 *      be used <b>only</b> in case of exceptions.
		 */
	    default CheckedDoublePredicate.Helper<EX> or(CheckedDoublePredicate<? extends EX> other) {
	        Objects.requireNonNull(other);
	        
	        return t -> testOrThrow(t) || other.testOrThrow(t);
	    }
	}
}
