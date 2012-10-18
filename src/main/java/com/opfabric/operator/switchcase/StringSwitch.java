package com.opfabric.operator.switchcase;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opfabric.operator.Op;
import com.opfabric.operator.ResultOp;

public class StringSwitch implements Op {
	private static final long serialVersionUID = -5865236642100951366L;
	
	private final static String DEFAULT_KEY = "$$_OP_FABRIC_SWITCH_DEFAULT_KEY_$$";
	
	private ResultOp<String> sw;
	// key is String or Pattern
	private Map<Object, Op> operations = new LinkedHashMap<Object, Op>();
	
	public StringSwitch() {
		
	}
	
	public StringSwitch(ResultOp<String> sw) {
		this.sw = sw;
	}

	
	public StringSwitch $case(String value, Op op) {
		operations.put(value, op);
		return this;
	}

	public StringSwitch $case(Pattern value, Op op) {
		operations.put(value, op);
		return this;
	}
	
	public Op $default(Op op) {
		operations.put(DEFAULT_KEY, op);
		return this;
	}
	
	
	public void _() {
		Op op = operations.get(sw._());
		if (op != null) {
			op._();
			return;
		}
		
		for (Entry<Object, Op> entry : operations.entrySet()) {
			if (entry.getKey() instanceof Pattern) {
				Pattern p = (Pattern)entry.getKey();
				Matcher m = p.matcher(sw._());
				if (m.find()) {
					entry.getValue()._();
					return;
				}
			}
		}
		
		Op defaultOp = operations.get(DEFAULT_KEY);
		if (defaultOp != null) {
			defaultOp._();
			return;
		}
	}

}
