package com.tcs.tools.yang;

public interface YangLeafList extends YangMember {
	public static String NAME = "leaf-list";
	public static String[] MEMBERS = { "config", "description", "if-feature", "max-elements", "min-elements", "must",
			"ordered-by", "reference", "status", "type", "units", "when" };

}
