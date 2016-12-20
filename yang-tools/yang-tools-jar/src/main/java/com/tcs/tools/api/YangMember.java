package com.tcs.tools.api;

import java.util.List;

public interface YangMember extends YangObject {
	public String getName();
	public Object getValue();
	public List<YangMember> getAllMembers();
	public YangMember getMember(String name);
	public YangCardinality getCardinality();
	public boolean hasRequiredMembers();
	public YangMember getParent();
	public boolean addMember(YangMember member);
}
