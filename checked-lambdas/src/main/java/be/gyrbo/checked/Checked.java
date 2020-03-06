/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import be.gyrbo.checked.function.CheckedBiFunction;
import be.gyrbo.checked.function.CheckedBinaryOperator;
import be.gyrbo.checked.function.CheckedConsumer;
import be.gyrbo.checked.function.CheckedDoubleBinaryOperator;
import be.gyrbo.checked.function.CheckedDoubleConsumer;
import be.gyrbo.checked.function.CheckedDoubleFunction;
import be.gyrbo.checked.function.CheckedDoublePredicate;
import be.gyrbo.checked.function.CheckedDoubleToIntFunction;
import be.gyrbo.checked.function.CheckedDoubleToLongFunction;
import be.gyrbo.checked.function.CheckedFunction;
import be.gyrbo.checked.function.CheckedIntBinaryOperator;
import be.gyrbo.checked.function.CheckedIntConsumer;
import be.gyrbo.checked.function.CheckedIntFunction;
import be.gyrbo.checked.function.CheckedIntPredicate;
import be.gyrbo.checked.function.CheckedIntToDoubleFunction;
import be.gyrbo.checked.function.CheckedIntToLongFunction;
import be.gyrbo.checked.function.CheckedLongBinaryOperator;
import be.gyrbo.checked.function.CheckedLongConsumer;
import be.gyrbo.checked.function.CheckedLongFunction;
import be.gyrbo.checked.function.CheckedLongPredicate;
import be.gyrbo.checked.function.CheckedLongToDoubleFunction;
import be.gyrbo.checked.function.CheckedLongToIntFunction;
import be.gyrbo.checked.function.CheckedPredicate;
import be.gyrbo.checked.function.CheckedToDoubleBiFunction;
import be.gyrbo.checked.function.CheckedToDoubleFunction;
import be.gyrbo.checked.function.CheckedToIntBiFunction;
import be.gyrbo.checked.function.CheckedToIntFunction;
import be.gyrbo.checked.function.CheckedToLongBiFunction;
import be.gyrbo.checked.function.CheckedToLongFunction;
import be.gyrbo.checked.stream.CheckedDoubleStream;
import be.gyrbo.checked.stream.CheckedIntStream;
import be.gyrbo.checked.stream.CheckedLongStream;
import be.gyrbo.checked.stream.CheckedStream;

public class Checked {
	public static <T, EX extends Exception> CheckedStream<T, EX> of(Stream<T> stream, Class<EX> exception) {
		return CheckedStream.of(stream, exception);
	}

	public static <EX extends Exception> CheckedIntStream<EX> of(IntStream stream, Class<EX> exception) {
		return CheckedIntStream.of(stream, exception);
	}

	public static <EX extends Exception> CheckedLongStream<EX> of(LongStream stream, Class<EX> exception) {
		return CheckedLongStream.of(stream, exception);
	}

	public static <EX extends Exception> CheckedDoubleStream<EX> of(DoubleStream stream, Class<EX> exception) {
		return CheckedDoubleStream.of(stream, exception);
	}

	public static <T, EX extends Exception> CheckedPredicate.Helper<T, EX> predicate(
			CheckedPredicate.Helper<T, EX> predicate) {
		return predicate;
	}
	
	public static <T, EX extends Exception> CheckedPredicate.Adapter<T, EX> of(
			Predicate<T> predicate) {
		return predicate::test;
	}
	
	public static <T, EX extends Exception> CheckedConsumer.Helper<T, EX> consumer(
			CheckedConsumer.Helper<T, EX> predicate) {
		return predicate;
	}
	
	public static <T, EX extends Exception> CheckedConsumer.Adapter<T, EX> of(
			Consumer<T> predicate) {
		return predicate::accept;
	}
	
	public static <T, U, R, EX extends Exception> CheckedBiFunction.Helper<T, U, R, EX> biFunction(
			CheckedBiFunction.Helper<T, U, R, EX> function) {
		return function;
	}
	
	public static <T, U, R, EX extends Exception> CheckedBiFunction.Adapter<T, U, R, EX> of(
			BiFunction<T, U, R> function) {
		return function::apply;
	}
	
	public static <T, EX extends Exception> CheckedBinaryOperator.Helper<T, EX> binaryOperator(
			CheckedBinaryOperator.Helper<T, EX> function) {
		return function;
	}
	
