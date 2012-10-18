package com.opfabric.operator;



public class ValueOp<V> implements ResultOp<V> {
	private static final long serialVersionUID = 5848592545290735557L;
	private V value;

	public ValueOp(V value) {
		this.value = value;
	}
	
	
	@Override
	public V _() {
		return value;
	}

}

