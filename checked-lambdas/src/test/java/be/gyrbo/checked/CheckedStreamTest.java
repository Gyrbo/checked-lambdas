/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import be.gyrbo.checked.util.CheckedOptionalInt;

public class CheckedStreamTest {

	@Test
	public void testFilterPredicate() throws ParseException {
		Predicate<String> isStrEmpty = String::isEmpty;
		
		String result = Checked.of(Stream.of("a", "b"), ParseException.class)
			.filter(Checked.of(isStrEmpty.negate()))
			.collect(Collectors.joining(", "));
		
		assertEquals("a, b", result);
	}

	@Test
	public void testFilterCheckedPredicate() {
		try {
			Checked.of(Stream.of("a", "b"), ParseException.class)
				.filter(CheckedPredicateTest::throwParseException)
				.collect(Collectors.joining(", "));
			
			fail("Should have thrown a ParseException");
		} catch (ParseException e) {
			// Expected
		}
	}

	@Test
	public void testFilterHandledCheckedPredicate() throws ParseException {
		String result = Checked.of(Stream.of("a", "b"), ParseException.class)
			.filter(Checked.predicate(CheckedPredicateTest::throwParseException).orReturn(true))
			.collect(Collectors.joining(", "));
		
		assertEquals("a, b", result);
	}
	
	@Test
	public void testReduce() throws ParseException {
		CheckedOptionalInt sum = Checked.of(Stream.of("1", "2", "3"), ParseException.class)
				.mapToInt(Checked.<String, NumberFormatException>toIntFunction(Integer::parseInt)
						.rethrow(e -> new ParseException(e.getMessage(), 0)))
				.reduce(Checked.intBinaryOperator((a, b) -> a + b));
		
		assertTrue(sum.isPresent());
		assertEquals(6, sum.getAsInt());
	}
}
