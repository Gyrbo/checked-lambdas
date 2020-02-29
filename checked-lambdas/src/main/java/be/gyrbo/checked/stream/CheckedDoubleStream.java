/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import be.gyrbo.checked.function.*;

public class CheckedDoubleStream<EX extends Exception> extends CheckedBaseStream<DoubleStream, EX> {
	
	protected CheckedDoubleStream(DoubleStream delegate, Class<EX> exception) {
		super(delegate, exception);
	}
	
	public static <T, EX extends Exception> CheckedDoubleStream<EX> of(DoubleStream delegate, Class<EX> exception) {
		return new CheckedDoubleStream<EX>(delegate, exception);
	}
	
	// Terminal operations

	public long count() throws EX {
		return delegate.count();
	}

	public OptionalDouble min() throws EX {
		return delegate.min();
	}

	public OptionalDouble max() throws EX {
		return delegate.max();
	}

	public PrimitiveIterator.OfDouble iterator() throws EX {
		return delegate.iterator();
	}

	public double[] toArray() throws EX {
		return delegate.toArray();
	}

	public Spliterator.OfDouble spliterator() throws EX {
		return delegate.spliterator();
	}

	public <R> R collect(Supplier<R> p0,
			ObjDoubleConsumer<R> p1,
			BiConsumer<R, R> p2) throws EX {
		return delegate.collect(p0, p1, p2);
	}

	public void forEach(CheckedDoubleConsumer<EX> p0) throws EX {
		delegate.forEach(p0.sneakyThrow());
	}

	public double sum() throws EX {
		return delegate.sum();
	}

	public OptionalDouble reduce(DoubleBinaryOperator p0) throws EX {
		return delegate.reduce(p0);
	}

	public double reduce(double p0,
			DoubleBinaryOperator p1) throws EX {
		return delegate.reduce(p0, p1);
	}

	public boolean allMatch(CheckedDoublePredicate<EX> p0) throws EX {
		return delegate.allMatch(p0.sneakyThrow());
	}

	public boolean anyMatch(CheckedDoublePredicate<EX> p0) throws EX {
		return delegate.anyMatch(p0.sneakyThrow());
	}

	public OptionalDouble findAny() throws EX {
		return delegate.findAny();
	}

	public OptionalDouble findFirst() throws EX {
		return delegate.findFirst();
	}

	public void forEachOrdered(CheckedDoubleConsumer<EX> p0) throws EX {
		delegate.forEachOrdered(p0.sneakyThrow());
	}

	public boolean noneMatch(CheckedDoublePredicate<EX> p0) throws EX {
		return delegate.noneMatch(p0.sneakyThrow());
	}

	public OptionalDouble average() throws EX {
		return delegate.average();
	}

	public DoubleSummaryStatistics summaryStatistics() throws EX {
		return delegate.summaryStatistics();
	}

	// Intermediate operations

	public CheckedDoubleStream<EX> limit(long p0) {
		return fromStream(delegate.limit(p0));
	}

	public CheckedDoubleStream<EX> skip(long p0) {
		return fromStream(delegate.skip(p0));
	}

	public CheckedDoubleStream<EX> peek(CheckedDoubleConsumer<EX> p0) {
		return fromStream(delegate.peek(p0.sneakyThrow()));
	}

	public CheckedDoubleStream<EX> filter(CheckedDoublePredicate<EX> p0) {
		return fromStream(delegate.filter(p0.sneakyThrow()));
	}

	public CheckedDoubleStream<EX> parallel() {
		return fromStream(delegate.parallel());
	}

	public CheckedDoubleStream<EX> map(DoubleUnaryOperator p0) {
		return fromStream(delegate.map(p0));
	}

	public CheckedDoubleStream<EX> distinct() {
		return fromStream(delegate.distinct());
	}

	public CheckedDoubleStream<EX> flatMap(CheckedDoubleFunction<? extends DoubleStream, EX> p0) {
		return fromStream(delegate.flatMap(p0.sneakyThrow()));
	}

	public CheckedIntStream<EX> mapToInt(CheckedDoubleToIntFunction<EX> p0) {
		return fromStream(delegate.mapToInt(p0.sneakyThrow()));
	}

	public CheckedLongStream<EX> mapToLong(CheckedDoubleToLongFunction<EX> p0) {
		return fromStream(delegate.mapToLong(p0.sneakyThrow()));
	}

	public CheckedDoubleStream<EX> sorted() {
		return fromStream(delegate.sorted());
	}

	public CheckedDoubleStream<EX> sequential() {
		return fromStream(delegate.sequential());
	}

	public <U> CheckedStream<U, EX> mapToObj(CheckedDoubleFunction<? extends U, EX> p0) {
		return fromStream(delegate.mapToObj(p0.sneakyThrow()));
	}

	public CheckedStream<Double, EX> boxed() {
		return fromStream(delegate.boxed());
	}

}
