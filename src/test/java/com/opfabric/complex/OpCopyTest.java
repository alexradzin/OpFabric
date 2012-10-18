package com.opfabric.complex;

import junit.framework.Assert;

import org.junit.Test;

import com.opfabric.OpFabric;
import com.opfabric.operator.NoOp;
import com.opfabric.operator.Op;
import com.opfabric.operator.Sequence;
import com.opfabric.operator.SetOp;

public abstract class OpCopyTest {
	@Test
	public void sequence() throws Exception {
		testScenario(new Sequence(new SetOp<Integer>("a", 1)), "a", 1, 0);
	}

	@Test
	public void sequence2() throws Exception {
		testScenario(new Sequence(new SetOp<Integer>("a", 1), new SetOp<Integer>("a", 2)), "a", 2, 0);
	}
	
	
	@Test
	public void testFor() throws Exception {
		Op op = OpFabric.$for("i", 0, 5, 1, new NoOp());
		testScenario(op, "i", 5, -1);
	}
	
	
	@Test
	public void testIf() throws Exception {
		Op op = OpFabric.$if(OpFabric.$contains("hello", "h")).$then(OpFabric.$set("yes", true)).$else(OpFabric.$set("yes", false));
		testScenario(op, "yes", true, false);
	}
	
	
	protected <V> void testScenario(Op originalOp, String varName, V expectedVarValue, V initialVarValue) throws Exception {
		OpFabric.set(varName, initialVarValue);
		originalOp._();
		Assert.assertEquals(expectedVarValue, OpFabric.get(varName, Object.class));
		

		OpFabric.set(varName, initialVarValue);
		Op opCopy = clone(originalOp);
		Assert.assertNotNull(opCopy);
		Assert.assertEquals(originalOp.getClass(), opCopy.getClass());
		originalOp._();
		Assert.assertEquals(expectedVarValue, OpFabric.get(varName, Object.class));
	}
	
	protected abstract Op clone(Op op) throws Exception;

}
