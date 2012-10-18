package com.opfabric.operator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatchingOp implements BooleanOp {
	private static final long serialVersionUID = 6138587176185855304L;
	
	private Matcher matcher;
	
	public PatternMatchingOp() {
	}
	
	public PatternMatchingOp(String str, Pattern p) {
		this.matcher = p.matcher(str);
	}

	public PatternMatchingOp(Pattern p, String str) {
		this.matcher = p.matcher(str);
	}

	public boolean _() {
		return matcher.find();
	}

}
