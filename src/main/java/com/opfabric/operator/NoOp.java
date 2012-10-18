package com.opfabric.operator;


public class NoOp implements Op {
	private static final long serialVersionUID = -5852316261729272337L;
	
	private final static NoOp noOp = new NoOp();

	public void _() {
		// do nothing. This is why this class is called NoOp.
	}
	
	public static NoOp noOp() {
		return noOp;
	}

}