	public static <T, EX extends Exception> CheckedBinaryOperator.Adapter<T, EX> of(
			BinaryOperator<T> function) {
		return function::apply;
	}
	
	public static <T, R, EX extends Exception> CheckedFunction.Helper<T, R, EX> function(
			CheckedFunction.Helper<T, R, EX> function) {
		return function;
	}
	
	public static <T, R, EX extends Exception> CheckedFunction.Adapter<T, R, EX> of(
			Function<T, R> function) {
		return function::apply;
	}
	
	public static <T, EX extends Exception> CheckedToIntFunction.Helper<T, EX> toIntFunction(
			CheckedToIntFunction.Helper<T, EX> function) {
		return function;
	}
	
	public static <T, EX extends Exception> CheckedToIntFunction.Adapter<T, EX> of(
			ToIntFunction<T> function) {
		return function::applyAsInt;
	}
	
	public static <T, EX extends Exception> CheckedToLongFunction.Helper<T, EX> toLongFunction(
			CheckedToLongFunction.Helper<T, EX> function) {
		return function;
	}
	
	public static <T, EX extends Exception> CheckedToLongFunction.Adapter<T, EX> of(
			ToLongFunction<T> function) {
		return function::applyAsLong;
	}
	
	public static <T, EX extends Exception> CheckedToDoubleFunction.Helper<T, EX> toDoubleFunction(
			CheckedToDoubleFunction.Helper<T, EX> function) {
		return function;
	}
	
	public static <T, EX extends Exception> CheckedToDoubleFunction.Adapter<T, EX> of(
			ToDoubleFunction<T> function) {
		return function::applyAsDouble;
	}
	
	public static <EX extends Exception> CheckedIntPredicate.Helper<EX> intPredicate(
			CheckedIntPredicate.Helper<EX> predicate) {
		return predicate;
	}
	
	public static <EX extends Exception> CheckedIntPredicate.Adapter<EX> of(
			IntPredicate predicate) {
		return predicate::test;
	}
	
	public static <EX extends Exception> CheckedIntConsumer.Helper<EX> intConsumer(
			CheckedIntConsumer.Helper<EX> predicate) {
		return predicate;
	}
	
	public static <EX extends Exception> CheckedIntConsumer.Adapter<EX> of(
			IntConsumer predicate) {
		return predicate::accept;
	}
	
	public static <T, U, EX extends Exception> CheckedToIntBiFunction.Helper<T, U, EX> toIntBiFunction(
			CheckedToIntBiFunction.Helper<T, U, EX> function) {
		return function;
	}
	
	public static <T, U, EX extends Exception> CheckedToIntBiFunction.Adapter<T, U, EX> of(
			ToIntBiFunction<T, U> function) {
		return function::applyAsInt;
	}
	
