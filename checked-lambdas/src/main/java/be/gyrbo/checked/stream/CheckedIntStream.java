/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.stream;

import java.util.IntSummaryStatistics;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.stream.IntStream;

import be.gyrbo.checked.function.CheckedBiConsumer;
import be.gyrbo.checked.function.CheckedIntBinaryOperator;
import be.gyrbo.checked.function.CheckedIntConsumer;
import be.gyrbo.checked.function.CheckedIntFunction;
import be.gyrbo.checked.function.CheckedIntPredicate;
import be.gyrbo.checked.function.CheckedIntToDoubleFunction;
import be.gyrbo.checked.function.CheckedIntToLongFunction;
import be.gyrbo.checked.function.CheckedIntUnaryOperator;
import be.gyrbo.checked.function.CheckedObjIntConsumer;
import be.gyrbo.checked.function.CheckedSupplier;
import be.gyrbo.checked.util.CheckedOptionalDouble;
import be.gyrbo.checked.util.CheckedOptionalInt;

public class CheckedIntStream<EX extends Exception> extends CheckedBaseStream<IntStream, EX> {
	
	protected CheckedIntStream(IntStream delegate, Class<EX> exception) {
		super(delegate, exception);
	}
	
	public static <T, EX extends Exception> CheckedIntStream<EX> of(IntStream delegate, Class<EX> exception) {
		return new CheckedIntStream<EX>(delegate, exception);
	}
	
	// Terminal operations

	public long count() throws EX {
		return delegate.count();
	}

	public PrimitiveIterator.OfInt iterator() throws EX {
		return delegate.iterator();
	}

	public int[] toArray() throws EX {
		return delegate.toArray();
	}

	public Spliterator.OfInt spliterator() throws EX {
		return delegate.spliterator();
	}

	public <R> R collect(CheckedSupplier<R, EX> p0,
			CheckedObjIntConsumer<R, EX> p1,
			CheckedBiConsumer<R, R, EX> p2) throws EX {
		return delegate.collect(p0.sneakyThrow(), p1.sneakyThrow(), p2.sneakyThrow());
	}

	public void forEach(CheckedIntConsumer<EX> p0) throws EX {
		delegate.forEach(p0.sneakyThrow());
	}

	public int sum() throws EX {
		return delegate.sum();
	}

	public int reduce(int p0,
			CheckedIntBinaryOperator<EX> p1) throws EX {
		return delegate.reduce(p0, p1.sneakyThrow());
	}

	public boolean allMatch(CheckedIntPredicate<EX> p0) throws EX {
		return delegate.allMatch(p0.sneakyThrow());
	}

	public boolean anyMatch(CheckedIntPredicate<EX> p0) throws EX {
		return delegate.anyMatch(p0.sneakyThrow());
	}

	public void forEachOrdered(CheckedIntConsumer<EX> p0) throws EX {
		delegate.forEachOrdered(p0.sneakyThrow());
	}

	public boolean noneMatch(CheckedIntPredicate<EX> p0) throws EX {
		return delegate.noneMatch(p0.sneakyThrow());
	}

	public IntSummaryStatistics summaryStatistics() throws EX {
		return delegate.summaryStatistics();
	}

	public CheckedOptionalInt min() throws EX {
		return CheckedOptionalInt.of(delegate.min());
	}

	public CheckedOptionalInt max() throws EX {
		return CheckedOptionalInt.of(delegate.max());
	}

	public CheckedOptionalInt reduce(CheckedIntBinaryOperator<EX> p0) throws EX {
		return CheckedOptionalInt.of(delegate.reduce(p0.sneakyThrow()));
	}

	public CheckedOptionalInt findAny() throws EX {
		return CheckedOptionalInt.of(delegate.findAny());
	}

	public CheckedOptionalInt findFirst() throws EX {
		return CheckedOptionalInt.of(delegate.findFirst());
	}

	public CheckedOptionalDouble average() throws EX {
		return CheckedOptionalDouble.of(delegate.average());
	}

	// Intermediate operations

	public CheckedIntStream<EX> limit(long p0) {
		return fromStream(delegate.limit(p0));
	}

	public CheckedIntStream<EX> skip(long p0) {
		return fromStream(delegate.skip(p0));
	}

	public CheckedIntStream<EX> peek(CheckedIntConsumer<EX> p0) {
		return fromStream(delegate.peek(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> filter(CheckedIntPredicate<EX> p0) {
		return fromStream(delegate.filter(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> parallel() {
		return fromStream(delegate.parallel());
	}

	public CheckedIntStream<EX> map(CheckedIntUnaryOperator<EX> p0) {
		return fromStream(delegate.map(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> distinct() {
		return fromStream(delegate.distinct());
	}

	public CheckedIntStream<EX> flatMap(CheckedIntFunction<? extends IntStream, EX> p0) {
		return fromStream(delegate.flatMap(p0.sneakyThrow()));
	}

	public CheckedDoubleStream<EX> mapToDouble(CheckedIntToDoubleFunction<EX> p0) {
		return fromStream(delegate.mapToDouble(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> mapToLong(CheckedIntToLongFunction<EX> p0) {
		return fromStream(delegate.mapToLong(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> sorted() {
		return fromStream(delegate.sorted());
	}

	public CheckedIntStream<EX> sequential() {
		return fromStream(delegate.sequential());
	}

	public <U> CheckedStream<U, EX> mapToObj(CheckedIntFunction<? extends U, EX> p0) {
		return fromStream(delegate.mapToObj(p0.sneakyThrow()));
	}

	public CheckedStream<Integer, EX> boxed() {
		return fromStream(delegate.boxed());
	}

	public CheckedDoubleStream<EX> asDoubleStream() {
		return fromStream(delegate.asDoubleStream());
	}

	public CheckedLongStream<EX> asLongStream() {
		return fromStream(delegate.asLongStream());
	}

}
