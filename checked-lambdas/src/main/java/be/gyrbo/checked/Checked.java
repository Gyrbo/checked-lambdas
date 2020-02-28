/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked;

import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import be.gyrbo.checked.function.CheckedDoubleFunction;
import be.gyrbo.checked.function.CheckedDoublePredicate;
import be.gyrbo.checked.function.CheckedDoubleToIntFunction;
import be.gyrbo.checked.function.CheckedDoubleToLongFunction;
import be.gyrbo.checked.function.CheckedFunction;
import be.gyrbo.checked.function.CheckedIntFunction;
import be.gyrbo.checked.function.CheckedIntPredicate;
import be.gyrbo.checked.function.CheckedIntToDoubleFunction;
import be.gyrbo.checked.function.CheckedIntToLongFunction;
import be.gyrbo.checked.function.CheckedLongFunction;
import be.gyrbo.checked.function.CheckedLongPredicate;
import be.gyrbo.checked.function.CheckedLongToDoubleFunction;
import be.gyrbo.checked.function.CheckedLongToIntFunction;
import be.gyrbo.checked.function.CheckedPredicate;
import be.gyrbo.checked.function.CheckedToDoubleFunction;
import be.gyrbo.checked.function.CheckedToIntFunction;
import be.gyrbo.checked.function.CheckedToLongFunction;
import be.gyrbo.checked.stream.CheckedDoubleStream;
import be.gyrbo.checked.stream.CheckedIntStream;
import be.gyrbo.checked.stream.CheckedLongStream;
import be.gyrbo.checked.stream.CheckedStream;

public class Checked {
	public static <T, EX extends Exception> CheckedStream<T, EX> stream(Stream<T> stream, Class<EX> exception) {
		return CheckedStream.of(stream, exception);
	}

	public static <EX extends Exception> CheckedIntStream<EX> intStream(IntStream stream, Class<EX> exception) {
		return CheckedIntStream.of(stream, exception);
	}

	public static <EX extends Exception> CheckedLongStream<EX> longStream(LongStream stream, Class<EX> exception) {
		return CheckedLongStream.of(stream, exception);
	}

	public static <EX extends Exception> CheckedDoubleStream<EX> doubleStream(DoubleStream stream, Class<EX> exception) {
		return CheckedDoubleStream.of(stream, exception);
	}

	public static <T, EX extends Exception> CheckedPredicate.Helper<T, EX> predicate(
			CheckedPredicate.Helper<T, EX> predicate) {
		return predicate;
	}
	
	public static <T, EX extends Exception> CheckedPredicate.Helper<T, EX> predicateOf(
			Predicate<T> predicate) {
		return predicate::test;
	}
	
	public static <T, R, EX extends Exception> CheckedFunction.Helper<T, R, EX> function(
			CheckedFunction.Helper<T, R, EX> function) {
		return function;
	}
	
	public static <T, R, EX extends Exception> CheckedFunction.Helper<T, R, EX> functionOf(
			Function<T, R> function) {
		return function::apply;
	}
	
	public static <T, EX extends Exception> CheckedToIntFunction.Helper<T, EX> functionToInt(
			CheckedToIntFunction.Helper<T, EX> function) {
		return function;
	}
	
	public static <T, EX extends Exception> CheckedToIntFunction.Helper<T, EX> functionToIntOf(
			ToIntFunction<T> function) {
		return function::applyAsInt;
	}
	
	public static <T, EX extends Exception> CheckedToLongFunction.Helper<T, EX> functionToLong(
			CheckedToLongFunction.Helper<T, EX> function) {
		return function;
	}
	
	public static <T, EX extends Exception> CheckedToLongFunction.Helper<T, EX> functionToLongOf(
			ToLongFunction<T> function) {
		return function::applyAsLong;
	}
	
	public static <T, EX extends Exception> CheckedToDoubleFunction.Helper<T, EX> functionToDouble(
			CheckedToDoubleFunction.Helper<T, EX> function) {
		return function;
	}
	
	public static <T, EX extends Exception> CheckedToDoubleFunction.Helper<T, EX> functionToDoubleOf(
			ToDoubleFunction<T> function) {
		return function::applyAsDouble;
	}
	
