package com.tcs.tools.api;

import java.io.Serializable;

public interface YangObject extends Serializable {
	public YangTree toTree();
	public boolean validate();
	public String getStatementName();
	public String[] getMemeberNames();
	public String[] getRequiredMemberNames();
	public String getYangFormat();
}
