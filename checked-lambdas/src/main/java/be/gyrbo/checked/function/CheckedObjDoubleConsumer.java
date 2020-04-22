/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedObjDoubleConsumer<T, EX extends Exception> {

	void acceptOrThrow(T t, double u) throws EX;
	
	/// Methods to deal with exceptions
	
	// Convert to a regular Predicate
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the function is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default ObjDoubleConsumer<T> sneakyThrow() {
		return (t, u) -> {
			@SuppressWarnings("unchecked")
			CheckedObjDoubleConsumer<T, RuntimeException> sneaky = (CheckedObjDoubleConsumer<T, RuntimeException>) this;
			sneaky.acceptOrThrow(t, u);
		};
	}	
	
	@FunctionalInterface
	public interface Adapter<T, EX extends Exception> extends CheckedObjDoubleConsumer<T, EX>, ObjDoubleConsumer<T> {
		default void acceptOrThrow(T t, double u) throws EX {
			acceptOrThrow(t, u);
		}
		
		default ObjDoubleConsumer<T> sneakyThrow() {
			return this;
		}
	}
	
	public interface Helper<T, EX extends Exception> extends CheckedObjDoubleConsumer<T, EX> {
		
		/**
		 * In case any exception occurs, don't do anything specific
		 */
		default CheckedObjDoubleConsumer.Adapter<T,  EX> ignoreAll() {
			return (t, u) -> {
				try {
					acceptOrThrow(t, u);
				} catch (Exception e) {
					// Ignore
				}
			};
		}
		
		/**
		 * In case a checked exception occurs, don't do anything specific
		 */
		default CheckedObjDoubleConsumer.Adapter<T,  EX> ignore() {
			return (t, u) -> {
				try {
					acceptOrThrow(t, u);
				} catch(RuntimeException e) {
					throw e;
				} catch (Exception e) {
					// Ignore
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied function will be used as the
		 * result. Otherwise the supplied function is never evaluated.
		 */
		default CheckedObjDoubleConsumer.Adapter<T, EX> fallbackTo(ObjDoubleConsumer<? super T> other) {
	        Objects.requireNonNull(other);
	        
			return (t, u) -> {
				try {
					acceptOrThrow(t, u);
				} catch (Exception e) {
					other.accept(t, u);
				}
			};
		}
		
		/**
		 * Rethrow any checked exceptions as unchecked exceptions by passing through the supplied mapper. 
		 */
		default CheckedObjDoubleConsumer.Adapter<T, EX> rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return (t, u) -> {
				try {
					this.acceptOrThrow(t, u);
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
		default <EX2 extends Exception> CheckedObjDoubleConsumer.Helper<T, EX2> rethrow(Function<? super EX, EX2> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return (t, u) -> {
				try {
					this.acceptOrThrow(t, u);
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
		default CheckedObjDoubleConsumer.Helper<T, EX> except(Consumer<? super EX> exceptionHandler) {	
	        Objects.requireNonNull(exceptionHandler);
	        
			return (t, u) -> {
				try {
					this.acceptOrThrow(t, u);
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
		default <EX2 extends EX> CheckedObjDoubleConsumer.Helper<T, EX> except(
				Class<EX2> cls, Consumer<? super EX2> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);
	        
			return (t, u) -> {
				try {
					this.acceptOrThrow(t, u);
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
		default CheckedObjDoubleConsumer.Helper<T, EX> exceptUnchecked(
				Consumer<? super RuntimeException> exceptionHandler) {
			return exceptUnchecked(RuntimeException.class, exceptionHandler);
		}
		
		default <REX extends RuntimeException> CheckedObjDoubleConsumer.Helper<T, EX> exceptUnchecked(
				Class<REX> cls, Consumer<? super REX> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);        
	        
			return (t, u) -> {
				try {
					this.acceptOrThrow(t, u);
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
		default CheckedObjDoubleConsumer.Helper<T, EX> orTry(CheckedObjDoubleConsumer<? super T, ? extends EX> other) {
	        Objects.requireNonNull(other);
	        
			return (t, u) -> {
				try {
					acceptOrThrow(t, u);
				} catch (Exception e) {
					other.acceptOrThrow(t, u);
				}
			};
		}
		
		/// Utility methods from BiConsumer
		
	    /**
	     * Will execute the other consumer if no exceptions occur.
	     */
	    default CheckedObjDoubleConsumer.Helper<T, EX> andThen(CheckedObjDoubleConsumer<? super T, ? extends EX> other) {
	        Objects.requireNonNull(other);

	        return (t, u) -> { acceptOrThrow(t, u); other.acceptOrThrow(t, u); };
	    }
	    
	    /**
	     * Will always execute the other consumer. Any exceptions from the original consumer will be masked
	     */
	    default CheckedObjDoubleConsumer.Helper<T, EX> andThenTry(CheckedObjDoubleConsumer<? super T, ? extends EX> other) {
	        Objects.requireNonNull(other);

	        return (t, u) -> {
				try {
					acceptOrThrow(t, u);
				} catch(Exception e) {
					// Ignore
				}
				
				other.acceptOrThrow(t, u);
	        };
	    }
	}
}
