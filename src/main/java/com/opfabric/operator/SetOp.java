package com.opfabric.operator;

import com.opfabric.OpFabric;

public class SetOp<V> implements Op {
	private static final long serialVersionUID = 4850011412571862359L;
	
	private String name;
	private V value;
	
	public SetOp(String name, V value) {
		this.name = name;
		this.value = value;
	}

	public void _() {
		OpFabric.set(name, value);
	}

}
