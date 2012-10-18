package com.opfabric.operator;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

public class ArgumentsMatcherTest {
	@Test
	public void testObjectToString() throws Exception {
		test(Object.class, new MethodArgumentsMatcher<Object>(Object.class), "toString", null, Object.class.getMethod("toString"));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testArrayListToString() throws Exception {
		test(ArrayList.class, new MethodArgumentsMatcher<ArrayList>(ArrayList.class), "toString", null, ArrayList.class.getMethod("toString"));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testAbstractListToString() throws Exception {
		test(AbstractList.class, new MethodArgumentsMatcher<AbstractList>(AbstractList.class), "toString", null, AbstractList.class.getMethod("toString"));
	}
	
	@Test
	public void testMathSin() throws Exception {
		Method sin = test(Math.class, new MethodArgumentsMatcher<Math>(Math.class), "sin", new Object[] {0.0}, Math.class.getMethod("sin", double.class));
		Assert.assertEquals(0.0, (Double)sin.invoke(null, 0.0), 0.001);
	}

	@Test
	public void testMathSinIntArg() throws Exception {
		Method sin = test(Math.class, new MethodArgumentsMatcher<Math>(Math.class), "sin", new Object[] {0}, Math.class.getMethod("sin", double.class));
		Assert.assertEquals(0.0, (Double)sin.invoke(null, 0.0), 0.001);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testCreateArrayList() throws Exception {
		test(ArrayList.class, new ConstructorArgumentsMatcher<ArrayList>(ArrayList.class), "ArrayList", null, ArrayList.class.getConstructor());
	}
	
	
	private <C, F extends Member> F test(Class<?> clazz, ArgumentsMatcher<C, F> matcher, String name, Object[] args, F expected) {
		F actual = matcher.findBestMatch(name, args);
		Assert.assertEquals(expected, actual);
		return actual;
	}

}
