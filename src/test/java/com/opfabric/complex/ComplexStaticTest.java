package com.opfabric.complex;

import java.util.concurrent.atomic.AtomicBoolean;

import junit.framework.Assert;

import org.junit.Test;

import com.opfabric.OpFabric;
import com.opfabric.operator.Op;
import com.opfabric.operator.OpWithArg;

import static com.opfabric.OpFabric.$try;
import static com.opfabric.OpFabric.$for;



@SuppressWarnings("serial")
public class ComplexStaticTest {
	@Test
	public void testTryCatchWithHardCodedLoop() {
		final AtomicBoolean expectedExceptionThrown = new AtomicBoolean(false);
		
		
		$try(new Op() {
			@Override
			public void _() {
				int[] arr = new int[2];
				// This loop will throw ArrayIndexOutOfBoundsException
				for (int i = 0;  i <= arr.length;  i++) {
					// do nothing
					arr[i] = arr[i];
				}
			}
		}).$catch(ArrayIndexOutOfBoundsException.class, new OpWithArg<ArrayIndexOutOfBoundsException>() {
			@Override
			public void _(ArrayIndexOutOfBoundsException arg) {
				expectedExceptionThrown.set(true);
			}
		})._();
		
		Assert.assertTrue("Expected exception was not thrown", expectedExceptionThrown.get());
	}

	
	@Test
	public void testTryCatchWithProgrammaticLoop() {
		final AtomicBoolean expectedExceptionThrown = new AtomicBoolean(false);
		
		
		$try(new Op() {
			@Override
			public void _() {
				final int[] arr = new int[2];
				$for("i", 0, arr.length + 1, 1, new Op() {
					@Override
					public void _() {
						int i = OpFabric.get("i", int.class);
						arr[i] = arr[i];
					}
				})._();
			}
		}).$catch(ArrayIndexOutOfBoundsException.class, new OpWithArg<ArrayIndexOutOfBoundsException>() {
			@Override
			public void _(ArrayIndexOutOfBoundsException arg) {
				expectedExceptionThrown.set(true);
			}
		})._();
		
		Assert.assertTrue("Expected exception was not thrown", expectedExceptionThrown.get());
	}
}
