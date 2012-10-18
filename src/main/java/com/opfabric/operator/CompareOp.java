package com.opfabric.operator;

import java.util.regex.Pattern;

public class CompareOp<T> implements BooleanOp {
	private static final long serialVersionUID = 6844720784275939722L;
	
	public static enum Op {
		SAME {
			@Override
			<T> boolean check(T value, T benchmark) {
				return benchmark == value;
			}
		}, 
		NOT_SAME {
			@Override
			<T> boolean check(T value, T benchmark) {
				return !SAME.check(value, benchmark);
			}
		},  
		EQ {
			@Override
			<T> boolean check(T value, T benchmark) {
				//return benchmark == null ? value == null : benchmark.equals(value);
				return compare(value, benchmark) == 0;
			}
		}, 
		NE {
			@Override
			<T> boolean check(T value, T benchmark) {
				return !EQ.check(value, benchmark);
			}
		},  
		LT {
			@Override
			<T> boolean check(T value, T benchmark) {
				return compare(value, benchmark) < 0;
			}
		}, 
		LE {
			@Override
			<T> boolean check(T value, T benchmark) {
				return compare(value, benchmark) <= 0;
			}
		},  
		GT {
			@Override
			<T> boolean check(T value, T benchmark) {
				return compare(value, benchmark) > 0;
			}
		},  
		GE {
			@Override
			<T> boolean check(T value, T benchmark) {
				return compare(value, benchmark) >= 0;
			}
		}, 
		CONTAINED_IN {
			@Override
			<T> boolean check(T value, T benchmark) {
				return value == null ? false : benchmark.toString().contains(value.toString());
			}
		},  
		MATCHED_BY {
			@Override
			<T> boolean check(T value, T benchmark) {
				return value == null ? false : Pattern.compile(benchmark.toString()).matcher(value.toString()).find();
			}
		},  
		START_OF {
			@Override
			<T> boolean check(T value, T benchmark) {
				return value == null ? false : benchmark.toString().startsWith(value.toString());
			}
		},  
		END_OF {
			@Override
			<T> boolean check(T value, T benchmark) {
				return value == null ? false : benchmark.toString().endsWith(value.toString());
			}
		}, 
		;
		
		abstract <T> boolean check(T value, T benchmark);
	}
	
	
	private ResultOp<T> valueOp;
	private Op op;
	private ResultOp<T> benchmarkOp;
	

	public CompareOp(ResultOp<T> valueOp, Op op, ResultOp<T> benchmarkOp) {
		this.benchmarkOp = benchmarkOp;
		this.op = op;
		this.valueOp = valueOp;
	}
	
	
	public CompareOp(T value, Op op, T benchmark) {
		this.benchmarkOp = new ValueOp<T>(benchmark);
		this.op = op;
		this.valueOp = new ValueOp<T>(value);
	}


	public boolean _() {
		return op.check(valueOp._(), benchmarkOp._());
	}

	
	@SuppressWarnings("unchecked")
	private static <T> int compare(T value, T benchmark) {
		if (benchmark == null) {
			return value == null ? 0 : 1;
		}
		if (value == null) {
			return benchmark == null ? 0 : -1;
		}
		if (Comparable.class.isAssignableFrom(benchmark.getClass())) {
			return ((Comparable<T>)cast(value)).compareTo((T)cast(benchmark));
		}
		throw new IllegalArgumentException(benchmark.getClass() + " does not implement Comparable");
	}

	
	/**
	 * Transforms numeric value to highest type. For example integer 123 is transformed to long 123.
	 * This is done to be able to safely compare numeric values belonging to different types.
	 * @param one
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <V, R> R cast(V one) {
		if (one instanceof Byte || one instanceof Short || one instanceof Integer) {
			return (R)new Long(((Number)one).longValue());
		}
		
		return (R)one;
	}
}
