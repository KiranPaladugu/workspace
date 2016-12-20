package com.tcs.tools.yang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tcs.tools.api.YangObject;
import com.tcs.tools.api.YangTreeNode;

public class TreeNode implements YangTreeNode {
	private TreeNode nextNode;
	private TreeNode previousNode;
	private List<YangTreeNode> children = new ArrayList<YangTreeNode>();
	private TreeNode parent;
	private YangObject yangObj;

	public List<YangTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<YangTreeNode> children) {
		this.children = children;
	}

	public void setNextNode(TreeNode nextNode) {
		this.nextNode = nextNode;
	}

	public void setPreviousNode(TreeNode previousNode) {
		this.previousNode = previousNode;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public void setYangObj(YangObject yangObj) {
		this.yangObj = yangObj;
	}

	public YangTreeNode getNextNode() {
		return nextNode;
	}

	public YangTreeNode getPreviousNode() {
		return previousNode;
	}

	public YangTreeNode getParent() {
		return parent;
	}

	public boolean hasParent() {
		return parent != null;
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public boolean isRoot() {
		return !hasParent();
	}

	public List<? extends YangTreeNode> getAllChidldNodes() {
		return children;
	}

	public YangObject getRefferingObject() {
		return yangObj;
	}

	public boolean addChild(YangTreeNode childNode) {
		if (!children.contains(children)) {
			children.add(childNode);
			return true;
		}
		return false;
	}

	public void addAll(Collection<YangTreeNode> children) {
		for (YangTreeNode node : children) {
			if (!this.children.contains(node)) {
				this.children.add(node);
			}
		}

	}

	public boolean removeChild(YangTreeNode childToRemove) {
		return children.remove(childToRemove);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((yangObj == null) ? 0 : yangObj.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeNode other = (TreeNode) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (yangObj == null) {
			if (other.yangObj != null)
				return false;
		} else if (!yangObj.equals(other.yangObj))
			return false;
		return true;
	}
	
}
