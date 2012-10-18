package com.opfabric.operator;

import junit.framework.Assert;

import org.junit.Test;

import com.opfabric.OpFabric;

public class CallTest {
	@Test
	public void testNewNoArgs() {
		testNew(Nothing.class, null, new Nothing());
	}

	@Test
	public void testNewOneArg() {
		testNew(Integer.class, new Object[] {123}, new Integer(123));
	}
	
	
	private <T> void testNew(Class<T> clazz, Object[] args, T expected) {
		T actual = OpFabric.$new(clazz, args)._();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCallStaticNoArgs() {
		testCall(System.class, "myobj", "value does not matter for static method", "lineSeparator", null, System.lineSeparator());
	}

	@Test
	public void testCallStaticOneArg() {
		testCall(System.class, "myobj", "value does not matter for static method", "identityHashCode", new Object[] {this}, System.identityHashCode(this));
	}

	@Test
	public void testCallNoArgs() {
		String str = "some text";
		testCall(System.class, "myobj", str, "hashCode", null, str.hashCode());
	}

	@Test
	public void testCallOneArg() {
		String str = "some text";
		testCall(String.class, "myobj", str, "substring", new Object[] {4}, str.substring(4));
	}

	@Test
	public void testCallTwoArgs() {
		String str = "some text";
		testCall(String.class, "myobj", str, "substring", new Object[] {1, 4}, str.substring(1, 4));
	}
	
	
	private <T, V> void testCall(Class<T> clazz, String objectName, V value, String methodName, Object[] args, V expected) {
		OpFabric.set(objectName, value);
		@SuppressWarnings("unchecked")
		ResultOp<V> actualOp = OpFabric.$call(clazz, objectName, methodName, (Class<V>)value.getClass(), args);
		V actual = actualOp._();
		Assert.assertEquals(expected, actual);
	}
	
	
	
	public static class Nothing {
		public int hashCode() {
			return 0;
		}
		
		public boolean equals(Object obj) {
			return obj != null && Nothing.class.equals(obj.getClass());
		}
	}
}
