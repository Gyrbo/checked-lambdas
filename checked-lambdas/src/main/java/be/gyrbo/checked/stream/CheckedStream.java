/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.stream;

import java.util.Comparator;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import be.gyrbo.checked.function.CheckedBiConsumer;
import be.gyrbo.checked.function.CheckedBiFunction;
import be.gyrbo.checked.function.CheckedBinaryOperator;
import be.gyrbo.checked.function.CheckedConsumer;
import be.gyrbo.checked.function.CheckedFunction;
import be.gyrbo.checked.function.CheckedIntFunction;
import be.gyrbo.checked.function.CheckedPredicate;
import be.gyrbo.checked.function.CheckedSupplier;
import be.gyrbo.checked.function.CheckedToDoubleFunction;
import be.gyrbo.checked.function.CheckedToIntFunction;
import be.gyrbo.checked.function.CheckedToLongFunction;
import be.gyrbo.checked.util.CheckedOptional;

public class CheckedStream<T, EX extends Exception> extends CheckedBaseStream<Stream<T>, EX> {
	
	protected CheckedStream(Stream<T> delegate) {
		super(delegate);
	}
	
	public static <T, EX extends Exception> CheckedStream<T, EX> of(Stream<T> delegate) {
		return new CheckedStream<T, EX>(delegate);
	}
	
	// Terminal operations

	public long count() throws EX {
		return delegate.count();
	}

	public <A> A[] toArray(CheckedIntFunction<A[], EX> p0) throws EX {
		return delegate.toArray(p0.sneakyThrow());
	}

	public Object[] toArray() throws EX {
		return delegate.toArray();
	}

	public <R> R collect(CheckedSupplier<R, EX> p0,
			CheckedBiConsumer<R, ? super T, EX> p1,
			CheckedBiConsumer<R, R, EX> p2) throws EX {
		return delegate.collect(p0.sneakyThrow(), p1.sneakyThrow(), p2.sneakyThrow());
	}

	public <R, A> R collect(Collector<? super T, A, R> p0) throws EX {
		return delegate.collect(p0);
	}

	public void forEach(CheckedConsumer<? super T, EX> p0) throws EX {
		delegate.forEach(p0.sneakyThrow());
	}

	public <U> U reduce(U p0,
			CheckedBiFunction<U, ? super T, U, EX> p1,
			CheckedBinaryOperator<U, EX> p2) throws EX {
		return delegate.reduce(p0, p1.sneakyThrow(), p2.sneakyThrow());
	}

	public T reduce(T p0,
			CheckedBinaryOperator<T, EX> p1) throws EX {
		return delegate.reduce(p0, p1.sneakyThrow());
	}

	public boolean allMatch(CheckedPredicate<? super T, EX> p0) throws EX {
		return delegate.allMatch(p0.sneakyThrow());
	}

	public boolean anyMatch(CheckedPredicate<? super T, EX> p0) throws EX {
		return delegate.anyMatch(p0.sneakyThrow());
	}

	public void forEachOrdered(CheckedConsumer<? super T, EX> p0) throws EX {
		delegate.forEachOrdered(p0.sneakyThrow());
	}

	public boolean noneMatch(CheckedPredicate<? super T, EX> p0) throws EX {
		return delegate.noneMatch(p0.sneakyThrow());
	}

	public CheckedOptional<T> min(Comparator<? super T> p0) throws EX {
		return CheckedOptional.of(delegate.min(p0));
	}

	public CheckedOptional<T> max(Comparator<? super T> p0) throws EX {
		return CheckedOptional.of(delegate.max(p0));
	}

	public CheckedOptional<T> reduce(CheckedBinaryOperator<T, EX> p0) throws EX {
		return CheckedOptional.of(delegate.reduce(p0.sneakyThrow()));
	}

	public CheckedOptional<T> findAny() throws EX {
		return CheckedOptional.of(delegate.findAny());
	}

	public CheckedOptional<T> findFirst() throws EX {
		return CheckedOptional.of(delegate.findFirst());
	}

	// Intermediate operations

	public CheckedStream<T, EX> limit(long p0) {
		return fromStream(delegate.limit(p0));
	}

	public CheckedStream<T, EX> skip(long p0) {
		return fromStream(delegate.skip(p0));
	}

	public CheckedStream<T, EX> peek(CheckedConsumer<? super T, EX> p0) {
		return fromStream(delegate.peek(p0.sneakyThrow()));
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
