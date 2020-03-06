/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.stream;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.LongStream;

import be.gyrbo.checked.function.CheckedLongBinaryOperator;
import be.gyrbo.checked.function.CheckedLongConsumer;
import be.gyrbo.checked.function.CheckedLongFunction;
import be.gyrbo.checked.function.CheckedLongPredicate;
import be.gyrbo.checked.function.CheckedLongToDoubleFunction;
import be.gyrbo.checked.function.CheckedLongToIntFunction;

public class CheckedLongStream<EX extends Exception> extends CheckedBaseStream<LongStream, EX> {
	
	protected CheckedLongStream(LongStream delegate, Class<EX> exception) {
		super(delegate, exception);
	}
	
	public static <T, EX extends Exception> CheckedLongStream<EX> of(LongStream delegate, Class<EX> exception) {
		return new CheckedLongStream<EX>(delegate, exception);
	}
	
	// Terminal operations

	public void forEach(CheckedLongConsumer<EX> p0) throws EX {
		delegate.forEach(p0.sneakyThrow());
	}

	public void forEachOrdered(CheckedLongConsumer<EX> p0) throws EX {
		delegate.forEachOrdered(p0.sneakyThrow());
	}

	public long[] toArray() throws EX {
		return delegate.toArray();
	}

	public long reduce(long p0,
			CheckedLongBinaryOperator<EX> p1) throws EX {
		return delegate.reduce(p0, p1.sneakyThrow());
	}

	public OptionalLong reduce(CheckedLongBinaryOperator<EX> p0) throws EX {
		return delegate.reduce(p0.sneakyThrow());
	}

	public <R> R collect(Supplier<R> p0,
			ObjLongConsumer<R> p1,
			BiConsumer<R, R> p2) throws EX {
		return delegate.collect(p0, p1, p2);
	}

	public long sum() throws EX {
		return delegate.sum();
	}

	public OptionalLong min() throws EX {
		return delegate.min();
	}

	public OptionalLong max() throws EX {
		return delegate.max();
	}

	public long count() throws EX {
		return delegate.count();
	}

	public OptionalDouble average() throws EX {
		return delegate.average();
	}

	public LongSummaryStatistics summaryStatistics() throws EX {
		return delegate.summaryStatistics();
	}

	public boolean anyMatch(CheckedLongPredicate<EX> p0) throws EX {
		return delegate.anyMatch(p0.sneakyThrow());
	}

	public boolean allMatch(CheckedLongPredicate<EX> p0) throws EX {
		return delegate.allMatch(p0.sneakyThrow());
	}

	public boolean noneMatch(CheckedLongPredicate<EX> p0) throws EX {
		return delegate.noneMatch(p0.sneakyThrow());
	}

	public OptionalLong findFirst() throws EX {
		return delegate.findFirst();
	}

	public OptionalLong findAny() throws EX {
		return delegate.findAny();
	}

	public PrimitiveIterator.OfLong iterator() throws EX {
		return delegate.iterator();
	}

	public Spliterator.OfLong spliterator() throws EX {
		return delegate.spliterator();
	}

	// Intermediate operations

	public CheckedLongStream<EX> filter(CheckedLongPredicate<EX> p0) {
		return fromStream(delegate.filter(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> map(LongUnaryOperator p0) {
		return fromStream(delegate.map(p0));
	}

	public <U> CheckedStream<U, EX> mapToObj(CheckedLongFunction<? extends U, EX> p0) {
		return fromStream(delegate.mapToObj(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> mapToInt(CheckedLongToIntFunction<EX> p0) {
		return fromStream(delegate.mapToInt(p0.sneakyThrow()));
	}

	public CheckedDoubleStream<EX> mapToDouble(CheckedLongToDoubleFunction<EX> p0) {
		return fromStream(delegate.mapToDouble(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> flatMap(CheckedLongFunction<? extends LongStream, EX> p0) {
		return fromStream(delegate.flatMap(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> distinct() {
		return fromStream(delegate.distinct());
	}

	public CheckedLongStream<EX> sorted() {
		return fromStream(delegate.sorted());
	}

	public CheckedLongStream<EX> peek(CheckedLongConsumer<EX> p0) {
		return fromStream(delegate.peek(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> limit(long p0) {
		return fromStream(delegate.limit(p0));
	}

	public CheckedLongStream<EX> skip(long p0) {
		return fromStream(delegate.skip(p0));
	}

	public CheckedDoubleStream<EX> asDoubleStream() {
		return fromStream(delegate.asDoubleStream());
	}

	public CheckedStream<Long, EX> boxed() {
		return fromStream(delegate.boxed());
	}

	public CheckedLongStream<EX> sequential() {
		return fromStream(delegate.sequential());
	}

	public CheckedLongStream<EX> parallel() {
		return fromStream(delegate.parallel());
	}

}
