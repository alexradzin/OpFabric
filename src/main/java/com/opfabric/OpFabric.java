package com.opfabric;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.opfabric.operator.AndOp;
import com.opfabric.operator.BooleanOp;
import com.opfabric.operator.CallOp;
import com.opfabric.operator.CompareOp;
import com.opfabric.operator.GetOp;
import com.opfabric.operator.IncrementOp;
import com.opfabric.operator.IntOp;
import com.opfabric.operator.MethodArgumentsMatcher;
import com.opfabric.operator.NotOp;
import com.opfabric.operator.Op;
import com.opfabric.operator.OrOp;
import com.opfabric.operator.PatternMatchingOp;
import com.opfabric.operator.ResultOp;
import com.opfabric.operator.Sequence;
import com.opfabric.operator.SetOp;
import com.opfabric.operator.ThrowingOp;
import com.opfabric.operator.ValueOp;
import com.opfabric.operator.XorOp;
import com.opfabric.operator.ifelse.If;
import com.opfabric.operator.ifelse.Then;
import com.opfabric.operator.loop.Do;
import com.opfabric.operator.loop.For;
import com.opfabric.operator.loop.While;
import com.opfabric.operator.switchcase.IntSwitch;
import com.opfabric.operator.switchcase.StringSwitch;
import com.opfabric.operator.trycatch.Try;

/**
 * This is an entry point (or facade) to <code>OpFabric</code> - the library that allows 
 * creating composite commands programmatically.
 * @author alexr
 *
 */
public class OpFabric {
	private static ThreadLocal<Map<String, Object>> variables = new ThreadLocal<Map<String, Object>>();
	
	
	public static Try $try(Op op) {
		return new Try(op);
	}

	
	public static <T extends Throwable> Try $try(ThrowingOp<T> op) {
		return new Try(op);
	}
	
	
	public static Then $if(BooleanOp condition) {
		return new If(condition);
	}
	
	public static IntSwitch $switch(IntOp sw) {
		return new IntSwitch(sw);
	}

	@SuppressWarnings("serial")
	public static IntSwitch $switch(final int sw) {
		return new IntSwitch(new IntOp() {
			public int _() {
				return sw;
			}
		});
	}

	
	public static StringSwitch $switch(ResultOp<String> sw) {
		return new StringSwitch(sw);
	}

	@SuppressWarnings("serial")
	public static StringSwitch $switch(final String sw) {
		return new StringSwitch(new ResultOp<String>() {
			public String _() {
				return sw;
			}
		});
	}
	
	
	
	public static While $while(BooleanOp condition, Op body) {
		return new While(condition, body);
	}

	
	public static Do $do(Op body) {
		return new Do(body);
	}
	
	public static For $for(Op before, BooleanOp condition, Op after, Op body) {
		return new For(before, condition, after, body);
	}
	
	public static For $for(final String counterName, final int initValue, final int lastValue, int step, Op body) {
		CompareOp.Op comp = initValue <= lastValue ? CompareOp.Op.LT : CompareOp.Op.GT;
		return new For(
				new SetOp<Integer>(counterName, initValue),
				new CompareOp<Integer>(new GetOp<Integer>(counterName, Integer.class), comp, new ValueOp<Integer>(lastValue)),
				new IncrementOp<Number>(counterName, step), body);
	}
	
	public static <V> void set(String name, V value) {
		getVars().put(name, value);
	}
	
	@SuppressWarnings("unchecked")
	public static <V> V get(String name, Class<V> type) {
		return (V)getVars().get(name);
	}
	
	public static <V> SetOp<V> $set(String name, V value) {
		return new SetOp<V>(name, value);
	}
	
	public static Sequence $sequence(Op ... ops) {
		return new Sequence(ops);
	}
	
	
	public static <T> BooleanOp $eq(T value, T benchmark) {
		return compare(value, CompareOp.Op.EQ, benchmark);
	}

	public static <T> BooleanOp $ne(T value, T benchmark) {
		return compare(value, CompareOp.Op.NE, benchmark);
	}

	public static <T> BooleanOp $gt(T value, T benchmark) {
		return compare(value, CompareOp.Op.GT, benchmark);
	}

	public static <T> BooleanOp $ge(T value, T benchmark) {
		return compare(value, CompareOp.Op.GE, benchmark);
	}

	public static <T> BooleanOp $lt(T value, T benchmark) {
		return compare(value, CompareOp.Op.LT, benchmark);
	}

	public static <T> BooleanOp $le(T value, T benchmark) {
		return compare(value, CompareOp.Op.LE, benchmark);
	}
	
	public static <T> BooleanOp $containedIn(T value, T benchmark) {
		return compare(value, CompareOp.Op.CONTAINED_IN, benchmark);
	}

	public static <T> BooleanOp $contains(T benchmark, T value) {
		return $containedIn(value, benchmark);
	}

	public static <T> BooleanOp $matchedBy(T value, T benchmark) {
		return compare(value, CompareOp.Op.MATCHED_BY, benchmark);
	}

	public static <T> BooleanOp $matches(T benchmark, T value) {
		return compare(value, CompareOp.Op.MATCHED_BY, benchmark);
	}

	public static <T> BooleanOp $startOf(T value, T benchmark) {
		return compare(value, CompareOp.Op.START_OF, benchmark);
	}

	public static <T> BooleanOp $startsWith(T benchmark, T value) {
		return compare(value, CompareOp.Op.START_OF, benchmark);
	}

	public static <T> BooleanOp $endOf(T value, T benchmark) {
		return compare(value, CompareOp.Op.END_OF, benchmark);
	}

	public static <T> BooleanOp $endsWith(T benchmark, T value) {
		return compare(value, CompareOp.Op.END_OF, benchmark);
	}
	
	
	public static BooleanOp $matches(Pattern pattern, String str) {
		return new PatternMatchingOp(pattern, str);
	}

	public static BooleanOp $matchedBy(String str, Pattern pattern) {
		return new PatternMatchingOp(str, pattern);
	}

	public static BooleanOp $not(BooleanOp op) {
		return new NotOp(op);
	}

	public static BooleanOp $and(BooleanOp ... ops) {
		return new AndOp(ops);
	}

	public static BooleanOp $or(BooleanOp ... ops) {
		return new OrOp(ops);
	}

	public static BooleanOp $xor(BooleanOp ... ops) {
		return new XorOp(ops);
	}
	
	public static <T> ResultOp<T> $new(Class<T> clazz, Object ... args) {
		return new CallOp<T, T>(clazz, true, args);
	}
	
	@SuppressWarnings("unchecked")
	public static <T, R> ResultOp<R> $call(
			Class<T> type, String objName, String methodName, Class<R> returnType, Object ... args) {
		
		Method method = new MethodArgumentsMatcher<T>(type).findBestMatch(methodName, args);
		
		return new CallOp<T, R>(
				new GetOp<T>(objName, type), 
				new ValueOp<Method>(method),
				new ValueOp<Class<R>>((Class<R>)method.getReturnType()),
				new ValueOp<Object[]>(args)
		);
	}
	
	private static Map<String, Object> getVars() {
		Map<String, Object> vars = variables.get();
		if (vars == null) {
			vars = new LinkedHashMap<String, Object>();
			variables.set(vars);
		}
		return vars;
	}
	
	private static <T> BooleanOp compare(T value, CompareOp.Op op, T benchmark) {
		return new CompareOp<T>(value, op, benchmark);
	}
	
}
