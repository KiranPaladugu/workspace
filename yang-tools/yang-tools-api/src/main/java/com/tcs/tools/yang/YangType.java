package com.tcs.tools.yang;

public interface YangType extends YangMember {
	public static String NAME = "type";
	public static String[] MEMBERS = { "bit", "enum", "length", "path", "pattern", "range", "require-instance",
			"type" };

	public String getType();

}