	public static <EX extends Exception> CheckedIntBinaryOperator.Helper<EX> intBinaryOperator(
			CheckedIntBinaryOperator.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedIntBinaryOperator.Adapter<EX> of(
			IntBinaryOperator function) {
		return function::applyAsInt;
	}
	
	public static <R, EX extends Exception> CheckedIntFunction.Helper<R, EX> intFunction(
			CheckedIntFunction.Helper<R, EX> function) {
		return function;
	}
	
	public static <R, EX extends Exception> CheckedIntFunction.Adapter<R, EX> of(
			IntFunction<R> function) {
		return function::apply;
	}
	
	public static <EX extends Exception> CheckedIntToLongFunction.Helper<EX> intToLongFunction(
			CheckedIntToLongFunction.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedIntToLongFunction.Adapter<EX> of(
			IntToLongFunction function) {
		return function::applyAsLong;
	}
	
	public static <EX extends Exception> CheckedIntToDoubleFunction.Helper<EX> intToDoubleFunction(
			CheckedIntToDoubleFunction.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedIntToDoubleFunction.Adapter<EX> of(
			IntToDoubleFunction function) {
		return function::applyAsDouble;
	}
	
	public static <EX extends Exception> CheckedLongPredicate.Helper<EX> longPredicate(
			CheckedLongPredicate.Helper<EX> predicate) {
		return predicate;
	}
	
	public static <EX extends Exception> CheckedLongPredicate.Adapter<EX> of(
			LongPredicate predicate) {
		return predicate::test;
	}
	
	public static <EX extends Exception> CheckedLongConsumer.Helper<EX> longConsumer(
			CheckedLongConsumer.Helper<EX> predicate) {
		return predicate;
	}
	
	public static <EX extends Exception> CheckedLongConsumer.Adapter<EX> of(
			LongConsumer predicate) {
		return predicate::accept;
	}
	
	public static <T, U, EX extends Exception> CheckedToLongBiFunction.Helper<T, U, EX> toLongBiFunction(
			CheckedToLongBiFunction.Helper<T, U, EX> function) {
		return function;
	}
	
	public static <T, U, EX extends Exception> CheckedToLongBiFunction.Adapter<T, U, EX> of(
			ToLongBiFunction<T, U> function) {
		return function::applyAsLong;
	}
	
	public static <EX extends Exception> CheckedLongBinaryOperator.Helper<EX> longBinaryOperator(
			CheckedLongBinaryOperator.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedLongBinaryOperator.Adapter<EX> of(
			LongBinaryOperator function) {
		return function::applyAsLong;
	}
	
	public static <R, EX extends Exception> CheckedLongFunction.Helper<R, EX> longFunction(
			CheckedLongFunction.Helper<R, EX> function) {
		return function;
	}
	
	public static <R, EX extends Exception> CheckedLongFunction.Adapter<R, EX> of(
			LongFunction<R> function) {
		return function::apply;
	}
	
	public static <EX extends Exception> CheckedLongToIntFunction.Helper<EX> longToIntFunction(
			CheckedLongToIntFunction.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedLongToIntFunction.Adapter<EX> of(
			LongToIntFunction function) {
		return function::applyAsInt;
	}
	
	public static <EX extends Exception> CheckedLongToDoubleFunction.Helper<EX> longToDoubleFunction(
			CheckedLongToDoubleFunction.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedLongToDoubleFunction.Adapter<EX> of(
			LongToDoubleFunction function) {
		return function::applyAsDouble;
	}
	
	public static <EX extends Exception> CheckedDoublePredicate.Helper<EX> doublePredicate(
			CheckedDoublePredicate.Helper<EX> predicate) {
		return predicate;
	}
	
	public static <EX extends Exception> CheckedDoublePredicate.Adapter<EX> of(
			DoublePredicate predicate) {
		return predicate::test;
	}
	
	public static <EX extends Exception> CheckedDoubleConsumer.Helper<EX> doubleConsumer(
			CheckedDoubleConsumer.Helper<EX> predicate) {
		return predicate;
	}
	
	public static <EX extends Exception> CheckedDoubleConsumer.Adapter<EX> of(
			DoubleConsumer predicate) {
		return predicate::accept;
	}
	
	public static <T, U, EX extends Exception> CheckedToDoubleBiFunction.Helper<T, U, EX> toDoubleBiFunction(
			CheckedToDoubleBiFunction.Helper<T, U, EX> function) {
		return function;
	}
	
	public static <T, U, EX extends Exception> CheckedToDoubleBiFunction.Adapter<T, U, EX> of(
			ToDoubleBiFunction<T, U> function) {
		return function::applyAsDouble;
	}
	
	public static <EX extends Exception> CheckedDoubleBinaryOperator.Helper<EX> doubleBinaryOperator(
			CheckedDoubleBinaryOperator.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedDoubleBinaryOperator.Adapter<EX> of(
			DoubleBinaryOperator function) {
		return function::applyAsDouble;
	}
	
	public static <R, EX extends Exception> CheckedDoubleFunction.Helper<R, EX> doubleFunction(
			CheckedDoubleFunction.Helper<R, EX> function) {
		return function;
	}
	
	public static <R, EX extends Exception> CheckedDoubleFunction.Adapter<R, EX> of(
			DoubleFunction<R> function) {
		return function::apply;
	}
	
	public static <EX extends Exception> CheckedDoubleToIntFunction.Helper<EX> doubleToIntFunction(
			CheckedDoubleToIntFunction.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedDoubleToIntFunction.Adapter<EX> of(
			DoubleToIntFunction function) {
		return function::applyAsInt;
	}
	
	public static <EX extends Exception> CheckedDoubleToLongFunction.Helper<EX> doubleToLongFunction(
			CheckedDoubleToLongFunction.Helper<EX> function) {
		return function;
	}
	
	public static <EX extends Exception> CheckedDoubleToLongFunction.Adapter<EX> of(
			DoubleToLongFunction function) {
		return function::applyAsLong;
	}
	
}