	public static <EX extends Exception> CheckedIntPredicate.Helper<EX> intPredicate(
			CheckedIntPredicate.Helper<EX> predicate) {
		return predicate;
	}
	
	public static <EX extends Exception> CheckedIntPredicate.Helper<EX> intPredicateOf(
			IntPredicate predicate) {
		return predicate::test;
	}
	
	public static <R, EX extends Exception> CheckedIntFunction.Helper<R, EX> functionInt(
			CheckedIntFunction.Helper<R, EX> function) {
		return function;
	}
	
	public static <R, EX extends Exception> CheckedIntFunction.Helper<R, EX> functionIntOf(
			IntFunction<R> function) {
		return function::apply;
	}
	
	public static <EX extends Exception> CheckedIntToLongFunction.Helper<EX> functionIntToLong(
			CheckedIntToLongFunction.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedIntToLongFunction.Helper<EX> functionIntToLongOf(
			IntToLongFunction function) {
		return function::applyAsLong;
	}
	
	public static <EX extends Exception> CheckedIntToDoubleFunction.Helper<EX> functionIntToDouble(
			CheckedIntToDoubleFunction.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedIntToDoubleFunction.Helper<EX> functionIntToDoubleOf(
			IntToDoubleFunction function) {
		return function::applyAsDouble;
	}
	
	public static <EX extends Exception> CheckedLongPredicate.Helper<EX> longPredicate(
			CheckedLongPredicate.Helper<EX> predicate) {
		return predicate;
	}
	
	public static <EX extends Exception> CheckedLongPredicate.Helper<EX> longPredicateOf(
			LongPredicate predicate) {
		return predicate::test;
	}
	
	public static <R, EX extends Exception> CheckedLongFunction.Helper<R, EX> functionLong(
			CheckedLongFunction.Helper<R, EX> function) {
		return function;
	}
	
	public static <R, EX extends Exception> CheckedLongFunction.Helper<R, EX> functionLongOf(
			LongFunction<R> function) {
		return function::apply;
	}
	
	public static <EX extends Exception> CheckedLongToIntFunction.Helper<EX> functionLongToInt(
			CheckedLongToIntFunction.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedLongToIntFunction.Helper<EX> functionLongToIntOf(
			LongToIntFunction function) {
		return function::applyAsInt;
	}
	
	public static <EX extends Exception> CheckedLongToDoubleFunction.Helper<EX> functionLongToDouble(
			CheckedLongToDoubleFunction.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedLongToDoubleFunction.Helper<EX> functionLongToDoubleOf(
			LongToDoubleFunction function) {
		return function::applyAsDouble;
	}
	
	public static <EX extends Exception> CheckedDoublePredicate.Helper<EX> doublePredicate(
			CheckedDoublePredicate.Helper<EX> predicate) {
		return predicate;
	}
	
	public static <EX extends Exception> CheckedDoublePredicate.Helper<EX> doublePredicateOf(
			DoublePredicate predicate) {
		return predicate::test;
	}
	
	public static <R, EX extends Exception> CheckedDoubleFunction.Helper<R, EX> functionDouble(
			CheckedDoubleFunction.Helper<R, EX> function) {
		return function;
	}
	
	public static <R, EX extends Exception> CheckedDoubleFunction.Helper<R, EX> functionDoubleOf(
			DoubleFunction<R> function) {
		return function::apply;
	}
	
	public static <EX extends Exception> CheckedDoubleToIntFunction.Helper<EX> functionDoubleToInt(
			CheckedDoubleToIntFunction.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedDoubleToIntFunction.Helper<EX> functionDoubleToIntOf(
			DoubleToIntFunction function) {
		return function::applyAsInt;
	}
	
	public static <EX extends Exception> CheckedDoubleToLongFunction.Helper<EX> functionDoubleToLong(
			CheckedDoubleToLongFunction.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedDoubleToLongFunction.Helper<EX> functionDoubleToLongOf(
			DoubleToLongFunction function) {
		return function::applyAsLong;
	}
	
}
