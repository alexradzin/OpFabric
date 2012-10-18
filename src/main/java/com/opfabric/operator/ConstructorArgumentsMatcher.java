package com.opfabric.operator;

import java.lang.reflect.Constructor;

public class ConstructorArgumentsMatcher<C> extends ArgumentsMatcher<C, Constructor<C>> {
	public ConstructorArgumentsMatcher(Class<C> clazz) {
		super(clazz);
	}
	
	protected Constructor<C> getCallableElement(String name, Class<?>[] paramTypes) {
		try {
			return clazz.getConstructor(paramTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

	
	@SuppressWarnings("unchecked")
	protected Constructor<C>[] getCallableElements() {
		return (Constructor<C>[])clazz.getConstructors();
	}
	
	protected Class<?>[] getParameterTypes(Constructor<C> constructor) {
		return constructor.getParameterTypes();
	}
	
}
