package com.tcs.tools.api;

import java.util.List;

public interface YangModule extends YangType {
	
	public YangMember getImports();

	public YangMember getAllRevisions();

	public List<YangTypeDef> getAllTypeDef();

	public YangTypeDef getTypeDef(String name);

	public String getPrefix();

	public String getNamespace();

	public String[] getMemberNames();
}
