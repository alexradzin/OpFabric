package com.opfabric.operator;

import java.io.Serializable;

/**
 * This interface is similar to {@link Op} but declares business method that returns {@code boolean} value.
 * As in case of {@link Op} the method is called {@link #_()} to make code shorter.
 * <br/><br>
 * This interface is implemented by regular boolean operations like {@link AndOp}, {@link OrOp} etc.
 * 
 * @author alexr
 */
public interface BooleanOp extends Serializable {
	public boolean _();
}
