package com.opfabric.operator.ifelse;

import static com.opfabric.OpFabric.$if;

import java.util.concurrent.atomic.AtomicBoolean;

import junit.framework.Assert;

import org.junit.Test;

import com.opfabric.operator.BooleanOp;
import com.opfabric.operator.Op;


@SuppressWarnings("serial")
public class IfElseTest {
	@Test
	public void testIfWithoutActions() {
		testIfWithoutActions(true);
		testIfWithoutActions(false);
	}
	
	private void testIfWithoutActions(final boolean condition) {
		$if(new BooleanOp() {
			public boolean _() {
				return condition;
			}
		});
	}

	@Test
	public void testIf() {
		testIf(true);
		testIf(false);
	}
	
	private void testIf(final boolean condition) {
		final AtomicBoolean thenVisited = new AtomicBoolean(false);
		$if(new BooleanOp() {
			public boolean _() {
				return condition;
			}
		}).$then(new Op() {
			public void _() {
				thenVisited.set(true);
			}
		})._();
		
		Assert.assertEquals(condition, thenVisited.get());
	}
	

	@Test
	public void testIfElse() {
		testIfElse(true);
		testIfElse(false);
	}
	
	
	private void testIfElse(final boolean condition) {
		final AtomicBoolean thenVisited = new AtomicBoolean(false);
		final AtomicBoolean elseVisited = new AtomicBoolean(false);
		
		$if(new BooleanOp() {
			public boolean _() {
				return condition;
			}
		}).$then(new Op() {
			public void _() {
				thenVisited.set(true);
			}
		}).$else(new Op() {
			public void _() {
				elseVisited.set(true);
			}
		})._();

		
		if (condition) {
			Assert.assertTrue("Then is not visited", thenVisited.get());
			Assert.assertFalse("Else is visited", elseVisited.get());
		} else {
			Assert.assertFalse("Then is visited", thenVisited.get());
			Assert.assertTrue("Else is not visited", elseVisited.get());
		}
	}

	
	@Test
	public void testIfElseIf() {
		testIfElseIf(true, true);
		testIfElseIf(true, false);
		testIfElseIf(false, true);
		testIfElseIf(false, false);
	}
	
	private void testIfElseIf(final boolean condition1, final boolean condition2) {
		final AtomicBoolean then1Visited = new AtomicBoolean(false);

		final AtomicBoolean then2Visited = new AtomicBoolean(false);
		
		
		$if(new BooleanOp() {
			public boolean _() {
				return condition1;
			}
		}).$then(new Op() {
			public void _() {
				then1Visited.set(true);
			}
		}).$else($if(new BooleanOp() {
			public boolean _() {
				return condition2;
			}
		}
		).$then(new Op() {
			public void _() {
				then2Visited.set(true);
			}
		})
		)._();

		
		if (condition1) {
			Assert.assertTrue("Then is not visited", then1Visited.get());
		} else {
			Assert.assertFalse("Then is visited", then1Visited.get());
		}
		
		
		if (!condition1 && condition2) {
			Assert.assertTrue("Then 2 is not visited", then2Visited.get());
		} else {
			Assert.assertFalse("Then 2 is visited", then2Visited.get());
		}
		
	}
	
}
