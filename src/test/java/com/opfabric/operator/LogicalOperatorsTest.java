package com.opfabric.operator;

import static com.opfabric.OpFabric.$and;
import static com.opfabric.OpFabric.$not;
import static com.opfabric.OpFabric.$or;
import static com.opfabric.OpFabric.$xor;
import junit.framework.Assert;

import org.junit.Test;

import com.opfabric.operator.BooleanOp;


public class LogicalOperatorsTest {
	@SuppressWarnings("serial")
	private final static BooleanOp trueOp = new BooleanOp() {
		public boolean _() {
			return true;
		}
	};

	@SuppressWarnings("serial")
	private final static BooleanOp falseOp = new BooleanOp() {
		public boolean _() {
			return false;
		}
	};
	
	@Test
	public void testAndEmpty() {
		testAnd(false);
	}

	@Test
	public void testAndOneTrue() {
		testAnd(true, trueOp);
	}

	@Test
	public void testAndOneFalse() {
		testAnd(false, falseOp);
	}
	
	@Test
	public void testAndSeveralTrue() {
		testAnd(true, trueOp, trueOp, trueOp);
	}

	@Test
	public void testAndSeveralFalse() {
		testAnd(false, falseOp, falseOp, falseOp);
	}

	@Test
	public void testAndSeveralTrueAndFalse() {
		testAnd(false, trueOp, falseOp, falseOp);
		testAnd(false, falseOp, trueOp, falseOp);
		testAnd(false, falseOp, falseOp, trueOp);
		testAnd(false, falseOp, trueOp, trueOp);
	}
	
	// or
	@Test
	public void testOrEmpty() {
		testOr(false);
	}

	@Test
	public void testNull() {
		testOr(false, (BooleanOp[])null);
		testXor(false, (BooleanOp[])null);
		testAnd(false, (BooleanOp[])null);
	}

	@Test
	public void testOrOneTrue() {
		testOr(true, trueOp);
	}

	@Test
	public void testOrOneFalse() {
		testOr(false, falseOp);
	}
	
	@Test
	public void testOrSeveralTrue() {
		testOr(true, trueOp, trueOp, trueOp);
	}

	@Test
	public void testOrSeveralFalse() {
		testOr(false, falseOp, falseOp, falseOp);
	}

	@Test
	public void testOrSeveralTrueAndFalse() {
		testOr(true, trueOp, falseOp, falseOp);
		testOr(true, falseOp, trueOp, falseOp);
		testOr(true, falseOp, falseOp, trueOp);
		testOr(true, falseOp, trueOp, trueOp);
	}
	
	// xor
	@Test
	public void testXorEmpty() {
		testXor(false);
	}

	@Test
	public void testXorOneTrue() {
		testXor(true, trueOp);
	}

	@Test
	public void testXorOneFalse() {
		testXor(false, falseOp);
	}
	
	@Test
	public void testXorSeveralTrue() {
		testXor(true, trueOp, trueOp, trueOp);
	}

	@Test
	public void testXorSeveralFalse() {
		testXor(false, falseOp, falseOp, falseOp);
	}

	@Test
	public void testXorSeveralTrueAndFalse() {
		testXor(true, trueOp, falseOp, falseOp);
		testXor(true, falseOp, trueOp, falseOp);
		testXor(true, falseOp, falseOp, trueOp);
		testXor(false, falseOp, trueOp, trueOp);
	}
	
	@Test
	public void testNot() {
		testNot(true, falseOp);
		testNot(false, trueOp);
	}
	
	/**
	 * Nothing should happen. No-op is no-op. Just to be sure that the "no-operation" exists, can be constructed and called. 
	 */
	@Test
	public void testNoOp() {
		new NoOp()._();
	}
	
	private void testAnd(boolean expected, BooleanOp ... ops) {
		Assert.assertEquals(expected, $and(ops)._());
	}

	private void testOr(boolean expected, BooleanOp ... ops) {
		Assert.assertEquals(expected, $or(ops)._());
	}

	private void testXor(boolean expected, BooleanOp ... ops) {
		Assert.assertEquals(expected, $xor(ops)._());
	}

	private void testNot(boolean expected, BooleanOp op) {
		Assert.assertEquals(expected, $not(op)._());
	}
}
