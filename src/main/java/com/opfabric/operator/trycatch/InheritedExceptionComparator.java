package com.opfabric.operator.trycatch;

import java.util.Comparator;

public class InheritedExceptionComparator implements Comparator<Class<? extends Throwable>> {
	public int compare(Class<? extends Throwable> c1,
			Class<? extends Throwable> c2) {
		if (c1.equals(c2) || c2.isAssignableFrom(c1)) {
			return 0;
		}
		return c1.getName().compareTo(c2.getName());
	}
}
