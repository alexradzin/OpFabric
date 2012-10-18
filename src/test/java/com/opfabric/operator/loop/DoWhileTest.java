package com.opfabric.operator.loop;

import static com.opfabric.OpFabric.$do;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.opfabric.operator.BooleanOp;
import com.opfabric.operator.Op;

@RunWith(Parameterized.class)
public final class DoWhileTest {
	private int n;
	
	public DoWhileTest(int n) {
		this.n = n;
	}
	
	
	@Parameters
	public static Collection<Object[]> interationCount() {
		return Arrays.asList(new Object[][] {{0}, {1}, {2}, {5}});
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testWhile() {
		final AtomicInteger conditionCounter = new AtomicInteger(0);		
		final AtomicInteger bodyVisitCounter = new AtomicInteger(0);

		$do(
			new Op() {
				public void _() {
					bodyVisitCounter.set(bodyVisitCounter.get() + 1); 
				}
			}).$while(new BooleanOp() {
			public boolean _() {
				boolean res = conditionCounter.get() < n;
				conditionCounter.set(conditionCounter.get() + 1); 
				return res;
			}
			
		})._();
		
		Assert.assertEquals(n + 1, bodyVisitCounter.get());
	}
}
