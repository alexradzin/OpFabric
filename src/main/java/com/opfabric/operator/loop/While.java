package com.opfabric.operator.loop;

import com.opfabric.operator.BooleanOp;
import com.opfabric.operator.Op;

public class While implements Op {
	private static final long serialVersionUID = 1L;

	private BooleanOp condition;
	
	private Op body;
	
	public While() {
		
	}
	
	public While(BooleanOp condition, Op body) {
		this.condition = condition;
		this.body = body;
	}

	public void _() {
		while(condition._()) {
			body._();
		}
	}

}
