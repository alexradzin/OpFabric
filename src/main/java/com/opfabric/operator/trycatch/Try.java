package com.opfabric.operator.trycatch;

import java.util.Map;
import java.util.TreeMap;

import com.opfabric.operator.Op;
import com.opfabric.operator.OpWithArg;
import com.opfabric.operator.ThrowingOp;

public class Try implements Op {
	private static final long serialVersionUID = 4014764107304456678L;
	private Op tryOp;
	private ThrowingOp<? extends Throwable> tryAndThrowOp;
	
	private Op finallyOp;
	
	private Map<Class<? extends Throwable>, OpWithArg<? extends Throwable>> catchOps = 
			new TreeMap<Class<? extends Throwable>, OpWithArg<? extends Throwable>>(new InheritedExceptionComparator()); 

	public Try() {
		
	}
	
	public Try(Op op) {
		this.tryOp = op;
	}

	public Try(ThrowingOp<? extends Throwable> op) {
		this.tryAndThrowOp = op;
	}
	
	
	public <T extends Throwable> Try $catch(Class<T> exceptionType, OpWithArg<T> op) {
		catchOps.put(exceptionType, op);
		return this;
	}

	public Try $catch(Class<? extends Throwable>[] exceptionTypes, OpWithArg<? extends Throwable> op) {
		for (Class<? extends Throwable> exceptionType : exceptionTypes) {
			catchOps.put(exceptionType, op);
		}
		return this;
	}
	
	public Try $finally(Op op) {
		finallyOp = op;
		return this;
	}


	public void _() {
		try {
			if (tryOp != null) {
				tryOp._();
			} else {
				tryAndThrowOp._();
			}
		} catch (Throwable e){
			@SuppressWarnings("unchecked")
			OpWithArg<Throwable> catchOp = (OpWithArg<Throwable>)catchOps.get(e.getClass());
			
			if (catchOp == null) {
				if (e instanceof RuntimeException) {
					throw (RuntimeException)e;
				}
				throw new RuntimeException(e);
			}
			catchOp._((Throwable)e);
		} finally {
			if (finallyOp != null) {
				finallyOp._();
			}
		}
	}
}
