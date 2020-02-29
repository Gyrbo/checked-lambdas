/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import be.gyrbo.checked.function.*;

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

	public OptionalInt min() throws EX {
		return delegate.min();
	}

	public OptionalInt max() throws EX {
		return delegate.max();
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

	public <R> R collect(Supplier<R> p0,
			ObjIntConsumer<R> p1,
			BiConsumer<R, R> p2) throws EX {
		return delegate.collect(p0, p1, p2);
	}

	public void forEach(CheckedIntConsumer<EX> p0) throws EX {
		delegate.forEach(p0.sneakyThrow());
	}

	public int sum() throws EX {
		return delegate.sum();
	}

	public OptionalInt reduce(IntBinaryOperator p0) throws EX {
		return delegate.reduce(p0);
	}

	public int reduce(int p0,
			IntBinaryOperator p1) throws EX {
		return delegate.reduce(p0, p1);
	}

	public boolean allMatch(CheckedIntPredicate<EX> p0) throws EX {
		return delegate.allMatch(p0.sneakyThrow());
	}

	public boolean anyMatch(CheckedIntPredicate<EX> p0) throws EX {
		return delegate.anyMatch(p0.sneakyThrow());
	}

	public OptionalInt findAny() throws EX {
		return delegate.findAny();
	}

	public OptionalInt findFirst() throws EX {
		return delegate.findFirst();
	}

	public void forEachOrdered(CheckedIntConsumer<EX> p0) throws EX {
		delegate.forEachOrdered(p0.sneakyThrow());
	}

	public boolean noneMatch(CheckedIntPredicate<EX> p0) throws EX {
		return delegate.noneMatch(p0.sneakyThrow());
	}

	public OptionalDouble average() throws EX {
		return delegate.average();
	}

	public IntSummaryStatistics summaryStatistics() throws EX {
		return delegate.summaryStatistics();
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

	public CheckedIntStream<EX> map(IntUnaryOperator p0) {
		return fromStream(delegate.map(p0));
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
