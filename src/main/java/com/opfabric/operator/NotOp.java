package com.opfabric.operator;

public class NotOp implements BooleanOp {
	private static final long serialVersionUID = 5537254611236442618L;
	
	private BooleanOp op;

	public NotOp() {
		
	}
	
	public NotOp(BooleanOp op) {
		this.op = op;
	}

	public boolean _() {
		return !op._();
 	}

}
