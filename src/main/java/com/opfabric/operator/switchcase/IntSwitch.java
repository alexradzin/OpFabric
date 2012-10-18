package com.opfabric.operator.switchcase;

import java.util.LinkedHashMap;
import java.util.Map;

import com.opfabric.operator.IntOp;
import com.opfabric.operator.Op;

public class IntSwitch implements Op {
	private static final long serialVersionUID = -3619456354898515878L;
	
	private IntOp sw;
	private Map<Integer, Op> operations = new LinkedHashMap<Integer, Op>();
	
	public IntSwitch() {
		
	}
	
	public IntSwitch(IntOp sw) {
		this.sw = sw;
	}

	public IntSwitch $case(int value, Op op) {
		operations.put(value, op);
		return this;
	}
	
	public Op $default(Op op) {
		operations.put(null, op);
		return this;
	}
	
	public void _() {
		for (Integer s : new Integer[] {sw._(), null}) {
			Op op = operations.get(s);
			if (op != null) {
				op._();
				return;
			}
		}
	}
}
