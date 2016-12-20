package com.tcs.tools.yang;

public interface YangLeaf extends YangMember {
	public static String NAME = "leaf";
	public static String[] MEMBERS = { "config", "default", "description", "if-feature", "mandatory", "must",
			"reference", "status", "type", "units", "when" };

}
