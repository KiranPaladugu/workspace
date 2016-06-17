package com.test.models;

import java.io.Serializable;

public interface TransferableDataObject extends Serializable{

	public static int BINARY=0;
	public static int CHAR=1;
	public abstract String getName();

	public abstract void setName(String name);

	public abstract long getLength();

	public abstract void setLength(long length);

	public abstract long getSize();

	public abstract byte[] getData();

	public abstract void setData(byte[] data);

	public abstract int getType();

	public abstract void setType(int type);

	public abstract void setCount(int count);
	public abstract int getCount();
}