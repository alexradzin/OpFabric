package com.opfabric.operator;

import static com.opfabric.OpFabric.$eq;
import static com.opfabric.OpFabric.$ge;
import static com.opfabric.OpFabric.$gt;
import static com.opfabric.OpFabric.$le;
import static com.opfabric.OpFabric.$lt;
import static com.opfabric.OpFabric.$matchedBy;
import static com.opfabric.OpFabric.$matches;
import static com.opfabric.OpFabric.$contains;
import static com.opfabric.OpFabric.$containedIn;
import static com.opfabric.OpFabric.$startOf;
import static com.opfabric.OpFabric.$startsWith;
import static com.opfabric.OpFabric.$endOf;
import static com.opfabric.OpFabric.$endsWith;
import static com.opfabric.OpFabric.$ne;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class ComparisonOperatorsTest {
	@Test
	public void testEq() {
		testEq(null, null, true);
		testEq(null, "", false);
		testEq("", null, false);
		testEq("", "", true);
		testEq("hello", "hello", true);
		testEq("hello", "bye", false);
		testEq(true, true, true);
		testEq(true, false, false);

		testEq(123, 123, true);
		testEq(123, 123L, true);
		testEq(123, 456, false);
	}

	@Test
	public void testNe() {
		testNe(null, null, false);
		testNe(null, "", true);
		testNe("", null, true);
		testNe("", "", false);
		testNe("hello", "hello", false);
		testNe("hello", "bye", true);
		testNe(true, true, false);
		testNe(true, false, true);

		testNe(123, 123, false);
		testNe(123, 123L, false);
		testNe(123, 456, true);
	}
	
	@Test
	public void testGe() {
		testGe(null, null, true);
		testGe(null, "", false);
		testGe("", null, true);
		testGe("", "", true);
		testGe("hello", "hello", true);
		testGe("hello", "bye", true);
		testGe("bye", "hello", false);
		testGe(true, true, true);
		testGe(true, false, true);

		testGe(123, 123, true);
		testGe(123, 123L, true);
		testGe(123, 456, false);
		testGe(456, 123, true);
	}

	@Test
	public void testGt() {
		testGt(null, null, false);
		testGt(null, "", false);
		testGt("", null, true);
		testGt("", "", false);
		testGt("hello", "hello", false);
		testGt("hello", "bye", true);
		testGt("bye", "hello", false);
		testGt(true, true, false);
		testGt(true, false, true);

		testGt(123, 123, false);
		testGt(123, 123L, false);
		testGt(123, 456, false);
		testGt(456, 123, true);
	}
	
	@Test
	public void testLe() {
		testLe(null, null, true);
		testLe(null, "", true);
		testLe("", null, false);
		testLe("", "", true);
		testLe("hello", "hello", true);
		testLe("hello", "bye", false);
		testLe("bye", "hello", true);
		testLe(true, true, true);
		testLe(true, false, false);

		testGe(123, 123, true);
		testLe(123, 123L, true);
		testLe(123, 456, true);
		testLe(456, 123, false);
	}

	@Test
	public void testLt() {
		testLt(null, null, false);
		testLt(null, "", true);
		testLt("", null, false);
		testLt("", "", false);
		testLt("hello", "hello", false);
		testLt("hello", "bye", false);
		testLt("bye", "hello", true);
		testLt(true, true, false);
		testLt(true, false, false);

		testLt(123, 123, false);
		testLt(123, 123L, false);
		testLt(123, 456, true);
		testLt(456, 123, false);
	}
	
	@Test
	public void testMatches() {
		testMatches("", Pattern.compile(""), true);
		testMatches("abc", Pattern.compile("abc"), true);
		testMatches("hello world", Pattern.compile("hello"), true);
		testMatches("hello world", Pattern.compile("world"), true);
		testMatches("bye", Pattern.compile("hello"), false);
		
		
		testMatchedBy("", "", true);
		testMatchedBy("abc", "abc", true);
		testMatchedBy("hello world", "hello", true);
		testMatchedBy("hello world", "world", true);
		testMatchedBy("bye", "hello", false);
	}
	

	@Test
	public void testContainedIn() {
		testContainedIn("", "", true);
		testContainedIn("abc", "abc", true);
		testContainedIn("hello", "hello world", true);
		testContainedIn("world", "hello world", true);
		testContainedIn("bye", "hello", false);
	}
	
	@Test
	public void testStart() {
		testStartOf("", "", true);
		testStartOf("abc", "abc", true);
		testStartOf("hello", "hello world", true);
		testStartOf("world", "hello world", false);
		testStartOf("bye", "hello", false);
	}
	
	@Test
	public void testEnd() {
		testEndOf("", "", true);
		testEndOf("abc", "abc", true);
		testEndOf("hello", "hello world", false);
		testEndOf("world", "hello world", true);
		testEndOf("bye", "hello", false);
	}
	
	private <T> void testEq(T value, T benchmark, boolean expected) {
		Assert.assertEquals(expected, $eq(value, benchmark)._());
	}


	private <T> void testNe(T value, T benchmark, boolean expected) {
		Assert.assertEquals(expected, $ne(value, benchmark)._());
	}


	private <T> void testGe(T value, T benchmark, boolean expected) {
		Assert.assertEquals(expected, $ge(value, benchmark)._());
	}


	private <T> void testGt(T value, T benchmark, boolean expected) {
		Assert.assertEquals(expected, $gt(value, benchmark)._());
	}


	private <T> void testLe(T value, T benchmark, boolean expected) {
		Assert.assertEquals(expected, $le(value, benchmark)._());
	}


	private <T> void testLt(T value, T benchmark, boolean expected) {
		Assert.assertEquals(expected, $lt(value, benchmark)._());
	}

	
	private void testMatches(String value, Pattern pattern, boolean expected) {
		Assert.assertEquals(expected, $matchedBy(value, pattern)._());
		Assert.assertEquals(expected, $matches(pattern, value)._());
	}
	

	private <T> void testMatchedBy(T value, T benchmark, boolean expected) {
		Assert.assertEquals(expected, $matches(benchmark, value)._());
		Assert.assertEquals(expected, $matchedBy(value, benchmark)._());
	}

	private <T> void testContainedIn(T value, T benchmark, boolean expected) {
		Assert.assertEquals(expected, $contains(benchmark, value)._());
		Assert.assertEquals(expected, $containedIn(value, benchmark)._());
	}
	

	private <T> void testStartOf(T value, T benchmark, boolean expected) {
		Assert.assertEquals(expected, $startOf(value, benchmark)._());
		Assert.assertEquals(expected, $startsWith(benchmark, value)._());
	}
	
	private <T> void testEndOf(T value, T benchmark, boolean expected) {
		Assert.assertEquals(expected, $endOf(value, benchmark)._());
		Assert.assertEquals(expected, $endsWith(benchmark, value)._());
	}
	
}
