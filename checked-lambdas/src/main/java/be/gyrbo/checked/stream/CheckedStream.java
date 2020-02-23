/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import be.gyrbo.checked.function.*;

public class CheckedStream<T, EX extends Exception> extends CheckedBaseStream<Stream<T>, EX> {
	
	protected CheckedStream(Stream<T> delegate, Class<EX> exception) {
		super(delegate, exception);
	}
	
	public static <T, EX extends Exception> CheckedStream<T, EX> of(Stream<T> delegate, Class<EX> exception) {
		return new CheckedStream<T, EX>(delegate, exception);
	}
	
	// Terminal operations

	public long count() throws EX {
		return delegate.count();
	}

	public Optional<T> min(Comparator<? super T> p0) throws EX {
		return delegate.min(p0);
	}

	public Optional<T> max(Comparator<? super T> p0) throws EX {
		return delegate.max(p0);
	}

	public <A> A[] toArray(IntFunction<A[]> p0) throws EX {
		return delegate.toArray(p0);
	}

	public Object[] toArray() throws EX {
		return delegate.toArray();
	}

	public <R> R collect(Supplier<R> p0,
			BiConsumer<R, ? super T> p1,
			BiConsumer<R, R> p2) throws EX {
		return delegate.collect(p0, p1, p2);
	}

	public <R, A> R collect(Collector<? super T, A, R> p0) throws EX {
		return delegate.collect(p0);
	}

	public void forEach(Consumer<? super T> p0) throws EX {
		delegate.forEach(p0);
	}

	public Optional<T> reduce(BinaryOperator<T> p0) throws EX {
		return delegate.reduce(p0);
	}

	public <U> U reduce(U p0,
			BiFunction<U, ? super T, U> p1,
			BinaryOperator<U> p2) throws EX {
		return delegate.reduce(p0, p1, p2);
	}

	public T reduce(T p0,
			BinaryOperator<T> p1) throws EX {
		return delegate.reduce(p0, p1);
	}

	public boolean allMatch(Predicate<? super T> p0) throws EX {
		return delegate.allMatch(p0);
	}

	public boolean anyMatch(Predicate<? super T> p0) throws EX {
		return delegate.anyMatch(p0);
	}

	public Optional<T> findAny() throws EX {
		return delegate.findAny();
	}

	public Optional<T> findFirst() throws EX {
		return delegate.findFirst();
	}

	public void forEachOrdered(Consumer<? super T> p0) throws EX {
		delegate.forEachOrdered(p0);
	}

	public boolean noneMatch(Predicate<? super T> p0) throws EX {
		return delegate.noneMatch(p0);
	}

	// Intermediate operations

	public CheckedStream<T, EX> limit(long p0) {
		return fromStream(delegate.limit(p0));
	}

	public CheckedStream<T, EX> skip(long p0) {
		return fromStream(delegate.skip(p0));
	}

	public CheckedStream<T, EX> peek(Consumer<? super T> p0) {
		return fromStream(delegate.peek(p0));
	}

	public CheckedStream<T, EX> filter(CheckedPredicate<? super T, EX> p0) {
		return fromStream(delegate.filter(p0.sneakyThrow()));
	}

	public <R> CheckedStream<R, EX> map(CheckedFunction<? super T, ? extends R, EX> p0) {
		return fromStream(delegate.map(p0.sneakyThrow()));
	}

	public CheckedStream<T, EX> distinct() {
		return fromStream(delegate.distinct());
	}

	public <R> CheckedStream<R, EX> flatMap(CheckedFunction<? super T, ? extends Stream<? extends R>, EX> p0) {
		return fromStream(delegate.flatMap(p0.sneakyThrow()));
	}

	public CheckedDoubleStream<EX> flatMapToDouble(CheckedFunction<? super T, ? extends DoubleStream, EX> p0) {
		return fromStream(delegate.flatMapToDouble(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> flatMapToInt(CheckedFunction<? super T, ? extends IntStream, EX> p0) {
		return fromStream(delegate.flatMapToInt(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> flatMapToLong(CheckedFunction<? super T, ? extends LongStream, EX> p0) {
		return fromStream(delegate.flatMapToLong(p0.sneakyThrow()));
	}

	public CheckedDoubleStream<EX> mapToDouble(CheckedToDoubleFunction<? super T, EX> p0) {
		return fromStream(delegate.mapToDouble(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> mapToInt(CheckedToIntFunction<? super T, EX> p0) {
		return fromStream(delegate.mapToInt(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> mapToLong(CheckedToLongFunction<? super T, EX> p0) {
		return fromStream(delegate.mapToLong(p0.sneakyThrow()));
	}

	public CheckedStream<T, EX> sorted() {
		return fromStream(delegate.sorted());
	}

	public CheckedStream<T, EX> sorted(Comparator<? super T> p0) {
		return fromStream(delegate.sorted(p0));
	}

}
