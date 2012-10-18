package com.opfabric.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class Sequence implements Op {
	private static final long serialVersionUID = 1875428941968973397L;
	private List<Op> ops = new ArrayList<Op>();

	public Sequence(Op ... ops) {
		if (ops != null) {
			this.ops.addAll(Arrays.asList(ops));
		}
	}

	@Override
	public void _() {
		for (Op op : ops) {
			op._();
		}
	}
	
	public Collection<Op> getOperations() {
		return ops;
	}
	
	public void addOp(Op op) {
		ops.add(op);
	}
}
