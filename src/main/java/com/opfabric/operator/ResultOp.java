package com.opfabric.operator;

import java.io.Serializable;

/**
 * This interface is similar to {@link Op} and {@link BooleanOp} but it is parametrized and its
 * business method returns generic type. 
 * @author alexr
 *
 * @param <R>
 */
public interface ResultOp<R> extends Serializable {
	public R _();
}
