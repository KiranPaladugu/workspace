package com.tcs.tools.yang;

public interface YangContainer extends YangMember {
	public static String NAME = "container";
	public static String[] MEMBERS = { "anyxml", "choice", "config", "container", "description", "grouping",
			"if-feature", "leaf", "leaf-list", "list", "must", "presence", "reference", "status", "typedef", "uses",
			"when" };

}
