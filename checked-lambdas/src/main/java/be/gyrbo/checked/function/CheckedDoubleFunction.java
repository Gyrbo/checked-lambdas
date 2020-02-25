/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.DoubleFunction;

@FunctionalInterface
public interface CheckedDoubleFunction<R, EX extends Exception> {

	R applyOrThrow(double t) throws EX;
	
	/// Methods to deal with exceptions
	
	// Convert to a regular Predicate
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the function is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default DoubleFunction<R> sneakyThrow() {
		return t -> {
			@SuppressWarnings("unchecked")
			CheckedDoubleFunction<R, RuntimeException> sneaky = (CheckedDoubleFunction<R, RuntimeException>) this;
			return sneaky.applyOrThrow(t);
		};
	}	
	
	@FunctionalInterface
	public interface Adapter<R, EX extends Exception> extends CheckedDoubleFunction<R, EX>, DoubleFunction<R> {
		default R applyOrThrow(double t) throws EX {
			return applyOrThrow(t);
		}
		
		default DoubleFunction<R> sneakyThrow() {
			return this;
		}
	}
	
	public interface Helper<R, EX extends Exception> extends CheckedDoubleFunction<R, EX> {
	
		/**
		 * In case an exception occurs, an empty Optional is returned
		 */
		default DoubleFunction<Optional<R>> optional() {
			return t -> {
				try {
					return Optional.of(applyOrThrow(t));
				} catch (Exception e) {
					return Optional.empty();
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied function will be used as the
		 * result. Otherwise the supplied function is never evaluated.
		 */
		default CheckedDoubleFunction.Adapter<R, EX> fallbackTo(DoubleFunction<? extends R> other) {
	        Objects.requireNonNull(other);
	        
			return t -> {
				try {
					return applyOrThrow(t);
				} catch (Exception e) {
					return other.apply(t);
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied value will be used as the
		 * result.
		 */
		default CheckedDoubleFunction.Adapter<R, EX> orReturn(R value) {
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
		default CheckedDoubleFunction.Adapter<R, EX> rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
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
		default <EX2 extends Exception> CheckedDoubleFunction.Helper<R, EX2> rethrow(Function<? super EX, EX2> mapper) {
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
		default CheckedDoubleFunction.Helper<R, EX> except(Consumer<? super EX> exceptionHandler) {	
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
		default <EX2 extends EX> CheckedDoubleFunction.Helper<R, EX> except(
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
		default CheckedDoubleFunction.Helper<R, EX> exceptUnchecked(
				Consumer<? super RuntimeException> exceptionHandler) {
			return exceptUnchecked(RuntimeException.class, exceptionHandler);
		}
		
		default <REX extends RuntimeException> CheckedDoubleFunction.Helper<R, EX> exceptUnchecked(
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
		default CheckedDoubleFunction.Helper<R, EX> orTry(CheckedDoubleFunction<R, ? extends EX> other) {
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
