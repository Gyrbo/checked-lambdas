/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedLongConsumer<EX extends Exception> {

	void acceptOrThrow(long t) throws EX;
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the Consumer is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default LongConsumer sneakyThrow() {
		return t -> {
			@SuppressWarnings("unchecked")
			CheckedLongConsumer<RuntimeException> sneaky = (CheckedLongConsumer<RuntimeException>) this;
			sneaky.acceptOrThrow(t);
		};
	}
	
	
	@FunctionalInterface
	public interface Adapter<EX extends Exception> extends CheckedLongConsumer<EX>, LongConsumer {
		default void acceptOrThrow(long t) throws EX {
			accept(t);
		}
		
		default LongConsumer sneakyThrow() {
			return this;
		}
	}
	

	public interface Helper<EX extends Exception> extends CheckedLongConsumer<EX> {
		
		/// Methods to deal with exceptions
		
		// Convert to a regular Consumer
		
		/**
		 * In case an exception occurs, don't do anything specific
		 */
		default CheckedLongConsumer.Adapter<EX> ignoreAll() {
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
		default CheckedLongConsumer.Adapter<EX> ignore() {
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
		default CheckedLongConsumer.Adapter<EX> fallbackTo(LongConsumer other) {
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
		default CheckedLongConsumer.Adapter<EX> rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
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
		default <EX2 extends Exception> CheckedLongConsumer.Helper<EX2> rethrow(Function<? super EX, EX2> mapper) {
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
		default CheckedLongConsumer.Helper<EX> except(Consumer<? super EX> exceptionHandler) {	
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
		default <EX2 extends EX> CheckedLongConsumer.Helper<EX> except(
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
		default CheckedLongConsumer.Helper<EX> exceptUnchecked(
				Consumer<? super RuntimeException> exceptionHandler) {
			return exceptUnchecked(RuntimeException.class, exceptionHandler);
		}
		
		/**
		 * Passes any unchecked exceptions that match the specified type to the supplied
		 * exception handler. Afterwards, the exception is rethrown.
		 */
		default <REX extends RuntimeException> CheckedLongConsumer.Helper<EX> exceptUnchecked(
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
		
		default CheckedLongConsumer.Helper<EX> orTry(CheckedLongConsumer<? extends EX> other) {
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
	    default CheckedLongConsumer.Helper<EX> andThen(CheckedLongConsumer<? extends EX> other) {
	        Objects.requireNonNull(other);

	        return t -> { acceptOrThrow(t); other.acceptOrThrow(t); };
	    }
	    
	    /**
	     * Will always execute the other consumer. Any exceptions from the original consumer will be masked
	     */
	    default CheckedLongConsumer.Helper<EX> andThenTry(CheckedLongConsumer<? extends EX> other) {
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
