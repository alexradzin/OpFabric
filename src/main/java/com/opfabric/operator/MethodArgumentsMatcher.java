package com.opfabric.operator;

import java.lang.reflect.Method;

public class MethodArgumentsMatcher<C> extends ArgumentsMatcher<C, Method> {
	public MethodArgumentsMatcher(Class<C> clazz) {
		super(clazz);
	}

	protected Method getCallableElement(String name, Class<?>[] paramTypes) {
		try {
			return clazz.getMethod(name, paramTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
	}
	
	
	protected Method[] getCallableElements() {
		return clazz.getMethods();
	}
	
	protected Class<?>[] getParameterTypes(Method method) {
		return method.getParameterTypes();
	}
	
//	protected boolean elementMatches(Method method) {
//		return name.equals(method.getName());
//	}
	
}
