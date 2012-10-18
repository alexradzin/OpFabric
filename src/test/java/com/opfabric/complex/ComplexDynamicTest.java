package com.opfabric.complex;

import static com.opfabric.OpFabric.$for;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.junit.Test;

import com.opfabric.operator.Op;
import com.opfabric.operator.Sequence;

@SuppressWarnings("serial")
public class ComplexDynamicTest implements Serializable {
	
	@Test
	public void testSequencialLoops00() {
		sequencialLoops(0,  0);
	}

	@Test
	public void testSequencialLoops11() {
		sequencialLoops(1,  1);
	}

	@Test
	public void testSequencialLoops35() {
		sequencialLoops(3,  5);
	}
	
	private void sequencialLoops(int n, int loops) {
		int expectedSum = n * loops;
		final AtomicInteger sum = new AtomicInteger(0);
		
		Sequence sequence = new Sequence();
		for (int l = 0;  l < loops;  l++) {
			sequence.addOp($for("i", 0, n, 1, new Op() {
				@Override
				public void _() {
					sum.set(sum.get() + 1);
				}
			}));
		}
		
		sequence._();
		
		Assert.assertEquals(expectedSum, sum.get());
	}
	

	
	@Test
	public void testNestedLoops00() {
		nestedLoops(0, 0,  1);
	}

	@Test
	public void testNestedLoops11() {
		nestedLoops(0, 1,  1);
	}

	@Test
	public void testNestedLoops35() {
		nestedLoops(0, 3,  2);
	}
	
	
	private void nestedLoops(int from, int n, int loops) {
		AtomicInteger sum = new AtomicInteger(0);
		int expectedSum = (int)Math.pow(n, loops);
		System.getProperties().put("test-counter", 0);
		
		
		Op op = createNestedLoops(from, n, loops, sum);
		op._();
		Assert.assertEquals(expectedSum, sum.get());
	}
	

	private Op createNestedLoops(int from, int n, int loops, final AtomicInteger sum) {
		Op increment = new Op() {
			@Override
			public void _() {
				sum.set(sum.get() + 1);
			}
		};		
		
		return createNestedLoops(from, n, loops, increment);
	}
	
	
	private Op createNestedLoops(int from, int n, int loops, Op increment) {
		
		Op op = increment;
		for (int l = 0;  l < loops;  l++) {
			char counter = (char)((int)'a' + l);
			op = $for("" + counter, from, n, 1, op);
		}
		
		return op;
	}
}
