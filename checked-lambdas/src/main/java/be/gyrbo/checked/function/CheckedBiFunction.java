/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedBiFunction<T, U, R, EX extends Exception> {

	R applyOrThrow(T t, U u) throws EX;
	
	/// Methods to deal with exceptions
	
	// Convert to a regular Predicate
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the function is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default BiFunction<T, U, R> sneakyThrow() {
		return (t, u) -> {
			@SuppressWarnings("unchecked")
			CheckedBiFunction<T, U, R, RuntimeException> sneaky = (CheckedBiFunction<T, U, R, RuntimeException>) this;
			return sneaky.applyOrThrow(t, u);
		};
	}	
	
	@FunctionalInterface
	public interface Adapter<T, U, R, EX extends Exception> extends CheckedBiFunction<T, U, R, EX>, BiFunction<T, U, R> {
		default R applyOrThrow(T t, U u) throws EX {
			return applyOrThrow(t, u);
		}
		
		default BiFunction<T, U, R> sneakyThrow() {
			return this;
		}
	}
	
	public interface Helper<T, U, R, EX extends Exception> extends CheckedBiFunction<T, U, R, EX> {
	
		/**
		 * In case an exception occurs, an empty Optional is returned
		 */
		default BiFunction<T, U, Optional<R>> optional() {
			return (t, u) -> {
				try {
					return Optional.of(applyOrThrow(t, u));
				} catch (Exception e) {
					return Optional.empty();
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied function will be used as the
		 * result. Otherwise the supplied function is never evaluated.
		 */
		default CheckedBiFunction.Adapter<T, U, R, EX> fallbackTo(BiFunction<? super T, ? super U, ? extends R> other) {
	        Objects.requireNonNull(other);
	        
			return (t, u) -> {
				try {
					return applyOrThrow(t, u);
				} catch (Exception e) {
					return other.apply(t, u);
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied value will be used as the
		 * result.
		 */
		default CheckedBiFunction.Adapter<T, U, R, EX> orReturn(R value) {
			return (t, u) -> {
				try {
					return applyOrThrow(t, u);
				} catch (Exception e) {
					return value;
				}
			};
		}
		
		/**
		 * Rethrow any checked exceptions as unchecked exceptions by passing through the supplied mapper. 
		 */
		default CheckedBiFunction.Adapter<T, U, R, EX> rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return (t, u) -> {
				try {
					return this.applyOrThrow(t, u);
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
		default <EX2 extends Exception> CheckedBiFunction.Helper<T, U, R, EX2> rethrow(Function<? super EX, EX2> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return (t, u) -> {
				try {
					return this.applyOrThrow(t, u);
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
		default CheckedBiFunction.Helper<T, U, R, EX> except(Consumer<? super EX> exceptionHandler) {	
	        Objects.requireNonNull(exceptionHandler);
	        
			return (t, u) -> {
				try {
					return this.applyOrThrow(t, u);
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
		default <EX2 extends EX> CheckedBiFunction.Helper<T, U, R, EX> except(
				Class<EX2> cls, Consumer<? super EX2> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);
	        
			return (t, u) -> {
				try {
					return this.applyOrThrow(t, u);
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
		default CheckedBiFunction.Helper<T, U, R, EX> exceptUnchecked(
				Consumer<? super RuntimeException> exceptionHandler) {
			return exceptUnchecked(RuntimeException.class, exceptionHandler);
		}
		
		default <REX extends RuntimeException> CheckedBiFunction.Helper<T, U, R, EX> exceptUnchecked(
				Class<REX> cls, Consumer<? super REX> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);        
	        
			return (t, u) -> {
				try {
					return this.applyOrThrow(t, u);
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
		default CheckedBiFunction.Helper<T, U, R, EX> orTry(CheckedBiFunction<? super T, ? super U,  R, ? extends EX> other) {
	        Objects.requireNonNull(other);
	        
			return (t, u) -> {
				try {
					return applyOrThrow(t, u);
				} catch (Exception e) {
					return other.applyOrThrow(t, u);
				}
			};
		}
	}
}
