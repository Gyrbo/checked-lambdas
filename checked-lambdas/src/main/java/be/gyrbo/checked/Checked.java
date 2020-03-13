/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import be.gyrbo.checked.function.CheckedBiConsumer;
import be.gyrbo.checked.function.CheckedBiFunction;
import be.gyrbo.checked.function.CheckedBiPredicate;
import be.gyrbo.checked.function.CheckedBinaryOperator;
import be.gyrbo.checked.function.CheckedBooleanSupplier;
import be.gyrbo.checked.function.CheckedConsumer;
import be.gyrbo.checked.function.CheckedDoubleBinaryOperator;
import be.gyrbo.checked.function.CheckedDoubleConsumer;
import be.gyrbo.checked.function.CheckedDoubleFunction;
import be.gyrbo.checked.function.CheckedDoublePredicate;
import be.gyrbo.checked.function.CheckedDoubleSupplier;
import be.gyrbo.checked.function.CheckedDoubleToIntFunction;
import be.gyrbo.checked.function.CheckedDoubleToLongFunction;
import be.gyrbo.checked.function.CheckedDoubleUnaryOperator;
import be.gyrbo.checked.function.CheckedFunction;
import be.gyrbo.checked.function.CheckedIntBinaryOperator;
import be.gyrbo.checked.function.CheckedIntConsumer;
import be.gyrbo.checked.function.CheckedIntFunction;
import be.gyrbo.checked.function.CheckedIntPredicate;
import be.gyrbo.checked.function.CheckedIntSupplier;
import be.gyrbo.checked.function.CheckedIntToDoubleFunction;
import be.gyrbo.checked.function.CheckedIntToLongFunction;
import be.gyrbo.checked.function.CheckedIntUnaryOperator;
import be.gyrbo.checked.function.CheckedLongBinaryOperator;
import be.gyrbo.checked.function.CheckedLongConsumer;
import be.gyrbo.checked.function.CheckedLongFunction;
import be.gyrbo.checked.function.CheckedLongPredicate;
import be.gyrbo.checked.function.CheckedLongSupplier;
import be.gyrbo.checked.function.CheckedLongToDoubleFunction;
import be.gyrbo.checked.function.CheckedLongToIntFunction;
import be.gyrbo.checked.function.CheckedLongUnaryOperator;
import be.gyrbo.checked.function.CheckedObjDoubleConsumer;
import be.gyrbo.checked.function.CheckedObjIntConsumer;
import be.gyrbo.checked.function.CheckedObjLongConsumer;
import be.gyrbo.checked.function.CheckedPredicate;
import be.gyrbo.checked.function.CheckedSupplier;
import be.gyrbo.checked.function.CheckedToDoubleBiFunction;
import be.gyrbo.checked.function.CheckedToDoubleFunction;
import be.gyrbo.checked.function.CheckedToIntBiFunction;
import be.gyrbo.checked.function.CheckedToIntFunction;
import be.gyrbo.checked.function.CheckedToLongBiFunction;
import be.gyrbo.checked.function.CheckedToLongFunction;
import be.gyrbo.checked.function.CheckedUnaryOperator;
import be.gyrbo.checked.stream.CheckedDoubleStream;
import be.gyrbo.checked.stream.CheckedIntStream;
import be.gyrbo.checked.stream.CheckedLongStream;
import be.gyrbo.checked.stream.CheckedStream;
import be.gyrbo.checked.util.CheckedOptional;
import be.gyrbo.checked.util.CheckedOptionalDouble;
import be.gyrbo.checked.util.CheckedOptionalInt;
import be.gyrbo.checked.util.CheckedOptionalLong;

public class Checked {
	public static <T, EX extends Exception> CheckedStream<T, EX> of(Stream<T> stream, Class<EX> exception) {
		return CheckedStream.of(stream);
	}

	public static <EX extends Exception> CheckedIntStream<EX> of(IntStream stream, Class<EX> exception) {
		return CheckedIntStream.of(stream);
	}

	public static <EX extends Exception> CheckedLongStream<EX> of(LongStream stream, Class<EX> exception) {
		return CheckedLongStream.of(stream);
	}

