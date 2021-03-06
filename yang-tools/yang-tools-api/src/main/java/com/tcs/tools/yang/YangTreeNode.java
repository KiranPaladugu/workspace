package com.tcs.tools.yang;

import java.util.Collection;
import java.util.List;

public interface YangTreeNode {
	public YangTreeNode getNextNode();
	public YangTreeNode getPreviousNode();
	public YangTreeNode getParent();
	public boolean hasParent();
	public boolean hasChildren();
	public boolean isRoot();
	public List<YangTreeNode> getAllChidldNodes();
	public YangObject getRefferingObject();
	public boolean addChild(YangTreeNode childNode);
	public void addAll(Collection<YangTreeNode> children);
	public boolean removeChild(YangTreeNode childToRemove);

}
