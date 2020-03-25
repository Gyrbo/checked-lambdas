/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package be.gyrbo.checked;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

public class CheckedCollectionsTest {

	@Test
	public void testMap() throws CheckedException {
		Map<String, Integer> map = new HashMap<>(Collections.singletonMap("One", 1));
		
		assertThrows(CheckedException.class, () -> 
			Checked.of(map).computeIfAbsent("Two", CheckedCollectionsTest::throwsFunction));
		
		assertEquals(1, map.size());
		
		Checked.of(map).computeIfAbsent("Three", k -> 3);
		
		assertEquals(2, map.size());
	}

	@Test
	public void testList() throws CheckedException {
		List<String> list = new ArrayList<String>(Arrays.asList("One", "Two", "Three"));
		
		assertThrows(CheckedException.class, () -> 
			Checked.of(list).replaceAll(CheckedCollectionsTest::throwsOperator));
		
		assertEquals(3, list.size());
		
		Checked.of(list).replaceAll(v -> "*");
		
		assertEquals(3, list.size());
		assertEquals("*", list.get(0));
		assertEquals("*", list.get(1));
		assertEquals("*", list.get(2));
	}

	@Test
	public void testSet() throws CheckedException {
		Set<String> set = new TreeSet<String>(Arrays.asList("One", "Two", "Three"));
		
		assertThrows(CheckedException.class, () -> 
			Checked.of(set).removeIf(CheckedCollectionsTest::throwsPredicate));
		
		assertEquals(3, set.size());
		
		Checked.of(set).removeIf(v -> v.length() > 3);
		
		assertEquals(2, set.size());
	}
	
	private static class CheckedException extends Exception {
		private static final long serialVersionUID = 6531267565148508065L;
	}
	
	private static Integer throwsFunction(String in) throws CheckedException {
		throw new CheckedException();
	}
	
	private static String throwsOperator(String in) throws CheckedException {
		throw new CheckedException();
	}
	
	private static boolean throwsPredicate(String in) throws CheckedException {
		throw new CheckedException();
	}
}
