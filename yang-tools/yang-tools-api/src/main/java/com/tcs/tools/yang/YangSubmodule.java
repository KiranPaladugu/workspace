package com.tcs.tools.yang;

public interface YangSubmodule extends YangType {

	public static String NAME = "submodule";
	public static String[] MEMBERS = { "anyxml", "augment", "belongs-to", "choice", "contact", "container",
			"description", "deviation", "extension", "feature", "grouping", "identity", "import", "include", "leaf",
			"leaf-list", "list", "notification", "organization", "reference", "revision", "rpc", "typedef", "uses",
			"yang-version" };

}