	public static <EX extends Exception> CheckedDoubleStream<EX> of(DoubleStream stream, Class<EX> exception) {
		return CheckedDoubleStream.of(stream);
	}

	public static <EX extends Exception> CheckedBooleanSupplier.Helper<EX> booleanSupplier(
			CheckedBooleanSupplier.Helper<EX> supplier) {
		return supplier;
	}
	
	public static <EX extends Exception> CheckedBooleanSupplier.Adapter<EX> of(
			BooleanSupplier supplier) {
		return supplier::getAsBoolean;
	}
	
	public static <T, U, EX extends Exception> CheckedBiPredicate.Helper<T, U, EX> biPredicate(
			CheckedBiPredicate.Helper<T, U, EX> predicate) {
		return predicate;
	}
	
	public static <T, U, EX extends Exception> CheckedBiPredicate.Adapter<T, U, EX> of(
			BiPredicate<T, U> predicate) {
		return predicate::test;
	}
	
	public static <T> CheckedOptional<T> of(Optional<T> optional) {
		return CheckedOptional.of(optional);
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
			CheckedConsumer.Helper<T, EX> consumer) {
		return consumer;
	}
	
	public static <T, EX extends Exception> CheckedConsumer.Adapter<T, EX> of(
			Consumer<T> consumer) {
		return consumer::accept;
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
			CheckedBinaryOperator.Helper<T, EX> operator) {
		return operator;
	}
	
	public static <T, EX extends Exception> CheckedBinaryOperator.Adapter<T, EX> of(
			BinaryOperator<T> operator) {
		return operator::apply;
	}
	
	public static <T, U, EX extends Exception> CheckedBiConsumer.Helper<T, U, EX> biConsumer(
			CheckedBiConsumer.Helper<T, U, EX> consumer) {
		return consumer;
	}
	
	public static <T, U, EX extends Exception> CheckedBiConsumer.Adapter<T, U, EX> of(
			BiConsumer<T, U> consumer) {
		return consumer::accept;
	}
	
	public static <T, EX extends Exception> CheckedSupplier.Helper<T, EX> supplier(
			CheckedSupplier.Helper<T, EX> supplier) {
		return supplier;
	}
	
	public static <T, EX extends Exception> CheckedSupplier.Adapter<T, EX> of(
			Supplier<T> supplier) {
		return supplier::get;
	}
	
	public static <T, EX extends Exception> CheckedUnaryOperator.Helper<T, EX> unaryOperator(
			CheckedUnaryOperator.Helper<T, EX> operator) {
		return operator;
	}
	
