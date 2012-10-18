package com.opfabric.operator.loop;

import static com.opfabric.OpFabric.$for;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.opfabric.OpFabric;
import com.opfabric.operator.Op;

@RunWith(Parameterized.class)
public final class ForCounterTest {
	private int from;
	private int to;
	private int step;
	private boolean useBody;
	private int expectedCountOfLoopBodyInvocations;
	private int expectedIndex;
	
	public ForCounterTest(
			int from, int to, int step, boolean useBody, 
			int expectedCountOfLoopBodyInvocations, int expectedIndex) {
		this.from = from;
		this.to = to;
		this.step = step;
		this.useBody = useBody;
		
		this.expectedCountOfLoopBodyInvocations = expectedCountOfLoopBodyInvocations;
		this.expectedIndex = expectedIndex;
	}
	
	
	@Parameters
	public static Collection<Object[]> interationCount() {
		return Arrays.asList(new Object[][] {
				{0, 0, 1, true, 0, 0}, 
				{0, 1, 1, true, 1, 1}, 
				{0, 10, 1, true, 10, 10}, 
				{0, 5, 2, true, 3, 6}, 
				{3, 5, 2, true, 1, 5}, 
				{6, 2, -2, true, 2, 2},
				{6, 2, -2, false, 0, 2} // body is null, so nothing to invoke and number of invocations is 0
				}
		);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testFor() {
		final AtomicInteger bodyVisitCounter = new AtomicInteger(0);
		
		Op body = useBody ? 
			new Op() {
				public void _() {
					bodyVisitCounter.set(bodyVisitCounter.get() + 1); 
				}
			} 
			:
			null;
		
		
		$for("i", from, to, step, body)._();
		
		Assert.assertEquals(expectedCountOfLoopBodyInvocations, bodyVisitCounter.get());
		Assert.assertEquals(expectedIndex, OpFabric.get("i", Integer.class).intValue());
	}
}
