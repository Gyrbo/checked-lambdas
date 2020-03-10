/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;


@FunctionalInterface
public interface CheckedIntUnaryOperator<EX extends Exception> {

	int applyOrThrow(int t) throws EX;
	
	/// Methods to deal with exceptions
	
	// Convert to a regular Predicate
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the function is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default IntUnaryOperator sneakyThrow() {
		return t -> {
			@SuppressWarnings("unchecked")
			CheckedIntUnaryOperator<RuntimeException> sneaky = (CheckedIntUnaryOperator<RuntimeException>) this;
			return sneaky.applyOrThrow(t);
		};
	}	
	
	@FunctionalInterface
	public interface Adapter<EX extends Exception> extends CheckedIntUnaryOperator<EX>, IntUnaryOperator {
		default int applyOrThrow(int t) throws EX {
			return applyOrThrow(t);
		}
		
		default IntUnaryOperator sneakyThrow() {
			return this;
		}
	}
	
	public interface Helper<EX extends Exception> extends CheckedIntUnaryOperator<EX> {
	
		/**
		 * In case an exception occurs, an empty Optional is returned
		 */
		default IntFunction<OptionalInt> optional() {
			return t -> {
				try {
					return OptionalInt.of(applyOrThrow(t));
				} catch (Exception e) {
					return OptionalInt.empty();
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied function will be used as the
		 * result. Otherwise the supplied function is never evaluated.
		 */
		default CheckedIntUnaryOperator.Adapter<EX> fallbackTo(IntUnaryOperator other) {
	        Objects.requireNonNull(other);
	        
			return t -> {
				try {
					return applyOrThrow(t);
				} catch (Exception e) {
					return other.applyAsInt(t);
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied value will be used as the
		 * result.
		 */
		default CheckedIntUnaryOperator.Adapter<EX> orReturn(int value) {
			return t -> {
				try {
					return applyOrThrow(t);
				} catch (Exception e) {
					return value;
				}
			};
		}
		
		/**
		 * Rethrow any checked exceptions as unchecked exceptions by passing through the supplied mapper. 
		 */
		default CheckedIntUnaryOperator.Adapter<EX> rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return t -> {
				try {
					return this.applyOrThrow(t);
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
		default <EX2 extends Exception> CheckedIntUnaryOperator.Helper<EX2> rethrow(Function<? super EX, EX2> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return t -> {
				try {
					return this.applyOrThrow(t);
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
		default CheckedIntUnaryOperator.Helper<EX> except(Consumer<? super EX> exceptionHandler) {	
	        Objects.requireNonNull(exceptionHandler);
	        
			return t -> {
				try {
					return this.applyOrThrow(t);
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
		default <EX2 extends EX> CheckedIntUnaryOperator.Helper<EX> except(
				Class<EX2> cls, Consumer<? super EX2> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);
	        
			return t -> {
				try {
					return this.applyOrThrow(t);
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
		default CheckedIntUnaryOperator.Helper<EX> exceptUnchecked(
				Consumer<? super RuntimeException> exceptionHandler) {
			return exceptUnchecked(RuntimeException.class, exceptionHandler);
		}
		
		default <REX extends RuntimeException> CheckedIntUnaryOperator.Helper<EX> exceptUnchecked(
				Class<REX> cls, Consumer<? super REX> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);        
	        
			return t -> {
				try {
					return this.applyOrThrow(t);
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
		default CheckedIntUnaryOperator.Helper<EX> orTry(CheckedIntUnaryOperator<? extends EX> other) {
	        Objects.requireNonNull(other);
	        
			return t -> {
				try {
					return applyOrThrow(t);
				} catch (Exception e) {
					return other.applyOrThrow(t);
				}
			};
		}
	}
}
