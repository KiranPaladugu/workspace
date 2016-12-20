package com.tcs.tools.yang;

import java.util.List;

public interface YangMember extends YangObject {
	public String getName();
	public Object getValue();
	public List<YangMember> getAllMembers();
	public YangMember getMember(String name);
}
