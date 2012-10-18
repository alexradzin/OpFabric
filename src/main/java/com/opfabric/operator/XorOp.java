package com.opfabric.operator;

public class XorOp implements BooleanOp {
	private static final long serialVersionUID = 5537254611236442618L;
	
	private BooleanOp[] ops;

	public XorOp() {
	}
	
	public XorOp(BooleanOp ... ops) {
		this.ops = ops == null ? new BooleanOp[0] : ops;
	}

	public boolean _() {
		boolean res = false;
		for(BooleanOp op : ops) {
			res ^= op._();
		}
		return res;
 	}

}
