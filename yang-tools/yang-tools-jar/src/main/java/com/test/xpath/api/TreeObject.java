package com.test.xpath.api;

public interface TreeObject extends XPathObject {
    TreeObject getNext();
    TreeObject getPrevious();
    TreeObject getParent();
    String getType();
}
