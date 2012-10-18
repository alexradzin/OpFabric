package com.opfabric.operator.switchcase;

import static com.opfabric.OpFabric.$switch;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

import com.opfabric.operator.Op;

@SuppressWarnings("serial")
public class SwitchTest {
	@Test
	public void testEmptyIntSwitch() {
		$switch(1)._();
	}

	@Test
	public void testEmptyStringSwitch() {
		$switch("")._();
		$switch((String)null)._();
		$switch("aaaaa")._();
	}
	
	
	@Test
	public void testIntSwitchDefaultOnly() {
		final AtomicBoolean defaultVisited = new AtomicBoolean(false);
		
		$switch(1).$default(new Op() {
			public void _() {
				defaultVisited.set(true);
			}
		})._();
		
		Assert.assertTrue(defaultVisited.get());
	}

	@Test
	public void testStringSwitchDefaultOnly() {
		final AtomicBoolean defaultVisited = new AtomicBoolean(false);
		
		$switch("hello").$default(new Op() {
			public void _() {
				defaultVisited.set(true);
			}
		})._();
		
		Assert.assertTrue(defaultVisited.get());
	}

	
	@Test
	public void testIntSwitchDefault() {
		testIntSwitch(-1, new boolean[] {false, false, false}, true);
		testIntSwitch(5, new boolean[] {false, false, false}, true);
		testIntSwitch(333, new boolean[] {false, false, false}, true);
	}
	
	@Test
	public void testIntSwitchCase() {
		testIntSwitch(0, new boolean[] {true, false, false}, false);
		testIntSwitch(1, new boolean[] {false, true, false, false}, false);
		testIntSwitch(2, new boolean[] {false, false, true, false}, false);
		testIntSwitch(123, new boolean[] {false, false, false, true}, false);
	}
	
	@Test
	public void testStringSwitchCase() {
		testStringSwitch("hello", new boolean[] {true, false, false, false}, false);
		testStringSwitch("", new boolean[] {false, false, false, false}, true);
		testStringSwitch("bye", new boolean[] {false, true, false, false}, false);
		testStringSwitch("hello, world", new boolean[] {false, false, true, false}, false);
		testStringSwitch("no match", new boolean[] {false, false, false, false}, true);
		testStringSwitch(null, new boolean[] {false, false, false, true}, false);
	}
	
	private void testIntSwitch(int sw, boolean[] expectedCase, boolean expectedDefault) {
		final AtomicBoolean[] caseVisited = new AtomicBoolean[expectedCase.length];
		final AtomicBoolean defaultVisited = new AtomicBoolean(false);

		for (int i = 0; i < expectedCase.length;  i++) {
			caseVisited[i] = new AtomicBoolean(false);
		}
		
		
		
		$switch(sw).
		$case(0, new Op() {
			public void _() {
				caseVisited[0].set(true);
			}
		}).
		$case(1, new Op() {
			public void _() {
				caseVisited[1].set(true);
			}
		}).
		$case(2, new Op() {
			public void _() {
				caseVisited[2].set(true);
			}
		}).
		$case(123, new Op() {
			public void _() {
				caseVisited[3].set(true);
			}
		}).
		$default(new Op() {
			public void _() {
				defaultVisited.set(true);
			}
		})._();
		
		for (int i = 0; i < expectedCase.length;  i++) {
			Assert.assertEquals(expectedCase[i], caseVisited[i].get());
		}
		
		Assert.assertEquals(expectedDefault, defaultVisited.get());
	}

	private void testStringSwitch(String sw, boolean[] expectedCase, boolean expectedDefault) {
		final AtomicBoolean[] caseVisited = new AtomicBoolean[expectedCase.length];
		final AtomicBoolean defaultVisited = new AtomicBoolean(false);

		for (int i = 0; i < expectedCase.length;  i++) {
			caseVisited[i] = new AtomicBoolean(false);
		}
		
		
		
		$switch(sw).
		$case("hello", new Op() {
			public void _() {
				caseVisited[0].set(true);
			}
		}).
		$case("bye", new Op() {
			public void _() {
				caseVisited[1].set(true);
			}
		}).
		$case(Pattern.compile("hello"), new Op() {
			public void _() {
				caseVisited[2].set(true);
			}
		}).
		$case((String)null, new Op() {
			public void _() {
				caseVisited[3].set(true);
			}
		}).
		$default(new Op() {
			public void _() {
				defaultVisited.set(true);
			}
		})._();
		
		for (int i = 0; i < expectedCase.length;  i++) {
			Assert.assertEquals("#" + i, expectedCase[i], caseVisited[i].get());
		}
		
		Assert.assertEquals(expectedDefault, defaultVisited.get());
	}
	
}
