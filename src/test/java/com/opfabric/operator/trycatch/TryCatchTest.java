package com.opfabric.operator.trycatch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.opfabric.operator.Op;
import com.opfabric.operator.OpWithArg;
import com.opfabric.operator.ThrowingOp;

import static com.opfabric.OpFabric.$try;

@SuppressWarnings("serial")
public class TryCatchTest {
	@Test
	public void testTryCatchFinally() {
		final Map<String, Boolean> visitedPoints = new HashMap<String, Boolean>();
		
		final String exceptionMessage = "this is exception message";
		
		$try(new Op() {public void _() {
			visitedPoints.put("try", true);
			throw new IllegalStateException(exceptionMessage);
		}}).$catch(IllegalStateException.class, new OpWithArg<IllegalStateException>() {
				public void _(IllegalStateException e) {
					visitedPoints.put("catchIllegalStateException", true);
					Assert.assertEquals(exceptionMessage, e.getMessage());
				}
			}).
			$catch(IllegalArgumentException.class, new OpWithArg<IllegalArgumentException>() {
				public void _(IllegalArgumentException arg) {
					Assert.fail("IllegalArgumentException was not thrown here");
				}
			}).
			$finally(new Op() {
				public void _() {
					visitedPoints.put("finally", true);
				}
			}).
		_();
		
		Assert.assertTrue(visitedPoints.get("try"));
		Assert.assertTrue(visitedPoints.get("catchIllegalStateException"));
		Assert.assertTrue(visitedPoints.get("finally"));
	}

	
	@Test
	public void testTryCatchRuntimeExceptionThrowIllegalArgumentException() {
		final Map<String, Boolean> visitedPoints = new HashMap<String, Boolean>();
		
		final String exceptionMessage = "this is exception message";
		
		$try(new Op() {public void _() {
			visitedPoints.put("try", true);
			throw new IllegalArgumentException(exceptionMessage);
		}}).$catch(RuntimeException.class, new OpWithArg<RuntimeException>() {
				public void _(RuntimeException e) {
					visitedPoints.put("catchRuntimeException", true);
					Assert.assertEquals(exceptionMessage, e.getMessage());
				}
			}).
		_();
		
		Assert.assertTrue(visitedPoints.get("try"));
		Assert.assertTrue(visitedPoints.get("catchRuntimeException"));
	}

	@Test
	public void testTryCatch2ExceptionsThrowOneOfThem() {
		testTryCatch2Exceptions(new IllegalArgumentException("arg"));
		testTryCatch2Exceptions(new IllegalStateException("state"));
	}
	
	@SuppressWarnings("unchecked")
	private void testTryCatch2Exceptions(final RuntimeException exception) {
		final Map<String, Boolean> visitedPoints = new HashMap<String, Boolean>();
		
		final String exceptionMessage = exception.getMessage();
		
		$try(new Op() {public void _() {
			visitedPoints.put("try", true);
			throw exception;
		}}).$catch(new Class[] {IllegalArgumentException.class, IllegalStateException.class}, new OpWithArg<RuntimeException>() {
				public void _(RuntimeException e) {
					visitedPoints.put("catchRuntimeException", true);
					Assert.assertEquals(exceptionMessage, e.getMessage());
					Assert.assertEquals(exception.getClass(), e.getClass());
				}
			}).
		_();
		
		Assert.assertTrue(visitedPoints.get("try"));
		Assert.assertTrue(visitedPoints.get("catchRuntimeException"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTryThrowOneExceptionCatchOther() {
		final Map<String, Boolean> visitedPoints = new HashMap<String, Boolean>();
		
		final String exceptionMessage = "my message";
		
		$try(new Op() {public void _() {
			visitedPoints.put("try", true);
			throw new IllegalArgumentException(exceptionMessage);
		}}).$catch(IllegalStateException.class, new OpWithArg<IllegalStateException>() {
				public void _(IllegalStateException e) {
					Assert.fail("wrong exception");
				}
			}).
		_();
		
	}

	
	@Test
	public void testTryThrowNotRuntimeExceptionAndCatchIt() {
		final Map<String, Boolean> visitedPoints = new HashMap<String, Boolean>();
		
		final String exceptionMessage = "my message";
		
		$try(new ThrowingOp<IOException>() {public void _() throws IOException {
			visitedPoints.put("try", true);
			throw new IOException(exceptionMessage);
		}}).$catch(IOException.class, new OpWithArg<IOException>() {
				public void _(IOException e) {
					visitedPoints.put("catchRuntimeException", true);
					Assert.assertEquals(exceptionMessage, e.getMessage());
					Assert.assertEquals(IOException.class, e.getClass());
				}
			}).
		_();
	}
	
	
}
