package com.opfabric.operator.ifelse;

import com.opfabric.operator.Op;

public interface Then extends Op {
	public Else $then(Op thenOp);
}
