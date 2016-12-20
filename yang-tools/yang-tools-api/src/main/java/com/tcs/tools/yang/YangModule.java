package com.tcs.tools.yang;

import java.util.List;

public interface YangModule extends YangType {
	public static String NAME = "module";
	public static String[] MEMBERS = { "anyxml", "augment", "choice", "contact", "container", "description",
			"deviation", "extension", "feature", "grouping", "identity", "import", "include", "leaf", "leaf-list",
			"list", "namespace", "notification", "organization", "prefix", "reference", "revision", "rpc", "typedef",
			"uses", "yang-version" };

	public YangMember getImports();

	public YangMember getAllRevisions();

	public List<YangTypeDef> getAllTypeDef();

	public YangTypeDef getTypeDef(String name);

	public String getPrefix();

	public String getNamespace();

	public String[] getMemberNames();
}
