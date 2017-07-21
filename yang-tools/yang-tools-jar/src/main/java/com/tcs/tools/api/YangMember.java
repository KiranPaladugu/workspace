package com.tcs.tools.api;

import java.util.List;

import com.test.xpath.api.TreeObject;

public interface YangMember extends YangObject , TreeObject{
	public String getName();
	public Object getValue();
	public List<YangMember> getAllMembers();
	public YangMember getMember(String name);
	public YangCardinality getCardinality();
	public boolean hasRequiredMembers();
	public YangMember getParent();
	public boolean addMember(YangMember member);
}
