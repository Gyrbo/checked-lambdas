/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.stream;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import be.gyrbo.checked.function.CheckedPredicate;

public class CheckedStream<T, EX extends Exception> {
	private final Stream<T> delegate;
	private final Class<EX> exception;
	
	private CheckedStream(Stream<T> delegate, Class<EX> exception) {
		this.delegate = delegate;
		this.exception = exception;
	}
	
	private <U> CheckedStream<U, EX> fromStream(Stream<U> delegate) {
		return new CheckedStream<U, EX>(delegate, exception);
	}
	
	public static <T, EX extends Exception> CheckedStream<T, EX> of(Stream<T> delegate, Class<EX> exception) {
		return new CheckedStream<T, EX>(delegate, exception);
	}

	public CheckedStream<T, EX> filter(CheckedPredicate<? super T, ? extends EX> predicate) {
		return fromStream(delegate.filter(predicate.sneakyThrow()));
	}

	public <R> CheckedStream<R, EX> map(Function<? super T, ? extends R> mapper) {
		return fromStream(delegate.map(mapper));
	}

	/*public IntStream mapToInt(ToIntFunction<? super T> mapper) {
		return delegate.mapToInt(mapper);
	}

	public LongStream mapToLong(ToLongFunction<? super T> mapper) {
		return delegate.mapToLong(mapper);
	}

	public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
		return delegate.mapToDouble(mapper);
	}*/

	public <R> CheckedStream<R, EX> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
		return fromStream(delegate.flatMap(mapper));
	}

	/*public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
		return delegate.flatMapToInt(mapper);
	}

	public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
		return delegate.flatMapToLong(mapper);
	}

	public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
		return delegate.flatMapToDouble(mapper);
	}*/

	public CheckedStream<T, EX> sorted(Comparator<? super T> comparator)  {
		return fromStream(delegate.sorted(comparator));
	}

	public CheckedStream<T, EX> peek(Consumer<? super T> action) {
		return fromStream(delegate.peek(action));
	}

	public CheckedStream<T, EX> limit(long maxSize) {
		return fromStream(delegate.limit(maxSize));
	}

	public CheckedStream<T, EX> skip(long n) {
		return fromStream(delegate.skip(n));
	}

	public CheckedStream<T, EX> onClose(Runnable closeHandler) {
		return fromStream(delegate.onClose(closeHandler));
	}

	public void close() throws EX {
		delegate.close();
	}

	public void forEach(Consumer<? super T> action) throws EX {
		delegate.forEach(action);
	}

	public void forEachOrdered(Consumer<? super T> action) throws EX {
		delegate.forEachOrdered(action);
	}

	public Object[] toArray() throws EX {
		return delegate.toArray();
	}

	public <A> A[] toArray(IntFunction<A[]> generator) throws EX {
		return delegate.toArray(generator);
	}

	public T reduce(T identity, BinaryOperator<T> accumulator) throws EX {
		return delegate.reduce(identity, accumulator);
	}

	public Optional<T> reduce(BinaryOperator<T> accumulator) throws EX {
		return delegate.reduce(accumulator);
	}

	public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) throws EX {
		return delegate.reduce(identity, accumulator, combiner);
	}

	public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) throws EX {
		return delegate.collect(supplier, accumulator, combiner);
	}

	public <R, A> R collect(Collector<? super T, A, R> collector) throws EX {
		return delegate.collect(collector);
	}

	public Optional<T> min(Comparator<? super T> comparator) throws EX {
		return delegate.min(comparator);
	}

	public Optional<T> max(Comparator<? super T> comparator) throws EX {
		return delegate.max(comparator);
	}

	public long count() throws EX {
		return delegate.count();
	}

	public boolean anyMatch(Predicate<? super T> predicate) throws EX {
		return delegate.anyMatch(predicate);
	}

	public boolean allMatch(Predicate<? super T> predicate) throws EX {
		return delegate.allMatch(predicate);
	}

	public boolean noneMatch(Predicate<? super T> predicate) throws EX {
		return delegate.noneMatch(predicate);
	}

	public Optional<T> findFirst() throws EX {
		return delegate.findFirst();
	}

	public Optional<T> findAny() throws EX {
		return delegate.findAny();
	}
	
	
}