	public static <T, EX extends Exception> CheckedUnaryOperator.Adapter<T, EX> of(
			UnaryOperator<T> operator) {
		return operator::apply;
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
	
	public static  CheckedOptionalInt of(OptionalInt optional) {
		return CheckedOptionalInt.of(optional);
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
			CheckedIntConsumer.Helper<EX> consumer) {
		return consumer;
	}
	
	public static <EX extends Exception> CheckedIntConsumer.Adapter<EX> of(
			IntConsumer consumer) {
		return consumer::accept;
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
			CheckedIntBinaryOperator.Helper<EX> operator) {
		return operator;
	}
	
	public static <EX extends Exception> CheckedIntBinaryOperator.Adapter<EX> of(
			IntBinaryOperator operator) {
		return operator::applyAsInt;
	}
	
	public static <T, EX extends Exception> CheckedObjIntConsumer.Helper<T, EX> objIntConsumer(
			CheckedObjIntConsumer.Helper<T, EX> consumer) {
		return consumer;
	}
	
	public static <T, EX extends Exception> CheckedObjIntConsumer.Adapter<T, EX> of(
			ObjIntConsumer<T> consumer) {
		return consumer::accept;
	}
	
	public static <EX extends Exception> CheckedIntSupplier.Helper<EX> intSupplier(
			CheckedIntSupplier.Helper<EX> supplier) {
		return supplier;
	}
	
	public static <EX extends Exception> CheckedIntSupplier.Adapter<EX> of(
			IntSupplier supplier) {
		return supplier::getAsInt;
	}
	
	public static <EX extends Exception> CheckedIntUnaryOperator.Helper<EX> intUnaryOperator(
			CheckedIntUnaryOperator.Helper<EX> operator) {
		return operator;
	}
	
	public static <EX extends Exception> CheckedIntUnaryOperator.Adapter<EX> of(
			IntUnaryOperator operator) {
		return operator::applyAsInt;
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
	
	public static  CheckedOptionalLong of(OptionalLong optional) {
		return CheckedOptionalLong.of(optional);
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
			CheckedLongConsumer.Helper<EX> consumer) {
		return consumer;
	}
	
	public static <EX extends Exception> CheckedLongConsumer.Adapter<EX> of(
			LongConsumer consumer) {
		return consumer::accept;
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
			CheckedLongBinaryOperator.Helper<EX> operator) {
		return operator;
	}
	
	public static <EX extends Exception> CheckedLongBinaryOperator.Adapter<EX> of(
			LongBinaryOperator operator) {
		return operator::applyAsLong;
	}
	
	public static <T, EX extends Exception> CheckedObjLongConsumer.Helper<T, EX> objLongConsumer(
			CheckedObjLongConsumer.Helper<T, EX> consumer) {
		return consumer;
	}
	
	public static <T, EX extends Exception> CheckedObjLongConsumer.Adapter<T, EX> of(
			ObjLongConsumer<T> consumer) {
		return consumer::accept;
	}
	
	public static <EX extends Exception> CheckedLongSupplier.Helper<EX> longSupplier(
			CheckedLongSupplier.Helper<EX> supplier) {
		return supplier;
	}
	
	public static <EX extends Exception> CheckedLongSupplier.Adapter<EX> of(
			LongSupplier supplier) {
		return supplier::getAsLong;
	}
	
	public static <EX extends Exception> CheckedLongUnaryOperator.Helper<EX> longUnaryOperator(
			CheckedLongUnaryOperator.Helper<EX> operator) {
		return operator;
	}
	
	public static <EX extends Exception> CheckedLongUnaryOperator.Adapter<EX> of(
			LongUnaryOperator operator) {
		return operator::applyAsLong;
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
	
	public static  CheckedOptionalDouble of(OptionalDouble optional) {
		return CheckedOptionalDouble.of(optional);
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
			CheckedDoubleConsumer.Helper<EX> consumer) {
		return consumer;
	}
	
	public static <EX extends Exception> CheckedDoubleConsumer.Adapter<EX> of(
			DoubleConsumer consumer) {
		return consumer::accept;
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
			CheckedDoubleBinaryOperator.Helper<EX> operator) {
		return operator;
	}
	
	public static <EX extends Exception> CheckedDoubleBinaryOperator.Adapter<EX> of(
			DoubleBinaryOperator operator) {
		return operator::applyAsDouble;
	}
	
	public static <T, EX extends Exception> CheckedObjDoubleConsumer.Helper<T, EX> objDoubleConsumer(
			CheckedObjDoubleConsumer.Helper<T, EX> consumer) {
		return consumer;
	}
	
	public static <T, EX extends Exception> CheckedObjDoubleConsumer.Adapter<T, EX> of(
			ObjDoubleConsumer<T> consumer) {
		return consumer::accept;
	}
	
	public static <EX extends Exception> CheckedDoubleSupplier.Helper<EX> doubleSupplier(
			CheckedDoubleSupplier.Helper<EX> supplier) {
		return supplier;
	}
	
	public static <EX extends Exception> CheckedDoubleSupplier.Adapter<EX> of(
			DoubleSupplier supplier) {
		return supplier::getAsDouble;
	}
	
	public static <EX extends Exception> CheckedDoubleUnaryOperator.Helper<EX> doubleUnaryOperator(
			CheckedDoubleUnaryOperator.Helper<EX> operator) {
		return operator;
	}
	
	public static <EX extends Exception> CheckedDoubleUnaryOperator.Adapter<EX> of(
			DoubleUnaryOperator operator) {
		return operator::applyAsDouble;
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
