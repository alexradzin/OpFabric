package com.opfabric.operator.ifelse;

import com.opfabric.operator.BooleanOp;
import com.opfabric.operator.Op;

public class If implements Op, Then, Else {
	private static final long serialVersionUID = 3871621678734749510L;

	private BooleanOp condition;

	private Op thenOp;
	private Op elseOp;
	
	public If(BooleanOp condition) {
		this.condition = condition;
	}
	
	
	public Else $then(Op thenOp) {
		this.thenOp = thenOp;
		return this;
	}
	
	public Else $else(Op elseOp) {
		this.elseOp = elseOp;
		return this;
	}

//	public NestedIf $then() {
//		thenOp = OpFabric.$if(condition);
//		return this;
//	}
//	
//	public NestedIf $else() {
//		elseOp = OpFabric.$if(condition);
//		return this;
//	}
	
	
//	public Then $if(BooleanOp condition) {
//		return OpFabric.$if(condition);
//	}
	
	
	public void _() {
		if (condition._()) {
			if(thenOp != null) {
				thenOp._();
			}
		} else if (elseOp != null) {
			elseOp._();
		}

	}
}
