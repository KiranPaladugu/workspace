package com.test.xpath.api;

import java.util.List;

public interface Node extends TreeObject {

    List<TreeObject> getAllChildren();
    Node getNext();
    Node getPrevious();
    Node getParent();
    boolean addChild(TreeObject child);
}
