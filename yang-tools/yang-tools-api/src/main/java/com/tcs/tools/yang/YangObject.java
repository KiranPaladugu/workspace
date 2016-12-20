package com.tcs.tools.yang;

import java.io.Serializable;

public interface YangObject extends Serializable {
	public YangTree getTreeModel();
	public boolean validate();
	public String getYangRefType();
}
