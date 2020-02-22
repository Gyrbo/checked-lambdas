/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import be.gyrbo.checked.function.CheckedPredicate;

public class CheckedPredicateTest {

	private static final String INPUT = "INPUT";
	

	@Test
	void testFallback() {
		Predicate<String> predicate =
				Checked.predicate(CheckedPredicateTest::throwParseException)
			.fallbackTo(true);
		assertTrue(predicate.test(INPUT));
	}
	
	@Test
	void testRethrowUnchecked1() {
		try {
			Checked.predicate(CheckedPredicateTest::throwParseException)
				.rethrow(DummyException::new)
				.test(INPUT);
			
			fail("DummyException should have been thrown");
		} catch (DummyException e) {
			// Expected
		}
	}
	
	@Test
	void testRethrowUnchecked2() {
		try {
			Predicate<String> predicate = Checked.predicate(CheckedPredicateTest::throwParseException)
				.rethrowUnchecked(DummyException::new);
			predicate.test(INPUT);
			
			fail("DummyException should have been thrown");
		} catch (DummyException e) {
			// Expected
		}
	}
	
	@Test
	void testOrTry() {
		CheckedPredicate<String, ParseException> predicate =
				Checked.predicate(CheckedPredicateTest::throwParseException)
			.orTry(CheckedPredicateTest::isEmpty);
		assertDoesNotThrow(() -> predicate.test(INPUT)); 
	}
	
	@Test
	void testExceptUnchecked() {
		AtomicBoolean dummyExceptionSeen = new AtomicBoolean();
		Checked.predicate(CheckedPredicateTest::throwParseException)
			.rethrow(DummyException::new)
			.exceptUnchecked(DummyException.class, e -> dummyExceptionSeen.set(true))
			.fallbackTo(true)
			.test(INPUT);
		
		assertTrue(dummyExceptionSeen.get());
	}
	
	@Test
	void testOptional() {
		Function<String, Optional<Boolean>> optionalPredicate = Checked.predicate(CheckedPredicateTest::throwParseException)
			.optional();
		Optional<Boolean> optional = optionalPredicate.apply(INPUT);
		
		assertFalse(optional.isPresent());
	}

	
	
	public static boolean throwParseException(String s) throws ParseException {
		throw new ParseException(s, 0);
	}
	
	private static boolean isEmpty(String s) {
		return s.isEmpty();
	}
	
	private static class DummyException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public DummyException(Throwable cause) {
			super(cause);
		}
	}
}
