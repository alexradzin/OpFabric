package com.opfabric.operator.loop;

import com.opfabric.operator.BooleanOp;
import com.opfabric.operator.NoOp;
import com.opfabric.operator.Op;
import com.opfabric.operator.Sequence;

public class For implements Op {
	private static final long serialVersionUID = 5895149795525167175L;
	
	private Op before = NoOp.noOp();
	private BooleanOp condition;
	private Op after = NoOp.noOp();
	
	private Op body = NoOp.noOp();
	
	public For() {
		
	}
	
	public For(BooleanOp condition, Op body) {
		this.condition = condition;
		this.body = body;
	}

	public For(Op before, BooleanOp condition, Op after, Op body) {
		this(new Sequence(before), condition, new Sequence(after), body);
	}

	public For(Sequence before, BooleanOp condition, Sequence after, Op body) {
		this.before = before;
		this.condition = condition;
		this.after = after;
		this.body = body;
	}
	
	
	public void _() {
		for (void_(before); boolean_(condition); void_(after)) {
			if (body != null) {
				body._();
			}
		}
	}
	
	private void void_(Op op) {
		if (op != null) {
			op._();
		}
	}

	private boolean boolean_(BooleanOp op) {
		if (op != null) {
			return op._();
		}
		return true;
	}
}
