package com.opfabric.operator;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;


abstract class ArgumentsMatcher<C, F extends Member> {
	protected final Class<C> clazz;
	
	private final static Map<Class<?>, Integer> numberTypesCompatibility = new HashMap<>();
	static {
		numberTypesCompatibility.put(byte.class, 1);
		numberTypesCompatibility.put(Byte.class, 1);

		numberTypesCompatibility.put(short.class, 2);
		numberTypesCompatibility.put(Short.class, 2);

		numberTypesCompatibility.put(int.class, 3);
		numberTypesCompatibility.put(Integer.class, 3);

		numberTypesCompatibility.put(long.class, 4);
		numberTypesCompatibility.put(Long.class, 4);

		numberTypesCompatibility.put(float.class, 5);
		numberTypesCompatibility.put(Float.class, 5);

		numberTypesCompatibility.put(double.class, 6);
		numberTypesCompatibility.put(Double.class, 6);
	}
	
	ArgumentsMatcher(Class<C> clazz) {
		this.clazz = clazz;
	}

	protected abstract F getCallableElement(String name, Class<?>[] paramTypes);
	protected abstract F[] getCallableElements();
	protected abstract Class<?>[] getParameterTypes(F f);

	private Class<?>[] getArgTypes(Object[] args) {
		if (args == null) {
			return null;
		}
		@SuppressWarnings("rawtypes")
		Class[] types = new Class[args.length];
		for (int i = 0;  i < args.length;  i++) {
			types[i] = args[i] == null ? null : args[i].getClass();
		}
		return types;
	}
	
	public F findBestMatch(String name, Object[] args) {
		// first check exact parameters match.
		Class<?>[] types = getArgTypes(args);
		F f = getCallableElement(name, types);
		if (f != null) {
			return f;
		}
		
		ELEMENT:
		for (F e : getCallableElements()) {
			if (!e.getName().equals(name)) {
				continue;
			}
		
			Class<?>[] elemParamTypes = getParameterTypes(e);
			if (elemParamTypes.length != types.length) {
				continue;
			}
			
			for(int i = 0;  i < types.length;  i++) {
				Class<?> p = elemParamTypes[i];
				Class<?> a = types[i];
				if (!isAssignableFrom(p, a)) {
					continue ELEMENT;
				}
			}
			
			return e;
		}
		
		return null;
	}
	
	private boolean isAssignableFrom(Class<?> c1, Class<?> c2) {
		if (c1.isAssignableFrom(c2)) {
			return true;
		}
		if ((Boolean.class.equals(c1) || boolean.class.equals(c1)) && (Boolean.class.equals(c2) || boolean.class.equals(c2))) {
			return true;
		}
		if ((Character.class.equals(c1) || char.class.equals(c1)) && (Character.class.equals(c2) || char.class.equals(c2))) {
			return true;
		}
		if ((Number.class.isAssignableFrom(c1) || c1.isPrimitive())  && (Number.class.isAssignableFrom(c2) || c2.isPrimitive())) {
			Integer n1 = numberTypesCompatibility.get(c1);
			Integer n2 = numberTypesCompatibility.get(c2);
			if (n1 == null || n2 == null) {
				return false;
			}
			return n1 >= n2;
		}
		return false;
	}
}
