package com.tcs.tools.api;

public interface YangTypeDef extends YangType {

	public static String NAME = "typedef";
	public static String[] MEMBERS = { "default", "description", "reference", "status", "type", "units" };

}
