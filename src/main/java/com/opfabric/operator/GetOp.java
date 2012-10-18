package com.opfabric.operator;

import com.opfabric.OpFabric;

public class GetOp<R> implements ResultOp<R> {
	private static final long serialVersionUID = -1582452095991675069L;

	private String name;
	private Class<R> type;

	public GetOp(String name, Class<R> type) {
		this.name = name;
		this.type = type;
	}
	
	
	@Override
	public R _() {
		return OpFabric.get(name, type);
	}

}
