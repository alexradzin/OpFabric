package com.opfabric.operator;

public interface ThrowingOp<T extends Throwable> {
	public void _() throws T;
}
