package com.opfabric.operator;

import java.io.Serializable;

/**
 * This is the most common and useful interface in this library. 
 * It is defined as a command pattern. The interface declares only one business method called {@link #_()}
 * unlike usual java naming conventions. It is done to make code of anonymous inner classes that often
 * implement this interface shorter.
 * <br/><br/>
 * This interface is implemented by all operations that should not return values, e.g. all operations that
 * represent regular java operators and allow creating logic programmatically.
 * <br/><br/>
 * This interface extends {@link Serializable} that forces all implementors to be serializable too, so 
 * commands created using this library can be saved in files, sent over network, stored in database etc.
 * 
 * @author alexr
 * 
 * @see #_()
 * @see BooleanOp
 */
public interface Op extends Serializable {
	public void _();
}
