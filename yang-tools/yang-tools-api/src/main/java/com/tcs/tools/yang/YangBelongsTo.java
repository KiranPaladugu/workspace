package com.tcs.tools.yang;

public interface YangBelongsTo extends YangMember {

	public static String NAME = "belongs-to";
	public static String[] MEMBERS = { "prefix" };
	public static String[] REQUIRED = { "prefix" };
}
