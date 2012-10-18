package com.opfabric.operator.loop;

import com.opfabric.operator.BooleanOp;
import com.opfabric.operator.Op;

public class Do implements Op {
	private static final long serialVersionUID = 1L;

	private BooleanOp condition;
	
	private Op body;
	
	public Do() {
		
	}
	
	public Do(Op body) {
		this.body = body;
	}

	public Op $while(BooleanOp condition) {
		this.condition = condition;
		return this;
	}
	
	public void _() {
		do {
			body._();
		}
		while(condition == null ? false : condition._());
	}
}
