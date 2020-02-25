/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked;

import java.util.function.Predicate;
import java.util.stream.Stream;

import be.gyrbo.checked.function.CheckedPredicate;
import be.gyrbo.checked.stream.CheckedStream;

/**
 * Facade for all the "checked" versions of regular classes
 */
public interface Checked {
	public static <T, EX extends Exception> CheckedStream<T, EX> stream(Stream<T> stream, Class<EX> exception) {
		return CheckedStream.of(stream, exception);
	}
	
	public static <T, EX extends Exception> CheckedPredicate.Helper<T, EX> predicate(
			CheckedPredicate.Helper<T, EX> predicate) {
		return predicate;
	}
	
	public static <T, EX extends Exception> CheckedPredicate.Helper<T, EX> predicateOf(Predicate<T> predicate) {
		return predicate::test;
	}
}
