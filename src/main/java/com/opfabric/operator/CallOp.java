package com.opfabric.operator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CallOp<T, R> implements ResultOp<R> {
	private static final long serialVersionUID = -2986001251171126273L;
	private ResultOp<T> objProvider;
	private ResultOp<Method> methodProvider;
	private ResultOp<Constructor<T>> constructorProvider;
	private ResultOp<Object[]> argsProvider;
//	private ResultOp<Class<R>> returnTypeProvider;


	public CallOp(ResultOp<T> objProvider, ResultOp<Method> methodProvider, ResultOp<Class<R>> returnTypeProvider, ResultOp<Object[]> argsProvider) {
		this.objProvider = objProvider;
		this.methodProvider = methodProvider;
		this.argsProvider = argsProvider;
//		this.returnTypeProvider = returnTypeProvider;
	}

	public CallOp(ResultOp<Constructor<T>> constructorProvider, ResultOp<Object[]> argsProvider) {
		this.constructorProvider = constructorProvider;
		this.argsProvider = argsProvider;
	}
	
	@SuppressWarnings("unchecked")
	public CallOp(T obj, Method method, Object ... args) {
		this(new ValueOp<T>(obj), new ValueOp<Method>(method), new ValueOp<Class<R>>((Class<R>)method.getReturnType()), new ValueOp<Object[]>(args));
	}

	@SuppressWarnings("unchecked")
	public CallOp(Method method, Object ... args) {
		this(null, new ValueOp<Method>(method), new ValueOp<Class<R>>((Class<R>)method.getReturnType()), new ValueOp<Object[]>(args));
	}

	
	@SuppressWarnings("unchecked")
	public CallOp(T obj, String methodName, Object ... args) {
		this(obj, new MethodArgumentsMatcher<T>((Class<T>)obj.getClass()).findBestMatch(methodName, args), args);		
	}

	/**
	 * 
	 * @param clazz
	 * @param create just to solve constructors ambiguity problem. 
	 * @param args
	 */
	public CallOp(Class<T> clazz, boolean create, Object ... args) {
		this(
				new ValueOp<Constructor<T>>(new ConstructorArgumentsMatcher<T>(clazz).findBestMatch(clazz.getName(), args)), 
				new ValueOp<Object[]>(args));		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public R _() {
		try {
			if (constructorProvider != null) {
				return (R)constructorProvider._().newInstance(argsProvider._());
			}
			return (R)methodProvider._().invoke(objProvider._(), argsProvider._());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
			throw new IllegalStateException(e);
		}
	}
}
