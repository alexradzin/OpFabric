package com.opfabric.complex;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.opfabric.operator.Op;

public class OpSerializationTest extends OpCopyTest {

	@Override
	protected Op clone(Op op) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(op);
		oos.flush();
		oos.close();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		Op copy = (Op)ois.readObject();
		return copy;
	}

}
