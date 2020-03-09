/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.stream;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import be.gyrbo.checked.function.CheckedBiConsumer;
import be.gyrbo.checked.function.CheckedIntBinaryOperator;
import be.gyrbo.checked.function.CheckedIntConsumer;
import be.gyrbo.checked.function.CheckedIntFunction;
import be.gyrbo.checked.function.CheckedIntPredicate;
import be.gyrbo.checked.function.CheckedIntToDoubleFunction;
import be.gyrbo.checked.function.CheckedIntToLongFunction;
import be.gyrbo.checked.function.CheckedObjIntConsumer;
import be.gyrbo.checked.function.CheckedSupplier;

public class CheckedIntStream<EX extends Exception> extends CheckedBaseStream<IntStream, EX> {
	
	protected CheckedIntStream(IntStream delegate, Class<EX> exception) {
		super(delegate, exception);
	}
	
	public static <T, EX extends Exception> CheckedIntStream<EX> of(IntStream delegate, Class<EX> exception) {
		return new CheckedIntStream<EX>(delegate, exception);
	}
	
	// Terminal operations

	public void forEach(CheckedIntConsumer<EX> p0) throws EX {
		delegate.forEach(p0.sneakyThrow());
	}

	public void forEachOrdered(CheckedIntConsumer<EX> p0) throws EX {
		delegate.forEachOrdered(p0.sneakyThrow());
	}

	public int[] toArray() throws EX {
		return delegate.toArray();
	}

	public int reduce(int p0,
			CheckedIntBinaryOperator<EX> p1) throws EX {
		return delegate.reduce(p0, p1.sneakyThrow());
	}

	public OptionalInt reduce(CheckedIntBinaryOperator<EX> p0) throws EX {
		return delegate.reduce(p0.sneakyThrow());
	}

	public <R> R collect(CheckedSupplier<R, EX> p0,
			CheckedObjIntConsumer<R, EX> p1,
			CheckedBiConsumer<R, R, EX> p2) throws EX {
		return delegate.collect(p0.sneakyThrow(), p1.sneakyThrow(), p2.sneakyThrow());
	}

	public int sum() throws EX {
		return delegate.sum();
	}

	public OptionalInt min() throws EX {
		return delegate.min();
	}

	public OptionalInt max() throws EX {
		return delegate.max();
	}

	public long count() throws EX {
		return delegate.count();
	}

	public OptionalDouble average() throws EX {
		return delegate.average();
	}

	public IntSummaryStatistics summaryStatistics() throws EX {
		return delegate.summaryStatistics();
	}

	public boolean anyMatch(CheckedIntPredicate<EX> p0) throws EX {
		return delegate.anyMatch(p0.sneakyThrow());
	}

	public boolean allMatch(CheckedIntPredicate<EX> p0) throws EX {
		return delegate.allMatch(p0.sneakyThrow());
	}

	public boolean noneMatch(CheckedIntPredicate<EX> p0) throws EX {
		return delegate.noneMatch(p0.sneakyThrow());
	}

	public OptionalInt findFirst() throws EX {
		return delegate.findFirst();
	}

	public OptionalInt findAny() throws EX {
		return delegate.findAny();
	}

	public PrimitiveIterator.OfInt iterator() throws EX {
		return delegate.iterator();
	}

	public Spliterator.OfInt spliterator() throws EX {
		return delegate.spliterator();
	}

	// Intermediate operations

	public CheckedIntStream<EX> filter(CheckedIntPredicate<EX> p0) {
		return fromStream(delegate.filter(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> map(IntUnaryOperator p0) {
		return fromStream(delegate.map(p0));
	}

	public <U> CheckedStream<U, EX> mapToObj(CheckedIntFunction<? extends U, EX> p0) {
		return fromStream(delegate.mapToObj(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> mapToLong(CheckedIntToLongFunction<EX> p0) {
		return fromStream(delegate.mapToLong(p0.sneakyThrow()));
	}

	public CheckedDoubleStream<EX> mapToDouble(CheckedIntToDoubleFunction<EX> p0) {
		return fromStream(delegate.mapToDouble(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> flatMap(CheckedIntFunction<? extends IntStream, EX> p0) {
		return fromStream(delegate.flatMap(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> distinct() {
		return fromStream(delegate.distinct());
	}

	public CheckedIntStream<EX> sorted() {
		return fromStream(delegate.sorted());
	}

	public CheckedIntStream<EX> peek(CheckedIntConsumer<EX> p0) {
		return fromStream(delegate.peek(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> limit(long p0) {
		return fromStream(delegate.limit(p0));
	}

	public CheckedIntStream<EX> skip(long p0) {
		return fromStream(delegate.skip(p0));
	}

	public CheckedLongStream<EX> asLongStream() {
		return fromStream(delegate.asLongStream());
	}

	public CheckedDoubleStream<EX> asDoubleStream() {
		return fromStream(delegate.asDoubleStream());
	}

	public CheckedStream<Integer, EX> boxed() {
		return fromStream(delegate.boxed());
	}

	public CheckedIntStream<EX> sequential() {
		return fromStream(delegate.sequential());
	}

	public CheckedIntStream<EX> parallel() {
		return fromStream(delegate.parallel());
	}

}
