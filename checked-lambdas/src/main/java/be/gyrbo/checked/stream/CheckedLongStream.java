/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.stream;

import java.util.LongSummaryStatistics;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.stream.LongStream;

import be.gyrbo.checked.function.CheckedBiConsumer;
import be.gyrbo.checked.function.CheckedLongBinaryOperator;
import be.gyrbo.checked.function.CheckedLongConsumer;
import be.gyrbo.checked.function.CheckedLongFunction;
import be.gyrbo.checked.function.CheckedLongPredicate;
import be.gyrbo.checked.function.CheckedLongToDoubleFunction;
import be.gyrbo.checked.function.CheckedLongToIntFunction;
import be.gyrbo.checked.function.CheckedLongUnaryOperator;
import be.gyrbo.checked.function.CheckedObjLongConsumer;
import be.gyrbo.checked.function.CheckedSupplier;
import be.gyrbo.checked.util.CheckedOptionalDouble;
import be.gyrbo.checked.util.CheckedOptionalLong;

public class CheckedLongStream<EX extends Exception> extends CheckedBaseStream<LongStream, EX> {
	
	protected CheckedLongStream(LongStream delegate) {
		super(delegate);
	}
	
	public static <T, EX extends Exception> CheckedLongStream<EX> of(LongStream delegate) {
		return new CheckedLongStream<EX>(delegate);
	}
	
	// Terminal operations

	public long count() throws EX {
		return delegate.count();
	}

	public PrimitiveIterator.OfLong iterator() throws EX {
		return delegate.iterator();
	}

	public long[] toArray() throws EX {
		return delegate.toArray();
	}

	public Spliterator.OfLong spliterator() throws EX {
		return delegate.spliterator();
	}

	public <R> R collect(CheckedSupplier<R, EX> p0,
			CheckedObjLongConsumer<R, EX> p1,
			CheckedBiConsumer<R, R, EX> p2) throws EX {
		return delegate.collect(p0.sneakyThrow(), p1.sneakyThrow(), p2.sneakyThrow());
	}

	public void forEach(CheckedLongConsumer<EX> p0) throws EX {
		delegate.forEach(p0.sneakyThrow());
	}

	public long sum() throws EX {
		return delegate.sum();
	}

	public long reduce(long p0,
			CheckedLongBinaryOperator<EX> p1) throws EX {
		return delegate.reduce(p0, p1.sneakyThrow());
	}

	public boolean allMatch(CheckedLongPredicate<EX> p0) throws EX {
		return delegate.allMatch(p0.sneakyThrow());
	}

	public boolean anyMatch(CheckedLongPredicate<EX> p0) throws EX {
		return delegate.anyMatch(p0.sneakyThrow());
	}

	public void forEachOrdered(CheckedLongConsumer<EX> p0) throws EX {
		delegate.forEachOrdered(p0.sneakyThrow());
	}

	public boolean noneMatch(CheckedLongPredicate<EX> p0) throws EX {
		return delegate.noneMatch(p0.sneakyThrow());
	}

	public LongSummaryStatistics summaryStatistics() throws EX {
		return delegate.summaryStatistics();
	}

	public CheckedOptionalLong min() throws EX {
		return CheckedOptionalLong.of(delegate.min());
	}

	public CheckedOptionalLong max() throws EX {
		return CheckedOptionalLong.of(delegate.max());
	}

	public CheckedOptionalLong reduce(CheckedLongBinaryOperator<EX> p0) throws EX {
		return CheckedOptionalLong.of(delegate.reduce(p0.sneakyThrow()));
	}

	public CheckedOptionalLong findAny() throws EX {
		return CheckedOptionalLong.of(delegate.findAny());
	}

	public CheckedOptionalLong findFirst() throws EX {
		return CheckedOptionalLong.of(delegate.findFirst());
	}

	public CheckedOptionalDouble average() throws EX {
		return CheckedOptionalDouble.of(delegate.average());
	}

	// Intermediate operations

	public CheckedLongStream<EX> limit(long p0) {
		return fromStream(delegate.limit(p0));
	}

	public CheckedLongStream<EX> skip(long p0) {
		return fromStream(delegate.skip(p0));
	}

	public CheckedLongStream<EX> peek(CheckedLongConsumer<EX> p0) {
		return fromStream(delegate.peek(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> filter(CheckedLongPredicate<EX> p0) {
		return fromStream(delegate.filter(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> parallel() {
		return fromStream(delegate.parallel());
	}

	public CheckedLongStream<EX> map(CheckedLongUnaryOperator<EX> p0) {
		return fromStream(delegate.map(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> distinct() {
		return fromStream(delegate.distinct());
	}

	public CheckedLongStream<EX> flatMap(CheckedLongFunction<? extends LongStream, EX> p0) {
		return fromStream(delegate.flatMap(p0.sneakyThrow()));
	}

	public CheckedDoubleStream<EX> mapToDouble(CheckedLongToDoubleFunction<EX> p0) {
		return fromStream(delegate.mapToDouble(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> mapToInt(CheckedLongToIntFunction<EX> p0) {
		return fromStream(delegate.mapToInt(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> sorted() {
		return fromStream(delegate.sorted());
	}

	public CheckedLongStream<EX> sequential() {
		return fromStream(delegate.sequential());
	}

	public <U> CheckedStream<U, EX> mapToObj(CheckedLongFunction<? extends U, EX> p0) {
		return fromStream(delegate.mapToObj(p0.sneakyThrow()));
	}

	public CheckedStream<Long, EX> boxed() {
		return fromStream(delegate.boxed());
	}

	public CheckedDoubleStream<EX> asDoubleStream() {
		return fromStream(delegate.asDoubleStream());
	}

}
