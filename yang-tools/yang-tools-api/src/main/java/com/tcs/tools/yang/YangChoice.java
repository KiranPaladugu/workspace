package com.tcs.tools.yang;

public interface YangChoice extends YangMember {

	public static String NAME = "choice";
	public static String[] MEMBERS = { "anyxml", "case", "config", "container", "default", "description", "if-feature",
			"leaf", "leaf-list", "list", "mandatory", "reference", "status", "when" };

}
