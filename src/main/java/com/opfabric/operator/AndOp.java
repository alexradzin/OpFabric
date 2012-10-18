package com.opfabric.operator;


public class AndOp implements BooleanOp {
	private static final long serialVersionUID = 5537254611236442618L;

	private BooleanOp[] ops;

	public AndOp(BooleanOp ... ops) {
		this.ops = ops == null ? new BooleanOp[0] : ops;
	}

	public boolean _() {
		for(BooleanOp op : ops) {
			if (!op._()) {
				return false;
			}
		}
		return ops.length > 0;
 	}

}
