package com.tcs.tools.yang;

public interface YangList extends YangMember {

	public static String NAME = "list";
	public static String[] MEMBERS = { "anyxml", "choice", "config", "container", "description", "grouping",
			"if-feature", "key", "leaf", "leaf-list", "list", "max-elements", "min-elements", "must", "ordered-by",
			"reference", "status", "typedef", "unique", "uses", "when" };

}
