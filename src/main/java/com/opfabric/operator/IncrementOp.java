package com.opfabric.operator;

import com.opfabric.OpFabric;

public class IncrementOp<V extends Number> implements Op {
	private static final long serialVersionUID = 7001479234062816863L;

	private String varName;
	private V incr;

	public IncrementOp() {
	}
	
	public IncrementOp(String varName, V incr) {
		this.varName = varName;
		this.incr = incr;
	}
	
	public void _() {
		Object obj = OpFabric.get(varName, Object.class);
		
		if (obj instanceof Integer) {
			obj = ((Integer)obj).intValue() + incr.intValue();
		} else if (obj instanceof Short) {
			obj = ((Short)obj).intValue() + incr.shortValue();
		} else if (obj instanceof Byte) {
			obj = ((Byte)obj).intValue() + incr.byteValue();
		} else if (obj instanceof Long) {
			obj = ((Long)obj).intValue() + incr.longValue();
		} else if (obj instanceof Float) {
			obj = ((Float)obj).intValue() + incr.floatValue();
		} else if (obj instanceof Double) {
			obj = ((Double)obj).intValue() + incr.doubleValue();
		} else {
			throw new UnsupportedOperationException("Type " + obj.getClass() + " cannot be incremented");
		}
		
		OpFabric.set(varName, obj);
	}

}
