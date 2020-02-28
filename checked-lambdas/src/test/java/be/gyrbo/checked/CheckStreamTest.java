/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class CheckStreamTest {

	@Test
	void testFilterPredicate() throws ParseException {
		Predicate<String> isStrEmpty = String::isEmpty;
		
		String result = Checked.stream(Stream.of("a", "b"), ParseException.class)
			.filter(Checked.predicateOf(isStrEmpty.negate()))
			.collect(Collectors.joining(", "));
		
		assertEquals("a, b", result);
	}

	@Test
	void testFilterCheckedPredicate() {
		try {
			Checked.stream(Stream.of("a", "b"), ParseException.class)
				.filter(CheckedPredicateTest::throwParseException)
				.collect(Collectors.joining(", "));
			
			fail("Should have thrown a ParseException");
		} catch (ParseException e) {
			// Expected
		}
	}

	@Test
	void testFilterHandledCheckedPredicate() throws ParseException {
		String result = Checked.stream(Stream.of("a", "b"), ParseException.class)
			.filter(Checked.predicate(CheckedPredicateTest::throwParseException).orReturn(true))
			.collect(Collectors.joining(", "));
		
		assertEquals("a, b", result);
	}
}
