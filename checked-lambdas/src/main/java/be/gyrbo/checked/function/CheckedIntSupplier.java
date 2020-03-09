/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.function;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.IntSupplier;

@FunctionalInterface
public interface CheckedIntSupplier<EX extends Exception> {

	int getOrThrow() throws EX;
	
	/**
	 * This causes any unchecked exceptions to still by thrown when the predicate is
	 * invoked, but it simply does not declare it.
	 * <p>
	 * <b>You should probably never use this</b>. It is mainly useful for libraries
	 * handling the exception in some other way.
	 */
	default IntSupplier sneakyThrow() {
		return () -> {
			@SuppressWarnings("unchecked")
			CheckedIntSupplier<RuntimeException> sneaky = (CheckedIntSupplier<RuntimeException>) this;
			return sneaky.getOrThrow();
		};
	}
	
	
	@FunctionalInterface
	public interface Adapter<EX extends Exception> extends CheckedIntSupplier<EX>, IntSupplier {
		default int getOrThrow() throws EX {
			return getAsInt();
		}
		
		default IntSupplier sneakyThrow() {
			return this;
		}
	}
	

	public interface Helper<EX extends Exception> extends CheckedIntSupplier<EX> {
		
		/// Methods to deal with exceptions
		
		// Convert to a regular Supplier
		
		/**
		 * In case an exception occurs, an empty Optional is returned
		 */
		default Supplier<OptionalInt> optional() {
			return () -> {
				try {
					return OptionalInt.of(getOrThrow());
				} catch (Exception e) {
					return OptionalInt.empty();
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied predicate will be used as the
		 * result. Otherwise the supplied predicate is never evaluated.
		 */
		default CheckedIntSupplier.Adapter<EX> fallbackTo(IntSupplier other) {
	        Objects.requireNonNull(other);
	        
			return () -> {
				try {
					return getOrThrow();
				} catch (Exception e) {
					return other.getAsInt();
				}
			};
		}
		
		/**
		 * In case an exception occurs, the supplied value will be used as the
		 * result.
		 */
		default CheckedIntSupplier.Adapter<EX> orReturn(int value) {
			return () -> {
				try {
					return getOrThrow();
				} catch (Exception e) {
					return value;
				}
			};
		}
		
		/**
		 * Rethrow any checked exceptions as unchecked exceptions by passing through the supplied mapper. 
		 */
		default CheckedIntSupplier.Adapter<EX> rethrowUnchecked(Function<? super EX, ? extends RuntimeException> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return () -> {
				try {
					return this.getOrThrow();
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
		default <EX2 extends Exception> CheckedIntSupplier.Helper<EX2> rethrow(Function<? super EX, EX2> mapper) {
	        Objects.requireNonNull(mapper);
	        
			return () -> {
				try {
					return this.getOrThrow();
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
		default CheckedIntSupplier.Helper<EX> except(Consumer<? super EX> exceptionHandler) {	
	        Objects.requireNonNull(exceptionHandler);
	        
			return () -> {
				try {
					return this.getOrThrow();
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
		default <EX2 extends EX> CheckedIntSupplier.Helper<EX> except(
				Class<EX2> cls, Consumer<? super EX2> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);
	        
			return () -> {
				try {
					return this.getOrThrow();
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
		default CheckedIntSupplier.Helper<EX> exceptUnchecked(
				Consumer<? super RuntimeException> exceptionHandler) {
			return exceptUnchecked(RuntimeException.class, exceptionHandler);
		}
		
		default <REX extends RuntimeException> CheckedIntSupplier.Helper<EX> exceptUnchecked(
				Class<REX> cls, Consumer<? super REX> exceptionHandler) {
	        Objects.requireNonNull(cls);
	        Objects.requireNonNull(exceptionHandler);        
	        
			return () -> {
				try {
					return this.getOrThrow();
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
		default CheckedIntSupplier.Helper<EX> orTry(CheckedIntSupplier<? extends EX> other) {
	        Objects.requireNonNull(other);
	        
			return () -> {
				try {
					return getOrThrow();
				} catch (Exception e) {
					return other.getOrThrow();
				}
			};
		}
	}
}
