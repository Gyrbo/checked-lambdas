/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedDoubleConsumer<EX extends Exception> {

	void acceptOrThrow(double t) throws EX;
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the Consumer is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default DoubleConsumer sneakyThrow() {
		return t -> {
			@SuppressWarnings("unchecked")
			CheckedDoubleConsumer<RuntimeException> sneaky = (CheckedDoubleConsumer<RuntimeException>) this;
			sneaky.acceptOrThrow(t);
		};
	}
	
	
	@FunctionalInterface
	public interface Adapter<EX extends Exception> extends CheckedDoubleConsumer<EX>, DoubleConsumer {
		default void acceptOrThrow(double t) throws EX {
			accept(t);
		}
		
		default DoubleConsumer sneakyThrow() {
			return this;
		}
	}
	

	public interface Helper<EX extends Exception> extends CheckedDoubleConsumer<EX> {
		
		/// Methods to deal with exceptions
		
		// Convert to a regular Consumer
		
		/**
		 * In case an exception occurs, don't do anything specific
		 */
		default CheckedDoubleConsumer.Adapter<EX> ignoreAll() {
			return t -> {
				try {
					acceptOrThrow(t);
				} catch (Exception e) {
					// Ignore
				}
			};
		}
		
		/**
		 * In case an exception occurs, don't do anything specific
		 */
		default CheckedDoubleConsumer.Adapter<EX> ignore() {
			return t -> {
				try {
					acceptOrThrow(t);
				} catch(RuntimeException e) {
					throw e;
				} catch (Exception e) {
					// Ignore
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied Consumer will be used as the
		 * result. Otherwise the supplied Consumer is never evaluated.
		 */
		default CheckedDoubleConsumer.Adapter<EX> fallbackTo(DoubleConsumer other) {
	        Objects.requireNonNull(other);
	        
			return t -> {
				try {
					acceptOrThrow(t);
				} catch (Exception e) {
					other.accept(t);
				}
			};
		}
		
		/**
		 * Rethrow any checked exceptions as unchecked exceptions by passing through the supplied mapper. 
		 */
		default CheckedDoubleConsumer.Adapter<EX> rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return t -> {
				try {
					this.acceptOrThrow(t);
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
		default <EX2 extends Exception> CheckedDoubleConsumer.Helper<EX2> rethrow(Function<? super EX, EX2> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return t -> {
				try {
					this.acceptOrThrow(t);
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
		default CheckedDoubleConsumer.Helper<EX> except(Consumer<? super EX> exceptionHandler) {	
	        Objects.requireNonNull(exceptionHandler);
	        
			return t -> {
				try {
					this.acceptOrThrow(t);
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
		default <EX2 extends EX> CheckedDoubleConsumer.Helper<EX> except(
				Class<EX2> cls, Consumer<? super EX2> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);
	        
			return t -> {
				try {
					this.acceptOrThrow(t);
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
		default CheckedDoubleConsumer.Helper<EX> exceptUnchecked(
				Consumer<? super RuntimeException> exceptionHandler) {
			return exceptUnchecked(RuntimeException.class, exceptionHandler);
		}
		
		/**
		 * Passes any unchecked exceptions that match the specified type to the supplied
		 * exception handler. Afterwards, the exception is rethrown.
		 */
		default <REX extends RuntimeException> CheckedDoubleConsumer.Helper<EX> exceptUnchecked(
				Class<REX> cls, Consumer<? super REX> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);        
	        
			return t -> {
				try {
					this.acceptOrThrow(t);
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
		
		default CheckedDoubleConsumer.Helper<EX> orTry(CheckedDoubleConsumer<? extends EX> other) {
	        Objects.requireNonNull(other);
	        
			return t -> {
				try {
					acceptOrThrow(t);
				} catch (Exception e) {
					other.acceptOrThrow(t);
				}
			};
		}
		
		/// Utility methods from Consumer
		
	    /**
	     * Will execute the other consumer if no exceptions occur.
	     */
	    default CheckedDoubleConsumer.Helper<EX> andThen(CheckedDoubleConsumer<? extends EX> other) {
	        Objects.requireNonNull(other);

	        return t -> { acceptOrThrow(t); other.acceptOrThrow(t); };
	    }
	    
	    /**
	     * Will always execute the other consumer. Any exceptions from the original consumer will be masked
	     */
	    default CheckedDoubleConsumer.Helper<EX> andThenTry(CheckedDoubleConsumer<? extends EX> other) {
	        Objects.requireNonNull(other);

	        return t -> {
				try {
					acceptOrThrow(t);
				} catch(Exception e) {
					// Ignore
				}
				
				other.acceptOrThrow(t);
	        };
	    }
	}
}
