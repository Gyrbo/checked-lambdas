/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked.stream;

import java.util.DoubleSummaryStatistics;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.stream.DoubleStream;

import be.gyrbo.checked.function.CheckedBiConsumer;
import be.gyrbo.checked.function.CheckedDoubleBinaryOperator;
import be.gyrbo.checked.function.CheckedDoubleConsumer;
import be.gyrbo.checked.function.CheckedDoubleFunction;
import be.gyrbo.checked.function.CheckedDoublePredicate;
import be.gyrbo.checked.function.CheckedDoubleToIntFunction;
import be.gyrbo.checked.function.CheckedDoubleToLongFunction;
import be.gyrbo.checked.function.CheckedDoubleUnaryOperator;
import be.gyrbo.checked.function.CheckedObjDoubleConsumer;
import be.gyrbo.checked.function.CheckedSupplier;
import be.gyrbo.checked.util.CheckedOptionalDouble;

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

	public PrimitiveIterator.OfDouble iterator() throws EX {
		return delegate.iterator();
	}

	public double[] toArray() throws EX {
		return delegate.toArray();
	}

	public Spliterator.OfDouble spliterator() throws EX {
		return delegate.spliterator();
	}

	public <R> R collect(CheckedSupplier<R, EX> p0,
			CheckedObjDoubleConsumer<R, EX> p1,
			CheckedBiConsumer<R, R, EX> p2) throws EX {
		return delegate.collect(p0.sneakyThrow(), p1.sneakyThrow(), p2.sneakyThrow());
	}

	public void forEach(CheckedDoubleConsumer<EX> p0) throws EX {
		delegate.forEach(p0.sneakyThrow());
	}

	public double sum() throws EX {
		return delegate.sum();
	}

	public double reduce(double p0,
			CheckedDoubleBinaryOperator<EX> p1) throws EX {
		return delegate.reduce(p0, p1.sneakyThrow());
	}

	public boolean allMatch(CheckedDoublePredicate<EX> p0) throws EX {
		return delegate.allMatch(p0.sneakyThrow());
	}

	public boolean anyMatch(CheckedDoublePredicate<EX> p0) throws EX {
		return delegate.anyMatch(p0.sneakyThrow());
	}

	public void forEachOrdered(CheckedDoubleConsumer<EX> p0) throws EX {
		delegate.forEachOrdered(p0.sneakyThrow());
	}

	public boolean noneMatch(CheckedDoublePredicate<EX> p0) throws EX {
		return delegate.noneMatch(p0.sneakyThrow());
	}

	public DoubleSummaryStatistics summaryStatistics() throws EX {
		return delegate.summaryStatistics();
	}

	public CheckedOptionalDouble min() throws EX {
		return CheckedOptionalDouble.of(delegate.min());
	}

	public CheckedOptionalDouble max() throws EX {
		return CheckedOptionalDouble.of(delegate.max());
	}

	public CheckedOptionalDouble reduce(CheckedDoubleBinaryOperator<EX> p0) throws EX {
		return CheckedOptionalDouble.of(delegate.reduce(p0.sneakyThrow()));
	}

	public CheckedOptionalDouble findAny() throws EX {
		return CheckedOptionalDouble.of(delegate.findAny());
	}

	public CheckedOptionalDouble findFirst() throws EX {
		return CheckedOptionalDouble.of(delegate.findFirst());
	}

	public CheckedOptionalDouble average() throws EX {
		return CheckedOptionalDouble.of(delegate.average());
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

	public CheckedDoubleStream<EX> map(CheckedDoubleUnaryOperator<EX> p0) {
		return fromStream(delegate.map(p0.sneakyThrow()));
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
